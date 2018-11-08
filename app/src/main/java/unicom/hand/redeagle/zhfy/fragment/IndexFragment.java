package unicom.hand.redeagle.zhfy.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import unicom.hand.redeagle.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends Fragment {
    private String titles[] = {"时政要闻","组织工作","手机报","组织建设","知识天地","红鹰视频"};
    private List<Fragment> frgments;


    public IndexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_index, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frgments = new ArrayList<>();
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("平顶山党建");

        LunboIndexContentFragment indexContentFragment1 = new LunboIndexContentFragment();
        indexContentFragment1.setType("34");
        IndexContentFragment1 indexContentFragment2 = new IndexContentFragment1();
        indexContentFragment2.setType("35");
        IndexContentFragment1 indexContentFragment3 = new IndexContentFragment1();
        indexContentFragment3.setType("42");
        IndexContentFragment1 indexContentFragment4 = new IndexContentFragment1();
        indexContentFragment4.setType("37");
        IndexContentFragment1 indexContentFragment5 = new IndexContentFragment1();
        indexContentFragment5.setType("2");
        IndexWebViewFragment1 indexWebViewFragment1 = new IndexWebViewFragment1();
        frgments.add(indexContentFragment1);
        frgments.add(indexContentFragment2);
        frgments.add(indexContentFragment3);
        frgments.add(indexContentFragment4);
        frgments.add(indexContentFragment5);
        frgments.add(indexWebViewFragment1);
        ViewPager vp_safelog = (ViewPager)view.findViewById(R.id.vp_safelog);
        VpAdapter vpAdapter = new VpAdapter(getChildFragmentManager());
        TabLayout mTabLayout = (TabLayout)view.findViewById(R.id.tab_FindFragment_title);
        vp_safelog.setAdapter(vpAdapter);
        mTabLayout.setupWithViewPager(vp_safelog);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

    }


    public class VpAdapter extends FragmentStatePagerAdapter {


        public VpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return frgments.get(position);
        }

        @Override
        public int getCount() {
            return frgments.size();
        }


    }

}
