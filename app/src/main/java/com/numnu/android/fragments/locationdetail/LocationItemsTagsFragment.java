package com.numnu.android.fragments.locationdetail;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.adapter.eventdetail.EventItemsCategoryAdapter;
import com.numnu.android.adapter.locationdetail.LocationItemsTagsAdapter;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.EventItemsResponse;
import com.numnu.android.network.response.EventTagsDataItem;
import com.numnu.android.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thulir on 9/10/17.
 */

public class LocationItemsTagsFragment extends Fragment {

    private RecyclerView menuitemsRecyclerView;
    private String locationId;
    private Context context;
    private boolean isLoading=false;
    private boolean isLastPage=false;
    private int PAGE_SIZE = 20;
    private int nextPage = 1;
    private LocationItemsTagsAdapter eventItemsCategoryAdapter;
    private EventItemsResponse eventItemsResponse;
    public ProgressDialog mProgressDialog;

    public static LocationItemsTagsFragment newInstance(String locationId) {

        LocationItemsTagsFragment eventBusinessFragment = new LocationItemsTagsFragment();
        Bundle args = new Bundle();
        args.putString("locationId", locationId);
        eventBusinessFragment.setArguments(args);

        return eventBusinessFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            locationId = bundle.getString("locationId");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    View  view= inflater.inflate(R.layout.fragment_event_category_items, container, false);

    menuitemsRecyclerView = view.findViewById(R.id.menu_items_recyclerview);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        menuitemsRecyclerView.setLayoutManager(layoutManager);
        menuitemsRecyclerView.setNestedScrollingEnabled(false);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(menuitemsRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        menuitemsRecyclerView.addItemDecoration(dividerItemDecoration);

        if(Utils.isNetworkAvailable(context)) {
            getItems("179");
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
                        loadMoreItems(locationId);
                    }
                }
            }
        });
        return view;

}

    private void showAlert() {
    }


    private void getItems(String id)
    {


        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<EventItemsResponse> call=apiServices.getLocationItemTags(id);
        call.enqueue(new Callback<EventItemsResponse>() {
            @Override
            public void onResponse(Call<EventItemsResponse> call, Response<EventItemsResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    eventItemsResponse = response.body();
                    updateUI();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<EventItemsResponse> call, Throwable t) {
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
        Call<EventItemsResponse> call=apiServices.getLocationItemTags(id, String.valueOf(nextPage));
        call.enqueue(new Callback<EventItemsResponse>() {
            @Override
            public void onResponse(Call<EventItemsResponse> call, Response<EventItemsResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    List<EventTagsDataItem> dataItems=response.body().getData();
                    if(!response.body().getPagination().isHasMore()){
                        isLastPage = true;
                    }
                    eventItemsCategoryAdapter.addData(dataItems);
                    eventItemsCategoryAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<EventItemsResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;

            }
        });

    }

    private void updateUI() {

         eventItemsCategoryAdapter = new LocationItemsTagsAdapter(context,locationId, eventItemsResponse.getData());
        menuitemsRecyclerView.setAdapter(eventItemsCategoryAdapter);
        eventItemsCategoryAdapter.notifyDataSetChanged();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity(),R.style.Custom);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Drawable drawable = new ProgressBar(getActivity()).getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(getActivity(), R.color.White_clr),
                PorterDuff.Mode.SRC_IN);
        mProgressDialog.setIndeterminateDrawable(drawable);
        mProgressDialog.show();
//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(context);
//            mProgressDialog.setMessage(getString(R.string.loading));
//            mProgressDialog.setIndeterminate(true);
//        }
//
//        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    
}

