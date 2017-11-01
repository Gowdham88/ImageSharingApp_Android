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
import com.numnu.android.fragments.home.CurrentEventsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dhivy on 01/11/2017.
 */

public class CurrentEventsAdapter extends RecyclerView.Adapter<CurrentEventsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> currentEventsArrayList = new ArrayList<>();

    public CurrentEventsAdapter(Context context, ArrayList<String> currentEventsArrayList) {
        this.context = context;
        this.currentEventsArrayList = currentEventsArrayList;
    }

    @Override
    public CurrentEventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_current_upcoming_event, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CurrentEventsAdapter.ViewHolder holder, int position) {
        holder.textViewName.setText(currentEventsArrayList.get(position));
        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, CurrentEventsFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        Picasso.with(context).load("null")
                .placeholder(R.drawable.food_715539_1920)
                .into(holder.imageViewIcon);

    }

    @Override
    public int getItemCount() {
        return currentEventsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewIcon;
        private TextView textViewName;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName =  itemView.findViewById(R.id.text_event);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = itemView.findViewById(R.id.current_event_image);

        }
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        public DateViewHolder(View itemView) {
            super(itemView);

        }
    }
}
