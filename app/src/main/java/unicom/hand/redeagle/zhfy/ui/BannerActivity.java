package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.adapter.PopListAdapter;
import unicom.hand.redeagle.zhfy.bean.EnableBannerBean;
import unicom.hand.redeagle.zhfy.bean.QueryLayoutBean;
import unicom.hand.redeagle.zhfy.bean.SaveBanner;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

public class BannerActivity extends Activity {

    private RelativeLayout rl_layout_gravity;
    private TextView tv_avg_model;
    private String intentconfId;
    private String intentconfEntity;
    private EditText et_content;
    private ImageView iv_show_name;
    private TextView tv_send;
    private boolean isShowSwitch = false;
    private TextView tv_save;
    private String conferenceBannerId = "";
    private String showpos = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        intentconfId = getIntent().getStringExtra("confId");
        intentconfEntity = getIntent().getStringExtra("confEntity");
        ImageView iv_left = (ImageView)findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv_show_name = (ImageView)findViewById(R.id.iv_show_name);
        tv_save = (TextView)findViewById(R.id.tv_save);
        tv_send = (TextView)findViewById(R.id.tv_send);
        et_content = (EditText)findViewById(R.id.et_content);
        tv_avg_model = (TextView)findViewById(R.id.tv_avg_model);
        rl_layout_gravity = (RelativeLayout)findViewById(R.id.rl_layout_gravity);
        rl_layout_gravity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow1(BannerActivity.this);
            }
        });
        iv_show_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isShowSwitch){
                    isShowSwitch = true;
                    iv_show_name.setImageResource(R.drawable.switch_on);
                }else{
                    isShowSwitch = false;
                    iv_show_name.setImageResource(R.drawable.switch_off);
                }
                enableBanner();
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShowSwitch){
                    sendBanner();

                }else{
                    Toast.makeText(BannerActivity.this, "请先启用横幅", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    saveBanner();
            }
        });
        getData();
    }

    private void enableBanner() {
        EnableBannerBean enablebean = new EnableBannerBean();
        enablebean.setConferenceRecordId(intentconfId);
        enablebean.setConferenceBannerId(conferenceBannerId);
        enablebean.setConfEntity(intentconfEntity);
        enablebean.setEnable(isShowSwitch);
        String json = GsonUtil.getJson(enablebean);
        AppApplication.getDataProvider().enableBanner(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","发送banner："+o.toString());

                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1) {
                        if(isShowSwitch){
                            tv_send.setEnabled(true);
                            tv_send.setTextColor(UIUtils.getColor(R.color.red));
                        }else{
                            tv_send.setEnabled(false);
                            tv_send.setTextColor(UIUtils.getColor(R.color.gray));
                        }

                        Toast.makeText(BannerActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    }else{
                        isShowSwitch = !isShowSwitch;
                        if(isShowSwitch){
                            iv_show_name.setImageResource(R.drawable.switch_on);
                            tv_send.setEnabled(true);
                            tv_send.setTextColor(UIUtils.getColor(R.color.red));
                        }else{
                            iv_show_name.setImageResource(R.drawable.switch_off);
                            tv_send.setEnabled(false);
                            tv_send.setTextColor(UIUtils.getColor(R.color.gray));
                        }
                        Toast.makeText(BannerActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    isShowSwitch = !isShowSwitch;
                    if(isShowSwitch){
                        iv_show_name.setImageResource(R.drawable.switch_on);
                        tv_send.setEnabled(true);
                        tv_send.setTextColor(UIUtils.getColor(R.color.red));
                    }else{
                        iv_show_name.setImageResource(R.drawable.switch_off);
                        tv_send.setEnabled(false);
                        tv_send.setTextColor(UIUtils.getColor(R.color.gray));
                    }
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }

    private void sendBanner() {
        SaveBanner savebean = new SaveBanner();
        savebean.setConferenceRecordId(intentconfId);
        savebean.setConferenceBannerId(conferenceBannerId);
        savebean.setConfEntity(intentconfEntity);
        String bannercontent = et_content.getText().toString().trim();
        if(TextUtils.isEmpty(bannercontent)){
            Toast.makeText(this, "请先输入横幅内容", Toast.LENGTH_SHORT).show();
            return;
        }
        savebean.setBannerContent(bannercontent);
        savebean.setEnable(isShowSwitch);
        savebean.setPosition(showpos);
        String json = GsonUtil.getJson(savebean);
        AppApplication.getDataProvider().sendBanner(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","发送banner："+o.toString());

                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1) {
                        Toast.makeText(BannerActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(BannerActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }

    private void saveBanner() {
        SaveBanner savebean = new SaveBanner();
        savebean.setConferenceRecordId(intentconfId);
        savebean.setConferenceBannerId(conferenceBannerId);
        savebean.setConfEntity(intentconfEntity);
        String bannercontent = et_content.getText().toString().trim();
        if(TextUtils.isEmpty(bannercontent)){
            Toast.makeText(this, "请先输入横幅内容", Toast.LENGTH_SHORT).show();
            return;
        }
        savebean.setBannerContent(bannercontent);
        savebean.setEnable(isShowSwitch);
        savebean.setPosition(showpos);
        String json = GsonUtil.getJson(savebean);
        AppApplication.getDataProvider().saveBanner(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","保存banner："+o.toString());

                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1) {
                        Toast.makeText(BannerActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
//                        JSONObject data = object.getJSONObject("data");
//                        String position = data.getString("position");
//                        showpos = position;
//                        tv_avg_model.setText(UIUtils.getBannerPos(position));
//                        conferenceBannerId = data.getString("conferenceBannerId");
//                        boolean enable = data.getBoolean("enable");
//                        isShowSwitch = enable;
//                        if(enable){
//                            tv_send.setEnabled(true);
//                            iv_show_name.setImageResource(R.drawable.switch_on);
//                        }else{
//                            tv_send.setEnabled(false);
//                            iv_show_name.setImageResource(R.drawable.switch_off);
//                        }
//                        String bannerContent = data.getString("bannerContent");
//                        et_content.setText(UIUtils.getRealText1(bannerContent));
                    }else{
                        Toast.makeText(BannerActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });


    }

    private void getData() {
        QueryLayoutBean querybean = new QueryLayoutBean();
        querybean.setConfEntity(intentconfEntity);
        querybean.setConferenceRecordId(intentconfId);
        String json = GsonUtil.getJson(querybean);
        AppApplication.getDataProvider().getBanner(json, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("aaa","获取banner："+o.toString());

                try {
                    JSONObject object = new JSONObject(o.toString());
                    int ret = object.getInt("ret");
                    if(ret == 1) {
                        JSONObject data = object.getJSONObject("data");
                        String position = data.getString("position");
                        showpos = position;
                        tv_avg_model.setText(UIUtils.getBannerPos(position));
                        conferenceBannerId = data.getString("conferenceBannerId");
                        boolean enable = data.getBoolean("enable");
                        isShowSwitch = enable;
                        if(enable){
                            tv_send.setEnabled(true);
                            tv_send.setTextColor(UIUtils.getColor(R.color.red));
//                            tv_send.setEnabled(true);
                            iv_show_name.setImageResource(R.drawable.switch_on);
                        }else{
                            tv_send.setEnabled(false);
                            tv_send.setTextColor(UIUtils.getColor(R.color.gray));
//                            tv_send.setEnabled(false);
                            iv_show_name.setImageResource(R.drawable.switch_off);
                        }
                        String bannerContent = data.getString("bannerContent");
                        et_content.setText(UIUtils.getRealText1(bannerContent));
                        if(!TextUtils.equals(bannerContent,"null") && !TextUtils.equals(bannerContent,null) && bannerContent.length()>0){
                            et_content.setSelection(bannerContent.length());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }

    private String[] titles1 = {"置顶","居中","置底"};
    private void popWindow1(Context context){
        View contentView1= LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pop_list, null, false);
        ListView list = (ListView)contentView1.findViewById(R.id.list);
        list.setAdapter(new PopListAdapter(context,titles1));
        TextView tv_number = (TextView)contentView1.findViewById(R.id.tv_number);
        tv_number.setVisibility(View.GONE);
        TextView tv_poptitle = (TextView)contentView1.findViewById(R.id.tv_title);
        tv_poptitle.setText("显示位置");
        TextView tv_close = (TextView)contentView1.findViewById(R.id.tv_close);
        final PopupWindow pop1=new PopupWindow(contentView1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop1.setOutsideTouchable(true);
        pop1.setTouchable(true);

        pop1.showAtLocation(rl_layout_gravity, Gravity.BOTTOM,0,0);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pop1.dismiss();
                tv_avg_model.setText(titles1[position]);
                if(id == 0){//等分
                    showpos = "Top";
                }else if(id == 1){//1+N
                    showpos = "Middle";
                }else if(id == 2){//单分全屏
                    showpos = "Bottom";
                }

            }
        });
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop1.dismiss();
            }
        });
    }


}
