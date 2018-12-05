package unicom.hand.redeagle.zhfy.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.yealink.common.data.AccountConstant;
import com.yealink.common.data.SipProfile;
import com.yealink.sdk.RegistListener;
import com.yealink.sdk.YealinkApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.Common;
import unicom.hand.redeagle.zhfy.bean.MyCityBean2;
import unicom.hand.redeagle.zhfy.view.CustomHorizontalScrollView;
import unicom.hand.redeagle.zhfy.view.MyListView;

import static unicom.hand.redeagle.R.id.tv_status;


public class XingZhengFuWuActivity extends AppCompatActivity {
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case MSG_ACCOUNT_CHANGE:
                    setStatus(YealinkApi.instance().getSipProfile());
                    break;
            }
        }
    };
    private LinearLayout mDepartMentPathLL;

    private MyListView mDataLV;
    private MyListView mDatamember;
    private List<MyCityBean2> toporgChildNode;
    private List<MyCityBean2> bottomorgChildNode;
    private MyLvAdapter myLvAdapter;
    private MyLvAdapter1 myLvAdapter1;

    private TextView common_title_right;
    private CustomHorizontalScrollView mCHSView;
//    private TextView tv_link;
    private int width;
    private int index = -1;
    private List<View> views;

    DbUtils db;
    List<MyCityBean2> list;
    List<MyCityBean2> jsonlist;
    private TextView tv_parentName;
    private int imgflags[] = {R.drawable.list_flag,R.drawable.list_flag1,R.drawable.list_flag2,R.drawable.list_flag3};
    private String callId = "";// 被叫电话
    private AlertDialog ad;
    private AlertDialog.Builder builder;
    private LinearLayout ll_status;
    private TextView statusText;
    private String issz;
    private String code;
    private TextView tv_parentCode;
    private ImageView iv_img;
    private LinearLayout ll_top;
    private Map<String,MyCityBean2> clickItemCodes;
    private TextView tv_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_xzfw);
        views = new ArrayList<>();
        mDepartMentPathLL = (LinearLayout) findViewById(R.id.department_hscrollview_llcover);
        mDataLV = (MyListView) findViewById(R.id.department_datalist);
        mDatamember = (MyListView) findViewById(R.id.department_member);
        mCHSView = (CustomHorizontalScrollView) findViewById(R.id.department_hscrollview);
        jsonlist = new ArrayList<>();
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickItemCodes != null){
                    int totalnum = clickItemCodes.size();
                    int size = totalnum-1;
                    Log.e("aaa","已经到父节点了："+size);
                    if(size>=1){
                        MyCityBean2 myCityBean2 = clickItemCodes.get(size + "");
                        removeTopTab(size,myCityBean2);
                        if(clickItemCodes.containsKey(totalnum + "")){
                            clickItemCodes.remove(totalnum + "");

                        }

                    }else{
                        finish();

                        Log.e("aaa","已经到父节点了");

                    }



                }
            }
        });
        toporgChildNode = new ArrayList<>();
        bottomorgChildNode = new ArrayList<>();

        iv_img = (ImageView)findViewById(R.id.iv_img);
        ll_top = (LinearLayout)findViewById(R.id.ll_top);
        tv_close = (TextView)findViewById(R.id.tv_close);

        db = DbUtils.create(XingZhengFuWuActivity.this, Common.DB_NAME);

        common_title_right = (TextView)findViewById(R.id.common_title_right);
        tv_parentName = (TextView)findViewById(R.id.tv_parentName);
        tv_parentCode = (TextView)findViewById(R.id.tv_parentCode);

        issz = getIntent().getStringExtra("issz");
        code = getIntent().getStringExtra("code");
        String name = getIntent().getStringExtra("dw");
        String isaddcity = getIntent().getStringExtra("isaddcity");
//        tv_parentName.setText(name);

        setDialog();
        clickItemCodes = new HashMap<>();

        getData(code);
        String title = name;
        common_title_right.setText(title);
        if(myLvAdapter == null){
            myLvAdapter = new MyLvAdapter();
            mDataLV.setAdapter(myLvAdapter);
        }else{
            myLvAdapter.notifyDataSetChanged();
        }
        if(myLvAdapter1 == null){
            myLvAdapter1 = new MyLvAdapter1();
            mDatamember.setAdapter(myLvAdapter1);
        }else{
            myLvAdapter1.notifyDataSetChanged();
        }


