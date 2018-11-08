package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.PublishMeetingActivity1;
import unicom.hand.redeagle.zhfy.adapter.PopListAdapter;
import unicom.hand.redeagle.zhfy.adapter.PopUserListAdapter;
import unicom.hand.redeagle.zhfy.bean.MeetDetail;
import unicom.hand.redeagle.zhfy.bean.MeetDetailListBean;
import unicom.hand.redeagle.zhfy.bean.MyNodeBean;
import unicom.hand.redeagle.zhfy.bean.PeopleBean;
import unicom.hand.redeagle.zhfy.bean.PeopleOnLine;
import unicom.hand.redeagle.zhfy.bean.PeopleOnLineBean;
import unicom.hand.redeagle.zhfy.bean.QueryLayoutBean;
import unicom.hand.redeagle.zhfy.bean.SaveLayoutBean;
import unicom.hand.redeagle.zhfy.bean.SerializableMap;
import unicom.hand.redeagle.zhfy.bean.layoutUser;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.GsonUtils;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

public class LayoutActivity extends Activity {
    private boolean isShowName = false;
    private ImageView iv_show_name;
    private ImageView iv_lun_xun;
    private RelativeLayout rl_layout_model;
    private RelativeLayout rl_model;
    private RelativeLayout rl_lunxun_type;
    private TextView tv_model_value;
    private TextView tv_avg_model;
    private TextView tv_view_lunxun;
    private String confId;
    private String intentconfEntity;
    private EditText tv_lunxun_time;
    private String conferenceLayoutId = "";
    private String  layoutType = "Average";

