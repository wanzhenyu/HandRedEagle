package unicom.hand.redeagle.zhfy.ui.old;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.BaseActivity;
import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.adapter.ImageGridViewAdapter;
import unicom.hand.redeagle.zhfy.bean.ItemBean;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.view.MyGridView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;


public class Activity_Face2Face extends BaseActivity {
	private MyGridView gridView;
	private List<ItemBean> beans;
	private ImageView iv_back;
	TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_face2face);
		title = (TextView)findViewById(R.id.common_title_middle);
		title.setText(getIntent().getStringExtra("title"));
		iv_back = (ImageView) findViewById(R.id.common_title_left);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		gridView = (MyGridView) findViewById(R.id.gridView1);
		beans = getDate();
		gridView.setAdapter(new ImageGridViewAdapter(Activity_Face2Face.this,
				beans));
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
                                    final int arg2, long arg3) {
				Intent intent = new Intent(Activity_Face2Face.this,
						Activity_list.class);
				intent.putExtra("item", beans.get(arg2));
				startActivity(intent);
			}
		});

		List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
		//若权限申请多条 自己在下面添加既可
		//注意:要记的给自己的权限添加图片哦
		permissonItems.add(new PermissionItem(Manifest.permission.CAMERA, "相机", R.drawable.permission_ic_camera));
		permissonItems.add(new PermissionItem(Manifest.permission.MODIFY_AUDIO_SETTINGS, "麦克风", R.drawable.permission_ic_phone));

		HiPermission.create(Activity_Face2Face.this)
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

	private ArrayList<ItemBean> getDate() {
		// TODO Auto-generated method stub
		Type dataType = new TypeToken<List<ItemBean>>() {
		}.getType();
		ArrayList<ItemBean> tempdata = GsonUtil.getGson().fromJson(AppApplication.preferenceProvider.getjson(),
				dataType);
		for (int i = tempdata.size() - 1; i >= 0; i--) {
			if (tempdata.get(i).getIsMain()
					|| !tempdata.get(i).getOrder().equals(getIntent().getStringExtra("title"))) {
				tempdata.remove(i);
			}
		}
		return tempdata;
	}


	@Override
	protected void handleMsg(Message msg) {
		// TODO Auto-generated method stub

	}

}
