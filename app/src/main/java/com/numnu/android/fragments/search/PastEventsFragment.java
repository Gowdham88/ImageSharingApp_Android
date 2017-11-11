package com.numnu.android.fragments.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.numnu.android.R;
import com.numnu.android.adapter.PastEventsAdapter;

import java.util.ArrayList;

/**
 * Created by thulir on 9/10/17.
 */

public class PastEventsFragment extends Fragment {

    private RecyclerView pastEventsList;
    private ArrayList<String> stringlist,stringlist1;
    Context context;

    public static PastEventsFragment newInstance() {
        PastEventsFragment fragment = new PastEventsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_past_events_list, container, false);
        pastEventsList = view.findViewById(R.id.past_recyclerview);

        TextView toolbarTitle=view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.past_events_title);
        setupRecyclerView();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void setupRecyclerView() {
        stringlist1 = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            stringlist1.add("E" + i);
        }
            PastEventsAdapter pastEventsAdapter = new PastEventsAdapter(context, stringlist1);
            pastEventsList.setAdapter(pastEventsAdapter);


    }
}

