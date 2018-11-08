package unicom.hand.redeagle.zhfy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yealink.common.data.Meeting;
import com.yealink.sdk.YealinkApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.ui.JoinMeetActivity;
import unicom.hand.redeagle.zhfy.ui.LoginSphyActivity;
import unicom.hand.redeagle.zhfy.ui.MyDepartMentActivity3;
import unicom.hand.redeagle.zhfy.utils.GsonUtils;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SphyFragment extends Fragment {


    private TextView tv_state;
    private TextView tv_time;
    private TextView tv_theme;
    private TextView tv_join;
    private SimpleDateFormat simpledate;
    private LinearLayout ll_hy;

    public SphyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sphy, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv_back  = (TextView)view.findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        LinearLayout ll_join = (LinearLayout)view.findViewById(R.id.ll_join);

        LinearLayout ll_now = (LinearLayout)view.findViewById(R.id.ll_now);
        ll_hy = (LinearLayout)view.findViewById(R.id.ll_hy);
        TextView tv_zx  = (TextView)view.findViewById(R.id.tv_zx);
        ll_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UIUtils.getContext(), JoinMeetActivity.class);
                startActivity(intent);


            }
        });

        ll_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UIUtils.getContext(), MyDepartMentActivity3.class);
                startActivity(intent);
            }
        });
        simpledate = new SimpleDateFormat("MM/dd hh:mm");
        tv_state = (TextView)view.findViewById(R.id.tv_state);
        tv_time = (TextView)view.findViewById(R.id.tv_time);
        tv_theme = (TextView)view.findViewById(R.id.tv_theme);
        tv_join = (TextView)view.findViewById(R.id.tv_join);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Meeting> list = YealinkApi.instance().getMeetingList();

                    if(list != null && list.size()>0){
                        for (int i=0;i<list.size();i++){
                            Meeting meeting = list.get(i);
                            String str = meeting.getTitle();
                            if(str.startsWith("[0]")){
                                showMeet(meeting);
                            }else if(!str.startsWith("["+1+"]") && !str.startsWith("进行中\n"+"["+1+"]") && !str.startsWith("["+2+"]") && !str.startsWith("进行中\n"+"["+2+"]") && !str.startsWith("["+3+"]") && !str.startsWith("进行中\n"+"["+3+"]") && !str.startsWith("["+4+"]") && !str.startsWith("进行中\n"+"["+4+"]")){
                                showMeet(meeting);

                            }
                        }


                    }else{
                        ll_hy.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },3000);


        tv_zx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
                Intent intent = new Intent(getActivity(), LoginSphyActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });





    }

    private void showMeet(final Meeting meeting) {
        ll_hy.setVisibility(View.VISIBLE);
//        final Meeting meeting = list.get(0);
        long startTime = meeting.getStartTime();
        String startformat = simpledate.format(new Date(startTime));
        long endTime = meeting.getEndTime();
        String endformat = simpledate.format(new Date(endTime));
        tv_time.setText("时间："+startformat+"~"+endformat);
        String title = meeting.getTitle();
        String hyrcTtitle = UIUtils.getHyrcTtitle(title);
        tv_theme.setText("主题："+hyrcTtitle);
        meeting.setTitle(hyrcTtitle);
//                    String string = getString(com.yealink.base.R.string.bs_conference_share_join0);
//                    String b = String.format(string, "342423ew");
//                    Resources res = getResources();
//                    ShareThirdActionSheet shareThirdActionSheet = new ShareThirdActionSheet();
        tv_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    YealinkApi.instance().joinMeeting(getActivity(),meeting);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void logout(){
        try {
            YealinkApi.instance().unregistCloud();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
