package com.numnu.android.adapter.search;

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
import com.numnu.android.network.response.DataItem;
import com.numnu.android.network.response.HomeEvebtResp;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thulir on 10/10/17.
 */

public class SearchEventsAdapter extends RecyclerView.Adapter<SearchEventsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    List<HomeEvebtResp> list = new ArrayList<>();
    HorizontalAdapterHome adapter;
    RecyclerView recyclerView;
    private StorageReference storageRef ;
    private FirebaseStorage storage;


    public SearchEventsAdapter(Context context, List<HomeEvebtResp> stringArrayList) {
        this.context=context;
        this.list =stringArrayList;
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();
    }
    public  void addData(List<HomeEvebtResp> stringArrayList){
        list.addAll(stringArrayList);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_event_item, parent, false);


        return new ViewHolder(view);
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private  ImageView imageViewIcon;
        private TextView textViewName,txtdate;

        ViewHolder(View itemView) {
            super(itemView);
            this.textViewName =  itemView.findViewById(R.id.text_event);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.business_recyclerview);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = itemView.findViewById(R.id.current_event_image);
            this.txtdate = itemView.findViewById(R.id.txt_event_date);

        }
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final HomeEvebtResp eventhomeRespo = list.get(position);
        holder.textViewName.setText(list.get(position).getName());
        if(!eventhomeRespo.getEventimages().isEmpty()&&eventhomeRespo.getEventimages().get(0).getImageurl()!=null) {
            storageRef.child(eventhomeRespo.getEventimages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
        String StrtDate=eventhomeRespo.getStartsat();
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        java.util.Date date = null;
        try
        {
            date = form.parse(StrtDate);
        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("MMM dd, hh:mm a");
        String StartDateStr = postFormater.format(date);
//        eventStartDate.setText(StartDateStr);

        String EndDate=eventhomeRespo.getEndsat();
        SimpleDateFormat endformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        java.util.Date endate = null;
        try
        {
            endate = endformat.parse(EndDate);
        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }
        SimpleDateFormat Formater = new SimpleDateFormat("MMM dd, hh:mm a");
        String endDateStr = Formater.format(endate);
        String Serverdate=StartDateStr+" - "+endDateStr;
        holder.txtdate.setText(Serverdate);
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
//        adapter = new HorizontalContentAdapter(context, eventDetailResponse.getTags());
        adapter = new HorizontalAdapterHome(context, eventhomeRespo.getTags());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
