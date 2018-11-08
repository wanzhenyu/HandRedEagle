package unicom.hand.redeagle.zhfy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import cn.qqtheme.framework.picker.DateTimePicker;
import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.adapter.HyLcAdapter;
import unicom.hand.redeagle.zhfy.bean.BookMeeting;
import unicom.hand.redeagle.zhfy.bean.ConferenceBean;
import unicom.hand.redeagle.zhfy.bean.MyNodeBean;
import unicom.hand.redeagle.zhfy.bean.PeopleBean;
import unicom.hand.redeagle.zhfy.bean.ProcedureInfos;
import unicom.hand.redeagle.zhfy.bean.QueryLayoutBean;
import unicom.hand.redeagle.zhfy.bean.SendHylc;
import unicom.hand.redeagle.zhfy.bean.SerializableMap;
import unicom.hand.redeagle.zhfy.bean.layoutUser;
import unicom.hand.redeagle.zhfy.ui.HylcActivity;
import unicom.hand.redeagle.zhfy.ui.SelectMemberJoinActivity;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.GsonUtils;
import unicom.hand.redeagle.zhfy.utils.Md5Utils;

public class PublishMeetingActivity1 extends Activity {

    private TextView tv_submit;
    private TextView tv_time;
    private DateTimePicker picker;
    private String time = "";
    private String startDate = "";

    private String endtime = "";
    private String endDate = "";
    private EditText et_name;
    private EditText et_theme;
    private EditText et_loc;
//    private EditText et_lecture;
    private RelativeLayout rl_joinmemeber;
    private TagFlowLayout fl_qx;
    private LayoutInflater inflate;
    private String strs[];
    private String type = "1";
    private EditText et_presenter;
    private EditText et_recoder;
    private StringBuilder sb;
    private Date datetime;
    private TextView tv_endtime;
    private RelativeLayout rl_presenter;
    private TextView tv_presenter;
    private RelativeLayout rl_recoder;
    private TextView tv_recoder;
    private List<PeopleBean> participants;
    private AlertDialog alertDialog;
    private int mTargetScene = SendMessageToWX.Req.WXSceneSession;
    private IWXAPI api;
    String sharestr = "";

    private String procedure1[] = {"会前准备","确定议题","发出通知","会议议程", "统计人数","宣布开会","开展讨论","进行表决"};
    private String procedure23[] = {"会前准备","确定议题","发出通知","会议议程","统计人数","宣布开会","开展讨论","形成决议"};
    private String procedure4[] ={"课前准备","确定课题","发出通知","课中工作","统计人数","开始授课","专题讨论","党课小结","课后工作"};
    private String title = "支部党员大会";
    private List<MyNodeBean> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_meeting1);
        fl_qx = (TagFlowLayout)findViewById(R.id.fl_qx);
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        api = WXAPIFactory.createWXAPI(this,Common.APP_ID);
        api.registerApp(Common.APP_ID);
        userList = new ArrayList<>();
        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        tv_submit = (TextView)findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitData();

            }
        });
        SimpleDateFormat hoursimpledate = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat yearsimpledate = new SimpleDateFormat("yyyy-MM-dd");
        datetime = new Date();
        participants = new ArrayList<>();
        inflate = LayoutInflater.from(PublishMeetingActivity1.this);
        //24小时值
        picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
        picker.setDateRangeStart(2018, 1, 1);//日期起点
        picker.setDateRangeEnd(2030, 1,1);//日期终点
        picker.setTimeRangeStart(0, 0);//时间范围起点
        picker.setTimeRangeEnd(23, 59);//时间范围终点

        final Calendar Cal=java.util.Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));

        Cal.setTime(datetime);

        Cal.add(Calendar.MINUTE,6);

        Date times = Cal.getTime();
        int year1 = Cal.get(Calendar.YEAR);
        int month1 = Cal.get(Calendar.MONTH)+1;
        int day1 = Cal.get(Calendar.DAY_OF_MONTH);
        int hour1 = Cal.get(Calendar.HOUR_OF_DAY);
        int minute1 = Cal.get(Calendar.MINUTE);
        int mill = Cal.get(Calendar.SECOND);