    private int layoutMaxView = 4;
    private int lastlayoutMaxView = 4;
    private String uid = "";
    private String userName = "";
    private String name = "";
    private String userEntity = "";
    private boolean enablePolling = false;
    private int pollingNumber = 11;
    private int pollingTimeInterval = 0;
    private boolean enableSpeechExcitation = false;
    private int speechExcitationTime = 0;
    private boolean osdEnable = false;
    private SaveLayoutBean saveLayoutBean;
    private LinearLayout ll_yyjl;
    private ImageView iv_video_jili;
    private EditText et_jili_time;
    private LinearLayout ll_dfqp;
    private LinearLayout ll_not_dfqp;
    private TextView tv_dfqp_name;
    private String pollingType = "Specified";
    private ImageView iv_dfqp;
    private LinearLayout ll_lunxun;
    layoutUser layoutuser;
    private List<MeetDetailListBean.Participants> intentparticipants;
    private List<PeopleOnLineBean> peopleOnLineBeenbeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        layoutuser = new layoutUser();
        saveLayoutBean = new SaveLayoutBean();
        ImageView iv_left = (ImageView)findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView tv_save = (TextView)findViewById(R.id.tv_save);
        tv_lunxun_time = (EditText)findViewById(R.id.tv_lunxun_time);
        et_jili_time = (EditText)findViewById(R.id.et_jili_time);
        confId = getIntent().getStringExtra("confId");
        intentconfEntity = getIntent().getStringExtra("confEntity");
        tv_model_value = (TextView)findViewById(R.id.tv_model_value);
        tv_avg_model = (TextView)findViewById(R.id.tv_avg_model);
        tv_view_lunxun = (TextView)findViewById(R.id.tv_view_lunxun);
        iv_show_name = (ImageView)findViewById(R.id.iv_show_name);
        iv_dfqp = (ImageView)findViewById(R.id.iv_dfqp);
        iv_video_jili = (ImageView)findViewById(R.id.iv_video_jili);
        ll_lunxun = (LinearLayout)findViewById(R.id.ll_lunxun);
        //显示会议名称
        iv_show_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(osdEnable){
                    osdEnable = false;
                    iv_show_name.setImageResource(R.drawable.switch_off);
                    iv_dfqp.setImageResource(R.drawable.switch_off);
                }else{
                    osdEnable = true;
                    iv_show_name.setImageResource(R.drawable.switch_on);
                    iv_dfqp.setImageResource(R.drawable.switch_on);
                }

            }
        });
        iv_dfqp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(osdEnable){
                    osdEnable = false;
                    iv_show_name.setImageResource(R.drawable.switch_off);
                    iv_dfqp.setImageResource(R.drawable.switch_off);
                }else{
                    osdEnable = true;
                    iv_show_name.setImageResource(R.drawable.switch_on);
                    iv_dfqp.setImageResource(R.drawable.switch_on);
                }

            }
        });

        iv_video_jili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(enableSpeechExcitation){
                    enableSpeechExcitation = false;
                    iv_video_jili.setImageResource(R.drawable.switch_off);
                }else{
                    enableSpeechExcitation = true;
                    iv_video_jili.setImageResource(R.drawable.switch_on);
                }
            }
        });

        ll_dfqp = (LinearLayout)findViewById(R.id.ll_dfqp);
        ll_not_dfqp = (LinearLayout)findViewById(R.id.ll_not_dfqp);

        ll_yyjl = (LinearLayout)findViewById(R.id.ll_yyjl);
        iv_lun_xun = (ImageView)findViewById(R.id.iv_lun_xun);
        //视频轮询
        iv_lun_xun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(osdEnable){
                    osdEnable = false;
                    iv_lun_xun.setImageResource(R.drawable.switch_off);
                    ll_lunxun.setVisibility(View.GONE);
                }else{
                    osdEnable = true;
                    iv_lun_xun.setImageResource(R.drawable.switch_on);
                    ll_lunxun.setVisibility(View.VISIBLE);
                }
                enablePolling();

            }
        });
        rl_layout_model = (RelativeLayout)findViewById(R.id.rl_layout_model);
        rl_model = (RelativeLayout)findViewById(R.id.rl_model);
        rl_lunxun_type = (RelativeLayout)findViewById(R.id.rl_lunxun_type);
        RelativeLayout rl_dfqp = (RelativeLayout)findViewById(R.id.rl_dfqp);
        rl_dfqp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(peopleOnLineBeenbeans != null && peopleOnLineBeenbeans.size()>0){
//                    popWindowUserList(LayoutActivity.this,intentparticipants);
                    Intent intent = new Intent(LayoutActivity.this, SelectMemberOnlineActivity.class);
                    intent.putExtra("selectnum","1");
                    List<layoutUser> intentuserlist = new ArrayList<layoutUser>();
                    intentuserlist.add(layoutuser);
                    intent.putExtra("selectitem",(Serializable) intentuserlist);
                    intent.putExtra("itemlist",(Serializable) peopleOnLineBeenbeans);
                    startActivityForResult(intent,3);
                }else{
                    Toast.makeText(LayoutActivity.this, "无人入会", Toast.LENGTH_SHORT).show();
                }
                

            }
        });
        tv_dfqp_name = (TextView)findViewById(R.id.tv_dfqp_name);
        //选择布局
        rl_layout_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popWindow1(LayoutActivity.this);
            }
        });
        //等分模式
        rl_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow2(LayoutActivity.this);
            }
        });

        //轮询方式
        rl_lunxun_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow3(LayoutActivity.this);
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });

        getData();
        getPeopleOnLine();
