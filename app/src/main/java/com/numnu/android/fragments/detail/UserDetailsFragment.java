package com.numnu.android.fragments.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import com.numnu.android.adapter.HorizontalContentAdapter;
import com.numnu.android.adapter.UserPostsAdapter;
import com.numnu.android.fragments.home.SettingsFragment;
import com.numnu.android.fragments.search.SliceFragment;

import java.util.ArrayList;

/**
 * Created by dhivy on 06/11/2017.
 */

public class UserDetailsFragment extends Fragment {
    private RecyclerView mUserPostsRecycler;
    Context context;
    private String id,username;
    HorizontalContentAdapter adapter;
    RecyclerView recyclerView;
    ImageView SettingImg;


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

        View view = inflater.inflate(R.layout.fragment_user_details, container, false);

        ImageView toolbarBackImage = view.findViewById(R.id.toolbar_back);
        toolbarBackImage.setVisibility(View.GONE);
        SettingImg=(ImageView) view.findViewById(R.id.setting_img);
        SettingImg.setVisibility(View.VISIBLE);
        SettingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, SettingsFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });


        toolbarBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        TextView toolbarTitle=view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("@Marc chiriqui");
        recyclerView=(RecyclerView)view.findViewById(R.id.business_recyclerview) ;
        adapter = new HorizontalContentAdapter(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mUserPostsRecycler = view.findViewById(R.id.user_posts_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mUserPostsRecycler.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mUserPostsRecycler.getContext(), LinearLayoutManager.VERTICAL);
        mUserPostsRecycler.addItemDecoration(dividerItemDecoration);
        mUserPostsRecycler.setNestedScrollingEnabled(false);

        setupRecyclerView();

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
