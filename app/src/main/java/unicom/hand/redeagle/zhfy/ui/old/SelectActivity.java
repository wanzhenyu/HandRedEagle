package unicom.hand.redeagle.zhfy.ui.old;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.BaseActivity;
import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.bean.ItemBean;
import unicom.hand.redeagle.zhfy.ui.Activity_listxzfw;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.view.MyGridView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends BaseActivity {
	private MyGridView gridView1, gridView2;
	private GridView1Adapter adapter1;
	private GridView2Adapter adapter2;
	private List<ItemBean> beans1, beans2;
	ArrayList<ItemBean> tempdata;
	private ImageView iv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select);
		iv_back = (ImageView) findViewById(R.id.common_title_left);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		gridView1 = (MyGridView) findViewById(R.id.gridView1);
		gridView2 = (MyGridView) findViewById(R.id.gridView2);

		beans1 = new ArrayList<ItemBean>();
		beans2 = new ArrayList<ItemBean>();
		adapter1 = new GridView1Adapter(SelectActivity.this, beans1);
		adapter2 = new GridView2Adapter(SelectActivity.this, beans2);



	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getDate();
	}
	private void getDate() {
		// TODO Auto-generated method stub
		beans1.clear();
		beans2.clear();
		Type dataType = new TypeToken<List<ItemBean>>() {
		}.getType();
		tempdata = GsonUtil.getGson().fromJson(
				AppApplication.preferenceProvider.getjson(), dataType);
		for (ItemBean itemBean : tempdata) { // 倒序
			if (itemBean.getIsSelect()) {
				beans1.add(itemBean);
			} else {
				beans2.add(itemBean);
			}
		}
		adapter1 = new GridView1Adapter(SelectActivity.this, beans1);

//		ItemBean bean = new ItemBean();
//		bean.setName("县乡村");
//		bean.setIcon(R.drawable.item_xxc);
//		bean.setIsSelect(true);
//		bean.setSqlString("face2face");
//		bean.setIsMain(true);
//		bean.setIsLongBoolean(true);
//		bean.setIsAdd(true);
//		beans2.add(bean);

		adapter2 = new GridView2Adapter(SelectActivity.this, beans2);
		adapter1.setCalInterface(new GridView1Adapter.CalInterface() {

			@Override
			public void onVedeoCallClick(int arg2) {
				// TODO Auto-generated method stub
				if (!beans1.get(arg2).getIsLongBoolean()) {
					for (ItemBean itemBean : tempdata) {
						if (itemBean.getName().equals(
								beans1.get(arg2).getName())) {
							if (!itemBean.getCode().equals("")) {
								tempdata.remove(itemBean);
								break;
							}else {
								itemBean.setIsSelect(false);
							}

						}
					}
					AppApplication.preferenceProvider.setjson(GsonUtil
							.getJson(tempdata));
					getDate();
				}
			}
		});
		adapter2.setCalInterface(new GridView2Adapter.CalInterface() {

			@Override
			public void onVedeoCallClick(int arg2) {
				// TODO Auto-generated method stub
				for (ItemBean itemBean : tempdata) {
					if (itemBean.getName().equals(beans2.get(arg2).getName())) {
						itemBean.setIsSelect(true);
					}
				}
				AppApplication.preferenceProvider.setjson(GsonUtil
						.getJson(tempdata));
				getDate();
			}
		});
		gridView1.setAdapter(adapter1);
		gridView2.setAdapter(adapter2);

		gridView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
				// TODO Auto-generated method stub


				Intent intent = null;
				if (adapter1.getItem(arg2).getName().equals("县乡村")) {
					intent = new Intent(SelectActivity.this,
							Activity_list.class);
					intent.putExtra("item", adapter1.getItem(arg2));
					startActivity(intent);
				}else if (adapter1.getItem(arg2).getName().equals("行政服务")) {
					adapter1.getItem(arg2).setIsAdd(true);
					intent = new Intent(SelectActivity.this,
							Activity_listxzfw.class);
					intent.putExtra("item", adapter1.getItem(arg2));
					intent.putExtra("isadd", "1");
					startActivity(intent);
				}else if (adapter1.getItem(arg2).getName().equals("医疗咨询")) {
					adapter1.getItem(arg2).setIsAdd(true);
					intent = new Intent(SelectActivity.this,
							Activity_listxzfw.class);
					intent.putExtra("item", adapter1.getItem(arg2));
					intent.putExtra("isadd", "1");
					startActivity(intent);
				}else if (adapter1.getItem(arg2).getName().equals("法律咨询")) {
					adapter1.getItem(arg2).setIsAdd(true);
					intent = new Intent(SelectActivity.this,
							Activity_listxzfw.class);
					intent.putExtra("item", adapter1.getItem(arg2));
					intent.putExtra("isadd", "1");
					startActivity(intent);
				}else if (adapter1.getItem(arg2).getName().equals("农技咨询")) {
					adapter1.getItem(arg2).setIsAdd(true);
					intent = new Intent(SelectActivity.this,
							Activity_listxzfw.class);
					intent.putExtra("item", adapter1.getItem(arg2));
					intent.putExtra("isadd", "1");
					startActivity(intent);
				}else if (adapter1.getItem(arg2).getName().equals("服务热线")) {
					adapter1.getItem(arg2).setIsAdd(true);
					intent = new Intent(SelectActivity.this,
							Activity_listxzfw.class);
					intent.putExtra("item", adapter1.getItem(arg2));
					intent.putExtra("isadd", "1");
					startActivity(intent);
				}













			}
		});
		gridView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
				// TODO Auto-generated method stub
				Intent intent = null;
				if (adapter2.getItem(arg2).getName().equals("县乡村")) {
					intent = new Intent(SelectActivity.this,
							Activity_list.class);
					intent.putExtra("item", adapter2.getItem(arg2));
					startActivity(intent);
				}else if (adapter2.getItem(arg2).getName().equals("医疗咨询")) {
					adapter2.getItem(arg2).setIsAdd(true);
					intent = new Intent(SelectActivity.this,
							Activity_list.class);
					intent.putExtra("item", adapter2.getItem(arg2));
					startActivity(intent);
				}else if (adapter2.getItem(arg2).getName().equals("法律咨询")) {
					adapter2.getItem(arg2).setIsAdd(true);
					intent = new Intent(SelectActivity.this,
							Activity_list.class);
					intent.putExtra("item", adapter2.getItem(arg2));
					startActivity(intent);
				}else if (adapter2.getItem(arg2).getName().equals("农技咨询")) {
					adapter2.getItem(arg2).setIsAdd(true);
					intent = new Intent(SelectActivity.this,
							Activity_list.class);
					intent.putExtra("item", adapter2.getItem(arg2));
					startActivity(intent);
				}else if (adapter2.getItem(arg2).getName().equals("服务热线")) {
					adapter2.getItem(arg2).setIsAdd(true);
					intent = new Intent(SelectActivity.this,
							Activity_list.class);
					intent.putExtra("item", adapter2.getItem(arg2));
					startActivity(intent);
				}

			}
		});
	}

	@Override
	protected void handleMsg(Message msg) {
		// TODO Auto-generated method stub

	}

}

