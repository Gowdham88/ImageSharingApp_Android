package com.numnu.android.fragments.search;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.numnu.android.R;
import com.numnu.android.adapter.eventdetail.EventBusinessesAdapter;
import com.numnu.android.adapter.search.SearchEventsAdapter;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.request.Citylocation;
import com.numnu.android.network.response.DataItem;
import com.numnu.android.network.response.EventBusinessesResponse;
import com.numnu.android.network.response.HomeEvebtResp;
import com.numnu.android.network.response.HomeEventResponse;
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
 * Created by thulir on 9/10/17.
 */

public class EventsFragment extends Fragment {

    private RecyclerView searchEventsList;
    private ArrayList<String> stringlist;
    Context context;
    HomeEventResponse eventhomeResponse;
    private boolean isLoading=false;
    private boolean isLastPage=false;
    private int PAGE_SIZE = 20;
    private int nextPage = 1;
    SearchEventsAdapter currentUpAdapter;
    private android.support.v7.app.AlertDialog dialog;
    private String keyword;
    private Double lat,lng;

    public static EventsFragment newInstance(String keyword) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString("keyword", keyword);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            keyword = bundle.getString("keyword");
        }

         lat = Double.valueOf(PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_LATITUDE));
         lng = Double.valueOf(PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_LONGITUDE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);
        searchEventsList = view.findViewById(R.id.search_recyclerview);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        searchEventsList.setLayoutManager(layoutManager);
        searchEventsList.setNestedScrollingEnabled(false);
        if(Utils.isNetworkAvailable(context)) {
            geteventhomeData();
        }else {
            showAlert();
        }
        searchEventsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        loadMoreItems();
                    }
                }
            }
        });
//        setupRecyclerView();

        return view;
    }

    private void showAlert() {
    }
    private void geteventhomeData()
    {
        showProgressDialog();

        LocationObject citylocation = new LocationObject();
        citylocation.setLattitude(lat);
        citylocation.setLongitude(lng);
        citylocation.setNearMeRadiusInMiles(14000);
        LocationHomePost locationhomepost=new LocationHomePost();
        locationhomepost.setClientapp(Constants.CLIENT_APP);
        locationhomepost.setClientip(Utils.getLocalIpAddress(context));
        locationhomepost.setLocationObject(citylocation);
        locationhomepost.setSearchText(keyword);
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<HomeEventResponse> call=apiServices.gethomeevents(locationhomepost);
        call.enqueue(new Callback<HomeEventResponse>() {
            @Override
            public void onResponse(Call<HomeEventResponse> call, Response<HomeEventResponse> response) {
                int responsecode = response.code();
                Log.e("userString", new Gson().toJson(response.body()));
                if(responsecode==200) {
                    eventhomeResponse = response.body();
                    updateUI();
                    isLoading = false;
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<HomeEventResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;

            }
        });

    }
    private void loadMoreItems()
    {
        LocationObject citylocation = new LocationObject();
        citylocation.setLattitude(lat);
        citylocation.setLongitude(lng);
        citylocation.setNearMeRadiusInMiles(14000);
        LocationHomePost locationhomepost=new LocationHomePost();
        locationhomepost.setClientapp(Constants.CLIENT_APP);
        locationhomepost.setClientip(Utils.getLocalIpAddress(context));
        locationhomepost.setLocationObject(citylocation);
        locationhomepost.setSearchText(keyword);
        nextPage += 1;
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<HomeEventResponse> call=apiServices.gethomeevents(String.valueOf(nextPage),locationhomepost);
        call.enqueue(new Callback<HomeEventResponse>() {
            @Override
            public void onResponse(Call<HomeEventResponse> call, Response<HomeEventResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    List<HomeEvebtResp> dataItems=response.body().getData();
                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                    Log.e("data", String.valueOf(response.body().getData()));
                    if(!response.body().getPagination().isHasMore()){
                        isLastPage = true;
                    }
                    currentUpAdapter.addData(dataItems);
                    currentUpAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<HomeEventResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    private void updateUI() {

        currentUpAdapter = new SearchEventsAdapter(context,eventhomeResponse.getData());
        searchEventsList.setAdapter(currentUpAdapter);
        currentUpAdapter.notifyDataSetChanged();
    }
    public void showProgressDialog() {


        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(getActivity());
        //View view = getLayoutInflater().inflate(R.layout.progress);
        alertDialog.setView(R.layout.progress);
        dialog = alertDialog.create();
        dialog.show();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void hideProgressDialog(){
        if(dialog!=null)
            dialog.dismiss();
    }


}

