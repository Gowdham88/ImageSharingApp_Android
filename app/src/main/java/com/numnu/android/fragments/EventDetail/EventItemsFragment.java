package com.numnu.android.fragments.EventDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numnu.android.R;
import com.numnu.android.adapter.EventItemsAdapter;

import java.util.ArrayList;

/**
 * Created by thulir on 9/10/17.
 */

public class EventItemsFragment extends Fragment {

    private RecyclerView menuitemsRecyclerView;
    private Context context;

    public static EventItemsFragment newInstance() {
        return new EventItemsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    View  view= inflater.inflate(R.layout.fragment_event_menu_items, container, false);

    menuitemsRecyclerView = view.findViewById(R.id.menu_items_recyclerview);
    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        menuitemsRecyclerView.setLayoutManager(layoutManager);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(menuitemsRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        menuitemsRecyclerView.addItemDecoration(dividerItemDecoration);

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
            stringlist.add("Menu item " + i);

            EventItemsAdapter currentUpAdapter = new EventItemsAdapter(context, stringlist);
            menuitemsRecyclerView.setAdapter(currentUpAdapter);
        }

    }
}
