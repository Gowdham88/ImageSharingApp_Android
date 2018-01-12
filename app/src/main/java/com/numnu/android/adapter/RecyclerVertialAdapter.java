package com.numnu.android.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.numnu.android.R;
import com.numnu.android.adapter.search.EventsWithToolbarAdapter;
import com.numnu.android.fragments.search.EventsFragmentwithToolbar;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.HomeApiResponse;
import com.numnu.android.network.response.HomeResponse;
import com.numnu.android.network.response.LocationHomePost;
import com.numnu.android.network.response.LocationObject;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.PreferencesHelper;
import com.numnu.android.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by czsm4 on 11/01/18.
 */

public class RecyclerVertialAdapter extends RecyclerView.Adapter<RecyclerVertialAdapter.ViewHolder> {

    private Context context;

    private List<HomeResponse> stringArrayList = new ArrayList<>();
    private StorageReference storageRef;
    private FirebaseStorage storage;
    RecyclerView currentEventList;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int PAGE_SIZE = 20;
    private int nextPage = 1;
    private android.support.v7.app.AlertDialog dialog;
    List<HomeResponse> Homeresponses;
    private ArrayList<String> stringlist;
    private Double lat, lng;
    CurrentUpEventsAdapter currUpAdapter;
    String Titlearray[] = {"Flatron", "Masuike", "Umami Burger", "Ada Salad"};
    LinearLayoutManager layoutManager;
    List<HomeResponse> relist = new ArrayList<>();
    //private ArrayList<String> stringArrayList = new ArrayList<>();

    public RecyclerVertialAdapter(Context context, List<HomeResponse> homelist) {
        this.context = context;
        this.stringArrayList = homelist;
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
        lat = Double.valueOf(PreferencesHelper.getPreference(context, PreferencesHelper.PREFERENCE_SEARCH_LATITUDE));
        lng = Double.valueOf(PreferencesHelper.getPreference(context, PreferencesHelper.PREFERENCE_SEARCH_LONGITUDE));

        //view.setOnClickListener(MainActivity.myOnClickListener);
//        lat = Double.valueOf(PreferencesHelper.getPreference(context, PreferencesHelper.PREFERENCE_SEARCH_LATITUDE));
//        lng = Double.valueOf(PreferencesHelper.getPreference(context, PreferencesHelper.PREFERENCE_SEARCH_LONGITUDE));
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewIcon;
        private TextView curtextViewName, textcity, textDate;
        private RecyclerView currentEventList;

        ViewHolder(View itemView) {
            super(itemView);
            this.curtextViewName = itemView.findViewById(R.id.past_events_text);
            this.imageViewIcon = itemView.findViewById(R.id.view_past_event_list);
            this.currentEventList = itemView.findViewById(R.id.past_recyclerview);

            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
//            this.imageViewIcon = itemView.findViewById(R.id.current_event_image);
//            this.textcity = itemView.findViewById(R.id.event_item_city);
//            this.textDate = itemView.findViewById(R.id.event_item_date);


        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.curtextViewName.setText(stringArrayList.get(position).getListTitle());
        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction =((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventsFragmentwithToolbar.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//        holder.currentEventList.setLayoutManager(layoutManager);
//        holder.currentEventList.setNestedScrollingEnabled(false);
//        setupRecyclerView();
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }
//    private void setupRecyclerView() {
//        stringlist = new ArrayList<>();
//
//        for (int i = 1; i <= 10; i++) {
//            stringlist.add("Item " + i);
//        }
//
//        recycler currentUpAdapter = new recycler(context, stringlist);
//        currentEventList.setAdapter(currentUpAdapter);
//
//    }


}
