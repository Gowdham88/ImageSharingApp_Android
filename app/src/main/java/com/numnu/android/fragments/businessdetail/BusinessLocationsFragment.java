package com.numnu.android.fragments.businessdetail;

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
import com.numnu.android.adapter.LocationItemsAdapter;
import com.numnu.android.fragments.detail.LocationDetailFragment;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.BusinessLocationResponse;
import com.numnu.android.network.response.Datum;
import com.numnu.android.network.response.ItemLocationResponse;
import com.numnu.android.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thulir on 9/10/17.
 */

public class BusinessLocationsFragment extends Fragment {
    private  String eventId,businessId;
    private RecyclerView menuitemsRecyclerView;
    private Context context;
    LocationItemsAdapter currentUpAdapter;
    private boolean isLoading=false;
    private boolean isLastPage=false;
    private int PAGE_SIZE = 20;
    private int nextPage = 1;
    BusinessLocationResponse BusinesslocationResponse;

    public static BusinessLocationsFragment newInstance(String eventId, String businessId) {

        BusinessLocationsFragment businessLocationsFragment = new BusinessLocationsFragment();
        Bundle args = new Bundle();
        args.putString("businessId", businessId);
        args.putString("eventId", eventId);
        businessLocationsFragment.setArguments(args);
        return businessLocationsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            eventId = bundle.getString("eventId");
            businessId = bundle.getString("businessId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    View  view= inflater.inflate(R.layout.fragment_business_location_items, container, false);

    menuitemsRecyclerView = view.findViewById(R.id.menu_items_recyclerview);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        menuitemsRecyclerView.setLayoutManager(layoutManager);
//    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(menuitemsRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
//        menuitemsRecyclerView.addItemDecoration(dividerItemDecoration);
        menuitemsRecyclerView.setNestedScrollingEnabled(false);
//    setupRecyclerView();

    final android.support.v7.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);

        if(Utils.isNetworkAvailable(context)) {
            getData("50");
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
                        loadMoreItems("50");
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

    private void getData(String id)
    {

        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<BusinessLocationResponse> call=apiServices.getbusinesslocation(id);
        call.enqueue(new Callback<BusinessLocationResponse>() {
            @Override
            public void onResponse(Call<BusinessLocationResponse> call, Response<BusinessLocationResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    BusinesslocationResponse=response.body();
                    updateUI();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<BusinessLocationResponse> call, Throwable t) {
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
        Call<BusinessLocationResponse> call=apiServices.getbusinesslocation(id, String.valueOf(nextPage));
        call.enqueue(new Callback<BusinessLocationResponse>() {
            @Override
            public void onResponse(Call<BusinessLocationResponse> call, Response<BusinessLocationResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    List<Datum> location=response.body().getData();
                    if(!response.body().getPagination().isHasMore()){
                        isLastPage = true;
                    }
                    currentUpAdapter.addData(location);
                    currentUpAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<BusinessLocationResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });

    }

    private void updateUI() {

        currentUpAdapter = new LocationItemsAdapter(context, BusinesslocationResponse.getData());
        menuitemsRecyclerView.setAdapter(currentUpAdapter);
        currentUpAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}

