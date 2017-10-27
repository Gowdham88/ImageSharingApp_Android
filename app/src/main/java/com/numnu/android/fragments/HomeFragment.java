package com.numnu.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.numnu.android.R;
import com.numnu.android.adapter.CurrentUpEventsAdapter;
import com.numnu.android.adapter.PastEventsAdapter;
import com.numnu.android.fragments.home.CurrentEventsFragment;
import com.numnu.android.fragments.home.PastEventsFragment;

import java.util.ArrayList;

/**
 * Created by thulir on 9/10/17.
 */

public class HomeFragment extends Fragment {

    private RecyclerView currentEventsList, pastEventsList;
    private ArrayList<String> stringlist,stringlist1;
    Context context;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        currentEventsList = view.findViewById(R.id.current_up_recyclerview);
        pastEventsList = view.findViewById(R.id.past_recyclerview);

        ImageView viewCurrentEventsList = view.findViewById(R.id.view_current_event_list);

        ImageView viewPastEventsList = view.findViewById(R.id.view_past_event_list);

        viewCurrentEventsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, CurrentEventsFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        viewPastEventsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, PastEventsFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

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
            stringlist1.add("Event " + i);
        }
            PastEventsAdapter pastEventsAdapter = new PastEventsAdapter(context, stringlist1);
            pastEventsList.setAdapter(pastEventsAdapter);


    }


}

