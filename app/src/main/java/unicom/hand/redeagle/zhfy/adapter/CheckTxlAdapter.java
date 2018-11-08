package unicom.hand.redeagle.zhfy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.bean.TxlBean;
import unicom.hand.redeagle.zhfy.ui.CheckTxlActivity;

import java.util.List;

public class CheckTxlAdapter extends BaseAdapter implements SectionIndexer {
	private List<TxlBean> list = null;
	private Context mContext;

	public CheckTxlAdapter(Context mContext, List<TxlBean> list) {
		this.mContext = mContext;
		this.list = list;
	}

	public void updateListView(List<TxlBean> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	private CheckInterface checkInterface;

	public interface CheckInterface {
		void onClick();
	}

	public void setOnCheckInterface(CheckInterface checkInterface) {
		this.checkInterface = checkInterface;
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final TxlBean mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(
					R.layout.fragment_phone_constacts_item2, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.tvPhone = (TextView) view.findViewById(R.id.phone);
			viewHolder.iv_video = (ImageView) view.findViewById(R.id.video);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		int section = getSectionForPosition(position);

		if (position == getPositionForSection(section)) {
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		} else {
			viewHolder.tvLetter.setVisibility(View.GONE);
		}

		viewHolder.tvTitle.setText(this.list.get(position).getUserName() );
		viewHolder.tvPhone.setText(this.list.get(position).getPhone() );

		viewHolder.iv_video.setBackgroundResource(R.mipmap.unchecked);
		for(String str :CheckTxlActivity.idsList){
			if(list.get(position).getPhone().equals(str)){
				viewHolder.iv_video.setBackgroundResource(R.mipmap.checked);
				break;
			}else{
				viewHolder.iv_video.setBackgroundResource(R.mipmap.unchecked);
			}
		}
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean b = false;
				int item = 0;
				for(int i = 0; i< CheckTxlActivity.idsList.size(); i++){
					if(list.get(position).getPhone().equals(CheckTxlActivity.idsList.get(i))){
						b  = true;
						item = i;
						break;
					}
				}
				if(b){
					CheckTxlActivity.idsList.remove(item);
				}else{
					CheckTxlActivity.idsList.add(list.get(position).getPhone());
				}
				notifyDataSetChanged();
				checkInterface.onClick();
			}
		});
		return view;

	}

	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		TextView tvPhone;
		ImageView iv_video;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		if (list != null) {
			for (int i = 0; i < getCount(); i++) {
				String sortStr = list.get(i).getSortLetters();
				char firstChar = sortStr.toUpperCase().charAt(0);
				if (firstChar == section) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * 提取英文的首字母，非英文字母
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}