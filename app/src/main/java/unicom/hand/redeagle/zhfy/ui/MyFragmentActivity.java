package unicom.hand.redeagle.zhfy.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.fragment.ContactRootFragment;
import unicom.hand.redeagle.zhfy.fragment.FragmentLogin;
import unicom.hand.redeagle.zhfy.fragment.TalkFragment;

public class MyFragmentActivity extends FragmentActivity {

    public static final String FA_LOGIN ="LOGIN";//登陆
    public static final String FA_CALL ="CALL";//拨号
    public static final String FA_CONTACT ="FA_CONTACT";//联系人
    ImageView commonTitleLeft;
    TextView commonTitleMiddle;
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        commonTitleLeft = (ImageView)findViewById(R.id.common_title_left);
        commonTitleMiddle = (TextView) findViewById(R.id.common_title_middle);

        commonTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String type = getIntent().getStringExtra("type");
        if (type.equals(FA_LOGIN)) {
            commonTitleMiddle.setText("登陆");
            showFragment(new FragmentLogin());
        }else if(type.equals(FA_CALL)) {
            commonTitleMiddle.setText("拨号");
            showFragment(new TalkFragment());
        }else if(type.equals(FA_CONTACT)) {
            commonTitleMiddle.setText("通讯录");
            showFragment(new ContactRootFragment());
//            Intent intent = new Intent(MyFragmentActivity.this,ContactRootActivity.class);
//            startActivity(intent);
        }else{
            commonTitleMiddle.setText("会议日程");
        }
    }

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    private void showFragment(Fragment fragment) {
        // TODO Auto-generated method stub
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).commit();
    }

}
