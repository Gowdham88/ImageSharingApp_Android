package com.numnu.android.fragments.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numnu.android.R;
import com.numnu.android.adapter.search.SearchEventsAdapter;

import java.util.ArrayList;

/**
 * Created by thulir on 9/10/17.
 */

public class EventsFragment extends Fragment {

    private RecyclerView searchEventsList;
    private ArrayList<String> stringlist;
    Context context;

    public static EventsFragment newInstance() {
        EventsFragment fragment = new EventsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);
        searchEventsList = view.findViewById(R.id.search_recyclerview);

        setupRecyclerView();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void setupRecyclerView() {
        stringlist = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            stringlist.add("Item " + i);
        }

        SearchEventsAdapter currentUpAdapter = new SearchEventsAdapter(context, stringlist);
        searchEventsList.setAdapter(currentUpAdapter);

    }


}
