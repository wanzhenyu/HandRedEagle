package unicom.hand.redeagle.zhfy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import unicom.hand.redeagle.zhfy.bean.MyNodeBean;
import unicom.hand.redeagle.zhfy.bean.SerializableMap;
import unicom.hand.redeagle.zhfy.view.CustomHorizontalScrollView;
import unicom.hand.redeagle.zhfy.view.MyListView;
import com.yealink.common.data.Contact;
import com.yealink.common.data.OrgNode;
import com.yealink.sdk.YealinkApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyDepartMentActivity2 extends AppCompatActivity {
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
//    boolean ischeck;
//    private Map<String,Boolean> map=new HashMap<>();
//    private Map<String,Boolean> bottommap=new HashMap<>();
//    private boolean istopallcheck = false;
//    private boolean isbottomallcheck = false;
    //    private ImageView mTopLeftIv, mTopRightIv;
//    private Map<String,Boolean> istemselect=new HashMap<>();


    private ArrayList<MyNodeBean> nodeBeens;
    private Map<String,MyNodeBean> maps ;
    /**
     * 0全选，1反选，2正常状态
     */
    private Map<String,String> allcheck;
    private String isallchecknodeid = "";
    private TextView tv_all;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_depart_ment1);
        views = new ArrayList<>();
        mDepartMentPathLL = (LinearLayout) findViewById(R.id.department_hscrollview_llcover);
        mDataLV = (MyListView) findViewById(R.id.department_datalist);
        mDatamember = (MyListView) findViewById(R.id.department_member);
        totalnum = getIntent().getIntExtra("totalnum", 0);
//        ischeck = getIntent().getBooleanExtra("ischeck", false);
        nodeBeens = new ArrayList<>();
        maps = new HashMap<>();
        meetnowcontact = new ArrayList<>();
        allcheck = new HashMap<>();
//        meetnowcontact = (ArrayList<Contact>)getIntent().getSerializableExtra("meetnowcontact");
        mCHSView = (CustomHorizontalScrollView) findViewById(R.id.department_hscrollview);
//        mTopLeftIv = (ImageView) findViewById(R.id.department_topview_leftarrow);
//        mTopRightIv = (ImageView) findViewById(R.id.department_topview_rightarrow);
//        String orgId = getIntent().getStringExtra("orgId");
        String title = "河南省平顶山市";
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toporgChildNode = new ArrayList<>();
        bottomorgChildNode = new ArrayList<>();

        OrgNode orgRoot = YealinkApi.instance().getOrgRoot(false);
        String parentnodeid = orgRoot.getNodeId();

        orgChildNode = YealinkApi.instance().getOrgChildNode(false, parentnodeid);
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

        tv_all = (TextView)findViewById(R.id.tv_all);
        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aBoolean = allcheck.get(isallchecknodeid);
                if(aBoolean == null){
                    allcheck.put(isallchecknodeid,"0");
                    tv_all.setText("反选");
                }else{

                    if(TextUtils.equals(aBoolean,"0")){
                        allcheck.put(isallchecknodeid,"1");
                        tv_all.setText("反选");
                    }else if(TextUtils.equals(aBoolean,"1")){
                        allcheck.put(isallchecknodeid,"0");
                        tv_all.setText("全选");
                    }


                }

                if(myLvAdapter1 != null){
                    myLvAdapter1.notifyDataSetChanged();
                }

                tv_number.setText("已选成员:"+meetnowcontact.size()+">");


            }
        });




//        View inflate = View.inflate(MyDepartMentActivity.this, R.layout.item_top_link, null);
//        tv_link = (TextView)inflate.findViewById(R.id.tv_link);
//        tv_link.setText(title);
//        mDepartMentPathLL.addView(inflate);
//        String parentorgId = getIntent().getStringExtra("parentorgId");
//        String parenttitle = getIntent().getStringExtra("parenttitle");
        initTab(title,parentnodeid);
//        initTab(title,orgId);
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

                YealinkApi.instance().meetNow(MyDepartMentActivity2.this,meetnowcontact);
            }
        });


        tv_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyDepartMentActivity2.this,DataListActivity.class);
//                intent.putExtra("list",maps);
//
////                intent.putParcelableArrayListExtra("list",nodeBeens);
//
//                startActivity(intent);

                SerializableMap serializabmap = new SerializableMap();
                serializabmap.setMap(maps);
                Bundle bundle=new Bundle();
                bundle.putSerializable("map", serializabmap);
                intent.putExtras(bundle);
                startActivity(intent);










            }
        });

        mDatamember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ImageView checkBox = (ImageView)view.findViewById(R.id.cb_agree);
                OrgNode orgNode =  (OrgNode)adapterView.getAdapter().getItem(i);
                MyNodeBean nodeBean = new MyNodeBean();
                String name = orgNode.getName();
                String serialNumber = orgNode.getSerialNumber();
                String nodeId = orgNode.getNodeId();
                nodeBean.setSerialNumber(serialNumber);
                nodeBean.setName(name);
                nodeBean.setNodeId(nodeId);
                allcheck.put(isallchecknodeid,"2");
