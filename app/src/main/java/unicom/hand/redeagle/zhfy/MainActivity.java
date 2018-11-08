package unicom.hand.redeagle.zhfy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.bean.ItemBean;
import unicom.hand.redeagle.zhfy.bean.MyCityBean2;
import unicom.hand.redeagle.zhfy.bean.MyNodeBean;
import unicom.hand.redeagle.zhfy.bean.ResultBaseBean;
import unicom.hand.redeagle.zhfy.bean.SerializableMap;
import unicom.hand.redeagle.zhfy.fragment.Fragment0;
import unicom.hand.redeagle.zhfy.fragment.Fragment2;
import unicom.hand.redeagle.zhfy.fragment.Fragment3;
import unicom.hand.redeagle.zhfy.fragment.Fragment_spzb;
import unicom.hand.redeagle.zhfy.fragment.IndexFragment;
import unicom.hand.redeagle.zhfy.fragment.LoginSphyFragment;
import unicom.hand.redeagle.zhfy.fragment.ShykFragment;
import unicom.hand.redeagle.zhfy.ui.LoginActivity;
import unicom.hand.redeagle.zhfy.ui.LoginSphyActivity;
import unicom.hand.redeagle.zhfy.ui.MeetingRecoderActivity;
import unicom.hand.redeagle.zhfy.ui.SelectMemberJoinActivity;
import unicom.hand.redeagle.zhfy.ui.SphyMainActivity;
import unicom.hand.redeagle.zhfy.ui.UpdateManager;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

import com.yealink.base.model.ConferenceInfo;
import com.yealink.callscreen.ExternalInterface;
import com.yealink.callscreen.function.OnInviteListener;
import com.yealink.common.data.CloudProfile;
import com.yealink.common.data.Contact;
import com.yealink.common.data.SipProfile;
import com.yealink.sdk.YealinkApi;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static unicom.hand.redeagle.R.id.tv_status;

public  class MainActivity extends AppCompatActivity implements LoginSphyFragment.onTestListener {
    private String mTextviewArray[] = {"服务面对面","三会一课","首页", "视频会议",  "我的"};
    private int mImageViewArray[] = {R.drawable.rb_selector_fwmdm, R.drawable.rb_selector_shyk,R.drawable.rb_selector_index, R.drawable.rb_selector_sphy,
            R.drawable.rb_selector_me};
    private Class fragmentArray[] = {Fragment0.class, ShykFragment.class, IndexFragment.class, Fragment2.class, Fragment3.class};
    // 定义FragmentTabHost对象
//    private FragmentTabHost mTabHost;
    private final static String TAG = "MainActivity";
    // 定义一个布局
    private LayoutInflater layoutInflater;
    private FragmentManager fm;
    private RadioGroup radioGroup;
    private RadioButton rb_index;
    private RadioButton rb_shyk;
    private RadioButton rb_mdm;
    private RadioButton rb_sphy;
    private RadioButton rb_me;
    private IndexFragment indexFragment;
    private ShykFragment shykFragment;
    private Fragment0 mdmFragment;
    private Fragment2 sphyFragment;
    private Fragment3 meFragment;
    private static ProgressBar pb;
    //    private LoginSphyFragment loginSphyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        UpdateManager manager = new UpdateManager(MainActivity.this, "zshy_handredeagle");
        manager.checkUpdateInfo();
        context = MainActivity.this;
        setDialog();

//        List<Contact> contacts = YealinkApi.instance().searchCloudContact("5010", true);
//        String json = GsonUtil.getJson(contacts);
//        Log.e("aaa","联系人："+json);
        initView();
        getCode();
//        getData();
        setDate();

