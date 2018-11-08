package unicom.hand.redeagle.zhfy.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.bean.FaceBean;

import java.util.ArrayList;
import java.util.List;


public class FaceAdapter extends BaseAdapter {
    private Context mContext;
    private List<FaceBean> mList;

    public FaceAdapter(Context c) {
        mContext = c;
        mList = new ArrayList<FaceBean>();
    }


    public void setDate(List<FaceBean> list) {
        mList = list;
        notifyDataSetChanged();
    }


    private CalInterface calInterface;

    public interface CalInterface {
        void onVedeoCallClick(FaceBean bean);
    }

    public void setCalInterface(CalInterface calInterface) {
        this.calInterface = calInterface;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        View view = View.inflate(mContext, R.layout.list_item_face, null);
        RelativeLayout rl = (RelativeLayout) view
                .findViewById(R.id.RelativeLayout);
        TextView text = (TextView) rl.findViewById(R.id.name);
        text.setText(mList.get(position).getCompany());
        ImageView image = (ImageView) rl.findViewById(R.id.video);
        ImageView icon_image = (ImageView) rl.findViewById(R.id.img);
        if (position!=0){
            icon_image.setBackgroundResource(R.mipmap.hotline_community);
        }else {
            icon_image.setBackgroundResource(R.mipmap.hotline_comprehensive_control);
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calInterface.onVedeoCallClick(mList.get(position));
            }
        });


        return rl;
    }


    @Override
    public FaceBean getItem(int arg0) {
        // TODO Auto-generated method stub
        return mList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

}
