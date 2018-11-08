package unicom.hand.redeagle.zhfy.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yealink.common.data.AccountConstant;
import com.yealink.common.data.SipProfile;
import com.yealink.sdk.RegistListener;
import com.yealink.sdk.YealinkApi;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.bean.ItemBean;
import unicom.hand.redeagle.zhfy.bean.MyCityBean2;
import unicom.hand.redeagle.zhfy.ui.XingZhengFuWuRootActivity;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;

import static unicom.hand.redeagle.R.id.tv_status;

/**
 * Created by linana on 2018-04-14.
 */

public class MdmAdapter extends BaseAdapter {
    List<MyCityBean2> bottomorgChildNode;
    Context context;
    private int imgflags[] = {R.drawable.list_flag,R.drawable.list_flag1,R.drawable.list_flag2,R.drawable.list_flag3};
//    private String callId = "";// 被叫电话
//    private AlertDialog ad;
//    private AlertDialog.Builder builder;
//    private LinearLayout ll_status;
//    private TextView statusText;
    String isadd = "0";
    public MdmAdapter(Context context, List<MyCityBean2> bottomorgChildNode,String isadd){
        this.context = context;
        this.bottomorgChildNode = bottomorgChildNode;
        this.isadd = isadd;

    }
    private String sqlString = "";// 查询标识

    public void setSqlString(String sqlString) {
        this.sqlString = sqlString;
    }
    private ListViewAdapter.CalInterface calInterface;

    public interface CalInterface {
        void onVedeoCallClick(MyCityBean2 bean);
    }

    public void setCalInterface(ListViewAdapter.CalInterface calInterface) {
        this.calInterface = calInterface;
    }
    public void setDate(List<MyCityBean2> list) {
        bottomorgChildNode = list;
        notifyDataSetChanged();
    }
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
        final MdmHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_child1lj_xzfw,null);
            groupViewHolder = new MdmHolder();
            groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
            groupViewHolder.label_expand_num = (TextView) convertView.findViewById(R.id.label_expand_num);
            groupViewHolder.iv_flag = (ImageView) convertView.findViewById(R.id.iv_flag);
            groupViewHolder.listvideo = (ImageView) convertView.findViewById(R.id.list_video);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (MdmHolder) convertView.getTag();
        }
        final MyCityBean2 bean = bottomorgChildNode.get(i);
        String name = bean.getName();
        final String serialNumber = bean.getCalledNum();
        groupViewHolder.tvTitle.setText(name);
        groupViewHolder.label_expand_num.setText(serialNumber);
