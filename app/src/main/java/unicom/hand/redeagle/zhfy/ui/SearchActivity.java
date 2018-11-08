package unicom.hand.redeagle.zhfy.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import unicom.hand.redeagle.R;
import com.yealink.common.data.Contact;
import com.yealink.common.data.OrgNode;
import com.yealink.sdk.YealinkApi;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {

    private List<OrgNode> contacts;
    private EditText et_text;
    private MySearchAdapter mySearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

        et_text = (EditText)findViewById(R.id.et_text);
//        contacts = YealinkApi.instance().searchCloudContact("会议室", false);

//        for (int i = 0; i< contacts.size(); i++){
//            Contact contact = contacts.get(i);
//            Log.e("aaa",contact.getName());
//
//        }
        contacts = new ArrayList<>();
        et_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = et_text.getText().toString().trim();
                    if(TextUtils.isEmpty(content)){
                        Toast.makeText(SearchActivity.this, "请先输入搜索内容", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    contacts = YealinkApi.instance().searchOrgContact(content,false);
//                    contacts = YealinkApi.instance().searchCloudContact(content, false);
                    mySearchAdapter.notifyDataSetChanged();
//                    Toast.makeText(SearchActivity.this, "开始搜索", Toast.LENGTH_SHORT).show();

                }

                return false;

            }

        });

        TextView common_title_right = (TextView)findViewById(R.id.common_title_right);
        common_title_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ListView mDataLV = (ListView) findViewById(R.id.department_datalist);

        mySearchAdapter = new MySearchAdapter();
        mDataLV.setAdapter(mySearchAdapter);


        mDataLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Contact contact = (Contact)adapterView.getAdapter().getItem(i);


                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                // 设置参数
                builder.setTitle("提示")
                        .setMessage("确定要拨打电话？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {// 积极

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                String serialNumber = contact.getSerialNumber();
                                if(!TextUtils.isEmpty(serialNumber)){
                                    YealinkApi.instance().call(SearchActivity.this,serialNumber);
                                }else{
                                    Toast.makeText(SearchActivity.this, "暂无号码!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();


            }
        });





//        List<Contact> contacts1 = YealinkApi.instance().searchOrgContact("会议室", false);
//
//        for (int i=0;i<contacts1.size();i++){
//            Contact contact = contacts.get(i);
//            Log.e("aaa",contact.getName());
//
//        }
    }



    class MySearchAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return contacts.size();
        }

        @Override
        public Object getItem(int i) {
            return contacts.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_search,null);
                groupViewHolder = new ViewHolder();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
                groupViewHolder.label_expand_code = (TextView) convertView.findViewById(R.id.label_expand_code);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (ViewHolder) convertView.getTag();
            }
            Contact orgNode = contacts.get(i);
            String name = orgNode.getName();

            groupViewHolder.tvTitle.setText(name);
            groupViewHolder.label_expand_code.setText(orgNode.getSerialNumber());
            return convertView;
        }
    }

    class ViewHolder{
        TextView tvTitle;
        TextView label_expand_code;
    }












}
