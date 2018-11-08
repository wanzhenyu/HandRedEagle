package unicom.hand.redeagle.zhfy.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yealink.common.data.AccountConstant;
import com.yealink.sdk.RegistListener;
import com.yealink.sdk.YealinkApi;

import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;
import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.MainActivity;
import unicom.hand.redeagle.zhfy.utils.StringUtils;


public class LoginSphyActivity extends Activity {
    private LinearLayout ll_login, ll_hy;
    private Button btn_login, btn_lsdl, btn_exit, btn_call;
    private EditText tv_username, tv_password, et_room, et_room_ps;
    ImageView commonTitleLeft;
//    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sphy);
//        type = getIntent().getStringExtra("type");
        initData();
        logout();
        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        //若权限申请多条 自己在下面添加既可
        //注意:要记的给自己的权限添加图片哦
        permissonItems.add(new PermissionItem(Manifest.permission.CAMERA, "相机", R.drawable.permission_ic_camera));
        permissonItems.add(new PermissionItem(Manifest.permission.MODIFY_AUDIO_SETTINGS, "麦克风", R.drawable.permission_ic_phone));

        HiPermission.create(LoginSphyActivity.this)
                .permissions(permissonItems)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                        Log.d("load", "！！onClose");
                    }

                    @Override
                    public void onFinish() {
                        Log.d("load", "！！onFinish");
                    }

                    @Override
                    public void onDeny(String permisson, int position) {
                        Log.d("load", "！！onDeny");
                    }

                    @Override
                    public void onGuarantee(String permisson, int position) {
                        Log.d("load", "！！onGuarantee");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mRegistListener != null){
            YealinkApi.instance().removeRegistListener(mRegistListener);
        }

    }

    @SuppressLint("NewApi")
    private void initData() {
        // TODO Auto-generated method stub
        ll_login = (LinearLayout)findViewById(R.id.layout_login);
        LinearLayout ll_bg = (LinearLayout)findViewById(R.id.ll_bg);

        ll_hy = (LinearLayout)findViewById(R.id.layout_hy);
        commonTitleLeft = (ImageView)findViewById(R.id.common_title_left);

        commonTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSphyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_lsdl = (Button) findViewById(R.id.btn_lsdl);
        btn_exit = (Button) findViewById(R.id.btn_exit);
        btn_call = (Button) findViewById(R.id.btn_call);
        tv_username = (EditText) findViewById(R.id.et_username);
        tv_password = (EditText) findViewById(R.id.et_password);

        tv_username.setText(AppApplication.preferenceProvider.getUsername());
        tv_password.setText(AppApplication.preferenceProvider.getPassWord());
        et_room = (EditText) findViewById(R.id.et_room);


//        if (TextUtils.equals(type,"sphy")){
//            btn_login.setText("进入会议室");
//        }else {
//            btn_login.setText("登录");
//            ll_bg.setBackgroundResource(R.drawable.shyk_login_bg);
//        }







        setDialog();
        btn_call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (!StringUtils.isNullOrEmpty(et_room.getText().toString())
                        ) {
                    YealinkApi.instance().call(getActivity(), et_room.getText().toString());

                } else {
                    Toast.makeText(getActivity(), "请输入```", Toast.LENGTH_SHORT).show();


                }
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (!StringUtils.isNullOrEmpty(tv_username.getText().toString())
                        && !StringUtils.isNullOrEmpty(tv_password.getText().toString())) {
                    if (ad != null && ad.isShowing()) {
                        ad.dismiss();
                    }
                    try {
                        ad.show();
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    logout();
//                    YealinkApi.instance().registerYms(tv_username.getText().toString(),
//                            tv_password.getText().toString(),
//                            "pds01.com", "202.110.98.2");
                    AppApplication.preferenceProvider.setUsername(tv_username.getText().toString());
                    AppApplication.preferenceProvider.setPassWord(tv_password.getText().toString());
                    YealinkApi.instance().registerYms(tv_username.getText().toString(),
                            tv_password.getText().toString(),
                            AppApplication.preferenceProvider.getIp(), AppApplication.preferenceProvider.getIp2());
                    Log.d("load", AppApplication.preferenceProvider.getIp()+"!"+ AppApplication.preferenceProvider.getIp2());
                } else {
                    Toast.makeText(getActivity(), "请输入完整的账号密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                ll_hy.setVisibility(View.GONE);
                ll_login.setVisibility(View.VISIBLE);
                logout();
            }
        });

    }
    private AlertDialog ad;
    private AlertDialog.Builder builder;
    private LinearLayout ll_status;
    private TextView statusText;

    private void setDialog() {
        // TODO Auto-generated method stub
        ll_status = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.ad_threetree_status, null);
        statusText = (TextView) ll_status.findViewById(R.id.tv_status);
        builder = new AlertDialog.Builder(getActivity());
        builder.setView(ll_status);
        builder.setCancelable(false);
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                logout();
            }
        });
        ad = builder.create();
        mHandler = new Handler();

        YealinkApi.instance().addRegistListener(mRegistListener);
    }
    private Handler mHandler;
    RegistListener mRegistListener = new RegistListener() {
        @Override
        public void onCloudRegist(final int status) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    updateStatus(status);
                }
            });

        }
    };
    private void logout(){
        try {
            YealinkApi.instance().unregistCloud();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateStatus(int status){
        if (status == AccountConstant.STATE_UNKNOWN) {
            statusText.setText("未知");
        } else if (status == AccountConstant.STATE_DISABLED) {
            statusText.setText("禁用");
        } else if (status == AccountConstant.STATE_REGING) {
            statusText.setText("正在注册...");
        } else if (status == AccountConstant.STATE_REGED) {
            statusText.setText("已注册");
            if (ad != null && ad.isShowing()) {
                ad.dismiss();
            }
//            if (TextUtils.equals(type,"sphy")){
//                Intent intent = new Intent(LoginSphyActivity.this, SphyMainActivity.class);
//                startActivity(intent);
//                finish();
//            }else {
//                finish();
//            }

            Intent intent = new Intent(LoginSphyActivity.this, SphyMainActivity.class);
            startActivity(intent);
            finish();




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

    public Context getActivity() {
        return LoginSphyActivity.this;
    }
}





