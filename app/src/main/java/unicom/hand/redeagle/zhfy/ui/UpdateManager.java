package unicom.hand.redeagle.zhfy.ui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import unicom.hand.redeagle.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class UpdateManager {

	private Context mContext;
	private String updateMsg = "发现新版本,建议立即更新使用！";// 提示语
	private String apkUrl = "";// 返回的安装包url
	private Dialog noticeDialog;
	Dialog downloadDialog;
	/*
	 * 下载包安装路径 private final String savePath = Environment
	 * .getExternalStoragePublicDirectory(
	 * Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
	 */
	private final String savePath = Environment.getExternalStorageDirectory()
			+ File.separator + "download";
	private final String saveFileName = savePath + File.separator
			+ "UnicomApp.apk";
	//	 获取当前客户端版本号地址
	private String updateUri =
			"http://125.46.106.41/webclient/query.do?action=queryversion";
//	private String updateUri = "http://202.102.246.168:8080/Tongbu/cx1.jsp";

	/* 进度条与通知ui刷新的handler和msg常量 */
	ProgressBar mProgress;
	private static final int UP_OK = 0;
	private static final int DOWN_UPDATE = 1;
	private static final int DOWN_OVER = 2;
	private static final String TAG = "UpdateManager";
	private int progress;
	private Thread downLoadThread;
	boolean interceptFlag = false;

	private String serverVersion = "0";
	private String partyId;
	// 执行更新Handler
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case UP_OK:
//				int localVersion = getAppVersionCode(mContext);
					int localVersion = Integer
							.parseInt( mContext.getString(R.string.version));;
					Log.d(TAG, "服务器版本：=" + serverVersion + ", 当前版本：="
							+ localVersion + ", 当前企业用户名：=" + partyId);

					try {
						if (serverVersion.length() > 0) {
							if (localVersion > 0) {
								if (Integer.parseInt(serverVersion) > localVersion) {
									showNoticeDialog();
								}
							}
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
//					Toast.makeText(mContext, serverVersion, 1).show();
					}
					break;
				case DOWN_UPDATE:
					mProgress.setProgress(progress);
					break;
				case DOWN_OVER:
					downloadDialog.dismiss();
					// 清理缓存信息qz-2013-08-27
					File file = mContext.getFilesDir();
					file.mkdir();
					installApk();
					break;
				default:
					break;
			}
		};
	};

	public UpdateManager(Context context, String mpartyId) {
		this.mContext = context;
		partyId = mpartyId;
		partyId += "_android";
	}

	// 外部接口让主Activity调用
	public void checkUpdateInfo() {

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				httpPost(partyId);
				mHandler.sendEmptyMessage(UP_OK);
			}
		});
		thread.start();

	}

	// 外部接口让主Activity调用
	public boolean isUpdate() {

//		int localVersion = getAppVersionCode(mContext);
		int localVersion = Integer
				.parseInt( mContext.getString(R.string.version));;
		boolean update = false;
		httpPost(partyId);

		Log.d(TAG, "服务器版本：=" + serverVersion + ", 当前版本：=" + localVersion
				+ ", 当前企业用户名：=" + partyId);

		if (serverVersion.length() > 0) {
			if (localVersion > 0) {
				if (Integer.parseInt(serverVersion) > localVersion) {
					update = true;
				}
			}
		}
		return update;
	}

	private int getAppVersionCode(Context context) {
		int versionCode = 0;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionCode = pi.versionCode;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}

		return versionCode;
	}

	// 版本更新提示
	private void showNoticeDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setCancelable(false);
		builder.setTitle("更新提示");
		builder.setMessage(updateMsg);
		builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog();
			}
		});
		builder.setNegativeButton("以后再说",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		noticeDialog = builder.create();
		noticeDialog.show();
	}

	// 下载进度提示
	public void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("版本更新");
		builder.setCancelable(false);
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.download_progressbar, null);
		mProgress = (ProgressBar) v.findViewById(R.id.download_progress);

		builder.setView(v);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.setCanceledOnTouchOutside(false);
		downloadDialog.show();

		downloadApk();
	}

	// 下载线程
	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				URL url = new URL(apkUrl);

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();

				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				File apkFile = new File(saveFileName);
				apkFile.deleteOnExit();
				FileOutputStream fos = new FileOutputStream(apkFile);

				int count = 0;
				byte buf[] = new byte[1024];

				do {
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					// 更新进度
					mHandler.sendEmptyMessage(DOWN_UPDATE);
					if (numread <= 0) {
						// 下载完成通知安装
						mHandler.sendEmptyMessage(DOWN_OVER);
						break;
					}
					fos.write(buf, 0, numread);
				} while (!interceptFlag);// 点击取消就停止下载.

				fos.close();
				is.close();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
				downloadDialog.dismiss();
			}
		}
	};

	/**
	 * 下载apk
	 */
	public void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	/**
	 * 安装apk
	 */
	private void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(apkfile);
		Log.d(TAG, "uri=" + uri.toString());
		i.setDataAndType(uri, "application/vnd.android.package-archive");
		mContext.startActivity(i);
	}

	public void httpPost(String partyId) {
		HttpPost httpRequest = new HttpPost(updateUri);
		/*
		 * Post运作传送变量必须用NameValuePair[]数组储存
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("partyId", partyId));
		try {
			/* 发出HTTP request */
			httpRequest.setHeader("Content-type",
					"application/x-www-form-urlencoded");
			httpRequest.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			// 设置请求超时
			HttpClient client = new DefaultHttpClient();
			// 请求超时
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					5000);
			/* 取得HTTP response */
			HttpResponse httpResponse = client.execute(httpRequest);
			// 获取内容实体信息
			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {
				String htmlPage = EntityUtils.toString(entity, "utf-8");
				Log.i(TAG, htmlPage);
				String[] page = htmlPage.split("#");

				serverVersion = page[0];
				apkUrl = page[1];
				Log.i(TAG, "serverVersion" + serverVersion);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, "serverVersion" + "网络请求错误");
			// TODO: handle exception
		}
	}
}