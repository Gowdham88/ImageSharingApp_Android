package com.numnu.android.fragments.search;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.numnu.android.R;
import com.numnu.android.adapter.UsersAdapter;
import com.numnu.android.adapter.search.SearchItemsListAdapter;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.HomeItemRes;
import com.numnu.android.network.response.HomeItemResponse;
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


public class SearchItemsFragment extends Fragment {

    private RecyclerView menuitemsRecyclerView;
    private Context context;
    private boolean isLoading=false;
    private boolean isLastPage=false;
    private int PAGE_SIZE = 20;
    private int nextPage = 1;
    SearchItemsListAdapter currentUpAdapter;
    HomeItemResponse homeitemResp;
    private android.support.v7.app.AlertDialog dialog;
    public static SearchItemsFragment newInstance() {
        return new SearchItemsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    View  view= inflater.inflate(R.layout.fragment_search_category_items, container, false);

    menuitemsRecyclerView = view.findViewById(R.id.menu_items_recyclerview);
//    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(menuitemsRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
//        menuitemsRecyclerView.addItemDecoration(dividerItemDecoration);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        menuitemsRecyclerView.setLayoutManager(layoutManager);
        menuitemsRecyclerView.setNestedScrollingEnabled(false);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(usersRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
//        usersRecyclerView.addItemDecoration(dividerItemDecoration);
        if(Utils.isNetworkAvailable(context)) {
//            getitemhomeData();
        }else {
            showAlert();


        }
        menuitemsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
//                        loadMoreItems();
                    }
                }
            }
        });
//    setupRecyclerView();
        return view;

}

    private void showAlert() {
    }
//    private void getitemhomeData()
//    {
//        showProgressDialog();
//        LocationObject citylocation = new LocationObject();
//        citylocation.setLattitude(13.625475);
//        citylocation.setLongitude(77.111111);
//        citylocation.setNearMeRadiusInMiles(14000);
//        LocationHomePost locationhomepost=new LocationHomePost();
//        locationhomepost.setClientapp(Constants.CLIENT_APP);
//        locationhomepost.setClientip(Utils.getLocalIpAddress(context));
//        locationhomepost.setLocationObject(citylocation);
//        locationhomepost.setSearchText("b");
//        isLoading = true;
//        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
//        Call<HomeItemResponse> call=apiServices.gethomeitems(locationhomepost);
//        call.enqueue(new Callback<HomeItemResponse>() {
//            @Override
//            public void onResponse(Call<HomeItemResponse> call, Response<HomeItemResponse> response) {
//                int responsecode = response.code();
//                if(responsecode==200) {
////                    homeitemResp=new HomeItemResponse();
//                    homeitemResp = response.body();
//                    updateUI();
//                    isLoading = false;
//                    hideProgressDialog();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<HomeItemResponse> call, Throwable t) {
//                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
//                isLoading = false;
//
//            }
//        });
//
//    }
//    private void loadMoreItems() {
//        LocationObject citylocation = new LocationObject();
//        citylocation.setLattitude(13.625475);
//        citylocation.setLongitude(77.111111);
//        citylocation.setNearMeRadiusInMiles(14000);
//        LocationHomePost locationhomepost=new LocationHomePost();
//        locationhomepost.setClientapp(Constants.CLIENT_APP);
//        locationhomepost.setClientip(Utils.getLocalIpAddress(context));
//        locationhomepost.setLocationObject(citylocation);
//        locationhomepost.setSearchText("b");
//        nextPage += 1;
//        isLoading = true;
//        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
//        Call<HomeItemResponse> call = apiServices.gethomeitems(String.valueOf(nextPage),locationhomepost);
//        call.enqueue(new Callback<HomeItemResponse>() {
//            @Override
//            public void onResponse(Call<HomeItemResponse> call, Response<HomeItemResponse> response) {
//                int responsecode = response.code();
//                if (responsecode == 200) {
//                    List<HomeItemRes> Items = response.body().getData();
//                    if (!response.body().getPagination().isHasMore()) {
//                        isLastPage = true;
//                    }
//                    currentUpAdapter.addData(Items);
//                    currentUpAdapter.notifyDataSetChanged();
//                    isLoading = false;
//                }
//            }
//
//            @Override
//            public void onFailure(Call<HomeItemResponse> call, Throwable t) {
//                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
//                isLoading = false;
//            }
//        });
//    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    private void updateUI() {

        currentUpAdapter = new SearchItemsListAdapter(context,homeitemResp.getData());
        menuitemsRecyclerView.setAdapter(currentUpAdapter);
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

