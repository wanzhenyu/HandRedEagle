package unicom.hand.redeagle.zhfy.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OKhttp请求的二次封装工具类
 * @author linana
 * @date 2017年3月27日
 */
public class OkhttpUtils {

	private static OkHttpClient singleton;
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static OkHttpClient.Builder builder;

	//	private static SSLSocketFactory socketFactory;
	public static final class RequestParams {
		private final List<String> names = new ArrayList<String>();
		private final List<String> values = new ArrayList<String>();

		public RequestParams add(String name, String value) {
			names.add(name);
			values.add(value);
			return this;
		}
	}

	public static OkHttpClient getInstance() {
		if (singleton == null) {
			synchronized (OkhttpUtils.class) {
				try {
					builder = new OkHttpClient.Builder();
					builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory());
					builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
					singleton = builder.build();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (singleton != null) {
			singleton.newBuilder()
					.connectTimeout(50000L, TimeUnit.MILLISECONDS)
					.readTimeout(50000L, TimeUnit.MILLISECONDS);

		}

		return singleton;

	}









	public static void setCertificates(InputStream... certificates)
	{
		try
		{
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(null);
			int index = 0;
			for (InputStream certificate : certificates)
			{
				String certificateAlias = Integer.toString(index++);
				keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

				try
				{
					if (certificate != null)
						certificate.close();
				} catch (IOException e)
				{
				}
			}

			SSLContext sslContext = SSLContext.getInstance("SSL");

			TrustManagerFactory trustManagerFactory =
					TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

			trustManagerFactory.init(keyStore);
			sslContext.init
					(
							null,
							trustManagerFactory.getTrustManagers(),
							new SecureRandom()
					);
//			singleton.sslSocketFactory(sslContext.getSocketFactory());
			getInstance().newBuilder().sslSocketFactory(sslContext.getSocketFactory()).hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return false;
                }
            }).build();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	/**
	 * 异步get请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param callback 请求结果回调
	 */
	public static void getAsync(String url, RequestParams params, ResultCallback callback) {
		HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
		if(params != null) {
			List<String> names = params.names;
			List<String> values = params.values;
			int length = names.size();
			if (length > 0) {
				for (int i = 0; i < length; i++) {
					builder.addQueryParameter(names.get(i), values.get(i));
				}
			}
		}
		HttpUrl formatUrl = builder.build();
		Request request = new Request.Builder().url(formatUrl).build();
		doRequest(request,callback);
	}

	/**
	 * 不带参数的异步post请求
	 * @param url 请求地址
	 * @param callback 请求结果回调
	 */
	public static void postAsync(String url, ResultCallback callback) {
		Request request = new Request.Builder().url(url).build();
		doRequest(request, callback);
	}
	/**
	 * JSON字符串提交
	 */
	public static void postAsyncJson(String url, String json, final ResultCallback callback) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder().url(url).post(body).build();
		doRequest(request, callback);
	}
	/**
	 * 表单提交
	 */
	public static void postAsyncForm(String url, HashMap<String, String> paramsMap, final ResultCallback callback) throws IOException {

		//创建一个FormBody.Builder
		FormBody.Builder builder = new FormBody.Builder();
		for (String key : paramsMap.keySet()) {
			//追加表单信息
			builder.add(key, paramsMap.get(key));

		}
		//生成表单实体对象
		RequestBody formBody = builder.build();
		Log.i("","--formBody--"+formBody);
		Request request = new Request.Builder().url(url).post(formBody).build();
		doRequest(request, callback);
	}

	/**
	 * 带参数的异步post请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param callback 请求结果回调
	 */
	public static void postAsync(String url, RequestParams params, final ResultCallback callback) {
		if(params == null) {
			postAsync(url, callback);
		}
		else {
			FormBody.Builder builder = new FormBody.Builder();
			List<String> names = params.names;
			List<String> values = params.values;
			int length = names.size();
			if (length > 0) {
				for (int i = 0; i < length; i++) {
					builder.add(names.get(i), values.get(i));
				}
			}
			RequestBody formBody = builder.build();
			Request request = new Request.Builder().url(url).post(formBody).build();
			doRequest(request, callback);
		}
	}

	/**
	 * 请求
	 * @param request 请求
	 * @param callback 请求结果回调
	 */
	public static void doRequest(final Request request, final ResultCallback callback) {
		OkhttpUtils.getInstance().newCall(request).enqueue(new Callback() {

			@Override
			public void onFailure(Call arg0, final IOException arg1) {
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						callback.onFailure(request, arg1);
					}
				});

			}

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				if (arg1.isSuccessful()) {
					final String responseBody = arg1.body().string();
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							callback.onResponse(responseBody);
						}
					});
				} else {
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							callback.onResponse(null);
						}
					});
				}
			}
		});
	}


	/**
	 * 下载文件
	 * @param fileUrl 文件url
	 * @param destFileDir 存储目标目录
	 */
//	public <T> void downLoadFile(String fileUrl, final String destFileDir, final ReqProgressCallBack callBack) {
//		final String fileName = MD5.encode(fileUrl);
//		final File file = new File(destFileDir, fileName);
//		if (file.exists()) {
////            successCallBack( file, callBack);
//			return;
//		}
//		final Request request = new Request.Builder().url(fileUrl).build();
//		final Call call = mOkHttpClient.newCall(request);
//		call.enqueue(new Callback() {
//
//			public void onFailure(Call call, IOException e) {
//				Log.e(TAG, e.toString());
////                failedCallBack("下载失败", callBack);
//			}
//
//			public void onResponse(Call call, Response response) throws IOException {
//				InputStream is = null;
//				byte[] buf = new byte[2048];
//				int len = 0;
//				FileOutputStream fos = null;
//				try {
//					long total = response.body().contentLength();
//					Log.e(TAG, "total------>" + total);
//					long current = 0;
//					is = response.body().byteStream();
//					fos = new FileOutputStream(file);
//					while ((len = is.read(buf)) != -1) {
//						current += len;
//						fos.write(buf, 0, len);
//						Log.e(TAG, "current------>" + current);
//						progressCallBack(total, current, callBack);
//					}
//					fos.flush();
////                    successCallBack((T) file, callBack);
//				} catch (IOException e) {
//					Log.e(TAG, e.toString());
////                    failedCallBack("下载失败", callBack);
//				} finally {
//					try {
//						if (is != null) {
//							is.close();
//						}
//						if (fos != null) {
//							fos.close();
//						}
//					} catch (IOException e) {
//						Log.e(TAG, e.toString());
//					}
//				}
//			}
//		});
//	}




	/** 
	 * 回调接口  
	 * @author huanglaiyun
	 * @date 2016年5月10日
	 */
	public interface ResultCallback {
		void onResponse(String body);
		void onFailure(Request request, IOException e);
	}

	public static void CancleAllRequest() {
		getInstance().dispatcher().cancelAll();
	}

//	public interface ReqProgressCallBack  extends ResultCallback{
//		/**
//		 * 响应进度更新
//		 */
//		void onProgress(long total, long current);
//	}
}