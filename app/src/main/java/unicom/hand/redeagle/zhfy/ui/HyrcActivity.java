package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.fragment.HyrcFragment;

public class HyrcActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyrc);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, new HyrcFragment()).commit();
    }
}
