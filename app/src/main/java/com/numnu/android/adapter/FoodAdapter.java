package com.numnu.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.numnu.android.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 11/6/2017.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {


    String[] arr = {"Biryani", "Biryani", "Biryani", "Biryani", "Biryani", "Biryani",  "Biryani", "Biryani"};
//
    Context context;
    LayoutInflater layout;
    ArrayList<String> list;

    public FoodAdapter(Context context, ArrayList<String> mylist) {
        this.context = context;
        this.list=mylist;
        layout = LayoutInflater.from(context);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView FoodText;
        ImageView cancleimg;
        LinearLayout linearLay;


        public ViewHolder(View itemView) {
            super(itemView);
            FoodText=(TextView)itemView.findViewById(R.id.food_txt);
            cancleimg=(ImageView)itemView.findViewById(R.id.food_cancel_button);
            linearLay=(LinearLayout)itemView.findViewById(R.id.food_linlay);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder Holder, final int position) {

       Holder.FoodText.setText( list.get(position));
       Holder.cancleimg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
//               Toast.makeText(context,  list.remove(position)+ " "+"removed", Toast.LENGTH_SHORT).show();
        list.remove(position);
        notifyDataSetChanged();
           }
       });

//

    }

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
//        return arr.length;
    }
}


