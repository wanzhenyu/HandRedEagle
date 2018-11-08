package unicom.hand.redeagle.zhfy.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.adapter.PopListAdapter;
import unicom.hand.redeagle.zhfy.bean.MyCityBean2;
import unicom.hand.redeagle.zhfy.bean.MyNodeBean;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

import com.google.gson.reflect.TypeToken;
import com.yealink.common.data.AccountConstant;
import com.yealink.common.data.SipProfile;
import com.yealink.sdk.RegistListener;
import com.yealink.sdk.YealinkApi;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static unicom.hand.redeagle.R.id.tv_status;


public class ContactDetailActivity extends AppCompatActivity {
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch(msg.what){
//                case MSG_ACCOUNT_CHANGE:
//                    setStatus(YealinkApi.instance().getSipProfile());
//                    break;
//            }
//        }
//    };
//    private String callId = "";// 被叫电话
//    private AlertDialog ad;
//    private AlertDialog.Builder builder;
//    private LinearLayout ll_status;
//    private TextView statusText;
//    private MyCityBean2 bean;
//    private String isvideo = "1";
    private LinearLayout ll_video;
    private MyNodeBean orgNode;

    //    List<MyCityBean2> jsonlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
//        jsonlist = new ArrayList<>();
        orgNode = (MyNodeBean)getIntent().getSerializableExtra("orgNode");
        final MyCityBean2 bean = new MyCityBean2();
        TextView tv_number = (TextView)findViewById(R.id.tv_number);
        TextView tv_name = (TextView)findViewById(R.id.tv_name);
         ll_video = (LinearLayout)findViewById(R.id.ll_video);
        LinearLayout ll_audio = (LinearLayout)findViewById(R.id.ll_audio);
        LinearLayout ll_dial = (LinearLayout)findViewById(R.id.ll_dial);
        ll_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String calledNum = bean.getCalledNum();
                if(TextUtils.isEmpty(calledNum)){
                    Toast.makeText(ContactDetailActivity.this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                }else{
                    popWindow(ContactDetailActivity.this);
                }

            }
        });

        if(orgNode != null){
            String name = orgNode.getName();
            tv_name.setText(name);
            bean.setName(name);
           final String serialNumber = orgNode.getSerialNumber();
            bean.setCalledNum(serialNumber);
            if(TextUtils.isEmpty(serialNumber)){
                tv_number.setText("暂无");
            }else{








                tv_number.setText(serialNumber);
                ll_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {







                        YealinkApi.instance().call(ContactDetailActivity.this,serialNumber);


                    }
                });



                ll_audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        YealinkApi.instance().call(ContactDetailActivity.this,serialNumber,false);
                    }
                });









            }



        }
       ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);

        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }




