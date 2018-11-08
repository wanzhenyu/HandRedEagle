package unicom.hand.redeagle.zhfy.ui;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.utils.StringUtils;

/**
 * @author Administrator
 */
public class IpActivity extends Activity {

    TextView commonTitleRight;
    EditText ip1;
    EditText ip2;
    ImageView commonTitleLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ip);
        ip1 = (EditText)findViewById(R.id.ip1) ;
        ip2 = (EditText)findViewById(R.id.ip2) ;
        commonTitleRight = (TextView) findViewById(R.id.common_title_right) ;
        ip1.setText( AppApplication.preferenceProvider.getIp());
        ip2.setText( AppApplication.preferenceProvider.getIp2());
        commonTitleLeft = (ImageView)findViewById(R.id.common_title_left);

        commonTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        commonTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isNullOrEmpty(ip1.getText().toString())
                        && !StringUtils.isNullOrEmpty(ip2.getText().toString())) {
                    AppApplication.preferenceProvider.setIp(ip1.getText().toString());
                    AppApplication.preferenceProvider.setIp2(ip2.getText().toString());
                    finish();
                }else {
                    Toast.makeText(IpActivity.this, "请输入完整", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}
