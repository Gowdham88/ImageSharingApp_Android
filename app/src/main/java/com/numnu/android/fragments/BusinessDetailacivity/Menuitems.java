package com.numnu.android.fragments.BusinessDetailacivity;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numnu.android.R;
import com.numnu.android.adapter.MenuItemsAdapter;

public class Menuitems extends Fragment {
    RecyclerView Rv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.fragment_menuitems, container, false);
        Rv =(RecyclerView)v.findViewById(R.id.business_menuitems_recyclerview);
        MenuItemsAdapter adapter = new MenuItemsAdapter(getActivity());
        Rv.setAdapter(adapter);
        Rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }


}
