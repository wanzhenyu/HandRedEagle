package unicom.hand.redeagle.zhfy.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wzy on 17/11/8.
 */

public class StringUtils {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
    public static String generator(){
        StringBuffer now = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        int a = (int)(Math.random() * 90000.0D + 10000.0D);
        int b = (int)(Math.random() * 90000.0D + 10000.0D);
        int c = (int)(Math.random() * 90000.0D + 10000.0D);
        return (now.append(a).append(b).append(c)).toString();
    }
}
