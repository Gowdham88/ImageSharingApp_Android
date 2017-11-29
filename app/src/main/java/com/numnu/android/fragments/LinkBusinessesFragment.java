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
import android.widget.ImageView;
import android.widget.TextView;

import com.numnu.android.R;
import com.numnu.android.adapter.EventBusinessAdapter;

import java.util.ArrayList;

/**
 * Created by thulir on 9/10/17.
 */

public class LinkBusinessesFragment extends Fragment {

    private RecyclerView businessRecyclerView;
    private Context context;

    public static LinkBusinessesFragment newInstance() {
        return new LinkBusinessesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  view= inflater.inflate(R.layout.fragment_event_businesses_link, container, false);

        businessRecyclerView = view.findViewById(R.id.business_recyclerview);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        businessRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(businessRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        businessRecyclerView.addItemDecoration(dividerItemDecoration);

        ImageView toolbarBackImage = view.findViewById(R.id.toolbar_back);

        toolbarBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        TextView toolbarTitle=view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Businesses");
        setupRecyclerView();

        final android.support.v7.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                businessRecyclerView.scrollToPosition(0);
            }
        });




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

