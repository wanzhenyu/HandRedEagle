package unicom.hand.redeagle.zhfy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.bean.ItemBean;

import java.util.List;

@SuppressLint("ViewHolder")
public class ImageGridViewAdapter extends BaseAdapter {
	private Context mContext;
	private List<ItemBean> mList;

	public ImageGridViewAdapter(Context c, List<ItemBean> list) {
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
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.image_gridview_item, null);
			holder.tv_name =   (TextView) convertView.findViewById(R.id.chooseText);
			holder.iv_icon= (ImageView) convertView.findViewById(R.id.chooseImage);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
        ItemBean itemBean = mList.get(position);
        holder.iv_icon.setImageResource(itemBean.getIcon());
		holder.tv_name.setText(itemBean.getName());
		return convertView;
	}
	class ViewHolder{
		TextView tv_name;
		ImageView iv_icon;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
}