package unicom.hand.redeagle.zhfy.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yealink.common.data.Meeting;
import com.yealink.sdk.YealinkApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

/**
 * Created by linana on 2018-04-16.
 */

public class HyrcAdapter extends BaseAdapter {
    private Activity context;
    List<Meeting> list;
    private final SimpleDateFormat simpledate;

    public HyrcAdapter(Activity context,List<Meeting> list){
        this.context = context;
        this.list = list;
        simpledate = new SimpleDateFormat("MM/dd HH:mm");
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HyrcHolder holder;
        if(view == null){
            holder = new HyrcHolder();
            view = View.inflate(context, R.layout.item_audio_guide,null);
            holder.tv_time = (TextView)view.findViewById(R.id.tv_time);
            holder.tv_des = (TextView)view.findViewById(R.id.tv_des);
            holder.tv_organizer = (TextView)view.findViewById(R.id.tv_organizer);
            holder.tv_join = (TextView)view.findViewById(R.id.tv_join);

            view.setTag(holder);
        }else{
            holder = (HyrcHolder)view.getTag();
        }

        final Meeting meeting = list.get(i);
        long startTime = meeting.getStartTime();
        String startformat = simpledate.format(new Date(startTime));
        long endTime = meeting.getEndTime();
        String endformat = simpledate.format(new Date(endTime));
        holder.tv_time.setText(startformat+"-"+endformat);

        String title = meeting.getTitle();
        holder.tv_des.setText(UIUtils.getRealText(title));
        String organizer = meeting.getOrganizer();
        holder.tv_organizer.setText("组织者:"+UIUtils.getRealText(organizer));
        holder.tv_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YealinkApi.instance().joinMeeting(context,meeting);
            }
        });

        return view;
    }


    class HyrcHolder{
        TextView tv_time;
        TextView tv_des;
        TextView tv_organizer;
        TextView tv_join;

    }


}