//                Boolean aBoolean = allcheck.get(isallchecknodeid);
//                if(aBoolean == null){//
//                    allcheck.put(isallchecknodeid,false);
//                }else{
//                    allcheck.put(isallchecknodeid,!aBoolean);
//
//
//                }

//                int i1 = Integer.parseInt(serialNumber);serialNumber
//                MyNodeBean myNodeBean1 = nodeBeens.get(i1);
                MyNodeBean myNodeBean = maps.get(serialNumber);
                if(myNodeBean != null){//已选中

                    if(myNodeBean.isCheck()){
//                        checkBox.setImageResource(R.drawable.cb_normal);
                        for (int p=0;p<meetnowcontact.size();p++){
                            Contact contact = meetnowcontact.get(p);
                            String serialNumber1 = contact.getSerialNumber();
                            if(TextUtils.equals(serialNumber1,serialNumber)){
                                meetnowcontact.remove(contact);
                                break;
                            }
                        }


                        myNodeBean.setCheck(false);
                        maps.remove(serialNumber);
                    }else{
                        meetnowcontact.add(orgNode);
//                        checkBox.setImageResource(R.drawable.cb_checked);
                        myNodeBean.setCheck(true);
                        maps.put(serialNumber,myNodeBean);
                    }

                    if(myLvAdapter1 != null){
                        myLvAdapter1.notifyDataSetChanged();
                    }
                }else{//未选中
//                    checkBox.setImageResource(R.drawable.cb_checked);
                    meetnowcontact.add(orgNode);
                    nodeBean.setCheck(true);
                    maps.put(serialNumber,nodeBean);
                    if(myLvAdapter1 != null){
                        myLvAdapter1.notifyDataSetChanged();
                    }
                }



//                if(nodeBeens.size() == 0){
//                    checkBox.setImageResource(R.drawable.cb_checked);
//                    nodeBean.setCheck(true);
//                    nodeBeens.add(nodeBean);
//                }else{
//                    int size = nodeBeens.size();
//                    for (int u=0;u<size;u++){
//                        MyNodeBean myNodeBean = nodeBeens.get(u);
//                        String serialNumber1 = myNodeBean.getSerialNumber();
//                        if(TextUtils.equals(serialNumber1,serialNumber)){
//                            checkBox.setImageResource(R.drawable.cb_normal);
//                            nodeBean.setCheck(false);
//                            nodeBeens.remove(myNodeBean);
//                            if(myLvAdapter1 != null){
//                                myLvAdapter1.notifyDataSetChanged();
//                            }
//
//                            break;
//
//
//                        }else{
//
//
//
//
//
//
//
//                        }
//                    }
//
//
//                }

//                Iterator<Map.Entry<String, MyNodeBean>> iterator = maps.entrySet().iterator();
//                while (iterator.hasNext()){
//                    MyNodeBean value = iterator.next().getValue();
//
//
//                    meetnowcontact.add(value);
//                }

                tv_number.setText("已选成员:"+meetnowcontact.size()+">");



            }
        });




    }

    public void initTab(String name, final String nodeId){
        isallchecknodeid = nodeId;
        String aBoolean = allcheck.get(isallchecknodeid);
        if(aBoolean == null){
            allcheck.put(isallchecknodeid,"0");
            tv_all.setText("反选");
        }else{

            if(TextUtils.equals(aBoolean,"0")){
                allcheck.put(isallchecknodeid,"1");
                tv_all.setText("反选");
            }else if(TextUtils.equals(aBoolean,"1")){
                allcheck.put(isallchecknodeid,"0");
                tv_all.setText("全选");
            }


        }

        index++;
        View inflate1 = View.inflate(MyDepartMentActivity2.this, R.layout.item_top_link, null);
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
                convertView = LayoutInflater.from(MyDepartMentActivity2.this).inflate(R.layout.item_lv_childlj,null);
                groupViewHolder = new ViewHolder();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
                groupViewHolder.cb_agree = (CheckBox) convertView.findViewById(R.id.cb_agree);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (ViewHolder) convertView.getTag();
            }
           final OrgNode orgNode = toporgChildNode.get(i);
            String serialNumber = orgNode.getSerialNumber();
            final String nodeId = orgNode.getNodeId();
            String name = orgNode.getName();
            final int size = orgNode.getData().getCount();
            groupViewHolder.tvTitle.setText(name+"("+size+")");



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
                convertView = LayoutInflater.from(MyDepartMentActivity2.this).inflate(R.layout.item_lv_child1lj,null);
                groupViewHolder = new ViewHolder1();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
                groupViewHolder.cb_agree = (ImageView) convertView.findViewById(R.id.cb_agree);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (ViewHolder1) convertView.getTag();
            }
            final OrgNode orgNode = bottomorgChildNode.get(i);
            String name = orgNode.getName();
            String serialNumber = orgNode.getSerialNumber();
            final String nodeId = orgNode.getNodeId();
            final int size = orgNode.getData().getCount();
            groupViewHolder.tvTitle.setText(name+"\n"+serialNumber);


