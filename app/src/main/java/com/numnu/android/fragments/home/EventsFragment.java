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

public class EventsFragment extends Fragment {

    private RecyclerView currentEventsList, pastEventsList;
    private ArrayList<String> stringlist,stringlist1;
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
        currentEventsList = view.findViewById(R.id.current_up_recyclerview);
        pastEventsList = view.findViewById(R.id.past_recyclerview);

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


        for (int i = 1; i <= 10; i++) {
            stringlist1.add(" Past Event  " + i);
        }
            PastEventsAdapter pastEventsAdapter = new PastEventsAdapter(context, stringlist1);
            pastEventsList.setAdapter(pastEventsAdapter);


    }
}

