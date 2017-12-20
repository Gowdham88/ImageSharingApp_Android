package com.numnu.android.adapter.businessdetail;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.numnu.android.R;
import com.numnu.android.adapter.HorizontalContentAdapter;
import com.numnu.android.fragments.detail.EventDetailFragment;
import com.numnu.android.network.response.EventdataItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thulir on 10/10/17.
 */

public class BusinessEventsAdapter extends RecyclerView.Adapter<BusinessEventsAdapter.ViewHolder> {

    private Context context;
    private List<EventdataItem> list = new ArrayList<>();
    HorizontalContentAdapter adapter;
    RecyclerView recyclerView;
    private StorageReference storageRef ;
    private FirebaseStorage storage;

    public BusinessEventsAdapter(Context context,List<EventdataItem> stringArrayList) {
        this.context=context;
        this.list=stringArrayList;
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();
    }

    public  void addData(List<EventdataItem> stringArrayList){
        list.addAll(stringArrayList);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_event_item, parent, false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final EventdataItem eventdataItem = list.get(position);

        String startDate=list.get(position).getStartsat();
        String endDate=list.get(position).getEndsat();
        String location = list.get(position).getLocation().getName();

        holder.textViewName.setText(list.get(position).getName());
        holder.textEventDate.setText(location);

        if(!eventdataItem.getEventimages().isEmpty()&&eventdataItem.getEventimages().get(0).getImageurl()!=null) {
            storageRef.child(eventdataItem.getEventimages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Picasso.with(context).load(uri)
                            .placeholder(R.drawable.background)
                            .fit()
                            .into(holder.imageViewIcon);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }


        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout, EventDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout, EventDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        adapter = new HorizontalContentAdapter(context, eventdataItem.getTags());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private  ImageView imageViewIcon;
        private TextView textViewName,textEventDate;

        ViewHolder(View itemView) {
            super(itemView);
            this.textViewName =  itemView.findViewById(R.id.text_event);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.business_recyclerview);
            this.textEventDate = (TextView) itemView.findViewById(R.id.txt_event_date);
            this.imageViewIcon = itemView.findViewById(R.id.current_event_image);
        }
    }
}