//        initTab("河南省平顶山市","401");

        final MyCityBean2 myCityBean2intent = (MyCityBean2)getIntent().getSerializableExtra("item");

        initTab(title, code,myCityBean2intent);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String calledNum = myCityBean2intent.getCalledNum();
                if(!TextUtils.isEmpty(calledNum)){
                    Intent intent = new Intent(XingZhengFuWuActivity.this,MdmContactDetailActivity.class);
                    intent.putExtra("orgNode",myCityBean2intent);
                    startActivity(intent);
                }else{
                    Toast.makeText(XingZhengFuWuActivity.this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mDataLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyCityBean2 myCityBean2 = toporgChildNode.get(i);
                String name = myCityBean2.getName();
                String code = myCityBean2.getCode();
                tv_parentName.setText(name);
                String calledNum = myCityBean2.getCalledNum();
                tv_parentCode.setText(calledNum);
                if(TextUtils.isEmpty(calledNum)){
                    iv_img.setImageResource(R.drawable.list_video_unenable);
                }else{
                    iv_img.setImageResource(R.drawable.list_video);
                }
                issz = "0";
                initTab(name,code,myCityBean2);

                if(toporgChildNode != null){
                    if(toporgChildNode.size()>0){
                        toporgChildNode.clear();
                    }
                }
                if(bottomorgChildNode != null){
                    if(bottomorgChildNode.size()>0){
                        bottomorgChildNode.clear();
                    }
                }
                getData(code);
                if(myLvAdapter == null){
                    myLvAdapter = new MyLvAdapter();
                    mDataLV.setAdapter(myLvAdapter);
                }else{
                    myLvAdapter.notifyDataSetChanged();
                }
                if(myLvAdapter1 == null){
                    myLvAdapter1 = new MyLvAdapter1();
                    mDatamember.setAdapter(myLvAdapter1);
                }else{
                    myLvAdapter1.notifyDataSetChanged();
                }





            }
        });






        mDatamember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyCityBean2 orgNode =  (MyCityBean2)adapterView.getAdapter().getItem(i);
                String calledNum = orgNode.getCalledNum();
                if(!TextUtils.isEmpty(calledNum)){
                    Intent intent = new Intent(XingZhengFuWuActivity.this,MdmContactDetailActivity.class);
                    intent.putExtra("orgNode",orgNode);
                    startActivity(intent);
                }else{
                    Toast.makeText(XingZhengFuWuActivity.this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                }





            }
        });

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();



            }
        });


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(clickItemCodes != null){
                int totalnum = clickItemCodes.size();

                if(totalnum <=1){
                    finish();
                    return true;
                }
                int size = totalnum-1;
                if(size>=1){
                    MyCityBean2 myCityBean2 = clickItemCodes.get(size + "");
                    removeTopTab(size,myCityBean2);
                    if(clickItemCodes.containsKey(totalnum + "")){
                        clickItemCodes.remove(totalnum + "");

                    }

                }else{


                }



            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void removeTopTab(int pos,final MyCityBean2 myCityBean2){


        index = pos-1;

        int childCount = mDepartMentPathLL.getChildCount();

        Log.e("aaaa","开始移除的起点："+pos);
        for (int i=pos;i<childCount;i++){
            View view1 = views.get(i);
            mDepartMentPathLL.removeView(view1);

            Log.e("aaaa","开始移除："+i);
        }
        for (int i=0;i<mDepartMentPathLL.getChildCount();i++){
            TextView childAt = (TextView)mDepartMentPathLL.getChildAt(i);
            if(i == mDepartMentPathLL.getChildCount()-1){
                childAt.setTextColor(getResources().getColor(R.color.red));
            }else{
                childAt.setTextColor(getResources().getColor(R.color.black));
            }
        }
        if(toporgChildNode != null){
            if(toporgChildNode.size()>0){
                toporgChildNode.clear();
            }
        }
        if(bottomorgChildNode != null){
            if(bottomorgChildNode.size()>0){
                bottomorgChildNode.clear();
            }
        }
        String name = myCityBean2.getName();

        tv_parentName.setText(name);
        String calledNum1 = myCityBean2.getCalledNum();
        tv_parentCode.setText(calledNum1);
        if(TextUtils.isEmpty(calledNum1)){
            iv_img.setImageResource(R.drawable.list_video_unenable);
        }else{
            iv_img.setImageResource(R.drawable.list_video);
        }
        ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String calledNum = myCityBean2.getCalledNum();
                if(!TextUtils.isEmpty(calledNum)){
                    Intent intent = new Intent(XingZhengFuWuActivity.this,MdmContactDetailActivity.class);
                    intent.putExtra("orgNode",myCityBean2);
                    startActivity(intent);
                }else{
                    Toast.makeText(XingZhengFuWuActivity.this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                }
            }
        });
        String code = myCityBean2.getCode();
        getData(code);
        if(myLvAdapter == null){
            myLvAdapter = new MyLvAdapter();
            mDataLV.setAdapter(myLvAdapter);
        }else{
            myLvAdapter.notifyDataSetChanged();
        }
        if(myLvAdapter1 == null){
            myLvAdapter1 = new MyLvAdapter1();
            mDatamember.setAdapter(myLvAdapter1);
        }else{
            myLvAdapter1.notifyDataSetChanged();
        }





    }

    public void getData(String parentCode){
        Log.e("aaaa","城市编码："+parentCode);
        if(mDepartMentPathLL.getChildCount() == 1){
            parentCode = code;
        }
        try {
            list = db.findAll(Selector
                    .from(MyCityBean2.class)
                    .where("parentCode", "=", parentCode)
                    .and(WhereBuilder.b("category", "=",
                            "face2face")).and(WhereBuilder.b("valid", "=",
                            1))
                    .orderBy("sort", false));
            List<MyCityBean2> listall
                    = db.findAll(MyCityBean2.class);
            Log.e("aaa","listall：="+listall.size());
//            String json = GsonUtil.getJson(list);
//            Log.e("aaa","面对面："+json);
//
            for (int k = 0;k<list.size();k++){
                final MyCityBean2 myCityBean2 = list.get(k);
                Integer hasChildren = myCityBean2.getHasChildren();
                if(hasChildren == 1){
                    if(TextUtils.equals(issz,"1")){//市级单位只显示第一个

                        if(k == 0){
                            toporgChildNode.add(myCityBean2);
                            String name = myCityBean2.getName();
                            tv_parentName.setText(name);
                            String calledNum = myCityBean2.getCalledNum();
                            tv_parentCode.setText(calledNum);
                            if(TextUtils.isEmpty(calledNum)){
                                iv_img.setImageResource(R.drawable.list_video_unenable);
                            }else{
                                iv_img.setImageResource(R.drawable.list_video);
                            }
                            ll_top.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String calledNum = myCityBean2.getCalledNum();
                                    if(!TextUtils.isEmpty(calledNum)){
                                        Intent intent = new Intent(XingZhengFuWuActivity.this,MdmContactDetailActivity.class);
                                        intent.putExtra("orgNode",myCityBean2);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(XingZhengFuWuActivity.this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });




                        }



                    }else{//乡村只显示除了第一条数据

                        if(k == 0&&TextUtils.equals(code,parentCode)){
                            String name = myCityBean2.getName();
                            tv_parentName.setText(name);
                            String calledNum = myCityBean2.getCalledNum();
                            tv_parentCode.setText(calledNum);
                            if(TextUtils.isEmpty(calledNum)){
                                iv_img.setImageResource(R.drawable.list_video_unenable);
                            }else{
                                iv_img.setImageResource(R.drawable.list_video);
                            }
                            ll_top.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String calledNum = myCityBean2.getCalledNum();
                                    if(!TextUtils.isEmpty(calledNum)){
                                        Intent intent = new Intent(XingZhengFuWuActivity.this,MdmContactDetailActivity.class);
                                        intent.putExtra("orgNode",myCityBean2);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(XingZhengFuWuActivity.this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            toporgChildNode.add(myCityBean2);
                        }
                    }

                }else{
                    bottomorgChildNode.add(myCityBean2);
//                    if(TextUtils.equals(code,parentCode)){
//                        bottomorgChildNode.remove(0);
//                    }else{
//
//                    }

//                    if(TextUtils.equals(issz,"0")){
//                        bottomorgChildNode.add(myCityBean2);
//                    }


                }
            }

//            if(TextUtils.equals(issz,"0")){
//                if(toporgChildNode != null && toporgChildNode.size()>0){
//                    toporgChildNode.remove(0);
//                }
//            }






        } catch (DbException e) {
            e.printStackTrace();
        }
    }






    public void initTab(final String name, final String nodeId,final MyCityBean2 myCityBean2){
        tv_parentName.setText(name);
        String calledNum = myCityBean2.getCalledNum();
        tv_parentCode.setText(calledNum);
        if(TextUtils.isEmpty(calledNum)){
            iv_img.setImageResource(R.drawable.list_video_unenable);
        }else{
            iv_img.setImageResource(R.drawable.list_video);
        }
        ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String calledNum = myCityBean2.getCalledNum();
                if(!TextUtils.isEmpty(calledNum)){
                    Intent intent = new Intent(XingZhengFuWuActivity.this,MdmContactDetailActivity.class);
                    intent.putExtra("orgNode",myCityBean2);
                    startActivity(intent);
                }else{
                    Toast.makeText(XingZhengFuWuActivity.this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                }
            }
        });
        index++;
        View inflate1 = View.inflate(XingZhengFuWuActivity.this, R.layout.item_top_link, null);
        final TextView tv_link = (TextView)inflate1.findViewById(R.id.tv_link);
        tv_link.setText(name+" >");
        tv_link.setTag(index);
        views.add(index,inflate1);
        mDepartMentPathLL.addView(inflate1);
        clickItemCodes.put(mDepartMentPathLL.getChildCount()+"",myCityBean2);

            for (int i=0;i<mDepartMentPathLL.getChildCount();i++){
                TextView childAt = (TextView)mDepartMentPathLL.getChildAt(i);
                if(i == mDepartMentPathLL.getChildCount()-1){
                    childAt.setTextColor(getResources().getColor(R.color.red));
                }else{
                    childAt.setTextColor(getResources().getColor(R.color.black));
                }
            }
            tv_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    removeTab(tv_link,name,myCityBean2,nodeId);

//                    int pos = ((int)tv_link.getTag());
//                    index = pos;
//
//                    int childCount = mDepartMentPathLL.getChildCount();
//
//
//                    for (int i=pos+1;i<childCount;i++){
//                        View view1 = views.get(i);
//                        mDepartMentPathLL.removeView(view1);
//
//                        Log.e("aaaa","开始移除："+i);
//                    }
//                    for (int i=0;i<mDepartMentPathLL.getChildCount();i++){
//                        TextView childAt = (TextView)mDepartMentPathLL.getChildAt(i);
//                        if(i == mDepartMentPathLL.getChildCount()-1){
//                            childAt.setTextColor(getResources().getColor(R.color.red));
//                        }else{
//                            childAt.setTextColor(getResources().getColor(R.color.black));
//                        }
//                    }
//                    if(toporgChildNode != null){
//                        if(toporgChildNode.size()>0){
//                            toporgChildNode.clear();
//                        }
//                    }
//                    if(bottomorgChildNode != null){
//                        if(bottomorgChildNode.size()>0){
//                            bottomorgChildNode.clear();
//                        }
//                    }
//                    tv_parentName.setText(name);
//                    String calledNum1 = myCityBean2.getCalledNum();
//                    tv_parentCode.setText(calledNum1);
//                    if(TextUtils.isEmpty(calledNum1)){
//                        iv_img.setImageResource(R.drawable.list_video_unenable);
//                    }else{
//                        iv_img.setImageResource(R.drawable.list_video);
//                    }
//                    ll_top.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            String calledNum = myCityBean2.getCalledNum();
//                            if(!TextUtils.isEmpty(calledNum)){
//                                Intent intent = new Intent(XingZhengFuWuActivity.this,MdmContactDetailActivity.class);
//                                intent.putExtra("orgNode",myCityBean2);
//                                startActivity(intent);
//                            }else{
//                                Toast.makeText(XingZhengFuWuActivity.this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                    getData(nodeId);
//                    if(myLvAdapter == null){
//                        myLvAdapter = new MyLvAdapter();
//                        mDataLV.setAdapter(myLvAdapter);
//                    }else{
//                        myLvAdapter.notifyDataSetChanged();
//                    }
//                    if(myLvAdapter1 == null){
//                        myLvAdapter1 = new MyLvAdapter1();
//                        mDatamember.setAdapter(myLvAdapter1);
//                    }else{
//                        myLvAdapter1.notifyDataSetChanged();
//                    }
                }
            });

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mCHSView.fullScroll(CustomHorizontalScrollView.FOCUS_RIGHT);
            }
        });
    }


    public void removeTab(TextView tv_link,String name,final MyCityBean2 myCityBean2,String nodeId){


        int pos = ((int)tv_link.getTag());
        int removepos =  pos+1;
        if(clickItemCodes.containsKey((removepos) + "")){
            Log.e("aaaa","包含："+removepos);
            clickItemCodes.remove((pos+1) + "");

        }else{
            Log.e("aaaa","不包含："+removepos);
        }
        index = pos;

        int childCount = mDepartMentPathLL.getChildCount();


        for (int i=pos+1;i<childCount;i++){
            View view1 = views.get(i);
            mDepartMentPathLL.removeView(view1);

            Log.e("aaaa","开始移除："+i);
        }
        for (int i=0;i<mDepartMentPathLL.getChildCount();i++){
            TextView childAt = (TextView)mDepartMentPathLL.getChildAt(i);
            if(i == mDepartMentPathLL.getChildCount()-1){
                childAt.setTextColor(getResources().getColor(R.color.red));
            }else{
                childAt.setTextColor(getResources().getColor(R.color.black));
            }
        }
        if(toporgChildNode != null){
            if(toporgChildNode.size()>0){
                toporgChildNode.clear();
            }
        }
        if(bottomorgChildNode != null){
            if(bottomorgChildNode.size()>0){
                bottomorgChildNode.clear();
            }
        }
        tv_parentName.setText(name);
        String calledNum1 = myCityBean2.getCalledNum();
        tv_parentCode.setText(calledNum1);
        if(TextUtils.isEmpty(calledNum1)){
            iv_img.setImageResource(R.drawable.list_video_unenable);
        }else{
            iv_img.setImageResource(R.drawable.list_video);
        }
        ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String calledNum = myCityBean2.getCalledNum();
                if(!TextUtils.isEmpty(calledNum)){
                    Intent intent = new Intent(XingZhengFuWuActivity.this,MdmContactDetailActivity.class);
                    intent.putExtra("orgNode",myCityBean2);
                    startActivity(intent);
                }else{
                    Toast.makeText(XingZhengFuWuActivity.this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getData(nodeId);
        if(myLvAdapter == null){
            myLvAdapter = new MyLvAdapter();
            mDataLV.setAdapter(myLvAdapter);
        }else{
            myLvAdapter.notifyDataSetChanged();
        }
        if(myLvAdapter1 == null){
            myLvAdapter1 = new MyLvAdapter1();
            mDatamember.setAdapter(myLvAdapter1);
        }else{
            myLvAdapter1.notifyDataSetChanged();
        }





    }








    class MyLvAdapter extends BaseAdapter {


        @Override
        public int getCount() {
//            if(TextUtils.equals(issz,"1")){
//                if(toporgChildNode.size()>0){
//                    return 1;
//                }
//
//            }

            return toporgChildNode.size();
        }

        @Override
        public Object getItem(int i) {
            return toporgChildNode.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            final ViewHolder groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(XingZhengFuWuActivity.this).inflate(R.layout.item_lv_child1lj_redeagle,null);
                groupViewHolder = new ViewHolder();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
              groupViewHolder.label_expand_num = (TextView) convertView.findViewById(R.id.label_expand_num);
// groupViewHolder.cb_agree = (CheckBox) convertView.findViewById(R.id.cb_agree);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (ViewHolder) convertView.getTag();
            }

            MyCityBean2 orgNode = toporgChildNode.get(i);
            String name = orgNode.getName();
            String id = orgNode.getCalledNum();
            groupViewHolder.tvTitle.setText(name);
            groupViewHolder.label_expand_num.setText(id);


//            if(TextUtils.equals(issz,"1")){
//                MyCityBean2 orgNode = toporgChildNode.get(0);
//                String name = orgNode.getName();
//                String id = orgNode.getId();
//                groupViewHolder.tvTitle.setText(name);
//                groupViewHolder.label_expand_num.setText(id);
//            }else{
//                if(i != 0){
//                    MyCityBean2 orgNode = toporgChildNode.get(i);
//                    String name = orgNode.getName();
//                    String id = orgNode.getId();
//                    groupViewHolder.tvTitle.setText(name);
//                    groupViewHolder.label_expand_num.setText(id);
//                }
//
//            }



            return convertView;
        }
    }

    class ViewHolder{
        TextView tvTitle;
        TextView label_expand_num;
//        CheckBox cb_agree;
    }
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
        ll_status = (LinearLayout) LayoutInflater.from(XingZhengFuWuActivity.this).inflate(R.layout.ad_threetree_status, null);
        statusText = (TextView) ll_status.findViewById(tv_status);
        builder = new AlertDialog.Builder(XingZhengFuWuActivity.this, R.style.Base_Theme_AppCompat_Dialog);
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
                convertView = LayoutInflater.from(XingZhengFuWuActivity.this).inflate(R.layout.item_lv_child1lj_xzfw,null);
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



//            groupViewHolder.listvideo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String collectjson = AppApplication.preferenceProvider.getCollectjson();
//                    if (TextUtils.isEmpty(collectjson)) {
//
//                        jsonlist.add(bean);
//                        AppApplication.preferenceProvider.setCollectjson(GsonUtil.getJson(jsonlist));
//                    } else {
//                        Type dataType = new TypeToken<List<MyCityBean2>>() {
//                        }.getType();
//                        ArrayList<MyCityBean2> tempdata = GsonUtil.getGson().fromJson(
//                                collectjson, dataType);
//                        String iscontain = "0";
//                        for (int j = 0; j < tempdata.size(); j++) {
//                            MyCityBean2 myCityBean2 = tempdata.get(j);
//                            String id = myCityBean2.getId();
//                            if (TextUtils.equals(id, serialNumber)) {
//                                iscontain = "1";
//                                break;
//                            } else {
//                                iscontain = "0";
//                            }
//
//                        }
//                        if (TextUtils.equals(iscontain, "0")) {
//                            tempdata.add(bean);
//                            AppApplication.preferenceProvider.setCollectjson(GsonUtil.getJson(tempdata));
//                        }
//
//                    }
//
//
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
//                    Log.d("aaa", "bean.getCallingNum()=" + bean.getCallingNum() + "callId＝" + callId);
//                    if (bean.getCallingNum() != null && bean.getCallingNum().length() != 6) {
//                        YealinkApi.instance().registerYms(bean.getCallingNum(),
//                                bean.getCallingPassword(),
//                                "pds01.com", "202.110.98.2");
//                    } else {
//                        SipProfile sp = YealinkApi.instance().getSipProfile();
//                        sp.userName = bean.getCallingNum();
//                        sp.registerName = bean.getCallingNum();
//                        sp.password = bean.getCallingNum();
////					sp.userName = "901089";
////					sp.registerName = "901089";
////					sp.password = "901089";
//                        sp.server = "42.236.68.190";
//                        sp.port = 5237;
//                        sp.isEnableOutbound = false;
//                        sp.isBFCPEnabled = false;
//                        sp.isEnabled = true;
//                        sp.transPort = SipProfile.TRANSPORT_TCP;
//                        YealinkApi.instance().registerSip(sp);
//                    }
//                }
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
//            });





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
                        YealinkApi.instance().call(XingZhengFuWuActivity.this, callId);
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
                        YealinkApi.instance().call(XingZhengFuWuActivity.this, callId);
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



}
