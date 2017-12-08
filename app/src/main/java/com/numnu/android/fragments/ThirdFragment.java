package com.numnu.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numnu.android.R;
import com.numnu.android.fragments.home.HomeFragment;
import com.numnu.android.fragments.home.UserPostsFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment {


    public ThirdFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ThirdFragment newInstance() {
        return new ThirdFragment();
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
        transaction.replace(R.id.frame_layout3, UserPostsFragment.newInstance());
        transaction.addToBackStack(null).commit();

        return inflater.inflate(R.layout.fragment_third, container, false);
    }

}