//        int pos = i%imgflags.length;
        groupViewHolder.iv_flag.setImageResource(R.drawable.fwmdm_list_logo);
        if(TextUtils.equals(this.isadd,"0")){
            if(TextUtils.isEmpty(serialNumber)){
                groupViewHolder.listvideo.setImageResource(R.drawable.list_video_unenable);
            }else{
                groupViewHolder.listvideo.setImageResource(R.drawable.list_video);
            }
        }else{
            groupViewHolder.listvideo.setImageResource(R.drawable.add_item);
        }
        groupViewHolder.listvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if(TextUtils.equals(isadd,"0")){
//                    calInterface.onVedeoCallClick(bean);
                }else{




                    if ((sqlString.equals("face2face")
                            && 2 < bottomorgChildNode.get(i).getLayer() && bottomorgChildNode
                            .get(i).getLayer() < 5)
                            || bottomorgChildNode.get(i).getHasChildren() == 1) {
                        String[] arry = new String[] { "添加该单位及其子单位到首页",
                                "仅添加该单位到首页" };// 性别选择
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                context);// 自定义对话框
                        builder.setTitle("请选择添加到首页的方式");
                        builder.setSingleChoiceItems(arry, 0,
                                new DialogInterface.OnClickListener() {// 2默认的选中

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {// which是被选中的位置
                                        // showToast(which+"");
                                        Type dataType = new TypeToken<List<ItemBean>>() {
                                        }.getType();
                                        ArrayList<ItemBean> tempdata;
                                        tempdata = GsonUtil
                                                .getGson()
                                                .fromJson(
                                                        AppApplication.preferenceProvider
                                                                .getjson(),
                                                        dataType);
                                        ItemBean bean = new ItemBean();
                                        bean.setName(bottomorgChildNode.get(i)
                                                .getCompany());

                                        bean.setIsSelect(true);
                                        if (sqlString.equals("medical")) {
                                            bean.setIcon(R.drawable.mdm_ylzx);
                                        }else if (sqlString.equals("law")) {
                                            bean.setIcon(R.drawable.mdm_flzx);
                                        }else if (sqlString.equals("agriculture")) {
                                            bean.setIcon(R.drawable.mdm_njzx);
                                        }else if (sqlString.equals("education")) {
                                            bean.setIcon(R.drawable.education);
                                        }else if (sqlString.equals("hotline")) {
                                            bean.setIcon(R.drawable.mdm_fwrx);
                                        }else  {
                                            bean.setIcon(R.drawable.item_xxc);
                                        }
                                        bean.setSqlString(sqlString);
                                        bean.setIsMain(true);
                                        bean.setIsLongBoolean(false);
                                        bean.setIsAdd(false);
                                        if (which == 0) {
                                            bean.setHasChildren(1);
                                        } else {
                                            bean.setHasChildren(0);
                                        }

                                        bean.setCode(bottomorgChildNode.get(i)
                                                .getCode());
                                        tempdata.add(bean);
                                        AppApplication.preferenceProvider

                                                .setjson(GsonUtil
                                                        .getJson(tempdata));
                                        dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
                                        Toast.makeText(context, "添加成功",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                        builder.setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.cancel();
                                    }
                                });
                        builder.show();// 让弹出框显示
                    } else {
                        Type dataType = new TypeToken<List<ItemBean>>() {
                        }.getType();
                        ArrayList<ItemBean> tempdata;
                        tempdata = GsonUtil.getGson().fromJson(
                                AppApplication.preferenceProvider.getjson(),
                                dataType);
                        ItemBean bean = new ItemBean();
                        bean.setName(bottomorgChildNode.get(i).getCompany());
                        if (sqlString.equals("medical")) {
                            bean.setIcon(R.drawable.mdm_ylzx);
                        }else if (sqlString.equals("law")) {
                            bean.setIcon(R.drawable.mdm_flzx);
                        }else if (sqlString.equals("agriculture")) {
                            bean.setIcon(R.drawable.mdm_njzx);
                        }else if (sqlString.equals("education")) {
                            bean.setIcon(R.drawable.education);
                        }else if (sqlString.equals("hotline")) {
                            bean.setIcon(R.drawable.mdm_fwrx);
                        }else  {
                            bean.setIcon(R.drawable.item_xxc);
                        }
                        bean.setIsSelect(true);
                        bean.setSqlString(sqlString);
                        bean.setIsMain(true);
                        bean.setIsLongBoolean(false);
                        bean.setIsAdd(false);
                        bean.setHasChildren(0);
                        bean.setCode(bottomorgChildNode.get(i).getCode());
                        tempdata.add(bean);
                        AppApplication.preferenceProvider.setjson(GsonUtil
                                .getJson(tempdata));
                        Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT)
                                .show();
                    }
















                }






//                if (ad != null && ad.isShowing()) {
//                    ad.dismiss();
//                }
//                try {
//                    ad.show();
//                } catch (Exception e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//                callId = bean.getCalledNum();
//                logout();
//                Log.d("aaa", "bean.getCallingNum()=" + bean.getCallingNum() + "callId＝"+callId);
//                if (bean.getCallingNum()!=null&&bean.getCallingNum().length()!=6) {
//                    YealinkApi.instance().registerYms(bean.getCallingNum(),
//                            bean.getCallingPassword(),
//                            "pds01.com", "202.110.98.2");
//                }else {
//                    SipProfile sp = YealinkApi.instance().getSipProfile();
//                    sp.userName = bean.getCallingNum();
//                    sp.registerName = bean.getCallingNum();
//                    sp.password = bean.getCallingNum();
////					sp.userName = "901089";
////					sp.registerName = "901089";
////					sp.password = "901089";
//                    sp.server = "42.236.68.190";
//                    sp.port = 5237;
//                    sp.isEnableOutbound=false;
//                    sp.isBFCPEnabled = false;
//                    sp.isEnabled = true;
//                    sp.transPort = SipProfile.TRANSPORT_TCP;
//                    YealinkApi.instance().registerSip(sp);
//                }
            }
        });
        return convertView;
    }

    public void addMenu(){

    }
