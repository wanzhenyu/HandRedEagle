package unicom.hand.redeagle.zhfy.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.view.CustomHorizontalScrollView;
import unicom.hand.redeagle.zhfy.view.MyListView;
import com.yealink.common.data.Contact;
import com.yealink.common.data.OrgNode;
import com.yealink.sdk.YealinkApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyDepartMentActivity1 extends AppCompatActivity {
    private LinearLayout mDepartMentPathLL;
    private MyListView mDataLV;
    private MyListView mDatamember;
    private List<OrgNode> orgChildNode;
    private List<OrgNode> toporgChildNode;
    private List<OrgNode> bottomorgChildNode;
    private MyLvAdapter myLvAdapter;
    private TextView common_title_right;
    private CustomHorizontalScrollView mCHSView;
//    private TextView tv_link;
    private int width;
    private int index = -1;
    private List<View> views;
    private MyLvAdapter1 myLvAdapter1;
    private ArrayList<Contact> meetnowcontact;
    private int totalnum = 0;
    private TextView tv_number;
    boolean ischeck;
    private Map<String,Boolean> map=new HashMap<>();
    private Map<String,Boolean> bottommap=new HashMap<>();
    private boolean istopallcheck = false;
    private boolean isbottomallcheck = false;
    //    private ImageView mTopLeftIv, mTopRightIv;
    private Map<String,Boolean> istemselect=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_depart_ment1);
        views = new ArrayList<>();
        mDepartMentPathLL = (LinearLayout) findViewById(R.id.department_hscrollview_llcover);
        mDataLV = (MyListView) findViewById(R.id.department_datalist);
        mDatamember = (MyListView) findViewById(R.id.department_member);
        totalnum = getIntent().getIntExtra("totalnum", 0);
        ischeck = getIntent().getBooleanExtra("ischeck", false);


        meetnowcontact = (ArrayList<Contact>)getIntent().getSerializableExtra("meetnowcontact");
        mCHSView = (CustomHorizontalScrollView) findViewById(R.id.department_hscrollview);
//        mTopLeftIv = (ImageView) findViewById(R.id.department_topview_leftarrow);
//        mTopRightIv = (ImageView) findViewById(R.id.department_topview_rightarrow);
        String orgId = getIntent().getStringExtra("orgId");
        String title = getIntent().getStringExtra("title");
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toporgChildNode = new ArrayList<>();
        bottomorgChildNode = new ArrayList<>();
         orgChildNode = YealinkApi.instance().getOrgChildNode(false, orgId);
        for (int i=0;i<orgChildNode.size();i++){
            OrgNode orgNode = orgChildNode.get(i);
            int type = orgNode.getType();
            if(type == 1){
                toporgChildNode.add(orgNode);
            }else if(type == 2){
                bottomorgChildNode.add(orgNode);
            }
        }
        common_title_right = (TextView)findViewById(R.id.common_title_right);
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

//        View inflate = View.inflate(MyDepartMentActivity.this, R.layout.item_top_link, null);
//        tv_link = (TextView)inflate.findViewById(R.id.tv_link);
//        tv_link.setText(title);
//        mDepartMentPathLL.addView(inflate);
        String parentorgId = getIntent().getStringExtra("parentorgId");
        String parenttitle = getIntent().getStringExtra("parenttitle");
        initTab(parenttitle,parentorgId);
        initTab(title,orgId);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        /**
         * orgNode.getType()
         * 节点类型1，代表部门，2代表个人
         */
        mDataLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OrgNode orgNode = orgChildNode.get(i);
                String name = orgNode.getName();
                common_title_right.setText(name);
                String nodeId = orgNode.getNodeId();

                Boolean aBoolean = map.get(nodeId);
                if(aBoolean != null){
                    istemselect.put(nodeId,aBoolean);
                    map.put(nodeId,aBoolean);

                }
                initTab(name,nodeId);

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
                orgChildNode = YealinkApi.instance().getOrgChildNode(false, nodeId);
                for (int j=0;j<orgChildNode.size();j++){
                    OrgNode orgNodej = orgChildNode.get(j);
                    Log.e("aaaa","子节点类型："+orgNodej.getType());
                }
                for (int k=0;k<orgChildNode.size();k++){
                    OrgNode orgchildNodeitem = orgChildNode.get(k);
                    int type = orgchildNodeitem.getType();
                    if(type == 1){

                        toporgChildNode.add(orgchildNodeitem);
                    }else if(type == 2){
                        bottomorgChildNode.add(orgchildNodeitem);
                    }
                }
