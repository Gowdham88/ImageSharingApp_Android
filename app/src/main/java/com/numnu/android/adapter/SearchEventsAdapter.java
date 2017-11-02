package com.numnu.android.adapter;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.numnu.android.R;
import com.numnu.android.fragments.EventDetailFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by thulir on 10/10/17.
 */

public class SearchEventsAdapter extends RecyclerView.Adapter<SearchEventsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> stringArrayList = new ArrayList<>();

    public SearchEventsAdapter(Context context, ArrayList<String> stringArrayList) {
        this.context=context;
        this.stringArrayList=stringArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_event_item, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private  ImageView imageViewIcon;
        private TextView textViewName;

        ViewHolder(View itemView) {
            super(itemView);
//            this.textViewName =  itemView.findViewById(R.id.text_event);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
//            this.imageViewIcon = itemView.findViewById(R.id.current_event_image);
        }
    }
}