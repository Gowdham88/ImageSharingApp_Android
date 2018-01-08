package com.numnu.android.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.numnu.android.R;
import com.numnu.android.fragments.detail.BusinessDetailFragment;
import com.numnu.android.network.response.BookmarkdataItem;
import com.numnu.android.network.response.BookmarkdataItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder> {


    Context context;
    List<BookmarkdataItem> list = new ArrayList<>();
    HorizontalContentAdapter adapter;
    private RecyclerView recyclerView;
    private StorageReference storageRef ;
    private FirebaseStorage storage;
    private String userId,type;

    public BookmarksAdapter(Context context, String eventId, String type, List<BookmarkdataItem> stringArrayList) {
        this.context=context;
        this.list =stringArrayList;
        this.userId=eventId;
        this.type = type;
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();
    }

    public  void addData(List<BookmarkdataItem> stringArrayList){
        list.addAll(stringArrayList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookmark_list_item, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final BookmarkdataItem eventBusinessesResponse = list.get(position);

        holder.textViewName.setText(list.get(position).getEntityname());


        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("businessId", String.valueOf(eventBusinessesResponse.getId()));
//                bundle.putString("eventId", eventId);
                BusinessDetailFragment businessDetailFragment = BusinessDetailFragment.newInstance();
                businessDetailFragment.setArguments(bundle);

                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, businessDetailFragment);
                transaction.addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  ImageView imageViewIcon;
        private TextView textViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName =  itemView.findViewById(R.id.txt_itemname);

        }
    }
}
