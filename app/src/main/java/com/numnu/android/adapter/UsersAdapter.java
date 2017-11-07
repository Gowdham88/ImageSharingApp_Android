package com.numnu.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numnu.android.R;

import java.util.ArrayList;

/**
 * Created by dhivy on 07/11/2017.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    Context context;
    ArrayList<String> stringArrayList = new ArrayList<>();

    public UsersAdapter(Context context, ArrayList<String> stringArrayList) {
        this.context = context;
        this.stringArrayList = stringArrayList;
    }

    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_item, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(UsersAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
