package unicom.hand.redeagle.view.network;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView.ScaleType;

import com.android.volley.toolbox.NetworkImageView;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.view.ImageTxtIndicatorView;

import java.util.List;

/**
 * Network ImageIndicatorView, by urls
 * 
 * @author steven-pan
 * 
 */
public class NetworkImageTxtIndicatorView extends ImageTxtIndicatorView {

	public NetworkImageTxtIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public NetworkImageTxtIndicatorView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs,defStyle);
	}
	public NetworkImageTxtIndicatorView(Context context) {
		super(context);
	}

	/**
	 * 设置显示图片URL列表
	 * 
	 * @param urlList
	 *            URL列表
	 */
	public void setupLayoutByImageUrl(final List<String> urlList, final List<String> txtList) {
		if (urlList == null)
			throw new NullPointerException();

		final int len = urlList.size();
		if (len > 0) {
			for (int index = 0; index < len; index++) {
				final NetworkImageView pageItem = new NetworkImageView(getContext());
				pageItem.setScaleType(ScaleType.FIT_XY);
				pageItem.setDefaultImageResId(R.drawable.ic_news_loading);
				pageItem.setImageUrl(urlList.get(index),
						AppApplication.getImageLoader());
				addViewItem(pageItem);
				addViewTxt(txtList.get(index));
			}
		}
	}
	
}
