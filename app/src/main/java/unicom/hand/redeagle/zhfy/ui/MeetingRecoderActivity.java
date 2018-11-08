package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
import unicom.hand.redeagle.zhfy.PublishMeetingActivity;
import unicom.hand.redeagle.zhfy.adapter.HyrcMd5Adapter;
import unicom.hand.redeagle.zhfy.adapter.PublishMeetingAdapter;
import unicom.hand.redeagle.zhfy.bean.ConferenceBean;
import unicom.hand.redeagle.zhfy.bean.HyrcBeanMd5;
import unicom.hand.redeagle.zhfy.bean.HyrcBeanMd51;
import unicom.hand.redeagle.zhfy.bean.IndexNewsBean;
import unicom.hand.redeagle.zhfy.bean.QueryMeetingBean;
import unicom.hand.redeagle.zhfy.bean.ResultBaseBean;
import unicom.hand.redeagle.zhfy.bean.ResultBaseBean1;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.GsonUtils;
import unicom.hand.redeagle.zhfy.utils.UIUtils;
import unicom.hand.redeagle.zhfy.view.MyListView;

public class MeetingRecoderActivity extends Activity {
//    private List<ConferenceBean> listbeans;
//    private PublishMeetingAdapter publishMeetingAdapter;
    private HyrcMd5Adapter hyrcadapter;
    private List<HyrcBeanMd51> hyrcbeans;
    private String title;
    private String type = "1";
    private String edittype = "look";
    private PullToRefreshScrollView mPullRefreshScrollView;
    int PageIndex = 1;
    int PageCount = 10;
    private ImageView iv_zw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_recoder);
//        listbeans = new ArrayList<>();
        iv_zw = (ImageView)findViewById(R.id.iv_zw);
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title = getIntent().getStringExtra("title");
        edittype = getIntent().getStringExtra("edittype");
        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText(title);
        MyListView lv_meeting = (MyListView)findViewById(R.id.lv_meeting);
        hyrcbeans = new ArrayList<>();
        hyrcadapter = new HyrcMd5Adapter(MeetingRecoderActivity.this,hyrcbeans);
//        publishMeetingAdapter = new PublishMeetingAdapter(MeetingRecoderActivity.this, listbeans);
        lv_meeting.setAdapter(hyrcadapter);
        if(TextUtils.equals(title,"支部党员大会")){
            type = "1";
        }else if(TextUtils.equals(title,"支部委员会")){
            type = "2";
        }else if(TextUtils.equals(title,"党小组会")){
            type = "3";
        }else if(TextUtils.equals(title,"党课")){
            type = "4";
        }

        lv_meeting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                HyrcBeanMd51 conferenceBean = (HyrcBeanMd51)adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(MeetingRecoderActivity.this,LookMeetingMd5Activity.class);
                intent.putExtra("item",conferenceBean);
                intent.putExtra("type",type);
                startActivity(intent);

            }
        });
        TextView tv_publish = (TextView)findViewById(R.id.tv_publish);
        tv_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.equals(title,"支部党员大会")){
                    type = "1";
                }else if(TextUtils.equals(title,"支部委员会")){
                    type = "2";
                }else if(TextUtils.equals(title,"党小组会")){
                    type = "3";
                }else if(TextUtils.equals(title,"党课")){
                    type = "4";
                }
                Intent intent = new Intent(MeetingRecoderActivity.this,PublishMeetingActivity.class);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });

        mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
        // 刷新label的设置
        mPullRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间" );
        mPullRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel("下拉刷新");
//          mPullRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel(
//                      "refreshingLabel");
        mPullRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新" );
        // 上拉、下拉设定
        mPullRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);


        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if(hyrcbeans != null && hyrcbeans.size()>0){
                    hyrcbeans.clear();
                    if(hyrcadapter != null){
                        hyrcadapter.notifyDataSetChanged();
                    }
                }
                PageIndex = 1;
                getData1();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                PageIndex++;
                getData1();
            }
        });
        getData1();


