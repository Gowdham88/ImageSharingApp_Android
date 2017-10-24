package com.numnu.android.fragments.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numnu.android.R;
import com.numnu.android.adapter.CurrentUpEventsAdapter;
import com.numnu.android.adapter.PastEventsAdapter;

import java.util.ArrayList;

/**
 * Created by thulir on 9/10/17.
 */

public class CurrentEventsFragment extends Fragment {

    private RecyclerView currentEventsList;
    private ArrayList<String> stringlist,stringlist1;
    Context context;

    public static CurrentEventsFragment newInstance() {
        CurrentEventsFragment fragment = new CurrentEventsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_current_events_list, container, false);
        currentEventsList = view.findViewById(R.id.current_up_recyclerview);

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
        stringlist1 = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            stringlist.add("Item " + i);
        }
            CurrentUpEventsAdapter currentUpAdapter = new CurrentUpEventsAdapter(context, stringlist);
            currentEventsList.setAdapter(currentUpAdapter);


    }
}

