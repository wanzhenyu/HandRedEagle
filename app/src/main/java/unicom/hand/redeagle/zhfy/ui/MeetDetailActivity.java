package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.BaseActivity;
import unicom.hand.redeagle.zhfy.bean.MeetDetail;
import unicom.hand.redeagle.zhfy.bean.MeetDetailListBean;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.GsonUtils;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

public class MeetDetailActivity extends BaseActivity {

    private TextView tv_subject;
    private TextView tv_time;
    private TextView tv_number;
    private TextView tv_psd;
    private TextView tv_organizer;
    private TagFlowLayout fl_yc;
    private LayoutInflater inflate;
    private SimpleDateFormat simdateformate;
    private TextView tv_bj;
    private String confId;
    private String  confEntity = "";
    private String type;

    //    List<MeetDetailListBean.Participants> intentparticipants;
    @Override
    protected void handleMsg(Message msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_detail);
//        intentparticipants = new ArrayList<>();
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_subject = (TextView)findViewById(R.id.tv_subject);
        tv_time = (TextView)findViewById(R.id.tv_time);
        tv_number = (TextView)findViewById(R.id.tv_number);
        tv_psd = (TextView)findViewById(R.id.tv_psd);
        tv_organizer = (TextView)findViewById(R.id.tv_organizer);
        fl_yc = (TagFlowLayout)findViewById(R.id.fl_yc);
        tv_bj = (TextView)findViewById(R.id.tv_bj);
        confId = getIntent().getStringExtra("confId");
        type = getIntent().getStringExtra("type");
        tv_bj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeetDetailActivity.this,ControllActivityActivity.class);
                intent.putExtra("confId",confId);
                intent.putExtra("confEntity",confEntity);
                intent.putExtra("type",type);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("serinfo", (Serializable) intentparticipants);
//                intent.putExtra("itemlist",bundle);
                startActivityForResult(intent,1);
            }
        });

        simdateformate = new SimpleDateFormat("MM/dd HH:mm");
        inflate = LayoutInflater.from(MeetDetailActivity.this);

        MeetDetail meetdetail = new MeetDetail();
        meetdetail.setConfEntity("1");
        meetdetail.setConfId(confId);
        String json = GsonUtil.getJson(meetdetail);
//        Log.e("aaa","提交的参数："+json);
        AppApplication.getDataProvider().getMeetDetail(json, new AjaxCallBack<Object>() {
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

                            MeetDetailListBean bean = GsonUtils.getBean(data.toString(), MeetDetailListBean.class);
                            if(bean != null){
                                tv_bj.setVisibility(View.VISIBLE);
                                confEntity = bean.getConfEntity();
                                setUI(bean);
                            }



                        }
                    }else{

                        JSONObject rows = object.getJSONObject("error");
                        String msg = rows.getString("msg");
                        Toast.makeText(MeetDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1){
            finish();
        }
    }

    private void setUI(MeetDetailListBean bean) {
//        if(intentparticipants.size()>0){
//            intentparticipants.clear();
//        }
        String subject = bean.getSubject();
        tv_subject.setText(UIUtils.getHyrcTtitle(subject));
        String confNumber = bean.getConfNumber();
        tv_number.setText(confNumber);

        String pinCode = bean.getPinCode();
        tv_psd.setText(pinCode);
        MeetDetailListBean.CurrentOperator currentOperator = bean.getCurrentOperator();
        if(currentOperator != null){
            String name = currentOperator.getName();
            tv_organizer.setText(name);
//            MeetDetailListBean.Participants  participant = new MeetDetailListBean.Participants();
//            participant.setName(name);
//            String role = currentOperator.getRole();
//            participant.setUserType(role);
//
//            String uid = currentOperator.getUid();
//            participant.set_id(uid);
//
//            String userEntity = currentOperator.getUserEntity();
//            participant.setType(role);
//
//            String userName = currentOperator.getUserName();
//            intentparticipants.add(participant);
        }


        long startTime = bean.getStartTime();
        long expiryTime = bean.getExpiryTime();
        String format = simdateformate.format(new Date(startTime));
        String format1 = simdateformate.format(new Date(expiryTime));

        tv_time.setText(format +"~"+format1);
        List<String> strs = new ArrayList<>();
        List<MeetDetailListBean.Participants> participants = bean.getParticipants();
        if(participants != null && participants.size()>0){
//            intentparticipants.addAll(participants);
//            strs = new String[participants.size()];
            for (int i=0;i<participants.size();i++){
                MeetDetailListBean.Participants participants1 = participants.get(i);
                String name1 = participants1.getName();
                String userType = participants1.getAccountType();
                if(TextUtils.equals(userType,"Normal")){
                    strs.add(name1);
                }

            }
        }else{
            strs.clear();
        }


        fl_yc.setAdapter(new TagAdapter<String>(strs)
            {
                @Override
                public View getView(FlowLayout parent, int position, String s)
                {
                    TextView tv = (TextView) inflate.inflate(R.layout.item_flowlayout_onlytext,
                            fl_yc, false);
                    tv.setText(s);
                    return tv;
                }
            });
    }
}
