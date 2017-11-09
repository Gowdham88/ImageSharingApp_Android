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
import com.numnu.android.fragments.ItemDetailFragment;

import java.util.ArrayList;

/**
 * Created by thulir on 10/10/17.
 */

public class EventItemsAdapter extends RecyclerView.Adapter<EventItemsAdapter.ViewHolder> {

    Context context;
    ArrayList<String> stringArrayList = new ArrayList<>();

    public EventItemsAdapter(Context context, ArrayList<String> stringArrayList) {
        this.context=context;
        this.stringArrayList=stringArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_menuitems_item, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, ItemDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

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
            this.textViewName =  itemView.findViewById(R.id.txt_itemname);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = itemView.findViewById(R.id.imageView_forward);
        }
    }
}