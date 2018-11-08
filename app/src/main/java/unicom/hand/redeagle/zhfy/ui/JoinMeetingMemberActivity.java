package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.adapter.EditMemberAdapter;
import unicom.hand.redeagle.zhfy.view.MyGridView;

public class JoinMeetingMemberActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_meeting_member);
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        MyGridView grid_top = (MyGridView)findViewById(R.id.grid_top);

        grid_top.setAdapter(new EditMemberAdapter(JoinMeetingMemberActivity.this));





    }







}
