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
import unicom.hand.redeagle.view.AutoPlayManager;
import unicom.hand.redeagle.zhfy.adapter.ImageGridViewAdapter;
import unicom.hand.redeagle.zhfy.bean.ItemBean;
import unicom.hand.redeagle.zhfy.ui.WebActivity;
import unicom.hand.redeagle.view.ImageIndicatorView;
import unicom.hand.redeagle.zhfy.view.MyGridView;

import java.util.ArrayList;

public class Fragment_spzb extends Fragment {

    private static final String TAG = "Fragment0";
    MyGridView gridView1,gridView2;
    private ImageIndicatorView imageIndicatorView;


    public static Fragment_spzb newInstance() {
        // TODO Auto-generated method stub
        Fragment_spzb fragment = new Fragment_spzb();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView ");
        if (mView == null) {
         mView = inflater.inflate(R.layout.fragment_spzb, container,
                    false);
            gridView1 = (MyGridView) mView.findViewById(R.id.gridView1);
            gridView2 = (MyGridView) mView.findViewById(R.id.gridView2);
            gridView2.setAdapter(new ImageGridViewAdapter(getActivity(), getDate2()));
            gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("url",urls2[position]);
                    intent.putExtra("title",girdNames2[position]);
                    startActivity(intent);

                }
            });

            gridView1.setAdapter(new ImageGridViewAdapter(getActivity(), getDate()));
            gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("url",urls[position]);
                    intent.putExtra("title",girdNames[position]);
                    startActivity(intent);

                }
            });

            imageIndicatorView = (ImageIndicatorView) mView
                    .findViewById(R.id.indicate_view);
            final Integer[] resArray = new Integer[] {
                    R.mipmap.banner0,R.mipmap.banner1,
                    R.mipmap.banner2 };
            imageIndicatorView.setupLayoutByDrawable(resArray);
            imageIndicatorView.show();
            AutoPlayManager autoPlayManager = new AutoPlayManager(
                    imageIndicatorView);
            autoPlayManager.setBroadcastEnable(true);
            // autoPlayManager.setBroadCastTimes(5);// 循环播放5次
            autoPlayManager.setBroadcastTimeIntevel(3 * 1000, 3 * 1000);// 播放启动时间及间隔
            autoPlayManager.loop();

//            社区信息
//            http://115.159.41.252:8088/zhfyApp/zhfy1.html
//            物业服务
//            http://115.159.41.252:8088/zhfyApp/zhfy2.html
//            阳光福源
//            http://115.159.41.252:8088/zhfyApp/zhfy3.html
//            一键求助
//            http://115.159.41.252:8088/zhfyApp/zhfy4.html
//            社区实景
//            http://115.159.41.252:8088/zhfyApp/zhfy5.html
        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }


        return mView;
    }

    private View mView;
    String[] urls = {"http://42.236.68.190:8090/zshy_web/zshy/DB/tv_live_central_work.html",
            "http://42.236.68.190:8090/zshy_web/zshy/DB/tv_live_live_broadcast.html",
            "http://42.236.68.190:8090/zshy_web/zshy/DB/tv_live_reading_aloud.html",
            "http://42.236.68.190:8090/zshy_web/zshy/DB/tv_live_watch_tv.html"
    };


    String[] girdNames = {"中心工作","现场直播", "书声朗朗",
            "看电视"
    };
    int[] gridIds = {
            R.mipmap.tv_live_0,
            R.mipmap.tv_live_1,
            R.mipmap.tv_live_2,
            R.mipmap.tv_live_3,

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
    String[] urls2 = {"http://42.236.68.190:8090/zshy_web/zshy/DB/wugang_party_building.html",
            "http://42.236.68.190:8090/zshy_web/zshy/DB/wugang_town_mien.html",
            "http://42.236.68.190:8090/zshy_web/zshy/DB/wugang_handheld_live.html",
            "http://42.236.68.190:8090/zshy_web/zshy/DB/wugang_distance_education.html"
    };


    String[] girdNames2 = {"舞钢党建","镇办风采", "掌上直播",
            "远程教育"
    };
    int[] gridIds2 = {
            R.mipmap.wugang_party_building,
            R.mipmap.wugang_town_mien,
            R.mipmap.wugang_handheld_live,
            R.mipmap.wugang_distance_education,

    };



    private ArrayList<ItemBean> getDate2() {
        ArrayList<ItemBean> beans = new ArrayList<ItemBean>();
        // TODO Auto-generated method stub
        for (int i = 0; i < girdNames.length; i++) {
            ItemBean bean = new ItemBean();
            bean.setName(girdNames2[i]);
            bean.setIcon(gridIds2[i]);
            beans.add(bean);
        }
        return beans;
    }
}
