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
import com.numnu.android.fragments.EventDetail.EventBusinessFragment;
import com.numnu.android.fragments.EventDetail.EventItemsCategoryFragment;
import com.numnu.android.fragments.home.SettingsFragment;
import com.numnu.android.fragments.search.EventsFragment;
import com.numnu.android.fragments.search.SliceFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by thulir on 10/10/17.
 */

public class EventReviewsAdapter extends RecyclerView.Adapter<EventReviewsAdapter.ViewHolder> {

    Context context;
    ArrayList<String> stringArrayList = new ArrayList<>();

    public EventReviewsAdapter(Context context, ArrayList<String> stringArrayList) {
        this.context=context;
        this.stringArrayList=stringArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.with(context).load(R.drawable.pasta)
                .placeholder(R.drawable.food_for_lunch_mom)
                .fit()
                .into(holder.imageViewIcon);
//        holder.textViewName.setText(stringArrayList.get(position));
        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, SliceFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        holder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, SettingsFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        holder.cottageHouseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventBusinessFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        holder.barbequeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventItemsCategoryFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        holder.eventName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventsFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  ImageView imageViewIcon;
        private  ImageView profileImage;
        private TextView textViewName;
        private TextView eventName;
        private TextView cottageHouseText;
        private TextView barbequeText;

        public ViewHolder(View itemView) {
            super(itemView);
//            this.textViewName =  itemView.findViewById(R.id.text_event_name);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
//            this.imageViewIcon = itemView.findViewById(R.id.review_image);
            this.imageViewIcon = itemView.findViewById(R.id.content_image);
            this.profileImage = itemView.findViewById(R.id.slice_profile_image);
            this.cottageHouseText = itemView.findViewById(R.id.cottage_house_txt);
            this.barbequeText = itemView.findViewById(R.id.barbq_txt);
            this.eventName = itemView.findViewById(R.id.barbados_txt);

        }
    }
}