//        Log.e("aaa","設置時間："+year1+"-"+month1+"-"+day1+" "+hour1+":"+minute1);
        picker.setSelectedItem(year1,month1,day1,hour1,minute1);
        tv_time = (TextView)findViewById(R.id.tv_time);
        String format = simpledate.format(times);
        time = hoursimpledate.format(times);
        startDate = yearsimpledate.format(times);
        tv_time.setText(format);
        tv_endtime = (TextView)findViewById(R.id.tv_endtime);
        LinearLayout rl_time = (LinearLayout)findViewById(R.id.rl_time);
        rl_joinmemeber = (RelativeLayout)findViewById(R.id.rl_joinmemeber);
        LinearLayout rl_endtime = (LinearLayout)findViewById(R.id.rl_endtime);

        rl_presenter = (RelativeLayout)findViewById(R.id.rl_presenter);
        tv_presenter = (TextView)findViewById(R.id.tv_presenter);
        rl_recoder = (RelativeLayout)findViewById(R.id.rl_recoder);
        tv_recoder = (TextView)findViewById(R.id.tv_recoder);



        rl_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                    @Override
                    public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                        //year:年，month:月，day:日，hour:时，minute:分
                        startDate = year+"-"+month+"-"+day;
                         Calendar Cal1=java.util.Calendar.getInstance();
                        Date datetime1 = new Date();
                        Cal1.setTime(datetime1);
                        String second = Cal1.get(Calendar.SECOND)+"";
                        if(second.length()<2){
                            second="0"+second;
                        }
                        PublishMeetingActivity1.this.time = hour+":"+minute+":"+second;
//                        +":"+datetime.getSeconds()
                        tv_time.setText(startDate+" "+ PublishMeetingActivity1.this.time);
                    }
                });
                picker.show();
            }
        });
        rl_endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                    @Override
                    public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                        //year:年，month:月，day:日，hour:时，minute:分
                        endDate = year+"-"+month+"-"+day;
                        Calendar Cal1=java.util.Calendar.getInstance();
                        Date datetime1 = new Date();
                        Cal1.setTime(datetime1);
                        String second = Cal1.get(Calendar.SECOND)+"";
                        if(second.length()<2){
                            second="0"+second;
                        }
                        endtime = hour+":"+minute+":"+second;
                        //+":"+datetime.getSeconds()
                        tv_endtime.setText(endDate+" "+endtime);
                    }
                });
                picker.show();
            }
        });

//        showDialog();
        et_name = (EditText)findViewById(R.id.et_name);
        et_name.setEnabled(false);
        String uid = AppApplication.preferenceProvider.getUsername();
        et_name.setText(uid+title);
        et_theme = (EditText)findViewById(R.id.et_theme);
        et_loc = (EditText)findViewById(R.id.et_loc);
        et_loc.setText(uid+"会议室");
