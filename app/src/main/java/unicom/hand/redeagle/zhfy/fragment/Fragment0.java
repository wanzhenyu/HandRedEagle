package unicom.hand.redeagle.zhfy.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yealink.common.data.CloudProfile;
import com.yealink.sdk.YealinkApi;

import unicom.hand.redeagle.view.AutoPlayManager;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.adapter.ImageGridViewAdapter;
import unicom.hand.redeagle.zhfy.bean.ItemBean;
import unicom.hand.redeagle.zhfy.ui.Activity_listxzfw;
import unicom.hand.redeagle.zhfy.ui.Activity_searchxzfw;
import unicom.hand.redeagle.zhfy.ui.AddItemXingZhengFuWuActivity;
import unicom.hand.redeagle.zhfy.ui.DangWuFuWuActivity;
import unicom.hand.redeagle.zhfy.ui.ItemXingZhengFuWuActivity;
import unicom.hand.redeagle.zhfy.ui.LoginMdmActivity;
import unicom.hand.redeagle.zhfy.ui.MdmItemActivity;
import unicom.hand.redeagle.zhfy.ui.SelectCityActivity;
import unicom.hand.redeagle.zhfy.ui.SelectXzfwActivity;
import unicom.hand.redeagle.zhfy.ui.ShiZhiOrXianJiActivity;
import unicom.hand.redeagle.zhfy.ui.XingZhengFuWuActivity;
import unicom.hand.redeagle.zhfy.ui.XingZhengFuWuRootActivity;
import unicom.hand.redeagle.zhfy.ui.old.Activity_Face2Face;
import unicom.hand.redeagle.zhfy.ui.old.Activity_list;
import unicom.hand.redeagle.zhfy.ui.old.Activity_search;
import unicom.hand.redeagle.zhfy.ui.old.SelectActivity;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.view.DialogErWeiMa;
import unicom.hand.redeagle.view.ImageIndicatorView;
import unicom.hand.redeagle.zhfy.view.MyGridView;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Fragment0 extends Fragment {

    private static final String TAG = "TestFragment";
    private ImageIndicatorView imageIndicatorView;
    private DialogErWeiMa dialog_version;
    private List<ItemBean> beans;
    private MyGridView gridView;
    private LinearLayout iv_search;
    private TextView tv_back;
    private TextView tv_select;


    public static Fragment0 newInstance() {
        // TODO Auto-generated method stub
        Fragment0 fragment = new Fragment0();
        return fragment;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        beans = getDate();
        gridView.setAdapter(new ImageGridViewAdapter(getActivity(), beans));

        String xzCityName = AppApplication.preferenceProvider.getXzCityName();
        tv_select.setText(xzCityName+" ▼");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView ");
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_0, container, false);
            imageIndicatorView = (ImageIndicatorView) mView
                    .findViewById(R.id.indicate_view);
            tv_back = (TextView) mView.findViewById(R.id.tv_exit);
            iv_search = (LinearLayout) mView.findViewById(R.id.imageView1);
            iv_search.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(getActivity(),
                            SelectCityActivity.class);
                    startActivity(intent);
                }
            });
            tv_back.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    getActivity().finish();
                }
            });
            ImageView iv_right_search = (ImageView)mView.findViewById(R.id.iv_right_search);
            iv_right_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),Activity_searchxzfw.class);
                    startActivity(intent);
                }
            });
            // getIp();

            tv_select = (TextView)mView.findViewById(R.id.tv_select);

            String cityCode = AppApplication.preferenceProvider.getCityCode();
            if(TextUtils.isEmpty(cityCode)){

                AppApplication.preferenceProvider.setCityCode("401");
                AppApplication.preferenceProvider.setXzCityName("平顶山市");
                String xzCityName = AppApplication.preferenceProvider.getXzCityName();
                tv_select.setText(xzCityName+" ▼");


            }



            gridView = (MyGridView) mView.findViewById(R.id.gridView1);
            final Integer[] resArray = new Integer[] {
                    R.drawable.icon_hotline_top, R.drawable.icon_hotline_top,
                    R.drawable.icon_hotline_top };
            imageIndicatorView.setupLayoutByDrawable(resArray);
            imageIndicatorView.show();
            AutoPlayManager autoPlayManager = new AutoPlayManager(
                    imageIndicatorView);
            autoPlayManager.setBroadcastEnable(true);
            // autoPlayManager.setBroadCastTimes(5);// 循环播放5次
            autoPlayManager.setBroadcastTimeIntevel(3 * 1000, 3 * 1000);// 播放启动时间及间隔
            autoPlayManager.loop();
            imageIndicatorView
                    .setOnItemClickListener(new ImageIndicatorView.OnItemClickListener() {

                        @Override
                        public void OnItemClick(View view, int position) {
                            // TODO Auto-generated method stub
                            dialog_version = new DialogErWeiMa(getActivity());
                            dialog_version.show();
                        }
                    });

            gridView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        final int arg2, long arg3) {
                    Intent intent = null;
                    if (beans.get(arg2).getName().equals("服务面对面")) {
                        intent = new Intent(getActivity(),
                                Activity_Face2Face.class);
                        intent.putExtra("title", "服务面对面");
                        startActivity(intent);
                    }else if (beans.get(arg2).getName().equals("党员e家")) {
                        intent = new Intent(getActivity(),
                                Activity_Face2Face.class);
                        intent.putExtra("title", "党员e家");
                        startActivity(intent);
                    } else if (beans.get(arg2).getName().equals("民情流水线")) {
//                        intent = new Intent(getActivity(),
//                                Activity_Evaluate.class);
//                        intent.putExtra("item", beans.get(arg2));
                        // startActivity(intent);
                    } else if (beans.get(arg2).getName().equals("电视直播")) {
//                        intent = new Intent(getActivity(), Activity_tv.class);
//                        intent.putExtra("item", beans.get(arg2));
//                        startActivity(intent);
                    } else if (beans.get(arg2).getName().equals("添加")) {
                        intent = new Intent(getActivity(), SelectXzfwActivity.class);
                        intent.putExtra("item", beans.get(arg2));
                        startActivity(intent);
                    } else if (beans.get(arg2).getName().equals("亲情视频")) {

                    } else {
//                        intent = new Intent(getActivity(), Activity_list.class);
                        ItemBean itemBean = beans.get(arg2);
                        String name = itemBean.getName();
                        Log.e("qqq","模块名称："+name);

                        if (TextUtils.equals(name, "行政服务")) {



                            String cityCode = AppApplication.preferenceProvider.getCityCode();
                            if(TextUtils.isEmpty(cityCode)){

                                AppApplication.preferenceProvider.setCityCode("401");
                                AppApplication.preferenceProvider.setXzCityName("平顶山市");



                            }
                            if(TextUtils.equals(cityCode,"401")){
                                intent = new Intent(getActivity(), XingZhengFuWuRootActivity.class);
                                intent.putExtra("code", "0");
                                intent.putExtra("isaddcity", "1");
                                intent.putExtra("dw", "平顶山市");
                            }else {
                                intent = new Intent(getActivity(), ShiZhiOrXianJiActivity.class);
                                Log.e("aaaa","城市代号："+cityCode);
                                intent.putExtra("code", cityCode);
                                intent.putExtra("isaddcity", "1");
                                intent.putExtra("dw", AppApplication.preferenceProvider.getXzCityName());
                            }

                            startActivity(intent);


                        }else if(TextUtils.equals(name, "党务服务")){
                            intent = new Intent(getActivity(), DangWuFuWuActivity.class);
                            startActivity(intent);
                        }else if(TextUtils.equals(name, "农技咨询") || TextUtils.equals(name, "医疗咨询") || TextUtils.equals(name, "法律咨询") || TextUtils.equals(name, "服务热线") || TextUtils.equals(name, "教育咨询")  ){
                            intent = new Intent(getActivity(), ItemXingZhengFuWuActivity.class);
                            itemBean.setIsAdd(false);
                            intent.putExtra("item",itemBean);
                            startActivity(intent);
                        }else{//Activity_listxzfw
                            //MdmItemActivity
                            Log.e("qqq","添加到首页：");
                            intent = new Intent(getActivity(), AddItemXingZhengFuWuActivity.class);
                            itemBean.setIsAdd(false);
                            intent.putExtra("item",itemBean);
                            startActivity(intent);
                        }


                    }

                }
            });

            gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               final int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    if (!beans.get(arg2).getIsLongBoolean()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                getActivity(), R.style.Translucent_NoTitle);
                        builder.setMessage("要从首页移除该功能吗？");
                        builder.setPositiveButton("移除",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Type dataType = new TypeToken<List<ItemBean>>() {
                                        }.getType();
                                        ArrayList<ItemBean> tempdata = GsonUtil
                                                .getGson()
                                                .fromJson(
                                                        AppApplication.preferenceProvider
                                                                .getjson(),
                                                        dataType);
                                        for (ItemBean itemBean : tempdata) {
                                            if (itemBean.getName().equals(
                                                    beans.get(arg2).getName())) {
                                                itemBean.setIsSelect(false);
                                                tempdata.remove(itemBean);
                                                break;
                                            }
                                        }
                                        AppApplication.preferenceProvider
                                                .setjson(GsonUtil
                                                        .getJson(tempdata));
                                        beans = getDate();
                                        gridView.setAdapter(new ImageGridViewAdapter(
                                                getActivity(), beans));
                                    }
                                });
                        builder.setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                        dialog.dismiss();
                                    }
                                });
                        builder.create().show();
                    }
                    return true;
                }
            });
        }

        // 缓存的rootView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;
    }




    /**
     *
     * @author CBB 描述：初始化天气
     */
    public String getWeatherBean(String province, String city, String county) {
        String str = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            InputStream in = getResources().getAssets().open("city.xml");
            parser.setInput(in, "UTF-8");
            // 产生第一个事件
            String countyNotFoundCode = "";
            boolean isPro = false;
            boolean isCity = false;
            int eventType = parser.getEventType();
            String ci = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    // 判断当前事件是否为文档开始事件
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    // 判断当前事件是否为标签元素开始事件
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("province")) { // 判断开始标签元素是否是province
                            String pro = parser.getAttributeValue(0);// 省
                            if (pro.contains(province)) {
                                isPro = true;
                            }
                        }
                        if (isPro) {
                            if (parser.getName().equals("city")) {
                                ci = parser.getAttributeValue(0);// 市
                                if (ci.contains(city)) {
                                    isCity = true;
                                }
                            }
                        }
                        if (isCity) {
                            if (parser.getName().equals("county")) {
                                String cou = parser.getAttributeValue(0);// 区
                                String code = parser.getAttributeValue(1);
                                if (cou.contains(city)) {// 记下首个天气代码，即城市代码
                                    str = code;
                                }
                                if (cou.contains(county)) {
                                    str = code;
                                }
                            }
                        }
                        break;
                    // 判断当前事件是否为标签元素结束事件
                    case XmlPullParser.END_TAG:
                        if (str != null) {
                            return str;
                        }
                        break;
                }
                // 进入下一个元素并触发相应事件
                eventType = parser.next();
            }
            // Logger.d(TAG, bean.getProvince() + ", " + bean.getCity() + ", "
            // + bean.getCounty() + ", " + bean.getWeatherCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }




    private ArrayList<ItemBean> getDate() {
        // TODO Auto-generated method stub
        Type dataType = new TypeToken<List<ItemBean>>() {
        }.getType();
        ArrayList<ItemBean> tempdata = GsonUtil.getGson().fromJson(
                AppApplication.preferenceProvider.getjson(), dataType);
        ArrayList<ItemBean> beans = new ArrayList<ItemBean>();
        for (ItemBean itemBean : tempdata) { // 倒序
            if (!itemBean.getIsMain() && itemBean.getIsSelect()) {
                beans.add(itemBean);
            }

            if (itemBean.getIsMain() && itemBean.getIsSelect()) {
                beans.add(itemBean);
            }
        }
        ItemBean bean = new ItemBean();
        bean.setName("添加");
        bean.setIcon(R.drawable.mdm_add);
        bean.setIsSelect(true);
        bean.setIsMain(true);
        bean.setIsLongBoolean(true);
        beans.add(bean);

        return beans;
    }

    private View mView;



}
