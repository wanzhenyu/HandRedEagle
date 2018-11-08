package unicom.hand.redeagle.zhfy.ui.old;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import unicom.hand.redeagle.zhfy.BaseActivity;
import unicom.hand.redeagle.zhfy.Common;
import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.adapter.ListViewAdapter;
import unicom.hand.redeagle.zhfy.bean.MyCityBean2;
import com.yealink.common.data.AccountConstant;
import com.yealink.common.data.SipProfile;
import com.yealink.sdk.RegistListener;
import com.yealink.sdk.YealinkApi;

import java.util.List;

import static unicom.hand.redeagle.R.id.tv_status;

public class Activity_search extends BaseActivity {
	private ListView listView;
	ListViewAdapter adapter;
	DbUtils db;
	List<MyCityBean2> list;
	Context context;
	private String TAG = "Activity_list";
	private EditText editText;
	private TextView textView;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		context = Activity_search.this;
		textView = (TextView) findViewById(R.id.common_title_right);
		textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		listView = (ListView) findViewById(R.id.listView);
		editText = (EditText) findViewById(R.id.search);
		db = DbUtils.create(Activity_search.this, Common.DB_NAME);
		setDialog();
		setAdapter();
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
				// TODO Auto-generated method stub
				String str = editText.getText().toString();
				if (!str.equals("")) {
					System.out.println(str);
					searchName(str);
				}
				
			}

			private void searchName(String str) {
				// TODO Auto-generated method stub
				try {
					list = db.findAll(Selector.from(MyCityBean2.class)
							.where("name", "like", "%" + str + "%").and(WhereBuilder.b("HasChildren", "!=", null))
							.orderBy("sort", false));
					adapter.setDate(list);
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void setAdapter() {
		// TODO Auto-generated method stub
		try {
			adapter = new ListViewAdapter(Activity_search.this,false);
			listView.setAdapter(adapter);
			list = db.findAll(Selector.from(MyCityBean2.class).orderBy("sort",
					false));
			adapter.setDate(list);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		adapter.setCalInterface(new ListViewAdapter.CalInterface() {

			@Override
			public void onVedeoCallClick(MyCityBean2 bean) {
				// TODO Auto-generated method stub
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
				logout();
				Log.d(TAG, "bean.getCallingNum()=" + bean.getCallingNum() + "");
				bean.setCallingNum("1001");
				if (bean.getCallingNum()!=null&&bean.getCallingNum().length()!=6) {
//					YealinkApi.instance().registerYms(bean.getCallingNum(),
//							bean.getCallingNum(),
//							"pds01.com", "202.110.98.2");
					YealinkApi.instance().registerYms("1001",
							"123456",
							"pds01.com", "202.110.98.2");
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

		});
	}


	private String callId = "";// 被叫电话
	private AlertDialog ad;
	private AlertDialog.Builder builder;
	private LinearLayout ll_status;
	private TextView statusText;


	private void setDialog() {
		// TODO Auto-generated method stub
		ll_status = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.ad_threetree_status, null);
		statusText = (TextView) ll_status.findViewById(tv_status);
		builder = new AlertDialog.Builder(context, R.style.Base_Theme_AppCompat_Dialog);
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
		Log.i(TAG, "state:" + sp.state);
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
