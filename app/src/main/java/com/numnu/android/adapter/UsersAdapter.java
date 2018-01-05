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
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.numnu.android.R;
import com.numnu.android.fragments.detail.UserDetailsFragment;
import com.numnu.android.network.response.DataItem;
import com.numnu.android.network.response.Homeuserresp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhivy on 07/11/2017.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    Context context;
    ArrayList<String> stringArrayList = new ArrayList<>();
    List<Homeuserresp> list = new ArrayList<>();
    private StorageReference storageRef ;
    private FirebaseStorage storage;
    HorizontalContentAdapter adapter;
    RecyclerView hrecyclerView;

    public UsersAdapter(Context context, List<Homeuserresp> stringArrayList) {
        this.context = context;
        this.list =stringArrayList;
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();
    }
    public  void addData(List<Homeuserresp> stringArrayList){
        list.addAll(stringArrayList);
    }
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_item, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final UsersAdapter.ViewHolder holder, int position) {
        final Homeuserresp homeuserRespo = list.get(position);
        holder.usertextView.setText(list.get(position).getUsername());
        holder.emailtextView.setText("@"+list.get(position).getUsername());
        if(!homeuserRespo.getUserimages().isEmpty()&&homeuserRespo.getUserimages().get(0).getImageurl()!=null) {
            storageRef.child(homeuserRespo.getUserimages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Picasso.with(context).load(uri)
                            .placeholder(R.drawable.background)
                            .fit()
                            .into(holder.imageView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("Showarrow",  true);
                UserDetailsFragment userFragment = new UserDetailsFragment();
                userFragment.setArguments(bundle);
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, userFragment);
                transaction.addToBackStack(null).commit();
//                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
//                transaction.replace(R.id.frame_layout, UserDetailsFragment.newInstance());
//                transaction.addToBackStack(null).commit();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("Showarrow",  true);
                UserDetailsFragment userFragment = new UserDetailsFragment();
                userFragment.setArguments(bundle);
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, userFragment);
                transaction.addToBackStack(null).commit();
            }
        });
//        adapter = new HorizontalContentAdapter(context, homeuserRespo.getTags());
//        hrecyclerView.setAdapter(adapter);
//        hrecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView = itemView.findViewById(R.id.slice_profile_image);
        TextView usertextView = itemView.findViewById(R.id.usertext_name);
        TextView emailtextView = itemView.findViewById(R.id.text_usermail_name);
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
