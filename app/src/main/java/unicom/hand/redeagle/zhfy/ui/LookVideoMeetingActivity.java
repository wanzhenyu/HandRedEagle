package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.yealink.base.util.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.picker.DateTimePicker;
import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.bean.EditMeetBean;
import unicom.hand.redeagle.zhfy.bean.HyrcBeanMd5;
import unicom.hand.redeagle.zhfy.bean.LookVideoMeetBean;
import unicom.hand.redeagle.zhfy.bean.MeetDetail;
import unicom.hand.redeagle.zhfy.bean.MeetDetailListBean;
import unicom.hand.redeagle.zhfy.bean.MyNodeBean;
import unicom.hand.redeagle.zhfy.bean.OnImgListener;
import unicom.hand.redeagle.zhfy.bean.OneMeetDetail;
import unicom.hand.redeagle.zhfy.bean.Oneparticipants;
import unicom.hand.redeagle.zhfy.bean.PeopleBean;
import unicom.hand.redeagle.zhfy.bean.QueryLayoutBean;
import unicom.hand.redeagle.zhfy.bean.SerializableMap;
import unicom.hand.redeagle.zhfy.fragment.MeetDetailAddImgAdapter;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.GsonUtils;
import unicom.hand.redeagle.zhfy.utils.MyCallBack;
import unicom.hand.redeagle.zhfy.utils.UIUtils;
import unicom.hand.redeagle.zhfy.utils.UploadUtils;
import unicom.hand.redeagle.zhfy.view.MyListView;

import static unicom.hand.redeagle.R.id.webView;

public class LookVideoMeetingActivity extends Activity  implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, OnImgListener, MyCallBack {

//    private HyrcBeanMd5 conferenceBean;
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
    private VideoView videoView;
    private ProgressBar progressBar;
    private int intPositionWhenPause = -1;
    private ImageView iv_play;
    private String startDate = "";
    private String starttime = "";


    private File file;
    private String filePath = "";
    private String mTmpImgPath = "";




    private String endDate = "";
    private String endtime = "";
    private TextView tv_end_time;
    private DateTimePicker endpicker;
    private List<PeopleBean> participantsList;
    private String type;
    private LinearLayout ll_presenter;
    private LinearLayout ll_lecture;
    private MyListView lv_hyrc;
    private List<String> addimgs;
    private MeetDetailAddImgAdapter meetDetailAddImgAdapter;
    private UploadUtils uploadUtils;
    private String confId = "";
    private List<String> urls;
    //    private WebView mWebView;
//    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_meeting_video);
        participantsList = new ArrayList<>();
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv_play = (ImageView)findViewById(R.id.iv_play);
        urls = new ArrayList<>();
        addimgs = new ArrayList<>();
        addimgs.add("");

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        yearFormat = new SimpleDateFormat("yyyy-MM-dd");
        timeFormat = new SimpleDateFormat("HH:mm:ss");

        lv_hyrc = (MyListView)findViewById(R.id.lv_hyrc);
        meetDetailAddImgAdapter = new MeetDetailAddImgAdapter(LookVideoMeetingActivity.this,addimgs,this,isEditAble);
        lv_hyrc.setAdapter(meetDetailAddImgAdapter);
        ll_presenter = (LinearLayout)findViewById(R.id.ll_presenter);
        ll_lecture = (LinearLayout)findViewById(R.id.ll_lecture);
        uploadUtils = new UploadUtils(LookVideoMeetingActivity.this,this);
//        conferenceBean = (HyrcBeanMd5)getIntent().getSerializableExtra("item");
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
        inflate = LayoutInflater.from(LookVideoMeetingActivity.this);
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
                Intent intent = new Intent(LookVideoMeetingActivity.this, SelectMemberJoinActivity.class);

                startActivityForResult(intent,2);
            }
        });


        mFlowLayout = (TagFlowLayout)findViewById(R.id.id_flowlayout);
//
//        fl_yc = (TagFlowLayout)findViewById(R.id.fl_yc);
//        fl_qx = (TagFlowLayout)findViewById(R.id.fl_qx);

