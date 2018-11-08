package unicom.hand.redeagle.zhfy;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class PreferenceProvider {
	private Context context;

	public PreferenceProvider(Context context) {
		this.context = context;
	}


	public void setjson(String uid) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putString("json", uid);
		editor.commit();
	}

	public String getjson() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getString("json", "");
	}
	public void setCollectjson(String uid) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putString("colletjson", uid);
		editor.commit();
	}

	public String getCollectjson() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getString("colletjson", "");
	}



	public void setMeetid(String meetid) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putString("meetid", meetid);
		editor.commit();
	}

	public String getMeetid() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getString("meetid", "");
	}


	public void setMeetPwd(String meetpwd) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putString("meetpwd", meetpwd);
		editor.commit();
	}

	public String getMeetPwd() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getString("meetpwd", "");
	}








	public void setUid(String uid) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putString("uid", uid);
		editor.commit();
	}

	public String getUid() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getString("uid", "0");
	}


	public void setPassWord(String PassWord) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putString("PassWord", PassWord);
		editor.commit();
	}

	public String getPassWord() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getString("PassWord", "");
	}

	public void setUsername(String username) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putString("username", username);
		editor.commit();
	}

	public String getUsername() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getString("username", "");
	}

	public void setUserId(String userid) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putString("userid", userid);
		editor.commit();
	}

	public String getUserId() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getString("userid", "");
	}

	public void setCityname(String cityname) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putString("cityname", cityname);
		editor.commit();
	}

	public String getCityname() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getString("cityname", "");
	}
	public void setCityCode(String citycode) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putString("citycode", citycode);
		editor.commit();
	}

	public String getCityCode() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getString("citycode", "");
	}





	public void setXzCityName(String xzcityname) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putString("xzcityname", xzcityname);
		editor.commit();
	}

	public String getXzCityName() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getString("xzcityname", "");
	}


	public void setFirst(String first01) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putString("first02", first01);
		editor.commit();
	}

	public String getFirst() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getString("first02", "");
	}





	public void setIp(String homename) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putString("ip1", homename);
		editor.commit();
	}

	public String getIp() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getString("ip1", "pds10.com");
	}
	public void setIp2(String homename) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putString("ip2", homename);
		editor.commit();
	}

	public String getIp2() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getString("ip2", "202.110.98.2");
//		return spf.getString("ip2", "222.136.225.139");
	}
	public void setStatus(boolean haslogin) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putBoolean("haslogin3", haslogin);
		editor.commit();
	}

	public boolean getStatus() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getBoolean("haslogin3", false);
	}

	public void set3gnet(boolean haslogin) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putBoolean("3gnet", haslogin);
		editor.commit();
	}

	public boolean get3gnet() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getBoolean("3gnet", true);
	}

	public void setLong(String md5, Long catelong) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putLong(md5, catelong);
		editor.commit();
	}

	public Long getLong(String md5, long defaultlong) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getLong(md5, defaultlong);
	}

	public void setIscheckBox(boolean b) {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = spf.edit();
		editor.putBoolean("IscheckBox", b);
		editor.commit();
	}

	public boolean getIscheckBox() {
		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(context);
		return spf.getBoolean("IscheckBox", false);
	}

}