//        et_lecture = (EditText)findViewById(R.id.et_lecture);

        et_presenter = (EditText)findViewById(R.id.et_presenter);

        et_recoder = (EditText)findViewById(R.id.et_recoder);

        //selectnum == 1只能选一个人，selectnum  == 0不限制选择人数
        rl_joinmemeber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PublishMeetingActivity1.this, SelectMemberJoinActivity.class);
                intent.putExtra("selectnum","0");
                intent.putExtra("selectitem",(Serializable) userList);
                startActivityForResult(intent,2);

            }
        });
        rl_presenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PublishMeetingActivity1.this, SelectMemberJoinActivity.class);
                intent.putExtra("selectnum","1");
                startActivityForResult(intent,3);
            }
        });
        rl_recoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PublishMeetingActivity1.this, SelectMemberJoinActivity.class);
                intent.putExtra("selectnum","1");
                startActivityForResult(intent,4);
            }
        });


    }
    private void sendHylc(String intentconfId,String conferenceProcessId,String intentconfEntity,List<ProcedureInfos> procedurebeans,boolean enable2) {
        SendHylc sendhylc = new SendHylc();
        sendhylc.setConferenceRecordId(intentconfId);
        sendhylc.setConferenceProcessId(conferenceProcessId);
        sendhylc.setConfEntity(intentconfEntity);
        sendhylc.setEnable(enable2);
        sendhylc.setProcedureInfos(procedurebeans);
        String json = GsonUtil.getJson(sendhylc);
        AppApplication.getDataProvider().sendHyLc(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","发送会议流程："+o.toString());

                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1) {
//                        Toast.makeText(HylcActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    }else{
//                        Toast.makeText(HylcActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);

            }
        });

    }

    private void getData(final String intentconfEntity, final String intentconfId) {

        QueryLayoutBean querybean = new QueryLayoutBean();
        querybean.setConfEntity(intentconfEntity);
        querybean.setConferenceRecordId(intentconfId);
        String json = GsonUtil.getJson(querybean);
        AppApplication.getDataProvider().getHyLc(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","获取会议流程："+o.toString());
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1) {
                        JSONObject data = object.getJSONObject("data");
                        String conferenceProcessId = data.getString("conferenceProcessId");
                        boolean enable1 = true;
                        List<ProcedureInfos> beans;
                        if(!data.isNull("procedureInfos")){
                            JSONArray procedureInfos = data.getJSONArray("procedureInfos");
                            beans = GsonUtils.getBeans(procedureInfos.toString(), ProcedureInfos.class);


                        }else{
                            beans = new ArrayList<ProcedureInfos>();
                            if(TextUtils.equals(type,"1")){
                                for (int i=0;i<procedure1.length;i++){
                                    ProcedureInfos procedure = new ProcedureInfos();
                                    procedure.setActive(false);
                                    procedure.setDisplayText(procedure1[i]);
                                    procedure.setId(""+(i+1));
                                    beans.add(procedure);
                                }

                            }else if(TextUtils.equals(type,"2") || TextUtils.equals(type,"3")){
                                for (int i=0;i<procedure23.length;i++){
                                    ProcedureInfos procedure = new ProcedureInfos();
                                    procedure.setActive(false);
                                    procedure.setDisplayText(procedure23[i]);
                                    procedure.setId(""+(i+1));
                                    beans.add(procedure);
                                }

                            }else if(TextUtils.equals(type,"4")){
                                for (int i=0;i<procedure4.length;i++){
                                    ProcedureInfos procedure = new ProcedureInfos();
                                    procedure.setActive(false);
                                    procedure.setDisplayText(procedure4[i]);
                                    procedure.setId(""+(i+1));
                                    beans.add(procedure);
                                }

                            }
                        }
                        sendHylc(intentconfId,conferenceProcessId,intentconfEntity,beans,enable1);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }
//    private int presentertype = 0;
//    private int recodertype = 0;
//
//    private String presenteruid = "";
//    private String recoderuid = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int  i =0;

        if(requestCode == 2){
            if(resultCode == 3){
                if(participants != null && participants.size()>0){
                    participants.clear();
                }
                if(userList != null && userList.size()>0){
                    userList.clear();
                }

                SerializableMap serializabmap = (SerializableMap)data.getSerializableExtra("item");
                Map<String, MyNodeBean> map = serializabmap.getMap();
                strs = new String[map.size()];
                Iterator<Map.Entry<String, MyNodeBean>> iterator = map.entrySet().iterator();
                while(iterator.hasNext()){
                    Map.Entry<String, MyNodeBean> next = iterator.next();
                    MyNodeBean value = next.getValue();
                    String name = value.getName();
                    String nodeId = value.getNodeId();
                    String serialNumber = value.getSerialNumber();
                    String uid = value.getUid();
                    Log.e("fff",serialNumber+"的id是："+",默认选中："+uid);
                    MyNodeBean layoutuser = new MyNodeBean();
                    layoutuser.setName(name);
                    layoutuser.setSerialNumber(serialNumber);
                    layoutuser.setNodeId(nodeId);
                    layoutuser.setUid(uid);
                    userList.add(layoutuser);
                    strs[i] = name;
                    i++;
                    PeopleBean joinpeople = new PeopleBean();
                    joinpeople.uid = value.getNodeId().substring(0,32);
                    if(value.getType() == 16){
                        joinpeople.type = "thirdparty";
                    }else{
                        joinpeople.type = "normal";
                    }

                    joinpeople.role = "Attendee";
                    participants.add(joinpeople);
                }
                fl_qx.setAdapter(new TagAdapter<String>(strs)
                {
                    @Override
                    public View getView(FlowLayout parent, int position, String s)
                    {
                        TextView tv = (TextView) inflate.inflate(R.layout.item_flowlayout_onlytext,
                                fl_qx, false);
                        tv.setText(s);
                        return tv;
                    }
                });

            }
        }else if(requestCode == 3){//主持人
            if(resultCode == 3) {
                SerializableMap serializabmap = (SerializableMap) data.getSerializableExtra("item");
                Map<String, MyNodeBean> map = serializabmap.getMap();
                Iterator<Map.Entry<String, MyNodeBean>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, MyNodeBean> next = iterator.next();
                    MyNodeBean value = next.getValue();
                    String name = value.getName();
//                    presentertype = value.getType();
//                    presenteruid = value.getNodeId().substring(0,32);
                    tv_presenter.setText(name);

                    PeopleBean presenterpeople = new PeopleBean();
                    presenterpeople.uid = value.getNodeId().substring(0,32);
                    if(value.getType() == 16){
                        presenterpeople.type = "thirdparty";
                    }else{
                        presenterpeople.type = "normal";
                    }
                    presenterpeople.role = "presenter";
                    participants.add(presenterpeople);



                }
            }

        }else if(requestCode == 4){//记录人
            if(resultCode == 3) {
                SerializableMap serializabmap = (SerializableMap) data.getSerializableExtra("item");
                Map<String, MyNodeBean> map = serializabmap.getMap();
                Iterator<Map.Entry<String, MyNodeBean>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, MyNodeBean> next = iterator.next();
                    MyNodeBean value = next.getValue();
                    String name = value.getName();
//                    recodertype = value.getType();
//                    recoderuid = value.getNodeId().substring(0,32);
                    tv_recoder.setText(name);

                    PeopleBean recoderpeople = new PeopleBean();
                    recoderpeople.uid = value.getNodeId().substring(0,32);
                    if(value.getType() == 16){
                        recoderpeople.type = "thirdparty";
                    }else{
                        recoderpeople.type = "normal";
                    }
                    recoderpeople.role = "Organizer";
                    participants.add(recoderpeople);

                }
            }
        }
    }

    private void submitData() {
        BookMeeting conferenceBean = new BookMeeting();
//        String name = et_name.getText().toString().trim();
//        if(TextUtils.isEmpty(name)){
//            Toast.makeText(this, "请先输入会议名称", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        conferenceBean.setName("["+type+"]"+name);

        String theme = et_theme.getText().toString().trim();
        if(TextUtils.isEmpty(theme)){
            Toast.makeText(this, "请先输入会议主题", Toast.LENGTH_SHORT).show();
            return;
        }
        sharestr = "";
        sharestr+="主题:";
        sharestr+=theme;
        sharestr+="\n";
        conferenceBean.setName("["+type+"]"+theme);
        if(TextUtils.isEmpty(time)){
            Toast.makeText(this, "请先选择会议开始时间", Toast.LENGTH_SHORT).show();
            return;
        }
        sharestr+="时间:";
        sharestr+=startDate;
        sharestr+=" ";
        sharestr+=time;
        sharestr+="\n";
        conferenceBean.setConferenceStartTime(time);
        conferenceBean.setConferenceDate(startDate);
        if(TextUtils.isEmpty(endtime)){
            Toast.makeText(this, "请先选择会议结束时间", Toast.LENGTH_SHORT).show();
            return;
        }
        conferenceBean.setConferenceEndDate(endDate);
        conferenceBean.setConferenceEndTime(endtime);
        final String loc = et_loc.getText().toString().trim();
//        if(TextUtils.isEmpty(loc)){
//            Toast.makeText(this, "请先输入会议举办地点", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        String lecture = et_lecture.getText().toString().trim();
//        if(TextUtils.isEmpty(lecture)){
//            Toast.makeText(this, "请先输入授课人", Toast.LENGTH_SHORT).show();
//            return;
//        }
        sb = new StringBuilder();
        String presenter = tv_presenter.getText().toString().trim();
//        if(TextUtils.isEmpty(presenter)){
//            Toast.makeText(this, "请先选择主持人", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        PeopleBean presenterpeople = new PeopleBean();
//        presenterpeople.uid = presenteruid;
//        if(presentertype == 16){
//            presenterpeople.type = "thirdparty";
//        }else{
//            presenterpeople.type = "normal";
//        }
//        presenterpeople.role = "Presenter";
//
//        participants.add(presenterpeople);
//        String recoder = tv_recoder.getText().toString().trim();
//        if(TextUtils.isEmpty(recoder)){
//            Toast.makeText(this, "请先选择记录人", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        PeopleBean recoderpeople = new PeopleBean();
//        recoderpeople.uid = presenteruid;
//        if(recodertype == 16){
//            recoderpeople.type = "thirdparty";
//        }else{
//            recoderpeople.type = "normal";
//        }
//        recoderpeople.role = "Organizer";
//        participants.add(recoderpeople);
//        if(strs != null){
//            for (int i=0;i<strs.length;i++){
//
//            }
//            Log.e("aaa","参会人员："+sb.toString());
//        }

        conferenceBean.setParticipants(participants);

        conferenceBean.setRecurrenceType("RECURS_ONCE");
        conferenceBean.setConferenceType("VC");
        conferenceBean.setUtcOffset("28800");
        conferenceBean.setEmailRemark("");
        conferenceBean.setLocation(new ArrayList<PeopleBean>());
        conferenceBean.setConferenceRoomIds(new ArrayList<PeopleBean>());
        conferenceBean.setInterval("1");
        conferenceBean.setRecurrenceRange("1");
        conferenceBean.setDaysOfWeeks(new ArrayList<PeopleBean>());
        conferenceBean.setDailyType("1");
        conferenceBean.setTimeZoneName("China_Standard_Time");
        conferenceBean.setPatternStartDate("");
        conferenceBean.setDstEnable(0);
        conferenceBean.setCanSaveRecurrencePattern(false);
        conferenceBean.setAutoInvite(true);
        conferenceBean.setConferenceProfile("default");

        String json = GsonUtil.getJson(conferenceBean);
//        json = "{\"recurrenceType\":\"RECURS_ONCE\",\"conferenceType\":\"VC\",\"conferenceRoomIds\": [],\"location\":[],\"emailRemark\":\"\",\"utcOffset\":\"28800\",\"participants\":[{\"type\": \"normal\",\"uid\": \"93f5e156729d4b50a096686fb84d3f24\",\"role\":\"presenter\"},{\"type\":\"thirdparty\",\"uid\": \"93f5e156729d4b50a096686fb84d3f24\",\"role\":\"attedee\"},{\"type\": \"external\",\"uid\": \"xx@yy.com\",\"role\": \"attedee\"}],\"patternStartDate\": \"\",\"canSaveRecurrencePattern\": false,\"dstEnable\": 0,\"autoInvite\": true,\"conferenceProfile\": \"default\",\"name\": \"2294的视频会议\",\"conferenceDate\": \"2018-07-06\",\"conferenceStartTime\": \"16:00:00\",\"conferenceEndDate\": \"2018-07-26\",\"conferenceEndTime\": \"16:30:00\",\"timeZoneName\": \"China_Standard_Time\",\"dailyType\": \"1\",\"interval\": \"1\",\"recurrenceRange\": \"1\",\"daysOfWeeks\": []}";
//        json = "{\"recurrenceType\":\"RECURS_ONCE\",\"conferenceType\":\"VC\",\"conferenceRoomIds\": [], \"location\": [],\"emailRemark\": \"\",\"utcOffset\": \"28800\",\"participants\": [],\"patternStartDate\": \"\",\"canSaveRecurrencePattern\": false,\"dstEnable\": 0,\"autoInvite\": true,\"conferenceProfile\": \"default\",\"name\":\"[1]万的视频会议\",\"conferenceDate\": \"2018-07-03\",\"conferenceStartTime\": \"16:00:00\",\"conferenceEndDate\": \"2018-07-10\",\"conferenceEndTime\": \"16:30:00\",\"timeZoneName\": \"China_Standard_Time\",\"dailyType\": \"1\",\"interval\": \"1\",\"recurrenceRange\": \"1\",\"daysOfWeeks\": []}\n";
        String s = Md5Utils.md5(json);
        Log.e("aaa","加密后的："+s);
        AppApplication.getDataProvider().publishMeeting1(json, new AjaxCallBack<Object>() {

            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","发布会议返回："+o.toString());
                try {
                    JSONObject object1 = new JSONObject(o.toString());
                    String ret = object1.getString("ret");
                    if(TextUtils.equals(ret,"1")){
                        JSONObject object = object1.getJSONObject("data");
                        JSONObject conferencePlan = object.getJSONObject("conferencePlan");
                        String id = conferencePlan.getString("_id");
                        String conferenceNumber = conferencePlan.getString("conferenceNumber");
                        //会议号码
                        sharestr+="会议号码:";
                        sharestr+=conferenceNumber;
                        sharestr+="\n";

                        String pinCode = conferencePlan.getString("pinCode");
                        //会议号码
                        sharestr+="会议密码:";
                        sharestr+=pinCode;
                        sharestr+="\n";

                        sharestr+="地点:";
                        sharestr+=loc;
                        sharestr+="\n";


                        String conferenceEntity = conferencePlan.getString("conferenceEntity");
                        if(!object.isNull("conferenceRecordIds")){
                            JSONArray conferenceRecordIds = object.getJSONArray("conferenceRecordIds");
                            if(conferenceRecordIds != null && conferenceRecordIds.length()>0){
                                String string = conferenceRecordIds.getString(0);
                                getData(conferenceEntity,string);
                            }
                        }


                        Toast.makeText(PublishMeetingActivity1.this, "预约成功", Toast.LENGTH_SHORT).show();
                       showDialog(sharestr);

                    }else{
                        JSONObject object = object1.getJSONObject("error");
                        String msg = object.getString("msg");
                        Toast.makeText(PublishMeetingActivity1.this, msg+"", Toast.LENGTH_SHORT).show();
                    }
//                    JSONObject object = object1.getJSONObject("error");
//                    String code = object.getString("errorCode");
//                    String msg = object.getString("msg");
//                    if(TextUtils.equals(code,"0")){
//                        finish();
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(PublishMeetingActivity1.this, "发布会议失败", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(PublishMeetingActivity1.this, "发布会议失败", Toast.LENGTH_SHORT).show();
            }
        });






    }

    private void showDialog(final String str) {


        View inflate = View.inflate(PublishMeetingActivity1.this, R.layout.dialog_publish_finish, null);
        ImageView iv_share = (ImageView)inflate.findViewById(R.id.iv_share);
        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alertDialog != null){
                    alertDialog.dismiss();
                }
                wxShare(str);
                sharestr = "";
                finish();
            }
        });
        ImageView iv_back = (ImageView)inflate.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alertDialog != null){
                    alertDialog.dismiss();
                }
                finish();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        // 设置参数
        builder.setView(inflate);
        alertDialog = builder.create();
        alertDialog.show();

    }

    private void wxShare(String str) {
        WXTextObject textObj = new WXTextObject();
        textObj.text = str;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = str;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = mTargetScene;
        api.sendReq(req);
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


}
