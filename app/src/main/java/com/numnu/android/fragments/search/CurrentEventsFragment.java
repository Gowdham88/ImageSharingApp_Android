package com.numnu.android.fragments.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.numnu.android.R;
import com.numnu.android.adapter.CurrentEventsAdapter;

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

        TextView toolbarTitle=view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.current_upcoming);

        ImageView toolbarBackIcon = view.findViewById(R.id.toolbar_back);
        toolbarBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
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
            stringlist.add("E" + i);
        }

        currentEventsList.setHasFixedSize(true);
        currentEventsList.setItemViewCacheSize(20);
        currentEventsList.setDrawingCacheEnabled(true);
        currentEventsList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        CurrentEventsAdapter currentEventsAdapter = new CurrentEventsAdapter(context, stringlist);
        currentEventsList.setAdapter(currentEventsAdapter);

    }
}

