package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.picker.DateTimePicker;
import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.bean.ConferenceBean;
import unicom.hand.redeagle.zhfy.bean.EditMeetBean;
import unicom.hand.redeagle.zhfy.bean.HyrcBeanMd5;
import unicom.hand.redeagle.zhfy.bean.HyrcBeanMd51;
import unicom.hand.redeagle.zhfy.bean.MeetDetailListBean;
import unicom.hand.redeagle.zhfy.bean.MyNodeBean;
import unicom.hand.redeagle.zhfy.bean.OneMeetDetail;
import unicom.hand.redeagle.zhfy.bean.OneMeetDetailBean;
import unicom.hand.redeagle.zhfy.bean.ParticipantBean;
import unicom.hand.redeagle.zhfy.bean.PeopleBean;
import unicom.hand.redeagle.zhfy.bean.SerializableMap;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.GsonUtils;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

public class LookMeetingMd5Activity extends Activity  {

    private HyrcBeanMd51 conferenceBean;
    private TagFlowLayout mFlowLayout;
//    private TagFlowLayout fl_yc;
//    private TagFlowLayout fl_qx;
    private LayoutInflater inflate;
    private TextView tv_qx;
    private TextView tv_ycch;
    private TextView tv_xcch;
    private TextView tv_join_member;
    private TextView tv_xcch_member;
    private TextView tv_ycch_member;
    private TextView tv_qx_member;
    private String isEditAble = "0";
    private EditText tv_name;
    private EditText tv_theme;
    private EditText tv_jb_loc;
    private EditText tv_jb_lecture;
    private EditText tv_jb_presenter;
    private EditText tv_jb_recoder;
    private DateTimePicker picker;
    private String time = "";
    private TextView tv_jb_time;
    private Date datetime;
    private TextView tv_bj;
    private StringBuilder sb;
    private SimpleDateFormat simpleDateFormat;
    private EditText et_summary;
    private String conferencePlanId = "";
    private SimpleDateFormat yearFormat;
    private SimpleDateFormat timeFormat;
    //    private int type = 1;
//    private TextView tv_summarize;
//    private VideoView videoView;
//    private ProgressBar progressBar;
//    private int intPositionWhenPause = -1;
//    private ImageView iv_play;
    private String startDate = "";
    private String starttime = "";

    private String endDate = "";
    private String endtime = "";
    private TextView tv_end_time;
    private DateTimePicker endpicker;
    private List<PeopleBean> participantsList;
    private String type;
    private String emailRemark = "";
    private LinearLayout ll_presenter;
    private LinearLayout ll_lecture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_meeting_md5_edit);
        participantsList = new ArrayList<>();
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        iv_play = (ImageView)findViewById(R.id.iv_play);
        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("编辑会议");

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        yearFormat = new SimpleDateFormat("yyyy-MM-dd");
        timeFormat = new SimpleDateFormat("HH:mm:ss");




        conferenceBean = (HyrcBeanMd51)getIntent().getSerializableExtra("item");
//        TextView tv_title = (TextView)findViewById(R.id.tv_title);
//        TextView tv_pub_time = (TextView)findViewById(R.id.tv_pub_time);
//        tv_summarize = (TextView)findViewById(R.id.tv_summarize);

        tv_join_member = (TextView)findViewById(R.id.tv_join_member);
        tv_xcch = (TextView)findViewById(R.id.tv_xcch);
        tv_ycch = (TextView)findViewById(R.id.tv_ycch);
        tv_qx = (TextView)findViewById(R.id.tv_qx);
        tv_end_time = (TextView)findViewById(R.id.tv_end_time);

        tv_xcch_member = (TextView)findViewById(R.id.tv_xcch_member);
        tv_ycch_member = (TextView)findViewById(R.id.tv_ycch_member);
        tv_qx_member = (TextView)findViewById(R.id.tv_qx_member);
        ll_presenter = (LinearLayout)findViewById(R.id.ll_presenter);
        ll_lecture = (LinearLayout)findViewById(R.id.ll_lecture);
