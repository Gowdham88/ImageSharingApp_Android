package com.numnu.android.adapter.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.numnu.android.R;
import com.numnu.android.fragments.auth.LoginFragment;
import com.numnu.android.fragments.detail.EventDetailFragment;
import com.numnu.android.fragments.detail.ItemDetailFragment;
import com.numnu.android.fragments.detail.SearchBusinessDetailFragment;
import com.numnu.android.fragments.detail.UserDetailsFragment;
import com.numnu.android.fragments.search.SliceFragment;
import com.numnu.android.network.response.PostdataItem;
import com.numnu.android.utils.PreferencesHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class SearchBusinessPostsAdapter extends RecyclerView.Adapter<SearchBusinessPostsAdapter.ViewHolder> {

    Context context;
    List<PostdataItem> list = new ArrayList<>();
    private StorageReference storageRef ;
    private FirebaseStorage storage;

    public SearchBusinessPostsAdapter(Context context, List<PostdataItem> stringArrayList) {
        this.context=context;
        this.list=stringArrayList;
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();
    }
    public  void addData(List<PostdataItem> stringArrayList){
        list.addAll(stringArrayList);
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
    public void onBindViewHolder(final ViewHolder holder, int position) {


        final PostdataItem postdataItem = list.get(position);
        if(!postdataItem.getPostimages().isEmpty()&&postdataItem.getPostimages().get(0).getImageurl()!=null) {
            storageRef.child(postdataItem.getPostimages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Picasso.with(context).load(uri)
                            .placeholder(R.drawable.background)
                            .fit()
                            .into(holder.imageViewIcon);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }

        if(!postdataItem.getPostcreator().getUserimages().isEmpty()&&postdataItem.getPostcreator().getUserimages().get(0).getImageurl()!=null) {
            storageRef.child(postdataItem.getPostcreator().getUserimages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Picasso.with(context).load(uri)
                            .placeholder(R.drawable.background)
                            .fit()
                            .into(holder.imageViewIcon);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }

        holder.eventName.setText(postdataItem.getEvent().getName());
        holder.username.setText(postdataItem.getPostcreator().getUsername());
        holder.name.setText(postdataItem.getPostcreator().getName());
        holder.title.setText(postdataItem.getComment());
        holder.cottageHouseText.setText(postdataItem.getBusiness().getBusinessname());
        if(!postdataItem.getTaggeditems().isEmpty()) {
            holder.barbequeText.setText(postdataItem.getTaggeditems().get(0).getName());
        }

//        holder.textViewName.setText(stringArrayList.get(position));
        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("postId", String.valueOf(postdataItem.getId()));
                SliceFragment sliceFragment = SliceFragment.newInstance();
                sliceFragment.setArguments(bundle);
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout,sliceFragment);
                transaction.addToBackStack(null).commit();
            }
        });

        holder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout, UserDetailsFragment.newInstance(String.valueOf(postdataItem.getPostcreator().getId())));
                transaction.addToBackStack(null).commit();
            }
        });

        holder.cottageHouseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout, SearchBusinessDetailFragment.newInstance(String.valueOf(postdataItem.getPostcreator().getId())));
                transaction.addToBackStack(null).commit();
            }
        });

        holder.cattgicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout, SearchBusinessDetailFragment.newInstance(String.valueOf(postdataItem.getPostcreator().getId())));
                transaction.addToBackStack(null).commit();
            }
        });
        holder.barbequeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout,
                        ItemDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        holder.barbqicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout,
                        ItemDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        holder.eventName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout, EventDetailFragment.newInstance(String.valueOf(postdataItem.getEvent().getId())));
                transaction.addToBackStack(null).commit();
            }
        });
        holder.eventicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout, EventDetailFragment.newInstance(String.valueOf(postdataItem.getEvent().getId())));
                transaction.addToBackStack(null).commit();
            }
        });
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout,  UserDetailsFragment.newInstance(String.valueOf(postdataItem.getPostcreator().getId())));
                transaction.addToBackStack(null).commit();
            }
        });
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout,  UserDetailsFragment.newInstance(String.valueOf(postdataItem.getPostcreator().getId())));
                transaction.addToBackStack(null).commit();
            }
        });
        holder.dotsimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertshare();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewIcon,barbqicon,cattgicon,eventicon;
        private  ImageView profileImage;
        private TextView eventName,username,email,name,title;
        private TextView cottageHouseText;
        private TextView barbequeText;
        ImageView dotsimg;
        public ViewHolder(View itemView) {
            super(itemView);
            this.title =  itemView.findViewById(R.id.title);
//            this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
//            this.imageViewIcon = itemView.findViewById(R.id.review_image);
            this.imageViewIcon = itemView.findViewById(R.id.content_image);
            this.profileImage = itemView.findViewById(R.id.slice_profile_image);
            this.cottageHouseText = itemView.findViewById(R.id.cottage_house_txt);
            this.barbequeText = itemView.findViewById(R.id.barbq_txt);
            this.eventName = itemView.findViewById(R.id.barbados_txt);
            this.name = itemView.findViewById(R.id.slice_toolbar_profile_name);
            this.username = itemView.findViewById(R.id.user_name);
            this.barbqicon = itemView.findViewById(R.id.barbq_icon);
            this.cattgicon = itemView.findViewById(R.id.cottage_house_icon);
            this.eventicon = itemView.findViewById(R.id.barbados_icon);
            dotsimg=(ImageView)itemView.findViewById(R.id.event_dots);

        }
    }
    public void showAlertshare() {

        LayoutInflater factory = LayoutInflater.from(context);
        final View deleteDialogView = factory.inflate(R.layout.bookmark_layout, null);
        final android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(context);
        alertDialog.setView(deleteDialogView);
        final TextView shareTxt = (TextView) deleteDialogView.findViewById(R.id.share);
        final TextView BookmarkTxt = (TextView) deleteDialogView.findViewById(R.id.bookmark);
        TextView cancel = (TextView) deleteDialogView.findViewById(R.id.gender_cancel);
//        LinearLayout GenderLinLay = (LinearLayout) deleteDialogView.findViewById(R.id.genlin_lay);
//        Button ok = deleteDialogView.findViewById(R.id.ok_button);

        final android.support.v7.app.AlertDialog alertDialog1 = alertDialog.create();
        shareTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Post Content here..."+context.getPackageName());
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, context.getResources().getText(R.string.share_using)));
                alertDialog1.dismiss();
            }
        });
        BookmarkTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean loginStatus =  PreferencesHelper.getPreferenceBoolean(context,PreferencesHelper.PREFERENCE_LOGGED_IN);
                if (!loginStatus) {
                    Bundle bundle = new Bundle();
                    bundle.putString("BusinessBookmarkIntent","businessbookmark");
                    LoginFragment logFragment = new LoginFragment();
                    logFragment.setArguments(bundle);
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                    transaction.add(R.id.frame_layout, logFragment);
                    transaction.addToBackStack(null).commit();
//                    Intent intent = new Intent(context, LoginFragment.class);
//                    intent.putExtra("BusinessBookmarkIntent","businessbookmark");
//                    context.startActivity(intent);
                    alertDialog1.dismiss();
                }else {
//                    postBookmark();
                    alertDialog1.dismiss();
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });
//        GenderLinLay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog1.dismiss();
//            }
//        });

//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });


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


}
