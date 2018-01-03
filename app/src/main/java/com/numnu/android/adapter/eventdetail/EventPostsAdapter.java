package com.numnu.android.adapter.eventdetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class EventPostsAdapter extends RecyclerView.Adapter<EventPostsAdapter.ViewHolder> {

    Context context;
    List<PostdataItem> list = new ArrayList<>();
    private StorageReference storageRef ;
    private FirebaseStorage storage;
    String postEndDate;
    Duration diff;
    String difference;

    public EventPostsAdapter(Context context, List<PostdataItem> stringArrayList) {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
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
                            .into(holder.profileImage);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }


        if(postdataItem.getRating() ==1){
            holder.inloveicon.setImageResource(R.drawable.rating1);

        }else if(postdataItem.getRating() ==2){
            holder.inloveicon.setImageResource(R.drawable.rating2);
        }else{
            holder.inloveicon.setImageResource(R.drawable.rating3);
        }
        holder.eventName.setText(postdataItem.getEvent().getName());
        String UserName=postdataItem.getPostcreator().getUsername();
        holder.username.setText("@"+UserName);
        holder.name.setText(postdataItem.getPostcreator().getName());
        holder.title.setText(postdataItem.getComment());
         postEndDate=postdataItem.getCreatedat();
        SimpleDateFormat postendformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        java.util.Date endate = null;
        try
        {
            endate = postendformat.parse(postEndDate);
        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }
//        SimpleDateFormat Formater = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        String postendDateStr = Formater.format(endate);

        String times=getTimeAgo(endate,context);
        holder.TimeTxt.setText(times);
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
                transaction.add(R.id.frame_layout, SearchBusinessDetailFragment.newInstance(String.valueOf(postdataItem.getBusiness().getId())));
                transaction.addToBackStack(null).commit();
            }
        });

        holder.cattgicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout, SearchBusinessDetailFragment.newInstance(String.valueOf(postdataItem.getBusiness().getId())));
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
                transaction.add(R.id.frame_layout, EventDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        holder.eventicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.add(R.id.frame_layout, EventDetailFragment.newInstance());
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
                showBottomSheet(LayoutInflater.from(context));
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
        private TextView barbequeText,TimeTxt;
        ImageView dotsimg,inloveicon;
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
            inloveicon=(ImageView)itemView.findViewById(R.id.inlove_icon);
            TimeTxt=(TextView)itemView.findViewById(R.id.time);

        }
    }
    private void showBottomSheet(LayoutInflater inflater) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View bottomSheetView = inflater.inflate(R.layout.dialog_share_bookmark,null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
        ImageView shareimg = bottomSheetView.findViewById(R.id.dialog_image);
        ImageView bookmarkimg = bottomSheetView.findViewById(R.id.bookmark_icon);
        TextView share = bottomSheetView.findViewById(R.id.share_title);
        TextView bookmark = bottomSheetView.findViewById(R.id.bookmark_title);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Post Content here..."+context.getPackageName());
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, context.getResources().getText(R.string.share_using)));
                bottomSheetDialog.dismiss();
            }
        });
        shareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Post Content here..."+context.getPackageName());
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, context.getResources().getText(R.string.share_using)));
                bottomSheetDialog.dismiss();
            }
        });
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    bottomSheetDialog.dismiss();
                }else if (loginStatus){
                    Toast.makeText(context, "Bookmarked this page", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bookmarkimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    bottomSheetDialog.dismiss();
                }else if (loginStatus){
                    Toast.makeText(context, "Bookmarked this page", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return (Date) calendar.getTime();
    }

    public static String getTimeAgo(java.util.Date date, Context ctx) {

        if(date == null) {
            return null;
        }

        long time = date.getTime();

        Date curDate = currentDate();
        long now = curDate.getTime();
        if (time > now || time <= 0) {
            return null;
        }

        int dim = getTimeDistanceInMinutes(time);

        String timeAgo = null;

        if (dim == 0) {
            timeAgo = ctx.getResources().getString(R.string.date_util_term_less) + " " +  ctx.getResources().getString(R.string.date_util_term_a) + " " + ctx.getResources().getString(R.string.date_util_unit_minute);
        } else if (dim == 1) {
            return "1 " + ctx.getResources().getString(R.string.date_util_unit_minute);
        } else if (dim >= 2 && dim <= 44) {
            timeAgo = dim + " " + ctx.getResources().getString(R.string.date_util_unit_minutes);
        } else if (dim >= 45 && dim <= 89) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " "+ctx.getResources().getString(R.string.date_util_term_an)+ " " + ctx.getResources().getString(R.string.date_util_unit_hour);
        } else if (dim >= 90 && dim <= 1439) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + (Math.round(dim / 60)) + " " + ctx.getResources().getString(R.string.date_util_unit_hours);
        } else if (dim >= 1440 && dim <= 2519) {
            timeAgo = "1 " + ctx.getResources().getString(R.string.date_util_unit_day);
        } else if (dim >= 2520 && dim <= 43199) {
            timeAgo = (Math.round(dim / 1440)) + " " + ctx.getResources().getString(R.string.date_util_unit_days);
        } else if (dim >= 43200 && dim <= 86399) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " "+ctx.getResources().getString(R.string.date_util_term_a)+ " " + ctx.getResources().getString(R.string.date_util_unit_month);
        } else if (dim >= 86400 && dim <= 525599) {
            timeAgo = (Math.round(dim / 43200)) + " " + ctx.getResources().getString(R.string.date_util_unit_months);
        } else if (dim >= 525600 && dim <= 655199) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " "+ctx.getResources().getString(R.string.date_util_term_a)+ " " + ctx.getResources().getString(R.string.date_util_unit_year);
        } else if (dim >= 655200 && dim <= 914399) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_over) + " "+ctx.getResources().getString(R.string.date_util_term_a)+ " " + ctx.getResources().getString(R.string.date_util_unit_year);
        } else if (dim >= 914400 && dim <= 1051199) {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_almost) + " 2 " + ctx.getResources().getString(R.string.date_util_unit_years);
        } else {
            timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + (Math.round(dim / 525600)) + " " + ctx.getResources().getString(R.string.date_util_unit_years);
        }

        return timeAgo + " " + ctx.getResources().getString(R.string.date_util_suffix);
    }

    private static int getTimeDistanceInMinutes(long time) {
        long timeDistance = currentDate().getTime() - time;
        return Math.round((Math.abs(timeDistance) / 1000) / 60);
    }
