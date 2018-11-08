package unicom.hand.redeagle.zhfy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import unicom.hand.redeagle.R;

/**
 * Created by linana on 2018-04-26.
 */

public class EditMemberAdapter extends BaseAdapter {
    Context context;
    public EditMemberAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        EditMemberHolder holder;
        if(view == null){
            holder = new EditMemberHolder();
            view = View.inflate(context, R.layout.item_edit_member,null);
            holder.tv_name = (TextView)view.findViewById(R.id.tv_name);
            view.setTag(holder);
        }else{
            holder = (EditMemberHolder)view.getTag();
        }


        return view;
    }

    class EditMemberHolder{
        TextView tv_name;
    }






}
