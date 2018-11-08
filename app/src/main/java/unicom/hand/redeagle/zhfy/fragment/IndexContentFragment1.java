package unicom.hand.redeagle.zhfy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.adapter.IndexNewsAdapter;
import unicom.hand.redeagle.zhfy.bean.IndexNewsBean;
import unicom.hand.redeagle.zhfy.bean.ResultBaseBean;
import unicom.hand.redeagle.zhfy.bean.ResultBaseBean1;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.GsonUtils;
import unicom.hand.redeagle.zhfy.utils.UIUtils;
import unicom.hand.redeagle.zhfy.view.MyListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexContentFragment1 extends LazyBaseFragment {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
//                toast("数据异常,请稍后再试");
                mPullRefreshScrollView.onRefreshComplete();
            }else if(msg.what == 1){
                mPullRefreshScrollView.onRefreshComplete();
//                setListViewHeight(lv_news);
                if(noticeAdapter != null){
                    noticeAdapter.notifyDataSetChanged();
                }
            }

        }
    };
    private String type = "";
    private int rows = 10;
    private int pageNo = 1;
    private MyListView lv_news;
    List<IndexNewsBean> listbeans;
    private IndexNewsAdapter noticeAdapter;
    private PullToRefreshScrollView mPullRefreshScrollView;
    public IndexContentFragment1() {
        // Required empty public constructor
    }




    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_index_content_fragment1;
    }

    @Override
    protected void initView(View v) {
        listbeans = new ArrayList<>();
        lv_news = (MyListView)v.findViewById(R.id.lv_news);
        String imgtype = "0";
        if(TextUtils.equals(type,"34")){
            imgtype = "0";
        }else if(TextUtils.equals(type,"35")){
            imgtype = "1";
        }else if(TextUtils.equals(type,"42")){
            imgtype = "2";
        }else if(TextUtils.equals(type,"37")){
            imgtype = "3";
        }else if(TextUtils.equals(type,"2")){
            imgtype = "4";
        }
        noticeAdapter = new IndexNewsAdapter(UIUtils.getContext(), listbeans,imgtype);
        lv_news.setAdapter(noticeAdapter);

        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                IndexNewsBean indexNewsBean = (IndexNewsBean)adapterView.getAdapter().getItem(i);

                Intent intent = new Intent(getActivity(),IndexNewsDetailActivity.class);
                intent.putExtra("id",indexNewsBean.getId()+"");
                startActivity(intent);
            }
        });
        mPullRefreshScrollView = (PullToRefreshScrollView) v.findViewById(R.id.pull_refresh_scrollview);
        // 刷新label的设置
        mPullRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间" );
        mPullRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel("下拉刷新");
        mPullRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新" );
        // 上拉、下拉设定
        mPullRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if(listbeans != null && listbeans.size()>0){
                    listbeans.clear();
                    if(noticeAdapter != null){
                        noticeAdapter.notifyDataSetChanged();
                    }
                }
                pageNo = 1;
                getData();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                pageNo++;
                getData();
            }
        });
        getData();


    }

    private void getData() {

        AppApplication.getDataProvider().getIndexNews(type, pageNo, rows, new AjaxCallBack<Object>() {

            @Override
            public boolean isProgress() {
                return super.isProgress();
            }

            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);

//                Log.e("aaa",type+"首页:"+ o.toString());

                if(o != null) {
                    try {
                        JSONObject object = new JSONObject(o.toString());
                        ResultBaseBean1 result = GsonUtils.getBean(o.toString(), ResultBaseBean1.class);
                        if (result != null && result.getCode() == 0) {

                            try {
//                                JSONArray array = new JSONArray(result.getData().toString());
                                Log.e("aaa",type+"首页:"+  GsonUtils.getJson(result.getData()));
                                List<IndexNewsBean> beans = GsonUtils.getBeans(GsonUtils.getJson(result.getData()), IndexNewsBean.class);
                                if(beans != null){
                                    if(beans.size()>0){
                                        listbeans.addAll(beans);
                                        handler.sendEmptyMessage(1);
                                    }else{
                                        toast("没有请求到数据");
                                        mPullRefreshScrollView.onRefreshComplete();
                                    }

                                }else{
                                    handler.sendEmptyMessage(0);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                handler.sendEmptyMessage(0);
                            }

                        }else{
                            handler.sendEmptyMessage(0);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(0);
                    }


                }





            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                handler.sendEmptyMessage(0);
            }
        });



    }

    @Override
    public void setTitleRightClick() {

    }

    public void setType(String type){
        this.type = type;

    }

}
