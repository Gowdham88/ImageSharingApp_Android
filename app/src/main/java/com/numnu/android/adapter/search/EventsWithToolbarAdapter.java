package com.numnu.android.adapter.search;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.numnu.android.R;
import com.numnu.android.adapter.HorizontalContentAdapter;

import java.util.ArrayList;

/**
 * Created by czsm4 on 05/01/18.
 */

public class EventsWithToolbarAdapter extends RecyclerView.Adapter<EventsWithToolbarAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    HorizontalContentAdapter adapter;
    RecyclerView recyclerView;
    private StorageReference storageRef ;
    private FirebaseStorage storage;
    private String eventId;

    public EventsWithToolbarAdapter(Context context, ArrayList<String> stringArrayList) {
        this.context=context;
        this.stringArrayList=stringArrayList;
    }
    @Override
    public EventsWithToolbarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_event_item, parent, false);


        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);

                transaction.add(R.id.frame_layout, EventDetailFragment.newInstance("51"));
                transaction.addToBackStack(null).commit();

            }
        });

        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout, EventDetailFragment.newInstance("51"));
                transaction.addToBackStack(null).commit();

            }
        });
//        adapter = new HorizontalContentAdapter(context, eventDetailResponse.getTags());
//        adapter = new HorizontalContentAdapter(context, eventhomeBusinesResponse.getTags());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }


    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewIcon;
        private TextView textViewName;

        ViewHolder(View itemView) {
            super(itemView);
            this.textViewName =  itemView.findViewById(R.id.text_event);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.business_recyclerview);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = itemView.findViewById(R.id.current_event_image);
        }
    }
}
