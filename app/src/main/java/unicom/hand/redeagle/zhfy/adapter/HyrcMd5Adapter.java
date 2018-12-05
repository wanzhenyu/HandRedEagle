package unicom.hand.redeagle.zhfy.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yealink.base.util.ToastUtil;
import com.yealink.common.data.ConferenceHistory;
import com.yealink.common.data.Meeting;
import com.yealink.sdk.YealinkApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.bean.HyrcBeanMd5;
import unicom.hand.redeagle.zhfy.bean.HyrcBeanMd51;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

/**
 * Created by linana on 2018-04-16.
 */

public class HyrcMd5Adapter extends BaseAdapter {
    private Activity context;
    List<HyrcBeanMd51> list;
    private final SimpleDateFormat simpledate;
    String flag;
    public HyrcMd5Adapter(Activity context, List<HyrcBeanMd51> list){
        this.context = context;
        this.list = list;
        simpledate = new SimpleDateFormat("MM/dd HH:mm");
//        this.flag = flag;
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
            holder.tv_state = (TextView)view.findViewById(R.id.tv_state);
            view.setTag(holder);
        }else{
            holder = (HyrcHolder)view.getTag();
        }

        final HyrcBeanMd51 meeting = list.get(i);
        final long startTime = Long.parseLong(meeting.getStartTime());
        String startformat = simpledate.format(new Date(startTime));
        final long endTime = Long.parseLong(meeting.getExpiryTime());
        String endformat = simpledate.format(new Date(endTime));
        holder.tv_time.setText(startformat+"~"+endformat);
        final String title = meeting.getSubject();
        Log.e("eee",title+",时间是："+startformat+"~"+endformat);

//        title = UIUtils.getHyrcTtitle(title);
        holder.tv_des.setText(UIUtils.getRealText(UIUtils.getHyrcTtitle(title)));
        String organizer = meeting.getOrganizerName();
        holder.tv_organizer.setText("组织者:"+UIUtils.getHyrcTtitle(UIUtils.getRealText(organizer)));
        holder.tv_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String conferencePlanId = meeting.getConferenceId();
                if(conferencePlanId == null || TextUtils.isEmpty(conferencePlanId)){
                    ToastUtil.toast(context,"暂时无法加入");
                    return;
                }

                Log.e("aaa","会议id："+conferencePlanId);
                Meeting meet = new Meeting();
                meet.setId(conferencePlanId);
                meet.setTitle(title);
                meet.setMeetingId(conferencePlanId);
                meet.setStartTime(startTime);
                meet.setEndTime(endTime);//AppApplication.preferenceProvider.getUsername()
                meet.setNumber(meeting.getConferenceNumber());

                String username = AppApplication.preferenceProvider.getUsername();
                String passWord = AppApplication.preferenceProvider.getPassWord();
                passWord = meeting.getPinCode();
                String ip = AppApplication.preferenceProvider.getIp2();
                String servertype = ConferenceHistory.TYPE_YMS;
                ip = "";
                meet.setFoucusUri(ip);
//                YealinkApi.instance().joinMeeting(context,meet);
                YealinkApi.instance().joinMeetingById(context,conferencePlanId,passWord,username,ip,true,true, servertype);

            }
        });
        if(TextUtils.equals(this.flag,"1")){
            holder.tv_join.setVisibility(View.INVISIBLE);
        }else{

        }
        long currenttime = System.currentTimeMillis();
        int state = meeting.getState();
        if(currenttime < startTime){
            holder.tv_state.setText("待开始");
            holder.tv_state.setTextColor(UIUtils.getColor(R.color.gray));
            holder.tv_state.setBackgroundResource(R.drawable.shape_gray_stroke);
            holder.tv_join.setVisibility(View.INVISIBLE);
        }else if(startTime<=currenttime && currenttime<=endTime){
            holder.tv_state.setText("进行中");
            holder.tv_state.setTextColor(Color.parseColor("#FDBB43"));
            holder.tv_state.setBackgroundResource(R.drawable.shape_ing_stroke);
            holder.tv_join.setVisibility(View.VISIBLE);
        }else{
            holder.tv_state.setText("已结束");
            holder.tv_state.setTextColor(UIUtils.getColor(R.color.red));
            holder.tv_state.setBackgroundResource(R.drawable.shape_red_stroke);
            holder.tv_join.setVisibility(View.INVISIBLE);
        }
//        int state = meeting.getState();
//        if(state == 0){
//            holder.tv_state.setText("待开始");
//            holder.tv_state.setTextColor(UIUtils.getColor(R.color.gray));
//            holder.tv_state.setBackgroundResource(R.drawable.shape_gray_stroke);
//            holder.tv_join.setVisibility(View.INVISIBLE);
//        }else if(state == 1){
//            holder.tv_state.setText("正在进行");
//            holder.tv_state.setTextColor(Color.parseColor("#FDBB43"));
//            holder.tv_state.setBackgroundResource(R.drawable.shape_ing_stroke);
//            holder.tv_join.setVisibility(View.VISIBLE);
//        }else if(state == 2){
//            holder.tv_state.setText("已结束");
//            holder.tv_state.setTextColor(UIUtils.getColor(R.color.red));
//            holder.tv_state.setBackgroundResource(R.drawable.shape_red_stroke);
//            holder.tv_join.setVisibility(View.INVISIBLE);
//        }

        return view;
    }


    class HyrcHolder{
        TextView tv_time;
        TextView tv_des;
        TextView tv_organizer;
        TextView tv_join;
        TextView tv_state;
        

    }


}
