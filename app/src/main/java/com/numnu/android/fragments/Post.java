package com.numnu.android.fragments;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numnu.android.R;
import com.numnu.android.adapter.PostsTabadapter;


public class Post extends Fragment {

    RecyclerView Rv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.fragmrntpost_tab, container, false);
        Rv=(RecyclerView)v.findViewById(R.id.post_recycler) ;
        PostsTabadapter adapter = new PostsTabadapter(getActivity());
        Rv.setAdapter(adapter);
        Rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

}
