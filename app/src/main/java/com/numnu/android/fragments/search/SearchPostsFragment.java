package com.numnu.android.fragments.search;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.adapter.SearchPostsAdapter;
import com.numnu.android.adapter.eventdetail.EventPostsAdapter;
import com.numnu.android.adapter.search.SearchHomePostAdapter;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.EventPostsResponse;
import com.numnu.android.network.response.HomePostResponse;
import com.numnu.android.network.response.HomepostResp;
import com.numnu.android.network.response.LocationHomePost;
import com.numnu.android.network.response.LocationObject;
import com.numnu.android.network.response.PostdataItem;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

/**
 * Created by thulir on 9/10/17.
 */

public class SearchPostsFragment extends Fragment {

    private RecyclerView menuitemsRecyclerView;
    Context context;
    ImageView dotsimg;
    private boolean isLoading=false;
    private boolean isLastPage=false;
    private int PAGE_SIZE = 20;
    private int nextPage = 1;
    private AlertDialog dialog;
    SearchHomePostAdapter currentUpAdapter;
    HomePostResponse homepostresponse;

    public static SearchPostsFragment newInstance() {
        SearchPostsFragment fragment = new SearchPostsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        menuitemsRecyclerView = view.findViewById(R.id.reviews_recyclerview);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        menuitemsRecyclerView.setLayoutManager(layoutManager);
        menuitemsRecyclerView.setNestedScrollingEnabled(false);
//    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);
        if(Utils.isNetworkAvailable(context)) {
            getHomeposts();
        }else {
            showAlert();
        }
        // Pagination
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
                        loadMoreItems();
                    }
                }
            }
        });
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(menuitemsRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
//        menuitemsRecyclerView.addItemDecoration(dividerItemDecoration);
//        setupRecyclerView();

        return view;
    }

    private void showAlert() {
    }
    private void getHomeposts()
    {
        LocationObject citylocation = new LocationObject();
        citylocation.setLattitude(13.625475);
        citylocation.setLongitude(77.111111);
        citylocation.setNearMeRadiusInMiles(13900);
        LocationHomePost locationhomepost=new LocationHomePost();
        locationhomepost.setClientapp(Constants.CLIENT_APP);
        locationhomepost.setClientip(Utils.getLocalIpAddress(context));
        locationhomepost.setLocationObject(citylocation);
        locationhomepost.setSearchText("c");
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<HomePostResponse> call=apiServices.gethomeposts(locationhomepost);
        call.enqueue(new Callback<HomePostResponse>() {
            @Override
            public void onResponse(Call<HomePostResponse> call, Response<HomePostResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    homepostresponse = response.body();
                    updateUI();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<HomePostResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });

    }

    private void loadMoreItems()
    {
        LocationObject citylocation = new LocationObject();
        citylocation.setLattitude(13.625475);
        citylocation.setLongitude(77.111111);
        citylocation.setNearMeRadiusInMiles(13900);
        LocationHomePost locationhomepost=new LocationHomePost();
        locationhomepost.setClientapp(Constants.CLIENT_APP);
        locationhomepost.setClientip(Utils.getLocalIpAddress(context));
        locationhomepost.setLocationObject(citylocation);
        locationhomepost.setSearchText("c");
        nextPage += 1;
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<HomePostResponse> call=apiServices.gethomeposts(String.valueOf(nextPage),locationhomepost);
        call.enqueue(new Callback<HomePostResponse>() {
            @Override
            public void onResponse(Call<HomePostResponse> call, Response<HomePostResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    List<HomepostResp> postItems=response.body().getData();
                    if(!response.body().getPagination().isHasMore()){
                        isLastPage = true;
                    }
                    currentUpAdapter.addData(postItems);
                    currentUpAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<HomePostResponse> call, Throwable t) {
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

        currentUpAdapter = new SearchHomePostAdapter(context, homepostresponse.getData());
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

//    private void setupRecyclerView() {
//        ArrayList<String> stringlist = new ArrayList<>();
//
//        for (int i = 1; i <= 10; i++) {
//            stringlist.add("Post item " + i);
//
//            SearchPostsAdapter currentUpAdapter = new SearchPostsAdapter(context, stringlist);
//            menuitemsRecyclerView.setAdapter(currentUpAdapter);
//        }
//
//    }

}
