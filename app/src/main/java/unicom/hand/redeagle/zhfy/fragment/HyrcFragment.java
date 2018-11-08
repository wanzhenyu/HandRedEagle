package unicom.hand.redeagle.zhfy.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yealink.common.data.Meeting;
import com.yealink.sdk.MeetingListener;
import com.yealink.sdk.YealinkApi;

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.adapter.HyrcAdapter;
import unicom.hand.redeagle.zhfy.adapter.HyrcMd5Adapter;
import unicom.hand.redeagle.zhfy.bean.DeleteMeetBean;
import unicom.hand.redeagle.zhfy.bean.HyrcBeanMd51;
import unicom.hand.redeagle.zhfy.bean.MyCityBean2;
import unicom.hand.redeagle.zhfy.bean.QueryMeetingBean;
import unicom.hand.redeagle.zhfy.bean.QueryMeetingBean1;
import unicom.hand.redeagle.zhfy.ui.LookVideoMeetingActivity;
import unicom.hand.redeagle.zhfy.ui.MeetDetailActivity;
import unicom.hand.redeagle.zhfy.ui.MeetingRecoderActivity;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.GsonUtils;
import unicom.hand.redeagle.zhfy.utils.UIUtils;
import unicom.hand.redeagle.zhfy.view.MyListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HyrcFragment extends Fragment {

    private HyrcMd5Adapter hyrcadapter;
    private List<HyrcBeanMd51> hyrcbeans;
    private MyListView lv_hyrc;
    List<Meeting> lists;
    private int intentstate;

    private PullToRefreshScrollView mPullRefreshScrollView;
    int PageIndex = 1;
    int PageCount = 10;
    private String type = "1";
    private ImageView iv_zw;
    private ProgressDialog progressdialog;

    public HyrcFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hyrc, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressdialog = new ProgressDialog(getActivity(),ProgressDialog.THEME_HOLO_LIGHT);
        progressdialog.setMessage("正在删除...");

        lv_hyrc = (MyListView)view.findViewById(R.id.lv_hyrc);
        lists = new ArrayList<>();
        iv_zw = (ImageView)view.findViewById(R.id.iv_zw);
        intentstate = getActivity().getIntent().getIntExtra("state", 1);
        ImageView common_title_left = (ImageView)view.findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
//        List<Meeting> meetingList = YealinkApi.instance().getMeetingList();
//        String json = GsonUtil.getJson(meetingList);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                List<Meeting> list = YealinkApi.instance().getMeetingList();
//                if(list != null && list.size()>0){
//                    lists.clear();
//                    lists.addAll(list);
//                    String json = GsonUtil.getJson(lists);
//
//                    Log.e("aaa","会议日程："+json);
//                    lv_hyrc.setAdapter(new HyrcAdapter(getActivity(),lists));
//                }
//
//
//
//                YealinkApi.instance().addMeetingListener(new MeetingListener() {
//                    @Override
//                    public void onMeetingListUpdate() {
//                        super.onMeetingListUpdate();
//                        List<Meeting> list = YealinkApi.instance().getMeetingList();
//                        if(list != null && list.size()>0){
//                            lists.clear();
//                            lists.addAll(list);
//                            lv_hyrc.setAdapter(new HyrcAdapter(getActivity(),lists));
//                        }
//                        String json = GsonUtil.getJson(list);
//
//                        Log.e("aaa","会议日程："+json);
//                    }
//
//
//                });
//            }
//        }, 3000);
        TextView tv_title = (TextView)view.findViewById(R.id.tv_title);
        String title = getActivity().getIntent().getStringExtra("title");
        tv_title.setText(title);
        type = getActivity().getIntent().getStringExtra("type");


        hyrcbeans = new ArrayList<>();
        hyrcadapter = new HyrcMd5Adapter(getActivity(),hyrcbeans);

        lv_hyrc.setAdapter(hyrcadapter);
        lv_hyrc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HyrcBeanMd51 HyrcBeanMd51 = (HyrcBeanMd51)adapterView.getAdapter().getItem(i);
                Integer state = HyrcBeanMd51.getState();
                Intent intent = null;
                if(state == 1 || state == 0){
                     intent = new Intent(getActivity(), MeetDetailActivity.class);
                }else{
                    intent = new Intent(getActivity(), LookVideoMeetingActivity.class);
                }
                intent.putExtra("type",type);
                intent.putExtra("confId",HyrcBeanMd51.getConferenceRecordId());
                startActivityForResult(intent,1);
            }
        });
        mPullRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);
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


        lv_hyrc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                showMyDialog(i);

                return true;
            }
        });





    }
    public void showMyDialog(final int pos) {


        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle("提示");
        builder.setMessage("确定要删除？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int poss) {
                deleteItem(pos);




            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        // 设置参数
        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void deleteItem(final int pos) {
        progressdialog.show();
        HyrcBeanMd51 hyrcBeanMd51 = hyrcbeans.get(pos);
        DeleteMeetBean deletebean = new DeleteMeetBean();
        deletebean.setConferencePlanId(hyrcBeanMd51.getConferencePlanId());
        deletebean.setConferenceRecordId(hyrcBeanMd51.getConferenceRecordId());
        String json = GsonUtil.getJson(deletebean);
        AppApplication.getDataProvider().deleteMeetingList(json, new AjaxCallBack<Object>() {

            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                progressdialog.dismiss();
                Log.e("bbb","删除会议:"+o.toString());
                try {
                    if(o != null) {
                        JSONObject object = new JSONObject(o.toString());
                        int ret = object.getInt("ret");
                        if (ret == 1) {

                            hyrcbeans.remove(pos);
                            hyrcadapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                progressdialog.dismiss();
                Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        if(hyrcbeans != null && hyrcbeans.size()>0){
            hyrcbeans.clear();
            if(hyrcadapter != null){
                hyrcadapter.notifyDataSetChanged();
            }
        }
        getData1();
    }

    private void getData1() {
        String json = "";
        if(intentstate == 1){
            QueryMeetingBean1 queryMeeting = new QueryMeetingBean1();
            queryMeeting.setPageNo(PageIndex);
            queryMeeting.setPageSize(PageCount);
            List<Integer> seartypes = new ArrayList<>();
            seartypes.add(0);
            seartypes.add(1);
            queryMeeting.setSearchType(seartypes);
            queryMeeting.setQueryDate(System.currentTimeMillis());
             json = GsonUtil.getJson(queryMeeting);
        }else{
            QueryMeetingBean queryMeeting = new QueryMeetingBean();
            queryMeeting.setPageNo(PageIndex);
            queryMeeting.setPageSize(PageCount);
            queryMeeting.setSearchType(intentstate);
            queryMeeting.setQueryDate(System.currentTimeMillis());
            json = GsonUtil.getJson(queryMeeting);
        }


        AppApplication.getDataProvider().getMeetingList1(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("bbb","查询1md5会议:"+o.toString());
                if(o != null) {
                    try {
                        JSONObject object = new JSONObject(o.toString());
                        int ret = object.getInt("ret");
                        if(ret ==1){
                            iv_zw.setVisibility(View.GONE);
                            mPullRefreshScrollView.setVisibility(View.VISIBLE);
                            JSONObject data = object.getJSONObject("data");
//                            JSONObject rows = data.getJSONObject("rows");
                            JSONObject pageModel = data.getJSONObject("pageModel");
                            JSONArray conferences = pageModel.getJSONArray("records");
                            List<HyrcBeanMd51> beans = GsonUtils.getBeans(conferences.toString(), HyrcBeanMd51.class);
                            if(beans != null){
                                if(beans.size()>0){
                                    for (int i=0;i<beans.size();i++){
                                        HyrcBeanMd51 HyrcBeanMd51 = beans.get(i);
                                        Integer state = HyrcBeanMd51.getState();
                                        String title = HyrcBeanMd51.getSubject();

                                        if(state == intentstate){
                                            if(UIUtils.getListByTitle(type,title)){
                                                hyrcbeans.add(HyrcBeanMd51);
                                            }

                                        }else{
                                            if(state == 0){
                                                if(UIUtils.getListByTitle("0",title)){
                                                    hyrcbeans.add(HyrcBeanMd51);
                                                }
                                            }


                                        }

                                    }
                                    if(hyrcbeans.size()<=0){
                                        mPullRefreshScrollView.setVisibility(View.GONE);
                                        iv_zw.setVisibility(View.VISIBLE);
                                        Toast.makeText(getActivity(), "没有请求到数据", Toast.LENGTH_SHORT).show();
                                    }

                                    if(hyrcadapter != null){
                                        hyrcadapter.notifyDataSetChanged();
                                    }

                                }else{
                                    if(hyrcbeans.size()<=0){
                                        mPullRefreshScrollView.setVisibility(View.GONE);
                                        iv_zw.setVisibility(View.VISIBLE);

                                    }
                                    Toast.makeText(getActivity(), "没有请求到数据", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                mPullRefreshScrollView.setVisibility(View.GONE);
                                iv_zw.setVisibility(View.VISIBLE);
                            }
                        }else{
                            mPullRefreshScrollView.setVisibility(View.GONE);
                            iv_zw.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "获取列表失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        if(hyrcbeans.size()<=0){
                            mPullRefreshScrollView.setVisibility(View.GONE);
                            iv_zw.setVisibility(View.VISIBLE);

                        }
                        Toast.makeText(getActivity(), "没有请求到数据", Toast.LENGTH_SHORT).show();
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












}
