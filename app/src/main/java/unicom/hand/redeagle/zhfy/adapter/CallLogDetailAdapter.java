package unicom.hand.redeagle.zhfy.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yealink.common.data.Calllog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

/**
 * Created by linana on 2018-08-14.
 */

public class CallLogDetailAdapter extends BaseAdapter {
    List<Calllog> callLogList;
    private  SimpleDateFormat yearsimpleDateFormat;
    private  SimpleDateFormat hoursimpleDateFormat;
    public  CallLogDetailAdapter(List<Calllog> callLogList){
        this.callLogList = callLogList;
        yearsimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        hoursimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    }
    @Override
    public int getCount() {
        return callLogList.size();
    }

    @Override
    public Object getItem(int i) {
        return callLogList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CallLogDetailHolder holder;
        if(view == null){
            holder = new CallLogDetailHolder();
            view = View.inflate(UIUtils.getContext(), R.layout.item_calllog_detail,null);
            holder.tv_time = (TextView)view.findViewById(R.id.tv_time);
            holder.tv_type = (TextView)view.findViewById(R.id.tv_type);
            holder.tv_duration = (TextView)view.findViewById(R.id.tv_duration);
            view.setTag(holder);

        }else{
            holder = (CallLogDetailHolder)view.getTag();
        }

        Calllog calllog = callLogList.get(i);
        long createTime = calllog.createTime;
        String yearformat = yearsimpleDateFormat.format(new Date(createTime));
        String amorPm = getAmorPm(createTime);
        String hourformat = hoursimpleDateFormat.format(new Date(createTime));
        String week = DateToWeek(createTime);
        holder.tv_time.setText(yearformat+" "+amorPm+""+hourformat+" "+week);

        int type = calllog.type;
        if(type == 1){
            holder.tv_type.setText("去电");
        }else if(type == 2){
            holder.tv_type.setText("来电");
        }else if(type == 4){
            holder.tv_type.setText("错过电话");
        }else if(type == 8){
            holder.tv_type.setText("会议");
        }else{
            holder.tv_type.setText("未知");
        }
        int duration = calllog.duration;
        holder.tv_duration.setText(convert_between_len(duration)+"");

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
    public static String convert_between_len(long sec) {
        if (sec < 0)
            return String.valueOf(sec);

        StringBuffer buf = new StringBuffer();
        if (sec > 60) {
            int min = (int) (sec / 60);
            int second = (int) (sec % 60);
            buf.append(min).append("分").append(second).append("秒");
        } else {
            buf.append(sec).append("秒");
        }

        return buf.toString();
    }

    class CallLogDetailHolder{
        TextView tv_time;
        TextView tv_type;
        TextView tv_duration;

    }


}
