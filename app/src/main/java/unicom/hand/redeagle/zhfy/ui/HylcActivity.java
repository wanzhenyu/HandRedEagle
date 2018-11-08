package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.adapter.HyLcAdapter;
import unicom.hand.redeagle.zhfy.bean.EnableHylcBean;
import unicom.hand.redeagle.zhfy.bean.EnableSubTitleBean;
import unicom.hand.redeagle.zhfy.bean.ProcedureInfos;
import unicom.hand.redeagle.zhfy.bean.QueryLayoutBean;
import unicom.hand.redeagle.zhfy.bean.SaveHylcBean;
import unicom.hand.redeagle.zhfy.bean.SendHylc;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.GsonUtils;
import unicom.hand.redeagle.zhfy.utils.UIUtils;
import unicom.hand.redeagle.zhfy.view.MyListView;

public class HylcActivity extends Activity {
//    private String[] titles = {"课前准备","确定议题","发出通知","课中工作","统计人数","开始授课","专题讨论","党课小结","课后工作"};
    private String procedure1[] = {"会前准备","确定议题","发出通知","会议议程", "统计人数","宣布开会","开展讨论","进行表决"};
    private String procedure23[] = {"会前准备","确定议题","发出通知","会议议程","统计人数","宣布开会","开展讨论","形成决议"};
    private String procedure4[] ={"课前准备","确定课题","发出通知","课中工作","统计人数","开始授课","专题讨论","党课小结","课后工作"};

    private String intentconfId;
    private String intentconfEntity;
    private LinearLayout ll_container;
    private ImageView iv_show_name;
    private boolean isShow = false;
    private String conferenceProcessId = "";
    private TextView tv_save;

    private String selectpos = "-1";

    private String selectId = "";
    private String selectDisplayText = "";
    private boolean selectActive = false;
    private HyLcAdapter hylcadapter;
    private MyListView lv_list;
    private TextView tv_send;

