package com.numnu.android.fragments.eventdetail;

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
import com.numnu.android.adapter.EventBusinessesAdapter;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.DataItem;
import com.numnu.android.network.response.EventBusinessesResponse;
import com.numnu.android.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thulir on 9/10/17.
 */

public class EventBusinessFragment extends Fragment {

    private RecyclerView businessRecyclerView;
    private Context context;
    private String eventId;
    EventBusinessesResponse eventBusinessesResponse;
    private EventBusinessesAdapter eventBusinessesAdapter;
    private boolean isLoading=false;
    private boolean isLastPage=false;
    private int PAGE_SIZE = 20;
    private int nextPage = 1;

    public static EventBusinessFragment newInstance(String eventId) {

        EventBusinessFragment eventBusinessFragment = new EventBusinessFragment();
        Bundle args = new Bundle();
        args.putString("eventId", eventId);
        eventBusinessFragment.setArguments(args);

        return eventBusinessFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            eventId = bundle.getString("eventId");
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  view= inflater.inflate(R.layout.fragment_event_business, container, false);

        businessRecyclerView = view.findViewById(R.id.business_recyclerview);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        businessRecyclerView.setLayoutManager(layoutManager);
        businessRecyclerView.setNestedScrollingEnabled(false);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(businessRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
//        businessRecyclerView.addItemDecoration(dividerItemDecoration);
            if(Utils.isNetworkAvailable(context)) {
                getBusinessDetails(eventId);
            }else {
                showAlert();
            }
        // Pagination
        businessRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        loadMoreItems(eventId);
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


    private void getBusinessDetails(String id)
    {
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<EventBusinessesResponse> call=apiServices.getEventBusinesses(id);
        call.enqueue(new Callback<EventBusinessesResponse>() {
            @Override
            public void onResponse(Call<EventBusinessesResponse> call, Response<EventBusinessesResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                     eventBusinessesResponse = response.body();
                    updateUI();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<EventBusinessesResponse> call, Throwable t) {
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
        Call<EventBusinessesResponse> call=apiServices.getEventBusinesses(id, String.valueOf(nextPage));
        call.enqueue(new Callback<EventBusinessesResponse>() {
            @Override
            public void onResponse(Call<EventBusinessesResponse> call, Response<EventBusinessesResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                      List<DataItem> dataItems=response.body().getData();
                      if(!response.body().getPagination().isHasMore()){
                          isLastPage = true;
                      }
                    eventBusinessesAdapter.addData(dataItems);
                    eventBusinessesAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<EventBusinessesResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });

    }

    private void updateUI() {

         eventBusinessesAdapter = new EventBusinessesAdapter(context, eventBusinessesResponse.getData());
        businessRecyclerView.setAdapter(eventBusinessesAdapter);
        eventBusinessesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


}

