package com.numnu.android.fragments.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.numnu.android.R;
import com.numnu.android.adapter.SearchPostsAdapter;

import java.util.ArrayList;

/**
 * Created by thulir on 9/10/17.
 */

public class SearchPostsFragment extends Fragment {

    private RecyclerView menuitemsRecyclerView;
    Context context;
    ImageView dotsimg;

    public static SearchPostsFragment newInstance() {
        SearchPostsFragment fragment = new SearchPostsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        menuitemsRecyclerView = view.findViewById(R.id.reviews_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        menuitemsRecyclerView.setLayoutManager(layoutManager);
        menuitemsRecyclerView.setNestedScrollingEnabled(false);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(menuitemsRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
//        menuitemsRecyclerView.addItemDecoration(dividerItemDecoration);
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

            SearchPostsAdapter currentUpAdapter = new SearchPostsAdapter(context, stringlist);
            menuitemsRecyclerView.setAdapter(currentUpAdapter);
        }

    }

}
