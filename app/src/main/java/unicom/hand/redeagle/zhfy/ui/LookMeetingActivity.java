package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
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

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import cn.qqtheme.framework.picker.DateTimePicker;
import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.PublishMeetingActivity;
import unicom.hand.redeagle.zhfy.bean.ConferenceBean;
import unicom.hand.redeagle.zhfy.bean.MyNodeBean;
import unicom.hand.redeagle.zhfy.bean.SerializableMap;

public class LookMeetingActivity extends Activity implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private ConferenceBean conferenceBean;
//    private TagFlowLayout mFlowLayout;
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
    private int type = 1;
    private TextView tv_summarize;
    private VideoView videoView;
    private ProgressBar progressBar;
    private int intPositionWhenPause = -1;
    private ImageView iv_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_meeting);

        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv_play = (ImageView)findViewById(R.id.iv_play);






        conferenceBean = (ConferenceBean)getIntent().getSerializableExtra("item");
        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        TextView tv_pub_time = (TextView)findViewById(R.id.tv_pub_time);
        tv_summarize = (TextView)findViewById(R.id.tv_summarize);

        tv_join_member = (TextView)findViewById(R.id.tv_join_member);
        tv_xcch = (TextView)findViewById(R.id.tv_xcch);
        tv_ycch = (TextView)findViewById(R.id.tv_ycch);
        tv_qx = (TextView)findViewById(R.id.tv_qx);

        tv_xcch_member = (TextView)findViewById(R.id.tv_xcch_member);
        tv_ycch_member = (TextView)findViewById(R.id.tv_ycch_member);
        tv_qx_member = (TextView)findViewById(R.id.tv_qx_member);
        type = conferenceBean.getType();

        tv_name = (EditText)findViewById(R.id.tv_name);
        tv_theme = (EditText)findViewById(R.id.tv_theme);
        tv_jb_time = (TextView)findViewById(R.id.tv_jb_time);
        tv_jb_loc = (EditText)findViewById(R.id.tv_jb_loc);
        tv_jb_lecture = (EditText)findViewById(R.id.tv_jb_lecture);
        tv_jb_presenter = (EditText)findViewById(R.id.tv_jb_presenter);
        tv_jb_recoder = (EditText)findViewById(R.id.tv_jb_recoder);
        inflate = LayoutInflater.from(LookMeetingActivity.this);

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
                }else{
//                    submitData();
                    isEditAble = "0";
                    tv_bj.setText("编辑");
                    tv_name.setEnabled(false);
                    tv_theme.setEnabled(false);
                    tv_jb_loc.setEnabled(false);
                    tv_jb_lecture.setEnabled(false);
                    tv_jb_presenter.setEnabled(false);
                    tv_jb_recoder.setEnabled(false);
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
                time = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+datetime.getSeconds();
                tv_jb_time.setText(time);
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

        tv_xcch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LookMeetingActivity.this, SelectMemberJoinActivity.class);

                startActivityForResult(intent,2);
            }
        });


