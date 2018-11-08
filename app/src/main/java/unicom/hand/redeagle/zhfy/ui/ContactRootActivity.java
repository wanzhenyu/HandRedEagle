package unicom.hand.redeagle.zhfy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import unicom.hand.redeagle.R;
import com.yealink.common.data.Contact;
import com.yealink.common.data.OrgNode;
import com.yealink.sdk.YealinkApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ContactRootActivity extends AppCompatActivity {
    private OrgNode orgRoots;
    private ExpandableListView ea_lv;
    private TextView tv_number;
    private int totalnum = 0;
    private int grouptotalnum = -1;
    private boolean isgroupcheck = false;
    private Map<Integer,Boolean> map=new HashMap<>();
    private LjMyAdapter ljMyAdapter;
    private boolean ischildallcheck = false;
    private ArrayList<Contact> meetnowcontact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_root);
        meetnowcontact = new ArrayList<>();
        TextView filter_edit = (TextView)findViewById(R.id.filter_edit);
        filter_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactRootActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ea_lv = (ExpandableListView)findViewById(R.id.ea_lv);
        orgRoots = YealinkApi.instance().getOrgRoot(false);
        if(orgRoots != null){
            final List<OrgNode> children = orgRoots.getChildren();

            ljMyAdapter = new LjMyAdapter(orgRoots, children);
            ea_lv.setAdapter(ljMyAdapter);

            ea_lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                    OrgNode orgNode = children.get(childPosition);
                    Boolean aBoolean = map.get(childPosition);
                    Intent intent = new Intent(ContactRootActivity.this,MyDepartMentActivity2.class);
                    String nodeId = orgNode.getNodeId();
                    String parentnodeId1 = orgRoots.getNodeId();
                    intent.putExtra("parentorgId",""+parentnodeId1);
                    intent.putExtra("parenttitle",""+orgRoots.getName());
                    intent.putExtra("totalnum",totalnum);
                    intent.putExtra("meetnowcontact",meetnowcontact);
                    intent.putExtra("orgId",""+nodeId);
                    intent.putExtra("ischeck",aBoolean);
                    intent.putExtra("title",""+orgNode.getName());
                    startActivity(intent);
                    return true;
                }
            });
        }else{
            Toast.makeText(ContactRootActivity.this,"联系人正在加载，请稍后再试", Toast.LENGTH_SHORT).show();
            finish();
        }





        ea_lv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        // 只展开一个
//                        for (int i = 0, count = ea_lv.getCount(); i < count; i++) {
//                            if (groupPosition != i) {
//                                ea_lv.collapseGroup(i);
//                            }
//                        }






                    }
                });


        tv_number = (TextView)findViewById(R.id.tv_number);
        LinearLayout ll_lj = (LinearLayout)findViewById(R.id.ll_lj);
        ll_lj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YealinkApi.instance().meetNow(ContactRootActivity.this,meetnowcontact);


            }
        });




    }



    public class LjMyAdapter extends BaseExpandableListAdapter {
        OrgNode orgRoot;
        List<OrgNode> children;
        public LjMyAdapter(OrgNode orgRoot,List<OrgNode> children){
            this.orgRoot = orgRoot;
            this.children = children;
        }
        @Override
        public int getGroupCount() {
            return 1;
        }

        @Override
        public int getChildrenCount(int i) {
            return children.size();
        }

        @Override
        public Object getGroup(int i) {
            return null;
        }

        @Override
        public Object getChild(int i, int i1) {
            return children.get(i);
        }

        @Override
        public long getGroupId(int i) {
            return 0;
        }

        @Override
        public long getChildId(int i, int i1) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
           final GroupViewHolderlj groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ContactRootActivity.this).inflate(R.layout.item_expand_group_lj,null);
                groupViewHolder = new GroupViewHolderlj();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
                groupViewHolder.cb_agree = (CheckBox) convertView.findViewById(R.id.cb_agree);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (GroupViewHolderlj) convertView.getTag();
            }
