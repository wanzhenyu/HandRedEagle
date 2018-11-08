package unicom.hand.redeagle.zhfy;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.yealink.sdk.IncomingListener;
import com.yealink.sdk.YealinkApi;

import unicom.hand.redeagle.view.network.NetworkImageCache;

/**
 * Created by wzy on 17/11/7.
 */

public class AppApplication extends Application {
    private static ImageLoader sImageLoader = null;
    private final NetworkImageCache imageCacheMap = new NetworkImageCache();
    public static PreferenceProvider preferenceProvider;
    public static boolean isLogion = false;
    public static int lastId =0;
    private static Context context;
    private static DataProvider dataProvider;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
//		  CrashHandler.getInstance().init(this);
        preferenceProvider = new PreferenceProvider(getApplicationContext());
        dataProvider = new DataProvider(this);
        sImageLoader = new ImageLoader(
                Volley.newRequestQueue(getApplicationContext()), imageCacheMap);
        context = getApplicationContext();
        try {
            YealinkApi.instance().init(this,"/zshy","DwcAlybpbb4cp1yxx409JoEP03nNkD/cW6fi/3QiemXXcxPKmuTYxKLILqvZnQtf8al/FOMlNU9z5/o7fcHkfQ==");
//            YealinkApi.instance().setExtInterface(new ExternalInterface() {
//                public void onTalkEvent(int type, Bundle data) {
//                    switch (type) {
//                        case ExternalInterface.EVENT_INCOMING:
//                            Log.e("aaa","来电话了");
//                            //做自己的事
//                            YealinkApi.instance().incoming(getApplicationContext());
//                            break;
//                    }
//                }
//            });
            YealinkApi.instance().addIncomingListener(new IncomingListener() {
                @Override
                public void onIncoming() {
                    //打开通话界面
                    YealinkApi.instance().incoming(getApplicationContext());
                }
            });
//            YealinkApi.instance().setIncomingListener(new IncomingListener() {
//                @Override
//                public void onIncoming() {
//                    //打开通话界面
//                    YealinkApi.instance().incoming(getApplicationContext());
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static  DataProvider getDataProvider(){
        return dataProvider;
    }
    public static ImageLoader getImageLoader() {
        return sImageLoader;
    }

    public static boolean hasPermission(Context context, String permission){
        int perm = context.checkCallingOrSelfPermission(permission);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
    public  static Context getContext(){
        return context;
    }
}
