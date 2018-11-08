package unicom.hand.redeagle.zhfy.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.bean.OnImgListener;

/**
 * Created by linana on 2018-07-30.
 */

public class MeetDetailAddImgAdapter extends BaseAdapter {
    Context context;
    List<String> imgs;
    OnImgListener listener;
    String isEditAble;
    public MeetDetailAddImgAdapter(Context context, List<String> imgs, OnImgListener listener,String isEditAble){
        this.context = context;
        this.imgs = imgs;
        this.listener = listener;
        this.isEditAble = isEditAble;
    }
    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public Object getItem(int i) {
        return imgs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.item_add_img,null);
            holder.tv_name = (TextView)view.findViewById(R.id.tv_name);
            holder.tv_add = (TextView)view.findViewById(R.id.tv_add);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        final String s = imgs.get(i);
        holder.tv_name.setText(s);
        if(TextUtils.isEmpty(s)){
            holder.tv_add.setText("添加");
        }else{
            holder.tv_add.setText("删除");
        }
        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(s)){//添加
                    listener.add();

                }else{//删除

                    listener.remove(i);
                }
            }
        });

        if(TextUtils.equals(isEditAble,"0")){
            holder.tv_add.setVisibility(View.GONE);
        }else{
            holder.tv_add.setVisibility(View.VISIBLE);
        }

        return view;
    }
    class ViewHolder{
        TextView tv_name;
        TextView tv_add;
    }
}
