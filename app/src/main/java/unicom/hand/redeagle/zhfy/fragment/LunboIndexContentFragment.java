package unicom.hand.redeagle.zhfy.fragment;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.squareup.picasso.Picasso;

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.Common;
import unicom.hand.redeagle.zhfy.adapter.IndexNewsAdapter;
import unicom.hand.redeagle.zhfy.bean.IndexNewsBean;
import unicom.hand.redeagle.zhfy.bean.PicNewsBean;
import unicom.hand.redeagle.zhfy.bean.ResultBaseBean1;
import unicom.hand.redeagle.zhfy.ui.PicNewsDetailActivity;
import unicom.hand.redeagle.zhfy.utils.GsonUtils;
import unicom.hand.redeagle.zhfy.utils.UIUtils;
import unicom.hand.redeagle.zhfy.view.MyListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LunboIndexContentFragment extends LazyBaseFragment {

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
            }else if(msg.what == 2){
                initRadioButton(picNewsList.size());
                viewPager.setAdapter(pagerAdapter);
            }else if(msg.what == 3){
                index++;
                viewPager.setCurrentItem(index);
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

    private List<PicNewsBean> picNewsList;
    private int index = 0,preIndex = 0;
    //是否需要轮播标志
    private boolean isContinue = true;
    //定时器，用于实现轮播
    private Timer timer;
    private RadioGroup group;
    private ViewPager viewPager;
    private TextView tv_title;

    public LunboIndexContentFragment() {
        // Required empty public constructor
    }




    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_index_content_fragment_lunbo;
    }

    @Override
    protected void initView(View v) {
        listbeans = new ArrayList<>();
        picNewsList = new ArrayList<>();
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

        tv_title = (TextView)v.findViewById(R.id.tv_title);
        viewPager = (ViewPager) v.findViewById(R.id.vp_index);
//        final DESUtil des = DESUtil.getInstance();
        viewPager.addOnPageChangeListener(onPageChangeListener);
        viewPager.setOnTouchListener(onTouchListener);
        group = (RadioGroup) v.findViewById(R.id.group);


        getData();
        getPicNews();
        timer = new Timer();//创建Timer对象
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //首先判断是否需要轮播，是的话我们才发消息
                if (isContinue) {
                    handler.sendEmptyMessage(3);
                }
            }
        },3000,3000);//延迟2秒，每隔2秒发一次消息

    }

    /**
     * 获取轮播图
     */

    private void getPicNews() {
        AppApplication.getDataProvider().getPicList(new AjaxCallBack<Object>() {

            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                if(o != null) {
                    try {
                        JSONObject object = new JSONObject(o.toString());
                        ResultBaseBean1 result = GsonUtils.getBean(o.toString(), ResultBaseBean1.class);
                        if (result != null && result.getCode() == 0) {

                            try {
//                                JSONArray array = new JSONArray(result.getData().toString());
                                List<PicNewsBean> beans = GsonUtils.getBeans(GsonUtils.getJson(result.getData()), PicNewsBean.class);
                                if(beans != null){
                                    if(beans.size()>0){
                                        picNewsList.addAll(beans);
                                        handler.sendEmptyMessage(2);
                                    }else{
                                    }

                                }else{
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else{
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });

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

                Log.e("aaa",type+"首页:"+ o.toString());

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
    /**
     * 根据图片个数初始化按钮
     * @param length
     */
    private void initRadioButton(int length) {
        if(group != null){
            group.removeAllViews();
        }
        for(int i = 0;i<length;i++){
            ImageView imageview = new ImageView(getActivity());
            imageview.setImageResource(R.drawable.rg_selector);//设置背景选择器
            imageview.setPadding(20,0,0,0);//设置每个按钮之间的间距
            //将按钮依次添加到RadioGroup中
            group.addView(imageview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //默认选中第一个按钮，因为默认显示第一张图片
            group.getChildAt(0).setEnabled(false);
        }
    }
    /**
     * 根据当前触摸事件判断是否要轮播
     */
    View.OnTouchListener onTouchListener  = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                //手指按下和划动的时候停止图片的轮播
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    isContinue = false;
                    break;
                default:
                    isContinue = true;
            }
            return false;
        }
    };
    /**
     *根据当前选中的页面设置按钮的选中
     */
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {

            if(picNewsList != null && picNewsList.size()>0){
                index = position;//当前位置赋值给索引
                int pos = index%picNewsList.size();
                tv_title.setText(picNewsList.get(pos).getTitle());
                setCurrentDot(pos);
            }

        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 设置对应位置按钮的状态
     * @param i 当前位置
     */
    private void setCurrentDot(int i) {
        if(group.getChildAt(i)!=null){
            group.getChildAt(i).setEnabled(false);//当前按钮选中
        }
        if(group.getChildAt(preIndex)!=null){
            group.getChildAt(preIndex).setEnabled(true);//上一个取消选中
            preIndex = i;//当前位置变为上一个，继续下次轮播
        }
    }
    PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            //返回一个比较大的值，目的是为了实现无限轮播
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int positions) {
            ImageView imageView = new ImageView(getActivity());
            if(picNewsList != null && picNewsList.size() >0){
               final int position = positions%picNewsList.size();

                final PicNewsBean advertBean = picNewsList.get(position);
//            Log.e("aaa","轮播图图片路径："+advertBean.getPiclist());
                Picasso.with(getActivity()).load(Common.BASE_URL+advertBean.getPicAddress()).placeholder(R.drawable.zanwuneirong).into(imageView);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        PicNewsBean picNewsBean = picNewsList.get(position);
                        Intent intent = new Intent(getActivity(),PicNewsDetailActivity.class);
                        intent.putExtra("content",picNewsBean.getContent()+"");
                        startActivity(intent);
//                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
//                        intent.putExtra("url", CommonUtils.BASEURL+advertBean.getDetailurl());
//                        intent.putExtra("title", "详情");
//                        startActivity(intent);



                    }
                });
            }



            return imageView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    };
}