//                Log.e("aaa","节点类型："+type);
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





        tv_number = (TextView)findViewById(R.id.tv_number);
        tv_number.setText("已选成员:"+totalnum+">");
        LinearLayout ll_lj = (LinearLayout)findViewById(R.id.ll_lj);

        ll_lj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                YealinkApi.instance().meetNow(MyDepartMentActivity1.this,meetnowcontact);
            }
        });









    }

    public void initTab(String name, final String nodeId){
        index++;
        View inflate1 = View.inflate(MyDepartMentActivity1.this, R.layout.item_top_link, null);
        final TextView tv_link = (TextView)inflate1.findViewById(R.id.tv_link);
        tv_link.setText(name+" >");
        tv_link.setTag(index);
        views.add(index,inflate1);
        mDepartMentPathLL.addView(inflate1);

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
                    Log.e("aaaa",mDepartMentPathLL.getChildCount()+"索引："+tv_link.getTag());
                    int pos = ((int)tv_link.getTag());
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
                    orgChildNode = YealinkApi.instance().getOrgChildNode(false, nodeId);
                    for (int k=0;k<orgChildNode.size();k++){
                        OrgNode orgchildNodeitem = orgChildNode.get(k);
                        int type = orgchildNodeitem.getType();



                        if(type == 1){
                            toporgChildNode.add(orgchildNodeitem);
                        }else if(type == 2){
                            bottomorgChildNode.add(orgchildNodeitem);
                        }
                    }

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

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mCHSView.fullScroll(CustomHorizontalScrollView.FOCUS_RIGHT);
            }
        });
    }

    class MyLvAdapter extends BaseAdapter {


        @Override
        public int getCount() {
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
                convertView = LayoutInflater.from(MyDepartMentActivity1.this).inflate(R.layout.item_lv_childlj,null);
                groupViewHolder = new ViewHolder();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
                groupViewHolder.cb_agree = (CheckBox) convertView.findViewById(R.id.cb_agree);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (ViewHolder) convertView.getTag();
            }
           final OrgNode orgNode = toporgChildNode.get(i);
            String serialNumber = orgNode.getSerialNumber();
            Log.e("fff",orgNode.getNodeId()+",部门："+serialNumber);
            final String nodeId = orgNode.getNodeId();
            String name = orgNode.getName();
            final int size = orgNode.getData().getCount();
            groupViewHolder.tvTitle.setText(name+"("+size+")");
            if(!istopallcheck){
                if(ischeck){
                    groupViewHolder.cb_agree.setChecked(true);
                    map.put(nodeId,true);
                }else{
                    map.put(nodeId,false);
                    groupViewHolder.cb_agree.setChecked(false);
                }
            }else{
                Boolean aBoolean = map.get(nodeId);
                if(aBoolean != null){
                    if(aBoolean){
                        groupViewHolder.cb_agree.setChecked(true);
                    }else{
                        groupViewHolder.cb_agree.setChecked(false);
                    }
                }


            }

//            Boolean aBoolean1 = istemselect.get(nodeId);
//            if(aBoolean1 != null){
//                if(aBoolean){
//                    groupViewHolder.cb_agree.setChecked(true);
//                }else{
//                    groupViewHolder.cb_agree.setChecked(false);
//                }
//            }

            groupViewHolder.cb_agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    istopallcheck = true;
                    if(groupViewHolder.cb_agree.isChecked()){
                        map.put(nodeId,true);
                        if(!meetnowcontact.contains(orgNode)){
                            meetnowcontact.add(orgNode);
                        }
                        totalnum += size;

                    }else{
                        map.put(nodeId,false);
                        if(meetnowcontact.contains(orgNode)){
                            meetnowcontact.remove(orgNode);
                        }
                        totalnum -= size;

                    }
                    notifyDataSetChanged();
                    tv_number.setText("已选成员:"+totalnum+">");


                }
            });

            return convertView;
        }
    }

    class ViewHolder{
        TextView tvTitle;
        CheckBox cb_agree;
    }

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
                convertView = LayoutInflater.from(MyDepartMentActivity1.this).inflate(R.layout.item_lv_child1lj,null);
                groupViewHolder = new ViewHolder1();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
                groupViewHolder.cb_agree = (CheckBox) convertView.findViewById(R.id.cb_agree);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (ViewHolder1) convertView.getTag();
            }
            final OrgNode orgNode = bottomorgChildNode.get(i);
            String name = orgNode.getName();
            String serialNumber = orgNode.getSerialNumber();
            final String nodeId = orgNode.getNodeId();
            Log.e("fff",orgNode.getNodeId()+",人名："+serialNumber);
            final int size = orgNode.getData().getCount();
            groupViewHolder.tvTitle.setText(name+"\n"+serialNumber);
            if(!isbottomallcheck){
                if(ischeck){
                    groupViewHolder.cb_agree.setChecked(true);
                    bottommap.put(nodeId,true);
                }else{
                    bottommap.put(nodeId,false);
                    groupViewHolder.cb_agree.setChecked(false);
                }
            }
            Boolean aBoolean = bottommap.get(nodeId);
            if(aBoolean){
                groupViewHolder.cb_agree.setChecked(true);
            }else{
                groupViewHolder.cb_agree.setChecked(false);
            }
            OrgNode parent = orgNode.getParent();
            if(parent != null){
                String nodeId1 = parent.getNodeId();
                Boolean aBoolean1 = map.get(nodeId1);
                if(aBoolean1 != null){
                    if(aBoolean){
                        groupViewHolder.cb_agree.setChecked(true);
                    }else{
                        groupViewHolder.cb_agree.setChecked(false);
                    }
                }
            }


            groupViewHolder.cb_agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isbottomallcheck = true;
                    if(groupViewHolder.cb_agree.isChecked()){
                        bottommap.put(nodeId,true);
                        if(!meetnowcontact.contains(orgNode)){
                            meetnowcontact.add(orgNode);
                        }
                        totalnum += size;

                    }else{
                        bottommap.put(nodeId,false);
                        if(meetnowcontact.contains(orgNode)){
                            meetnowcontact.remove(orgNode);
                        }
                        totalnum -= size;

                    }
                    notifyDataSetChanged();
                    tv_number.setText("已选成员:"+totalnum+">");


                }
            });
            return convertView;
        }
    }

    class ViewHolder1{
        TextView tvTitle;
        CheckBox cb_agree;
    }



}
