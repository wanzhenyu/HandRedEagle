package unicom.hand.redeagle.zhfy.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * GSON解析Date设置
 * @author huanglaiyun
 * @date 2017年3月27日
 */
public class DateDeserializer implements JsonDeserializer<Date> {
	@Override
	public Date deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		return new Date(Long.valueOf(arg0.getAsJsonPrimitive().getAsString()));
	}
}  