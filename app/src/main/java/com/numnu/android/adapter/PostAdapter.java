package com.numnu.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.numnu.android.R;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    String[] arr = {"Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani"};
    int[] image = {R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg,
            R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg,
            R.drawable.biryaniimg, R.drawable.biryaniimg};
    Context context;

    public PostAdapter(Context context) {
        this.context = context;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostAdapter.ViewHolder holder, int position) {
//        holder.BusinessImage.setImageResource(image[position]);
        holder.postTitle.setText(arr[position]);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView postTitle,CategoryTitle1,CategoryTitle2;
        ImageView BusinessImage;


        public ViewHolder(View itemView) {
            super(itemView);
            postTitle=(TextView)itemView.findViewById(R.id.post_text);
//            CategoryTitle1=(TextView)itemView.findViewById(R.id.category1_business);
//            CategoryTitle2=(TextView)itemView.findViewById(R.id.category2_business);
//            BusinessImage=(ImageView)itemView.findViewById(R.id.menuitem_image);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