//    private void logout(){
//        SipProfile sp = YealinkApi.instance().getSipProfile();
//        sp.registerName = "";
//        sp.userName = "";
//        sp.password = "";
//        YealinkApi.instance().registerSip(sp);
//    }
//
//    private void setDialog() {
//        // TODO Auto-generated method stub
//        ll_status = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.ad_threetree_status, null);
//        statusText = (TextView) ll_status.findViewById(tv_status);
//        builder = new AlertDialog.Builder(context, R.style.Base_Theme_AppCompat_Dialog);
//        builder.setView(ll_status);
//        builder.setCancelable(false);
//        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                dialog.dismiss();
//                logout();
//            }
//        });
//        ad = builder.create();
//        YealinkApi.instance().addRegistListener(mRegistListener);
//    }
//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch(msg.what){
//                case MSG_ACCOUNT_CHANGE:
//                    setStatus(YealinkApi.instance().getSipProfile());
//                    break;
//            }
//        }
//    };
//    private static final int MSG_ACCOUNT_CHANGE = 200;
//    RegistListener mRegistListener = new RegistListener() {
//        @Override
//        public void onCloudRegist(final int status) {
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    updateStatus(status);
//                }
//            });
//
//        }
//        @Override
//        public void onSipRegist(int status) {
//            handler.sendEmptyMessage(MSG_ACCOUNT_CHANGE);
//        }
//    };
//
//
//
//    private void setStatus(SipProfile sp){
//        if (sp.state == AccountConstant.STATE_UNKNOWN) {
//            statusText.setText("未知");
//        } else if (sp.state == AccountConstant.STATE_DISABLED) {
//            statusText.setText("禁用");
//        } else if (sp.state == AccountConstant.STATE_REGING) {
//            statusText.setText("正在注册...");
//        } else if (sp.state == AccountConstant.STATE_REGED) {
//            statusText.setText("已注册");
//            if (ad != null && ad.isShowing()) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        ad.dismiss();
//                        YealinkApi.instance().call(context, callId);
//                    }
//                },2000);
//            }
//        } else if (sp.state == AccountConstant.STATE_REG_FAILED) {
//            statusText.setText("注册失败");
//        } else if (sp.state == AccountConstant.STATE_UNREGING) {
//            statusText.setText("正在注销...");
//        } else if (sp.state == AccountConstant.STATE_UNREGED) {
//            statusText.setText("已注销");
//        } else if (sp.state == AccountConstant.STATE_REG_ON_BOOT) {
//            statusText.setText("启动时注册");
//        }
//    }
//
//
//    private void updateStatus(int status) {
//        if (status == AccountConstant.STATE_UNKNOWN) {
//            statusText.setText("未知");
//        } else if (status == AccountConstant.STATE_DISABLED) {
//            statusText.setText("禁用");
//        } else if (status == AccountConstant.STATE_REGING) {
//            statusText.setText("正在注册...");
//        } else if (status == AccountConstant.STATE_REGED) {
//            statusText.setText("已注册");
//            if (ad != null && ad.isShowing()) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        ad.dismiss();
//                        YealinkApi.instance().call(context, callId);
//                    }
//                },2000);
//            }
//        } else if (status == AccountConstant.STATE_REG_FAILED) {
//            statusText.setText("注册失败");
//        } else if (status == AccountConstant.STATE_UNREGING) {
//            statusText.setText("正在注销...");
//        } else if (status == AccountConstant.STATE_UNREGED) {
//            statusText.setText("已注销");
//        } else if (status == AccountConstant.STATE_REG_ON_BOOT) {
//            statusText.setText("启动时注册");
//        }
//    }





    class MdmHolder{
        TextView tvTitle;
        TextView label_expand_num;
        ImageView iv_flag;
        ImageView listvideo;
    }
}
