package unicom.hand.redeagle.zhfy.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.utils.StringUtils;
import com.yealink.common.data.AccountConstant;
import com.yealink.sdk.RegistListener;
import com.yealink.sdk.YealinkApi;

public class FragmentLogin extends Fragment {

	private static final String TAG = "TestFragment";

	private LinearLayout ll_login, ll_hy;
	private Button btn_login, btn_lsdl, btn_exit,btn_call;
	private EditText tv_username, tv_password, et_room,et_room_ps;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		Log.v(TAG, "onCreateView ");
		if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_login, container,
					false);

			ll_login = (LinearLayout)mView.findViewById(R.id.layout_login);
			ll_hy = (LinearLayout)mView. findViewById(R.id.layout_hy);

			btn_login = (Button)mView. findViewById(R.id.btn_login);
			btn_lsdl = (Button) mView.findViewById(R.id.btn_lsdl);
			btn_exit = (Button)mView. findViewById(R.id.btn_exit);
			btn_call= (Button) mView.findViewById(R.id.btn_call);
			tv_username = (EditText) mView.findViewById(R.id.et_username);
			tv_password = (EditText)mView. findViewById(R.id.et_password);
			et_room = (EditText)mView. findViewById(R.id.et_room);
			setDialog();
			btn_call.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (!StringUtils.isNullOrEmpty(et_room.getText().toString())
							) {
						YealinkApi.instance().call(getActivity(),et_room.getText().toString());

					} else {
						Toast.makeText(getActivity(),"请输入```", Toast.LENGTH_SHORT).show();


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
						YealinkApi.instance().registerYms(tv_username.getText().toString(),
								tv_password.getText().toString(),
								"pds01.com","202.110.98.2");

					} else {
						Toast.makeText(getActivity(),"请输入完整的账号密码", Toast.LENGTH_SHORT).show();
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

		ViewGroup parent = (ViewGroup) mView.getParent();
		if (parent != null) {
			parent.removeView(mView);
		}

		return mView;
	}
	private View mView;

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
			getActivity().finish();
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
