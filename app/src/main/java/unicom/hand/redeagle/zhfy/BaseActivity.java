package unicom.hand.redeagle.zhfy;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import unicom.hand.redeagle.R;

public abstract class BaseActivity extends FragmentActivity{
	
	protected abstract void handleMsg(Message msg);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
	}

	protected Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
//				case Common.FACEUPLOADFAIL:
//					showCustomToast("上传头像失败");
//					break;
				default:
					break;
			}

			handleMsg(msg);
		};
	};

	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(BaseActivity.this).inflate(
				R.layout.common_toast, null);
		((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(BaseActivity.this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}
}
