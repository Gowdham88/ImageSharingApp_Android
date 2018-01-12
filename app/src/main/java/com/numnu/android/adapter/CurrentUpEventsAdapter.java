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
import com.numnu.android.fragments.detail.EventDetailFragment;
import com.numnu.android.network.response.HomeApiResponse;
import com.numnu.android.network.response.HomeEvebtResp;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thulir on 10/10/17.
 */

public class CurrentUpEventsAdapter extends RecyclerView.Adapter<CurrentUpEventsAdapter.ViewHolder> {

    private Context context;
    List<HomeApiResponse> list;
    private StorageReference storageRef ;
    private FirebaseStorage storage;
    //private ArrayList<String> stringArrayList = new ArrayList<>();
    int imgarray[] = {R.drawable.pasta,R.drawable.burger,R.drawable.large_berger,R.drawable.sasitem };
    String Titlearray[]={"Flatron","Masuike","Umami Burger","Ada Salad"};
    String TitleCityarray[]={"Montreal","Miami","New Orleans","Miami"};

    public CurrentUpEventsAdapter(Context context,List<HomeApiResponse> stringArrayList) {
        this.context=context;
        this.list =stringArrayList;
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();
//        this.stringArrayList=stringArrayList;
    }
    public  void addData(List<HomeApiResponse> stringArrayList){
        list.addAll(stringArrayList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.current_event_item, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final HomeApiResponse homeRespo = list.get(position);

        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout, EventDetailFragment.newInstance("34"));
                transaction.addToBackStack(null).commit();
            }
        });

        if(!homeRespo.getItemimages().isEmpty()&&homeRespo.getItemimages().get(0).getImageurl()!=null) {
            storageRef.child(homeRespo.getItemimages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
        holder.textViewName.setText(homeRespo.getName());
        String StrtDate=homeRespo.getStartsat();
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
        SimpleDateFormat postFormater = new SimpleDateFormat("MMM yyyy");
        String StartDateStr = postFormater.format(date);
//        eventStartDate.setText(StartDateStr);

        String EndDate=homeRespo.getEndsat();
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
        SimpleDateFormat Formater = new SimpleDateFormat("MMM yyyy");
        String endDateStr = Formater.format(endate);
        String Serverdate=StartDateStr+" - "+endDateStr;
        holder.textDate.setText(Serverdate);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private  ImageView imageViewIcon;
        private TextView textViewName,textcity,textDate;

        ViewHolder(View itemView) {
            super(itemView);
            this.textViewName =  itemView.findViewById(R.id.text_event);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = itemView.findViewById(R.id.current_event_image);
            this.textcity = itemView.findViewById(R.id.event_item_city);
            this.textDate = itemView.findViewById(R.id.event_item_date);


        }
    }
}