//    public static String parseDate(String millis) {
//        if (millis.equalsIgnoreCase("")) {
//            return "";
//        }
//        //API.log("Day Ago "+dayago);
//        String result = "now";
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        java.util.Date date = new java.util.Date();
//        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
//        Calendar calendar = Calendar.getInstance();
//
//        long dayagolong = Long.valueOf(millis) * 1000;
//        calendar.setTimeInMillis(dayagolong);
//        String agoformater = formatter.format(calendar.getTime());
//
//        java.util.Date CurrentDate = null;
//        java.util.Date CreateDate = null;
//
//        try {
//
//            CurrentDate = new java.util.Date();
//            CreateDate = (java.util.Date) formatter.parse(String.valueOf(sqlDate));
//
//            long different = Math.abs(CurrentDate.getTime() - CreateDate.getTime());
//
//            long secondsInMilli = 1000;
//            long minutesInMilli = secondsInMilli * 60;
//            long hoursInMilli = minutesInMilli * 60;
//            long daysInMilli = hoursInMilli * 24;
//
//            long elapsedDays = different / daysInMilli;
//            different = different % daysInMilli;
//
//            long elapsedHours = different / hoursInMilli;
//            different = different % hoursInMilli;
//
//            long elapsedMinutes = different / minutesInMilli;
//            different = different % minutesInMilli;
//
//            long elapsedSeconds = different / secondsInMilli;
//
//            different = different % secondsInMilli;
//            if (elapsedDays == 0) {
//                if (elapsedHours == 0) {
//                    if (elapsedMinutes == 0) {
//                        if (elapsedSeconds < 0) {
//                            return "0" + " s";
//                        } else {
//                            if (elapsedDays > 0 && elapsedSeconds < 59) {
//                                return "now";
//                            }
//                        }
//                    } else {
//                        return String.valueOf(elapsedMinutes) + "m ago";
//                    }
//                } else {
//                    return String.valueOf(elapsedHours) + "h ago";
//                }
//
//            } else {
//                if (elapsedDays <= 29) {
//                    return String.valueOf(elapsedDays) + "d ago";
//                }
//                if (elapsedDays > 29 && elapsedDays <= 58) {
//                    return "1Mth ago";
//                }
//                if (elapsedDays > 58 && elapsedDays <= 87) {
//                    return "2Mth ago";
//                }
//                if (elapsedDays > 87 && elapsedDays <= 116) {
//                    return "3Mth ago";
//                }
//                if (elapsedDays > 116 && elapsedDays <= 145) {
//                    return "4Mth ago";
//                }
//                if (elapsedDays > 145 && elapsedDays <= 174) {
//                    return "5Mth ago";
//                }
//                if (elapsedDays > 174 && elapsedDays <= 203) {
//                    return "6Mth ago";
//                }
//                if (elapsedDays > 203 && elapsedDays <= 232) {
//                    return "7Mth ago";
//                }
//                if (elapsedDays > 232 && elapsedDays <= 261) {
//                    return "8Mth ago";
//                }
//                if (elapsedDays > 261 && elapsedDays <= 290) {
//                    return "9Mth ago";
//                }
//                if (elapsedDays > 290 && elapsedDays <= 319) {
//                    return "10Mth ago";
//                }
//                if (elapsedDays > 319 && elapsedDays <= 348) {
//                    return "11Mth ago";
//                }
//                if (elapsedDays > 348 && elapsedDays <= 360) {
//                    return "12Mth ago";
//                }
//
//                if (elapsedDays > 360 && elapsedDays <= 720) {
//                    return "1 year ago";
//                }
//
//                if (elapsedDays > 720) {
//                    SimpleDateFormat formatterYear = new SimpleDateFormat("MM/dd/yyyy");
//                    Calendar calendarYear = Calendar.getInstance();
//                    calendarYear.setTimeInMillis(dayagolong);
//                    return formatterYear.format(calendarYear.getTime()) + "";
//                }
//
//            }
//
//        } catch (java.text.ParseException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
}