//        if(conferenceBean != null){
//            conferencePlanId = conferenceBean.getConferencePlanId();
//
//            String title = conferenceBean.getTitle();
//            tv_name.setText(UIUtils.getHyrcTtitle(title));
////            String theme = conferenceBean.getTheme();
//            tv_theme.setText(UIUtils.getHyrcTtitle(title));
//
////            String gmtCreate = conferenceBean.getGmtStart();
////            time  =gmtCreate;
////            tv_pub_time.setText(gmtCreate);
//            String start = conferenceBean.getStart();
//            String end = conferenceBean.getEnd();
//            String format = simpleDateFormat.format(new Date(Long.parseLong(start)));
//            tv_jb_time.setText(format);
//            time = format;
//            starttime = timeFormat.format(new Date(Long.parseLong(start)));
//            startDate = yearFormat.format(new Date(Long.parseLong(start)));
//            String format1 = simpleDateFormat.format(new Date(Long.parseLong(end)));
//            tv_end_time.setText(format1);
//            endtime = timeFormat.format(new Date(Long.parseLong(end)));
//            endDate = yearFormat.format(new Date(Long.parseLong(end)));
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

//
//
//
//
//
//
//
//        }

        initVideoView();
        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = urls.size();
                if(urls == null || size <= 0){
                    ToastUtil.toast(LookVideoMeetingActivity.this,"暂无视频");
                    return;
                }
                iv_play.setVisibility(View.GONE);
                videoView.start();
            }
        });

//        initWebView();


        confId = getIntent().getStringExtra("confId");
        OneMeetDetail meetdetail = new OneMeetDetail();
//        meetdetail.setConfEntity("1");
        meetdetail.setConferenceRecordId(confId);
        String json = GsonUtil.getJson(meetdetail);
        Log.e("aaa","提交的参数："+json);
        AppApplication.getDataProvider().getOneMeetDetail(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","会议详情："+o.toString());
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1){
                        if(!object.isNull("data")){
                            JSONObject data = object.getJSONObject("data");
                            String json1 = GsonUtil.getJson(data);
                            Log.e("aaa","返回课题："+json1);

                            LookVideoMeetBean bean = GsonUtils.getBean(data.toString(), LookVideoMeetBean.class);
                            setUI(bean);

                        }
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




        getVideoUrl();








    }

    //得到视频连接
    private void getVideoUrl() {
        QueryLayoutBean queryLayoutBean = new QueryLayoutBean();
        queryLayoutBean.setConferenceRecordId(confId);
        queryLayoutBean.setConfEntity("1");
        String json = GsonUtils.getJson(queryLayoutBean);
        AppApplication.getDataProvider().getVideoUrl(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","会议详情onSuccess："+o.toString());
                try {
                    if(o != null){
                        JSONObject object = new JSONObject(o.toString());
                        int ret = object.getInt("ret");
                        if(ret == 1) {
                            if (!object.isNull("data")) {
                                JSONArray data = object.getJSONArray("data");
                                for (int i=0;i<data.length();i++){
                                    JSONObject jsonObject = data.getJSONObject(i);
                                    String recordingFileName = jsonObject.getString("recordingWatchingUrl");

                                            if(!TextUtils.equals(recordingFileName,null) && !TextUtils.isEmpty(recordingFileName)){
                                                urls.add(recordingFileName);
                                            }
                                }

                                playVideo();



                            }
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("aaa","onFailure会议详情：");
            }
        });


    }

    private void playVideo() {
        int size = urls.size();
        if(urls != null && size>0){
            String s = urls.get(size - 1);
//            String replace = s.replace("http://222.136.225.137:80/ss/file/download/", "http://222.136.225.137:82/html5player/?fn=");
            Uri uri=Uri.parse(s);
            videoView.setVideoURI(uri);
        }


    }

    @Override
    public void add() {

        showPickImgDialog();


    }

    @Override
    public void remove(int pos) {
        addimgs.remove(pos);
        if(meetDetailAddImgAdapter != null){
            meetDetailAddImgAdapter.notifyDataSetChanged();
        }
    }
    private static final String[] pic_item = {"从相册选择", "使用相机拍照", "取消"};
    private void showPickImgDialog() {
        file = new File(Environment.getExternalStorageDirectory(),
                "temp.jpg");
        filePath = Environment.getExternalStorageDirectory()
                + "/temp.jpg";
        AlertDialog.Builder builder = new AlertDialog.Builder(
                LookVideoMeetingActivity.this);
        builder.setItems(pic_item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0://从相册选择
                        Intent intent_posoto = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent_posoto, 101);
                        break;
                    case 1://从照相机选择
                        mTmpImgPath = filePath;
                        Intent toSysCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // Samsung的系统相机，版式是横板的,同时此activity不要设置单例模式
                        toSysCamera.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                        toSysCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(file));
                        // 调用系统拍照
                        startActivityForResult(toSysCamera, 102);
                        break;
                    case 2:
                        dialogInterface.dismiss();
                        break;
                }
            }
        });
        builder.create().show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 101:
                    if (null != data) {
                        Uri uriImage = data.getData();
                        Cursor cursorImage = this.getContentResolver().query(
                                uriImage, null, null, null, null);
                        String imgSelectedPath = null;
                        if (cursorImage != null) {
                            if (cursorImage.moveToNext()) {
                                imgSelectedPath = cursorImage.getString(cursorImage
                                        .getColumnIndex("_data"));
                                cursorImage.close();
                            }
                        } else {
                            imgSelectedPath = uriImage.getPath();
                        }
                        mTmpImgPath = imgSelectedPath;
//                        mTmpImgPath = PhotoBitmapUtils.amendRotatePhoto(mTmpImgPath, getContext());
                        try {
//                            imageAdd.setImageURI(Uri.fromFile(new File(mTmpImgPath)));
//                            cropPhoto(Uri.fromFile(new File(mTmpImgPath)));

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            mTmpImgPath = "";
//                            toast("图片选择失败，请重试！");
                        }
//                        Log.d("LoginActivity", "--onSuccess" + imgSelectedPath);
                    }