    private List<ProcedureInfos> procedurebeans;
    private TextView tv_reset;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hylc);
        procedurebeans = new ArrayList<>();
        ImageView iv_left = (ImageView)findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv_show_name = (ImageView)findViewById(R.id.iv_show_name);
        intentconfId = getIntent().getStringExtra("confId");
        intentconfEntity = getIntent().getStringExtra("confEntity");
        type = getIntent().getStringExtra("type");
        ll_container = (LinearLayout)findViewById(R.id.ll_container);
        lv_list = (MyListView)findViewById(R.id.lv_list);
        tv_save = (TextView)findViewById(R.id.tv_save);
        tv_send = (TextView)findViewById(R.id.tv_send);
        tv_reset = (TextView)findViewById(R.id.tv_reset);
        TextView tv_backset = (TextView)findViewById(R.id.tv_backset);
        iv_show_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(isShow){
//                    isShow = false;
//                    iv_show_name.setImageResource(R.drawable.switch_off);
//                }else{
//                    isShow = true;
//                    iv_show_name.setImageResource(R.drawable.switch_on);
//                }
                enableHylc();
            }
        });
        tv_save.setEnabled(false);
        tv_reset.setEnabled(false);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHylc();
            }
        });
        tv_backset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetHylc1();
            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendHylc();
            }
        });
        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetHylc();
            }
        });
        hylcadapter = new HyLcAdapter(HylcActivity.this,procedurebeans);
        lv_list.setAdapter(hylcadapter);

        getData();

    }
    private void resetHylc1() {
        SaveHylcBean enablebean = new SaveHylcBean();
        enablebean.setConferenceRecordId(intentconfId);
        enablebean.setConferenceProcessId(conferenceProcessId);
        enablebean.setConfEntity(intentconfEntity);
        enablebean.setEnable(false);
        resetsetHylcActive();
        enablebean.setProcedureInfos(hylcadapter.getUploadBeans());
//        enablebean.setId(selectId);
//        enablebean.setActive(selectActive);
//        enablebean.setDisplayText(selectDisplayText);
        String json = GsonUtil.getJson(enablebean);
        AppApplication.getDataProvider().saveHyLc(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
//                Log.e("aaa","保存会议流程："+o.toString());

                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1) {
                        sendHylc1();

//                        Toast.makeText(HylcActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(HylcActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(HylcActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void resetHylc() {
        SaveHylcBean enablebean = new SaveHylcBean();
        enablebean.setConferenceRecordId(intentconfId);
        enablebean.setConferenceProcessId(conferenceProcessId);
        enablebean.setConfEntity(intentconfEntity);
        enablebean.setEnable(false);
        resetsetHylcActive();
        enablebean.setProcedureInfos(hylcadapter.getUploadBeans());
//        enablebean.setId(selectId);
//        enablebean.setActive(selectActive);
//        enablebean.setDisplayText(selectDisplayText);
        String json = GsonUtil.getJson(enablebean);
        AppApplication.getDataProvider().saveHyLc(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
//                Log.e("aaa","保存会议流程："+o.toString());

                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1) {
                        Toast.makeText(HylcActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(HylcActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(HylcActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void sendHylc() {
        SendHylc  sendhylc = new SendHylc();
        sendhylc.setConferenceRecordId(intentconfId);
        sendhylc.setConferenceProcessId(conferenceProcessId);
        sendhylc.setConfEntity(intentconfEntity);
        sendhylc.setEnable(isShow);
        setHylcActive();
        sendhylc.setProcedureInfos(hylcadapter.getUploadBeans());
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
                        Toast.makeText(HylcActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(HylcActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(HylcActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void sendHylc1() {
        SendHylc  sendhylc = new SendHylc();
        sendhylc.setConferenceRecordId(intentconfId);
        sendhylc.setConferenceProcessId(conferenceProcessId);
        sendhylc.setConfEntity(intentconfEntity);
        sendhylc.setEnable(isShow);
        setHylcActive();
        sendhylc.setProcedureInfos(hylcadapter.getUploadBeans());
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
                        Toast.makeText(HylcActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(HylcActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(HylcActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void saveHylc() {
        SaveHylcBean enablebean = new SaveHylcBean();
        enablebean.setConferenceRecordId(intentconfId);
        enablebean.setConferenceProcessId(conferenceProcessId);
        enablebean.setConfEntity(intentconfEntity);
        enablebean.setEnable(isShow);
        setHylcActive();
        if(hylcadapter != null){
            hylcadapter.notifyDataSetChanged();
        }
        enablebean.setProcedureInfos(hylcadapter.getUploadBeans());
//        enablebean.setId(selectId);
//        enablebean.setActive(selectActive);
//        enablebean.setDisplayText(selectDisplayText);
        String json = GsonUtil.getJson(enablebean);
        AppApplication.getDataProvider().saveHyLc(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
//                Log.e("aaa","保存会议流程："+o.toString());

                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1) {
                        Toast.makeText(HylcActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(HylcActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(HylcActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void resetsetHylcActive() {
        List<ProcedureInfos> selectinfos = new ArrayList<>();
        int selectPos = hylcadapter.getSelectPos();
        for (int i=0;i<procedurebeans.size();i++){
            ProcedureInfos procedureInfos = procedurebeans.get(i);
            procedureInfos.setActive(false);

            if(TextUtils.equals(type,"1")){
                procedureInfos.setDisplayText(procedure1[i]);

            }else if(TextUtils.equals(type,"2") || TextUtils.equals(type,"3")){
                procedureInfos.setDisplayText(procedure23[i]);

            }else if(TextUtils.equals(type,"4")){
                procedureInfos.setDisplayText(procedure4[i]);


            }



            selectinfos.add(procedureInfos);

        }
        if(procedurebeans.size()>0){
            procedurebeans.clear();
        }
        procedurebeans.addAll(selectinfos);
        if(hylcadapter != null){
            hylcadapter.notifyDataSetChanged();
        }
    }
    private void setHylcActive() {
        List<ProcedureInfos> selectinfos = new ArrayList<>();
        int selectPos = hylcadapter.getSelectPos();
        for (int i=0;i<procedurebeans.size();i++){
            ProcedureInfos procedureInfos = procedurebeans.get(i);
            if(selectPos == i){
                procedureInfos.setActive(true);
            }else{
                procedureInfos.setActive(false);
            }
            selectinfos.add(procedureInfos);

        }
        if(procedurebeans.size()>0){
            procedurebeans.clear();
        }
        procedurebeans.addAll(selectinfos);
        if(hylcadapter != null){
            hylcadapter.notifyDataSetChanged();
        }
    }

    private void enableHylc() {
        EnableHylcBean enablebean = new EnableHylcBean();
        enablebean.setConferenceRecordId(intentconfId);
        enablebean.setConferenceBannerId(conferenceProcessId);
        enablebean.setConfEntity(intentconfEntity);
        enablebean.setEnable(isShow);
        String json = GsonUtil.getJson(enablebean);
        AppApplication.getDataProvider().enableHyLc(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","开启禁用字幕："+o.toString());

                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1) {
                        if(isShow){
                            isShow = false;
                            iv_show_name.setImageResource(R.drawable.switch_off);
                        }else{
                            isShow = true;
                            iv_show_name.setImageResource(R.drawable.switch_on);
                        }
                        Toast.makeText(HylcActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    }else{
//                        isShow = !isShow;
//                        if(isShow){
//                            iv_show_name.setImageResource(R.drawable.switch_on);
//                        }else{
//                            iv_show_name.setImageResource(R.drawable.switch_off);
//                        }
                        Toast.makeText(HylcActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    isShow = !isShow;
//                    if(isShow){
//                        iv_show_name.setImageResource(R.drawable.switch_on);
//                    }else{
//                        iv_show_name.setImageResource(R.drawable.switch_off);
//                    }
                }
                setSentButtonEnble();

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(HylcActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                isShow = !isShow;
                if(isShow){
                    iv_show_name.setImageResource(R.drawable.switch_on);
                }else{
                    iv_show_name.setImageResource(R.drawable.switch_off);
                }
                setSentButtonEnble();
            }
        });

    }

    private void setSentButtonEnble() {
        if(isShow){
            tv_send.setEnabled(true);
            tv_send.setTextColor(UIUtils.getColor(R.color.red));
        }else{
            tv_send.setEnabled(false);
            tv_send.setTextColor(UIUtils.getColor(R.color.gray));
        }
    }

    private void getData() {

        QueryLayoutBean querybean = new QueryLayoutBean();
        querybean.setConfEntity(intentconfEntity);
        querybean.setConferenceRecordId(intentconfId);
        String json = GsonUtil.getJson(querybean);
        AppApplication.getDataProvider().getHyLc(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","获取会议流程："+o.toString());
                tv_save.setEnabled(true);
                tv_reset.setEnabled(true);
                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1) {
                        JSONObject data = object.getJSONObject("data");
                        conferenceProcessId = data.getString("conferenceProcessId");
                        boolean enable = data.getBoolean("enable");
                        isShow = enable;
                        if(enable){
                            iv_show_name.setImageResource(R.drawable.switch_on);
                        }else{
                            iv_show_name.setImageResource(R.drawable.switch_off);
                        }
                        setSentButtonEnble();
                        if(!data.isNull("procedureInfos")){
                            JSONArray procedureInfos = data.getJSONArray("procedureInfos");
                            List<ProcedureInfos> beans = GsonUtils.getBeans(procedureInfos.toString(), ProcedureInfos.class);
                            if(procedurebeans.size()>0){
                                procedurebeans.clear();
                            }
                            procedurebeans.addAll(beans);
                            if(hylcadapter != null){
                                hylcadapter.notifyDataSetChanged();
                            }



//                            setUi(beans);
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

//    private void setUi(List<ProcedureInfos> beans) {
//        if(beans != null){
//            for (int i=0;i<beans.size();i++){
//                View inflate = View.inflate(HylcActivity.this, R.layout.item_hylc, null);
//                ll_container.addView(inflate);
//                EditText et_input = (EditText)inflate.findViewById(R.id.et_input);
//                TextView tv_number = (TextView)inflate.findViewById(R.id.tv_number);
//                ProcedureInfos procedureInfos = beans.get(i);
//                final  String id = procedureInfos.getId();
//                tv_number.setText(""+id);
//                final String displayText = procedureInfos.getDisplayText();
//                et_input.setHint(UIUtils.getRealText(displayText));
//
//                ImageView iv_select = (ImageView)inflate.findViewById(R.id.iv_select);
//                iv_select.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        selectpos = id;
//                        selectId = id;
//                        selectDisplayText = displayText;
//                        selectActive = true;
//                        refreshUi();
//                    }
//                });
//
//
//            }
//        }
//
//
//    }

    private void refreshUi() {


    }
}