//        mFlowLayout = (TagFlowLayout)findViewById(R.id.id_flowlayout);
//
//        fl_yc = (TagFlowLayout)findViewById(R.id.fl_yc);
//        fl_qx = (TagFlowLayout)findViewById(R.id.fl_qx);

        if(conferenceBean != null){
            String title = conferenceBean.getTitle();
            tv_title.setText(title);
            tv_name.setText(title);
            String theme = conferenceBean.getTheme();
            tv_theme.setText(theme);

            String gmtCreate = conferenceBean.getGmtStart();
            time  =gmtCreate;
            tv_pub_time.setText(gmtCreate);
            tv_jb_time.setText(gmtCreate);
            String site = conferenceBean.getSite();
            tv_jb_loc.setText(site);

            String summarize = conferenceBean.getSummarize();
            tv_summarize.setText(summarize);


            //缺席人员
//            String[] absentees = conferenceBean.getAbsentees().split(";");
//            tv_qx.setText("缺席("+absentees.length+"人)");
            tv_qx_member.setText(conferenceBean.getAbsentees());
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
            tv_xcch_member.setText(conferenceBean.getAttendees());
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
            String comperes = conferenceBean.getComperes();
            tv_jb_presenter.setText(comperes);
            //授课人
            String lecturers = conferenceBean.getLecturers();
            tv_jb_lecture.setText(lecturers);
            //参与人员
            String[] people = conferenceBean.getPeople().split(";");

            sb = new StringBuilder();
            sb.append(conferenceBean.getPeople());
            tv_xcch_member.setText(sb.toString());





            //记录人
            String recorders = conferenceBean.getRecorders();
            tv_jb_recoder.setText(recorders);
            //远程参与人
//            String[] telecommuters = conferenceBean.getTelecommuters().split(";");
//            tv_ycch.setText("远程参会("+telecommuters.length+"人)");
            tv_ycch_member.setText(conferenceBean.getTelecommuters());


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
        initVideoView();
        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_play.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
//                videoView.setVideoURI(uri);
                videoView.start();
            }
        });

    }

    /**
     *初始化videoview播放
     */
    public void initVideoView(){
        //初始化进度条
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //初始化VideoView
        videoView = (VideoView) findViewById(R.id.videoView);
        //初始化videoview控制条
        MediaController mediaController=new MediaController(this);
        //设置videoview的控制条
        videoView.setMediaController(mediaController);
        //设置显示控制条
        mediaController.show(0);


        //设置播放完成以后监听
        videoView.setOnCompletionListener(this);
        //设置发生错误监听，如果不设置videoview会向用户提示发生错误
        videoView.setOnErrorListener(this);
        //设置在视频文件在加载完毕以后的回调函数
        videoView.setOnPreparedListener(this);
        //设置videoView的点击监听
//        videoView.setOnTouchListener(this);
        //设置网络视频路径
        Uri uri=Uri.parse("http://v11-tt.ixigua.com/99159571a129ee7cc6a8937890cd5c3a/5b1de366/video/m/2209b5971fcd2a74c70807a61a9bfc18b101157d86b0000a286b6de3f3f/");
        videoView.setVideoURI(uri);
        //设置为全屏模式播放
//        setVideoViewLayoutParams(1);
    }

    /**
     * 视频文件加载文成后调用的回调函数
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        //如果文件加载成功,隐藏加载进度条
        progressBar.setVisibility(View.GONE);

    }
    /**
     * 页面暂停效果处理
     */
    @Override
    protected  void onPause() {
        super.onPause();
        //如果当前页面暂停则保存当前播放位置，全局变量保存
        intPositionWhenPause = videoView.getCurrentPosition();
        //停止回放视频文件
        videoView.stopPlayback();
    }

    /**
     * 页面从暂停中恢复
     */
    @Override
    protected void onResume() {
        super.onResume();
        //跳转到暂停时保存的位置
        if(intPositionWhenPause>=0){
            videoView.seekTo(intPositionWhenPause);
            //初始播放位置
            intPositionWhenPause=-1;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //启动视频播放
        videoView.start();
        //设置获取焦点
        videoView.setFocusable(true);

    }


    /**
     * 设置videiview的全屏和窗口模式
     * @param paramsType 标识 1为全屏模式 2为窗口模式
     */
    public void setVideoViewLayoutParams(int paramsType){
//全屏模式
        if(1==paramsType) {
//设置充满整个父布局
            RelativeLayout.LayoutParams LayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//设置相对于父布局四边对齐
            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//为VideoView添加属性
            videoView.setLayoutParams(LayoutParams);
        }else{
//窗口模式
            //获取整个屏幕的宽高
            DisplayMetrics DisplayMetrics=new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(DisplayMetrics);
//设置窗口模式距离边框50
            int videoHeight=DisplayMetrics.heightPixels-50;
            int videoWidth=DisplayMetrics.widthPixels-50;
            RelativeLayout.LayoutParams LayoutParams = new RelativeLayout.LayoutParams(videoWidth,videoHeight);
//设置居中
            LayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//为VideoView添加属性
            videoView.setLayoutParams(LayoutParams);
        }
    }
    /**
     * 视频播放发生错误时调用的回调函数
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what){
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.e("text","发生未知错误");

                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Log.e("text","媒体服务器死机");
                break;
            default:
                Log.e("text","onError+"+what);
                break;
        }
        switch (extra){
            case MediaPlayer.MEDIA_ERROR_IO:
                //io读写错误
                Log.e("text","文件或网络相关的IO操作错误");
                break;
            case MediaPlayer.MEDIA_ERROR_MALFORMED:
                //文件格式不支持
                Log.e("text","比特流编码标准或文件不符合相关规范");
                break;
            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                //一些操作需要太长时间来完成,通常超过3 - 5秒。
                Log.e("text","操作超时");
                break;
            case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                //比特流编码标准或文件符合相关规范,但媒体框架不支持该功能
                Log.e("text","比特流编码标准或文件符合相关规范,但媒体框架不支持该功能");
                break;
            default:
                Log.e("text","onError+"+extra);
                break;
        }
        return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2){
            if(resultCode == 3){
                SerializableMap serializabmap = (SerializableMap)data.getSerializableExtra("item");
                Map<String, MyNodeBean> map = serializabmap.getMap();

                sb.delete(0,sb.length());
                Iterator<Map.Entry<String, MyNodeBean>> iterator = map.entrySet().iterator();
                while(iterator.hasNext()){
                    Map.Entry<String, MyNodeBean> next = iterator.next();
                    MyNodeBean value = next.getValue();
                    String name = value.getName();
                    sb.append(name);
                }

                tv_xcch_member.setText(sb.toString());
            }
        }
    }



    private void submitData() {
        String name = tv_name.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "请先输入会议名称", Toast.LENGTH_SHORT).show();
            return;
        }

        String theme = tv_theme.getText().toString().trim();
        if(TextUtils.isEmpty(theme)){
            Toast.makeText(this, "请先输入会议主题", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(time)){
            Toast.makeText(this, "请先选择会议开始时间", Toast.LENGTH_SHORT).show();
            return;
        }
        String loc = tv_jb_loc.getText().toString().trim();
        if(TextUtils.isEmpty(loc)){
            Toast.makeText(this, "请先输入会议举办地点", Toast.LENGTH_SHORT).show();
            return;
        }

        String lecture = tv_jb_lecture.getText().toString().trim();
//        if(TextUtils.isEmpty(lecture)){
//            Toast.makeText(this, "请先输入授课人", Toast.LENGTH_SHORT).show();
//            return;
//        }
        String presenter = tv_jb_presenter.getText().toString().trim();

        String recoder = tv_jb_recoder.getText().toString().trim();


        Log.e("aaa","参会人员："+sb.toString());
        AppApplication.getDataProvider().updateMeeting(type+"", "0", name, theme, loc, lecture, presenter, recoder, sb.toString(), "", "", "", "", time, new AjaxCallBack<Object>() {

            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","发布会议返回："+o.toString());


                try {
                    JSONObject object = new JSONObject(o.toString());
                    String code = object.getString("code");
                    String msg = object.getString("msg");
                    if(TextUtils.equals(code,"0")){
                        finish();
                    }
                    Toast.makeText(LookMeetingActivity.this, msg+"", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        iv_play.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
