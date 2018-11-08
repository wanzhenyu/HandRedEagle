package unicom.hand.redeagle.zhfy.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * NetworkUtils
 * @author huanglaiyun
 * @date 2017年3月27日
 */
public class NetworkUtils {

	public static final int NETWORN_NONE = 0;
	public static final int NETWORN_WIFI = 1;
	public static final int NETWORN_MOBILE = 2;

	public static int getNetworkState(Context context)
	{
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		//Wifi
		State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if(state == State.CONNECTED||state == State.CONNECTING){
			return NETWORN_WIFI;
		}

		//3G
		state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		if(state == State.CONNECTED||state == State.CONNECTING){
			return NETWORN_MOBILE;
		}
		return NETWORN_NONE;
	}

	public static boolean checkConnection(Context mContext){
		ConnectivityManager manager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		manager.getActiveNetworkInfo();
		State wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		State mobileState = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		if (wifiState != null && mobileState != null
				&& State.CONNECTED != wifiState
				&& State.CONNECTED == mobileState) {
			return true;
		} else if (wifiState != null && mobileState != null
				&& State.CONNECTED != wifiState
				&& State.CONNECTED != mobileState) {
			return false;
		} else if (wifiState != null && State.CONNECTED == wifiState) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前手机设备是否已经联网
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {  
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {  
				return mNetworkInfo.isAvailable();  
			}  
		}  
		return false;  
	}


//	private void initGPS() {
//		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//		// 判断GPS模块是否开启，如果没有则开启
//		if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
//			final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//			dialog.setTitle("请打开GPS连接");
//			dialog.setMessage("为方便司机更容易接到您，请先打开GPS");
//			dialog.setPositiveButton("设置", new android.content.DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface arg0, int arg1) {
//					// 转到手机设置界面，用户设置GPS
//					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//					Toast.makeText(MainActivity.this, "打开后直接点击返回键即可，若不打开返回下次将再次出现", Toast.LENGTH_SHORT).show();
//					startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
//				}
//			});
//			dialog.setNeutralButton("取消", new android.content.DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface arg0, int arg1) {
//					arg0.dismiss();
//				}
//			});
//			dialog.show();
//		} else {
//			searchRouteResult(startPoint, endPoint);//路径规划
//			// 弹出Toast
////          Toast.makeText(TrainDetailsActivity.this, "GPS is ready",Toast.LENGTH_LONG).show();
////          // 弹出对话框
////          new AlertDialog.Builder(this).setMessage("GPS is ready").setPositiveButton("OK", null).show();
//		}
//	}











}