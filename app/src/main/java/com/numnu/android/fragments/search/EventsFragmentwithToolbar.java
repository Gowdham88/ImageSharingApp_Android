package com.numnu.android.fragments.search;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.adapter.RecyclerVertialAdapter;
import com.numnu.android.adapter.search.EventsWithToolbarAdapter;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.DataItem;
import com.numnu.android.network.response.EventBusinessesResponse;
import com.numnu.android.network.response.HomeApiResponse;
import com.numnu.android.network.response.HomeResponse;
import com.numnu.android.network.response.LocationHomePost;
import com.numnu.android.network.response.LocationObject;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by czsm4 on 16/11/17.
 */

public class EventsFragmentwithToolbar extends Fragment {

    private RecyclerView searchEventsList;
    private ArrayList<String> stringlist;
    Context context;
    Toolbar toolbar;
    RelativeLayout toolbackimg;
    private boolean isLoading=false;
    private boolean isLastPage=false;
    private int PAGE_SIZE = 20;
    private int nextPage = 1;
    HomeResponse hmresponse;
    EventsWithToolbarAdapter evntAdapter;
    int posit;
    String str = "0";
    private android.support.v7.app.AlertDialog dialog;

    public static EventsFragmentwithToolbar newInstance() {
        EventsFragmentwithToolbar fragment = new EventsFragmentwithToolbar();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_withtoolbar_events, container, false);
        searchEventsList = view.findViewById(R.id.search_recyclerview);
        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.events);
        toolbackimg=(RelativeLayout)view.findViewById(R.id.toolbar_back);
        toolbackimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
//        setupRecyclerView();

        final android.support.v7.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchEventsList.scrollToPosition(0);
            }
        });
    Bundle bun=new Bundle();
    if(bun!=null){
        int myvalue=bun.getInt("position",posit);
        str= String.valueOf(myvalue);
    }

        final LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        searchEventsList.setLayoutManager(layoutManager);
        searchEventsList.setNestedScrollingEnabled(false);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(businessRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
//        businessRecyclerView.addItemDecoration(dividerItemDecoration);
        if(Utils.isNetworkAvailable(context)) {
            gethomeevent();
        }else {
            showAlert();
        }
        // Pagination
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
        return view;

    }



    private void showAlert() {
//        AlertDialog.Builder builder=new AlertDialog.Builder(context);
//        builder.setMessage("No Internet connection");
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        builder.create().show();
    }


    private void  gethomeevent()
    {
        showProgressDialog();
        LocationObject citylocation = new LocationObject();
        citylocation.setLattitude(13.0312186);
        citylocation.setLongitude(77.0312186);
        citylocation.setNearMeRadiusInMiles(14000);
        LocationHomePost locationhomepost=new LocationHomePost();
        locationhomepost.setClientapp(Constants.CLIENT_APP);
        locationhomepost.setClientip(Utils.getLocalIpAddress(context));
        locationhomepost.setLocationObject(citylocation);
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<HomeResponse> call=apiServices.gethomeevntresp(String.valueOf(str),locationhomepost);
        call.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    hmresponse = response.body();
                    updateUI();
                    isLoading = false;
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });

    }



    private void loadMoreItems() {
        LocationObject citylocation = new LocationObject();
        citylocation.setLattitude(13.0312186);
        citylocation.setLongitude(77.0312186);
        citylocation.setNearMeRadiusInMiles(14000);
        LocationHomePost locationhomepost=new LocationHomePost();
        locationhomepost.setClientapp(Constants.CLIENT_APP);
        locationhomepost.setClientip(Utils.getLocalIpAddress(context));
        locationhomepost.setLocationObject(citylocation);
        nextPage += 1;
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<HomeResponse> call = apiServices.gethomeevntresp(str,String.valueOf(nextPage),locationhomepost);
        call.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                int responsecode = response.code();
                if (responsecode == 200) {
                    List<HomeApiResponse> dataItems = response.body().getData();
                    if (!response.body().getPagination().isHasMore()) {
                        isLastPage = true;
                    }
                    evntAdapter.addData(dataItems);
                    evntAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
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
        evntAdapter = new  EventsWithToolbarAdapter(context,hmresponse.getData());
        searchEventsList.setAdapter(evntAdapter);
        evntAdapter.notifyDataSetChanged();
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

//    private void setupRecyclerView() {
//        stringlist = new ArrayList<>();
//
//        for (int i = 1; i <= 10; i++) {
//            stringlist.add("Item " + i);
//        }
//
//        EventsWithToolbarAdapter currentUpAdapter = new EventsWithToolbarAdapter(context, stringlist);
//        searchEventsList.setAdapter(currentUpAdapter);
//
//    }
//

}