//        getData();




    }

    private void getData1() {
        QueryMeetingBean queryMeeting = new QueryMeetingBean();
//        queryMeeting.setActive(true);
//        queryMeeting.setConfType(0);
//        queryMeeting.setOrderByField("");
//        queryMeeting.setOrderByType(1);
        queryMeeting.setPageNo(PageIndex);
        queryMeeting.setPageSize(PageCount);
//        queryMeeting.setRole("");
        queryMeeting.setSearchType(2);
//        queryMeeting.setTotal(10);
//        queryMeeting.setSearchKey("");
        queryMeeting.setQueryDate(System.currentTimeMillis());
        String json = GsonUtil.getJson(queryMeeting);
//        Log.e("bbb","查询md5会议:"+json);
        AppApplication.getDataProvider().getMeetingList1(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("bbb","查询会议记录会议:"+o.toString());
                if(o != null) {
                    try {
                        JSONObject object = new JSONObject(o.toString());
                        int ret = object.getInt("ret");
                        if(ret ==1){
                            iv_zw.setVisibility(View.GONE);
                            mPullRefreshScrollView.setVisibility(View.VISIBLE);
                            JSONObject data = object.getJSONObject("data");
                            JSONObject rows = data.getJSONObject("rows");
                            JSONObject pageModel = rows.getJSONObject("pageModel");
                            JSONArray conferences = pageModel.getJSONArray("records");
                            List<HyrcBeanMd51> beans = GsonUtils.getBeans(conferences.toString(), HyrcBeanMd51.class);
                            if(beans != null){
                                if(beans.size()>0){
                                    for (int i=0;i<beans.size();i++){
                                        HyrcBeanMd51 hyrcBeanMd5 = beans.get(i);
                                        Integer state = hyrcBeanMd5.getState();
                                        String title1 = hyrcBeanMd5.getSubject();
                                        if(state == 2){
                                            if(UIUtils.getListByTitle(type,title1)){
                                                hyrcbeans.add(hyrcBeanMd5);
                                            }
                                        }else{
                                        }

                                    }
                                    if(hyrcbeans.size()<=0){
                                        mPullRefreshScrollView.setVisibility(View.GONE);
                                        iv_zw.setVisibility(View.VISIBLE);
                                        Toast.makeText(MeetingRecoderActivity.this, "没有请求到数据", Toast.LENGTH_SHORT).show();
                                    }
                                    if(hyrcadapter != null){
                                        hyrcadapter.notifyDataSetChanged();
                                    }


                                }else{
                                    mPullRefreshScrollView.setVisibility(View.GONE);
                                    iv_zw.setVisibility(View.VISIBLE);
                                    Toast.makeText(MeetingRecoderActivity.this, "没有请求到数据", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                mPullRefreshScrollView.setVisibility(View.GONE);
                                iv_zw.setVisibility(View.VISIBLE);
                            }
                        }else{
                            mPullRefreshScrollView.setVisibility(View.GONE);
                            iv_zw.setVisibility(View.VISIBLE);
                            Toast.makeText(MeetingRecoderActivity.this, "获取列表失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        mPullRefreshScrollView.setVisibility(View.GONE);
                        iv_zw.setVisibility(View.VISIBLE);
                        Toast.makeText(MeetingRecoderActivity.this, "没有请求到数据", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }

                mPullRefreshScrollView.onRefreshComplete();




            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                mPullRefreshScrollView.onRefreshComplete();
                mPullRefreshScrollView.setVisibility(View.GONE);
                iv_zw.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getData() {
//        if(listbeans.size()>0){
//            listbeans.clear();
//        }
        if(TextUtils.equals(title,"支部党员大会")){
            type = "1";
        }else if(TextUtils.equals(title,"支部委员会")){
            type = "2";
        }else if(TextUtils.equals(title,"党小组会")){
            type = "3";
        }else if(TextUtils.equals(title,"党课")){
            type = "4";
        }

        AppApplication.getDataProvider().getMeetingList(type,new AjaxCallBack<Object>() {

            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);

                if(o != null) {
                    try {
                        JSONObject object = new JSONObject(o.toString());
                        ResultBaseBean result = GsonUtils.getBean(o.toString(), ResultBaseBean.class);
                        if (result != null && result.getCode() == 0) {

                            try {
//                                JSONArray array = new JSONArray(result.getData().toString());
//                                Log.e("aaa","会议列表:"+  GsonUtils.getJson(result.getData()));
                                List<ConferenceBean> beans = GsonUtils.getBeans(GsonUtils.getJson(result.getData()), ConferenceBean.class);
                                if(beans != null){
                                    if(beans.size()>0){
//                                        listbeans.addAll(beans);
//                                        if(publishMeetingAdapter != null){
//                                            publishMeetingAdapter.notifyDataSetChanged();
//                                        }

                                    }else{
                                        Toast.makeText(MeetingRecoderActivity.this, "没有请求到数据", Toast.LENGTH_SHORT).show();
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


}
