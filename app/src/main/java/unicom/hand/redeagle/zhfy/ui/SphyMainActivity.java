package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.fragment.ContactFragment;
import unicom.hand.redeagle.zhfy.fragment.Dial1Fragment;
import unicom.hand.redeagle.zhfy.fragment.SphyFragment;
import unicom.hand.redeagle.zhfy.fragment.SphyHyrcFragment;
import unicom.hand.redeagle.zhfy.utils.UIUtils;
import unicom.hand.redeagle.zhfy.view.PopupWindows;

public class SphyMainActivity extends FragmentActivity {
    private FragmentManager fm;
    private RadioGroup radioGroup;
    private SphyFragment sphyFragment;
    private SphyHyrcFragment hyrcFragment;
    private ContactFragment contactFragment;
    private Dial1Fragment dialFragment;
    private FrameLayout framelayout;
    private PopupWindows popupWindows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sphy_main);
        fm = getSupportFragmentManager();
         FragmentTransaction ft = fm.beginTransaction();

        Log.e("aaa","开始");
        sphyFragment = new SphyFragment();
        hyrcFragment = new SphyHyrcFragment();
        contactFragment = new ContactFragment();
        dialFragment = new Dial1Fragment();
        ft.add(R.id.framecontent, sphyFragment)
                .add(R.id.framecontent, hyrcFragment)
                .add(R.id.framecontent, contactFragment)
                .add(R.id.framecontent, dialFragment);
        ft.show(sphyFragment).hide(hyrcFragment).hide(contactFragment).hide(dialFragment).commit();
        Log.e("aaa","Dial1Fragment开始1");
        radioGroup = (RadioGroup) findViewById(R.id.rg_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                changeButton(i);
            }
        });
        framelayout = (FrameLayout)findViewById(R.id.framecontent);
        popupWindows = new PopupWindows(SphyMainActivity.this, framelayout);
         RadioButton rb_4 = (RadioButton)findViewById(R.id.rb_4);
        rb_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                  FragmentTransaction ft1 = fm.beginTransaction();
//                if(!rb_4.isChecked()){
//                    ft1.show(dialFragment).hide(sphyFragment).hide(hyrcFragment).hide(contactFragment).commit();
//                }

//                dialFragment.setShowPop();
//                int[] windowPos = calculatePopWindowPos(framelayout, popupWindows.getContentView());
//                int xOff = 20;// 可以自己调整偏移
//                windowPos[0] -= xOff;
                if(popupWindows.isShowing()){
                    popupWindows.dismiss();
                }else{
                    popupWindows.showAtLocation(framelayout, Gravity.NO_GRAVITY,0, -UIUtils.dip2px(36));
                }



            }
        });

//        RadioButton rb_3 = (RadioButton)findViewById(R.id.rb_3);
//        rb_3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction ft1 = fm.beginTransaction();
//                ft1.show(contactFragment).hide(sphyFragment).hide(hyrcFragment).hide(dialFragment).commit();
//
//            }
//        });
//
//        RadioButton rb_2 = (RadioButton)findViewById(R.id.rb_2);
//        rb_2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction ft1 = fm.beginTransaction();
//                ft1.show(hyrcFragment).hide(sphyFragment).hide(contactFragment).hide(dialFragment).commit();
//
//            }
//        });
//        RadioButton rb_1 = (RadioButton)findViewById(R.id.rb_1);
//        rb_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction ft1 = fm.beginTransaction();
//                ft1.show(sphyFragment).hide(hyrcFragment).hide(contactFragment).hide(dialFragment).commit();
//
//
//            }
//        });

        Log.e("aaa","Dial1Fragment开始2");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(popupWindows != null){
            popupWindows.dismiss();
        }
        Log.e("aaa","Dial1Fragment开始1");
    }

    public void changeButton(int position) {
        FragmentTransaction ft = fm.beginTransaction();
        switch (position){
            case R.id.rb_1:
                if(popupWindows.isShowing()){
                    popupWindows.dismiss();
                }
                ft.show(sphyFragment).hide(hyrcFragment).hide(contactFragment).hide(dialFragment).commit();
                break;
            case R.id.rb_2:
                if(popupWindows.isShowing()){
                    popupWindows.dismiss();
                }
                ft.show(hyrcFragment).hide(sphyFragment).hide(contactFragment).hide(dialFragment).commit();
                break;
            case R.id.rb_3:
                if(popupWindows.isShowing()){
                    popupWindows.dismiss();
                }
                ft.show(contactFragment).hide(sphyFragment).hide(hyrcFragment).hide(dialFragment).commit();
                break;
            case R.id.rb_4:

                ft.show(dialFragment).hide(sphyFragment).hide(hyrcFragment).hide(contactFragment).commit();
//                popupWindows = new PopupWindows(SphyMainActivity.this, framelayout);
//                if(popupWindows.isShowing()){
//                    popupWindows.dismiss();
//                }else{
//                    popupWindows.showAtLocation(framelayout, Gravity.BOTTOM,0,50);
//                }

                break;
            default:
                break;
        }


    }


}
