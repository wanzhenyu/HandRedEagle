package unicom.hand.redeagle.zhfy.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.utils.StringUtils;
import com.yealink.base.util.ToastUtil;
import com.yealink.sdk.YealinkApi;

import static unicom.hand.redeagle.R.id.et_room;

/**
 * Created by wzy on 17/12/1.
 */

public class CallDialog extends Dialog {

    private EditText editText;
    private Button button;
    public CallDialog(@NonNull final Context context) {
        super(context, R.style.Translucent_DialogNoTitle);
        setContentView(R.layout.dialog_call);
        button= (Button) findViewById(R.id.btn_call);
        editText = (EditText)findViewById(et_room);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isNullOrEmpty(editText.getText().toString())
                        ) {
                    YealinkApi.instance().call(context,editText.getText().toString());

                } else {
                    ToastUtil.toast(context,"号码为空！");


                }
            }
        });
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }
}
