package com.numnu.android.adapter;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.numnu.android.R;
import com.numnu.android.fragments.EventDetail.EventItemsListFragment;
import com.numnu.android.fragments.EventDetailFragment;
import com.numnu.android.fragments.ItemDetailFragment;


/**
 * Created by lenovo on 10/5/2017.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    String[] arr = {"Festival", "Wine", "Party", "Rum", "Festival", "Wine", "Party", "Rum", "Festival", "Wine", "Party", "Rum",};
    Context context;
    LayoutInflater layout;

    public ItemsAdapter(Context context) {
        this.context = context;
        layout = LayoutInflater.from(context);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ItemTxt,CountTxt;
        ImageView Itemimg;


        public ViewHolder(View itemView) {
            super(itemView);
            ItemTxt=(TextView)itemView.findViewById(R.id.item_category_text);
            Itemimg=(ImageView)itemView.findViewById(R.id.item_category_img);
            CountTxt=(TextView)itemView.findViewById(R.id.item_count_text);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menuitems_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder Holder, final int position) {

        Holder.ItemTxt.setText(arr[position]);

        Holder.ItemTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EventItemsListFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        Holder.Itemimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EventItemsListFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.length;
    }
}

