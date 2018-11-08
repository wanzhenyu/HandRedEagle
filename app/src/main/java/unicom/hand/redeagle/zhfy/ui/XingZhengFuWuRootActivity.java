package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.yealink.common.data.AccountConstant;
import com.yealink.common.data.SipProfile;
import com.yealink.sdk.RegistListener;
import com.yealink.sdk.YealinkApi;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.BaseActivity;
import unicom.hand.redeagle.zhfy.Common;
import unicom.hand.redeagle.zhfy.PublishMeetingActivity1;
import unicom.hand.redeagle.zhfy.bean.MyCityBean2;
import unicom.hand.redeagle.zhfy.ui.old.Activity_list;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.GsonUtils;
import unicom.hand.redeagle.zhfy.view.MyListView;

import static unicom.hand.redeagle.R.id.tv_status;

public class XingZhengFuWuRootActivity extends BaseActivity {
//    private MyListView mDataLV;
    private MyListView mDatamember;
//    private List<MyCityBean2> toporgChildNode;
    private List<MyCityBean2> bottomorgChildNode;
//    private MyLvAdapter myLvAdapter;
    private MyLvAdapter1 myLvAdapter1;
    DbUtils db;
    List<MyCityBean2> list;
    private int imgflags[] = {R.drawable.list_flag,R.drawable.list_flag1,R.drawable.list_flag2,R.drawable.list_flag3};
    private String callId = "";// 被叫电话
    private AlertDialog ad;
    private AlertDialog.Builder builder;
    private LinearLayout ll_status;
    private TextView statusText;
    private TextView tv_zj;

    @Override
    protected void handleMsg(Message msg) {
        switch(msg.what){
            case MSG_ACCOUNT_CHANGE:
                setStatus(YealinkApi.instance().getSipProfile());
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xing_zheng_fu_wu_root);
        initRightTitle("行政服务");

//        mDataLV = (MyListView) findViewById(R.id.department_datalist);
        mDatamember = (MyListView) findViewById(R.id.department_member);

//        toporgChildNode = new ArrayList<>();
        bottomorgChildNode = new ArrayList<>();
        db = DbUtils.create(XingZhengFuWuRootActivity.this, Common.DB_NAME);
        getData("0");
//        if(myLvAdapter == null){
//            myLvAdapter = new MyLvAdapter();
//            mDataLV.setAdapter(myLvAdapter);
//        }else{
//            myLvAdapter.notifyDataSetChanged();
//        }




        if(myLvAdapter1 == null){
            myLvAdapter1 = new MyLvAdapter1();
            mDatamember.setAdapter(myLvAdapter1);
        }else{
            myLvAdapter1.notifyDataSetChanged();
        }

        LinearLayout ll_xj = (LinearLayout)findViewById(R.id.ll_xj);
        ll_xj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list !=null && list.size()>0){
                    MyCityBean2 myCityBean2 = list.get(0);
                    Intent intent = new Intent(XingZhengFuWuRootActivity.this, XingZhengFuWuActivity.class);
                    intent.putExtra("code","0");
                    intent.putExtra("isaddcity","1");
                    intent.putExtra("issz","0");
                    intent.putExtra("dw","平顶山市");
                    intent.putExtra("item",myCityBean2);
                    startActivity(intent);
                }else{
                    Toast.makeText(XingZhengFuWuRootActivity.this, "暂时无数据", Toast.LENGTH_SHORT).show();
                }



            }
        });

        LinearLayout ll_sj = (LinearLayout)findViewById(R.id.ll_sj);
        ll_sj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list !=null && list.size()>0){
                    MyCityBean2 myCityBean2 = list.get(0);
                    Intent intent = new Intent(XingZhengFuWuRootActivity.this, XingZhengFuWuActivity.class);
                    intent.putExtra("code","402");
                    intent.putExtra("isaddcity","0");
                    intent.putExtra("issz","1");
                    intent.putExtra("dw","市直单位");
                    intent.putExtra("item",myCityBean2);
                    startActivity(intent);
                }else{
                    Toast.makeText(XingZhengFuWuRootActivity.this, "暂时无数据", Toast.LENGTH_SHORT).show();
                }

            }
        });
        setDialog();
