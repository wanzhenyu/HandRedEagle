package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.yealink.common.SettingsManager;
import com.yealink.common.data.ConferenceHistory;
import com.yealink.sdk.YealinkApi;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;

public class JoinMeetActivity extends Activity implements View.OnClickListener {
    private EditText mMeetIdET;
    private EditText mMeetPwdET;
    private EditText mMeetNameET;
    private Spinner mMeetServerTypeSP;
    private EditText mMeetServerAddrET;
    private CheckBox mMeetMicCKB;
    private CheckBox mMeetCameraCKB;
    private Button mMeetJoinBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meet_joinmeet);
        mMeetIdET = (EditText)findViewById(R.id.meet_id);
        mMeetPwdET =(EditText) findViewById(R.id.meet_pwd);
        mMeetNameET = (EditText)findViewById(R.id.meet_name);
        mMeetServerTypeSP = (Spinner)findViewById(R.id.meet_server_type);
        mMeetServerAddrET = (EditText)findViewById(R.id.meet_server_addr);
        mMeetMicCKB = (CheckBox)findViewById(R.id.meet_mic_ckb);
        mMeetCameraCKB = (CheckBox)findViewById(R.id.meet_camera_ckb);
        mMeetCameraCKB.setChecked(true);
        mMeetMicCKB.setChecked(true);
        mMeetJoinBtn = (Button)findViewById(R.id.meet_join_btn);
        mMeetJoinBtn.setOnClickListener(this);
        //Cloud/Yms账号已登录，隐藏服务器地址，和服务器类型选项
        if(SettingsManager.getInstance().isCloudLogin()){
            mMeetServerAddrET.setVisibility(View.GONE);
            mMeetServerTypeSP.setVisibility(View.GONE);
        }
        ImageView common_title_left = (ImageView)findViewById(R.id.common_title_left);
        common_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String meetid = AppApplication.preferenceProvider.getMeetid();
        mMeetIdET.setText(meetid);
        String meetPwd = AppApplication.preferenceProvider.getMeetPwd();
        mMeetPwdET.setText(meetPwd);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.meet_join_btn:
                joinMeet();
                break;
        }
    }

    private void joinMeet(){
        String meetId = mMeetIdET.getText().toString();
        String meetName = mMeetNameET.getText().toString();
        AppApplication.preferenceProvider.setMeetid(meetId);
        String meetServerAddr = mMeetServerAddrET.getText().toString();
        String meetPwd = mMeetPwdET.getText().toString();

        AppApplication.preferenceProvider.setMeetPwd(meetPwd);

        boolean meetMicOpen = mMeetMicCKB.isChecked();
        boolean meetCameraOpen = mMeetCameraCKB.isChecked();
        //服务器类型判断
        String meetServerType;
        if(mMeetServerTypeSP.getSelectedItemPosition() == 0){
            meetServerType = ConferenceHistory.TYPE_YMS;
        } else {
            meetServerType = ConferenceHistory.TYPE_CLOUD;
        }

        YealinkApi.instance().joinMeetingById(JoinMeetActivity.this,meetId,meetPwd,meetName,meetServerAddr,meetCameraOpen,meetMicOpen,meetServerType);
    }


}
