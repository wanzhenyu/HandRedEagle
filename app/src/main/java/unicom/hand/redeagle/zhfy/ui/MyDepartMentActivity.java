package unicom.hand.redeagle.zhfy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.bean.MyCityBean2;
import unicom.hand.redeagle.zhfy.bean.MyNodeBean;
import unicom.hand.redeagle.zhfy.utils.UIUtils;
import unicom.hand.redeagle.zhfy.view.CustomHorizontalScrollView;
import unicom.hand.redeagle.zhfy.view.MyListView;

import com.yealink.common.data.OrgNode;
import com.yealink.sdk.YealinkApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyDepartMentActivity extends AppCompatActivity {
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
    private View view_1;
    private Map<String,String> clickItemCodes;
    private TextView tv_close;
    //    private ImageView mTopLeftIv, mTopRightIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_depart_ment);
        views = new ArrayList<>();
        mDepartMentPathLL = (LinearLayout) findViewById(R.id.department_hscrollview_llcover);
        mDataLV = (MyListView) findViewById(R.id.department_datalist);
        mDatamember = (MyListView) findViewById(R.id.department_member);

        mCHSView = (CustomHorizontalScrollView) findViewById(R.id.department_hscrollview);
//        mTopLeftIv = (ImageView) findViewById(R.id.department_topview_leftarrow);
//        mTopRightIv = (ImageView) findViewById(R.id.department_topview_rightarrow);
        String orgId = getIntent().getStringExtra("orgId");
        String title = getIntent().getStringExtra("title");
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

        view_1 = findViewById(R.id.view_1);
        tv_close = (TextView)findViewById(R.id.tv_close);
        orgChildNode = YealinkApi.instance().getOrgChildNode(false, orgId);
        for (int i=0;i<orgChildNode.size();i++){
            OrgNode orgNode = orgChildNode.get(i);
            int type = orgNode.getType();
//            Log.e("aaa","节点类型："+type);
            if(type == 1){
                toporgChildNode.add(orgNode);
            }else if(type == 2){
                bottomorgChildNode.add(orgNode);
            }
        }
        common_title_right = (TextView)findViewById(R.id.common_title_right);
        common_title_right.setText("联系人");


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

//        View inflate = View.inflate(MyDepartMentActivity.this, R.layout.item_top_link, null);
//        tv_link = (TextView)inflate.findViewById(R.id.tv_link);
//        tv_link.setText(title);
//        mDepartMentPathLL.addView(inflate);
        clickItemCodes = new HashMap<>();
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
//                common_title_right.setText(name);
                String nodeId = orgNode.getNodeId();
                Log.e("aaa","AdapterView节点id："+nodeId);
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

                    Log.e("aaa","已经到父节点了");
                }



            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void removeTobTab(int pos,String nodeId) {
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
//                    Log.e("aaa","tv_link节点id："+nodeId);
        orgChildNode = YealinkApi.instance().getOrgChildNode(false, nodeId);
        for (int k=0;k<orgChildNode.size();k++){
            OrgNode orgchildNodeitem = orgChildNode.get(k);
            int type = orgchildNodeitem.getType();

            String name1 = orgchildNodeitem.getName();

            if(type == 1){
                boolean b = UIUtils.removeContactVirtual(name1);
                if(!b){
                    toporgChildNode.add(orgchildNodeitem);
                }

            }else if(type == 2){
                boolean b = UIUtils.removeContactVirtual(name1);
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

    public void initTab(String name, final String nodeId){
        index++;
        View inflate1 = View.inflate(MyDepartMentActivity.this, R.layout.item_top_link, null);
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
//                    Log.e("aaa","tv_link节点id："+nodeId);
                    orgChildNode = YealinkApi.instance().getOrgChildNode(false, nodeId);
                    for (int k=0;k<orgChildNode.size();k++){
                        OrgNode orgchildNodeitem = orgChildNode.get(k);
                        int type = orgchildNodeitem.getType();

                        String name1 = orgchildNodeitem.getName();

                        if(type == 1){
                            boolean b = UIUtils.removeContactVirtual(name1);
                            if(!b){
                                toporgChildNode.add(orgchildNodeitem);
                            }

                        }else if(type == 2){
                            boolean b = UIUtils.removeContactVirtual(name1);
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



        mDatamember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               OrgNode orgNode = (OrgNode) adapterView.getAdapter().getItem(i);
                MyNodeBean myNodeBean = new MyNodeBean();
                String name1 = orgNode.getName();
                myNodeBean.setName(name1);
                String serialNumber = orgNode.getSerialNumber();
                myNodeBean.setSerialNumber(serialNumber);
                Intent intent = new Intent(MyDepartMentActivity.this,ContactDetailActivity.class);
                intent.putExtra("orgNode",myNodeBean);
                startActivity(intent);

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
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MyDepartMentActivity.this).inflate(R.layout.item_lv_child,null);
                groupViewHolder = new ViewHolder();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (ViewHolder) convertView.getTag();
            }
            OrgNode orgNode = toporgChildNode.get(i);
            String name = orgNode.getName();
            int size = orgNode.getData().getCount();
            groupViewHolder.tvTitle.setText(name+"("+size+")");

            return convertView;
        }
    }

    class ViewHolder{
        TextView tvTitle;
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
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder1 groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MyDepartMentActivity.this).inflate(R.layout.item_lv_child1,null);
                groupViewHolder = new ViewHolder1();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
                groupViewHolder.label_expand_code = (TextView) convertView.findViewById(R.id.label_expand_code);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (ViewHolder1) convertView.getTag();
            }
            OrgNode orgNode = bottomorgChildNode.get(i);
            String name = orgNode.getName();
            int size = orgNode.getData().getCount();
            groupViewHolder.tvTitle.setText(name);
            groupViewHolder.label_expand_code.setText(orgNode.getSerialNumber());
            return convertView;
        }
    }

    class ViewHolder1{
        TextView tvTitle;
        TextView label_expand_code;
    }



}
