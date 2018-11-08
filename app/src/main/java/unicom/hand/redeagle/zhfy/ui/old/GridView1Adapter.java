package unicom.hand.redeagle.zhfy.ui.old;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.bean.ItemBean;

import java.util.List;

@SuppressLint("ViewHolder")
public class GridView1Adapter extends BaseAdapter {
	private Context mContext;
	private List<ItemBean> mList;

	public GridView1Adapter(Context c, List<ItemBean> list) {
		mContext = c;
		mList = list;
	}
	private CalInterface calInterface;
	public interface CalInterface {
		void onVedeoCallClick(int arg2);
	}

	public void setCalInterface(CalInterface calInterface) {
		this.calInterface = calInterface;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		View view = View.inflate(mContext, R.layout.image_gridview_item2, null);
		RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.relaGrid);

		ImageView image = (ImageView) rl.findViewById(R.id.chooseImage);
		image.setBackgroundResource(mList.get(position).getIcon());
		TextView text = (TextView) rl.findViewById(R.id.chooseText);
		text.setText(mList.get(position).getName());
		
		ImageView image2 = (ImageView) rl.findViewById(R.id.imageView1);

		if (mList.get(position).getIsLongBoolean()) {
			image2.setBackgroundResource(R.drawable.icon_add);
		}else{
			image2.setBackgroundResource(R.drawable.icon_de);
		}
		image2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				calInterface.onVedeoCallClick(position);
			}
		});
		return rl;
	}

	@Override
	public ItemBean getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
}