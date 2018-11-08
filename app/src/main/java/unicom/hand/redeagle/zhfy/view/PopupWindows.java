package unicom.hand.redeagle.zhfy.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import unicom.hand.redeagle.R;
import com.yealink.base.util.ToastUtil;
import com.yealink.sdk.YealinkApi;

/**
 * Created by wzy on 17/12/1.
 */

    public class PopupWindows extends PopupWindow implements View.OnClickListener{

//    private View vw;
    private ImageView iv_call,iv_dowm,iv_del;
    private View mNumber0;
    private View mNumber1;
    private View mNumber2;
    private View mNumber3;
    private View mNumber4;
    private View mNumber5;
    private View mNumber6;
    private View mNumber7;
    private View mNumber8;
    private View mNumber9;
    private View mDot;
    /** #号 **/
    private View mSharp;
    private EditText mEditText;
    private Boolean isPupShow = false;
    Context mContext;
    public PopupWindows(Context mContext, View parent) {
        this.mContext = mContext;
        View view = View.inflate(mContext, R.layout.popup_call, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins_newwork));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_newwork));
        initView(ll_popup);

        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(false);
       setOutsideTouchable(false);  //设置点击屏幕其它地方弹出框消失
        setTouchable(true);
        setContentView(view);
//        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//        showAtLocation();
//        setBackgroundAlpha(0.5f);
//        setOnDismissListener(new OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                // popupWindow隐藏时恢复屏幕正常透明度
//                setBackgroundAlpha(1.0f);
//            }
//        });
//        update();



    }
//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        if(view instanceof RadioButton) {
//            view.setFocusable(true);
//            view.setFocusableInTouchMode(true);
//        }
//        return false;
//    }

    private void initView(LinearLayout ll_popup) {
        mEditText = (EditText)ll_popup.findViewById(R.id.editText);
//        vw = (View) ll_popup.findViewById(R.id.view);
//        vw.setOnClickListener(this);
        iv_call= (ImageView) ll_popup.findViewById(R.id.iv_call2);
        iv_call.setOnClickListener(this);
        iv_dowm= (ImageView) ll_popup.findViewById(R.id.imageView2);
        iv_dowm.setOnClickListener(this);
        iv_del= (ImageView) ll_popup.findViewById(R.id.iv_del);
        iv_del.setOnClickListener(this);
        // 各个按键
        mNumber0 = ll_popup.findViewById(R.id.number0);
        mNumber0.setOnClickListener(this);
        mNumber1 = ll_popup.findViewById(R.id.number1);
        mNumber1.setOnClickListener(this);
        mNumber2 = ll_popup.findViewById(R.id.number2);
        mNumber2.setOnClickListener(this);
        mNumber3 = ll_popup.findViewById(R.id.number3);
        mNumber3.setOnClickListener(this);
        mNumber4 = ll_popup.findViewById(R.id.number4);
        mNumber4.setOnClickListener(this);
        mNumber5 = ll_popup.findViewById(R.id.number5);
        mNumber5.setOnClickListener(this);
        mNumber6 = ll_popup.findViewById(R.id.number6);
        mNumber6.setOnClickListener(this);
        mNumber7 = ll_popup.findViewById(R.id.number7);
        mNumber7.setOnClickListener(this);
        mNumber8 = ll_popup.findViewById(R.id.number8);
        mNumber8.setOnClickListener(this);
        mNumber9 = ll_popup.findViewById(R.id.number9);
        mNumber9.setOnClickListener(this);
        mDot = ll_popup.findViewById(R.id.dot);
        mDot.setOnClickListener(this);
        mSharp = ll_popup.findViewById(R.id.sharp);
        mSharp.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_call2:
                try {
                    String number = mEditText.getText().toString();
                    if(!TextUtils.isEmpty(number)){
                        YealinkApi.instance().call(mContext,number);
                    } else {
                        ToastUtil.toast(mContext,"号码为空！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_del:
                String trim = mEditText.getText().toString().trim();
                if(trim.length()>1){
                    String substring = trim.substring(0, trim.length() - 1);
                    mEditText.setText(substring);
                    mEditText.setSelection(substring.length());
                }else{
                    mEditText.setText("");
                }

                break;
            case R.id.imageView2:
                dismiss();
                break;
//            case R.id.view:
//               dismiss();
//                break;
            case R.id.number0:
                onInput("0");
                break;
            case R.id.number1:
                onInput("1");
                break;
            case R.id.number2:
                onInput("2");
                break;
            case R.id.number3:
                onInput("3");
                break;
            case R.id.number4:
                onInput("4");
                break;
            case R.id.number5:
                onInput("5");
                break;
            case R.id.number6:
                onInput("6");
                break;
            case R.id.number7:
                onInput("7");
                break;
            case R.id.number8:
                onInput("8");
                break;
            case R.id.number9:
                onInput("9");
                break;
            case R.id.dot:
                onInput("*");
                break;
            case R.id.sharp:
                onInput("#");
                break;
            default:
                break;
        }
    }
    private void onInput(String number) {
        mEditText.append(number);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }
}

