package unicom.hand.redeagle.zhfy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.yealink.sdk.YealinkApi;

import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.zhfy.BaseFragment;
import unicom.hand.redeagle.zhfy.Common;
import unicom.hand.redeagle.zhfy.MainActivity;
import unicom.hand.redeagle.zhfy.PublishMeetingActivity;
import unicom.hand.redeagle.zhfy.bean.MyCityBean2;
import unicom.hand.redeagle.zhfy.ui.Activity_listxzfw;
import unicom.hand.redeagle.zhfy.ui.JoinMeetingMemberActivity;
import unicom.hand.redeagle.zhfy.ui.LoginActivity;
import unicom.hand.redeagle.zhfy.ui.LoginSphyActivity;
import unicom.hand.redeagle.zhfy.ui.ManageMeetingActivity;
import unicom.hand.redeagle.zhfy.ui.MeetingActivity;
import unicom.hand.redeagle.zhfy.ui.MeetingRecoderActivity;
import unicom.hand.redeagle.zhfy.utils.GsonUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShykFragment extends Fragment {


    private DbUtils dbUtils;

    public ShykFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shyk, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout ll_menu1 = (LinearLayout)view.findViewById(R.id.ll_menu1);
        dbUtils = DbUtils.create(getActivity(), Common.DB_NAME);
        ll_menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = AppApplication.preferenceProvider.getUsername();
                Log.e("aaa","当前用户名："+username);
                try {
                    List<MyCityBean2> all = dbUtils.findAll(Selector
                            .from(MyCityBean2.class).where("valid", "=", "1")
                            .and(WhereBuilder.b("layer", "=",
                                    1)).and(WhereBuilder.b("calledNum", "=",
                                    username)).orderBy("sort", false));
                    if(all != null && all.size()>0){//普通群众
                        Intent intent = new Intent(getActivity(), MeetingActivity.class);
                        intent.putExtra("title","支部党员大会");
                        startActivity(intent);
                    }else{
                        if(all != null){//管理员
                            Intent intent = new Intent(getActivity(), ManageMeetingActivity.class);
                            intent.putExtra("title","支部党员大会");
                            startActivity(intent);
                        }

                    }
//                    String json = GsonUtils.getJson(all);
//                    Log.e("aaa","查出来的数据："+json);

                } catch (DbException e) {
                    e.printStackTrace();
                }
//                Intent intent = new Intent(getActivity(), MeetingActivity.class);
//                intent.putExtra("title","支部党员大会");
//                startActivity(intent);
            }
        });
        LinearLayout ll_menu2 = (LinearLayout)view.findViewById(R.id.ll_menu2);

        ll_menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = AppApplication.preferenceProvider.getUsername();
                Log.e("aaa","当前用户名："+username);
                try {
                    List<MyCityBean2> all = dbUtils.findAll(Selector
                            .from(MyCityBean2.class).where("valid", "=", "1")
                            .and(WhereBuilder.b("layer", "=",
                                    1)).and(WhereBuilder.b("calledNum", "=",
                                    username)).orderBy("sort", false));
                    if(all != null && all.size()>0){//普通群众
                        Intent intent = new Intent(getActivity(), MeetingActivity.class);
                        intent.putExtra("title","支部委员会");
                        startActivity(intent);
                    }else{
                        if(all != null){//管理员
                            Intent intent = new Intent(getActivity(), ManageMeetingActivity.class);
                            intent.putExtra("title","支部委员会");
                            startActivity(intent);
                        }

                    }
//                    String json = GsonUtils.getJson(all);
//                    Log.e("aaa","查出来的数据："+json);

                } catch (DbException e) {
                    e.printStackTrace();
                }
//                Intent intent = new Intent(getActivity(), MeetingActivity.class);
//                intent.putExtra("title","支部委员会");
//                startActivity(intent);
            }
        });
        LinearLayout ll_menu3 = (LinearLayout)view.findViewById(R.id.ll_menu3);

        ll_menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = AppApplication.preferenceProvider.getUsername();
                Log.e("aaa","当前用户名："+username);
                try {
                    List<MyCityBean2> all = dbUtils.findAll(Selector
                            .from(MyCityBean2.class).where("valid", "=", "1")
                            .and(WhereBuilder.b("layer", "=",
                                    1)).and(WhereBuilder.b("calledNum", "=",
                                    username)).orderBy("sort", false));
                    if(all != null && all.size()>0){//普通群众
                        Intent intent = new Intent(getActivity(), MeetingActivity.class);
                        intent.putExtra("title","党小组会");
                        startActivity(intent);
                    }else{
                        if(all != null){//管理员
                            Intent intent = new Intent(getActivity(), ManageMeetingActivity.class);
                            intent.putExtra("title","党小组会");
                            startActivity(intent);
                        }

                    }
//                    String json = GsonUtils.getJson(all);
//                    Log.e("aaa","查出来的数据："+json);

                } catch (DbException e) {
                    e.printStackTrace();
                }
//                Intent intent = new Intent(getActivity(), MeetingActivity.class);
//                intent.putExtra("title","党小组会");
//                startActivity(intent);
            }
        });

        LinearLayout ll_menu4 = (LinearLayout)view.findViewById(R.id.ll_menu4);

        ll_menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = AppApplication.preferenceProvider.getUsername();
                Log.e("aaa","当前用户名："+username);
                try {
                    List<MyCityBean2> all = dbUtils.findAll(Selector
                            .from(MyCityBean2.class).where("valid", "=", "1")
                            .and(WhereBuilder.b("layer", "=",
                                    1)).and(WhereBuilder.b("calledNum", "=",
                                    username)).orderBy("sort", false));
                    if(all != null && all.size()>0){//普通群众
                        Intent intent = new Intent(getActivity(), MeetingActivity.class);
                        intent.putExtra("title","党课");
                        startActivity(intent);
                    }else{
                        if(all != null){//管理员
                            Intent intent = new Intent(getActivity(), ManageMeetingActivity.class);
                            intent.putExtra("title","党课");
                            startActivity(intent);
                        }

                    }
//                    String json = GsonUtils.getJson(all);
//                    Log.e("aaa","查出来的数据："+json);

                } catch (DbException e) {
                    e.printStackTrace();
                }
//                Intent intent = new Intent(getActivity(), ManageMeetingActivity.class);
//                intent.putExtra("title","党课");
//                startActivity(intent);
            }
        });

        TextView tv_zx = (TextView)view.findViewById(R.id.tv_zx);
        tv_zx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
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


    @Override
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
}
