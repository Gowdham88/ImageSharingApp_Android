package com.numnu.android.fragments.search;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numnu.android.R;
import com.numnu.android.adapter.EventBusinessAdapter;

import java.util.ArrayList;

/**
 * Created by thulir on 9/10/17.
 */

public class SearchListFragment extends Fragment {

    private RecyclerView businessRecyclerView;
    private Context context;

    public static SearchListFragment newInstance() {
        return new SearchListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  view= inflater.inflate(R.layout.fragment_search_list, container, false);

        businessRecyclerView = view.findViewById(R.id.business_recyclerview);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        businessRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(businessRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        businessRecyclerView.addItemDecoration(dividerItemDecoration);

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
            stringlist.add("business " + i);

            EventBusinessAdapter currentUpAdapter = new EventBusinessAdapter(context, stringlist);
            businessRecyclerView.setAdapter(currentUpAdapter);
        }

    }
}