//            groupViewHolder.cb_agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    if(b){
//                        isgroupcheck = true;
//                        totalnum += orgRoot.getData().getCount();
//                    }else{
//                        isgroupcheck = false;
//                        totalnum -= orgRoot.getData().getCount();
//                    }
//                    tv_number.setText("已选成员:"+totalnum+">");
//                }
//            });


            groupViewHolder.cb_agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ischildallcheck = false;
                    Toast.makeText(ContactRootActivity.this, "点击", Toast.LENGTH_SHORT).show();
                    if(groupViewHolder.cb_agree.isChecked()){
                        isgroupcheck = true;
                        groupViewHolder.cb_agree.setChecked(true);
                        totalnum += orgRoot.getData().getCount();
                    }else{
                        isgroupcheck = false;
                        totalnum -= orgRoot.getData().getCount();
                        groupViewHolder.cb_agree.setChecked(false);
                    }
                    tv_number.setText("已选成员:"+totalnum+">");
                    if(ljMyAdapter != null){
                        ljMyAdapter.notifyDataSetChanged();
                    }

                }
            });
            if(isgroupcheck){
                groupViewHolder.cb_agree.setChecked(true);
            }else{
                groupViewHolder.cb_agree.setChecked(false);
            }





            String name = orgRoot.getName();
            int size = orgRoot.getData().getCount();
            grouptotalnum = size;
            groupViewHolder.tvTitle.setText(name+"("+size+")");
            return convertView;
        }

        @Override
        public View getChildView(int i, final int i1, boolean b, View convertView, ViewGroup viewGroup) {
           final ChildViewHolderlj groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ContactRootActivity.this).inflate(R.layout.item_expand_child_lj,null);
                groupViewHolder = new ChildViewHolderlj();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
                groupViewHolder.cb_agree = (CheckBox) convertView.findViewById(R.id.cb_agree);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (ChildViewHolderlj) convertView.getTag();
            }
            final OrgNode orgNode = children.get(i1);
//            groupViewHolder.cb_agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    Log.e("ddd","字列表："+b);
//                    if(b){
//                        totalnum += orgNode.getData().getCount();
//                        map.put(i1,true);
//                    }else{
//                        map.put(i1,false);
//                        totalnum -= orgNode.getData().getCount();
//                    }
//                    tv_number.setText("已选成员:"+totalnum+">");
//                }
//            });



            groupViewHolder.cb_agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ischildallcheck = true;
//                    Log.e("ddd","字1列表："+groupViewHolder.cb_agree.isChecked());
                    if(groupViewHolder.cb_agree.isChecked()){
                        totalnum += orgNode.getData().getCount();
                        map.put(i1,true);
                        if(!meetnowcontact.contains(orgNode)){
                            meetnowcontact.add(orgNode);
                        }
                    }else{
                        map.put(i1,false);
                        totalnum -= orgNode.getData().getCount();
                        if(meetnowcontact.contains(orgNode)){
                            meetnowcontact.remove(orgNode);
                        }
                    }
                    Log.e("ddd","会议成员数量："+meetnowcontact.size());
                    tv_number.setText("已选成员:"+totalnum+">");
                    if(totalnum == 0){
                        isgroupcheck = false;
                        if(ljMyAdapter != null){
                            ljMyAdapter.notifyDataSetChanged();
                        }

                    }else{
                        if(grouptotalnum == totalnum){
                            isgroupcheck = true;
                            if(ljMyAdapter != null){
                                ljMyAdapter.notifyDataSetChanged();
                            }
                        }
                    }


                }
            });









//            Log.e("ddd","刷新列表列表："+isgroupcheck);
            if(!ischildallcheck){
                if(isgroupcheck){
                    map.put(i1,true);
                    if(!meetnowcontact.contains(orgNode)){
                        Log.e("ddd",orgNode.getName()+"加入成员数量："+meetnowcontact.size());
                        meetnowcontact.add(orgNode);
                    }

//                groupViewHolder.cb_agree.setChecked(true);
                }else{
                    map.put(i1,false);
                    if(meetnowcontact.contains(orgNode)){
                        Log.e("ddd",orgNode.getName()+"溢出成员数量："+meetnowcontact.size());
                        meetnowcontact.remove(orgNode);
                    }

//                groupViewHolder.cb_agree.setChecked(false);
                }
            }

            Boolean aBoolean = map.get(i1);
            if(aBoolean){
                groupViewHolder.cb_agree.setChecked(true);
            }else{
                groupViewHolder.cb_agree.setChecked(false);
            }
            String name = orgNode.getName();
            int size = orgNode.getData().getCount();
            groupViewHolder.tvTitle.setText(name+"("+size+")");

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }




        class GroupViewHolderlj {
            TextView tvTitle;
            CheckBox cb_agree;
        }
        class ChildViewHolderlj{
            TextView tvTitle;
            CheckBox cb_agree;
        }








    }











}
