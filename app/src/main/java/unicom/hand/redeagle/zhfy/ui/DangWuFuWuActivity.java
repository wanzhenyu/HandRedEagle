package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.yealink.common.data.AccountConstant;
import com.yealink.common.data.CloudProfile;
import com.yealink.common.data.SipProfile;
import com.yealink.sdk.RegistListener;
import com.yealink.sdk.YealinkApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.BaseActivity;
import unicom.hand.redeagle.zhfy.Common;
import unicom.hand.redeagle.zhfy.adapter.ImageGridViewAdapter;
import unicom.hand.redeagle.zhfy.bean.ItemBean;
import unicom.hand.redeagle.zhfy.bean.MyCityBean2;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.view.MyGridView;

import static unicom.hand.redeagle.R.id.tv_status;

public class DangWuFuWuActivity extends BaseActivity {
    private MyGridView gridView;
     Integer[] resArray = new Integer[] {
            R.drawable.dwfw_1, R.drawable.dwfw_2,
            R.drawable.dwfw_3,R.drawable.dwfw_4, R.drawable.dwfw_5,
             R.drawable.dwfw_6  };
    private String titles[] = {"党员组织\n关系接转","支部选举","第一书记服务","党费缴纳","党员电化教育","党建信息化"};
    private List<ItemBean> beans;
    DbUtils db;
    List<MyCityBean2> list;

    private String callId = "";// 被叫电话
    private AlertDialog ad;
    private AlertDialog.Builder builder;
    private LinearLayout ll_status;
    private TextView statusText;
    Context context;
    public Map<Integer,String> maptitles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_wu_fu_wu);
        context = DangWuFuWuActivity.this;
        beans = new ArrayList<>();
        setDialog();
        maptitles = new HashMap<>();
        db = DbUtils.create(DangWuFuWuActivity.this, Common.DB_NAME);
        for (int i=0;i<resArray.length;i++){
            ItemBean itemBean = new ItemBean();
            itemBean.setIcon(resArray[i]);
            itemBean.setName(titles[i]);

            beans.add(itemBean);

        }
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        try {
            list = db.findAll(Selector
                    .from(MyCityBean2.class)
                    .where("category", "=", "party")
                    .orderBy("sort", false));
        } catch (DbException e) {
            e.printStackTrace();
        }

        if(list != null && list.size()>0){
            for (int i=0;i<list.size();i++){
                MyCityBean2 bean = list.get(i);
                String name = bean.getName();
                maptitles.put(i,name);

            }
        }
        String json = GsonUtil.getJson(list);
//        Log.e("aaa","党务服务："+json);

        gridView = (MyGridView) findViewById(R.id.gridView1);
        gridView.setAdapter(new ImageGridViewAdapter(
                DangWuFuWuActivity.this, beans));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                CloudProfile cp = YealinkApi.instance().getCloudProfile();
