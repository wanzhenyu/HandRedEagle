package unicom.hand.redeagle.zhfy;

import android.os.Environment;

import unicom.hand.redeagle.zhfy.utils.UIUtils;

/**
 * Created by wzy on 17/11/7.
 */

public class Common {
    public static final String APP_ID = "wx1a49d58c97bfcfa4";
    public static final String SDCARD_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath();
    public static final String ROOT_DIR = SDCARD_PATH + "/"+ UIUtils.getPackageName(UIUtils.getContext())+"/";
    public static final String DB_NAME = ROOT_DIR + "zshy2018.sqlite";

    public static final String BASE_URL = "http://42.236.68.190:8090/";
    public static final String VERSION = "1.0";
//    public static final String BASE_MD5URL = "http://222.136.225.139:1889";
    public static final String BASE_MD5URL = "http://202.110.98.2:1889";

    public static final String UPLOADFILE = BASE_MD5URL+"/pds/phone/conference/upfileImage";
}