//        type = conferenceBean.getType();
        type = getIntent().getStringExtra("type");
        if(TextUtils.equals(type,"4")){//党课显示授课人，隐藏主持人，其他则相反
            ll_lecture.setVisibility(View.VISIBLE);
            ll_presenter.setVisibility(View.GONE);
        }else{
            ll_lecture.setVisibility(View.GONE);
            ll_presenter.setVisibility(View.VISIBLE);
        }
        tv_name = (EditText)findViewById(R.id.tv_name);
        tv_theme = (EditText)findViewById(R.id.tv_theme);
        tv_jb_time = (TextView)findViewById(R.id.tv_jb_time);
        tv_jb_loc = (EditText)findViewById(R.id.tv_jb_loc);
        tv_jb_lecture = (EditText)findViewById(R.id.tv_jb_lecture);
        tv_jb_presenter = (EditText)findViewById(R.id.tv_jb_presenter);
        tv_jb_recoder = (EditText)findViewById(R.id.tv_jb_recoder);
        inflate = LayoutInflater.from(LookMeetingMd5Activity.this);
        et_summary = (EditText)findViewById(R.id.et_summary);
        sb = new StringBuilder();
        tv_bj = (TextView)findViewById(R.id.tv_bj);
        tv_bj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.equals(isEditAble,"0")){
                    isEditAble = "1";
                    tv_bj.setText("完成");
                    tv_name.setEnabled(true);
                    tv_theme.setEnabled(true);
                    tv_jb_loc.setEnabled(true);
                    tv_jb_lecture.setEnabled(true);
                    tv_jb_presenter.setEnabled(true);
                    tv_jb_recoder.setEnabled(true);
                    et_summary.setEnabled(true);
                    tv_end_time.setEnabled(true);
                }else{
                    submitData();
                    isEditAble = "0";
                    tv_bj.setText("编辑");
                    tv_name.setEnabled(false);
                    tv_theme.setEnabled(false);
                    tv_jb_loc.setEnabled(false);
                    tv_jb_lecture.setEnabled(false);
                    tv_jb_presenter.setEnabled(false);
                    tv_jb_recoder.setEnabled(false);
                    et_summary.setEnabled(false);
                    tv_end_time.setEnabled(false);
                }
            }
        });
        datetime = new Date();
        picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
        picker.setDateRangeStart(2018, 1, 1);//日期起点
        picker.setDateRangeEnd(2030, 1,1);//日期终点
        picker.setTimeRangeStart(0, 0);//时间范围起点
        picker.setTimeRangeEnd(23, 59);//时间范围终点

        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                //year:年，month:月，day:日，hour:时，minute:分
                int seconds = datetime.getSeconds();
                time = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+seconds;
                tv_jb_time.setText(time);
                startDate = year+"-"+month+"-"+day;
                starttime = hour+":"+minute+":"+seconds;
            }
        });
        tv_jb_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.equals(isEditAble,"1")){
                    picker.show();
                }

            }
        });
        endpicker = new DateTimePicker(this, DateTimePicker.HOUR_24);
        endpicker.setDateRangeStart(2018, 1, 1);//日期起点
        endpicker.setDateRangeEnd(2030, 1,1);//日期终点
        endpicker.setTimeRangeStart(0, 0);//时间范围起点
        endpicker.setTimeRangeEnd(23, 59);//时间范围终点

        endpicker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                //year:年，month:月，day:日，hour:时，minute:分
                int seconds = datetime.getSeconds();
                String time1 = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+seconds;
                tv_end_time.setText(time1);
                endDate = year+"-"+month+"-"+day;
                endtime = hour+":"+minute+":"+seconds;
            }
        });
        tv_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.equals(isEditAble,"1")){
                    endpicker.show();
                }
            }
        });

        tv_xcch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LookMeetingMd5Activity.this, SelectMemberJoinActivity.class);

                startActivityForResult(intent,2);
            }
        });

        getOneMeetDetail();

        mFlowLayout = (TagFlowLayout)findViewById(R.id.id_flowlayout);
