package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.yealink.common.data.SipProfile;
import com.yealink.sdk.YealinkApi;


import java.util.ArrayList;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.Common;
import unicom.hand.redeagle.zhfy.bean.MyCityBean2;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.GsonUtils;
import unicom.hand.redeagle.zhfy.view.MyListView;

import static android.R.id.list;

public class SelectCityActivity extends Activity {
    private ListView mDatamember;
    DbUtils db;
    private MyLvAdapter1 myLvAdapter1;
    private List<MyCityBean2> bottomorgChildNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mDatamember = (ListView) findViewById(R.id.department_member);
        bottomorgChildNode = new ArrayList<>();
        db = DbUtils.create(SelectCityActivity.this, Common.DB_NAME);
        getData("0");
        if(myLvAdapter1 == null){
            myLvAdapter1 = new MyLvAdapter1();
            mDatamember.setAdapter(myLvAdapter1);
        }else{
            myLvAdapter1.notifyDataSetChanged();
        }

        mDatamember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyCityBean2 myCityBean2 = (MyCityBean2)adapterView.getAdapter().getItem(i);
;
                String code = myCityBean2.getAreaCode();
                Log.e("aaa","地区areacode;"+code);
                String name = myCityBean2.getName();
                AppApplication.preferenceProvider.setCityCode(code);
                AppApplication.preferenceProvider.setXzCityName(name);
                myLvAdapter1.notifyDataSetChanged();
                finish();


            }
        });





    }
    public void getData(String parentCode){
            try {
                bottomorgChildNode = db.findAll(Selector
                        .from(MyCityBean2.class)
                        .where("parentCode", "=", parentCode)
                        .and(WhereBuilder.b("category", "=",
                                "face2face")).and(WhereBuilder.b("valid", "=",
                                1))
                        .orderBy("sort", false));
//                Log.e("aaa",",地区列表："+ GsonUtils.getJson(bottomorgChildNode));
//                .and(WhereBuilder.b("name", "!=",
//                        "平顶山市"))
            } catch (DbException e) {
                e.printStackTrace();
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
                convertView = LayoutInflater.from(SelectCityActivity.this).inflate(R.layout.item_select_city,null);
                groupViewHolder = new ViewHolder1();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
                groupViewHolder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (ViewHolder1) convertView.getTag();
            }
            final MyCityBean2 bean = bottomorgChildNode.get(i);
            String name = bean.getName();
            groupViewHolder.tvTitle.setText(name);

            String cityCode = AppApplication.preferenceProvider.getCityCode();
            String code = bean.getCode();
            if(TextUtils.equals(code,cityCode)){
                groupViewHolder.iv_img.setVisibility(View.VISIBLE);
            }else{
                groupViewHolder.iv_img.setVisibility(View.GONE);
            }
            return convertView;
        }
    }


    class ViewHolder1{
        TextView tvTitle;
        ImageView iv_img;
    }



}
