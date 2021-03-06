package com.numnu.android.adapter.search;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import com.numnu.android.fragments.detail.SearchBusinessDetailFragment;
import com.numnu.android.fragments.eventdetail.EventBusinessDetailFragment;
import com.numnu.android.network.response.HBussresp;
import com.numnu.android.network.response.HomeItemRes;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thulir on 10/10/17.
 */

public class SearchBusinessAdapter extends RecyclerView.Adapter<SearchBusinessAdapter.ViewHolder> {

    Context context;
    ArrayList<String> stringArrayList = new ArrayList<>();
    List<HBussresp> listbuss= new ArrayList<>();
    private StorageReference storageRef ;
    private FirebaseStorage storage;
    HorizontalAdapterHome adapter;
    RecyclerView recyclerView;


    public SearchBusinessAdapter(Context context, List<HBussresp> stringArrayList) {
        this.context=context;
        this.listbuss=stringArrayList;
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();
    }
    public  void addData(List<HBussresp> stringArrayList){
        listbuss.addAll(stringArrayList);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_business_item, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HBussresp homebusRespo = listbuss.get(position);
        holder.textViewName.setText(listbuss.get(position).getBusinessname());
//        String StrtDate=homebusRespo.;
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
//        SimpleDateFormat postFormater = new SimpleDateFormat("MMM dd, hh:mm a");
//        String StartDateStr = postFormater.format(date);
////        eventStartDate.setText(StartDateStr);
//
//        String EndDate=homebusRespo.getEndsat();
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
//        SimpleDateFormat Formater = new SimpleDateFormat("MMM dd, hh:mm a");
//        String endDateStr = Formater.format(endate);
//        String Serverdate=StartDateStr+" - "+endDateStr;
//        holder.txtdate.setText(Serverdate);
        Picasso.with(context).load(R.drawable.sasitem)
                .placeholder(R.drawable.background)
                .fit()
                .into(holder.imageViewIcon);
        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout, EventBusinessDetailFragment.newInstance(String.valueOf(homebusRespo.getId())));
                transaction.addToBackStack(null).commit();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout, EventBusinessDetailFragment.newInstance(String.valueOf(homebusRespo.getId())));
                transaction.addToBackStack(null).commit();
            }
        });

        adapter = new HorizontalAdapterHome(context, homebusRespo.getTags());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public int getItemCount() {
        return listbuss.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  ImageView imageViewIcon;
        private TextView textViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName =  itemView.findViewById(R.id.business_name);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.business_recyclerview);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = itemView.findViewById(R.id.notification_image);
        }
    }
}
