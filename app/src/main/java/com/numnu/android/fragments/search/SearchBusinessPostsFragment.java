package com.numnu.android.fragments.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.adapter.businessdetail.BusinessPostsAdapter;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.EventPostsResponse;
import com.numnu.android.network.response.PostdataItem;
import com.numnu.android.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thulir on 9/10/17.
 */

public class SearchBusinessPostsFragment extends Fragment {

    private  String businessId;
    private RecyclerView recyclerView;
    Context context;
    EventPostsResponse eventPostsResponse;
    private BusinessPostsAdapter businessPostsAdapter;
    private boolean isLoading=false;
    private boolean isLastPage=false;
    private int PAGE_SIZE = 20;
    private int nextPage = 1;

    public static SearchBusinessPostsFragment newInstance( String businessId) {
        SearchBusinessPostsFragment eventBusinessFragment = new SearchBusinessPostsFragment();
        Bundle args = new Bundle();
        args.putString("businessId", businessId);
        eventBusinessFragment.setArguments(args);

        return eventBusinessFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            businessId = bundle.getString("businessId");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    View  view= inflater.inflate(R.layout.fragment_event_reviews, container, false);

    recyclerView = view.findViewById(R.id.reviews_recyclerview);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
//    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);
        if(Utils.isNetworkAvailable(context)) {
            getData();
        }else {
            showAlert();
        }
        // Pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        loadMoreItems(businessId);
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


    private void getData()
    {
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<EventPostsResponse> call=apiServices.getBusinessPosts(businessId);
        call.enqueue(new Callback<EventPostsResponse>() {
            @Override
            public void onResponse(Call<EventPostsResponse> call, Response<EventPostsResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    eventPostsResponse = response.body();
                    updateUI();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<EventPostsResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });

    }

    private void loadMoreItems(String id)
    {
        nextPage += 1;
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<EventPostsResponse> call=apiServices.getBusinessPosts(businessId, String.valueOf(nextPage));
        call.enqueue(new Callback<EventPostsResponse>() {
            @Override
            public void onResponse(Call<EventPostsResponse> call, Response<EventPostsResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    List<PostdataItem> dataItems=response.body().getPostdata();
                    if(!response.body().getPagination().isHasMore()){
                        isLastPage = true;
                    }
                    businessPostsAdapter.addData(dataItems);
                    businessPostsAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<EventPostsResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });

    }

    private void updateUI() {

        businessPostsAdapter = new BusinessPostsAdapter(context, eventPostsResponse.getPostdata());
        recyclerView.setAdapter(businessPostsAdapter);
        businessPostsAdapter.notifyDataSetChanged();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

}

