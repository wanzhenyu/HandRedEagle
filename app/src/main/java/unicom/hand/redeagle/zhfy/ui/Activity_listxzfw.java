package unicom.hand.redeagle.zhfy.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.yealink.common.data.AccountConstant;
import com.yealink.common.data.SipProfile;
import com.yealink.sdk.RegistListener;
import com.yealink.sdk.YealinkApi;

import java.util.ArrayList;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.BaseActivity;
import unicom.hand.redeagle.zhfy.Common;
import unicom.hand.redeagle.zhfy.adapter.ListViewAdapter;
import unicom.hand.redeagle.zhfy.adapter.MdmAdapter;
import unicom.hand.redeagle.zhfy.bean.ItemBean;
import unicom.hand.redeagle.zhfy.bean.MyCityBean2;
import unicom.hand.redeagle.zhfy.ui.old.Activity_search;
import unicom.hand.redeagle.zhfy.utils.GsonUtils;
import unicom.hand.redeagle.zhfy.view.ReSpinner;

import static unicom.hand.redeagle.R.id.list;
import static unicom.hand.redeagle.R.id.tv_status;


public class Activity_listxzfw extends BaseActivity {
	private ReSpinner sp1, sp2, sp3;
	private ListView listView;
	private String[] strings2, strings3;
	MdmAdapter adapter = null;
	DbUtils db;
	List<MyCityBean2> onebeans;
	List<MyCityBean2> twobeans;
	List<MyCityBean2> list;
	Context context;
	private String TAG = "Activity_list";
	private ImageView iv_back, iv_search;
	private TextView common_title_middle;
	private ItemBean itemBean;
	private LinearLayout spLayout;
	private String isadd = "0";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		context = Activity_listxzfw.this;
		list = new ArrayList<>();
		spLayout = (LinearLayout) findViewById(R.id.LinearLayout);
		sp1 = (ReSpinner) findViewById(R.id.spinner1);
		sp2 = (ReSpinner) findViewById(R.id.spinner2);
		sp3 = (ReSpinner) findViewById(R.id.spinner3);
		itemBean = (ItemBean) getIntent().getSerializableExtra("item");
		String isaddnum = getIntent().getStringExtra("isadd");
		if(TextUtils.isEmpty(isaddnum)){
			isadd = "0";
		}else{
			isadd = "1";
		}
		common_title_middle = (TextView) findViewById(R.id.common_title_middle);
		common_title_middle.setText(itemBean.getName());
		iv_back = (ImageView) findViewById(R.id.common_title_left);
		iv_search = (ImageView) findViewById(R.id.common_title_right);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		iv_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, Activity_searchxzfw.class);
				startActivity(intent);
			}
		});
		listView = (ListView) findViewById(R.id.listView);

		setDialog();
		initSpDate();
		if (!itemBean.getName().equals("通讯录")){
			setAdapter();
		}


		if (itemBean.getName().equals("行政服务")
				|| itemBean.getName().equals("县乡村")) {
			spLayout.setVisibility(View.VISIBLE);
			iv_search.setVisibility(View.VISIBLE);
		} else {
			spLayout.setVisibility(View.GONE);
			iv_search.setVisibility(View.VISIBLE);
		}
	}

	private void setAdapter() {
		// TODO Auto-generated method stub
		try {

			adapter = new MdmAdapter(Activity_listxzfw.this,
					list,isadd);
			adapter.setSqlString(itemBean.getSqlString());
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

					MyCityBean2 orgNode =  (MyCityBean2)adapterView.getAdapter().getItem(i);
					String calledNum = orgNode.getCalledNum();
					if(!TextUtils.isEmpty(calledNum)) {
						Intent intent = new Intent(Activity_listxzfw.this, MdmContactDetailActivity.class);
						intent.putExtra("orgNode", orgNode);
						startActivity(intent);
					}else{
						Toast.makeText(Activity_listxzfw.this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
					}


				}
			});
			if (itemBean.getSqlString().equals("face2face")
					&& itemBean.getCode().equals("")) {
				list = db.findAll(Selector
						.from(MyCityBean2.class)
						.where("parentCode", "=", "402")
						.and(WhereBuilder.b("category", "=",
								itemBean.getSqlString())).and(WhereBuilder.b("valid", "=",
								1))
						.orderBy("sort", false));
			} else if (!itemBean.getCode().equals("")) {
				if (itemBean.getHasChildren() == 1) {
					list = db.findAll(Selector
							.from(MyCityBean2.class)
							.where("parentCode", "=", itemBean.getCode())
							.and(WhereBuilder.b("category", "=",
									itemBean.getSqlString())).and(WhereBuilder.b("valid", "=",
									1))
							.orderBy("sort", false));
				} else {
					list = db.findAll(Selector
							.from(MyCityBean2.class)
							.where("areaCode", "=", itemBean.getCode())
							.and(WhereBuilder.b("category", "=",
									itemBean.getSqlString())).and(WhereBuilder.b("valid", "=",
									1))
							.orderBy("sort", false));
				}

			} else {
				String cityCode = AppApplication.preferenceProvider.getCityCode();
				list = db.findAll(Selector.from(MyCityBean2.class)
						.where("category", "=", itemBean.getSqlString()).and(WhereBuilder.b("valid", "=",
								1)).and(WhereBuilder.b("parentCode", "=",cityCode))
						.orderBy("sort", false));
				List<MyCityBean2> list1  = db.findAll(Selector.from(MyCityBean2.class)
						.where("parentCode", "=", cityCode)
						.orderBy("sort", false));

//				for (int i=0;i<list1.size();i++){
//					MyCityBean2 myCityBean2 = list1.get(i);
//					String areaCode1 = myCityBean2.getAreaCode();
//					List<MyCityBean2> sublist = db.findAll(Selector.from(MyCityBean2.class)
//						.where("category", "=", itemBean.getSqlString()).and(WhereBuilder.b("valid", "=",
//								1)).and(WhereBuilder.b("parentCode", "=",areaCode1))
//						.orderBy("sort", false));
//					list.addAll(sublist);
//
//				}
				Log.e("aaa",cityCode+",父类值是："+ GsonUtils.getJson(list));
				Log.e("aaa",cityCode+",腹肌值是："+ GsonUtils.getJson(list1));
				getChildData(list1);
//				Log.e("aaa",itemBean.getCode()+"分类是："+ itemBean.getSqlString());

//				.and(WhereBuilder.b("name", "like", "%"+AppApplication.preferenceProvider.getXzCityName()+"%"))

//				Cursor cursor = db.execQuery("SELECT  * FROM    t_Directory_2018_0412 WHERE parentCode = '10001' UNION ALL SELECT  m.* FROM   t_Directory_2018_0412 m JOIN q ON (m.parentCode = q.Code)");
			}
			//.and(WhereBuilder.b("parentCode", "=", AppApplication.preferenceProvider.getCityCode()))


			adapter.setDate(list);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		adapter.setCalInterface(new ListViewAdapter.CalInterface() {

			@Override
			public void onVedeoCallClick(MyCityBean2 bean) {
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
				Log.d(TAG, "bean.getCallingNum()=" + bean.getCallingNum() + "callId＝"+callId);
				if (bean.getCallingNum()!=null&&bean.getCallingNum().length()!=6) {
					YealinkApi.instance().registerYms(bean.getCallingNum(),
							bean.getCallingNum(),
							"pds01.com", "202.110.98.2");
				}else {
					SipProfile sp = YealinkApi.instance().getSipProfile();
					sp.userName = bean.getCallingNum();
					sp.registerName = bean.getCallingNum();
					sp.password = bean.getCallingNum();
//					sp.userName = "901089";
//					sp.registerName = "901089";
//					sp.password = "901089";
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

	private void getChildData(List<MyCityBean2> list1) {

		try {
			for (int i=0;i<list1.size();i++){
                MyCityBean2 myCityBean2 = list1.get(i);
                String areaCode1 = myCityBean2.getAreaCode();
                List<MyCityBean2> sublist = db.findAll(Selector.from(MyCityBean2.class)
                        .where("category", "=", itemBean.getSqlString()).and(WhereBuilder.b("valid", "=",
                                1)).and(WhereBuilder.b("parentCode", "=",areaCode1))
                        .orderBy("sort", false));
				Log.e("aaa",areaCode1+",值是："+ GsonUtils.getJson(sublist));
				if(sublist != null && sublist.size()>0){
					list.addAll(sublist);
					getChildData(sublist);

				}



            }
		} catch (DbException e) {
			e.printStackTrace();
		}

	}

	private void logout(){
		SipProfile sp = YealinkApi.instance().getSipProfile();
		sp.registerName = "";
		sp.userName = "";
		sp.password = "";
		YealinkApi.instance().registerSip(sp);
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		YealinkApi.instance().removeRegistListener(mRegistListener);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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



	private void initSpDate() {
		// TODO Auto-generated method stub
		db = DbUtils.create(Activity_listxzfw.this, Common.DB_NAME);
		try {
			onebeans = db.findAll(Selector
					.from(MyCityBean2.class)
					.where("parentCode", "=", "0")
					.and(WhereBuilder.b("category", "=",
							itemBean.getSqlString())).and(WhereBuilder.b("valid", "=",
							1)).orderBy("sort", false));
			onebeans.add(0, getMyCityBean2("县级 >"));
			strings2 = new String[onebeans.size()];
			for (int i = 0; i < onebeans.size(); i++) {
				strings2[i] = onebeans.get(i).getCompany();
			}
			setSpinnerDate(strings2, sp2);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
				// TODO Auto-generated method stub
				initDate("402");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				initDate("402");
			}
		});
		sp2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (!sp2.getSelectedItem().toString().equals("县级 >")) {
					try {
						twobeans = db.findAll(Selector
								.from(MyCityBean2.class)
								.where("parentCode", "=",
										onebeans.get(arg2).getCode())
								.and(WhereBuilder.b("category", "=",
										itemBean.getSqlString()))
								.and(WhereBuilder.b("valid", "=",
										1))
								.orderBy("sort", false));
//						Log.e("aaa","分类："+itemBean.getSqlString()+",code:"+onebeans.get(arg2).getCode());
						if (twobeans.size() > 0) {
							if (-1 != twobeans.get(0).getCompany()
									.indexOf("部门")) {
								twobeans.remove(0);
							}
						}
						twobeans.add(0, getMyCityBean2("乡、村级>"));
						strings3 = new String[twobeans.size()];
						for (int i = 0; i < twobeans.size(); i++) {
							strings3[i] = twobeans.get(i).getCompany();
						}
						setSpinnerDate(strings3, sp3);
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					initDate(onebeans.get(arg2).getCode());
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		sp3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (!sp3.getSelectedItem().toString().equals("乡、村级>")) {
					initDate(twobeans.get(arg2).getCode());
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initDate(String string) {
		// TODO Auto-generated method stub
		try {
			list = db.findAll(Selector.from(MyCityBean2.class)
					.where("parentCode", "=", string).and(WhereBuilder.b("valid", "=",
							1)).and(WhereBuilder.b("category", "=",
							itemBean.getSqlString())).orderBy("sort", false));
			adapter.setDate(list);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private MyCityBean2 getMyCityBean2(String string) {
		// TODO Auto-generated method stub
		MyCityBean2 bean2 = new MyCityBean2();
		bean2.setCompany(string);
		return bean2;
	}

	private void setSpinnerDate(String[] strings, Spinner sp) {
		// TODO Auto-generated method stub
		// 适配器
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				Activity_listxzfw.this, android.R.layout.simple_spinner_item,
				strings);
		// 设置样式
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 加载适配器
		sp.setAdapter(adapter);
	}

}
