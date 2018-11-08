package unicom.hand.redeagle.zhfy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.bean.ItemBean;

@SuppressLint("ViewHolder")
public class DangwuImageGridViewAdapter extends BaseAdapter {
	private Context mContext;
	private List<ItemBean> mList;

	public DangwuImageGridViewAdapter(Context c, List<ItemBean> list) {
		mContext = c;
		mList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		View view = View.inflate(mContext, R.layout.image_gridview_item, null);
		RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.relaGrid);

		ImageView image = (ImageView) rl.findViewById(R.id.chooseImage);
		image.setBackgroundResource(mList.get(position).getIcon());
		TextView text = (TextView) rl.findViewById(R.id.chooseText);
		text.setText(mList.get(position).getName());
		return rl;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}