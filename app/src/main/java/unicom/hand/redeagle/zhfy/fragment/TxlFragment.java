package unicom.hand.redeagle.zhfy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.adapter.TxlAdapter;
import unicom.hand.redeagle.zhfy.bean.TxlBean;
import unicom.hand.redeagle.zhfy.view.txl.CharacterParser;
import unicom.hand.redeagle.zhfy.view.txl.ClearEditText;
import unicom.hand.redeagle.zhfy.view.txl.PinyinComparator2;
import unicom.hand.redeagle.zhfy.view.txl.SideBar;
import com.yealink.common.data.Contact;
import com.yealink.sdk.YealinkApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 通讯录
 * Created by chenhn on 2017/10/19.
 */

public class TxlFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "TxlFragment";
    private Context mContext;
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private TxlAdapter adapter;
    private ClearEditText mClearEditText;
    private CharacterParser characterParser;
    private List<TxlBean> SourceDateList;
    private ImageView imageView;
    private PinyinComparator2 pinyinComparator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "Fragment1 ");
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_txl, container,
                    false);
            mContext = getActivity();
            sideBar = (SideBar) mView.findViewById(R.id.sidrbar);
            dialog = (TextView) mView.findViewById(R.id.dialog);
            sortListView = (ListView) mView.findViewById(R.id.country_lvcountry);
            mClearEditText = (ClearEditText) mView.findViewById(R.id.filter_edit);


        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private View mView;

    private void initData() {

        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator2();

        sideBar.setTextView(dialog);

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                String number = SourceDateList.get(position).getPhone();
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
//                        + number));
//                startActivity(intent);
            }
        });

        List<Contact> contacts =YealinkApi.instance().searchCloudContact("",false);

        if (contacts != null) {
            SourceDateList = new ArrayList<TxlBean>();
            for (Contact bean : contacts) {
                TxlBean txlBean = new TxlBean();
                txlBean.setUserName(bean.getName());
                txlBean.setPhone(bean.getSerialNumber());
                SourceDateList.add(txlBean);
            }
        }

        if (SourceDateList != null) {

            for (TxlBean bean : SourceDateList) {
                String pinyin = "";
                pinyin = characterParser.getSelling(bean
                        .getUserName());
                String sortString = pinyin.substring(0, 1)
                        .toUpperCase();
                if (sortString.matches("[A-Z]")) {
                    bean.setSortLetters(sortString.toUpperCase());
                } else {
                    bean.setSortLetters("#");
                }
            }

            Collections.sort(SourceDateList, (Comparator<? super TxlBean>) pinyinComparator);
            adapter = new TxlAdapter(mContext, SourceDateList);
            sortListView.setAdapter(adapter);
            sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

                @Override
                public void onTouchingLetterChanged(String s) {
                    int position = adapter.getPositionForSection(s
                            .charAt(0));
                    if (position != -1) {
                        sortListView.setSelection(position);
                    }
                }
            });

            mClearEditText
                    .addTextChangedListener(new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence s,
                                                  int start, int before, int count) {
                            filterData(s.toString());
                        }

                        @Override
                        public void beforeTextChanged(
                                CharSequence s, int start,
                                int count, int after) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
        }

    }

    private void filterData(String filterStr) {
        List<TxlBean> filterDateList = new ArrayList<TxlBean>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (TxlBean TxlBean : SourceDateList) {
                String name = TxlBean.getUserName();
                if (name != null) {
                    if (name.indexOf(filterStr.toString()) != -1
                            || characterParser.getSelling2(name).startsWith(
                            filterStr.toString())) {
                        filterDateList.add(TxlBean);
                    }
                }
                String phone = TxlBean.getPhone();
                if (phone != null) {
                    if (phone.indexOf(filterStr.toString()) != -1
                            || characterParser.getSelling(phone).startsWith(
                            filterStr.toString())) {
                        filterDateList.add(TxlBean);
                    }
                }

            }
        }

        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_makecall:
                break;
        }
    }
}
