package com.numnu.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.numnu.android.R;
import com.numnu.android.adapter.NotificationsAdapter;

import java.util.ArrayList;

/**
 * Created by thulir on 9/10/17.
 */

public class NotificationFragment extends Fragment {

    private RecyclerView notificationRecyclerView;
    private Context context;
    private ArrayList<String> stringlist;

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view= inflater.inflate(R.layout.fragment_notification, container, false);

        notificationRecyclerView = view.findViewById(R.id.notification_recycler_view);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        notificationRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(notificationRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        notificationRecyclerView.addItemDecoration(dividerItemDecoration);

        TextView toolbarTitle=view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Notifications");

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
            stringlist.add("Notification " + i);

            NotificationsAdapter currentUpAdapter = new NotificationsAdapter(context, stringlist);
            notificationRecyclerView.setAdapter(currentUpAdapter);
        }

    }
}

