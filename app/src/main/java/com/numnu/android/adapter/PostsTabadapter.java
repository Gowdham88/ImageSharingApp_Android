package com.numnu.android.adapter;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.numnu.android.R;
import com.numnu.android.fragments.EventDetail.EventItemsListFragment;
import com.numnu.android.fragments.detail.EventDetailFragment;
import com.numnu.android.fragments.detail.ItemDetailFragment;
import com.numnu.android.fragments.detail.SearchBusinessDetailFragment;
import com.numnu.android.fragments.search.SliceFragment;

/**
 * Created by lenovo on 11/1/2017.
 */

public class PostsTabadapter extends RecyclerView.Adapter<PostsTabadapter.ViewHolder> {

    String[] arr = {"Category", "Category", "Category", "Category", "Category", "Category", "Category", "Category", "Category", "Category", "Category", "Category",};
    Context context;
    LayoutInflater layout;

    public PostsTabadapter(Context context) {
        this.context = context;
        layout = LayoutInflater.from(context);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView PostcategoryTxt,postcountTxt;
        ImageView PostForImg;
        LinearLayout PostLinLay;

        public ViewHolder(View itemView) {
            super(itemView);
            PostcategoryTxt=(TextView)itemView.findViewById(R.id.post_category_text);
            postcountTxt=(TextView)itemView.findViewById(R.id.post_count_text);
            PostForImg=(ImageView)itemView.findViewById(R.id.post_forward_img);
            PostLinLay=(LinearLayout) itemView.findViewById(R.id.post_lin_lay);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder Holder, final int position) {

        Holder.PostcategoryTxt.setText(arr[position]);
        Holder.PostForImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, SliceFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        Holder.PostLinLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, SliceFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

//

    }

    @Override
    public int getItemCount() {
        return arr.length;
    }
}


