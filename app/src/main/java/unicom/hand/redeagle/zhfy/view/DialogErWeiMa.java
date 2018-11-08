package unicom.hand.redeagle.zhfy.view;


import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;
import android.widget.TextView;

import unicom.hand.redeagle.R;

public class DialogErWeiMa extends Dialog {

	private Context context;
	private OnSuccessListener successListener;
	TextView tv_version;

	public interface OnSuccessListener {
		public boolean onSuccessPress();
	}

	public void setSuccessListener(OnSuccessListener successListener) {
		this.successListener = successListener;
	}

	public DialogErWeiMa(Context context) {
		super(context, R.style.Translucent_NoTitle);
		this.context = context;
		initView();
		setScreenBgDarken();
	}

	private void setScreenBgDarken() {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 1.0f;
		lp.dimAmount = 1.0f;
		getWindow().setAttributes(lp);
	}

	private void initView() {
		setContentView(R.layout.dialog_erweima);
	}

	@Override
	public void dismiss() {
		if (isShowing()) {
			super.dismiss();
		}
	}

	// protected void showCustomToast(String text) {
	// View toastRoot =
	// LayoutInflater.from(context).inflate(R.layout.common_toast, null);
	// ((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
	// Toast toast = new Toast(context);
	// toast.setGravity(Gravity.CENTER, 0, 0);
	// toast.setDuration(Toast.LENGTH_SHORT);
	// toast.setView(toastRoot);
	// toast.show();
	// }
}
