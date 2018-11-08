package unicom.hand.redeagle.zhfy.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yealink.common.data.CallLogGroup;
import com.yealink.common.data.Calllog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.ui.CallLogDetailActivity;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

/**
 * Created by linana on 2018-08-14.
 */

public class CallLogAdapter extends BaseAdapter {
    List<CallLogGroup> callLogGroups;
    private final SimpleDateFormat simpleDateFormat;

    public  CallLogAdapter(List<CallLogGroup> callLogGroups){
        this.callLogGroups = callLogGroups;
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }
    @Override
    public int getCount() {
        return callLogGroups.size();
    }

    @Override
    public Object getItem(int i) {
        return callLogGroups.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CallLogHolder holder;
        if(view == null){
            holder = new CallLogHolder();
            view= View.inflate(UIUtils.getContext(), R.layout.item_calllog,null);
            holder.tv_name = (TextView)view.findViewById(R.id.tv_name);
            holder.tv_time = (TextView)view.findViewById(R.id.tv_time);
            holder.iv_type = (ImageView)view.findViewById(R.id.iv_type);
            holder.iv_detail = (ImageView)view.findViewById(R.id.iv_detail);
            view.setTag(holder);
        }else{
            holder = (CallLogHolder)view.getTag();
        }
        final CallLogGroup callLogGroup = callLogGroups.get(i);
        String cloudDisplayName = callLogGroup.getLastCalllog().getCloudDisplayName();
        holder.tv_name.setText(cloudDisplayName);
        long createTime = callLogGroup.getLastCalllog().createTime;
        String format = simpleDateFormat.format(new Date(createTime));
        String amorPm = DateToWeek(createTime);
        holder.tv_time.setText(format+" "+amorPm);
        int type = callLogGroup.getLastCalllog().type;
        if(type == 1){
            holder.iv_type.setVisibility(View.VISIBLE);
            holder.iv_type.setImageResource(R.drawable.call_out);
        }else if(type == 2){
            holder.iv_type.setVisibility(View.VISIBLE);
            holder.iv_type.setImageResource(R.drawable.call_in);
        }else if(type == 4){
            holder.iv_type.setVisibility(View.VISIBLE);
            holder.iv_type.setImageResource(R.drawable.call_wj);
        }else if(type == 8){
            holder.iv_type.setVisibility(View.VISIBLE);
            holder.iv_type.setImageResource(R.drawable.call_head);
        }else{
            holder.iv_type.setVisibility(View.GONE);
        }
        holder.iv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UIUtils.getContext(), CallLogDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("item",callLogGroup);
                UIUtils.getContext().startActivity(intent);
            }
        });


        return view;
    }


    Calendar c = Calendar.getInstance();
    public static String[] WEEK = {"周日","周一","周二","周三","周四","周五","周六"};
    public static final int WEEKDAYS = 7;

    public static String DateToWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }

        return WEEK[dayIndex - 1];
    }








    public String getAmorPm(long time){
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);

        int hour = mCalendar.get(Calendar.HOUR);
        int apm = mCalendar.get(Calendar.AM_PM);
        if(apm == 0){
            return "上午";
        }
        return "下午";
    }

    class CallLogHolder{
        TextView tv_name;
        TextView tv_time;
        ImageView iv_type;
        ImageView iv_detail;


    }
}
