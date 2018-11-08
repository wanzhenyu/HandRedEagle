package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.fragment.LoginNdmFragment;
import unicom.hand.redeagle.zhfy.fragment.LoginSphyFragment;

public class LoginMdmActivity extends FragmentActivity implements LoginNdmFragment.onMdmTestListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mdm);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, new LoginNdmFragment()).commit();
    }

    @Override
    public void onFinishLogin() {
        finish();
    }
}
