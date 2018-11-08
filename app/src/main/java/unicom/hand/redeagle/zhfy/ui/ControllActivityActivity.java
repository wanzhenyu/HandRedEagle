package unicom.hand.redeagle.zhfy.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yealink.base.util.ToastUtil;
import com.yealink.sdk.YealinkApi;

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.BaseActivity;
import unicom.hand.redeagle.zhfy.adapter.PopListAdapter;
import unicom.hand.redeagle.zhfy.bean.LockBean;
import unicom.hand.redeagle.zhfy.bean.MeetDetail;
import unicom.hand.redeagle.zhfy.bean.MuteBean;
import unicom.hand.redeagle.zhfy.bean.MyNodeBean;
import unicom.hand.redeagle.zhfy.bean.RecoderMeetBean;
import unicom.hand.redeagle.zhfy.bean.SerializableMap;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

public class ControllActivityActivity extends BaseActivity {

    private RelativeLayout rl_invite;
    private RelativeLayout rl_layout;
    private RelativeLayout rl_textmsg;
    private RelativeLayout rl_recoder;
    private RelativeLayout rl_unforbiddenask;
    private RelativeLayout rl_forbiddenask;
    private RelativeLayout rl_oknoise;
    private RelativeLayout rl_closenoise;
    private RelativeLayout rl_unlock;
    private RelativeLayout rl_lock;
    private RelativeLayout rl_exit;
    private String confId;
    private TextView tv_recoder_status;
    private String recoderId = "";
    private String intentconfEntity;
    private String type;
    //    private List<MeetDetailListBean.Participants> intentparticipants;

    @Override
    protected void handleMsg(Message msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controll_activity);
        ImageView iv_left = (ImageView)findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        Bundle itemlist = getIntent().getBundleExtra("itemlist");
//        intentparticipants = (List<MeetDetailListBean.Participants>)itemlist.getSerializable("serinfo");
        confId = getIntent().getStringExtra("confId");
        intentconfEntity = getIntent().getStringExtra("confEntity");
        rl_invite = (RelativeLayout)findViewById(R.id.rl_invite);
        rl_layout = (RelativeLayout)findViewById(R.id.rl_layout);
        rl_textmsg = (RelativeLayout)findViewById(R.id.rl_textmsg);
        rl_recoder = (RelativeLayout)findViewById(R.id.rl_recoder);
        rl_unforbiddenask = (RelativeLayout)findViewById(R.id.rl_unforbiddenask);
        rl_forbiddenask = (RelativeLayout)findViewById(R.id.rl_forbiddenask);
        rl_oknoise = (RelativeLayout)findViewById(R.id.rl_oknoise);
        rl_closenoise = (RelativeLayout)findViewById(R.id.rl_closenoise);
        rl_unlock = (RelativeLayout)findViewById(R.id.rl_unlock);
        rl_lock = (RelativeLayout)findViewById(R.id.rl_lock);
        rl_exit = (RelativeLayout)findViewById(R.id.rl_exit);
        tv_recoder_status = (TextView)findViewById(R.id.tv_recoder_status);


        //邀请
        rl_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ControllActivityActivity.this, SelectMemberJoinActivity.class);

                startActivityForResult(intent,2);
            }
        });
        //布局
        rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ControllActivityActivity.this, LayoutActivity.class);
                intent.putExtra("confId",confId);
                intent.putExtra("confEntity",intentconfEntity);
                startActivity(intent);
            }
        });
        type = getIntent().getStringExtra("type");
        //文字消息
        rl_textmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ControllActivityActivity.this, TxtMainActivity.class);
                intent.putExtra("confId",confId);
                intent.putExtra("confEntity",intentconfEntity);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });

        //会议录制
        rl_recoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow(ControllActivityActivity.this);
            }
        });
        //全员解除禁言
        rl_unforbiddenask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglemute(1);
            }
        });
        //全员禁言
        rl_forbiddenask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglemute(0);
            }
        });
        //解除闭音
        rl_oknoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deaf(1);
            }
        });
        //全员闭音
        rl_closenoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deaf(0);
            }
        });
        //解锁会议
        rl_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lock(1);
            }
        });
        rl_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lock(0);
            }
        });
        rl_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindowexit(ControllActivityActivity.this);
            }
        });
        getRecoderStatus();







    }
    private String[] exittitles = {"结束会议","离开会议,会议继续进行"};
    private void popWindowexit(Context context){
        View contentView1= LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pop_list_textcolor, null, false);
//        ListView list = (ListView)contentView1.findViewById(R.id.list);
//        list.setAdapter(new PopListAdapter(context,exittitles));
        TextView tv_number = (TextView)contentView1.findViewById(R.id.tv_number);
