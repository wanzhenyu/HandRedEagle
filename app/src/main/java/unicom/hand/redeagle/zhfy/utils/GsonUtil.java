package unicom.hand.redeagle.zhfy.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class GsonUtil {
	
	private static Gson gson = new Gson();
	
	public static Gson getGson(){
		return gson;
	}
	
	/**
	 * json转换为bean
	 * @param json
	 * @param c
	 * @return
	 * 
	 */
	public static Object getObject(String json, Class c){
		return gson.fromJson(json, c);
	}
	
	/**
	 * json转换为map
	 * @param json
	 * @return
	 */
	public static Map getMap(String json){
		return gson.fromJson(json, Map.class);
	}
	
	/**
	 * json转换为list
	 * @param json
	 * @return
	 */
	public static List getList(String json){
		return gson.fromJson(json, List.class);
	}
	
	/**
	 * bean/map/list转换为json
	 * @param
	 * @return
	 */
	public static String getJson(Object src, Type typeOfSrc){
		return gson.toJson(src, typeOfSrc);
	}
	
	public static String getJson(Object src){
		return gson.toJson(src);
	}
}