//                    Log.e(TAG, mTmpImgPath);
                    Bitmap bitmapFromGallery = BitmapFactory.decodeFile(mTmpImgPath);
                    File file2 = new File(Environment.getExternalStorageDirectory()+"/kdwy.png");

//                    uploadUtils.compressBitmapToFile(bitmapFromGallery,file2);
                    uploadUtils.upload(file2.getAbsolutePath());

                    break;
                case 102:
                    String path = "";
                    // 机型适配（不同手机返回的地址不一样）
                    if (null != data && null != data.getData()) {
                        String[] projection = {MediaStore.MediaColumns.DATA};
                        Cursor cursor = managedQuery(data.getData(), projection,
                                null, null, null);
                        int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                        cursor.moveToFirst();
                        path = cursor.getString(index);
                    }
                    if (path == "") {
                        path = mTmpImgPath;
                    }
                    mTmpImgPath = path;
//                    mTmpImgPath = PhotoBitmapUtils.amendRotatePhoto(mTmpImgPath, getContext());
                    try {
//                        imageAdd.setImageURI(Uri.fromFile(new File(mTmpImgPath)));
//                        cropPhoto(Uri.fromFile(new File(mTmpImgPath)));

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        mTmpImgPath = "";
//                        toast("图片选择失败，请重试！");
                    }
//                    Log.d("LoginActivity", "--onSuccess" + path);
                    Bitmap bitmapFromCamera = BitmapFactory.decodeFile(mTmpImgPath);
                    File file1 = new File(Environment.getExternalStorageDirectory()+"/kdwy.png");

//                    compressBitmapToFile(bitmapFromCamera,file1);
                    uploadUtils.upload(file1.getAbsolutePath());
//                   Bitmap bitmapFromCamera =BitmapFactory.decodeFile(mTmpImgPath);;
//                    iv_change_icon.setImageBitmap(bitmapFromCamera);
                    break;
                case 103: // 图片缩放完成后
                    Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                    Drawable drawable = new BitmapDrawable(this.getResources(), bm);
