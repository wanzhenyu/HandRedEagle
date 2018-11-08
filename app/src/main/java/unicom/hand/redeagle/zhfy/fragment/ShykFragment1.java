package unicom.hand.redeagle.zhfy.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import unicom.hand.redeagle.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShykFragment1 extends Fragment {


    public ShykFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shyk1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout ll_newmeet = (LinearLayout)view.findViewById(R.id.ll_newmeet);
        for (int i=0;i<3;i++){
            View inflate = View.inflate(getActivity(), R.layout.item_newmeet, null);
            ll_newmeet.addView(inflate);
        }







    }
}
