package unicom.hand.redeagle.zhfy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import unicom.hand.redeagle.R;


/**
 * Created by linana on 2018-01-25.
 */

public class PopListAdapter extends BaseAdapter {
    private Context context;
    String[] strs;
    private LayoutInflater inflater ;
    public PopListAdapter(Context context, String[] strings){
        this.context = context;
        this.strs = strings;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        if(strs == null){
            return 0;
        }
        return strs.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder ;
        if(view == null){
            holder = new Holder();
            view = View.inflate(context, R.layout.pop_item,null);
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(holder);
        }else{
            holder = (Holder)view.getTag();
        }
        holder.tv_name.setText(strs[i]);
        return view;
    }
    class Holder{
    TextView tv_name;
    }



}
