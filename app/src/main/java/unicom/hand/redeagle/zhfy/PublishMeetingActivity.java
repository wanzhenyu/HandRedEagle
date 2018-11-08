package unicom.hand.redeagle.zhfy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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
import unicom.hand.redeagle.zhfy.bean.MyNodeBean;
import unicom.hand.redeagle.zhfy.bean.SerializableMap;
import unicom.hand.redeagle.zhfy.ui.LookMeetingActivity;
import unicom.hand.redeagle.zhfy.ui.MyDepartMentActivity;
import unicom.hand.redeagle.zhfy.ui.SelectMemberJoinActivity;

public class PublishMeetingActivity extends Activity {

    private TextView tv_submit;
    private TextView tv_time;
    private DateTimePicker picker;
    private String time = "";
    private EditText et_name;
    private EditText et_theme;
    private EditText et_loc;
    private EditText et_lecture;
    private RelativeLayout rl_joinmemeber;
    private TagFlowLayout fl_qx;
    private LayoutInflater inflate;
    private String strs[];
    private String type = "1";
    private EditText et_presenter;
    private EditText et_recoder;
    private StringBuilder sb;
    private Date datetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_meeting);
        fl_qx = (TagFlowLayout)findViewById(R.id.fl_qx);
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        type = getIntent().getStringExtra("type");
        tv_submit = (TextView)findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitData();

            }
        });
        inflate = LayoutInflater.from(PublishMeetingActivity.this);
        //24小时值
        picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
        picker.setDateRangeStart(2018, 1, 1);//日期起点
        picker.setDateRangeEnd(2030, 1,1);//日期终点
        picker.setTimeRangeStart(0, 0);//时间范围起点
        picker.setTimeRangeEnd(23, 59);//时间范围终点
        tv_time = (TextView)findViewById(R.id.tv_time);
        LinearLayout rl_time = (LinearLayout)findViewById(R.id.rl_time);
        rl_joinmemeber = (RelativeLayout)findViewById(R.id.rl_joinmemeber);

        datetime = new Date();

        rl_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                    @Override
                    public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                        //year:年，month:月，day:日，hour:时，minute:分
                        time = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+datetime.getSeconds();
                        tv_time.setText(time);
                    }
                });
                picker.show();
            }
        });


        et_name = (EditText)findViewById(R.id.et_name);
        et_theme = (EditText)findViewById(R.id.et_theme);
        et_loc = (EditText)findViewById(R.id.et_loc);
        et_lecture = (EditText)findViewById(R.id.et_lecture);

        et_presenter = (EditText)findViewById(R.id.et_presenter);

        et_recoder = (EditText)findViewById(R.id.et_recoder);

        rl_joinmemeber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PublishMeetingActivity.this, SelectMemberJoinActivity.class);

                startActivityForResult(intent,2);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int  i =0;

        if(requestCode == 2){
            if(resultCode == 3){
                SerializableMap serializabmap = (SerializableMap)data.getSerializableExtra("item");
                Map<String, MyNodeBean> map = serializabmap.getMap();
                strs = new String[map.size()];
                Iterator<Map.Entry<String, MyNodeBean>> iterator = map.entrySet().iterator();
                while(iterator.hasNext()){
                    Map.Entry<String, MyNodeBean> next = iterator.next();
                    MyNodeBean value = next.getValue();
                    String name = value.getName();
                    strs[i] = name;
                    i++;
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
        }
    }

    private void submitData() {
        String name = et_name.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "请先输入会议名称", Toast.LENGTH_SHORT).show();
            return;
        }

        String theme = et_theme.getText().toString().trim();
        if(TextUtils.isEmpty(theme)){
            Toast.makeText(this, "请先输入会议主题", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(time)){
            Toast.makeText(this, "请先选择会议开始时间", Toast.LENGTH_SHORT).show();
            return;
        }
        String loc = et_loc.getText().toString().trim();
        if(TextUtils.isEmpty(loc)){
            Toast.makeText(this, "请先输入会议举办地点", Toast.LENGTH_SHORT).show();
            return;
        }

        String lecture = et_lecture.getText().toString().trim();
        if(TextUtils.isEmpty(lecture)){
            Toast.makeText(this, "请先输入授课人", Toast.LENGTH_SHORT).show();
            return;
        }
        sb = new StringBuilder();
        String presenter = et_presenter.getText().toString().trim();

        String recoder = et_recoder.getText().toString().trim();

        if(strs != null){
            for (int i=0;i<strs.length;i++){
                sb.append(strs[i]+" ");
            }
            Log.e("aaa","参会人员："+sb.toString());
        }

        AppApplication.getDataProvider().publishMeeting(type, "0", name, theme, loc, lecture, presenter, recoder, sb.toString(), "", "", "", "", time, new AjaxCallBack<Object>() {

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
                    Toast.makeText(PublishMeetingActivity.this, msg+"", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(PublishMeetingActivity.this, "发布会议失败", Toast.LENGTH_SHORT).show();
            }
        });






    }







}
