package unicom.hand.redeagle.zhfy.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yealink.sdk.YealinkApi;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.adapter.PopListAdapter;
import unicom.hand.redeagle.zhfy.bean.MyCityBean2;
import unicom.hand.redeagle.zhfy.bean.MyNodeBean;
import unicom.hand.redeagle.zhfy.utils.UIUtils;


public class ContactDetailActivity extends AppCompatActivity {
    private LinearLayout ll_video;
    private MyNodeBean orgNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        orgNode = (MyNodeBean)getIntent().getSerializableExtra("orgNode");
        final MyCityBean2 bean = new MyCityBean2();
        TextView tv_number = (TextView)findViewById(R.id.tv_number);
        TextView tv_name = (TextView)findViewById(R.id.tv_name);
         ll_video = (LinearLayout)findViewById(R.id.ll_video);
        LinearLayout ll_audio = (LinearLayout)findViewById(R.id.ll_audio);
        LinearLayout ll_dial = (LinearLayout)findViewById(R.id.ll_dial);
        ll_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String calledNum = bean.getCalledNum();
                if(TextUtils.isEmpty(calledNum)){
                    Toast.makeText(ContactDetailActivity.this, "该站点暂无号码", Toast.LENGTH_SHORT).show();
                }else{
                    popWindow(ContactDetailActivity.this);
                }

            }
        });

        if(orgNode != null){
            String name = orgNode.getName();
            tv_name.setText(name);
            bean.setName(name);
           final String serialNumber = orgNode.getSerialNumber();
            bean.setCalledNum(serialNumber);
            if(TextUtils.isEmpty(serialNumber)){
                tv_number.setText("暂无");
            }else{

                tv_number.setText(serialNumber);
                ll_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        YealinkApi.instance().call(ContactDetailActivity.this,serialNumber);
                    }
                });

                ll_audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        YealinkApi.instance().call(ContactDetailActivity.this,serialNumber,false);
                    }
                });

            }

        }
       ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);

        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private String[] titles = {"音频呼叫","视频呼叫"};
    private void popWindow(Context context){
        View contentView1= LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pop_list, null, false);
        ListView list = (ListView)contentView1.findViewById(R.id.list);
        list.setAdapter(new PopListAdapter(context,titles));
        TextView tv_number = (TextView)contentView1.findViewById(R.id.tv_number);
        tv_number.setText(orgNode.getSerialNumber());
        TextView tv_close = (TextView)contentView1.findViewById(R.id.tv_close);
        final PopupWindow pop1=new PopupWindow(contentView1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop1.setOutsideTouchable(true);
        pop1.setTouchable(true);

        pop1.showAtLocation(ll_video, Gravity.BOTTOM,0,0);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pop1.dismiss();

                if(id == 0){
                    YealinkApi.instance().call(ContactDetailActivity.this,orgNode.getSerialNumber(),false);
                }else if(id == 1){
                    YealinkApi.instance().call(ContactDetailActivity.this,orgNode.getSerialNumber());

                }

            }
        });
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop1.dismiss();
            }
        });
    }

}
