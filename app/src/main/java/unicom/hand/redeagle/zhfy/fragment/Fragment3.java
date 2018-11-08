package unicom.hand.redeagle.zhfy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.MainActivity;
import unicom.hand.redeagle.zhfy.ui.IpActivity;
import unicom.hand.redeagle.zhfy.ui.PhoneRecoderActivity;
import unicom.hand.redeagle.zhfy.ui.WebActivity;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

public class Fragment3 extends Fragment {

    private static final String TAG = "TestFragment";
    private LinearLayout ll_help, ll_phone, ll_store,ll_ip,ll_tb;

    public static Fragment3 newInstance() {
        // TODO Auto-generated method stub
        Fragment3 fragment = new Fragment3();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView ");
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_3, container, false);
            ll_help = (LinearLayout) mView.findViewById(R.id.ll_help);
            ll_phone = (LinearLayout) mView.findViewById(R.id.ll_phone);
            ll_store = (LinearLayout) mView.findViewById(R.id.ll_mystore);
            ll_ip = (LinearLayout) mView.findViewById(R.id.ll_ip);
            ll_tb = (LinearLayout) mView.findViewById(R.id.ll_tb);
            ll_help.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("url","http://42.236.68.190:8090/zshy_web/zshy/help_zshy.html");
                    intent.putExtra("title","帮助");
                    startActivity(intent);
                }
            });
            ll_ip.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(getActivity(), IpActivity.class);
                    startActivity(intent);

                }
            });
            ll_store.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub

                }
            });

            ll_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), PhoneRecoderActivity.class);
                    startActivity(intent);



                }
            });
            ll_tb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)getActivity()).getCode();



                }
            });








        }

        // 缓存的rootView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;
    }

    private View mView;

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
}