//
//        fl_yc = (TagFlowLayout)findViewById(R.id.fl_yc);
//        fl_qx = (TagFlowLayout)findViewById(R.id.fl_qx);

        if(conferenceBean != null){
            conferencePlanId = conferenceBean.getConferencePlanId();

            String title = conferenceBean.getSubject();
            tv_name.setText(UIUtils.getHyrcTtitle(title));
//            String theme = conferenceBean.getTheme();
            tv_theme.setText(UIUtils.getHyrcTtitle(title));

//            String gmtCreate = conferenceBean.getGmtStart();
//            time  =gmtCreate;
//            tv_pub_time.setText(gmtCreate);
            String start = conferenceBean.getStartTime();
            String end = conferenceBean.getExpiryTime();
            String format = simpleDateFormat.format(new Date(Long.parseLong(start)));
            tv_jb_time.setText(format);
            time = format;
            starttime = timeFormat.format(new Date(Long.parseLong(start)));
            startDate = yearFormat.format(new Date(Long.parseLong(start)));
            String format1 = simpleDateFormat.format(new Date(Long.parseLong(end)));
            tv_end_time.setText(format1);
            endtime = timeFormat.format(new Date(Long.parseLong(end)));
            endDate = yearFormat.format(new Date(Long.parseLong(end)));
//            String site = conferenceBean.getSite();
//            tv_jb_loc.setText(site);
//
//            String summarize = conferenceBean.getSummarize();
//            tv_summarize.setText(summarize);


            //缺席人员
//            String[] absentees = conferenceBean.getAbsentees().split(";");
//            tv_qx.setText("缺席("+absentees.length+"人)");
//            tv_qx_member.setText(conferenceBean.getAbsentees());
//            fl_qx.setAdapter(new TagAdapter<String>(absentees)
//            {
//                @Override
//                public View getView(FlowLayout parent, int position, String s)
//                {
//                    TextView tv = (TextView) inflate.inflate(R.layout.item_flowlayout_onlytext,
//                            fl_qx, false);
//                    tv.setText(s);
//                    return tv;
//                }
//            });


            //现场参与人员
//            String[] attendees = conferenceBean.getAttendees().split(";");
//            tv_xcch.setText("现场参会("+attendees.length+"人)");
//            tv_xcch_member.setText(conferenceBean.getAttendees());
//            mFlowLayout.setAdapter(new TagAdapter<String>(attendees)
//            {
//                @Override
//                public View getView(FlowLayout parent, int position, String s)
//                {
//                    TextView tv = (TextView) inflate.inflate(R.layout.item_flowlayout_onlytext,
//                            mFlowLayout, false);
//                    tv.setText(s);
//                    return tv;
//                }
//            });
            //主持人
//            String comperes = conferenceBean.getComperes();
//            tv_jb_presenter.setText(comperes);
//            //授课人
//            String lecturers = conferenceBean.getLecturers();
//            tv_jb_lecture.setText(lecturers);
//            //参与人员
//            String[] people = conferenceBean.getPeople().split(";");
//

//            sb.append(conferenceBean.getPeople());
//            tv_xcch_member.setText(sb.toString());





            //记录人
//            String recorders = conferenceBean.getRecorders();
//            tv_jb_recoder.setText(recorders);
            //远程参与人
//            String[] telecommuters = conferenceBean.getTelecommuters().split(";");
//            tv_ycch.setText("远程参会("+telecommuters.length+"人)");
//            tv_ycch_member.setText(conferenceBean.getTelecommuters());


//            fl_yc.setAdapter(new TagAdapter<String>(telecommuters)
//            {
//                @Override
//                public View getView(FlowLayout parent, int position, String s)
//                {
//                    TextView tv = (TextView) inflate.inflate(R.layout.item_flowlayout_onlytext,
//                            fl_yc, false);
//                    tv.setText(s);
//                    return tv;
//                }
//            });

//            String totalnum= (attendees.length+telecommuters.length+absentees.length)+"";

//            tv_join_member.setText("参会人员:"+totalnum+"人");








        }

    }

    private void getOneMeetDetail() {
        if(conferenceBean != null) {
            String conferenceRecordId = conferenceBean.getConferenceRecordId();
            OneMeetDetail onebean = new OneMeetDetail();
            onebean.setConferenceRecordId(conferenceRecordId);
            String json = GsonUtil.getJson(onebean);
            AppApplication.getDataProvider().getOneMeetDetail(json, new AjaxCallBack<Object>() {
                @Override
                public void onSuccess(Object o) {
                    super.onSuccess(o);
                    Log.e("aaa","编辑会议详情："+o.toString());
                    if(o != null) {
                        try {
                            JSONObject object = new JSONObject(o.toString());
                            int ret = object.getInt("ret");
                            if (ret == 1) {
                                JSONObject data = object.getJSONObject("data");
//                                String json1 = GsonUtil.getJson(data);
                                OneMeetDetailBean bean = GsonUtils.getBean(data.toString(), OneMeetDetailBean.class);
                                Log.e("aaa","编辑会1议详情："+data.toString());
                                setUi(bean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    super.onFailure(t, errorNo, strMsg);
                }
            });

        }

    }

    private void setUi(OneMeetDetailBean bean) {
        String subject = bean.getSubject();
        tv_name.setText(UIUtils.getHyrcTtitle(subject));
//            String theme = conferenceBean.getTheme();
        tv_theme.setText(UIUtils.getHyrcTtitle(subject));

//            String gmtCreate = conferenceBean.getGmtStart();
//            time  =gmtCreate;
//            tv_pub_time.setText(gmtCreate);
        String start = bean.getStartTime();
        String end = bean.getExpiryTime();
        String format = simpleDateFormat.format(new Date(Long.parseLong(start)));
        tv_jb_time.setText(format);
        time = format;
//        timeFormat.format(new Date(Long.parseLong(start)));
        starttime = timeFormat.format(new Date(Long.parseLong(start)));
        startDate = yearFormat.format(new Date(Long.parseLong(start)));
        String format1 = simpleDateFormat.format(new Date(Long.parseLong(end)));
        tv_end_time.setText(format1);
        endtime = timeFormat.format(new Date(Long.parseLong(end)));
        endDate = yearFormat.format(new Date(Long.parseLong(end)));
        emailRemark = bean.getEmailRemark();
        et_summary.setText(emailRemark);
        List<ParticipantBean> participants = bean.getParticipants();
        mFlowLayout.setAdapter(new TagAdapter<ParticipantBean>(participants)
            {
                @Override
                public View getView(FlowLayout parent, int position, ParticipantBean s)
                {
                    TextView tv = (TextView) inflate.inflate(R.layout.item_flowlayout_onlytext,
                            mFlowLayout, false);
                    tv.setText(s.getPhone());
                    return tv;
                }
            });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2){
            if(resultCode == 3){
                SerializableMap serializabmap = (SerializableMap)data.getSerializableExtra("item");
                Map<String, MyNodeBean> map = serializabmap.getMap();

                if(participantsList.size()>0){
                    participantsList.clear();
                }
                sb.delete(0,sb.length());
                Iterator<Map.Entry<String, MyNodeBean>> iterator = map.entrySet().iterator();
                while(iterator.hasNext()){
                    Map.Entry<String, MyNodeBean> next = iterator.next();
                    MyNodeBean value = next.getValue();
                    String name = value.getName();
                    sb.append(name);
                    PeopleBean joinpeople = new PeopleBean();
                    joinpeople.uid = value.getNodeId().substring(0,32);
                    if(value.getType() == 16){
                        joinpeople.type = "thirdparty";
                    }else{
                        joinpeople.type = "normal";
                    }

                    joinpeople.role = "attendee";
                    participantsList.add(joinpeople);
                }

                tv_xcch_member.setText(sb.toString());
            }
        }
    }



    private void submitData() {
        EditMeetBean editmeetbean = new EditMeetBean();
        editmeetbean.setConferencePlanId(conferencePlanId);
        String name = tv_name.getText().toString().trim();
        name = "["+type+"]"+name;
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "请先输入会议名称", Toast.LENGTH_SHORT).show();
            return;
        }
        editmeetbean.setName(name);
        editmeetbean.setConferenceDate(startDate);
        editmeetbean.setConferenceStartTime(starttime);
        editmeetbean.setConferenceEndDate(endDate);
        editmeetbean.setConferenceEndTime(endtime);
        editmeetbean.setTimeZoneName("China_Standard_Time");
        editmeetbean.setUtcOffset(28800);
        editmeetbean.setRecurrenceRange(1);
        editmeetbean.setRecurrenceType("RECURS_ONCE");
        editmeetbean.setConferenceProfile("default");//会议模式
        editmeetbean.setParticipants(participantsList);
        editmeetbean.setDstEnable(1);
        editmeetbean.setDailyType("1");
        editmeetbean.setDaysOfWeeks(new ArrayList<PeopleBean>());
        editmeetbean.setInterval("1");
        editmeetbean.setConferenceType("VC");
        editmeetbean.setConferenceRoomIds(new ArrayList<PeopleBean>());
        editmeetbean.setLocation(new ArrayList<PeopleBean>());
        emailRemark = et_summary.getText().toString().trim();
        editmeetbean.setEmailRemark(emailRemark);

        String theme = tv_theme.getText().toString().trim();
        theme = "["+type+"]"+theme;
        if(TextUtils.isEmpty(theme)){
            Toast.makeText(this, "请先输入会议主题", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(time)){
            Toast.makeText(this, "请先选择会议开始时间", Toast.LENGTH_SHORT).show();
            return;
        }
        String loc = tv_jb_loc.getText().toString().trim();
//        if(TextUtils.isEmpty(loc)){
//            Toast.makeText(this, "请先输入会议举办地点", Toast.LENGTH_SHORT).show();
//            return;
//        }

        String lecture = tv_jb_lecture.getText().toString().trim();
//        if(TextUtils.isEmpty(lecture)){
//            Toast.makeText(this, "请先输入授课人", Toast.LENGTH_SHORT).show();
//            return;
//        }
        String presenter = tv_jb_presenter.getText().toString().trim();

        String recoder = tv_jb_recoder.getText().toString().trim();
        String json = GsonUtils.getJson(editmeetbean);
        Log.e("aaa","传参："+json);
        AppApplication.getDataProvider().editMeetDetail(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","服务器返回："+o.toString());
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1){
                        Toast.makeText(LookMeetingMd5Activity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LookMeetingMd5Activity.this, "操作失败", Toast.LENGTH_SHORT).show();
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

//        AppApplication.getDataProvider().updateMeeting(1+"", "0", name, theme, loc, lecture, presenter, recoder, sb.toString(), "", "", "", "", time, new AjaxCallBack<Object>() {
//
//            @Override
//            public void onSuccess(Object o) {
//                super.onSuccess(o);
//                Log.e("aaa","发布会议返回："+o.toString());
//
//
//                try {
//                    JSONObject object = new JSONObject(o.toString());
//                    String code = object.getString("code");
//                    String msg = object.getString("msg");
//                    if(TextUtils.equals(code,"0")){
//                        finish();
//                    }
//                    Toast.makeText(LookMeetingMd5Activity.this, msg+"", Toast.LENGTH_SHORT).show();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                super.onFailure(t, errorNo, strMsg);
//            }
//        });






    }


//    @Override
//    public void onCompletion(MediaPlayer mediaPlayer) {
//        iv_play.setVisibility(View.VISIBLE);
//        progressBar.setVisibility(View.GONE);
//    }
}
