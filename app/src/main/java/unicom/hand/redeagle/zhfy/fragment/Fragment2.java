package unicom.hand.redeagle.zhfy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.adapter.ImageGridViewAdapter;
import unicom.hand.redeagle.zhfy.bean.ItemBean;
import unicom.hand.redeagle.zhfy.ui.CallActivity;
import unicom.hand.redeagle.zhfy.ui.MyDepartMentActivity3;
import unicom.hand.redeagle.zhfy.ui.MyFragmentActivity;
import unicom.hand.redeagle.zhfy.view.CallDialog;
import unicom.hand.redeagle.zhfy.view.MyGridView;

import java.util.ArrayList;

public class Fragment2 extends Fragment {

    private static final String TAG = "Fragment0";
    MyGridView gridView1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView ");
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_2, container,
                    false);
            gridView1 = (MyGridView) mView.findViewById(R.id.gridView1);
            gridView1.setAdapter(new ImageGridViewAdapter(getActivity(), getDate()));


            gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = null;
                    if (position==0){

                        CallDialog callDialog = new CallDialog(getActivity());
                        callDialog.show();
                    }else if(position==1){
                        intent = new Intent(getActivity(), CallActivity.class);
//                        intent.putExtra("type", MyFragmentActivity.FA_CALL);
                        startActivity(intent);

                    }else if (position==3){
                        intent = new Intent(getActivity(), MyDepartMentActivity3.class);
                        startActivity(intent);
                    }
                    else if (position==2){
                        intent = new Intent(getActivity(), MyFragmentActivity.class);
                        intent.putExtra("type", MyFragmentActivity.FA_CONTACT);
                        startActivity(intent);
                    }else{
                        intent = new Intent(getActivity(), MyFragmentActivity.class);
                        intent.putExtra("type", "");
                        startActivity(intent);
                    }

                }
            });

        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }


        return mView;
    }

    private View mView;



    String[] girdNames = {"加入会议", "视频通话", "通讯录","立即会议","会议日程"

    };
    int[] gridIds = {
            R.mipmap.video_join_meeting,
            R.mipmap.item_xxc,
            R.mipmap.item_hyzb,
            R.mipmap.video_meeting_instant_meeting,
            R.mipmap.video_03,
    };


    private ArrayList<ItemBean> getDate() {
        ArrayList<ItemBean> beans = new ArrayList<ItemBean>();
        // TODO Auto-generated method stub
        for (int i = 0; i < girdNames.length; i++) {
            ItemBean bean = new ItemBean();
            bean.setName(girdNames[i]);
            bean.setIcon(gridIds[i]);
            beans.add(bean);
        }
        return beans;
    }
}
