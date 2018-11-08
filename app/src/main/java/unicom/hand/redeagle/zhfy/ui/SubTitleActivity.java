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

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.adapter.PopListAdapter;
import unicom.hand.redeagle.zhfy.adapter.PopUserListAdapter;
import unicom.hand.redeagle.zhfy.bean.EnableBannerBean;
import unicom.hand.redeagle.zhfy.bean.EnableSubTitleBean;
import unicom.hand.redeagle.zhfy.bean.MeetDetail;
import unicom.hand.redeagle.zhfy.bean.MeetDetailListBean;
import unicom.hand.redeagle.zhfy.bean.MyNodeBean;
import unicom.hand.redeagle.zhfy.bean.PeopleOnLine;
import unicom.hand.redeagle.zhfy.bean.PeopleOnLineBean;
import unicom.hand.redeagle.zhfy.bean.QueryLayoutBean;
import unicom.hand.redeagle.zhfy.bean.SaveSubTitleBean;
import unicom.hand.redeagle.zhfy.bean.SerializableMap;
import unicom.hand.redeagle.zhfy.bean.layoutUser;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.GsonUtils;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

public class SubTitleActivity extends Activity {

    private RelativeLayout rl_layout_gravity;
    private TextView tv_gravity;
    private RelativeLayout rl_zm_type;
    private TextView tv_zm_tag;
    private String intentconfId;
    private String intentconfEntity;
    private String showpos = "";
    private boolean isShowSwitch = false;
    private ImageView iv_begin;
    private EditText et_duration;
    private String subtitleType = "";
    private String conferenceSubTitleId = "";
    private LinearLayout ll_ori;
    private RelativeLayout rl_duration;
    private RelativeLayout rl_roll_num;
    private RelativeLayout rl_roll_ori;
    private TextView tv_roll_num;
    private TextView tv_roll_ori;

    private int intervalTime = 3;//间隔时间
    private int repeatTimes = 2;//重复次数
    private String rollDirection = "RightToLeft";
    private TextView tv_save;
    private EditText et_content;
    private int durationTime = 5;
    private TextView tv_send;
    private TextView tv_app_obj;
//    private List<MeetDetailListBean.Participants> participants;
    private RelativeLayout rl_qydx;
    private List<PeopleOnLineBean> peopleOnLineBeenbeans;
    private List<layoutUser> userList;

    private List<MyNodeBean> selectuserList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_title);
        ImageView iv_left = (ImageView)findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        selectuserList = new ArrayList<>();
        userList = new ArrayList<>();
        intentconfId = getIntent().getStringExtra("confId");
        intentconfEntity = getIntent().getStringExtra("confEntity");

        et_content = (EditText)findViewById(R.id.et_content);
        ll_ori = (LinearLayout) findViewById(R.id.ll_ori);
        rl_layout_gravity = (RelativeLayout)findViewById(R.id.rl_layout_gravity);
        tv_gravity = (TextView)findViewById(R.id.tv_gravity);
        rl_layout_gravity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow1(SubTitleActivity.this);
            }
        });
        rl_qydx = (RelativeLayout)findViewById(R.id.rl_qydx);
        tv_send = (TextView)findViewById(R.id.tv_send);
        tv_save = (TextView)findViewById(R.id.tv_save);
        tv_app_obj = (TextView)findViewById(R.id.tv_app_obj);
        tv_roll_num = (TextView)findViewById(R.id.tv_roll_num);
        tv_roll_ori = (TextView)findViewById(R.id.tv_roll_ori);
        rl_roll_num = (RelativeLayout)findViewById(R.id.rl_roll_num);
        rl_roll_ori = (RelativeLayout)findViewById(R.id.rl_roll_ori);
        rl_duration = (RelativeLayout)findViewById(R.id.rl_duration);
        rl_zm_type = (RelativeLayout)findViewById(R.id.rl_zm_type);
        tv_zm_tag = (TextView)findViewById(R.id.tv_zm_tag);
        rl_zm_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow2(SubTitleActivity.this);
            }
        });
        //滚动方向
        rl_roll_ori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow4(SubTitleActivity.this);
            }
        });

        //滚动次数
        rl_roll_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow3(SubTitleActivity.this);
            }
        });
        et_duration = (EditText)findViewById(R.id.tv_duration);
        iv_begin = (ImageView)findViewById(R.id.iv_begin);
        iv_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isShowSwitch){
                    isShowSwitch = true;
                    iv_begin.setImageResource(R.drawable.switch_on);
                }else{
                    isShowSwitch = false;
                    iv_begin.setImageResource(R.drawable.switch_off);
                }
                enableSubTitle();

            }
        });


        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSubTitle();
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSubTitle();
            }
        });
        rl_qydx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(peopleOnLineBeenbeans != null && peopleOnLineBeenbeans.size()>0){
//                    popWindowUserList(LayoutActivity.this,intentparticipants);
                    Intent intent = new Intent(SubTitleActivity.this, SelectMemberOnlineActivity.class);
                    intent.putExtra("selectnum","0");
                    intent.putExtra("itemlist",(Serializable) peopleOnLineBeenbeans);
                    intent.putExtra("selectitem",(Serializable) userList);
                    intent.putExtra("defaultitem",(Serializable) selectuserList);
                    startActivityForResult(intent,3);
                }else{
                    Toast.makeText(SubTitleActivity.this, "无人入会", Toast.LENGTH_SHORT).show();
                }

            }
        });
        getSubTitlt();
        getPeopleOnLine();
