package unicom.hand.redeagle.zhfy.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.bean.IndexNewsBean;

/**
 * Created by linana on 2018-04-27.
 */

public class IndexNewsAdapter extends BaseAdapter {
    private Context context;
    private List<IndexNewsBean> lists;
    String type = "";
    public IndexNewsAdapter(Context context, List<IndexNewsBean> lists,String type){
        this.context = context;
        this.lists = lists;
        this.type = type;
    }
    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        IndexNewsHolder holder;
        if(view == null){
            holder = new IndexNewsHolder();
            view = View.inflate(context, R.layout.item_indexnews,null);
            holder.tv_title = (TextView)view.findViewById(R.id.tv_title);
            holder.tv_time = (TextView)view.findViewById(R.id.tv_time);
            holder.iv_img = (ImageView)view.findViewById(R.id.iv_img);
            view.setTag(holder);
        }else{
            holder = (IndexNewsHolder)view.getTag();
        }
        IndexNewsBean indexNewsBean = lists.get(i);

        String edit_dt = indexNewsBean.getEdit_dt();
        holder.tv_time.setText(edit_dt);
        String title1 = indexNewsBean.getTitle1();
        holder.tv_title.setText(title1);
        if(TextUtils.equals(type,"0")){
            holder.iv_img.setImageResource(R.drawable.ig_34);
        }else if(TextUtils.equals(type,"1")){
            holder.iv_img.setImageResource(R.drawable.ig_36);
        }else if(TextUtils.equals(type,"2")){
            holder.iv_img.setImageResource(R.drawable.ig_42);
        }else if(TextUtils.equals(type,"3")){
            holder.iv_img.setImageResource(R.drawable.ig_37);
        }else if(TextUtils.equals(type,"4")){
            holder.iv_img.setImageResource(R.drawable.ig_47);
        }

        return view;
    }

    class IndexNewsHolder{
        TextView tv_title;
        TextView tv_time;
        ImageView iv_img;
    }



}
