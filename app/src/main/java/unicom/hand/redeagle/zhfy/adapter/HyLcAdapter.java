package unicom.hand.redeagle.zhfy.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.bean.ProcedureInfos;
import unicom.hand.redeagle.zhfy.ui.HylcActivity;
import unicom.hand.redeagle.zhfy.utils.UIUtils;

/**
 * Created by linana on 2018-07-12.
 */

public class HyLcAdapter extends BaseAdapter {
    private int selectpos = -1;
    List<ProcedureInfos> beans;
//    List<ProcedureInfos> uploadbeans;
    Context context;
    public HyLcAdapter(Context context,List<ProcedureInfos> beans){
        this.context = context;
        this.beans = beans;
//        this.uploadbeans = beans;
        for (int i=0;i<beans.size();i++){
            ProcedureInfos procedureInfos = beans.get(i);
            boolean active = procedureInfos.isActive();
            if(active){
                selectpos = i;
            }
        }
    }
    public List<ProcedureInfos> getUploadBeans(){
        return beans;
    }
    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int i) {
        return beans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        HyLcHolder holder;
        if(view == null){
            holder = new HyLcHolder();
            view = View.inflate(context, R.layout.item_hylc, null);
            holder.et_input = (EditText)view.findViewById(R.id.et_input);
            holder.tv_number = (TextView)view.findViewById(R.id.tv_number);
            holder.iv_select = (ImageView)view.findViewById(R.id.iv_select);
            holder.et_input.addTextChangedListener(new MyTextWatcher(holder));
            view.setTag(holder);
        }else{
            holder = (HyLcHolder)view.getTag();
        }
        holder.position = i;
        final ProcedureInfos procedureInfos = beans.get(i);
        final  String id = procedureInfos.getId();
        holder.tv_number.setText(""+id);
        String displayText = procedureInfos.getDisplayText();
        holder.et_input.setText(UIUtils.getRealText(displayText));
        holder.iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectpos = i;
                refreshUi();
            }
        });


//        String s = holder.et_input.getText().toString();
//        if(TextUtils.isEmpty(s)){
//            Log.e("aaa","空");
//            holder.et_input.setText(UIUtils.getRealText(displayText));
//        }else{
//            Log.e("aaa","不是空:"+s);
//            procedureInfos.setDisplayText(s);
//            holder.et_input.setText(s);
//        }





        if(selectpos == i){
            holder.iv_select.setImageResource(R.drawable.cb_checked);
        }else{
            if(selectpos == -1){
                boolean active = procedureInfos.isActive();
                if(active){
                    holder.iv_select.setImageResource(R.drawable.cb_checked);
                }else{
                    holder.iv_select.setImageResource(R.drawable.cb_normal);
                }
            }else{
                holder.iv_select.setImageResource(R.drawable.cb_normal);
            }

        }
//        holder.et_input.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String s = editable.toString();
//                procedureInfos.setDisplayText(s);
//                Log.e("aaa","输入框当前字符串："+s);
//
//            }
//        });


        return view;
    }
    public int getSelectPos(){
        return selectpos;
    }

    private void refreshUi() {
        notifyDataSetChanged();
    }

    class HyLcHolder{
        EditText et_input;
        TextView tv_number;
        ImageView iv_select;
        int position;
    }


    class MyTextWatcher implements TextWatcher{
        HyLcHolder holder;
        public MyTextWatcher(HyLcHolder holder){
            this.holder =holder;
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String s = editable.toString();
            ProcedureInfos procedureInfos = beans.get(holder.position);
            String displayText = procedureInfos.getDisplayText();
            if(TextUtils.isEmpty(s)){

                holder.et_input.setText(displayText);
                    }else{
                        procedureInfos.setDisplayText(s);
                    }

            beans.set(holder.position,procedureInfos);


        }
    }






}
