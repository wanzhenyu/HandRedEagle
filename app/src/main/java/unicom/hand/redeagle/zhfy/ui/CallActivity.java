package unicom.hand.redeagle.zhfy.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import unicom.hand.redeagle.R;
import com.yealink.base.util.ToastUtil;
import com.yealink.sdk.YealinkApi;


public class CallActivity extends Activity implements View.OnClickListener {
    private ImageView iv_back;
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
    private ImageView iv_call;
    private ImageView iv_del;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        initData();

    }

    @SuppressLint("NewApi")
    private void initData() {
        // TODO Auto-generated method stub
        iv_back = (ImageView) findViewById(R.id.common_title_left);
        iv_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        iv_call= (ImageView)findViewById(R.id.iv_call2);
        iv_del= (ImageView)findViewById(R.id.imageView2);
        iv_call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String number = mEditText.getText().toString();
                if(!TextUtils.isEmpty(number)){
                    YealinkApi.instance().call(CallActivity.this,number);
                } else {
                    ToastUtil.toast(CallActivity.this,"号码为空！");
                }
            }
        });
        iv_del.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                mEditText.setText("");
            }
        });
        mEditText = (EditText)findViewById(R.id.editText);
        // 各个按键
        mNumber0 = findViewById(R.id.number0);
        mNumber0.setOnClickListener(this);
        mNumber1 = findViewById(R.id.number1);
        mNumber1.setOnClickListener(this);
        mNumber2 = findViewById(R.id.number2);
        mNumber2.setOnClickListener(this);
        mNumber3 = findViewById(R.id.number3);
        mNumber3.setOnClickListener(this);
        mNumber4 = findViewById(R.id.number4);
        mNumber4.setOnClickListener(this);
        mNumber5 = findViewById(R.id.number5);
        mNumber5.setOnClickListener(this);
        mNumber6 = findViewById(R.id.number6);
        mNumber6.setOnClickListener(this);
        mNumber7 = findViewById(R.id.number7);
        mNumber7.setOnClickListener(this);
        mNumber8 = findViewById(R.id.number8);
        mNumber8.setOnClickListener(this);
        mNumber9 = findViewById(R.id.number9);
        mNumber9.setOnClickListener(this);
        mDot = findViewById(R.id.dot);
        mDot.setOnClickListener(this);
        mSharp = findViewById(R.id.sharp);
        mSharp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

}

