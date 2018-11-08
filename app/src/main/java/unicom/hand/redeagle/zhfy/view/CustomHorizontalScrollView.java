package unicom.hand.redeagle.zhfy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;


/** 
 * 自定义横向滑动view
 * @author huanglaiyun
 * @date 2016年7月27日
 */
public class CustomHorizontalScrollView extends HorizontalScrollView {
//	private View mAttachedView;
//	private ImageView mLeftImg;
//	private ImageView mRightImg;
//	private int mWindowWidth = 0;
//	private Activity mContext;
//	public void setSomeParam(Activity context, View view, ImageView leftImage, ImageView rightImage) {
//		mContext = context;
//		mAttachedView = view;
//		mLeftImg = leftImage;
//		mRightImg = rightImage;
//		DisplayMetrics dm = new DisplayMetrics();
//		mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
//		mWindowWidth = dm.widthPixels;
//	}
	public CustomHorizontalScrollView(Context context) {
		super(context);
	}
	public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

//	@Override
//	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//		super.onScrollChanged(l, t, oldl, oldt);
//		if (!mContext.isFinishing() && mAttachedView != null && mRightImg != null && mLeftImg != null) {
//			if (mAttachedView.getWidth() <= mWindowWidth) {
//				mLeftImg.setVisibility(View.GONE);
//				mRightImg.setVisibility(View.GONE);
//			} else {
//				if (l == 0) {
//					mLeftImg.setVisibility(View.GONE);
//					mRightImg.setVisibility(View.VISIBLE);
//				} else if (mAttachedView.getWidth() - l == this.getMeasuredWidth()) {
//					mLeftImg.setVisibility(View.VISIBLE);
//					mRightImg.setVisibility(View.GONE);
//				} else {
//					mLeftImg.setVisibility(View.VISIBLE);
//					mRightImg.setVisibility(View.VISIBLE);
//				}
//			}
//		}
//	}
}