//        getJoinMember();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3){
            if(resultCode == 3){
                if(userList != null && userList.size()>0){
                    userList.clear();
                }

                if(selectuserList != null && selectuserList.size()>0){
                    selectuserList.clear();
                }

                SerializableMap serializabmap = (SerializableMap)data.getSerializableExtra("item");

                Map<String, MyNodeBean> map = serializabmap.getMap();
                Set<Map.Entry<String, MyNodeBean>> entries = map.entrySet();

                Iterator<Map.Entry<String, MyNodeBean>> iterator = entries.iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, MyNodeBean> next = iterator.next();
                    String key = next.getKey();
                    MyNodeBean myNodeBean = map.get(key);
                    selectuserList.add(myNodeBean);
                    layoutUser layoutuser = new layoutUser();
                    String name = myNodeBean.getName();
                    layoutuser.setName(name);
                    String serialNumber = myNodeBean.getSerialNumber();
                    String uid1 = myNodeBean.getUid();
                    layoutuser.setUserName(serialNumber);
                    if(peopleOnLineBeenbeans != null && peopleOnLineBeenbeans.size()>0){
                        for (int i=0;i<peopleOnLineBeenbeans.size();i++){
                            PeopleOnLineBean peopleOnLineBean = peopleOnLineBeenbeans.get(i);
                            String id = peopleOnLineBean.getId();

                            if(TextUtils.equals(uid1,id)){
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
                    String uid = myNodeBean.getUid();
                    layoutuser.setUid(uid);
                    userList.add(layoutuser);
//                    myNodeBean.get
                }
//                Log.e("aaa","启用对象;"+userList.size());
                setLayoutUser(userList);


            }
        }
    }

    /**
     * 获取入伙人员
     */
    private void getPeopleOnLine() {
        PeopleOnLine peoplebean = new PeopleOnLine();
        peoplebean.setConfEntity(intentconfEntity);
        peoplebean.setConfId(intentconfId);
        Log.e("ccc",intentconfEntity);
        Log.e("ccc",intentconfId);
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
    private void getJoinMember() {
        MeetDetail meetdetail = new MeetDetail();
        meetdetail.setConfEntity("1");
        meetdetail.setConfId(intentconfId);
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
//                                participants = bean.getParticipants();


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
    private void popWindowUserList(Context context,List<MeetDetailListBean.Participants> participants1){
        View contentView1= LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pop_list, null, false);
        ListView list = (ListView)contentView1.findViewById(R.id.list);
        list.setAdapter(new PopUserListAdapter(context,participants1));
        TextView tv_number = (TextView)contentView1.findViewById(R.id.tv_number);
        tv_number.setVisibility(View.GONE);
        TextView tv_poptitle = (TextView)contentView1.findViewById(R.id.tv_title);
        tv_poptitle.setText("人员列表");
        TextView tv_close = (TextView)contentView1.findViewById(R.id.tv_close);
        final PopupWindow pop1=new PopupWindow(contentView1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop1.setOutsideTouchable(true);
        pop1.setTouchable(true);

        pop1.showAtLocation(rl_layout_gravity, Gravity.BOTTOM,0,0);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pop1.dismiss();
//                MeetDetailListBean.Participants itempart = (MeetDetailListBean.Participants)parent.getAdapter().getItem(position);
//                if(itempart != null){
//                    String name1 = itempart.getName();
//                    tv_dfqp_name.setText(name1);
//                    saveLayoutBean.setName(name1);
//                    String id1 = itempart.get_id();
//                    saveLayoutBean.setUserName(name1);
//                    saveLayoutBean.setUid(id1);
//                }


            }
        });
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop1.dismiss();
            }
        });
    }
    private void sendSubTitle() {
//        if(!isShowSwitch){
//            Toast.makeText(this, "请先启用字幕", Toast.LENGTH_SHORT).show();
//            return;
//        }
        SaveSubTitleBean enablebean = new SaveSubTitleBean();
        enablebean.setConferenceRecordId(intentconfId);
        enablebean.setConferenceSubtitleId(conferenceSubTitleId);
        enablebean.setConfEntity(intentconfEntity);
        String subtitlecontent = et_content.getText().toString().trim();
        if(TextUtils.isEmpty(subtitlecontent)){
            Toast.makeText(this, "请先输入字幕内容", Toast.LENGTH_SHORT).show();
            return;
        }
        enablebean.setSubtitleContent(subtitlecontent);
        enablebean.setSubtitleType(subtitleType);
        enablebean.setRepeatTimes(repeatTimes);
        enablebean.setIntervalTime(intervalTime);
        enablebean.setDurationTime(durationTime);
//        enablebean.setUid("");
//        enablebean.setUserName("");
//        enablebean.setName("");
//        enablebean.setUserEntity("");
        enablebean.setUserList(userList);
        enablebean.setEnable(isShowSwitch);
        enablebean.setPosition(showpos);
        enablebean.setRollDirection(rollDirection);
        String json = GsonUtil.getJson(enablebean);
        AppApplication.getDataProvider().sendSubTitle(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","保存字幕："+o.toString());

                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1) {
                        Toast.makeText(SubTitleActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SubTitleActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(SubTitleActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void saveSubTitle() {
        SaveSubTitleBean enablebean = new SaveSubTitleBean();
        enablebean.setConferenceRecordId(intentconfId);
        enablebean.setConferenceSubtitleId(conferenceSubTitleId);
        enablebean.setConfEntity(intentconfEntity);
        String subtitlecontent = et_content.getText().toString().trim();
        if(TextUtils.isEmpty(subtitlecontent)){
            Toast.makeText(this, "请先输入字幕内容", Toast.LENGTH_SHORT).show();
            return;
        }
        String et_duration1 = et_duration.getText().toString().trim();
        if(TextUtils.isEmpty(et_duration1)){
            Toast.makeText(this, "请先输入显示时长", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            durationTime = Integer.parseInt(et_duration1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            durationTime = 5;
        }
        enablebean.setSubtitleContent(subtitlecontent);
        enablebean.setSubtitleType(subtitleType);
        enablebean.setRepeatTimes(repeatTimes);
        enablebean.setIntervalTime(intervalTime);
        enablebean.setDurationTime(durationTime);

//        enablebean.setUid("");
//        enablebean.setUserName("");
//        enablebean.setName("");
//        enablebean.setUserEntity("");
//        enablebean.setUserList();
        enablebean.setEnable(isShowSwitch);
        enablebean.setPosition(showpos);
        enablebean.setRollDirection(rollDirection);
        enablebean.setUserList(userList);
        String json = GsonUtil.getJson(enablebean);
        AppApplication.getDataProvider().saveSubTitle(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","保存字幕："+o.toString());

                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1) {
                        Toast.makeText(SubTitleActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SubTitleActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(SubTitleActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void enableSubTitle() {
        EnableSubTitleBean enablebean = new EnableSubTitleBean();
        enablebean.setConferenceRecordId(intentconfId);
        enablebean.setConferenceBannerId(conferenceSubTitleId);
        enablebean.setConfEntity(intentconfEntity);
        enablebean.setEnable(isShowSwitch);
        String json = GsonUtil.getJson(enablebean);
        AppApplication.getDataProvider().enableSubTitle(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","开启禁用字幕："+o.toString());

                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1) {
                        if(isShowSwitch){
                            tv_send.setEnabled(true);
                            tv_send.setTextColor(UIUtils.getColor(R.color.red));
                            iv_begin.setImageResource(R.drawable.switch_on);
                        }else{
                            tv_send.setEnabled(false);
                            tv_send.setTextColor(UIUtils.getColor(R.color.gray));
                            iv_begin.setImageResource(R.drawable.switch_off);
                        }
                        Toast.makeText(SubTitleActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    }else{
                        isShowSwitch = !isShowSwitch;
                        if(isShowSwitch){
                            tv_send.setEnabled(true);
                            tv_send.setTextColor(UIUtils.getColor(R.color.red));
                            iv_begin.setImageResource(R.drawable.switch_on);
                        }else{
                            tv_send.setEnabled(false);
                            tv_send.setTextColor(UIUtils.getColor(R.color.gray));
                            iv_begin.setImageResource(R.drawable.switch_off);
                        }
                        Toast.makeText(SubTitleActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    isShowSwitch = !isShowSwitch;
                    if(isShowSwitch){
                        tv_send.setEnabled(true);
                        tv_send.setTextColor(UIUtils.getColor(R.color.red));
                        iv_begin.setImageResource(R.drawable.switch_on);
                    }else{
                        tv_send.setTextColor(UIUtils.getColor(R.color.gray));
                        tv_send.setEnabled(false);
                        iv_begin.setImageResource(R.drawable.switch_off);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(SubTitleActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                isShowSwitch = !isShowSwitch;
                if(isShowSwitch){
                    iv_begin.setImageResource(R.drawable.switch_on);
                }else{
                    iv_begin.setImageResource(R.drawable.switch_off);
                }
            }
        });

    }

    private void getSubTitlt() {

        QueryLayoutBean querybean = new QueryLayoutBean();
        querybean.setConfEntity(intentconfEntity);
        querybean.setConferenceRecordId(intentconfId);
        String json = GsonUtil.getJson(querybean);
        AppApplication.getDataProvider().getSubTitle(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","获取字幕："+o.toString());

                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1) {
                        JSONObject data = object.getJSONObject("data");
                        String position = data.getString("position");
                        showpos = position;
                        tv_gravity.setText(UIUtils.getBannerPos(position));

                        if(!data.isNull("userList")){
                            JSONArray userListarray = data.getJSONArray("userList");
                            List<layoutUser> beans = GsonUtils.getBeans(userListarray.toString(), layoutUser.class);
                            if(beans != null && beans.size()>0){
                                setLayoutUser(beans);
                                userList.addAll(beans);
                            }
                        }



                        boolean enable = data.getBoolean("enable");
                        isShowSwitch = enable;
                        if(enable){
                            tv_send.setEnabled(true);
                            tv_send.setTextColor(UIUtils.getColor(R.color.red));
                            iv_begin.setImageResource(R.drawable.switch_on);
                        }else{
                            tv_send.setEnabled(false);
//                            tv_send.setBackgroundColor(UIUtils.getColor(R.color.light_gray));
                            tv_send.setTextColor(UIUtils.getColor(R.color.gray));
                            iv_begin.setImageResource(R.drawable.switch_off);
                        }

//                        if(!data.isNull("userList")){
//
//                        }

                        String subtitleType1 = data.getString("subtitleType");
                        subtitleType = subtitleType1;
                        tv_zm_tag.setText(UIUtils.getSubTitleType(subtitleType1));

                        if(TextUtils.equals(subtitleType1,"Static")){
                            rl_duration.setVisibility(View.VISIBLE);
                            ll_ori.setVisibility(View.GONE);
                        }else if(TextUtils.equals(subtitleType1,"Dynamic")){
                            rl_duration.setVisibility(View.GONE);
                            ll_ori.setVisibility(View.VISIBLE);
                        }


                        int durationTime1 = data.getInt("durationTime");
                        et_duration.setText(durationTime1+"");

                        String conferenceSubtitleId1 = data.getString("conferenceSubtitleId");
                        conferenceSubTitleId = conferenceSubtitleId1;

                        String rollDirection1 = data.getString("rollDirection");
                        if(TextUtils.equals(rollDirection1,"RightToLeft")){
                            tv_roll_ori.setText("向左");
                        }else if(TextUtils.equals(rollDirection1,"LeftToRight")){
                            tv_roll_ori.setText("向右");
                        }
                        rollDirection = rollDirection1;
                        int repeatTimes1 = data.getInt("repeatTimes");
                        tv_roll_num.setText(repeatTimes1+"");
                        repeatTimes = repeatTimes1;
                        int intervalTime1 = data.getInt("intervalTime");
                        intervalTime  =intervalTime1;
                        String subtitleContent1 = data.getString("subtitleContent");
                        if(!TextUtils.equals(subtitleContent1,"null") && subtitleContent1.length()>0){
                            et_content.setText(subtitleContent1);
                            et_content.setSelection(subtitleContent1.length());
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

    private void setLayoutUser(List<layoutUser> beans) {
        String str = "";
        for (int i=0;i<beans.size();i++){
            layoutUser layoutUser = beans.get(i);
            String name = layoutUser.getName();
            str+=name+";";
        }
        tv_app_obj.setText(str);
    }

    private String[] titles2 = {"静态字幕","动态字幕"};
    private void popWindow2(Context context){
        View contentView1= LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pop_list, null, false);
        ListView list = (ListView)contentView1.findViewById(R.id.list);
        list.setAdapter(new PopListAdapter(context,titles2));
        TextView tv_number = (TextView)contentView1.findViewById(R.id.tv_number);
        tv_number.setVisibility(View.GONE);
        TextView tv_poptitle = (TextView)contentView1.findViewById(R.id.tv_title);
        tv_poptitle.setText("字幕类型");
        TextView tv_close = (TextView)contentView1.findViewById(R.id.tv_close);
        final PopupWindow pop1=new PopupWindow(contentView1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop1.setOutsideTouchable(true);
        pop1.setTouchable(true);

        pop1.showAtLocation(rl_zm_type, Gravity.BOTTOM,0,0);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pop1.dismiss();
                tv_zm_tag.setText(titles2[position]);
                if(id == 0){//等分
                    subtitleType = "Static";
                    rl_duration.setVisibility(View.VISIBLE);
                    ll_ori.setVisibility(View.GONE);
                }else if(id == 1){//1+N
                    subtitleType = "Dynamic";
                    rl_duration.setVisibility(View.GONE);
                    ll_ori.setVisibility(View.VISIBLE);
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
    private String[] titles4 = {"向左","向右"};
    private void popWindow4(Context context){
        View contentView1= LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pop_list, null, false);
        ListView list = (ListView)contentView1.findViewById(R.id.list);
        list.setAdapter(new PopListAdapter(context,titles4));
        TextView tv_number = (TextView)contentView1.findViewById(R.id.tv_number);
        tv_number.setVisibility(View.GONE);
        TextView tv_poptitle = (TextView)contentView1.findViewById(R.id.tv_title);
        tv_poptitle.setText("滚动方向");
        TextView tv_close = (TextView)contentView1.findViewById(R.id.tv_close);
        final PopupWindow pop1=new PopupWindow(contentView1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop1.setOutsideTouchable(true);
        pop1.setTouchable(true);

        pop1.showAtLocation(rl_layout_gravity, Gravity.BOTTOM,0,0);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pop1.dismiss();
                tv_roll_ori.setText(titles4[position]);
                if(position == 0){
                    rollDirection = "RightToLeft";
                }else if(position == 1){
                    rollDirection = "LeftToRight";
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

    private String[] titles3 = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
    private void popWindow3(Context context){
        View contentView1= LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pop_list, null, false);
        ListView list = (ListView)contentView1.findViewById(R.id.list);
        list.setAdapter(new PopListAdapter(context,titles3));
        TextView tv_number = (TextView)contentView1.findViewById(R.id.tv_number);
        tv_number.setVisibility(View.GONE);
        TextView tv_poptitle = (TextView)contentView1.findViewById(R.id.tv_title);
        tv_poptitle.setText("滚动次数");
        TextView tv_close = (TextView)contentView1.findViewById(R.id.tv_close);
        final PopupWindow pop1=new PopupWindow(contentView1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop1.setOutsideTouchable(true);
        pop1.setTouchable(true);

        pop1.showAtLocation(rl_layout_gravity, Gravity.BOTTOM,0,0);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pop1.dismiss();
                tv_roll_num.setText(titles3[position]);
                repeatTimes = position+1;


            }
        });
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop1.dismiss();
            }
        });
    }



    private String[] titles1 = {"置顶","居中","置底"};
    private void popWindow1(Context context){
        View contentView1= LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pop_list, null, false);
        ListView list = (ListView)contentView1.findViewById(R.id.list);
        list.setAdapter(new PopListAdapter(context,titles1));
        TextView tv_number = (TextView)contentView1.findViewById(R.id.tv_number);
        tv_number.setVisibility(View.GONE);
        TextView tv_poptitle = (TextView)contentView1.findViewById(R.id.tv_title);
        tv_poptitle.setText("显示位置");
        TextView tv_close = (TextView)contentView1.findViewById(R.id.tv_close);
        final PopupWindow pop1=new PopupWindow(contentView1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop1.setOutsideTouchable(true);
        pop1.setTouchable(true);

        pop1.showAtLocation(rl_layout_gravity, Gravity.BOTTOM,0,0);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pop1.dismiss();
                tv_gravity.setText(titles1[position]);
                if(id == 0){//等分
                    showpos = "Top";
                }else if(id == 1){//1+Nshowpos = "Top";
                    showpos = "Middle";
                }else if(id == 2){//单分全屏
                    showpos = "Bottom";
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
