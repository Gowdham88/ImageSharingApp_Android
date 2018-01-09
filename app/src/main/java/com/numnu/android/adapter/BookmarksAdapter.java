package com.numnu.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.numnu.android.R;
import com.numnu.android.fragments.detail.BusinessDetailFragment;
import com.numnu.android.fragments.detail.EventDetailFragment;
import com.numnu.android.fragments.detail.ItemDetailFragment;
import com.numnu.android.fragments.detail.LocationDetailFragment;
import com.numnu.android.fragments.detail.SearchBusinessDetailFragment;
import com.numnu.android.fragments.search.SliceFragment;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.BookmarkdataItem;
import com.numnu.android.network.response.BookmarkdataItem;
import com.numnu.android.network.response.GetBookmarksResponse;
import com.numnu.android.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

        final BookmarkdataItem bookmarkdataItem = list.get(position);

        holder.textViewName.setText(bookmarkdataItem.getEntityname());


        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment selectedFragment = null;
                switch (type){
                    case "event":
                        selectedFragment= EventDetailFragment.newInstance(String.valueOf(bookmarkdataItem.getId()));
                        break;
                    case "post":
                        Bundle bundle = new Bundle();
                        bundle.putString("postId", String.valueOf(bookmarkdataItem.getId()));
                        SliceFragment sliceFragment = SliceFragment.newInstance();
                        sliceFragment.setArguments(bundle);
                        selectedFragment = sliceFragment;
                        break;
                    case "business":
                        selectedFragment= SearchBusinessDetailFragment.newInstance(String.valueOf(bookmarkdataItem.getId()));
                        break;
                    case "location":
                        selectedFragment= LocationDetailFragment.newInstance(String.valueOf(bookmarkdataItem.getId()));
                        break;
                    case "item":
                        selectedFragment= ItemDetailFragment.newInstance(String.valueOf(bookmarkdataItem.getId()));
                        break;

                }
                        FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.addToBackStack(null).commit();

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert(String.valueOf(bookmarkdataItem.getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView delete;
        private TextView textViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName =  itemView.findViewById(R.id.txt_itemname);
            this.delete =  itemView.findViewById(R.id.btn_delete);

        }
    }

    public void showAlert(final String id) {

        LayoutInflater factory = LayoutInflater.from(context);
        final View deleteDialogView = factory.inflate(R.layout.bookmark_popup, null);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setView(deleteDialogView);
        final TextView delete= deleteDialogView.findViewById(R.id.popup_delete);
        TextView cancel= deleteDialogView.findViewById(R.id.gender_cancel);
        LinearLayout popupLayout= deleteDialogView.findViewById(R.id.genlin_lay);

        final AlertDialog alertDialog1 = alertDialog.create();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               deleteBookmark(id);
               alertDialog1.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });
        popupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });

        alertDialog1.setCanceledOnTouchOutside(false);
        try {
            alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        alertDialog1.show();
//        alertDialog1.getWindow().setLayout((int)Utils.convertDpToPixel(290,
//                getActivity()),(int)Utils.convertDpToPixel(290,getActivity()));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog1.getWindow().getAttributes());
        lp.gravity = Gravity.BOTTOM;
        lp.windowAnimations = R.style.shareDialogAnimation;
        alertDialog1.getWindow().setAttributes(lp);
    }


    private void deleteBookmark(String bookmarkId)
    {
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<Void> call=apiServices.deleteBookmark(bookmarkId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    Toast.makeText(context, "Sucessfully deleted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
