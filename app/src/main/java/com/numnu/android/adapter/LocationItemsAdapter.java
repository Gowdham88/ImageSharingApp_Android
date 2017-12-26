package com.numnu.android.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import com.numnu.android.fragments.detail.LocationDetailFragment;
import com.numnu.android.network.response.Datum;
import com.numnu.android.network.response.Location;
import com.numnu.android.network.response.PostdataItem;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thulir on 10/10/17.
 */

public class LocationItemsAdapter extends RecyclerView.Adapter<LocationItemsAdapter.ViewHolder> {

    Context context;
    List<Datum> stringArrayList = new ArrayList<>();
    private StorageReference storageRef ;
    private FirebaseStorage storage;
    String Createdate;
    String StartDateStr;

    public LocationItemsAdapter(Context context, List<Datum> stringArrayList) {
        this.context=context;
        this.stringArrayList=stringArrayList;
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();
    }
    public  void addData(List<Datum> stringArrayList){
        stringArrayList.addAll(stringArrayList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_item, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private  ImageView imageViewIcon;
        private TextView textViewName,crdate;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName =  itemView.findViewById(R.id.menu_item_name);
            this.crdate =  itemView.findViewById(R.id.event_date);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = itemView.findViewById(R.id.notification_image);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

//        Picasso.with(context).load(R.drawable.sasitem)
//                .placeholder(R.drawable.background)
//                .fit()
//                .into(holder.imageViewIcon);
        final Datum Locationdata = stringArrayList.get(position);
        if(!Locationdata.getLocationimages().isEmpty()&&Locationdata.getLocationimages().get(0).getImageurl()!=null) {
            storageRef.child(Locationdata.getLocationimages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
        holder.textViewName.setText(Locationdata.getName());
//         Createdate= Locationdata.getCreatedat();
//         String str= String.valueOf(Createdate);
//        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
//        java.util.Date date = null;
//        try
//        {
//            date = form.parse(str);
//        }
//        catch (ParseException e)
//        {
//
//            e.printStackTrace();
//        }
//        SimpleDateFormat postFormater = new SimpleDateFormat("MMM dd");
//         StartDateStr = postFormater.format(date);
        holder.crdate.setText(Locationdata.getCreatedat());
//        holder.date.setText(Locationdata.);
        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, LocationDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, LocationDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        holder.crdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, LocationDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }


}