//
//    public void initCall(String calledNum){
//
//
//        if (ad != null && ad.isShowing()) {
//            ad.dismiss();
//        }
//        try {
//            ad.show();
//        } catch (Exception e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        callId = bean.getCalledNum();
//        logout();
//        Log.d("aaa", "bean.getCallingNum()=" + bean.getCallingNum() + "callId＝" + callId);
//        if (bean.getCallingNum() != null && bean.getCallingNum().length() != 6) {
//            YealinkApi.instance().registerYms(bean.getCallingNum(),
//                    bean.getCallingPassword(),
//                    "pds01.com", "202.110.98.2");
//        } else {
//            SipProfile sp = YealinkApi.instance().getSipProfile();
//            sp.userName = bean.getCallingNum();
//            sp.registerName = bean.getCallingNum();
//            sp.password = bean.getCallingNum();
////					sp.userName = "901089";
////					sp.registerName = "901089";
////					sp.password = "901089";
//            sp.server = "42.236.68.190";
//            sp.port = 5237;
//            sp.isEnableOutbound = false;
//            sp.isBFCPEnabled = false;
//            sp.isEnabled = true;
//            sp.transPort = SipProfile.TRANSPORT_TCP;
//            YealinkApi.instance().registerSip(sp);
//        }
//    }










    private String[] titles = {"音频呼叫","视频呼叫"};
    private void popWindow(Context context){
        View contentView1= LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pop_list, null, false);
        ListView list = (ListView)contentView1.findViewById(R.id.list);
        list.setAdapter(new PopListAdapter(context,titles));
        TextView tv_number = (TextView)contentView1.findViewById(R.id.tv_number);
        tv_number.setText(orgNode.getSerialNumber());
        TextView tv_close = (TextView)contentView1.findViewById(R.id.tv_close);
        final PopupWindow pop1=new PopupWindow(contentView1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop1.setOutsideTouchable(true);
        pop1.setTouchable(true);

        pop1.showAtLocation(ll_video, Gravity.BOTTOM,0,0);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pop1.dismiss();

                if(id == 0){
                    YealinkApi.instance().call(ContactDetailActivity.this,orgNode.getSerialNumber(),false);
                }else if(id == 1){
                    YealinkApi.instance().call(ContactDetailActivity.this,orgNode.getSerialNumber());

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




//    private void logout(){
//        SipProfile sp = YealinkApi.instance().getSipProfile();
//        sp.registerName = "";
//        sp.userName = "";
//        sp.password = "";
//        YealinkApi.instance().registerSip(sp);
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        YealinkApi.instance().removeRegistListener(mRegistListener);
//    }
//    private void setDialog() {
//        // TODO Auto-generated method stub
//        ll_status = (LinearLayout) LayoutInflater.from(ContactDetailActivity.this).inflate(R.layout.ad_threetree_status, null);
//        statusText = (TextView) ll_status.findViewById(tv_status);
//        builder = new AlertDialog.Builder(ContactDetailActivity.this, R.style.Base_Theme_AppCompat_Dialog);
//        builder.setView(ll_status);
//        builder.setCancelable(false);
//        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                dialog.dismiss();
//                logout();
//            }
//        });
//        ad = builder.create();
//        YealinkApi.instance().addRegistListener(mRegistListener);
//    }
//    private static final int MSG_ACCOUNT_CHANGE = 200;
//    RegistListener mRegistListener = new RegistListener() {
//        @Override
//        public void onCloudRegist(final int status) {
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    updateStatus(status);
//                }
//            });
//
//        }
//        @Override
//        public void onSipRegist(int status) {
//            handler.sendEmptyMessage(MSG_ACCOUNT_CHANGE);
//        }
//    };
//
//
//
//
//    private void setStatus(SipProfile sp){
//        if (sp.state == AccountConstant.STATE_UNKNOWN) {
//            statusText.setText("未知");
//        } else if (sp.state == AccountConstant.STATE_DISABLED) {
//            statusText.setText("禁用");
//        } else if (sp.state == AccountConstant.STATE_REGING) {
//            statusText.setText("正在注册...");
//        } else if (sp.state == AccountConstant.STATE_REGED) {
//            statusText.setText("已注册");
//            if (ad != null && ad.isShowing()) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        ad.dismiss();
//                        if(TextUtils.equals(isvideo,"1")){
//                            YealinkApi.instance().call(ContactDetailActivity.this, callId);
//                        }else if(TextUtils.equals(isvideo,"0")){
//                            YealinkApi.instance().call(ContactDetailActivity.this, callId,false);
//                        }
//
//                    }
//                },2000);
//            }
//        } else if (sp.state == AccountConstant.STATE_REG_FAILED) {
//            statusText.setText("注册失败");
//        } else if (sp.state == AccountConstant.STATE_UNREGING) {
//            statusText.setText("正在注销...");
//        } else if (sp.state == AccountConstant.STATE_UNREGED) {
//            statusText.setText("已注销");
//        } else if (sp.state == AccountConstant.STATE_REG_ON_BOOT) {
//            statusText.setText("启动时注册");
//        }
//    }
//
//
//    private void updateStatus(int status) {
//        if (status == AccountConstant.STATE_UNKNOWN) {
//            statusText.setText("未知");
//        } else if (status == AccountConstant.STATE_DISABLED) {
//            statusText.setText("禁用");
//        } else if (status == AccountConstant.STATE_REGING) {
//            statusText.setText("正在注册...");
//        } else if (status == AccountConstant.STATE_REGED) {
//            statusText.setText("已注册");
//            if (ad != null && ad.isShowing()) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        ad.dismiss();
//                        if(TextUtils.equals(isvideo,"1")){
//                            YealinkApi.instance().call(ContactDetailActivity.this, callId);
//                        }else if(TextUtils.equals(isvideo,"0")){
//                            YealinkApi.instance().call(ContactDetailActivity.this, callId,false);
//                        }
//                    }
//                },2000);
//            }
//        } else if (status == AccountConstant.STATE_REG_FAILED) {
//            statusText.setText("注册失败");
//        } else if (status == AccountConstant.STATE_UNREGING) {
//            statusText.setText("正在注销...");
//        } else if (status == AccountConstant.STATE_UNREGED) {
//            statusText.setText("已注销");
//        } else if (status == AccountConstant.STATE_REG_ON_BOOT) {
//            statusText.setText("启动时注册");
//        }
//    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
