package com.numnu.android.adapter.search;

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
import com.numnu.android.fragments.detail.EventDetailFragment;
import com.numnu.android.network.response.HomeApiResponse;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by czltd on 1/12/18.
 */

public class HorizontalHomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private int mRowIndex = -1;
    List<HomeApiResponse> mDataList;
    private Context context;
    private StorageReference storageRef ;
    private FirebaseStorage storage;

    public HorizontalHomePageAdapter(Context context) {

        this.context = context;
        this.storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        this.storageRef = storage.getReference();
    }

    public void setData(List<HomeApiResponse> data) {
        if (mDataList != data) {
            mDataList = data;
            notifyDataSetChanged();
        }
    }

    public void setRowIndex(int index) {
        mRowIndex = index;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewIcon;
        private TextView textViewName,textcity,textDate;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.textViewName =  itemView.findViewById(R.id.text_event);
            this.imageViewIcon = itemView.findViewById(R.id.current_event_image);
            this.textcity = itemView.findViewById(R.id.event_item_city);
            this.textDate = itemView.findViewById(R.id.event_item_date);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.current_event_item, parent, false);
        ItemViewHolder holder = new ItemViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder rawHolder, int position) {
        final HomeApiResponse homeRespo = mDataList.get(position);
        final ItemViewHolder holder = (ItemViewHolder) rawHolder;
        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout, EventDetailFragment.newInstance("34"));
                transaction.addToBackStack(null).commit();
            }
        });

//        if(!homeRespo.getItemimages().isEmpty()&&homeRespo.getItemimages().get(0).getImageurl()!=null) {
//            storageRef.child(homeRespo.getItemimages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    // Got the download URL for 'users/me/profile.png'
//                    Picasso.with(context).load(uri)
//                            .placeholder(R.drawable.background)
//                            .fit()
//                            .into(holder.imageViewIcon);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    // Handle any errors
//                }
//            });
//        }
//        holder.textViewName.setText(homeRespo.getName());
//        String StrtDate=homeRespo.getStartsat();
//        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
//        java.util.Date date = null;
//        try
//        {
//            date = form.parse(StrtDate);
//        }
//        catch (ParseException e)
//        {
//
//            e.printStackTrace();
//        }
//        SimpleDateFormat postFormater = new SimpleDateFormat("MMM yyyy");
//        String StartDateStr = postFormater.format(date);
////        eventStartDate.setText(StartDateStr);
//
//        String EndDate=homeRespo.getEndsat();
//        SimpleDateFormat endformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
//        java.util.Date endate = null;
//        try
//        {
//            endate = endformat.parse(EndDate);
//        }
//        catch (ParseException e)
//        {
//
//            e.printStackTrace();
//        }
//        SimpleDateFormat Formater = new SimpleDateFormat("MMM yyyy");
//        String endDateStr = Formater.format(endate);
//        String Serverdate=StartDateStr+" - "+endDateStr;
//        holder.textDate.setText(Serverdate);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

}
