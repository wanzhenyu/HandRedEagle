package unicom.hand.redeagle.zhfy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yealink.common.NativeInit;
import com.yealink.common.data.OrgNode;
import com.yealink.sdk.YealinkApi;

import java.util.ArrayList;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.ui.MyDepartMentActivity;
import unicom.hand.redeagle.zhfy.ui.SearchActivity;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements NativeInit.NativeInitListener {

    private OrgNode orgRoots;
    List<OrgNode> childrens;
    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView common_title_left = (ImageView)view.findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        TextView filter_edit = (TextView)view.findViewById(R.id.filter_edit);
        filter_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        try {
            NativeInit.registerListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        childrens = new ArrayList<>();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if(NativeInit.isInited()){
                        orgRoots = YealinkApi.instance().getOrgRoot(false);
                        ExpandableListView ea_lv = (ExpandableListView)view.findViewById(R.id.ea_lv);

                        if(orgRoots != null){
                            final List<OrgNode> children = orgRoots.getChildren();



                            if(children != null && children.size()>0){
                                for (int i=0;i<children.size();i++){
                                    OrgNode orgNode = children.get(i);
                                    String name = orgNode.getName();
                                    boolean b = UIUtils.removeContactVirtual(name);
                                    if(!b){
                                        childrens.add(orgNode);
                                    }
                                }
                                ea_lv.setAdapter(new MyAdapter(orgRoots,childrens));
                                ea_lv.expandGroup(0);
                            }

                            ea_lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                                @Override
                                public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                                    OrgNode orgNode = children.get(childPosition);
                                    Intent intent = new Intent(getActivity(),MyDepartMentActivity.class);
                                    String nodeId = orgNode.getNodeId();
                                    String parentnodeId1 = orgRoots.getNodeId();
                                    intent.putExtra("parentorgId",""+parentnodeId1);
                                    intent.putExtra("parenttitle",""+orgRoots.getName());
                                    Log.e("aaa","节点id："+nodeId);
                                    intent.putExtra("orgId",""+nodeId);
                                    intent.putExtra("title",""+orgNode.getName());
                                    startActivity(intent);
                                    return true;
                                }
                            });
                        }else{
                            Toast.makeText(UIUtils.getContext(),"通讯录正在加载，请稍后再试", Toast.LENGTH_SHORT).show();

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 3500);





    }

    @Override
    public void onInitFinish() {
        Log.e("aaa","初始化完成");
    }

    @Override
    public void onNativeError(int i) {
        Log.e("aaa","初始化失败"+i);
    }


    public class MyAdapter extends BaseExpandableListAdapter {
        OrgNode orgRoot;
        List<OrgNode> children;
        public MyAdapter(OrgNode orgRoot,List<OrgNode> children){
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
            GroupViewHolder groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_expand_group,null);
                groupViewHolder = new GroupViewHolder();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (GroupViewHolder) convertView.getTag();
            }

            String name = orgRoot.getName();
//            int size = orgRoot.getData().getCount();
            int size = 0;
            for (int j=0;j<childrens.size();j++){
                OrgNode orgNode = childrens.get(j);
                size+=orgNode.getData().getCount();
            }
            groupViewHolder.tvTitle.setText(name+"("+size+")");
            return convertView;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
            ChildViewHolder groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_expand_child,null);
                groupViewHolder = new MyAdapter.ChildViewHolder();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (ChildViewHolder) convertView.getTag();
            }
            OrgNode orgNodechild = children.get(i1);
            String name = orgNodechild.getName();
            int size = orgNodechild.getData().getCount();
            groupViewHolder.tvTitle.setText(name+"("+size+")");

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }




        class GroupViewHolder {
            TextView tvTitle;
        }
        class ChildViewHolder {
            TextView tvTitle;
        }








    }
}
