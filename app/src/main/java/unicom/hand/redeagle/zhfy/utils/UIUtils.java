package unicom.hand.redeagle.zhfy.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import unicom.hand.redeagle.zhfy.AppApplication;


public class UIUtils {

	public static Context getContext() {
		return AppApplication.getContext();
	}

//	public static Handler getHandler() {
//		return GooglePlayApplication.getHandler();
//	}
//
//	public static int getMainThreadId() {
//		return GooglePlayApplication.getMainThreadId();
//	}

	// /////////////////加载资源文件 ///////////////////////////

	// 获取字符串
	public static String getString(int id) {
		return getContext().getResources().getString(id);
	}

	// 获取字符串数组
	public static String[] getStringArray(int id) {
		return getContext().getResources().getStringArray(id);
	}

	// 获取图片
	public static Drawable getDrawable(int id) {
		return getContext().getResources().getDrawable(id);
	}

	// 获取颜色
	public static int getColor(int id) {
		return getContext().getResources().getColor(id);
	}
	
	//根据id获取颜色的状态选择器
	public static ColorStateList getColorStateList(int id) {
		return getContext().getResources().getColorStateList(id);
	}

	// 获取尺寸
	public static int getDimen(int id) {
		return getContext().getResources().getDimensionPixelSize(id);// 返回具体像素值
	}

	// /////////////////dip和px转换//////////////////////////

	public static int dip2px(float dip) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * density + 0.5f);
	}

	public static float px2dip(int px) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return px / density;
	}

	// /////////////////加载布局文件//////////////////////////
	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}

public static String getRealText(String str){
	String text;
	if(!TextUtils.isEmpty(str) && !TextUtils.equals(str,null) && !TextUtils.equals(str,"null")){
		text = str;
	}else{
		text= "暂无";
	}

	return text;
}
	public static String getRealText1(String str){
		String text;
		if(!TextUtils.isEmpty(str) && !TextUtils.equals(str,null) && !TextUtils.equals(str,"null")){
			text = str;
		}else{
			text= "";
		}

		return text;
	}
	public static String getHyrcTtitle(String str){
		str = str.replace("[0]","");
		str = str.replace("[1]","");
		str = str.replace("[2]","");
		str = str.replace("[3]","");
		str = str.replace("[4]","");
		str = str.replace("进行中\n","");
		return str;
	}

	public static String getLayoutMode(String str){
		if(TextUtils.equals(str,"Average")){

			return "等分模式";
		}else if(TextUtils.equals(str,"1+N")){

			return "1+N模式";
		}else if(TextUtils.equals(str,"2+N")){

			return "2+N模式";
		}else if(TextUtils.equals(str,"Oneself")){

			return "单方全屏";
		}
		return str;
	}





	public static boolean getListByTitle(String type,String str){
		Log.e("eee",type+",标题是："+str);
		if(str.startsWith("["+type+"]") || str.startsWith("进行中\n"+"["+type+"]")){
			return true;
		}
		return false;
	}
	public static boolean getListBySphyTitle(String type,String str){
//		Log.e("eee",type+",标题是："+str);
		if(str.startsWith("["+type+"]") || str.startsWith("进行中\n"+"["+type+"]")){
			return true;
		}
		if(!str.startsWith("["+1+"]") && !str.startsWith("进行中\n"+"["+1+"]") && !str.startsWith("["+2+"]") && !str.startsWith("进行中\n"+"["+2+"]") && !str.startsWith("["+3+"]") && !str.startsWith("进行中\n"+"["+3+"]") && !str.startsWith("["+4+"]") && !str.startsWith("进行中\n"+"["+4+"]")){
			return true;
		}
		return false;
	}
	public static boolean removeContactVirtual(String str){
		if(str.startsWith("phone.book")){
			return true;
		}
		return false;
	}
	/**
	 * 轮询类型
	 * @param str
	 * @return
	 */
	public static String getLunMode(String str){
		if(TextUtils.equals(str,"Specified")){

			return "单张视图轮询";
		}else if(TextUtils.equals(str,"All")){

			return "全屏轮询";
		}
		return str;
	}
	public static String getBannerPos(String str){
		if(TextUtils.equals(str,"Top")){

			return "置顶";
		}else if(TextUtils.equals(str,"Middle")){

			return "居中";
		}else if(TextUtils.equals(str,"Bottom")){

			return "置底";
		}
		return str;
	}

	public static String getSubTitleType(String str){
		if(TextUtils.equals(str,"Static")){

			return "静态字幕";
		}else if(TextUtils.equals(str,"Dynamic")){

			return "动态字幕";
		}
		return str;
	}
	public static String getPackageName(Context context){
		String packageName = context.getPackageName();
		return packageName;
	}
}