//                //没登录
//                if (cp != null && cp.state != 2) {
//                    Intent intent = new Intent(DangWuFuWuActivity.this, LoginActivity.class);
//                    intent.putExtra("type","dwfw");
//                    startActivity(intent);
//                }else{
//
//                }

                call(i);

            }
        });






    }

    private void call(int j) {
        Log.e("aaa","党务服务："+maptitles.containsValue(titles[j]));
        if(j == 0){//党员组织关系结转
            if(list != null){
                if(!maptitles.containsValue(titles[0])){
                    Toast.makeText(this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i=0;i<list.size();i++){
                    MyCityBean2 bean = list.get(i);
                    String name = bean.getName();

                    if(TextUtils.equals(name,titles[0])){
                        Log.e("aaa","开始呼叫："+name);
                        goCall(bean);
                        break;
                    }
                }
            }else{
                Toast.makeText(this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                return;
            }


        }else if(j == 1){//支部选举
            if(list != null){
                if(!maptitles.containsValue(titles[1])){
                    Toast.makeText(this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i=0;i<list.size();i++){
                    MyCityBean2 bean = list.get(i);
                    String name = bean.getName();
                    if(TextUtils.equals(name,"支部选举")){
                        goCall(bean);
                        break;
                    }
                }
            }else{
                Toast.makeText(this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                return;
            }


        }else if(j == 2){//第一书记服务
            if(list != null){
                if(!maptitles.containsValue(titles[2])){
                    Toast.makeText(this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i=0;i<list.size();i++){
                    MyCityBean2 bean = list.get(i);
                    String name = bean.getName();
                    if(TextUtils.equals(name,"第一书记服务")){
                        goCall(bean);
                        break;
                    }
                }
            }else{
                Toast.makeText(this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                return;
            }


        }else if(j == 3){//党费缴纳
            if(list != null){
                if(!maptitles.containsValue(titles[3])){
                    Toast.makeText(this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i=0;i<list.size();i++){
                    MyCityBean2 bean = list.get(i);
                    String name = bean.getName();
                    if(TextUtils.equals(name,"党费缴纳")){
                        goCall(bean);
                        break;
                    }
                }
            }else{
                Toast.makeText(this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                return;
            }


        }else if(j == 4){//党员电化教育
            if(list != null){
                if(!maptitles.containsValue(titles[4])){
                    Toast.makeText(this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i=0;i<list.size();i++){
                    MyCityBean2 bean = list.get(i);
                    String name = bean.getName();
                    if(TextUtils.equals(name,"党员电化教育")){
                        goCall(bean);
                        break;
                    }
                }
            }else{
                Toast.makeText(this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                return;
            }


        }else if(j == 5){//党员信息化
            if(list != null){
                if(!maptitles.containsValue(titles[5])){
                    Toast.makeText(this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i=0;i<list.size();i++){
                    MyCityBean2 bean = list.get(i);
                    String name = bean.getName();
                    if(TextUtils.equals(name,"党建信息化")){
                        goCall(bean);
                        break;
//                        YealinkApi.instance().call(DangWuFuWuActivity.this,myCityBean2.getCalledNum());
                    }
                }
            }else{
                Toast.makeText(this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                return;
            }


        }





    }






    public void goCall(MyCityBean2 bean){
        if (ad != null && ad.isShowing()) {
            ad.dismiss();
        }
        try {
            ad.show();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        callId = bean.getCalledNum();
        if(TextUtils.isEmpty(callId)){
            Toast.makeText(this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
            return;
            
        }
        logout();
        Log.d("aaa", "bean.getCallingNum()=" + bean.getCallingNum() + "");
//				bean.setCallingNum("1001");
        if (bean.getCallingNum()!=null&&bean.getCallingNum().length()!=6) {
            YealinkApi.instance().registerYms(bean.getCallingNum(),
                    bean.getCallingPassword(),
                    "pds01.com", "202.110.98.2");
//					YealinkApi.instance().registerYms("1001",
//							"123456",
//							"pds01.com", "202.110.98.2");
        }else {
//					SipProfile sp = SettingsManager.getInstance().getSipProfile(0);
            SipProfile sp = YealinkApi.instance().getSipProfile();
            sp.userName = bean.getCallingNum();
            sp.registerName = bean.getCallingNum();
            sp.password = bean.getCallingNum();
            sp.server = "42.236.68.190";
            sp.port = 5237;
            sp.isEnableOutbound=false;
            sp.isBFCPEnabled = false;
            sp.isEnabled = true;
            sp.transPort = SipProfile.TRANSPORT_TCP;
            YealinkApi.instance().registerSip(sp);
        }
    }






    private void setDialog() {
        // TODO Auto-generated method stub
        ll_status = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.ad_threetree_status, null);
        statusText = (TextView) ll_status.findViewById(tv_status);
        builder = new AlertDialog.Builder(context);
        builder.setView(ll_status);
        builder.setCancelable(false);
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                logout();
            }
        });
        ad = builder.create();
        YealinkApi.instance().addRegistListener(mRegistListener);
    }

    RegistListener mRegistListener = new RegistListener() {
        @Override
        public void onCloudRegist(final int status) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    updateStatus(status);
                }
            });

        }
        @Override
        public void onSipRegist(int status) {
            handler.sendEmptyMessage(MSG_ACCOUNT_CHANGE);
        }
    };
    private static final int MSG_ACCOUNT_CHANGE = 200;
    @Override
    protected void handleMsg(Message msg) {
        switch(msg.what){
            case MSG_ACCOUNT_CHANGE:
                setStatus(YealinkApi.instance().getSipProfile());
                break;
        }
    }


    private void setStatus(SipProfile sp){
        Log.i("aaa", "state:" + sp.state);
        if (sp.state == AccountConstant.STATE_UNKNOWN) {
            statusText.setText("未知");
        } else if (sp.state == AccountConstant.STATE_DISABLED) {
            statusText.setText("禁用");
        } else if (sp.state == AccountConstant.STATE_REGING) {
            statusText.setText("正在注册...");
        } else if (sp.state == AccountConstant.STATE_REGED) {
            statusText.setText("已注册");
            if (ad != null && ad.isShowing()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ad.dismiss();
                        YealinkApi.instance().call(context, callId);
                    }
                },2000);
            }
        } else if (sp.state == AccountConstant.STATE_REG_FAILED) {
            statusText.setText("注册失败");
        } else if (sp.state == AccountConstant.STATE_UNREGING) {
            statusText.setText("正在注销...");
        } else if (sp.state == AccountConstant.STATE_UNREGED) {
            statusText.setText("已注销");
        } else if (sp.state == AccountConstant.STATE_REG_ON_BOOT) {
            statusText.setText("启动时注册");
        }
    }



    private void logout(){
        SipProfile sp = YealinkApi.instance().getSipProfile();
        sp.registerName = "";
        sp.userName = "";
        sp.password = "";
        YealinkApi.instance().registerSip(sp);
    }

    private void updateStatus(int status) {
        if (status == AccountConstant.STATE_UNKNOWN) {
            statusText.setText("未知");
        } else if (status == AccountConstant.STATE_DISABLED) {
            statusText.setText("禁用");
        } else if (status == AccountConstant.STATE_REGING) {
            statusText.setText("正在注册...");
        } else if (status == AccountConstant.STATE_REGED) {
            statusText.setText("已注册");
            if (ad != null && ad.isShowing()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ad.dismiss();
                        YealinkApi.instance().call(context, callId);
                    }
                },2000);
            }
        } else if (status == AccountConstant.STATE_REG_FAILED) {
            statusText.setText("注册失败");
        } else if (status == AccountConstant.STATE_UNREGING) {
            statusText.setText("正在注销...");
        } else if (status == AccountConstant.STATE_UNREGED) {
            statusText.setText("已注销");
        } else if (status == AccountConstant.STATE_REG_ON_BOOT) {
            statusText.setText("启动时注册");
        }
    }














}