        try {
            YealinkApi.instance().setExtInterface(new ExternalInterface() {
                @Override
                public void inviteMember(FragmentManager manager, OnInviteListener listener, String[] members) {
    //                MeetInviteFragment fragment = new MeetInviteFragment();
    //                fragment.show(manager);

                    Intent intent = new Intent(MainActivity.this, SelectMemberJoinActivity.class);
                    startActivityForResult(intent,100);
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int  i =0;
        if (resultCode==3){
            SerializableMap serializabmap = (SerializableMap)data.getSerializableExtra("item");
            Map<String, MyNodeBean> map = serializabmap.getMap();
            String [] strs = new String[map.size()];
            Iterator<Map.Entry<String, MyNodeBean>> iterator = map.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, MyNodeBean> next = iterator.next();
                MyNodeBean value = next.getValue();
                String name = value.getName();
                strs[i] = name;
                i++;
            }
            try {
                YealinkApi.instance().meetInvite(strs);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            ArrayList<Contact> meetnowcontact = data.getParcelableArrayListExtra("list");
//            if (meetnowcontact!=null&&meetnowcontact.size()>0){
//                Log.e("aaaa",meetnowcontact.size()+"!!!!!");
//                String[] strings = new String[meetnowcontact.size()];
//                for (int i = 0;i<meetnowcontact.size();i++){
//                    strings[i] = meetnowcontact.get(i).getSerialNumber();
//                }
//                YealinkApi.instance().meetInvite(strings);
//            }
        }
    }





    public  void getCode() {
        FinalHttp fh = new FinalHttp();
        fh.configTimeout(40000);//超时时间
        fh.post(baseUrl+"/pds/sys/phone/getVersion", new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                super.onSuccess(t);
                ad.dismiss();
                Log.d(TAG, "listArea=" + t.toString()+"--"+AppApplication.preferenceProvider.getUid() + "");
                ResultBaseBean result = (ResultBaseBean) GsonUtil
                        .getObject(t.toString(), ResultBaseBean.class);
                if (result.getMsg()!=null&&result!=null&&result.getCode()==0){
                    try {
                        if (Integer.valueOf(result.getMsg())>Integer.valueOf(AppApplication.preferenceProvider.getUid())){
                            code = result.getMsg();
//                        if (AppApplication.preferenceProvider.getUid().equals("0")){
//                            AppApplication.preferenceProvider.setUid(code);
//                        }
                            getData();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onStart() {
                super.onStart();
                ad.show();
                statusText.setText("检查数据版本中，请稍后！");
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.d(TAG, "strMsg=" + strMsg+ "");
                ad.dismiss();
            }
        });
    }

    static String code = "0";
    //平顶山
    static String  baseUrl = "http://42.236.68.190:8010";
    //广州
//    String baseUrl = "http://122.13.4.168:8090";
    public   void getData() {
        FinalHttp fh = new FinalHttp();
        fh.configTimeout(40000);//超时时间

        String url =baseUrl+"/pds/sys/phone/listArea";
        if (!AppApplication.preferenceProvider.getUid().equals("0")){
            url = baseUrl+"/pds/sys/phone/listArea?strVersion="+AppApplication.preferenceProvider.getUid();
        }
        fh.post(url, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                super.onSuccess(t);
                Log.d(TAG, "listArea=" + t.toString() + "");
                ResultBaseBean result = (ResultBaseBean) GsonUtil
                        .getObject(t.toString(), ResultBaseBean.class);
                statusText.setText("数据获取成功！");
                statusText.setText("正在解析数据！");


                Type dataType = new TypeToken<List<MyCityBean2>>() {
                }.getType();
                List<MyCityBean2> list = new ArrayList<MyCityBean2>();
                List<MyCityBean2> lista_add = null;
                List<MyCityBean2> lista_upData = null;
                List<MyCityBean2> lista_delete = null;
                DbUtils db = DbUtils.create(context, Common.DB_NAME);
//                dbUtils.delete(MyCityBean2.class, WhereBuilder.b("id", "=", 1));
                try {
                    List<MyCityBean2> lastList =db.findAll(MyCityBean2.class);
                    if (lastList!=null)
                        Log.d(TAG, "firstList=" + lastList.size() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (result.getData() != null) {
                    lista_add= GsonUtil
                            .getGson().fromJson(
                                    GsonUtil.getJson(result
                                            .getData()),
                                    dataType);
//                    Log.d(TAG, "lista_add=" + lista_add.size() + "");

                    if (lista_add!=null){
                        list.addAll(lista_add);
//                        Log.d(TAG, "list=" + list.size() + "");
                    }
                }
                if (result.getUpdate() != null) {
                    lista_upData= GsonUtil
                            .getGson().fromJson(
                                    GsonUtil.getJson(result
                                            .getUpdate()),
                                    dataType);
//                    Log.d(TAG, "lista_upData=" + lista_upData.size() + "");
                    if (lista_upData!=null){
                        list.addAll(lista_upData);
//                        Log.d(TAG, "list=" + list.size() + "");
                    }
                    for (MyCityBean2 myCityBean2 :lista_upData){
                        try {
                            db.delete(MyCityBean2.class, WhereBuilder.b("id", "=", myCityBean2.getAreaId()));
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        List<MyCityBean2> lastList =db.findAll(MyCityBean2.class);
//                        if (lastList!=null)
//                            Log.d(TAG, "delete=" + lastList.size() + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (result.getDelete() != null) {
                    lista_delete= GsonUtil
                            .getGson().fromJson(
                                    GsonUtil.getJson(result
                                            .getDelete()),
                                    dataType);
//                    Log.d(TAG, "lista_delete=" + lista_delete.size() + "");
                }
                pb.setProgress(65);
                if (list.size()>0) {

                    for (MyCityBean2 myCityBean2 : list) {
                        myCityBean2.setId(myCityBean2.getAreaId()+"");
                        myCityBean2.setExpanding(1);

                    }
                    pb.setProgress(75);
                    try {
                        statusText.setText("更新数据中！");
                        db.saveOrUpdateAll(list);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    pb.setProgress(95);
                    ad.dismiss();
                }else {
                    ad.dismiss();
                }
                pb.setProgress(100);
                if (lista_delete!=null) {
                    for (MyCityBean2 myCityBean2 : lista_delete) {
                        try {
                            db.delete(MyCityBean2.class, WhereBuilder.b("id", "=", myCityBean2.getAreaId()));
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                }

                try {
                    List<MyCityBean2> lastList =db.findAll(MyCityBean2.class);
                    Log.d(TAG, "lastList=" + lastList.size() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AppApplication.preferenceProvider.setUid(code);
            }

            @Override
            public void onStart() {
                super.onStart();
                ad.show();
                pb.setProgress(10);
                pb.setMax(100);
                statusText.setText("数据更新中，请稍后！");
            }

            @Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
                int pos = (int)(40*(current/count));
                Log.e("aaa","当前进度位置："+pos);
                pb.setProgress(pos);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.d(TAG, "strMsg=" + strMsg+ "");
                ad.dismiss();
            }
        });
    }
    private void setDate() {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        List<ItemBean> beans = new ArrayList<ItemBean>();

        ItemBean bean0 = new ItemBean();
        bean0.setName("行政服务");
        bean0.setIcon(R.drawable.mdm_xzfw);
        bean0.setIsSelect(true);
        bean0.setIsMain(true);
        bean0.setIsLongBoolean(true);
        bean0.setSqlString("face2face");
        bean0.setOrder("服务面对面");
        bean0.setCode("");
        bean0.setIsAdd(true);
        beans.add(bean0);

        ItemBean bean1 = new ItemBean();
        bean1.setName("党务服务");
        bean1.setIcon(R.drawable.mdm_dwfw);
        bean1.setSqlString("face2face");
        bean1.setIsSelect(true);
        bean1.setIsMain(true);
        bean1.setIsLongBoolean(true);
        beans.add(bean1);
//        ItemBean bean2 = new ItemBean();
//        bean2.setName("电视直播");
//        bean2.setIcon(R.drawable.item_dszb);
//        bean2.setIsSelect(true);
//        bean2.setIsMain(true);
//        bean2.setIsLongBoolean(true);
//        beans.add(bean2);

        ItemBean bean = new ItemBean();
        bean.setName("农技咨询");
        bean.setIcon(R.drawable.mdm_njzx);
        bean.setIsSelect(true);
        bean.setIsMain(true);
        bean.setIsLongBoolean(true);
        bean.setSqlString("agriculture");
        bean.setOrder("服务面对面");
        bean.setCode("");
        bean.setIsAdd(true);
        beans.add(bean);


        ItemBean bean3 = new ItemBean();
        bean3.setName("医疗咨询");
        bean3.setIcon(R.drawable.mdm_ylzx);
        bean3.setIsSelect(true);
        bean3.setIsMain(true);
        bean3.setIsLongBoolean(true);
        bean3.setSqlString("medical");
        bean3.setOrder("服务面对面");
        bean3.setCode("");
        bean3.setIsAdd(true);
        beans.add(bean3);




        ItemBean bean4 = new ItemBean();
        bean4.setName("法律咨询");
        bean4.setIcon(R.drawable.mdm_flzx);
        bean4.setIsSelect(true);
        bean4.setIsMain(true);
        bean4.setIsLongBoolean(true);
        bean4.setSqlString("law");
        bean4.setOrder("服务面对面");
        bean4.setCode("");
        bean4.setIsAdd(true);
        beans.add(bean4);

        ItemBean bean5 = new ItemBean();
        bean5.setName("服务热线");
        bean5.setIcon(R.drawable.mdm_fwrx);
        bean5.setIsSelect(true);
        bean5.setIsMain(true);
        bean5.setIsLongBoolean(true);
        bean5.setSqlString("hotline");
        bean5.setOrder("服务面对面");
        bean5.setCode("");
        bean5.setIsAdd(true);
        beans.add(bean5);

        ItemBean bean6 = new ItemBean();
        bean6.setName("教育咨询");
        bean6.setIcon(R.drawable.education);
        bean6.setIsSelect(true);
        bean6.setIsMain(true);
        bean6.setIsLongBoolean(true);
        bean6.setSqlString("education");
        bean6.setOrder("服务面对面");
        bean6.setCode("");
        bean6.setIsAdd(true);
        beans.add(bean6);


//        ItemBean bean3 = new ItemBean();
//        bean3.setName("亲情视频");
//        bean3.setIcon(R.drawable.item_qqdh);
//        bean3.setIsSelect(true);
//        bean3.setIsMain(true);
//        beans.add(bean3);
//
//        ItemBean bean5 = new ItemBean();
//        bean5.setName("党群综合服务");
//        bean5.setIcon(R.drawable.item_dqzhfw);
//        bean5.setSqlString("face2face");
//        bean5.setIsSelect(false);
//        bean5.setIsMain(false);
//        bean5.setOrder("服务面对面");
//        beans.add(bean5);
//
//        ItemBean bean6 = new ItemBean();
//        bean6.setName("医疗咨询");
//        bean6.setIcon(R.drawable.item_yl);
//        bean6.setSqlString("medical");
//        bean6.setIsSelect(false);
//        bean6.setIsMain(false);
//        bean6.setOrder("服务面对面");
//        beans.add(bean6);
//
//        ItemBean bean7 = new ItemBean();
//        bean7.setName("法律咨询");
//        bean7.setIcon(R.drawable.item_fl);
//        bean7.setSqlString("law");
//        bean7.setIsSelect(false);
//        bean7.setIsMain(false);
//        bean7.setOrder("服务面对面");
//        beans.add(bean7);
//
//        ItemBean bean8 = new ItemBean();
//        bean8.setName("农技咨询");
//        bean8.setIcon(R.drawable.item_njfw);
//        bean8.setSqlString("agriculture");
//        bean8.setIsSelect(false);
//        bean8.setIsMain(false);
//        bean8.setOrder("服务面对面");
//        beans.add(bean8);
//
//        ItemBean bean9 = new ItemBean();
//        bean9.setName("服务热线");
//        bean9.setIcon(R.drawable.item_fwrx);
//        bean9.setSqlString("hotline");
//        bean9.setIsSelect(false);
//        bean9.setIsMain(false);
//        bean9.setOrder("服务面对面");
//        beans.add(bean9);
//
//        ItemBean bean10 = new ItemBean();
//        bean10.setName("党的知识");
//        bean10.setIcon(R.drawable.item_ddzs);
//        bean10.setSqlString("");
//        bean10.setIsSelect(false);
//        bean10.setIsMain(false);
//        bean10.setOrder("党员e家");
//        beans.add(bean10);
//
//        ItemBean bean11 = new ItemBean();
//        bean11.setName("党费缴纳");
//        bean11.setIcon(R.drawable.item_dfjn);
//        bean11.setSqlString("");
//        bean11.setIsSelect(false);
//        bean11.setIsMain(false);
//        bean11.setOrder("党员e家");
//        beans.add(bean11);
//
//        ItemBean bean12 = new ItemBean();
//        bean12.setName("组织关系转接");
//        bean12.setIcon(R.drawable.item_zzgxzj);
//        bean12.setSqlString("");
//        bean12.setIsSelect(false);
//        bean12.setIsMain(false);
//        bean12.setOrder("党员e家");
//        beans.add(bean12);
//
//        ItemBean bean13 = new ItemBean();
//        bean13.setName("组织关系转入");
//        bean13.setIcon(R.drawable.item_zzgxzr);
//        bean13.setSqlString("");
//        bean13.setIsSelect(false);
//        bean13.setIsMain(false);
//        bean13.setOrder("党员e家");
//        beans.add(bean13);
//        String getjson = AppApplication.preferenceProvider.getjson();
//        if(TextUtils.isEmpty(getjson)){
//            AppApplication.preferenceProvider.setjson(GsonUtil.getJson(beans));
//            AppApplication.preferenceProvider.setStatus(true);
//        }
        String first = AppApplication.preferenceProvider.getFirst();
        if(TextUtils.isEmpty(first)){
            AppApplication.preferenceProvider.setjson(GsonUtil.getJson(beans));
            AppApplication.preferenceProvider.setStatus(true);
            AppApplication.preferenceProvider.setFirst("1");
        }else{

        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    private void initView() {
        // TODO Auto-generated method stub
        layoutInflater = LayoutInflater.from(this);
//        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
//        mTabHost.setup(this, getSupportFragmentManager(), R.id.framecontent);
//        mTabHost.getTabWidget().setDividerDrawable(R.color.transparent);
//        int count = fragmentArray.length;
//        for (int i = 0; i < count; i++) {
//            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
//            mTabHost.addTab(tabSpec, fragmentArray[i], null);
//            mTabHost.setTag(mTextviewArray[i]);
//        }
//
//        mTabHost.setOnTabChangedListener(new TabHostListener());

        fm = getSupportFragmentManager();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        indexFragment = new IndexFragment();
        shykFragment = new ShykFragment();
        mdmFragment = new Fragment0();
        sphyFragment = new Fragment2();
        meFragment = new Fragment3();
        String username = AppApplication.preferenceProvider.getUsername();
        String passWord = AppApplication.preferenceProvider.getPassWord();
        try {
            if(!TextUtils.isEmpty(username)){
                YealinkApi.instance().registerYms(username,passWord,AppApplication.preferenceProvider.getIp(), AppApplication.preferenceProvider.getIp2());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        loginSphyFragment = new LoginSphyFragment();
        ft.add(R.id.framecontent, indexFragment)
                .add(R.id.framecontent, shykFragment)
                .add(R.id.framecontent, mdmFragment)
                .add(R.id.framecontent, sphyFragment)
                .add(R.id.framecontent, meFragment)
//                .add(R.id.framecontent, loginSphyFragment)
                .commit();
        ft.show(indexFragment).hide(shykFragment).hide(mdmFragment).
                hide(sphyFragment).hide(meFragment)
                ;

        rb_index = (RadioButton)findViewById(R.id.rb_index);
        rb_mdm = (RadioButton)findViewById(R.id.rb_mdm);
        rb_shyk = (RadioButton)findViewById(R.id.rb_shyk);
        rb_sphy = (RadioButton)findViewById(R.id.rb_sphy);
        rb_me = (RadioButton)findViewById(R.id.rb_me);
        radioGroup = (RadioGroup) findViewById(R.id.rg_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                changeButton(i);
            }
        });







    }


    public void setShowSphy(){
        radioGroup.check(R.id.rb_index);
//        changeButton(R.id.rb_index);
    }
    public  int lastId =0;
    public void changeButton(int position) {
        FragmentTransaction ft = fm.beginTransaction();
        switch (position){
            case R.id.rb_index:
               lastId = 0;
                ft.show(indexFragment).hide(shykFragment).hide(mdmFragment).
                        hide(sphyFragment).hide(meFragment).commit();
                break;
            case R.id.rb_shyk:
                lastId = 1;
                ft.show(shykFragment).hide(indexFragment).hide(mdmFragment).
                        hide(sphyFragment).hide(meFragment).commit();
//                SipProfile sipProfile = YealinkApi.instance().getSipProfile();
                try {
                    CloudProfile cp1 = YealinkApi.instance().getCloudProfile();
                    if (cp1 != null && cp1.state != 2) {

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    //                    intent.putExtra("type","shyk");
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rb_mdm:
                lastId = 2;
                ft.show(mdmFragment).hide(indexFragment).hide(shykFragment).
                        hide(sphyFragment).hide(meFragment).commit();
                break;
            case R.id.rb_sphy:
                lastId = 3;
                try {
                    CloudProfile cp = YealinkApi.instance().getCloudProfile();
                    //没登录
                    if (cp != null && cp.state != 2) {
                        Intent intent = new Intent(MainActivity.this, LoginSphyActivity.class);
    //                    intent.putExtra("type","sphy");
                        startActivity(intent);
                        return;
                    }else {
                        Intent intent = new Intent(MainActivity.this, SphyMainActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.rb_me:
                lastId = 4;
                ft.show(meFragment).hide(indexFragment).hide(shykFragment).
                        hide(sphyFragment).hide(mdmFragment).commit();



                break;
            default:
                break;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lastId == 3){
            rb_index.setChecked(true);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.show(indexFragment).hide(shykFragment).hide(mdmFragment).
                    hide(sphyFragment).hide(meFragment).commit();
        }else if (lastId == 1){
            try {
                CloudProfile cp = YealinkApi.instance().getCloudProfile();
                //没登录
                if (cp != null && cp.state != 2) {
                    rb_index.setChecked(true);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.show(indexFragment).hide(shykFragment).hide(mdmFragment).
                            hide(sphyFragment).hide(meFragment).commit();
                }else{

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onShowIndex() {
        setShowSphy();
    }


    private class TabHostListener implements TabHost.OnTabChangeListener {
        @Override
        public void onTabChanged(String tabId) {
            Log.v(TAG, "onTabChanged-" + tabId);

            if (tabId.equals("视频会议")) {
                try {
                    CloudProfile cp = YealinkApi.instance().getCloudProfile();
                    //没登录
                    if (cp != null && cp.state != 2) {
                        Log.v(TAG, "cp.state ==" + cp.state);
    //                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    //                    startActivity(intent);
    //                    finish();


    //                    TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[3]).setIndicator(getTabItemView(3));
    //                    mTabHost.addTab(tabSpec, LoginSphyFragment.class, null);
    //                    mTabHost.setTag("login");
    //
    //                    mTabHost.setCurrentTab(3);






                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 获取tab按钮
     *
     * @return
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_ic);
        imageView.setImageResource(mImageViewArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.tab_tv);
        textView.setText(mTextviewArray[index]);
        return view;
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(MainActivity.this,
                        getResources().getString(R.string.info_exit),
                        Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                logout();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void logout() {
        try {
            YealinkApi.instance().unregistCloud();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String callId = "";// 被叫电话
    private static  AlertDialog ad;
    private AlertDialog.Builder builder;
    private LinearLayout ll_status;
    private static TextView statusText;
    static  Context context;

    private void setDialog() {
        // TODO Auto-generated method stub
        ll_status = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.ad_threetree_status, null);
        statusText = (TextView) ll_status.findViewById(tv_status);
        pb = (ProgressBar)ll_status.findViewById(R.id.pb);
        builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
        builder.setView(ll_status);
        builder.setCancelable(false);
//        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                dialog.dismiss();
//                logout();
//            }
//        });
        ad = builder.create();
    }

}
