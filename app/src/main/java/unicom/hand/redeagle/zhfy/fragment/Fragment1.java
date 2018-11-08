package unicom.hand.redeagle.zhfy.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.view.PagerSmallTabStrip;
import unicom.hand.redeagle.zhfy.view.PopupWindows;

import java.util.ArrayList;

public class Fragment1 extends Fragment {

	private static final String TAG = "Fragment1";
	private PagerSmallTabStrip tab;
	ViewPager viewpager;
	private ArrayList<Fragment> fragmentList;
	private ArrayList<String> strList;
	private Button btn_call;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		Log.v(TAG, "Fragment1 ");
		if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_1, container,
					false);
			new PopupWindows(getActivity(),mView);

			tab = (PagerSmallTabStrip) mView.findViewById(R.id.tab);
			viewpager = (ViewPager) mView.findViewById(R.id.pager);
			btn_call = (Button) mView.findViewById(R.id.btn_call);
			fragmentList = new ArrayList<Fragment>();
			strList = new ArrayList<String>();

			Fragment frag0 = new Fragment();
			Fragment frag1 = new TxlFragment();


			fragmentList.add(frag0);//直播课堂
			fragmentList.add(frag1);//博士视频
			viewpager.setAdapter(new MyFragmentPagerAdapter(
					getChildFragmentManager(), fragmentList));
			viewpager.setCurrentItem(0);
			viewpager.setOffscreenPageLimit(2);
//			工作职能、领导讲话、信息公开、先进事迹
			tab.setTabText(new String[] { "通话", "联系人"  });
			tab.setViewPager(viewpager);



			btn_call.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					new PopupWindows(getActivity(),v);
				}
			});

		}

		ViewGroup parent = (ViewGroup) mView.getParent();
		if (parent != null) {
			parent.removeView(mView);
		}

		return mView;
	}
	private View mView;



	class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		ArrayList<Fragment> list;

		public MyFragmentPagerAdapter(FragmentManager fm,
									  ArrayList<Fragment> list) {
			super(fm);
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}

	}

}