//            int i1 = Integer.parseInt(serialNumber);
            MyNodeBean myNodeBean1 = maps.get(serialNumber);

            if(myNodeBean1 != null){
                String serialNumber1 = myNodeBean1.getSerialNumber();

                if(!myNodeBean1.isCheck()){

                    groupViewHolder.cb_agree.setImageResource(R.drawable.cb_normal);
//                    myNodeBean1.setCheck(false);
//                    maps.remove(myNodeBean1);
                }else{

                    groupViewHolder.cb_agree.setImageResource(R.drawable.cb_checked);
//                    myNodeBean1.setCheck(true);
//                    maps.put(serialNumber,myNodeBean1);
                }
//                groupViewHolder.cb_agree.setImageResource(R.drawable.cb_normal);
//                myNodeBean1.setCheck(false);
//                maps.remove(myNodeBean1);
            }else{
                groupViewHolder.cb_agree.setImageResource(R.drawable.cb_normal);
            }

//            if(aBoolean == null){
//                allcheck.put(isallchecknodeid,"0");
//                tv_all.setText("反选");
//            }else{
//
//                if(TextUtils.equals(aBoolean,"0")){
//                    allcheck.put(isallchecknodeid,"1");
//                    tv_all.setText("反选");
//                }else if(TextUtils.equals(aBoolean,"1")){
//                    allcheck.put(isallchecknodeid,"0");
//                    tv_all.setText("全选");
//                }
//
//
//            }






            String aBoolean = allcheck.get(isallchecknodeid);
            if(aBoolean != null){
                if(TextUtils.equals(aBoolean,"1")){
                    Log.e("aaa","全选："+serialNumber);
                    MyNodeBean nodeBean = new MyNodeBean();

                    nodeBean.setSerialNumber(serialNumber);
                    nodeBean.setName(name);
                    nodeBean.setNodeId(nodeId);
                    nodeBean.setCheck(true);

                    if(!maps.containsKey(serialNumber)){
                        maps.put(serialNumber,nodeBean);
                    }


                    if(!meetnowcontact.contains(orgNode)){
                        meetnowcontact.add(orgNode);
                        tv_number.setText("已选成员:"+meetnowcontact.size()+">");
                    }

                    groupViewHolder.cb_agree.setImageResource(R.drawable.cb_checked);


                }else if(TextUtils.equals(aBoolean,"0")){
                    Log.e("aaa","fan选："+serialNumber);
                    MyNodeBean nodeBean = new MyNodeBean();

                    nodeBean.setSerialNumber(serialNumber);
                    nodeBean.setName(name);
                    nodeBean.setNodeId(nodeId);
                    nodeBean.setCheck(false);
                    maps.remove(serialNumber);
                    for (int p=0;p<meetnowcontact.size();p++){
                        Contact contact = meetnowcontact.get(p);
                        String serialNumber2 = contact.getSerialNumber();
                        if(TextUtils.equals(serialNumber2,serialNumber)){
                            meetnowcontact.remove(contact);
                            tv_number.setText("已选成员:"+meetnowcontact.size()+">");
                            break;
                        }
                    }
                    groupViewHolder.cb_agree.setImageResource(R.drawable.cb_normal);
                }
            }else{




            }
//            else{
//                groupViewHolder.cb_agree.setImageResource(R.drawable.cb_checked);
//
//                MyNodeBean nodeBean = new MyNodeBean();
//
//                nodeBean.setSerialNumber(serialNumber);
//                nodeBean.setName(name);
//                nodeBean.setNodeId(nodeId);
//                nodeBean.setCheck(true);
//
//                maps.put(serialNumber,nodeBean);
//            }
//            if(myNodeBean1.isCheck()){
//                groupViewHolder.cb_agree.setImageResource(R.drawable.cb_normal);
//                myNodeBean1.setCheck(false);
//                nodeBeens.remove(myNodeBean1);
//
//            }else{
//                groupViewHolder.cb_agree.setImageResource(R.drawable.cb_checked);
//                myNodeBean1.setCheck(true);
//                nodeBeens.add(myNodeBean1);
//
//
//            }
            return convertView;
        }
    }

    class ViewHolder1{
        TextView tvTitle;
        ImageView cb_agree;
    }



}
