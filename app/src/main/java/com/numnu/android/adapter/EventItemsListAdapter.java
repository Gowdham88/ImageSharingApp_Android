package com.numnu.android.adapter;

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
import com.numnu.android.fragments.detail.ItemInfoFragment;
import com.numnu.android.network.response.DataItem;
import com.numnu.android.network.response.EventTagBusiness;
import com.numnu.android.network.response.EventTagsDataItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thulir on 10/10/17.
 */

public class EventItemsListAdapter extends RecyclerView.Adapter<EventItemsListAdapter.ViewHolder> {

    Context context;
    List<EventTagBusiness> list = new ArrayList<>();
    HorizontalContentAdapter adapter;
    RecyclerView recyclerView;
    private StorageReference storageRef ;
    private FirebaseStorage storage;

    public EventItemsListAdapter(Context context, List<EventTagBusiness> stringArrayList) {
        this.context=context;
        this.list=stringArrayList;
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();
    }

    public  void addData(List<EventTagBusiness> stringArrayList){
        list.addAll(stringArrayList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_listitem, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final EventTagBusiness dataItem = list.get(position);

        holder.textViewName.setText(list.get(position).getBusinessname());

        storageRef.child(dataItem.getItemimages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Picasso.with(context).load(uri)
                        .placeholder(R.drawable.food_715539_1920)
                        .fit()
                        .into(holder.imageViewIcon);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, ItemInfoFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, ItemInfoFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        adapter = new HorizontalContentAdapter(context, dataItem.getTags());
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


        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName =  itemView.findViewById(R.id.item_name);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.business_recyclerview);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = itemView.findViewById(R.id.item_image);

        }
    }


}
