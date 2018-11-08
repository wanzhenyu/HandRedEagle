package unicom.hand.redeagle.zhfy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.bean.MyNodeBean;
import unicom.hand.redeagle.zhfy.bean.SerializableMap;
import unicom.hand.redeagle.zhfy.utils.UIUtils;
import unicom.hand.redeagle.zhfy.view.CustomHorizontalScrollView;
import unicom.hand.redeagle.zhfy.view.MyListView;
import com.yealink.common.data.Contact;
import com.yealink.common.data.OrgNode;
import com.yealink.sdk.YealinkApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyDepartMentActivity3 extends AppCompatActivity {
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

    private Map<String,Boolean> allcheck;
    private String isallchecknodeid = "";
    private TextView tv_all;

    private Map<String,Boolean> isallclick ;
    private View view_1;

    private Map<String,String> clickItemCodes;
    private TextView tv_close;

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
        isallclick = new HashMap<>();
        view_1 = findViewById(R.id.view_1);
        tv_close = (TextView)findViewById(R.id.tv_close);
        clickItemCodes = new HashMap<>();
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
                if(clickItemCodes != null){
                    int totalnum = clickItemCodes.size();
                    int size = totalnum-1;
                    Log.e("aaa","已经到父节点了："+size);
                    if(size>=1){
                        String code = clickItemCodes.get(size + "");
                        removeTobTab(size,code);
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
        common_title_right = (TextView)findViewById(R.id.common_title_right);
        common_title_right.setText("联系人");
        tv_all = (TextView)findViewById(R.id.tv_all);

        OrgNode orgRoot = YealinkApi.instance().getOrgRoot(false);
        if(orgRoot != null){
            String parentnodeid = orgRoot.getNodeId();

            orgChildNode = YealinkApi.instance().getOrgChildNode(false, parentnodeid);
            for (int i=0;i<orgChildNode.size();i++){
                OrgNode orgNode = orgChildNode.get(i);
                int type = orgNode.getType();
                if(type == 1){
                    String name = orgNode.getName();
                    boolean b = UIUtils.removeContactVirtual(name);
                    if(!b){
                        toporgChildNode.add(orgNode);
                    }
                }else if(type == 2){
                    String name = orgNode.getName();
                    boolean b = UIUtils.removeContactVirtual(name);
                    if(!b){
                        bottomorgChildNode.add(orgNode);
                    }

                }
            }

            if(toporgChildNode.size()>0){
                view_1.setVisibility(View.VISIBLE);
            }else{
                view_1.setVisibility(View.GONE);
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


            tv_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Boolean aBoolean = allcheck.get(isallchecknodeid);
                    if(aBoolean == null){
                        allcheck.put(isallchecknodeid,true);
                        tv_all.setText("反选");
                    }else{

                        if(aBoolean){
                            allcheck.put(isallchecknodeid,false);
                            tv_all.setText("全选");
                        }else{
                            allcheck.put(isallchecknodeid,true);
                            tv_all.setText("反选");
                        }


                    }


                    for (int j=0;j<bottomorgChildNode.size();j++){
                        OrgNode orgNode1 = bottomorgChildNode.get(j);
                        MyNodeBean nodeBean = new MyNodeBean();
                        String name = orgNode1.getName();
                        String serialNumber = orgNode1.getSerialNumber();
                        String nodeId = orgNode1.getNodeId();
                        nodeBean.setSerialNumber(serialNumber);
                        nodeBean.setName(name);
                        nodeBean.setNodeId(nodeId);
                        String serialNumber1 = orgNode1.getSerialNumber();
                        Boolean aBoolean1 = isallclick.get(isallchecknodeid);
                        if(aBoolean1 == null){
                            nodeBean.setCheck(true);
                            if(!maps.containsKey(serialNumber1)){
                                meetnowcontact.add(orgNode1);
                            }
                            maps.put(serialNumber1,nodeBean);

                        }else{
                            nodeBean.setCheck(!aBoolean1);
                            if(aBoolean1){

                                for (int l=0;l<meetnowcontact.size();l++){
                                    Contact contact = meetnowcontact.get(l);

                                    String serialNumber2 = contact.getSerialNumber();
                                    if(TextUtils.equals(serialNumber2,serialNumber1)){
                                        meetnowcontact.remove(contact);
                                        maps.remove(serialNumber2);

                                    }
                                }



                            }else{
                                if(!maps.containsKey(serialNumber1)){
                                    meetnowcontact.add(orgNode1);
                                }
                                maps.put(serialNumber1,nodeBean);


                            }

                        }


                    }

                    Boolean aBoolean1 = isallclick.get(isallchecknodeid);
                    if(aBoolean1 == null){
                        isallclick.put(isallchecknodeid,false);
                    }else{
                        isallclick.put(isallchecknodeid,!aBoolean1);
                    }


                    if(myLvAdapter1 != null){
                        myLvAdapter1.notifyDataSetChanged();
                    }

                    tv_number.setText("已选成员:"+meetnowcontact.size()+">");


                }
            });


            initTab(title,parentnodeid);
        }else{
            view_1.setVisibility(View.GONE);
            Toast.makeText(this, "联系人正在加载中，请稍后再试", Toast.LENGTH_SHORT).show();
        }

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
//                common_title_right.setText(name);
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
                if(toporgChildNode.size()>0){
                    view_1.setVisibility(View.VISIBLE);
                }else{
                    view_1.setVisibility(View.GONE);
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

                YealinkApi.instance().meetNow(MyDepartMentActivity3.this,meetnowcontact);
            }
        });


        tv_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyDepartMentActivity3.this,DataListActivity.class);

                SerializableMap serializabmap = new SerializableMap();
                serializabmap.setMap(maps);
                Bundle bundle=new Bundle();
                bundle.putSerializable("map", serializabmap);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);


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
                    String code = clickItemCodes.get(size + "");
                    removeTobTab(size,code);
                    if(clickItemCodes.containsKey(totalnum + "")){
                        clickItemCodes.remove(totalnum + "");

                    }

                }else{

//                    Log.e("aaa","已经到父节点了");
                }



            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void removeTobTab(int pos, String code) {
        index = pos-1;

        int childCount = mDepartMentPathLL.getChildCount();


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
        orgChildNode = YealinkApi.instance().getOrgChildNode(false, code);
        for (int k=0;k<orgChildNode.size();k++){
            OrgNode orgchildNodeitem = orgChildNode.get(k);
            int type = orgchildNodeitem.getType();



            if(type == 1){
                String name = orgchildNodeitem.getName();
                boolean b = UIUtils.removeContactVirtual(name);
                if(!b){
                    toporgChildNode.add(orgchildNodeitem);
                }
//                            toporgChildNode.add(orgchildNodeitem);
            }else if(type == 2){
                String name = orgchildNodeitem.getName();
                boolean b = UIUtils.removeContactVirtual(name);
                if(!b){
                    bottomorgChildNode.add(orgchildNodeitem);
                }
//                            bottomorgChildNode.add(orgchildNodeitem);
            }
        }
        if(toporgChildNode.size()>0){
            view_1.setVisibility(View.VISIBLE);
        }else{
            view_1.setVisibility(View.GONE);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if(requestCode == 1){
                if(resultCode == 2){
                    Bundle bundle = data.getExtras();
//                    SerializableMap serializableMap = (SerializableMap) bundle.get("datamap");
//                    Map<String, MyNodeBean> serializableMapMap = serializableMap.getMap();
                    List<MyNodeBean> deletenodeBeens = (List<MyNodeBean>) bundle.getSerializable("deletelist");
//                    if(serializableMapMap != null){
//                        maps = serializableMapMap;
//                    }
                    for (int i=0;i<deletenodeBeens.size();i++){
                        MyNodeBean myNodeBean = deletenodeBeens.get(i);
                        String serialNumber = myNodeBean.getSerialNumber();
                        if(!maps.containsKey(serialNumber)){


                        }else{
                            for (int p=0;p<meetnowcontact.size();p++){
                                Contact contact = meetnowcontact.get(p);
                                String serialNumber1 = contact.getSerialNumber();
                                if(TextUtils.equals(serialNumber1,serialNumber)){
                                    meetnowcontact.remove(contact);
                                    break;
                                }
                            }
                            maps.remove(serialNumber);
                        }

                    }


                }
                if(myLvAdapter1 != null){
                    myLvAdapter1.notifyDataSetChanged();
                }

                tv_number.setText("已选成员:"+meetnowcontact.size()+">");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void initTab(String name, final String nodeId){
        isallchecknodeid = nodeId;
        Boolean aBoolean = allcheck.get(isallchecknodeid);
        if(aBoolean ==null){
            tv_all.setText("全选");
        }else{
            if(aBoolean){
                tv_all.setText("反选");
            }else{
                tv_all.setText("全选");
            }
        }
        isallclick.put(nodeId,false);

        index++;
        View inflate1 = View.inflate(MyDepartMentActivity3.this, R.layout.item_top_link, null);
        final TextView tv_link = (TextView)inflate1.findViewById(R.id.tv_link);
        tv_link.setText(name+" >");
        tv_link.setTag(index);
        views.add(index,inflate1);
        mDepartMentPathLL.addView(inflate1);
        clickItemCodes.put(mDepartMentPathLL.getChildCount()+"",nodeId);

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
//                    Log.e("aaaa",mDepartMentPathLL.getChildCount()+"索引："+tv_link.getTag());
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
                    orgChildNode = YealinkApi.instance().getOrgChildNode(false, nodeId);
                    for (int k=0;k<orgChildNode.size();k++){
                        OrgNode orgchildNodeitem = orgChildNode.get(k);
                        int type = orgchildNodeitem.getType();



                        if(type == 1){
                            String name = orgchildNodeitem.getName();
                            boolean b = UIUtils.removeContactVirtual(name);
                            if(!b){
                                toporgChildNode.add(orgchildNodeitem);
                            }
//                            toporgChildNode.add(orgchildNodeitem);
                        }else if(type == 2){
                            String name = orgchildNodeitem.getName();
                            boolean b = UIUtils.removeContactVirtual(name);
                            if(!b){
                                bottomorgChildNode.add(orgchildNodeitem);
                            }
//                            bottomorgChildNode.add(orgchildNodeitem);
                        }
                    }
                    if(toporgChildNode.size()>0){
                        view_1.setVisibility(View.VISIBLE);
                    }else{
                        view_1.setVisibility(View.GONE);
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
                convertView = LayoutInflater.from(MyDepartMentActivity3.this).inflate(R.layout.item_lv_childlj,null);
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
                convertView = LayoutInflater.from(MyDepartMentActivity3.this).inflate(R.layout.item_lv_child1lj,null);
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
//            Boolean aBoolean = allcheck.get(isallchecknodeid);
//            if(aBoolean != null){
//                if(aBoolean){
//                    Log.e("aaa","全选："+serialNumber);
//                    MyNodeBean nodeBean = new MyNodeBean();
//
//                    nodeBean.setSerialNumber(serialNumber);
//                    nodeBean.setName(name);
//                    nodeBean.setNodeId(nodeId);
//                    nodeBean.setCheck(true);
//
//                    if(!maps.containsKey(serialNumber)){
//                        maps.put(serialNumber,nodeBean);
//                    }
//
//
//                    if(!meetnowcontact.contains(orgNode)){
//                        meetnowcontact.add(orgNode);
//                        tv_number.setText("已选成员:"+meetnowcontact.size()+">");
//                    }
//
//                    groupViewHolder.cb_agree.setImageResource(R.drawable.cb_checked);
//
//
//                }else{
//                    Log.e("aaa","fan选："+serialNumber);
//                    MyNodeBean nodeBean = new MyNodeBean();
//
//                    nodeBean.setSerialNumber(serialNumber);
//                    nodeBean.setName(name);
//                    nodeBean.setNodeId(nodeId);
//                    nodeBean.setCheck(false);
//                    maps.remove(serialNumber);
//                    for (int p=0;p<meetnowcontact.size();p++){
//                        Contact contact = meetnowcontact.get(p);
//                        String serialNumber2 = contact.getSerialNumber();
//                        if(TextUtils.equals(serialNumber2,serialNumber)){
//                            meetnowcontact.remove(contact);
//                            tv_number.setText("已选成员:"+meetnowcontact.size()+">");
//                            break;
//                        }
//                    }
//                    groupViewHolder.cb_agree.setImageResource(R.drawable.cb_normal);
//                }
//            }
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
