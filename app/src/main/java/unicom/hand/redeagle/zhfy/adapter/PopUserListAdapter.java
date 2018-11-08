package unicom.hand.redeagle.zhfy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.bean.MeetDetailListBean;


/**
 * Created by linana on 2018-01-25.
 */

public class PopUserListAdapter extends BaseAdapter {
    private Context context;
    List<MeetDetailListBean.Participants> intentparticipants;
    private LayoutInflater inflater ;
    public PopUserListAdapter(Context context, List<MeetDetailListBean.Participants> intentparticipants){
        this.context = context;
        this.intentparticipants = intentparticipants;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        if(intentparticipants == null){
            return 0;
        }
        return intentparticipants.size();
    }

    @Override
    public Object getItem(int i) {
        return intentparticipants.get(i);
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
        MeetDetailListBean.Participants participants = intentparticipants.get(i);
        holder.tv_name.setText(participants.getName());
        return view;
    }
    class Holder{
    TextView tv_name;
    }



}
