package com.numnu.android.adapter.eventdetail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.numnu.android.R;
import com.numnu.android.adapter.HorizontalContentAdapter;
import com.numnu.android.fragments.detail.BusinessDetailFragment;
import com.numnu.android.network.response.DataItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



public class EventBusinessesAdapter extends RecyclerView.Adapter<EventBusinessesAdapter.ViewHolder> {


    Context context;
    List<DataItem> list = new ArrayList<>();
    HorizontalContentAdapter adapter;
    private RecyclerView recyclerView;
    private StorageReference storageRef ;
    private FirebaseStorage storage;
    private String eventId;

    public EventBusinessesAdapter(Context context, String eventId,List<DataItem> stringArrayList) {
        this.context=context;
        this.list =stringArrayList;
        this.eventId=eventId;
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();
    }

    public  void addData(List<DataItem> stringArrayList){
        list.addAll(stringArrayList);
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
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final DataItem eventBusinessesResponse = list.get(position);

        holder.textViewName.setText(list.get(position).getBusinessname());
        if(!eventBusinessesResponse.getUserimages().isEmpty()&&eventBusinessesResponse.getUserimages().get(0).getImageurl()!=null) {
            storageRef.child(eventBusinessesResponse.getUserimages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
                Bundle bundle = new Bundle();
                bundle.putString("businessId", String.valueOf(eventBusinessesResponse.getId()));
                bundle.putString("eventId", eventId);
                BusinessDetailFragment businessDetailFragment = BusinessDetailFragment.newInstance();
                businessDetailFragment.setArguments(bundle);

                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout, businessDetailFragment);
                transaction.addToBackStack(null).commit();
            }
        });
        holder.RelativeLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("businessId", String.valueOf(eventBusinessesResponse.getId()));
                bundle.putString("eventId", eventId);
                BusinessDetailFragment businessDetailFragment = BusinessDetailFragment.newInstance();
                businessDetailFragment.setArguments(bundle);

                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout, businessDetailFragment);
                transaction.addToBackStack(null).commit();
            }
        });

        adapter = new HorizontalContentAdapter(context, eventBusinessesResponse.getTags());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  ImageView imageViewIcon;
        private TextView textViewName;
        private  RelativeLayout RelativeLay;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName =  itemView.findViewById(R.id.business_name);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.business_recyclerview);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = itemView.findViewById(R.id.notification_image);
            this.RelativeLay=itemView.findViewById(R.id.business_rel_lay);
        }
    }
}
