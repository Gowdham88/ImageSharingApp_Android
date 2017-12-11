package com.numnu.android.fragments.EventDetail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.numnu.android.R;
import com.numnu.android.adapter.EventBusinessAdapter;
import com.numnu.android.adapter.EventBusinessesAdapter;
import com.numnu.android.fragments.detail.BusinessDetailFragment;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.EventBusinessesResponse;
import com.numnu.android.network.response.EventDetailResponse;

import java.util.ArrayList;
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
    List<EventBusinessesResponse> eventBusinessesResponse=new ArrayList<>();

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
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        businessRecyclerView.setLayoutManager(layoutManager);
        businessRecyclerView.setNestedScrollingEnabled(false);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(businessRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
//        businessRecyclerView.addItemDecoration(dividerItemDecoration);
            getBusinessDetails(eventId);
        return view;

    }

    private void getBusinessDetails(String id)
    {
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<List<EventBusinessesResponse>> call=apiServices.getEventBusinesses(id);
        call.enqueue(new Callback<List<EventBusinessesResponse>>() {
            @Override
            public void onResponse(Call<List<EventBusinessesResponse>> call, Response<List<EventBusinessesResponse>> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                     eventBusinessesResponse = response.body();
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<List<EventBusinessesResponse>> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateUI() {

        EventBusinessesAdapter eventBusinessesAdapter = new EventBusinessesAdapter(context, eventBusinessesResponse);
        businessRecyclerView.setAdapter(eventBusinessesAdapter);
        eventBusinessesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


}

