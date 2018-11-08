package unicom.hand.redeagle.zhfy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.yealink.base.util.ToastUtil;
import com.yealink.common.data.CallLogGroup;
import com.yealink.sdk.YealinkApi;

import java.util.ArrayList;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.adapter.CallLogAdapter;
import unicom.hand.redeagle.zhfy.bean.CallLogGroupBean;
import unicom.hand.redeagle.zhfy.bean.ThjlBean;
import unicom.hand.redeagle.zhfy.ui.CallLogDetailActivity;
import unicom.hand.redeagle.zhfy.ui.SphyMainActivity;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.view.PopupWindows;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dial1Fragment extends Fragment {

//    private ImageView iv_back;
//    private View mNumber0;
//    private View mNumber1;
//    private View mNumber2;
//    private View mNumber3;
//    private View mNumber4;
//    private View mNumber5;
//    private View mNumber6;
//    private View mNumber7;
//    private View mNumber8;
//    private View mNumber9;
//    private View mDot;
//    /** #号 **/
//    private View mSharp;
//    private EditText mEditText;
//    private ImageView iv_call;
//    private ImageView iv_del;
//
//    private List<ThjlBean> jsonlist;

    View inflate;
    private CallLogAdapter callLogAdapter;
    private List<CallLogGroup> callLogGroups ;
    private ListView lv_thjl;

    //    private PopupWindows popupWindows;
    public Dial1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         inflate = inflater.inflate(R.layout.fragment_dial, container, false);
        return inflate;
    }

    public View getRootView(){
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callLogGroups = new ArrayList<>();
//        popupWindows = new PopupWindows(getActivity(), view);
        initData(view);
    }

