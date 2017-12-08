package com.numnu.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numnu.android.R;
import com.numnu.android.fragments.home.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends RootFragment {

    Boolean isfirst = true;

    public FirstFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance() {
        FirstFragment fragment = new FirstFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout1, HomeFragment.newInstance());
            transaction.addToBackStack(null).commit();


        return inflater.inflate(R.layout.fragment_first, container, false);
    }

}
