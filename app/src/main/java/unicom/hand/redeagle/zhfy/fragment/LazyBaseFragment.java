package unicom.hand.redeagle.zhfy.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.utils.UIUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class LazyBaseFragment extends Fragment {
    private ImageView iv_right;
    private ProgressDialog progressDialog;

    public LazyBaseFragment() {
        // Required empty public constructor
    }


    protected View rootView;

    private int count;//记录开启进度条的情况 只能开一个
    //当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
    private boolean isFragmentVisible;
    //是否是第一次开启网络加载
    public boolean isFirst;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        initView(rootView);
        //可见，但是并没有加载过
        if (isFragmentVisible && !isFirst) {
            onFragmentVisibleChange(true);
        }
        return rootView;
    }

    //获取布局文件
    protected abstract int getLayoutResource();


    //初始化view
    protected abstract void initView(View v);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isFragmentVisible = true;
        }
        if (rootView == null) {
            return;
        }
        //可见，并且没有加载过
        if (!isFirst&&isFragmentVisible) {
            onFragmentVisibleChange(true);
            return;
        }
        //由可见——>不可见 已经加载过
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }


    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作.
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {

    }



    /**
     * 初始化标题栏，左边有返回箭头，中间是标题
     * @param text 标题文字
     */
    public void initCenterTile(View v, String text){


        TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
        tv_title.setText(text);
    }

    public void showProgressDialog(String text){
        progressDialog = new ProgressDialog(UIUtils.getContext(), ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setMessage(text);
        progressDialog.show();
    }
    public  void hideProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
    /**
     * 初始化标题栏，左边有返回箭头，中间是标题
     * @param text 标题文字
     */
    public void initLeftTile(View v, String text){

        ImageView iv_left = (ImageView) v.findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
        tv_title.setText(text);
    }

    /**
     * 初始化标题栏左边返回图标，中间是文字，右边是图标
     * @param text
     */
    public void initRightTile(View v, String text, int id){

        ImageView iv_left = (ImageView) v.findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
        tv_title.setText(text);



        iv_right = (ImageView) v.findViewById(R.id.iv_right);
        iv_right.setImageResource(id);
        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTitleRightClick();
            }
        });

    }
    /**
     * toast显示文字
     * @param text
     */
    public void toast(String text){
        Toast.makeText(UIUtils.getContext(), text+"", Toast.LENGTH_SHORT).show();
    }



    public abstract void setTitleRightClick();















}
