package com.numnu.android.fragments.search;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.adapter.UsersAdapter;
import com.numnu.android.adapter.search.SearchEventsAdapter;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.DataItem;
import com.numnu.android.network.response.EventBusinessesResponse;
import com.numnu.android.network.response.HomeUserresponse;
import com.numnu.android.network.response.Homeuserresp;
import com.numnu.android.network.response.LocationHomePost;
import com.numnu.android.network.response.LocationObject;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UsersFragment extends Fragment {
    private RecyclerView usersRecyclerView;
    Context context;
    private boolean isLoading=false;
    private boolean isLastPage=false;
    private int PAGE_SIZE = 20;
    private int nextPage = 1;
    UsersAdapter usersAdapter;
    HomeUserresponse userhomeresponse;


    public static UsersFragment newInstance() {
        UsersFragment fragment = new UsersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        usersRecyclerView = view.findViewById(R.id.users_recycler);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        usersRecyclerView.setLayoutManager(layoutManager);
        usersRecyclerView.setNestedScrollingEnabled(false);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(usersRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
//        usersRecyclerView.addItemDecoration(dividerItemDecoration);
        if(Utils.isNetworkAvailable(context)) {
            getuserhomeData();
        }else {
            showAlert();
        }
        usersRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    private void getuserhomeData()
    {
//        LocationObject citylocation = new LocationObject();
//        citylocation.setLattitude(13.625475);
//        citylocation.setLongitude(77.111111);
//        citylocation.setNearMeRadiusInMiles(14000);
//        LocationHomePost locationhomepost=new LocationHomePost();
//        locationhomepost.setClientapp(Constants.CLIENT_APP);
//        locationhomepost.setClientip(Utils.getLocalIpAddress(context));
//        locationhomepost.setLocationObject(citylocation);
//        locationhomepost.setSearchText("burger");
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<HomeUserresponse> call=apiServices.gethomeuser();
        call.enqueue(new Callback<HomeUserresponse>() {
            @Override
            public void onResponse(Call<HomeUserresponse> call, Response<HomeUserresponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    userhomeresponse = response.body();
                    updateUI();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<HomeUserresponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });

    }
    private void loadMoreItems() {
        LocationObject citylocation = new LocationObject();
        citylocation.setLattitude(13.625475);
        citylocation.setLongitude(77.111111);
        citylocation.setNearMeRadiusInMiles(14000);
        LocationHomePost locationhomepost=new LocationHomePost();
        locationhomepost.setClientapp(Constants.CLIENT_APP);
        locationhomepost.setClientip(Utils.getLocalIpAddress(context));
        locationhomepost.setLocationObject(citylocation);
        locationhomepost.setSearchText("burger");
        nextPage += 1;
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<HomeUserresponse> call = apiServices.gethomeuser(String.valueOf(nextPage));
        call.enqueue(new Callback<HomeUserresponse>() {
            @Override
            public void onResponse(Call<HomeUserresponse> call, Response<HomeUserresponse> response) {
                int responsecode = response.code();
                if (responsecode == 200) {
                    List<Homeuserresp> dataItems = response.body().getData();
                    if (!response.body().getPagination().isHasMore()) {
                        isLastPage = true;
                    }
                    usersAdapter.addData(dataItems);
                    usersAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<HomeUserresponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });
    }
        @Override
        public void onAttach (Context context){
            super.onAttach(context);
            this.context = context;
        }

    private void updateUI() {

        usersAdapter = new UsersAdapter(context,userhomeresponse.getData());
        usersRecyclerView.setAdapter(usersAdapter);
        usersAdapter.notifyDataSetChanged();
    }
//    private void setupRecyclerView() {
//        ArrayList<String> stringlist = new ArrayList<>();
//
//        for (int i = 1; i <= 10; i++) {
//            stringlist.add("Users Item " + i);
//
//            UsersAdapter usersAdapter = new UsersAdapter(context, stringlist);
//            usersRecyclerView.setAdapter(usersAdapter);
//        }
//
//    }
}