//        mDataLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                MyCityBean2 myCityBean2 = toporgChildNode.get(i);
//                Intent intent = new Intent(XingZhengFuWuRootActivity.this, XingZhengFuWuActivity.class);
//                intent.putExtra("item",myCityBean2);
//                startActivity(intent);
//
//
//
//
//
//
//            }
//        });


        mDatamember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MyCityBean2 orgNode =  (MyCityBean2)adapterView.getAdapter().getItem(i);
                String calledNum = orgNode.getCalledNum();
                if(!TextUtils.isEmpty(calledNum)) {
                    Intent intent = new Intent(XingZhengFuWuRootActivity.this, MdmContactDetailActivity.class);
                    intent.putExtra("orgNode", orgNode);
                    startActivity(intent);
                }else{
                    Toast.makeText(XingZhengFuWuRootActivity.this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                }


            }
        });
        mDatamember.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                showMyDialog(i);

                return true;
            }
        });

        tv_zj = (TextView)findViewById(R.id.tv_zj);






    }
    public void showMyDialog(final int pos) {


        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("提示");
        builder.setMessage("确定要删除？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int poss) {
                try {
                    MyCityBean2 myCityBean21 = bottomorgChildNode.get(pos);


                    String collectjson = AppApplication.preferenceProvider.getCollectjson();
                    if(!TextUtils.isEmpty(collectjson) && collectjson != null){
                        Type dataType = new TypeToken<List<MyCityBean2>>() {
                        }.getType();
                        ArrayList<MyCityBean2> tempdata = GsonUtil.getGson().fromJson(collectjson
                                , dataType);

                            if(tempdata != null && tempdata.size()>0){
                                for (int i=tempdata.size()-1;i<tempdata.size();i--){

                                    Log.e("aaa","索引："+i);
                                    MyCityBean2 myCityBean2 = tempdata.get(i);
                                    String calledName = myCityBean21.getCalledNum();
                                    String calledNum = myCityBean2.getCalledNum();
                                    if(TextUtils.equals(calledName,calledNum)){
                                        tempdata.remove(myCityBean2);
                                        String json = GsonUtils.getJson(tempdata);
                                        Log.e("aaa","索引："+json);
                                        AppApplication.preferenceProvider.setCollectjson(json);
                                        break;
                                    }
                                }
                            }


                    }

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

                bottomorgChildNode.remove(pos);
                if(myLvAdapter1 != null){
                    myLvAdapter1.notifyDataSetChanged();
                }
                if(bottomorgChildNode.size()>0){
                    tv_zj.setVisibility(View.VISIBLE);
                }else{
                    tv_zj.setVisibility(View.GONE);
                }
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
    @Override
    protected void onResume() {
        super.onResume();

        if(bottomorgChildNode != null && bottomorgChildNode.size()>0){
            bottomorgChildNode.clear();
        }
        String collectjson = AppApplication.preferenceProvider.getCollectjson();
        if(!TextUtils.isEmpty(collectjson) && collectjson != null){
            Type dataType = new TypeToken<List<MyCityBean2>>() {
            }.getType();
            ArrayList<MyCityBean2> tempdata = GsonUtil.getGson().fromJson(collectjson
                    , dataType);

            try {
                if(tempdata != null && tempdata.size()>0){
                    for (int i=tempdata.size()-1;i<tempdata.size();i--){

                        Log.e("aaa","索引："+i);
                        MyCityBean2 myCityBean2 = tempdata.get(i);
                        bottomorgChildNode.add(myCityBean2);
                        if( i ==0){
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        if(bottomorgChildNode.size()>0){
            tv_zj.setVisibility(View.VISIBLE);
        }else{
            tv_zj.setVisibility(View.GONE);
        }
        if(myLvAdapter1 == null){
            myLvAdapter1 = new MyLvAdapter1();
            mDatamember.setAdapter(myLvAdapter1);
        }else{
            myLvAdapter1.notifyDataSetChanged();
        }


    }
        public void getData(String parentCode){
        try {
            list = db.findAll(Selector
                    .from(MyCityBean2.class)
                    .where("parentCode", "=", parentCode)
                    .and(WhereBuilder.b("category", "=",
                            "face2face")).and(WhereBuilder.b("valid", "=",
                            1)).and(WhereBuilder.b("name", "=",
                            "平顶山市"))
                    .orderBy("sort", false));

            String json = GsonUtil.getJson(list);
            Log.e("aaa","面对面："+json);

//            for (MyCityBean2 myCityBean2 : list){
//                Integer hasChildren = myCityBean2.getHasChildren();
//                if(hasChildren == 1){
//                    toporgChildNode.add(myCityBean2);
//                }else{
//                    bottomorgChildNode.add(myCityBean2);
//                }
//            }



        } catch (DbException e) {
            e.printStackTrace();
        }
    }
//    class MyLvAdapter extends BaseAdapter {
//
//
//        @Override
//        public int getCount() {
//            return toporgChildNode.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return toporgChildNode.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(final int i, View convertView, ViewGroup viewGroup) {
//            final ViewHolder groupViewHolder;
//            if (convertView == null) {
//                convertView = LayoutInflater.from(XingZhengFuWuRootActivity.this).inflate(R.layout.item_lv_childlj_xzfw,null);
//                groupViewHolder = new ViewHolder();
//                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
////                groupViewHolder.cb_agree = (CheckBox) convertView.findViewById(R.id.cb_agree);
//                convertView.setTag(groupViewHolder);
//            } else {
//                groupViewHolder = (ViewHolder) convertView.getTag();
//            }
//            MyCityBean2 orgNode = toporgChildNode.get(i);
//            String name = orgNode.getName();
//            groupViewHolder.tvTitle.setText(name);
//
//
//
//            return convertView;
//        }
//    }
//
//    class ViewHolder{
//        TextView tvTitle;
////        CheckBox cb_agree;
//    }
    private void logout(){
        SipProfile sp = YealinkApi.instance().getSipProfile();
        sp.registerName = "";
        sp.userName = "";
        sp.password = "";
        YealinkApi.instance().registerSip(sp);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        YealinkApi.instance().removeRegistListener(mRegistListener);
    }
    private void setDialog() {
        // TODO Auto-generated method stub
        ll_status = (LinearLayout) LayoutInflater.from(XingZhengFuWuRootActivity.this).inflate(R.layout.ad_threetree_status, null);
        statusText = (TextView) ll_status.findViewById(tv_status);
        builder = new AlertDialog.Builder(XingZhengFuWuRootActivity.this, R.style.Base_Theme_AppCompat_Dialog);
        builder.setView(ll_status);
        builder.setCancelable(false);
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                logout();
            }
        });
        ad = builder.create();
        YealinkApi.instance().addRegistListener(mRegistListener);
    }
    private static final int MSG_ACCOUNT_CHANGE = 200;
    RegistListener mRegistListener = new RegistListener() {
        @Override
        public void onCloudRegist(final int status) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    updateStatus(status);
                }
            });

        }
        @Override
        public void onSipRegist(int status) {
            handler.sendEmptyMessage(MSG_ACCOUNT_CHANGE);
        }
    };
    class MyLvAdapter1 extends BaseAdapter {


        @Override
        public int getCount() {
            return bottomorgChildNode.size();
        }

        @Override
        public Object getItem(int i) {
            return bottomorgChildNode.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            final ViewHolder1 groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(XingZhengFuWuRootActivity.this).inflate(R.layout.item_lv_child1lj_xzfw,null);
                groupViewHolder = new ViewHolder1();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
                groupViewHolder.label_expand_num = (TextView) convertView.findViewById(R.id.label_expand_num);
                groupViewHolder.iv_flag = (ImageView) convertView.findViewById(R.id.iv_flag);
                groupViewHolder.listvideo = (ImageView) convertView.findViewById(R.id.list_video);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (ViewHolder1) convertView.getTag();
            }
            final MyCityBean2 bean = bottomorgChildNode.get(i);
            String name = bean.getName();
            final String serialNumber = bean.getCalledNum();
            groupViewHolder.tvTitle.setText(name);
            groupViewHolder.label_expand_num.setText(serialNumber);
//            int pos = i%imgflags.length;
            groupViewHolder.iv_flag.setImageResource(R.drawable.fwmdm_list_logo);
            if(TextUtils.isEmpty(serialNumber)){
                groupViewHolder.listvideo.setImageResource(R.drawable.list_video_unenable);
            }else{
                groupViewHolder.listvideo.setImageResource(R.drawable.list_video);
            }
            groupViewHolder.listvideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    if (ad != null && ad.isShowing()) {
//                        ad.dismiss();
//                    }
//                    try {
//                        ad.show();
//                    } catch (Exception e1) {
//                        // TODO Auto-generated catch block
//                        e1.printStackTrace();
//                    }
//                    callId = bean.getCalledNum();
//                    logout();
//                    Log.d("aaa", "bean.getCallingNum()=" + bean.getCallingNum() + "callId＝"+callId);
//                    if (bean.getCallingNum()!=null&&bean.getCallingNum().length()!=6) {
//                        YealinkApi.instance().registerYms(bean.getCallingNum(),
//                                bean.getCallingPassword(),
//                                "pds01.com", "202.110.98.2");
//                    }else {
//                        SipProfile sp = YealinkApi.instance().getSipProfile();
//                        sp.userName = bean.getCallingNum();
//                        sp.registerName = bean.getCallingNum();
//                        sp.password = bean.getCallingNum();
////					sp.userName = "901089";
////					sp.registerName = "901089";
////					sp.password = "901089";
//                        sp.server = "42.236.68.190";
//                        sp.port = 5237;
//                        sp.isEnableOutbound=false;
//                        sp.isBFCPEnabled = false;
//                        sp.isEnabled = true;
//                        sp.transPort = SipProfile.TRANSPORT_TCP;
//                        YealinkApi.instance().registerSip(sp);
//                    }
                }
            });
            return convertView;
        }
    }
    private void setStatus(SipProfile sp){
        if (sp.state == AccountConstant.STATE_UNKNOWN) {
            statusText.setText("未知");
        } else if (sp.state == AccountConstant.STATE_DISABLED) {
            statusText.setText("禁用");
        } else if (sp.state == AccountConstant.STATE_REGING) {
            statusText.setText("正在注册...");
        } else if (sp.state == AccountConstant.STATE_REGED) {
            statusText.setText("已注册");
            if (ad != null && ad.isShowing()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ad.dismiss();
                        YealinkApi.instance().call(XingZhengFuWuRootActivity.this, callId);
                    }
                },2000);
            }
        } else if (sp.state == AccountConstant.STATE_REG_FAILED) {
            statusText.setText("注册失败");
        } else if (sp.state == AccountConstant.STATE_UNREGING) {
            statusText.setText("正在注销...");
        } else if (sp.state == AccountConstant.STATE_UNREGED) {
            statusText.setText("已注销");
        } else if (sp.state == AccountConstant.STATE_REG_ON_BOOT) {
            statusText.setText("启动时注册");
        }
    }


    private void updateStatus(int status) {
        if (status == AccountConstant.STATE_UNKNOWN) {
            statusText.setText("未知");
        } else if (status == AccountConstant.STATE_DISABLED) {
            statusText.setText("禁用");
        } else if (status == AccountConstant.STATE_REGING) {
            statusText.setText("正在注册...");
        } else if (status == AccountConstant.STATE_REGED) {
            statusText.setText("已注册");
            if (ad != null && ad.isShowing()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ad.dismiss();
                        YealinkApi.instance().call(XingZhengFuWuRootActivity.this, callId);
                    }
                },2000);
            }
        } else if (status == AccountConstant.STATE_REG_FAILED) {
            statusText.setText("注册失败");
        } else if (status == AccountConstant.STATE_UNREGING) {
            statusText.setText("正在注销...");
        } else if (status == AccountConstant.STATE_UNREGED) {
            statusText.setText("已注销");
        } else if (status == AccountConstant.STATE_REG_ON_BOOT) {
            statusText.setText("启动时注册");
        }
    }
    class ViewHolder1{
        TextView tvTitle;
        TextView label_expand_num;
        ImageView iv_flag;
        ImageView listvideo;
    }
    public void initRightTitle(String text){

        ImageView iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(text);
        ImageView iv_right = (ImageView) findViewById(R.id.iv_right);
        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(XingZhengFuWuRootActivity.this,
                        Activity_searchxzfw.class);
                startActivity(intent);
            }
        });

    }


}
