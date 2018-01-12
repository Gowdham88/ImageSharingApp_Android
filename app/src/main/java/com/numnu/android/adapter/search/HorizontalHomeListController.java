package com.numnu.android.adapter.search;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.numnu.android.R;
import com.numnu.android.fragments.search.EventsFragmentwithToolbar;
import com.numnu.android.network.response.HomeResponse;

import java.util.ArrayList;
import java.util.List;

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

                FragmentTransaction transaction =  ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventsFragmentwithToolbar.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        horizontalList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount         = layoutManager.getChildCount();
                int totalItemCount           = layoutManager.getItemCount();
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
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
