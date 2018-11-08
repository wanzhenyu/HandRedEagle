package unicom.hand.redeagle.zhfy.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import unicom.hand.redeagle.R;


public class PagerSmallTabStrip extends LinearLayout {
	private LinearLayout tabsContainer;
	private ViewPager pager;
	private String[] tabtexts;
	private final PageListener pageListener = new PageListener();
	public OnPageChangeListener delegatePageListener;
	private int tabCount;
	private int currentPosition = 0;

	private LayoutParams defaultTabLayoutParams;
	private int tabTextSize = 18;
	private int tabTextSize_unselect = 18 ;
	private int tabTextColor = 0xFFFFFFFF;
	private int tabTextColor_unselect = 0xFFEBA8A4;
//	private int tabBackgroundResId_left = R.drawable.background_tab_left;
//	private int tabBackgroundResId_mid = R.drawable.background_tab_mid;
//	private int tabBackgroundResId_right = R.drawable.background_tab_right;

	public PagerSmallTabStrip(Context context) {
		this(context, null);
	}

	public PagerSmallTabStrip(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi") public PagerSmallTabStrip(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		tabsContainer = new LinearLayout(context);
		tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
		tabsContainer.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		tabsContainer.setBackgroundColor(tabTextColor_unselect);
		addView(tabsContainer);
		defaultTabLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

		DisplayMetrics dm = getResources().getDisplayMetrics();

	}

	public void setTabText(String[] tabtexts) {
		this.tabtexts = tabtexts;
		if (tabtexts == null) {
			throw new IllegalStateException("tabtexts == null ");
		}
		;
	}

	public void setViewPager(ViewPager pager) {
		this.pager = pager;

		if (pager.getAdapter() == null) {
			throw new IllegalStateException(
					"ViewPager does not have adapter instance.");
		}
		pager.setOnPageChangeListener(pageListener);
		notifyDataSetChanged();
	}

	public void setOnPageChangeListener(OnPageChangeListener listener) {
		this.delegatePageListener = listener;
	}

	public void notifyDataSetChanged() {
		tabsContainer.removeAllViews();
		tabCount = pager.getAdapter().getCount();

		if (tabtexts.length != tabCount) {
			throw new IllegalStateException(" tabtexts.length != tabCount");
		}

		LayoutParams tab_params = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		tab_params.weight = 1;

		for (int position = 0; position < tabCount; position++) {
			TextView tab = new TextView(getContext());
			tab.setText(tabtexts[position]);
			tab.setGravity(Gravity.CENTER);
			tab.setSingleLine();
			tab.setTag(position);
			tab.setFocusable(true);
			tab.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pager.setCurrentItem((Integer) v.getTag());
				}
			});

			tab.setPadding(24, 0, 24, 0);
			tabsContainer.addView(tab, position, tab_params);
		}

		updateTabStyles();
		updateTabsStatus();
	}

	private void updateTabStyles() {

		for (int i = 0; i < tabCount; i++) {
			View v = tabsContainer.getChildAt(i);
			if (v instanceof TextView) {
				TextView tab = (TextView) v;
				tab.setTextSize(TypedValue.COMPLEX_UNIT_SP, tabTextSize);
				tab.setTextColor(tabTextColor);
			}
		}

	}

	public void updateTabsStatus() {
		for (int i = 0; i < tabCount; i++) {
			View v = tabsContainer.getChildAt(i);
//			if (i == 0) {
//				if (currentPosition == i) {
//					v.setBackgroundResource(R.drawable.sub_press);
//				} else {
//					v.setBackgroundResource(tabBackgroundResId_left);
//				}
//			} else if (i == tabCount - 1) {
//				if (currentPosition == i) {
//					v.setBackgroundResource(R.drawable.sub_press);
//				} else {
//					v.setBackgroundResource(tabBackgroundResId_left);
//				}
//			} else {
//				if (currentPosition == i) {
//					v.setBackgroundResource(R.drawable.sub_press);
//				} else {
//					v.setBackgroundResource(tabBackgroundResId_left);
//				}
//			}
			v.setBackgroundColor(getResources().getColor(R.color.actionbar_color));

			if (v instanceof TextView) {
				TextView tab = (TextView) v;
				if(currentPosition == i){
					tab.setTextSize(TypedValue.COMPLEX_UNIT_SP, tabTextSize);
					tab.setTextColor(tabTextColor);
				}else{
					tab.setTextSize(TypedValue.COMPLEX_UNIT_SP, tabTextSize_unselect);
					tab.setTextColor(tabTextColor_unselect);
				}
			}
		}
	}

	private class PageListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {

			invalidate();

			if (delegatePageListener != null) {
				delegatePageListener.onPageScrolled(position, positionOffset,
						positionOffsetPixels);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE) {
				// scrollToChild(pager.getCurrentItem(), 0);
			}

			if (delegatePageListener != null) {
				delegatePageListener.onPageScrollStateChanged(state);
			}
		}

		@Override
		public void onPageSelected(int position) {
			currentPosition = position;
			updateTabsStatus();
			if (delegatePageListener != null) {
				delegatePageListener.onPageSelected(position);
			}
		}

	}

}
