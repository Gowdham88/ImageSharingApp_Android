package com.numnu.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.numnu.android.R;

import java.util.ArrayList;

/**
 * Created by dhivy on 06/11/2017.
 */

public class UserPostsAdapter extends RecyclerView.Adapter<UserPostsAdapter.ViewHolder> {
    Context context;
    ArrayList<String> stringArrayList = new ArrayList<>();

    public UserPostsAdapter(Context context, ArrayList<String> stringArrayList) {
        this.context = context;
        this.stringArrayList = stringArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewIcon;
        private  ImageView profileImage;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageViewIcon = itemView.findViewById(R.id.content_image);
            this.profileImage = itemView.findViewById(R.id.slice_profile_image);
        }
    }
}
