package unicom.hand.redeagle.zhfy.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.adapter.CheckTxlAdapter;
import unicom.hand.redeagle.zhfy.bean.TxlBean;
import unicom.hand.redeagle.zhfy.view.txl.CharacterParser;
import unicom.hand.redeagle.zhfy.view.txl.ClearEditText;
import unicom.hand.redeagle.zhfy.view.txl.PinyinComparator2;
import unicom.hand.redeagle.zhfy.view.txl.SideBar;
import com.yealink.base.util.ToastUtil;
import com.yealink.common.data.Contact;
import com.yealink.sdk.YealinkApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class CheckTxlActivity extends Activity {
    public static List<String> idsList ;
    private Context mContext;
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private TextView check;
    private CheckTxlAdapter adapter;
    private ClearEditText mClearEditText;
    private CharacterParser characterParser;
    private List<TxlBean> SourceDateList;
    private ImageView imageView;
    private PinyinComparator2 pinyinComparator;
    private ImageView iv_back;
    List<Contact> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_txl);

        initView();
        initData();
    }

    private void initView() {
        idsList = new ArrayList<String>();
        mContext = CheckTxlActivity.this;
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        check= (TextView) findViewById(R.id.common_title_right);
        sortListView = (ListView)findViewById(R.id.country_lvcountry);
        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

        iv_back = (ImageView) findViewById(R.id.common_title_left);
        iv_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });


    }


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

        contacts = YealinkApi.instance().searchCloudContact("",false);

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
            adapter = new CheckTxlAdapter(mContext, SourceDateList);
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

        adapter.setOnCheckInterface(new CheckTxlAdapter.CheckInterface() {
            @Override
            public void onClick() {
                check.setText("确定"+"("+idsList.size()+")");
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(check.getText().toString())){
                    List<Contact> checkcontacts =  new ArrayList<Contact>();
                    for (String str:idsList){
                        for (Contact bean:contacts){
                            if (bean.getSerialNumber().equals(str)){
                                checkcontacts.add(bean);
                            }
                        }
                    }
                    YealinkApi.instance().meetNow(mContext, (ArrayList<Contact>) checkcontacts);

                } else {
                    ToastUtil.toast(mContext,"请选择参会人员！");
                }


            }
        });
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

}





