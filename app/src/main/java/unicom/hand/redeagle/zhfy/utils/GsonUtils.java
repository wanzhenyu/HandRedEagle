package unicom.hand.redeagle.zhfy.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Gson解析工具类
 *
 * @author huanglaiyun
 * @date 2017年3月27日
 */
public class GsonUtils {
    private static Gson gsons = new Gson();

    /**
     * 将json字符串转化成JavaBean对象
     */
    public static <T> T getBean(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * json转换为bean
     *
     * @param json
     * @param c
     * @return
     */
    public static Object getObject(String json, Class c) {
        Gson gson = new Gson();
        return gson.fromJson(json, c);
    }

    /**
     * 将json字符串转化成List<JavaBean>对象
     */
    public static <T> List<T> getBeans(String jsonString, Class<T> cls) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();



        return gson.fromJson(jsonString, new ListOfSomething<T>(cls));
    }


    public static <T> List<T> getBeansArray(String jsonString, final Class<T> cls) {
        ArrayList<T> listbeans = new ArrayList<>();

        try {
            JsonArray jsonArray = new JsonParser().parse(jsonString).getAsJsonArray();

            //循环遍历数组
            for (JsonElement user : jsonArray) {
                T userBean = new Gson().fromJson(user, new TypeToken<T>() {
                }.getType());
                listbeans.add(userBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listbeans;

    }

    public static String getJson(Object src){
        return gsons.toJson(src);
    }


    /**
     * 将json字符串转化成List<String>对象
     */
    public static List<String> getList(String jsonString) {
        List<String> list = new ArrayList<String>();
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
            list = gson.fromJson(jsonString, new TypeToken<List<String>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 将json字符串转化成List<Map<String,Object>>对象
     */
    public static List<Map<String, Object>> listKeyMap(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
            list = gson.fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 将JavaBean转化成json字符串
     */
    public static String toJson(Object obj) {
        String json = null;
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
            json = gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    static class ListOfSomething<X> implements ParameterizedType {

        private Class<?> wrapped;

        public ListOfSomething(Class<X> wrapped) {
            this.wrapped = wrapped;
        }

        public Type[] getActualTypeArguments() {
            return new Type[]{wrapped};
        }

        public Type getRawType() {
            return List.class;
        }

        public Type getOwnerType() {
            return null;
        }
    }
}