package com.numnu.android.adapter.locationdetail;

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
import com.numnu.android.fragments.eventdetail.EventItemsListFragment;
import com.numnu.android.fragments.locationdetail.LocationItemsListFragment;
import com.numnu.android.network.response.EventTagsDataItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thulir on 10/10/17.
 */

public class LocationItemsTagsAdapter extends RecyclerView.Adapter<LocationItemsTagsAdapter.ViewHolder> {

    Context context;
    String locationId;
    List<EventTagsDataItem> list = new ArrayList<>();

    public LocationItemsTagsAdapter(Context context, String locationId, List<EventTagsDataItem> stringArrayList) {
        this.context=context;
        this.locationId = locationId;
        this.list=stringArrayList;
    }

    public  void addData(List<EventTagsDataItem> stringArrayList){
        list.addAll(stringArrayList);
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
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final EventTagsDataItem eventBusinessesResponse = list.get(position);
        holder.textViewName.setText(eventBusinessesResponse.getTagtext());
        holder.itemcount.setText(eventBusinessesResponse.getItemcount()+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocationItemsListFragment eventItemsListFragment= LocationItemsListFragment.newInstance(holder.textViewName.getText().toString(),locationId, String.valueOf(eventBusinessesResponse.getTagid()));

                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout,eventItemsListFragment);
                transaction.addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  ImageView imageViewIcon;
        private TextView textViewName,itemcount;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName =  itemView.findViewById(R.id.txt_itemname);
            this.itemcount = (TextView) itemView.findViewById(R.id.txt_itemcount);
            this.imageViewIcon = itemView.findViewById(R.id.imageView_forward);
        }
    }
}
