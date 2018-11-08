package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import unicom.hand.redeagle.R;

public class TxtMainActivity extends Activity {

    private RelativeLayout rl_hf;
    private RelativeLayout rl_zm;
    private RelativeLayout rl_hylc;
    private String intentconfId;
    private String intentconfEntity;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txt_main);
        ImageView iv_left = (ImageView)findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rl_hf = (RelativeLayout)findViewById(R.id.rl_hf);
        rl_zm = (RelativeLayout)findViewById(R.id.rl_zm);
        rl_hylc = (RelativeLayout)findViewById(R.id.rl_hylc);
        intentconfId = getIntent().getStringExtra("confId");
        intentconfEntity = getIntent().getStringExtra("confEntity");
        rl_hf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TxtMainActivity.this,BannerActivity.class);
                intent.putExtra("confId",intentconfId);
                intent.putExtra("confEntity",intentconfEntity);
                startActivity(intent);

            }
        });
        rl_zm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TxtMainActivity.this,SubTitleActivity.class);
                intent.putExtra("confId",intentconfId);
                intent.putExtra("confEntity",intentconfEntity);
                startActivity(intent);
            }
        });
        type = getIntent().getStringExtra("type");
        rl_hylc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TxtMainActivity.this,HylcActivity.class);
                intent.putExtra("confId",intentconfId);
                intent.putExtra("confEntity",intentconfEntity);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });



    }
}
