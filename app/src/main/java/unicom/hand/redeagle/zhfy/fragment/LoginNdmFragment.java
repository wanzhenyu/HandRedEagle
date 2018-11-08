package unicom.hand.redeagle.zhfy.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import unicom.hand.redeagle.zhfy.utils.StringUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginNdmFragment extends Fragment {

    private LinearLayout ll_login, ll_hy;
    private Button btn_login, btn_lsdl, btn_exit, btn_call;
    private EditText tv_username, tv_password, et_room, et_room_ps;
    ImageView commonTitleLeft;
    private onMdmTestListener mainActivity;
    public LoginNdmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_shyk, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData(view);
        logout();

        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        //若权限申请多条 自己在下面添加既可
        //注意:要记的给自己的权限添加图片哦
        permissonItems.add(new PermissionItem(Manifest.permission.CAMERA, "相机", R.drawable.permission_ic_camera));
        permissonItems.add(new PermissionItem(Manifest.permission.MODIFY_AUDIO_SETTINGS, "麦克风", R.drawable.permission_ic_phone));

        HiPermission.create(getActivity())
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


    @SuppressLint("NewApi")
    private void initData(View view) {
        // TODO Auto-generated method stub
        ll_login = (LinearLayout)view.findViewById(R.id.layout_login);
        ll_hy = (LinearLayout)view.findViewById(R.id.layout_hy);
        commonTitleLeft = (ImageView)view.findViewById(R.id.common_title_left);

//        commonTitleLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
        btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_lsdl = (Button) view.findViewById(R.id.btn_lsdl);
        btn_exit = (Button) view.findViewById(R.id.btn_exit);
        btn_call = (Button) view.findViewById(R.id.btn_call);
        tv_username = (EditText) view.findViewById(R.id.et_username);
        tv_password = (EditText) view.findViewById(R.id.et_password);

        tv_username.setText(AppApplication.preferenceProvider.getUsername());
        tv_password.setText(AppApplication.preferenceProvider.getPassWord());

        et_room = (EditText) view.findViewById(R.id.et_room);
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
    public interface onMdmTestListener

    {

        public void onFinishLogin();

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }

    }
    protected void onAttachToContext(Context context) {
        //do something
        mainActivity = (onMdmTestListener)context;
    }





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
            AppApplication.isLogion = true;
            if(mainActivity != null){
                mainActivity.onFinishLogin();
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
