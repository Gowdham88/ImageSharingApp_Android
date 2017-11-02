package com.numnu.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.numnu.android.R;

/**
 * Created by lenovo on 11/1/2017.
 */

public class PostsTabadapter extends RecyclerView.Adapter<PostsTabadapter.ViewHolder> {

    String[] arr = {"Category", "Category", "Category", "Category", "Category", "Category", "Category", "Category", "Category"
            , "Category", "Category","Category", "Category", "Category", "Category", "Category", "Category", "Category",
            "Category", "Category"};
//    int[] image = {R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg,
//            R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg,
//            R.drawable.biryaniimg, R.drawable.biryaniimg};
    Context context;
    LayoutInflater layout;

    public PostsTabadapter(Context context) {
        this.context = context;
        layout = LayoutInflater.from(context);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView PostcategoryTxt;

        public ViewHolder(View itemView) {
            super(itemView);
            PostcategoryTxt=(TextView)itemView.findViewById(R.id.post_category_text);
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

//

    }

    @Override
    public int getItemCount() {
        return arr.length;
    }
}


