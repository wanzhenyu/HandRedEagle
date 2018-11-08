package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.Common;
import unicom.hand.redeagle.zhfy.bean.MyCityBean2;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.view.MyListView;

public class ShiZhiOrXianJiActivity extends Activity {

    private String xzCityName;
    private String cityCode;
    List<MyCityBean2> list;


    private MyListView mDatamember;
    private List<MyCityBean2> bottomorgChildNode;
    private MyLvAdapter1 myLvAdapter1;
    private TextView tv_zj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shi_zhi_or_xian_ji);
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView tv_szdw = (TextView)findViewById(R.id.tv_szdw);
        TextView tv_xjdw = (TextView)findViewById(R.id.tv_xjdw);
        cityCode = AppApplication.preferenceProvider.getCityCode();
        xzCityName = AppApplication.preferenceProvider.getXzCityName();
        if(TextUtils.equals(cityCode,"401")){
            tv_szdw.setText("市直单位");
            tv_xjdw.setText("县级单位");
        }else{
            tv_xjdw.setText("乡村");
            tv_szdw.setText("县直单位");
        }
        tv_zj = (TextView)findViewById(R.id.tv_zj);

        DbUtils db = DbUtils.create(ShiZhiOrXianJiActivity.this, Common.DB_NAME);
        try {
             list = db.findAll(Selector
                    .from(MyCityBean2.class)
                    .where("parentCode", "=", cityCode)
                    .and(WhereBuilder.b("category", "=",
                            "face2face")).and(WhereBuilder.b("valid", "=",
                            1))
                    .orderBy("sort", false));
        } catch (DbException e) {
            e.printStackTrace();
        }
        LinearLayout ll_xj = (LinearLayout)findViewById(R.id.ll_xj);
        ll_xj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list != null && list.size()>0){
                    Intent intent = new Intent(ShiZhiOrXianJiActivity.this, XingZhengFuWuActivity.class);
                    MyCityBean2 myCityBean2 = list.get(0);
                    intent.putExtra("item",myCityBean2);
                    intent.putExtra("code",cityCode);
                    intent.putExtra("issz","0");
                    intent.putExtra("dw",xzCityName);
                    startActivity(intent);
                }else{
                    Toast.makeText(ShiZhiOrXianJiActivity.this, "暂时无数据", Toast.LENGTH_SHORT).show();
                }



            }
        });
        LinearLayout ll_sj = (LinearLayout)findViewById(R.id.ll_sj);
        ll_sj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShiZhiOrXianJiActivity.this, XingZhengFuWuActivity.class);
                if(list != null && list.size()>0){
                    MyCityBean2 myCityBean2 = list.get(0);
                    intent.putExtra("item",myCityBean2);
                    intent.putExtra("code",myCityBean2.getCode());
                    intent.putExtra("issz","1");
                    intent.putExtra("dw",myCityBean2.getName());
                    startActivity(intent);
                }else{
                    Toast.makeText(ShiZhiOrXianJiActivity.this, "暂时无数据", Toast.LENGTH_SHORT).show();
                }



            }
        });

        mDatamember = (MyListView) findViewById(R.id.department_member);
        bottomorgChildNode = new ArrayList<>();
        mDatamember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MyCityBean2 orgNode =  (MyCityBean2)adapterView.getAdapter().getItem(i);
                String calledNum = orgNode.getCalledNum();
                if(!TextUtils.isEmpty(calledNum)) {
                    Intent intent = new Intent(ShiZhiOrXianJiActivity.this, MdmContactDetailActivity.class);
                    intent.putExtra("orgNode", orgNode);
                    startActivity(intent);
                }else{
                    Toast.makeText(ShiZhiOrXianJiActivity.this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                }


            }
        });


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
//            bottomorgChildNode.addAll(tempdata);
            if(tempdata != null && tempdata.size()>0){
                for (int i=tempdata.size()-1;i<tempdata.size();i--){
                    MyCityBean2 myCityBean2 = tempdata.get(i);
                    bottomorgChildNode.add(myCityBean2);
                    if( i ==0){
                        break;
                    }
                }
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
                convertView = LayoutInflater.from(ShiZhiOrXianJiActivity.this).inflate(R.layout.item_lv_child1lj_xzfw,null);
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
            return convertView;
        }
        class ViewHolder1{
            TextView tvTitle;
            TextView label_expand_num;
            ImageView iv_flag;
            ImageView listvideo;
        }
    }







}
