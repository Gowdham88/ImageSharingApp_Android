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
 * Created by lenovo on 10/5/2017.
 */

public class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemsAdapter.ViewHolder> {

    String[] arr = {"Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani"};
    int[] image = {R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg,
            R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg, R.drawable.biryaniimg,
            R.drawable.biryaniimg, R.drawable.biryaniimg};
    Context context;
    LayoutInflater layout;

    public MenuItemsAdapter(Context context) {
        this.context = context;
        layout = LayoutInflater.from(context);

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menuitems_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder Holder, final int position) {

        Holder.BusinessImage.setImageResource(image[position]);
        Holder.BusinessTitle.setText(arr[position]);

//

    }

    @Override
    public int getItemCount() {
        return arr.length;
    }
}

