package com.numnu.android.adapter.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.numnu.android.R;
import com.numnu.android.activity.HomeActivity;
import com.numnu.android.fragments.search.EventsFragmentwithToolbar;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
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
 * Created by czltd on 1/12/18.
 */

public class HorizontalHomeListController extends RecyclerView.Adapter<HorizontalHomeListController.SimpleViewHolder> {

    private static Context mContext;
    private static List<HomeResponse> mData;
    private static RecyclerView horizontalList;
    private static LinearLayoutManager layoutManager;
    private boolean isLoading=false;
    private boolean isLastPage=false;
    private int PAGE_SIZE = 20;
    private int nextPage = 1;
    int position;

    public HorizontalHomeListController(Context context, List<HomeResponse> data) {
        mContext = context;
        if (data != null)
            mData = new ArrayList<>(data);
        else mData = new ArrayList<>();

    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        private HorizontalHomePageAdapter horizontalAdapter;
        TextView curtextViewName, textcity, textDate;
        ImageView imageViewIcon;

        public SimpleViewHolder(View view) {

            super(view);

            Context context = itemView.getContext();
            this.curtextViewName = itemView.findViewById(R.id.past_events_text);
            this.imageViewIcon = itemView.findViewById(R.id.view_past_event_list);
            horizontalList = (RecyclerView) itemView.findViewById(R.id.past_recyclerview);
            layoutManager =  new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            horizontalList.setLayoutManager(layoutManager);
            horizontalAdapter = new HorizontalHomePageAdapter(mContext);
            horizontalList.setAdapter(horizontalAdapter);
        }
    }



    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.recyvertical_content, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {

        holder.curtextViewName.setText(mData.get(position).getListTitle());
        holder.horizontalAdapter.setRowIndex(position);
        holder.horizontalAdapter.setData(mData.get(position).getData());
        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new Fragment();
                Bundle bundle= new Bundle();
                bundle.putInt("position",position);
                fragment.setArguments(bundle);
                FragmentTransaction transaction =  ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventsFragmentwithToolbar.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) horizontalList.getLayoutManager();

       horizontalList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount         = linearLayoutManager.getChildCount();
                int totalItemCount           = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                Log.e("visibleItemCount", String.valueOf(visibleItemCount));
                Log.e("totalItemCount", String.valueOf(totalItemCount));
                Log.e("firstItemPosition", String.valueOf(firstVisibleItemPosition));
                Log.e("position", String.valueOf(position));

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0 && totalItemCount < mData.get(position).getPagination().getTotalRows()) {

                        loadMoreItems(position);

                    }
                }



            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void loadMoreItems(final Integer postion)
    {

        Log.e("dsjhdsjh", String.valueOf(postion));

        LocationObject citylocation = new LocationObject();
        citylocation.setLattitude(13.0312186);
        citylocation.setLongitude(77.0312186);
        citylocation.setNearMeRadiusInMiles(15000);
        LocationHomePost locationhomepost=new LocationHomePost();
        locationhomepost.setClientapp(Constants.CLIENT_APP);
        locationhomepost.setClientip(Utils.getLocalIpAddress(mContext));
        locationhomepost.setLocationObject(citylocation);

        nextPage += 1;
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<HomeResponse> call=apiServices.gethomeevntresp(String.valueOf(postion),String.valueOf(nextPage),locationhomepost);
        call.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    Log.e("reponse", new Gson().toJson(response.body()));
                    mData.get(postion).setPagination(response.body().getPagination());

                    List<HomeApiResponse> vData = new ArrayList<>();
                    vData = response.body().getData();

                    for (int i=0;i<vData.size();i++){

                        mData.get(postion).getData().add(vData.get(i));

                    }

                    Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
                    Log.e("data", String.valueOf(response.body().getData()));
                    if(!response.body().getPagination().isHasMore()){
                        isLastPage = true;
                    }
                    notifyDataSetChanged();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                Toast.makeText(mContext, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });

    }

}
