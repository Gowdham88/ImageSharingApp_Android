package com.numnu.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.numnu.android.R;

/**
 * Created by lenovo on 10/31/2017.
 */

public class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemsAdapter.ViewHolder> {

    String[] arr = {"Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani"};
    int[] image = {R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg,
            R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg,
            R.drawable.biryaniimg, R.drawable.biryaniimg};
    Context context;

    public MenuItemsAdapter(Context context) {
        this.context = context;

    }
    @Override
    public MenuItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menuitems_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuItemsAdapter.ViewHolder holder, int position) {
            holder.BusinessImage.setImageResource(image[position]);
            holder.BusinessTitle.setText(arr[position]);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView BusinessTitle,CategoryTitle1,CategoryTitle2;
        ImageView BusinessImage;


        public ViewHolder(View itemView) {
            super(itemView);
            BusinessTitle=(TextView)itemView.findViewById(R.id.text_business);
            CategoryTitle1=(TextView)itemView.findViewById(R.id.category1_business);
            CategoryTitle2=(TextView)itemView.findViewById(R.id.category2_business);
            BusinessImage=(ImageView)itemView.findViewById(R.id.menuitem_image);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