//        getTempLayout();
//        getJoinMember();

    }
    private void popWindowUserList(Context context,List<MeetDetailListBean.Participants> participants1){
        View contentView1= LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pop_list, null, false);
        ListView list = (ListView)contentView1.findViewById(R.id.list);
        list.setAdapter(new PopUserListAdapter(context,participants1));
        TextView tv_number = (TextView)contentView1.findViewById(R.id.tv_number);
        tv_number.setVisibility(View.GONE);
        TextView tv_poptitle = (TextView)contentView1.findViewById(R.id.tv_title);
        tv_poptitle.setText("选择人员");
        TextView tv_close = (TextView)contentView1.findViewById(R.id.tv_close);
        final PopupWindow pop1=new PopupWindow(contentView1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop1.setOutsideTouchable(true);
        pop1.setTouchable(true);

        pop1.showAtLocation(rl_layout_model, Gravity.BOTTOM,0,0);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pop1.dismiss();
                MeetDetailListBean.Participants itempart = (MeetDetailListBean.Participants)parent.getAdapter().getItem(position);
                if(itempart != null){
                    String name1 = itempart.getName();
                    layoutuser.setName(name1);
                tv_dfqp_name.setText(name1);
//                    saveLayoutBean.setName(name1);
                    String id1 = itempart.get_id();
                    layoutuser.setUid(id1);
                    String phone = itempart.getPhone();
                    layoutuser.setUserName(phone);
                    String entity = itempart.getEntity();
                    layoutuser.setUserEntity(entity);
//                    saveLayoutBean.setLayoutUser(layoutuser);
//                    saveLayoutBean.setUserName(name1);
//                    saveLayoutBean.setUid(id1);
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

    private void getJoinMember() {
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
                                intentparticipants = bean.getParticipants();

                                

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
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 3){
            SerializableMap serializabmap = (SerializableMap)data.getSerializableExtra("item");
            Map<String, MyNodeBean> map = serializabmap.getMap();
            Iterator<Map.Entry<String, MyNodeBean>> iterator = map.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, MyNodeBean> next = iterator.next();
                MyNodeBean value = next.getValue();
                String name1 = value.getName();
                tv_dfqp_name.setText(name1);
                layoutuser.setName(name1);
//                saveLayoutBean.setName(name1);
                String serialNumber = value.getSerialNumber();
//                saveLayoutBean.setUserName(serialNumber);
                layoutuser.setUserName(serialNumber);
                String uid = value.getUid();
                layoutuser.setUid(uid);
                layoutMaxView = 1;
                if(peopleOnLineBeenbeans != null && peopleOnLineBeenbeans.size()>0){
                    for (int i=0;i<peopleOnLineBeenbeans.size();i++){
                        PeopleOnLineBean peopleOnLineBean = peopleOnLineBeenbeans.get(i);
                        String id = peopleOnLineBean.getId();
                        if(TextUtils.equals(uid,id)){
                            String entity = peopleOnLineBean.getEntity();
                            if(entity == null){
                                entity = "1";
                            }
                            layoutuser.setUserEntity(entity);
                            break;
                        }

                    }

                }else{
                    layoutuser.setUserEntity("1");
                }


            }


        }

    }

    /**
     * 开启或者禁用轮询
     */
    private void enablePolling() {

    }

    /**
     * 获取入伙人员
     */
    private void getPeopleOnLine() {
        PeopleOnLine peoplebean = new PeopleOnLine();
        peoplebean.setConfEntity(intentconfEntity);
        peoplebean.setConfId(confId);
        String json = GsonUtil.getJson(peoplebean);
        AppApplication.getDataProvider().getPeopleOnLine(json, new AjaxCallBack<Object>() {

            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","获取入会人员："+o.toString());
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1){
                        JSONArray data = object.getJSONArray("data");
                        peopleOnLineBeenbeans = GsonUtils.getBeans(data.toString(), PeopleOnLineBean.class);
//                        Toast.makeText(LayoutActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    }else{
//                        Toast.makeText(LayoutActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
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

    private void submitData() {

        saveLayoutBean.setConferenceRecordId(confId);
        saveLayoutBean.setConfEntity(intentconfEntity);
        saveLayoutBean.setConferenceLayoutId(conferenceLayoutId);
//        saveLayoutBean.setName(layoutuser.getName());
//        saveLayoutBean.setUid(layoutuser.getUid());
//        saveLayoutBean.setUserEntity(layoutuser.getUserEntity());
//        saveLayoutBean.setUserName(layoutuser.getUserName());
//        List<layoutUser> submituser = new ArrayList<>();
//        submituser.add(layoutuser);
        saveLayoutBean.setLayoutUser(layoutuser);
        String lunxuntime = tv_lunxun_time.getText().toString().trim();
        if(TextUtils.isEmpty(lunxuntime)){
            Toast.makeText(this, "请先输入轮询间隔", Toast.LENGTH_SHORT).show();
            return;
        }
        pollingTimeInterval = Integer.parseInt(lunxuntime);
        if(TextUtils.equals(layoutType,"1+N")){
            String jilitime = et_jili_time.getText().toString().trim();
            if(TextUtils.isEmpty(jilitime)){
                Toast.makeText(this, "请先输入激励时间", Toast.LENGTH_SHORT).show();
                return;
            }
            speechExcitationTime = Integer.parseInt(jilitime);
        }
        if(layoutMaxView == -1){
            if(TextUtils.equals(layoutType,"Average")){
                layoutMaxView = 4;
            }else if(TextUtils.equals(layoutType,"1+N")){
                layoutMaxView = 7;
            }
        }
        saveLayoutBean.setLayoutType(layoutType);
        saveLayoutBean.setLayoutMaxView(layoutMaxView);
        saveLayoutBean.setEnablePolling(enablePolling);
        saveLayoutBean.setPollingType(pollingType);
        saveLayoutBean.setPollingNumber(pollingNumber);
        saveLayoutBean.setPollingTimeInterval(pollingTimeInterval);
        saveLayoutBean.setEnableSpeechExcitation(enableSpeechExcitation);
        saveLayoutBean.setSpeechExcitationTime(speechExcitationTime);
        saveLayoutBean.setOsdEnable(osdEnable);
        String json = GsonUtil.getJson(saveLayoutBean);
        AppApplication.getDataProvider().saveSimpleLayout(json, new AjaxCallBack<Object>() {

            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","保存布局："+o.toString());
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1){
                        Toast.makeText(LayoutActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(LayoutActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
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

    private void getTempLayout() {
        QueryLayoutBean querybean = new QueryLayoutBean();
        querybean.setConfEntity(intentconfEntity);
        querybean.setConferenceRecordId(confId);
        String json = GsonUtil.getJson(querybean);
        AppApplication.getDataProvider().getTempLayout(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","获取布局模板返回内容："+o.toString());
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }

    private void getData() {
        QueryLayoutBean querybean = new QueryLayoutBean();
        querybean.setConfEntity(intentconfEntity);
        querybean.setConferenceRecordId(confId);
        String json = GsonUtil.getJson(querybean);

        AppApplication.getDataProvider().getLayout(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","获取布局返回内容："+o.toString());
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1){
                        JSONObject data = object.getJSONObject("data");
                        boolean osdVisible1 = data.getBoolean("osdVisible");//
                        conferenceLayoutId = data.getString("conferenceLayoutId");


                        if(!data.isNull("simpleLayout")){
                            JSONObject simpleLayout = data.getJSONObject("simpleLayout");
                            int pollingNumber1 = simpleLayout.getInt("pollingNumber");
                            pollingNumber = pollingNumber1;
                            String pollingType1 = simpleLayout.getString("pollingType");
                            pollingType = pollingType1;
//                            if(TextUtils.equals(pollingType,"Specified")){
//                                tv_view_lunxun.setText(titles3[0]);
//                            }else if(TextUtils.equals(pollingType,"All")){
//                                tv_view_lunxun.setText(titles3[1]);
//                            }

                            int pollingTimeInterval1 = simpleLayout.getInt("pollingTimeInterval");//轮询的时间间隔
                            int speechExcitationTime1 = simpleLayout.getInt("speechExcitationTime");//语音激励时间
                            boolean osdEnable1 = simpleLayout.getBoolean("osdEnable");//显示会场名称
                            osdEnable = osdEnable1;
                            if(!osdEnable){
                                iv_lun_xun.setImageResource(R.drawable.switch_off);
                                ll_lunxun.setVisibility(View.GONE);
                            }else{
                                iv_lun_xun.setImageResource(R.drawable.switch_on);
                                ll_lunxun.setVisibility(View.VISIBLE);
                            }
                            tv_lunxun_time.setText(pollingTimeInterval1+"");
                            pollingTimeInterval = pollingTimeInterval1;
                            tv_lunxun_time.setText(pollingTimeInterval+"");
                            speechExcitationTime = speechExcitationTime1;
                            et_jili_time.setText(speechExcitationTime+"");
                            if(!osdEnable1){
                                iv_show_name.setImageResource(R.drawable.switch_off);
                                iv_dfqp.setImageResource(R.drawable.switch_off);
                            }else{
                                iv_show_name.setImageResource(R.drawable.switch_on);
                                iv_dfqp.setImageResource(R.drawable.switch_on);
                            }
                            boolean enableSpeechExcitation1 = simpleLayout.getBoolean("enableSpeechExcitation");//是否开启语音激励
                            enableSpeechExcitation = enableSpeechExcitation1;
                            if(!enableSpeechExcitation){
                                iv_video_jili.setImageResource(R.drawable.switch_off);
                            }else{
                                iv_video_jili.setImageResource(R.drawable.switch_on);
                            }
                            String layoutType1 = simpleLayout.getString("layoutType");
                            layoutType = layoutType1;
                            tv_avg_model.setText(UIUtils.getLayoutMode(layoutType1));
                            if(TextUtils.equals(layoutType,"1+N")){
                                ll_yyjl.setVisibility(View.VISIBLE);
                            }else{
                                ll_yyjl.setVisibility(View.GONE);
                            }
                            if(TextUtils.equals(layoutType,"Oneself")){
                                ll_dfqp.setVisibility(View.VISIBLE);
                                ll_not_dfqp.setVisibility(View.GONE);
                            }else{
                                ll_dfqp.setVisibility(View.GONE);
                                ll_not_dfqp.setVisibility(View.VISIBLE);
                            }

                            int layoutMaxView1 = simpleLayout.getInt("layoutMaxView");
                            layoutMaxView = layoutMaxView1;
                            lastlayoutMaxView = layoutMaxView1;
                            setModeValue(layoutMaxView1);


//                            String pollingType1 = simpleLayout.getString("pollingType");
//                            pollingType = pollingType1;
                            tv_view_lunxun.setText(UIUtils.getLunMode(pollingType1));
                            if(!simpleLayout.isNull("layoutUser")){
                                JSONObject layoutUser = simpleLayout.getJSONObject("layoutUser");
                                String uid = layoutUser.getString("uid");
                                layoutuser.setUid(uid);
                                String userName = layoutUser.getString("userName");
                                layoutuser.setUserName(userName);
                                tv_dfqp_name.setText(userName);
                                String name = layoutUser.getString("name");
                                layoutuser.setName(name);
                                String userEntity = layoutUser.getString("userEntity");
                                layoutuser.setUserEntity(userEntity);
                                String permission = layoutUser.getString("permission");
                            }


                        }else {
                            ll_lunxun.setVisibility(View.GONE);
                            if(TextUtils.equals(layoutType,"1+N")) {
                                tv_model_value.setText("1+7");
                            }else if(TextUtils.equals(layoutType,"Average")) {
                                tv_model_value.setText("4*4");
                            }
                            et_jili_time.setText(speechExcitationTime+"");
                            tv_lunxun_time.setText(pollingTimeInterval+"");
                            et_jili_time.setText(speechExcitationTime+"");
                        }


                    }else{
                        JSONObject rows = object.getJSONObject("error");
                        String msg = rows.getString("msg");
                        Toast.makeText(LayoutActivity.this, msg, Toast.LENGTH_SHORT).show();
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

    private void setModeValue(int layoutMaxView1) {
        if(TextUtils.equals(layoutType,"1+N")){
            if(layoutMaxView == 0){
                tv_model_value.setText(titles1n[0]);
            }else if(layoutMaxView == 4){
                tv_model_value.setText(titles1n[1]);
            }else if(layoutMaxView == 7){
                tv_model_value.setText(titles1n[2]);
            }else if(layoutMaxView == 9){
                tv_model_value.setText(titles1n[3]);

            }else if(layoutMaxView == 12){
                tv_model_value.setText(titles1n[4]);

            }else if(layoutMaxView == 16){
                tv_model_value.setText(titles1n[5]);

            }else if(layoutMaxView == 20){
                tv_model_value.setText(titles1n[6]);
            }
        }else if(TextUtils.equals(layoutType,"Average")){
            if(layoutMaxView == 2){//2

                tv_model_value.setText(titles2[0]);
            }else if(layoutMaxView == 3){
                tv_model_value.setText(titles2[1]);
            }else if(layoutMaxView == 4){//4
                tv_model_value.setText(titles2[2]);
            }else if(layoutMaxView == 5){//5
                tv_model_value.setText(titles2[3]);
            }else if(layoutMaxView == 6){//6
                tv_model_value.setText(titles2[4]);
            }else if(layoutMaxView == 7){//7
                tv_model_value.setText(titles2[5]);
            }
        }

    }


    private String[] titles1 = {"等分模式","1+N模式","单方全屏"};
    private void popWindow1(Context context){
        View contentView1= LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pop_list, null, false);
        ListView list = (ListView)contentView1.findViewById(R.id.list);
        list.setAdapter(new PopListAdapter(context,titles1));
        TextView tv_number = (TextView)contentView1.findViewById(R.id.tv_number);
        tv_number.setVisibility(View.GONE);
        TextView tv_poptitle = (TextView)contentView1.findViewById(R.id.tv_title);
        tv_poptitle.setText("选择布局");
        TextView tv_close = (TextView)contentView1.findViewById(R.id.tv_close);
        final PopupWindow pop1=new PopupWindow(contentView1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop1.setOutsideTouchable(true);
        pop1.setTouchable(true);

        pop1.showAtLocation(rl_layout_model, Gravity.BOTTOM,0,0);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pop1.dismiss();
                tv_avg_model.setText(titles1[position]);
                if(id == 0){//等分
                    layoutType = "Average";
                    layoutMaxView = 4;
                }else if(id == 1){//1+N
                    layoutType = "1+N";
                    layoutMaxView = 7;
                }else if(id == 2){//单方全屏
                    layoutType = "Oneself";
                    layoutMaxView = lastlayoutMaxView;
                }

                setModeValue(layoutMaxView);
                if(TextUtils.equals(layoutType,"1+N")){
                    ll_yyjl.setVisibility(View.VISIBLE);
                }else{
                    ll_yyjl.setVisibility(View.GONE);
                }
                if(TextUtils.equals(layoutType,"Oneself")){
                    ll_dfqp.setVisibility(View.VISIBLE);
                    ll_not_dfqp.setVisibility(View.GONE);
                }else{
                    layoutMaxView = -1;
                    ll_dfqp.setVisibility(View.GONE);
                    ll_not_dfqp.setVisibility(View.VISIBLE);
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
    private String[] titles2 = {"2*2","3*3","4*4","5*5","6*6","7*7"};
    private String[] titles1n = {"1+0","1+4","1+7","1+9","1+12","1+16","1+20"};
    private void popWindow2(Context context){
        if(TextUtils.equals(layoutType,"1+N")){
            View contentView1= LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pop_list, null, false);
            ListView list = (ListView)contentView1.findViewById(R.id.list);
            list.setAdapter(new PopListAdapter(context,titles1n));
            TextView tv_number = (TextView)contentView1.findViewById(R.id.tv_number);
            tv_number.setVisibility(View.GONE);
            TextView tv_poptitle = (TextView)contentView1.findViewById(R.id.tv_title);
            tv_poptitle.setText("选择模式");
            TextView tv_close = (TextView)contentView1.findViewById(R.id.tv_close);
            final PopupWindow pop1=new PopupWindow(contentView1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            pop1.setOutsideTouchable(true);
            pop1.setTouchable(true);

            pop1.showAtLocation(rl_layout_model, Gravity.BOTTOM,0,0);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pop1.dismiss();
                    tv_model_value.setText(titles1n[position]);
                    if(TextUtils.equals(layoutType,"1+N")){
                        if(id == 0){//2

                            layoutMaxView = 0;
                        }else if(id == 1){//3
                            layoutMaxView = 4;
                        }else if(id == 2){//4
                            layoutMaxView = 7;
                        }else if(id == 3){//5
                            layoutMaxView = 9;
                        }else if(id == 4){//6
                            layoutMaxView = 12;
                        }else if(id == 5){//7
                            layoutMaxView = 16;
                        }else if(id == 6){//7
                            layoutMaxView = 20;
                        }
                    }


                }
            });
            tv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pop1.dismiss();
                }
            });
        }else if(TextUtils.equals(layoutType,"Average")){
            View contentView1= LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pop_list, null, false);
            ListView list = (ListView)contentView1.findViewById(R.id.list);
            list.setAdapter(new PopListAdapter(context,titles2));
            TextView tv_number = (TextView)contentView1.findViewById(R.id.tv_number);
            tv_number.setVisibility(View.GONE);
            TextView tv_poptitle = (TextView)contentView1.findViewById(R.id.tv_title);
            tv_poptitle.setText("选择模式");
            TextView tv_close = (TextView)contentView1.findViewById(R.id.tv_close);
            final PopupWindow pop1=new PopupWindow(contentView1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            pop1.setOutsideTouchable(true);
            pop1.setTouchable(true);

            pop1.showAtLocation(rl_layout_model, Gravity.BOTTOM,0,0);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pop1.dismiss();
                    tv_model_value.setText(titles2[position]);
                    if(TextUtils.equals(layoutType,"Average")){
                        if(id == 0){//2

                            layoutMaxView = 2;
                        }else if(id == 1){//3
                            layoutMaxView = 3;
                        }else if(id == 2){//4
                            layoutMaxView = 4;
                        }else if(id == 3){//5
                            layoutMaxView = 5;
                        }else if(id == 4){//6
                            layoutMaxView = 6;
                        }else if(id == 5){//7
                            layoutMaxView = 7;
                        }
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


    }


    private String[] titles3 = {"单张视图轮询","全屏轮询"};
    private void popWindow3(Context context){
        View contentView1= LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pop_list, null, false);
        ListView list = (ListView)contentView1.findViewById(R.id.list);
        list.setAdapter(new PopListAdapter(context,titles3));
        TextView tv_number = (TextView)contentView1.findViewById(R.id.tv_number);
        tv_number.setVisibility(View.GONE);
        TextView tv_poptitle = (TextView)contentView1.findViewById(R.id.tv_title);
        tv_poptitle.setText("轮询方式");
        TextView tv_close = (TextView)contentView1.findViewById(R.id.tv_close);
        final PopupWindow pop1=new PopupWindow(contentView1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop1.setOutsideTouchable(true);
        pop1.setTouchable(true);

        pop1.showAtLocation(rl_layout_model, Gravity.BOTTOM,0,0);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pop1.dismiss();
                tv_view_lunxun.setText(titles3[position]);
                if(id == 0){//2
                    pollingType = "Specified";
                }else if(id == 1){//3
                    pollingType = "All";
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



}