//        tv_number.setVisibility(View.GONE);
        tv_number.setText("您要结束会议还是离开会议?");
        TextView tv_poptitle = (TextView)contentView1.findViewById(R.id.tv_title);
        tv_poptitle.setText("退出会议");
        TextView tv_close = (TextView)contentView1.findViewById(R.id.tv_close);
        final PopupWindow pop1=new PopupWindow(contentView1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop1.setOutsideTouchable(true);
        pop1.setTouchable(true);

        pop1.showAtLocation(rl_recoder, Gravity.BOTTOM,0,0);
        //结束会议
        TextView tv_name1 = (TextView)contentView1.findViewById(R.id.tv_name1);
        tv_name1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop1.dismiss();
                finishMeet();
            }
        });
        //离开会议
        TextView tv_name2= (TextView)contentView1.findViewById(R.id.tv_name2);
        tv_name2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop1.dismiss();
                setResult(1);
                finish();
            }
        });
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop1.dismiss();
            }
        });
    }

    private void finishMeet() {
        MeetDetail recoderbean = new MeetDetail();
        recoderbean.setConfEntity(intentconfEntity);
        recoderbean.setConfId(confId);
        String json = GsonUtil.getJson(recoderbean);
        AppApplication.getDataProvider().finishRecoder(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1){
                        Toast.makeText(ControllActivityActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                        setResult(1);
                        finish();
                    }else{
                        JSONObject rows = object.getJSONObject("error");
                        String msg = rows.getString("msg");
                        Toast.makeText(ControllActivityActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(ControllActivityActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void exitDialog() {

    }

    /**
     * 锁定或者解除会议
     * @param i
     */
    private void lock(int i) {
        LockBean mutebean = new LockBean();
        mutebean.setConfId(confId);
        mutebean.setConfEntity(intentconfEntity);
//        mutebean.setUserEntities(new ArrayList<String>());
        if(i == 1){
            mutebean.setBlock(false);
        }else{
            mutebean.setBlock(true);
        }
        String json = GsonUtil.getJson(mutebean);
        AppApplication.getDataProvider().lock(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","锁定会议返回："+o.toString());
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1){
                        Toast.makeText(ControllActivityActivity.this, "操作成功", Toast.LENGTH_SHORT).show();

                    }else{
                        JSONObject rows = object.getJSONObject("error");
                        String msg = rows.getString("msg");
                        Toast.makeText(ControllActivityActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(ControllActivityActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * 解除闭音或者全员闭音
     * @param i
     */
    private void deaf(int i) {
        MuteBean mutebean = new MuteBean();
        mutebean.setConfId(confId);
        mutebean.setConfEntity(intentconfEntity);
//        mutebean.setUserEntities(new ArrayList<String>());
        if(i == 1){
            mutebean.setBlock(false);
        }else{
            mutebean.setBlock(true);
        }
        String json = GsonUtil.getJson(mutebean);
        AppApplication.getDataProvider().deaf(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","禁言返回："+o.toString());
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1){
                        Toast.makeText(ControllActivityActivity.this, "操作成功", Toast.LENGTH_SHORT).show();

                    }else{
                        JSONObject rows = object.getJSONObject("error");
                        String msg = rows.getString("msg");
                        Toast.makeText(ControllActivityActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(ControllActivityActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
            }
        });


    }


    /**
     * 禁言或者解除禁言
     * @param i 1代表解除禁言，非1代表禁言
     */
    private void togglemute(int i) {
        MuteBean mutebean = new MuteBean();
        mutebean.setConfId(confId);
        mutebean.setConfEntity(intentconfEntity);
//        mutebean.setUserEntities(new ArrayList<String>());
        if(i == 1){
            mutebean.setBlock(false);
        }else{
            mutebean.setBlock(true);
        }
        String json = GsonUtil.getJson(mutebean);
        AppApplication.getDataProvider().mute(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","禁言返回："+o.toString());
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1){
                        Toast.makeText(ControllActivityActivity.this, "操作成功", Toast.LENGTH_SHORT).show();

                    }else{
                        JSONObject rows = object.getJSONObject("error");
                        String msg = rows.getString("msg");
                        Toast.makeText(ControllActivityActivity.this, msg, Toast.LENGTH_SHORT).show();
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

    /**
     * 得到会议录制当前状态
     */
    private void getRecoderStatus() {
        RecoderMeetBean recoderbean = new RecoderMeetBean();
        recoderbean.setConfEntity(intentconfEntity);
        recoderbean.setConfId(confId);
        String json = GsonUtil.getJson(recoderbean);
        AppApplication.getDataProvider().getRecoderStatus(json, new AjaxCallBack<Object>() {

            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","会议录制状态："+o.toString());
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1){

                        JSONObject rows = object.getJSONObject("data");
                        String id = rows.getString("_id");
                        String conferenceRecordId = rows.getString("conferenceRecordId");

                                if(conferenceRecordId == null){
                                    recoderId = "";
                                }else{
                                    recoderId = conferenceRecordId;
                                }
//                        String recordingId = rows.getString("recordingId");
//                        String recordingTaskName = rows.getString("recordingTaskName");
                        String recordingStatus = rows.getString("recordingStatus");
                        if(TextUtils.equals(recordingStatus,"NotBegin")){
                            tv_recoder_status.setText("未开始");
                        }else if(TextUtils.equals(recordingStatus,"Ongoing")){
                            tv_recoder_status.setText("进行中");
                        }else if(TextUtils.equals(recordingStatus,"Finish")){
                            tv_recoder_status.setText("已完成");
                        }else if(TextUtils.equals(recordingStatus,"Pause")){
                            tv_recoder_status.setText("暂停");
                        }else{
                            tv_recoder_status.setText("");
                        }



                    }else{
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

    /**
     * 会议录制
     */
    private String[] titles = {"开始","暂停","继续","结束"};
    private void popWindow(Context context){
        View contentView1= LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pop_list, null, false);
        ListView list = (ListView)contentView1.findViewById(R.id.list);
        list.setAdapter(new PopListAdapter(context,titles));
        TextView tv_number = (TextView)contentView1.findViewById(R.id.tv_number);
        tv_number.setVisibility(View.GONE);
        TextView tv_poptitle = (TextView)contentView1.findViewById(R.id.tv_title);
        tv_poptitle.setText("会议录制");
        TextView tv_close = (TextView)contentView1.findViewById(R.id.tv_close);
        final PopupWindow pop1=new PopupWindow(contentView1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop1.setOutsideTouchable(true);
        pop1.setTouchable(true);

        pop1.showAtLocation(rl_recoder, Gravity.BOTTOM,0,0);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pop1.dismiss();

                if(id == 0){//开始
                    beginRecoder();
                }else if(id == 1){//暂停
                    pauseRecoder();
                }else if(id == 2){//继续
                    continueRecoder();
                }else if(id == 3){//结束
                    finishRecoder();
                }

            }
        });
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop1.dismiss();
            }
        });
    }

    private void finishRecoder() {
        RecoderMeetBean recoderbean = new RecoderMeetBean();
        recoderbean.setConfEntity(intentconfEntity);
        recoderbean.setConfId(confId);
        String json = GsonUtil.getJson(recoderbean);
        AppApplication.getDataProvider().finishRecoder(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","结束录制返回："+o.toString());
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1){
                        Toast.makeText(ControllActivityActivity.this, "结束录制成功", Toast.LENGTH_SHORT).show();

                        tv_recoder_status.setText("已结束");


                    }else{
                        Toast.makeText(ControllActivityActivity.this, "结束录制失败", Toast.LENGTH_SHORT).show();
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

    /**
     *
     */
    private void continueRecoder() {
        RecoderMeetBean recoderbean = new RecoderMeetBean();
        recoderbean.setConfEntity(intentconfEntity);
        recoderbean.setConfId(confId);
        String json = GsonUtil.getJson(recoderbean);
        AppApplication.getDataProvider().continueRecoder(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","继续录制返回："+o.toString());
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1){
                        Toast.makeText(ControllActivityActivity.this, "继续录制成功", Toast.LENGTH_SHORT).show();
                        tv_recoder_status.setText("进行中");


                    }else{
                        Toast.makeText(ControllActivityActivity.this, "继续录制失败", Toast.LENGTH_SHORT).show();
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

    /**
     * 暂停录制
     */
    private void pauseRecoder() {
        RecoderMeetBean recoderbean = new RecoderMeetBean();
        recoderbean.setConfEntity(intentconfEntity);
        recoderbean.setConfId(confId);
        String json = GsonUtil.getJson(recoderbean);
        AppApplication.getDataProvider().pauseRecoder(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","暂停录制返回："+o.toString());
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1){
                        Toast.makeText(ControllActivityActivity.this, "暂停录制成功", Toast.LENGTH_SHORT).show();
                        tv_recoder_status.setText("暂停");


                    }else{
                        Toast.makeText(ControllActivityActivity.this, "暂停录制失败", Toast.LENGTH_SHORT).show();
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

    /**
     * 开始录制
     */
    private void beginRecoder() {
        String state_text = tv_recoder_status.getText().toString().trim();
        if(TextUtils.equals(state_text,"进行中")){
            ToastUtil.toast(ControllActivityActivity.this,"录制中");
            return;
        }
        RecoderMeetBean recoderbean = new RecoderMeetBean();
        recoderbean.setConfEntity(intentconfEntity);
        recoderbean.setConfId(confId);
        String json = GsonUtil.getJson(recoderbean);
        AppApplication.getDataProvider().beginRecoder(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","开始录制返回："+o.toString());
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1){
                        Toast.makeText(ControllActivityActivity.this, "开始录制", Toast.LENGTH_SHORT).show();
                        tv_recoder_status.setText("进行中");


                    }else{
                        Toast.makeText(ControllActivityActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
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
        int  i =0;
        if(requestCode == 2){
            if(resultCode == 3){
                SerializableMap serializabmap = (SerializableMap)data.getSerializableExtra("item");
                Map<String, MyNodeBean> map = serializabmap.getMap();
                String [] strs = new String[map.size()];
                Iterator<Map.Entry<String, MyNodeBean>> iterator = map.entrySet().iterator();
                while(iterator.hasNext()){
                    Map.Entry<String, MyNodeBean> next = iterator.next();
                    MyNodeBean value = next.getValue();
                    String name = value.getName();
                    strs[i] = name;
                    i++;
                }

                YealinkApi.instance().meetInvite(strs);

            }
        }
    }













}
