package com.numnu.android.fragments.home;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.numnu.android.R;

/**
 * Created by Thulirsoft on 20/10/2017.
 */

public class SliceFragment extends Fragment {

    private BottomSheetBehavior mBottomSheetBehavior1;
    Context context;

    public static SliceFragment newInstance() {
        SliceFragment fragment = new SliceFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_slice, container, false);

        TextView textView = view.findViewById(R.id.liked_counts);
        textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

//        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
//        View bottomSheetView = inflater.inflate(R.layout.fragment_slice,null);
//        bottomSheetDialog.setContentView(bottomSheetView);
//
//
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(mBottomSheetBehavior1.getState() != BottomSheetBehavior.STATE_EXPANDED) {
//                    mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);
////                    mButton1.setText(R.string.collapse_button1);
//                }
//                else {
//                    mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_COLLAPSED);
////                    mButton1.setText(R.string.button1);
//                }
//            }
//        });
        return view;
    }
}
