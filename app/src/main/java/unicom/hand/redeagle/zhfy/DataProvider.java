package unicom.hand.redeagle.zhfy;

import android.content.Context;
import android.util.Log;

import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.Md5Utils;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lml
 *
 */
public class DataProvider {
	private Context context;
	private final String TAG = "DataProvider";

	public DataProvider(Context context) {
		this.context = context;
	}
	public void getWeather(String code, AjaxCallBack<Object> callback){
		Log.d(TAG, "code=" + code);
		FinalHttp fh = new FinalHttp();
		fh.get(getWeatherAPI(code), callback);
	}

	public void getdata(String url, AjaxCallBack<Object> callback){
		Log.d(TAG, "url=" + url);
		FinalHttp fh = new FinalHttp();
		fh.get(url, callback);
	}
	// ---------------------------------天气
	public static String getWeatherAPI(String code) {
		return "http://api.k780.com:88/?app=weather.today&weaid="+code+"&appkey=15823&sign=e5751ee382910720621aba65e4d47c86&format=json";
	}
	/**
	 * 获取自己的登陆账号 和志愿者账号
	 *
	 * @param
	 * @param callback
	 */
	public void getVolunteerAccount(String code,
									AjaxCallBack<Object> callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("code", code);
		StringEntity entity = null;
		try {
			String data = GsonUtil.getJson(params);
			Log.i(TAG,  data);
			entity = new StringEntity(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		FinalHttp fh = new FinalHttp();
		fh.post("", entity, "application/json", callback);
	}
	/**
	 * 获取在线的志愿者
	 */
	public void getWebwaiter(AjaxCallBack<Object> callback){
		Map<String, String> params = new HashMap<String, String>();
		StringEntity entity = null;
		try {
			String data = GsonUtil.getJson(params);
			Log.i(TAG, "" + data);
			entity = new StringEntity(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		FinalHttp fh = new FinalHttp();
		fh.post("", entity, "application/json", callback);
	}


	/**
	 * 首页新闻
	 * @param id
	 * @param pageNo
	 * @param pageSize
	 * @param callback
	 */
	public void getIndexNews(String id,int pageNo,int pageSize,
									AjaxCallBack<Object> callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("pageNo", pageNo+"");
		params.put("pageSize", pageSize+"");
		StringEntity entity = null;
		try {
			String data = GsonUtil.getJson(params);
			Log.i(TAG,  data);
			entity = new StringEntity(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		FinalHttp fh = new FinalHttp();
		fh.post("http://42.236.68.190:8090/Zshy/servlet/tdydjwServlet", entity, "application/json", callback);
	}

	/**
	 * 首页新闻详情
	 * @param id
	 * @param callback
	 */
	public void getIndexNewsDetail(String id,
							 AjaxCallBack<Object> callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		StringEntity entity = null;
		try {
			String data = GsonUtil.getJson(params);
			Log.i(TAG,  data);
			entity = new StringEntity(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		FinalHttp fh = new FinalHttp();
		fh.post("http://42.236.68.190:8090/Zshy/servlet/djwxxServlet", entity, "application/json", callback);
	}


	/**
	 * 发布会议
	 * @param type type会议类型：1：支部党员大会，2：支部委员会，3：党小组会，4：党课
	 * @param status 召开状态：1已经召开，0未召开
	 * @param title
	 * @param theme
	 * @param site
	 * @param lecturers
	 * @param comperes 主持人
	 * @param recorders 记录人
	 * @param people
	 * @param attendees
	 * @param telecommuters
	 * @param absentees
	 * @param summarize
	 * @param gmtStart
	 * @param callback
	 */
	public void publishMeeting(String type,String status,String title,String theme,
							   String site,String lecturers,String comperes,String recorders,
							   String people,String attendees,String telecommuters,String absentees,String summarize,String gmtStart,
								   AjaxCallBack<Object> callback) {
		Map<String, String> params = new HashMap<String, String>();
//		AjaxParams params = new AjaxParams();
		params.put("type", type);
		params.put("status", status);
		params.put("title", title);
		params.put("theme", theme);
		params.put("site", site);
		params.put("lecturers",lecturers);
		params.put("comperes", comperes);
		params.put("recorders", recorders);
		params.put("people", people);
		params.put("attendees", attendees);
		params.put("telecommuters", telecommuters);
		params.put("absentees", absentees);
		params.put("summarize", summarize);
		params.put("gmtStart", gmtStart);
		StringEntity entity = null;
		try {
			String data = GsonUtil.getJson(params);
			Log.i(TAG,  data);
			entity = new StringEntity(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		FinalHttp fh = new FinalHttp();
//		fh.post("http://42.236.68.190:8010/pds/phone/conference/save",params,callback);
		fh.post("http://42.236.68.190:8010/pds/phone/conference/save", entity, "application/json", callback);
	}
	public void publishMeeting1(String json,
							   AjaxCallBack<Object> callback) {
//		Map<String, String> params = new HashMap<String, String>();
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
//		params.put("comperes", comperes);
//		params.put("recorders", recorders);
//		params.put("people", people);
//		params.put("attendees", attendees);
//		params.put("telecommuters", telecommuters);
//		params.put("absentees", absentees);
//		params.put("summarize", summarize);
//		params.put("gmtStart", gmtStart);
//		StringEntity entity = null;
//		try {
//			String data = GsonUtil.getJson(params);
//			Log.i("aaa",  data);
//			entity = new StringEntity(data, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		FinalHttp fh = new FinalHttp();
		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencePlan/create",params,callback);
//		fh.post("http://42.236.68.190:8010/pds/phone/conference/save",params,callback);
//		fh.post("http://222.136.225.139:1889/api/v1/openinterface/conferencePlan/create", entity, "application/json", callback);
	}
	public void getMeetDetail(String json,
								AjaxCallBack<Object> callback) {
//		Map<String, String> params = new HashMap<String, String>();
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();
		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/summary",params,callback);
	}
	public void getOneMeetDetail(String json,
							  AjaxCallBack<Object> callback) {
//		Map<String, String> params = new HashMap<String, String>();
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		FinalHttp fh = new FinalHttp();
		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conference/queryOne",params,callback);
	}
	/**
	 * 编辑会议
	 * @param json
	 * @param callback
	 */
	public void editMeetDetail(String json,
							  AjaxCallBack<Object> callback) {
//		Map<String, String> params = new HashMap<String, String>();
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		FinalHttp fh = new FinalHttp();
		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencePlan/edit",params,callback);
	}
	public void updateMeeting(String type,String status,String title,String theme,
							   String site,String lecturers,String comperes,String recorders,
							   String people,String attendees,String telecommuters,String absentees,String summarize,String gmtStart,
							   AjaxCallBack<Object> callback) {
		Map<String, String> params = new HashMap<String, String>();
//		AjaxParams params = new AjaxParams();
		params.put("type", type);
		params.put("status", status);
		params.put("title", title);
		params.put("theme", theme);
		params.put("site", site);
		params.put("lecturers",lecturers);
		params.put("comperes", comperes);
		params.put("recorders", recorders);
		params.put("people", people);
		params.put("attendees", attendees);
		params.put("telecommuters", telecommuters);
		params.put("absentees", absentees);
		params.put("summarize", summarize);
		params.put("gmtStart", gmtStart);
		StringEntity entity = null;
		try {
			String data = GsonUtil.getJson(params);
			Log.i(TAG,  data);
			entity = new StringEntity(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		FinalHttp fh = new FinalHttp();
//		fh.post("http://42.236.68.190:8010/pds/phone/conference/save",params,callback);
		fh.post("http://42.236.68.190:8010/pds/phone/conference/update", entity, "application/json", callback);
	}
	public void saveHyLc(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/message/process/save", params, callback);
	}
	/**
	 * 开启或者禁用会议流程
	 * @param json
	 * @param callback
	 */
	public void enableHyLc(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/message/process/enable", params, callback);
	}
	/**
	 * 获取会议流程
	 * @param json
	 * @param callback
	 */
	public void getHyLc(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/message/process/query", params, callback);
	}

	/**
	 * 发送会议流程
	 * @param json
	 * @param callback
	 */
	public void sendHyLc(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/message/process/send", params, callback);
	}
    /**
     * 获取字幕先关设置
     * @param json
     * @param callback
     */
    public void getSubTitle(String json,AjaxCallBack<Object> callback) {
        AjaxParams params = new AjaxParams();
        String timestamp = System.currentTimeMillis()+"";
        params.put("timestamp",timestamp);
        params.put("v", Common.VERSION);

        params.put("userName", Md5Utils.getUserName());
        params.put("password", Md5Utils.getPsd());
        params.put("parameter", json);
        String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
        String sign = Md5Utils.md5(str);
        params.put("sign",sign);
        Log.i("aaa",  "以下是提交的参数"+str);
        Log.i("aaa",  System.currentTimeMillis()+"");
        Log.i("aaa",  Common.VERSION);
        Log.i("aaa",  Md5Utils.getUserName());
        Log.i("aaa",  Md5Utils.getPsd());
        Log.i("aaa",  json);
        Log.i("aaa",  "签名："+sign);
        FinalHttp fh = new FinalHttp();

        fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/message/subtitle/query", params, callback);
    }
	public void saveSubTitle(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/message/subtitle/save", params, callback);
	}
	public void sendSubTitle(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/message/subtitle/send", params, callback);
	}
	/**
	 * 开启或者禁用字幕
	 * @param json
	 * @param callback
	 */
	public void enableSubTitle(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/message/subtitle/enable", params, callback);
	}















    public void enableBanner(String json,AjaxCallBack<Object> callback) {
        AjaxParams params = new AjaxParams();
        String timestamp = System.currentTimeMillis()+"";
        params.put("timestamp",timestamp);
        params.put("v", Common.VERSION);

        params.put("userName", Md5Utils.getUserName());
        params.put("password", Md5Utils.getPsd());
        params.put("parameter", json);
        String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
        String sign = Md5Utils.md5(str);
        params.put("sign",sign);
        Log.i("aaa",  "以下是提交的参数"+str);
        Log.i("aaa",  System.currentTimeMillis()+"");
        Log.i("aaa",  Common.VERSION);
        Log.i("aaa",  Md5Utils.getUserName());
        Log.i("aaa",  Md5Utils.getPsd());
        Log.i("aaa",  json);
        Log.i("aaa",  "签名："+sign);
        FinalHttp fh = new FinalHttp();

        fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/message/banner/enable", params, callback);
    }
	public void sendBanner(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/message/banner/send", params, callback);
	}
    public void saveBanner(String json,AjaxCallBack<Object> callback) {
        AjaxParams params = new AjaxParams();
        String timestamp = System.currentTimeMillis()+"";
        params.put("timestamp",timestamp);
        params.put("v", Common.VERSION);

        params.put("userName", Md5Utils.getUserName());
        params.put("password", Md5Utils.getPsd());
        params.put("parameter", json);
        String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
        String sign = Md5Utils.md5(str);
        params.put("sign",sign);
        Log.i("aaa",  "以下是提交的参数"+str);
        Log.i("aaa",  System.currentTimeMillis()+"");
        Log.i("aaa",  Common.VERSION);
        Log.i("aaa",  Md5Utils.getUserName());
        Log.i("aaa",  Md5Utils.getPsd());
        Log.i("aaa",  json);
        Log.i("aaa",  "签名："+sign);
        FinalHttp fh = new FinalHttp();

        fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/message/banner/save", params, callback);
    }
	public void getBanner(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/message/banner/query", params, callback);
	}
	/**
	 * 解锁会议或者锁定会议
	 * @param json
	 * @param callback
	 */
	public void lock(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/lock", params, callback);
	}

	/**
	 * 结束会议
	 * @param json
	 * @param callback
	 */
	public void finishMeet(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/finish", params, callback);
	}
    /**
     * 闭音或者解锁闭音
     * @param json
     * @param callback
     */
    public void deaf(String json,AjaxCallBack<Object> callback) {
        AjaxParams params = new AjaxParams();
        String timestamp = System.currentTimeMillis()+"";
        params.put("timestamp",timestamp);
        params.put("v", Common.VERSION);

        params.put("userName", Md5Utils.getUserName());
        params.put("password", Md5Utils.getPsd());
        params.put("parameter", json);
        String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
        String sign = Md5Utils.md5(str);
        params.put("sign",sign);
        Log.i("aaa",  "以下是提交的参数"+str);
        Log.i("aaa",  System.currentTimeMillis()+"");
        Log.i("aaa",  Common.VERSION);
        Log.i("aaa",  Md5Utils.getUserName());
        Log.i("aaa",  Md5Utils.getPsd());
        Log.i("aaa",  json);
        Log.i("aaa",  "签名："+sign);
        FinalHttp fh = new FinalHttp();

        fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/deafAll", params, callback);
    }
	/**
	 * 禁言或者解除禁言
	 * @param json
	 * @param callback
	 */
	public void mute(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/muteAll", params, callback);
	}

	/**
	 * 开始录制
	 * @param json
	 * @param callback
	 */
	public void beginRecoder(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/recording/begin", params, callback);
	}


	/**
	 * 暂停录制
	 * @param json
	 * @param callback
	 */
	public void pauseRecoder(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/recording/pause", params, callback);
	}


	/**
	 * 继续录制
	 * @param json
	 * @param callback
	 */
	public void continueRecoder(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/recording/continue", params, callback);
	}


	/**
	 * 结束录制
	 * @param json
	 * @param callback
	 */
	public void finishRecoder(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/recording/finish", params, callback);
	}







	/**
	 * 得到录制当前状态
	 * @param json
	 * @param callback
	 */
	public void getRecoderStatus(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/recording/query", params, callback);
	}

	public void getTempLayout(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/layout/template/list", params, callback);
	}


	public void getPeopleOnLine(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();
		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/attendeetree", params, callback);
	}








	public void saveSimpleLayout(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/layout/simple/save", params, callback);
	}
	public void getLayout(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/layout/simple/query", params, callback);
	}
	public void deleteMeetingList(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
		FinalHttp fh = new FinalHttp();

		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencePlan/delete", params, callback);
	}
    public void getMeetingList1(String json,AjaxCallBack<Object> callback) {
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		Log.i("aaa",  "以下是提交的参数"+str);
		Log.i("aaa",  System.currentTimeMillis()+"");
		Log.i("aaa",  Common.VERSION);
		Log.i("aaa",  Md5Utils.getUserName());
		Log.i("aaa",  Md5Utils.getPsd());
		Log.i("aaa",  json);
		Log.i("aaa",  "签名："+sign);
        FinalHttp fh = new FinalHttp();

        fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conference/query", params, callback);
    }

	public void getMeetingList(String type,AjaxCallBack<Object> callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", type);
		StringEntity entity = null;
		try {
			String data = GsonUtil.getJson(params);
			Log.i(TAG,  data);
			entity = new StringEntity(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		FinalHttp fh = new FinalHttp();

		fh.post("http://42.236.68.190:8010/pds/phone/conference/list", entity, "application/json", callback);
	}

	/**
	 * 首页轮播图
	 * @param callback
	 */
	public void getPicList(AjaxCallBack<Object> callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", "01");
		params.put("pageNo", "1");
		params.put("pageSize", "5");
		StringEntity entity = null;
		try {
			String data = GsonUtil.getJson(params);
			Log.i(TAG,  data);
			entity = new StringEntity(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		FinalHttp fh = new FinalHttp();
		fh.post("http://42.236.68.190:8090/zshy_web/webpicnews.do?action=PicNewList", entity, "application/json", callback);
	}

	public void getVideoUrl(String json,
								 AjaxCallBack<Object> callback) {
//		Map<String, String> params = new HashMap<String, String>();
		AjaxParams params = new AjaxParams();
		String timestamp = System.currentTimeMillis()+"";
		params.put("timestamp",timestamp);
		params.put("v", Common.VERSION);

		params.put("userName", Md5Utils.getUserName());
		params.put("password", Md5Utils.getPsd());
		params.put("parameter", json);
		String str = "Yealinkparameter"+json + "timestamp"+timestamp +"v"+ Common.VERSION+"Yealink";
		String sign = Md5Utils.md5(str);
		params.put("sign",sign);
		FinalHttp fh = new FinalHttp();
		fh.post(Common.BASE_MD5URL+"/api/v1/openinterface/conferencectrl/recording/list",params,callback);
	}


}
