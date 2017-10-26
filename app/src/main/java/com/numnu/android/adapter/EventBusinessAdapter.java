package com.numnu.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.numnu.android.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by thulir on 10/10/17.
 */

public class EventBusinessAdapter extends RecyclerView.Adapter<EventBusinessAdapter.ViewHolder> {

    Context context;
    ArrayList<String> stringArrayList = new ArrayList<>();

    public EventBusinessAdapter(Context context, ArrayList<String> stringArrayList) {
        this.context=context;
        this.stringArrayList=stringArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_business_item, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textViewName.setText(stringArrayList.get(position));

        Picasso.with(context).load("null")
                .placeholder(R.drawable.food_2616456_1920)
                .into(holder.imageViewIcon);
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  ImageView imageViewIcon;
        private TextView textViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName =  itemView.findViewById(R.id.text_notification);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = itemView.findViewById(R.id.notification_image);
        }
    }
}
