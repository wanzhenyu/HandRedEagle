package unicom.hand.redeagle.zhfy.ui;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.Common;
import unicom.hand.redeagle.zhfy.MainActivity;
import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.bean.ThjlBean;
import unicom.hand.redeagle.zhfy.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;

/**
 *
 * 
 * @author Administrator
 * 
 */
public class Load extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_load);

//        ThjlBean thjlBean = new ThjlBean();
//        thjlBean.title = "标题";
//        thjlBean.serialNum = "345223131";
//        thjlBean.time = "20180701";
//        thjlBean.type = "类型";
//        String json = GsonUtil.getJson(thjlBean);
//        thjlBean.title = json;
//        String json1 = GsonUtil.getJson(thjlBean);
//        Log.e("aaa",json1+"时间戳:"+System.currentTimeMillis());

        if (AppApplication.hasPermission(Load.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
			setDb();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(Load.this,
                            MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, 2000);
		}else {

            List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
            //若权限申请多条 自己在下面添加既可
            //注意:要记的给自己的权限添加图片哦
            permissonItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储", R.drawable.permission_ic_storage));
            permissonItems.add(new PermissionItem(Manifest.permission.CAMERA, "相机", R.drawable.permission_ic_camera));
            permissonItems.add(new PermissionItem(Manifest.permission.MODIFY_AUDIO_SETTINGS, "麦克风", R.drawable.permission_ic_phone));

            HiPermission.create(Load.this)
                    .permissions(permissonItems)
                    .checkMutiPermission(new PermissionCallback() {
                        @Override
                        public void onClose() {
                            Log.d("load", "！！onClose");
                            setDb();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent mainIntent = new Intent(Load.this,
                                            MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            }, 2000);
                        }

                        @Override
                        public void onFinish() {
                            Log.d("load", "！！onFinish");
                            setDb();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent mainIntent = new Intent(Load.this,
                                            MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            }, 2000);
                        }

                        @Override
                        public void onDeny(String permisson, int position) {
                            Log.d("load", "！！onDeny");
                            setDb();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent mainIntent = new Intent(Load.this,
                                            MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            }, 2000);
                        }

                        @Override
                        public void onGuarantee(String permisson, int position) {
                            Log.d("load", "！！onGuarantee");
                            setDb();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent mainIntent = new Intent(Load.this,
                                            MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            }, 2000);
                        }
                    });
        }


	}

	public void setDb(){
		File dir = new File(Common.ROOT_DIR);
		if (!dir.exists()) {
            AppApplication.preferenceProvider.setUid("0");
			dir.mkdirs();
		}
		File file = new File(Common.DB_NAME);
		if (!file.exists()) {
			try {
				FileUtil.delAllFile(Common.ROOT_DIR);
				FileUtil.loadFile(Load.this, file, R.raw.zshy);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