//    public void setShowPop(){
//        if(popupWindows.isShowing()){
//            popupWindows.dismiss();
//        }else{
//            popupWindows.showAtLocation(inflate, Gravity.BOTTOM,0,400);
//
//        }
//    }


    @Override
    public void onResume() {
        super.onResume();
        if(callLogGroups != null && callLogGroups.size()>0){
            callLogGroups.clear();
        }
        try {
            try {
                YealinkApi instance = YealinkApi.instance();
                if(instance != null){
                    try {
                        List<CallLogGroup> callLogGroupss = instance.searchCallLog("");
                        if(callLogGroupss != null && callLogGroupss.size()>0){
                            callLogGroups.addAll(callLogGroupss);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            if(this.callLogGroups != null && this.callLogGroups.size()>0){
                String json = GsonUtil.getJson(this.callLogGroups);
                Log.e("bbb",""+json);
                if(callLogAdapter == null){
                    callLogAdapter = new CallLogAdapter(this.callLogGroups);
                    lv_thjl.setAdapter(callLogAdapter);

                }else{
                    callLogAdapter.notifyDataSetChanged();
                }

            }else{
                Log.e("bbb",""+ this.callLogGroups.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initData(View view) {


        lv_thjl = (ListView)view.findViewById(R.id.lv_thjl);

        lv_thjl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    CallLogGroup callloggroup = (CallLogGroup)adapterView.getAdapter().getItem(i);
//                CallLogGroupBean callbean = new CallLogGroupBean();
//                callbean.setBean(callloggroup);
//                Intent intent = new Intent(getActivity(), CallLogDetailActivity.class);
//                intent.putExtra("item",callloggroup);
//                startActivity(intent);
                    YealinkApi.instance().call(getActivity(),callloggroup.getNumber());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        // TODO Auto-generated method stub
//        iv_back = (ImageView) view.findViewById(R.id.common_title_left);
//        iv_back.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                getActivity().finish();
//            }
//        });
//        jsonlist = new ArrayList<>();
//        ListView lv_thjl = (ListView)view.findViewById(R.id.lv_thjl);
//        iv_call= (ImageView)view.findViewById(R.id.iv_call2);
//        iv_del= (ImageView)view.findViewById(R.id.imageView2);
//        iv_call.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//               final String number = mEditText.getText().toString();
//                if(!TextUtils.isEmpty(number)){
//
//                    YealinkApi.instance().call(getActivity(),number);
//
//
//                    ThjlBean thjlBean = new ThjlBean();
//
//
////                    String collectjson = AppApplication.preferenceProvider.getCollectjson();
////                    if (TextUtils.isEmpty(collectjson)) {
////
//////                        jsonlist.add(bean);
////                        AppApplication.preferenceProvider.setCollectjson(GsonUtil.getJson(jsonlist));
////                    } else {
////                        Type dataType = new TypeToken<List<MyCityBean2>>() {
////                        }.getType();
////                        ArrayList<MyCityBean2> tempdata = GsonUtil.getGson().fromJson(
////                                collectjson, dataType);
////                        String iscontain = "0";
////                        for (int j = 0; j < tempdata.size(); j++) {
////                            MyCityBean2 myCityBean2 = tempdata.get(j);
////                            String id = myCityBean2.getId();
////                            if (TextUtils.equals(id, number)) {
////                                iscontain = "1";
////                                break;
////                            } else {
////                                iscontain = "0";
////                            }
////
////                        }
////                        if (TextUtils.equals(iscontain, "0")) {
////                            tempdata.add(bean);
////                            AppApplication.preferenceProvider.setCollectjson(GsonUtil.getJson(tempdata));
////                        }
////
////                    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//                } else {
//                    ToastUtil.toast(getActivity(),"号码为空！");
//                }
//            }
//        });
//        iv_del.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                mEditText.setText("");
//            }
//        });
//        mEditText = (EditText)view.findViewById(R.id.editText);
//        // 各个按键
//        mNumber0 = view.findViewById(R.id.number0);
//        mNumber0.setOnClickListener(this);
//        mNumber1 = view.findViewById(R.id.number1);
//        mNumber1.setOnClickListener(this);
//        mNumber2 = view.findViewById(R.id.number2);
//        mNumber2.setOnClickListener(this);
//        mNumber3 = view.findViewById(R.id.number3);
//        mNumber3.setOnClickListener(this);
//        mNumber4 = view.findViewById(R.id.number4);
//        mNumber4.setOnClickListener(this);
//        mNumber5 = view.findViewById(R.id.number5);
//        mNumber5.setOnClickListener(this);
//        mNumber6 = view.findViewById(R.id.number6);
//        mNumber6.setOnClickListener(this);
//        mNumber7 = view.findViewById(R.id.number7);
//        mNumber7.setOnClickListener(this);
//        mNumber8 = view.findViewById(R.id.number8);
//        mNumber8.setOnClickListener(this);
//        mNumber9 = view.findViewById(R.id.number9);
//        mNumber9.setOnClickListener(this);
//        mDot = view.findViewById(R.id.dot);
//        mDot.setOnClickListener(this);
//        mSharp = view.findViewById(R.id.sharp);
//        mSharp.setOnClickListener(this);
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.number0:
//                onInput("0");
//                break;
//            case R.id.number1:
//                onInput("1");
//                break;
//            case R.id.number2:
//                onInput("2");
//                break;
//            case R.id.number3:
//                onInput("3");
//                break;
//            case R.id.number4:
//                onInput("4");
//                break;
//            case R.id.number5:
//                onInput("5");
//                break;
//            case R.id.number6:
//                onInput("6");
//                break;
//            case R.id.number7:
//                onInput("7");
//                break;
//            case R.id.number8:
//                onInput("8");
//                break;
//            case R.id.number9:
//                onInput("9");
//                break;
//            case R.id.dot:
//                onInput("*");
//                break;
//            case R.id.sharp:
//                onInput("#");
//                break;
//            default:
//                break;
//        }
//    }
//    private void onInput(String number) {
//        mEditText.append(number);
//    }






}
