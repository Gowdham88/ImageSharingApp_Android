package com.numnu.android.fragments.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.numnu.android.R;
import com.numnu.android.adapter.UserPostsAdapter;
import com.numnu.android.fragments.home.SettingsFragment;

import java.util.ArrayList;

/**
 * Created by dhivy on 06/11/2017.
 */

public class UserDetailsFragment extends Fragment {
    private RecyclerView mUserPostsRecycler;
    Context context;
    private String id,username;

    public static UserDetailsFragment newInstance() {
        UserDetailsFragment fragment = new UserDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id=bundle.getString("type","location");
            username = bundle.getString("uname", "Festival");

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if(!PreferencesHelper.getPreferenceBoolean(getActivity(),PreferencesHelper.PREFERENCE_LOGGED_IN))
//        {
//            SignupFragment signupFragment=new SignupFragment();
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            transaction.replace(R.id.frame_layout,signupFragment);
//            transaction.addToBackStack(null).commit();
//        }

        View view = inflater.inflate(R.layout.fragment_user_details, container, false);

        ImageView toolbarBackImage = view.findViewById(R.id.toolbar_back);




        toolbarBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        TextView toolbarTitle=view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("@Marc chiriqui");

        mUserPostsRecycler = view.findViewById(R.id.user_posts_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mUserPostsRecycler.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mUserPostsRecycler.getContext(), LinearLayoutManager.VERTICAL);
        mUserPostsRecycler.addItemDecoration(dividerItemDecoration);
        mUserPostsRecycler.setNestedScrollingEnabled(false);

        setupRecyclerView();

        ImageView toolbarIcon = view.findViewById(R.id.slice_toolbar_icon);

        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, SettingsFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        final Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUserPostsRecycler.scrollToPosition(0);
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void setupRecyclerView() {
        ArrayList<String> stringlist = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            stringlist.add("Post item " + i);
            UserPostsAdapter userPostAdapter = new UserPostsAdapter(context, stringlist);
            mUserPostsRecycler.setAdapter(userPostAdapter);
        }
    }

}