//                    iv_change_icon.setBackgroundDrawable(drawable);
                    mTmpImgPath = Environment.getExternalStorageDirectory()
                            + "/temp.jpg";
                    // FileUtil.saveBitmap( bm );
                default:
                    break;
            }

        }else if(requestCode == 2){
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
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void sucess(String body) {
        addimgs.add(body);
        if(meetDetailAddImgAdapter != null){
            meetDetailAddImgAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void failed(String msg) {

    }

    //    private void initWebView() {
//        mWebView = (WebView) findViewById(webView);
//        webSettings = mWebView.getSettings();
//        webSettings.setDefaultTextEncodingName("UTF-8");
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setAllowFileAccess(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setLoadsImagesAutomatically(true);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//
//        // 开启支持视频
//        webSettings.setPluginState(WebSettings.PluginState.ON);
//        webSettings.setGeolocationEnabled(true);
//
//
//        mWebView.setWebViewClient(new HelloWebViewClient());
//        mWebView.loadUrl("http://124.72.94.27/ss/file/download/[in]T_fe0571c797[2018-07-06-16-48-33][2018-07-06-16-48-33].mp4");
//    }
    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }


    private void setUI(LookVideoMeetBean bean) {
        String subject = bean.getSubject();
        tv_name.setText(UIUtils.getHyrcTtitle(subject));
        tv_theme.setText(UIUtils.getHyrcTtitle(subject));
//        tv_number.setText(confNumber);
//
//        String pinCode = bean.getPinCode();
//        tv_psd.setText(pinCode);
//
//        String name = bean.getCurrentOperator().getName();
//        tv_organizer.setText(name);
        String conferenceDate = bean.getConferenceDate();
        String startTime = bean.getConferenceStartTime();
//        long expiryTime = bean.getExpiryTime();
//        String format1 = timeFormat.format(new Date(expiryTime));

        tv_jb_time.setText(conferenceDate+" "+startTime);
        String []strs = null;
        List<Oneparticipants> participants = bean.getParticipants();
        if(participants != null && participants.size()>0){
            strs = new String[participants.size()];
            for (int i=0;i<participants.size();i++){
                Oneparticipants participants1 = participants.get(i);
                String name1 = participants1.getDisplayText();
                strs[i] = name1;
            }
        }else{
            strs = new String[0];
        }


        mFlowLayout.setAdapter(new TagAdapter<String>(strs)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) inflate.inflate(R.layout.item_flowlayout_onlytext,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == 2){
//            if(resultCode == 3){
//                SerializableMap serializabmap = (SerializableMap)data.getSerializableExtra("item");
//                Map<String, MyNodeBean> map = serializabmap.getMap();
//
//                if(participantsList.size()>0){
//                    participantsList.clear();
//                }
//                sb.delete(0,sb.length());
//                Iterator<Map.Entry<String, MyNodeBean>> iterator = map.entrySet().iterator();
//                while(iterator.hasNext()){
//                    Map.Entry<String, MyNodeBean> next = iterator.next();
//                    MyNodeBean value = next.getValue();
//                    String name = value.getName();
//                    sb.append(name);
//                    PeopleBean joinpeople = new PeopleBean();
//                    joinpeople.uid = value.getNodeId().substring(0,32);
//                    if(value.getType() == 16){
//                        joinpeople.type = "thirdparty";
//                    }else{
//                        joinpeople.type = "normal";
//                    }
//
//                    joinpeople.role = "attendee";
//                    participantsList.add(joinpeople);
//                }
//
//                tv_xcch_member.setText(sb.toString());
//            }
//        }
//    }



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
        editmeetbean.setEmailRemark("");

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
//        Log.e("aaa","传参："+json);
        AppApplication.getDataProvider().editMeetDetail(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","服务器返回："+o.toString());
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1){
                        Toast.makeText(LookVideoMeetingActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LookVideoMeetingActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
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
        iv_play.setVisibility(View.VISIBLE);
        //设置videoView的点击监听
//        videoView.setOnTouchListener(this);
        //设置网络视频路径
//        Uri uri=Uri.parse("http://124.72.94.27/ss/file/download/[in]T_fe0571c797[2018-07-06-16-48-33][2018-07-06-16-48-33].mp4");
//        videoView.setVideoURI(uri);
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
//        iv_play.setVisibility(View.GONE);
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
    public void onCompletion(MediaPlayer mediaPlayer) {
        iv_play.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
