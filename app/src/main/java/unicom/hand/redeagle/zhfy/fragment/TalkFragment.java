package unicom.hand.redeagle.zhfy.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import unicom.hand.redeagle.R;
import com.yealink.base.util.ToastUtil;
import com.yealink.sdk.YealinkApi;

/**
 * 通话功能
 * Created by chenhn on 2017/10/19.
 */

public class TalkFragment extends Fragment implements View.OnClickListener {

    private EditText mNumber;



    private static final String TAG = "Fragment1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "Fragment1 ");
        if (mView == null) {
            mView = inflater.inflate(R.layout.talk_makecall, container,
                    false);
            mNumber = (EditText) mView.findViewById(R.id.number);
            View makeCallBtn = mView.findViewById(R.id.btn_makecall);
            makeCallBtn.setOnClickListener(this);
        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;
    }
    private View mView;





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_makecall:
                String number = mNumber.getText().toString();
                if(!TextUtils.isEmpty(number)){
                    YealinkApi.instance().call(getActivity(),number);
                } else {
                    ToastUtil.toast(getActivity(),"号码为空！");
                }
                break;
        }
    }
}
