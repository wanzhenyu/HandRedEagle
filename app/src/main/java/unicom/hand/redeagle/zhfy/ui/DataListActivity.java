package unicom.hand.redeagle.zhfy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.bean.MyNodeBean;
import unicom.hand.redeagle.zhfy.bean.SerializableMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class DataListActivity extends AppCompatActivity {

//    private List<Contact> contacts;
    private EditText et_text;
    private MySearchAdapter mySearchAdapter;
    private List<MyNodeBean> nodeBeens;
    private SerializableMap serializableMap;
    private Map<String, MyNodeBean> map;
    private List<MyNodeBean> deletenodeBeens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datalist);

        et_text = (EditText)findViewById(R.id.et_text);
        nodeBeens = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
         serializableMap = (SerializableMap) bundle.get("map");
        deletenodeBeens = new ArrayList<>();
        map = serializableMap.getMap();
        for (int i = 0; i< map.size(); i++){

        }
        Iterator<Map.Entry<String, MyNodeBean>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            MyNodeBean value = iterator.next().getValue();
            nodeBeens.add(value);
        }
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                SerializableMap serializabmap = new SerializableMap();
                serializabmap.setMap(map);
                Bundle bundle=new Bundle();
                bundle.putSerializable("datamap", serializabmap);
                bundle.putSerializable("deletelist",(Serializable)deletenodeBeens);
                intent.putExtras(bundle);
                Log.e("aaa",deletenodeBeens.size()+"列表数量:"+map.size());
                setResult(2,intent);
                finish();
            }
        });
//        Log.e("aaa","列表数量:"+serializableMap.getMap().size());

        ListView mDataLV = (ListView) findViewById(R.id.department_datalist);

        mySearchAdapter = new MySearchAdapter();
        mDataLV.setAdapter(mySearchAdapter);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent();
            SerializableMap serializabmap = new SerializableMap();
            serializabmap.setMap(map);
            Bundle bundle=new Bundle();
            bundle.putSerializable("datamap", serializabmap);
            bundle.putSerializable("deletelist",(Serializable)deletenodeBeens);
            intent.putExtras(bundle);
//            Log.e("aaa",deletenodeBeens.size()+"列表数量:"+map.size());
            setResult(2,intent);




            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    class MySearchAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return nodeBeens.size();
        }

        @Override
        public Object getItem(int i) {
            return nodeBeens.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(DataListActivity.this).inflate(R.layout.item_datalist,null);
                groupViewHolder = new ViewHolder();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
                groupViewHolder.iv_substract = (ImageView) convertView.findViewById(R.id.iv_substract);
                groupViewHolder.label_expand_code = (TextView) convertView.findViewById(R.id.label_expand_code);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (ViewHolder) convertView.getTag();
            }
            final MyNodeBean orgNode = nodeBeens.get(i);
            String name = orgNode.getName();
           final String serialNumber = orgNode.getSerialNumber();
            groupViewHolder.tvTitle.setText(name);
            groupViewHolder.label_expand_code.setText(serialNumber);
            groupViewHolder.iv_substract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    map.remove(serialNumber);
                    nodeBeens.remove(orgNode);
                    deletenodeBeens.add(orgNode);
                    notifyDataSetChanged();


                }
            });

            return convertView;
        }
    }

    class ViewHolder{
        TextView tvTitle;
        TextView label_expand_code;
        ImageView iv_substract;
    }












}
