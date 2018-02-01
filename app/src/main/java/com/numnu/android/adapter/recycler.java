package com.numnu.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.numnu.android.R;
import com.numnu.android.network.response.HomeResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czsm4 on 11/01/18.
 */

public class recycler extends RecyclerView.Adapter<recycler.ViewHolder> {

    private Context context;

    private ArrayList<String> stringArrayList = new ArrayList<>();
    private StorageReference storageRef ;
    private FirebaseStorage storage;
    RecyclerView currentEventList;
    private boolean isLoading=false;
    private boolean isLastPage=false;
    private int PAGE_SIZE = 20;
    private int nextPage = 1;
    private android.support.v7.app.AlertDialog dialog;
    List<HomeResponse> Homeresponses;
    private ArrayList<String> stringlist;
    private Double lat,lng;
    CurrentUpEventsAdapter currUpAdapter;
    String Titlearray[]={"Flatron","Masuike","Umami Burger","Ada Salad"};
    //private ArrayList<String> stringArrayList = new ArrayList<>();

    public recycler(Context context, ArrayList<String> stringlist) {
        this.context=context;
        this.stringArrayList=stringArrayList;
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();
//        this.stringArrayList=stringArrayList;
    }
//    public  void addData(List<HomeResponse> stringArrayList){
//        list.addAll(stringArrayList);
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyvertical_content, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);
//        lat = Double.valueOf(PreferencesHelper.getPreference(context, PreferencesHelper.PREFERENCE_SEARCH_LATITUDE));
//        lng = Double.valueOf(PreferencesHelper.getPreference(context, PreferencesHelper.PREFERENCE_SEARCH_LONGITUDE));
        return new ViewHolder(view);
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
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.textViewName.setText(Titlearray[position]);

//        if(Utils.isNetworkAvailable(context)) {
//            gethomeData();
//        }else {
//            showAlert();
//        }
//        currentEventList.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//
//                if (!isLoading && !isLastPage) {
//                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
//                            && firstVisibleItemPosition >= 0
//                            && totalItemCount >= PAGE_SIZE) {
////                        loadMoreItems();
//                    }
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }



}