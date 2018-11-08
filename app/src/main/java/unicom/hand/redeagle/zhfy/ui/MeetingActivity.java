package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import unicom.hand.redeagle.R;

public class MeetingActivity extends Activity {

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        initView();
    }

    private void initView() {

        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        title = getIntent().getStringExtra("title");
        tv_title.setText(title);
        LinearLayout ll_look = (LinearLayout)findViewById(R.id.ll_look);
        ll_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String type = "";
                if(TextUtils.equals(title,"支部党员大会")){
                    type = "1";
                }else if(TextUtils.equals(title,"支部委员会")){
                    type = "2";
                }else if(TextUtils.equals(title,"党小组会")){
                    type = "3";
                }else if(TextUtils.equals(title,"党课")){
                    type = "4";
                }
                Intent intent = new Intent(MeetingActivity.this,MeetingRecoderActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("edittype","edit");
                startActivity(intent);




//                Intent intent = new Intent(MeetingActivity.this,MeetingRecoderActivity.class);
//                intent.putExtra("title", title);
//                startActivity(intent);
            }
        });
        LinearLayout ll_hyrc = (LinearLayout)findViewById(R.id.ll_hyrc);
        ll_hyrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "";
                if(TextUtils.equals(title,"支部党员大会")){
                    type = "1";
                }else if(TextUtils.equals(title,"支部委员会")){
                    type = "2";
                }else if(TextUtils.equals(title,"党小组会")){
                    type = "3";
                }else if(TextUtils.equals(title,"党课")){
                    type = "4";
                }
                Intent intent = new Intent(MeetingActivity.this,HyrcActivity.class);
                intent.putExtra("state",1);
                intent.putExtra("title","会议日程");
                intent.putExtra("type",type);
                startActivity(intent);


//                Intent intent = new Intent(MeetingActivity.this,HyrcActivity.class);
//                intent.putExtra("title", title);
//                startActivity(intent);
            }
        });
        LinearLayout ll_join = (LinearLayout)findViewById(R.id.ll_join);
        ll_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeetingActivity.this,JoinMeetActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });





    }
}
