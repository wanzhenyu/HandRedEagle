package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yealink.common.data.CallLogGroup;
import com.yealink.common.data.Calllog;

import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.BaseActivity;
import unicom.hand.redeagle.zhfy.adapter.CallLogDetailAdapter;
import unicom.hand.redeagle.zhfy.bean.CallLogGroupBean;
import unicom.hand.redeagle.zhfy.utils.UIUtils;
import unicom.hand.redeagle.zhfy.view.MyListView;


public class CallLogDetailActivity extends BaseActivity {

    @Override
    protected void handleMsg(Message msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log_detail);
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        CallLogGroupBean callbean = (CallLogGroupBean)getIntent().getSerializableExtra("item");
        CallLogGroup callloggroup = (CallLogGroup)getIntent().getParcelableExtra("item");
        TextView tv_name = (TextView)findViewById(R.id.tv_name);
        String cloudDisplayName = callloggroup.getLastCalllog().getCloudDisplayName();
        tv_name.setText(UIUtils.getHyrcTtitle(cloudDisplayName));
        TextView tv_num = (TextView)findViewById(R.id.tv_num);
//        int accountId = callloggroup.getM;
        int size = callloggroup.getNumberList().size();
        tv_num.setText("号码数量:"+size);

        TextView tv_code = (TextView)findViewById(R.id.tv_code);
        String number = callloggroup.getNumber();
        tv_code.setText("号码:"+number);
        
        MyListView lv = (MyListView)findViewById(R.id.lv);
        List<Calllog> callLogList = callloggroup.getCallLogList();
        if(callLogList != null && callLogList.size()>0){
            lv.setAdapter(new CallLogDetailAdapter(callLogList));
        }


    }






}
