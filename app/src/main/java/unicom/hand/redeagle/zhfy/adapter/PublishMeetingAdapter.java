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
import unicom.hand.redeagle.zhfy.bean.ConferenceBean;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

/**
 * Created by linana on 2018-04-26.
 */

public class PublishMeetingAdapter extends BaseAdapter {
    Context context;
    List<ConferenceBean> listbeans;
    public PublishMeetingAdapter(Context context,List<ConferenceBean> listbeans){
        this.context = context;
        this.listbeans = listbeans;
    }
    @Override
    public int getCount() {
        return listbeans.size();
    }

    @Override
    public Object getItem(int i) {
        return listbeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PublishMeetHolder holder;
        if(view == null){
            holder = new PublishMeetHolder();
            view = View.inflate(context, R.layout.item_newmeet,null);
            holder.tv_title = (TextView)view.findViewById(R.id.tv_title);
            holder.ty_type = (TextView)view.findViewById(R.id.ty_type);
            holder.tv_time = (TextView)view.findViewById(R.id.tv_time);
            holder.tv_status = (TextView)view.findViewById(R.id.tv_status);
            holder.iv_img = (ImageView)view.findViewById(R.id.iv_img);
            view.setTag(holder);
        }else{
            holder = (PublishMeetHolder)view.getTag();
        }
        ConferenceBean conferenceBean = listbeans.get(i);
        String title = conferenceBean.getTitle();
        holder.tv_title.setText(title);
        String gmtCreate = conferenceBean.getGmtStart();
        holder.tv_time.setText("会议时间:"+gmtCreate);
        int type = conferenceBean.getType();
//        String typeText = getTypeText(type);
//        holder.ty_type.setText(typeText);

        int status = conferenceBean.getStatus();
        String statusText = getStatusText(status);
        holder.tv_status.setText(statusText);
        if(status ==1){
            holder.tv_status.setTextColor(UIUtils.getColor(R.color.wzk));
        }else{
            holder.tv_status.setTextColor(UIUtils.getColor(R.color.yzk));
        }
        if(type == 1){
            holder.iv_img.setImageResource(R.drawable.shyk_dydh);
        }else if(type == 2){
            holder.iv_img.setImageResource(R.drawable.shyk_wyh);
        }else if(type == 3){
            holder.iv_img.setImageResource(R.drawable.shyk_xzh);
        }else if(type == 4){
            holder.iv_img.setImageResource(R.drawable.shyk_dk);
        }


        return view;
    }
    public String getStatusText(int type){
        String text = "未召开";
        if(type == 1){
            text = "已召开";
        }else if(type == 2){
            text = "未召开";
        }
        return text;
    }

    public String getTypeText(int type){
        String text = "";
        if(type == 1){
            text = "支部党员大会";
        }else if(type == 2){
            text = "支部委员会";
        }if(type == 3){
            text = "党小组会";
        }if(type == 4){
            text = "党课";
        }
        return text;

    }

    class PublishMeetHolder{
        TextView tv_title;
        TextView ty_type;
        TextView tv_time;
        TextView tv_status;
        ImageView iv_img;



    }


}
