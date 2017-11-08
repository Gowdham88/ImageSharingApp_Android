package com.numnu.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numnu.android.R;
import com.numnu.android.adapter.PostsAdapter;
import com.numnu.android.adapter.UserPostsAdapter;

import java.util.ArrayList;

/**
 * Created by dhivy on 06/11/2017.
 */

public class UserPostsFragment extends Fragment {
    private RecyclerView mUserPostsRecycler;
    Context context;

    public static UserPostsFragment newInstance() {
        UserPostsFragment fragment = new UserPostsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_posts, container, false);
        mUserPostsRecycler = view.findViewById(R.id.user_posts_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mUserPostsRecycler.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mUserPostsRecycler.getContext(), LinearLayoutManager.VERTICAL);
        mUserPostsRecycler.addItemDecoration(dividerItemDecoration);

        setupRecyclerView();

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
