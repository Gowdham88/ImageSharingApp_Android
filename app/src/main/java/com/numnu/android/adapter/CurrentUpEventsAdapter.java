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

public class CurrentUpEventsAdapter extends RecyclerView.Adapter<CurrentUpEventsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> stringArrayList = new ArrayList<>();

    public CurrentUpEventsAdapter(Context context, ArrayList<String> stringArrayList) {
        this.context=context;
        this.stringArrayList=stringArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.current_event_item, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textViewName.setText(stringArrayList.get(position));
        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EventDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        Picasso.with(context).load("null")
                .placeholder(R.drawable.food_1631727_1920)
                .into(holder.imageViewIcon);
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
            this.textViewName =  itemView.findViewById(R.id.text_event);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = itemView.findViewById(R.id.current_event_image);
        }
    }
}
