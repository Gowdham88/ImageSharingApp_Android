package com.numnu.android.fragments.detail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.numnu.android.R;
import com.numnu.android.activity.GoogleMapActivity;
import com.numnu.android.activity.webFragment;
import com.numnu.android.adapter.HorizontalContentAdapter;
import com.numnu.android.fragments.auth.LoginFragment;
import com.numnu.android.fragments.eventdetail.EventBusinessFragment;
import com.numnu.android.fragments.eventdetail.EventItemsCategoryFragment;
import com.numnu.android.fragments.eventdetail.EventPostsFragment;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.EventDetailResponse;
import com.numnu.android.network.response.EventlinksItem;
import com.numnu.android.utils.ContentWrappingViewPager;
import com.numnu.android.utils.CustomScrollView;
import com.numnu.android.utils.PreferencesHelper;
import com.numnu.android.utils.TouchImageView;
import com.numnu.android.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thulir on 9/10/17.
 */

public class EventDetailFragment extends Fragment implements View.OnClickListener {

    SearchView searchViewFood, searchViewLocation;
    private Context context;
    TextView weblink1, weblink2, weblink3;
    private TextView viewEventMap, eventName, city, eventStartDate, eventEndDate,morebutton;
    private ImageView eventImageView;
    private TextView eventDescription;
    private AppBarLayout appBarLayout;
    private PopupWindow pw;
    HorizontalContentAdapter adapter;
    RecyclerView recyclerView;
    private ViewPager viewPager;
    private CustomScrollView nestedScrollView;
    private Boolean isExpanded = false;
    private String eventId="34";
    private EventDetailResponse eventDetailResponse;
    private List<EventlinksItem> eventlinks ;
    // Create a storage reference from our app
    StorageReference storageRef ;
    private FirebaseStorage storage;
    StorageReference imageref;
    private Uri imgPath;
    public ProgressDialog mProgressDialog;
     int Max=4;

    public static EventDetailFragment newInstance() {
        return new EventDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            eventId = bundle.getString("eventId","34");
        }

         storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
         storageRef = storage.getReference();



    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        viewPager = view.findViewById(R.id.event_viewpager);

        recyclerView=(RecyclerView)view.findViewById(R.id.business_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        weblink1 = view.findViewById(R.id.txt_weblink_1);
        weblink2 = view.findViewById(R.id.txt_weblink_2);
        weblink3 = view.findViewById(R.id.txt_weblink_3);


        weblink1.setOnClickListener(this);
        weblink2.setOnClickListener(this);
        weblink3.setOnClickListener(this);


        viewEventMap = view.findViewById(R.id.txt_view_event_map);
        eventDescription = view.findViewById(R.id.event_description);

        eventName = view.findViewById(R.id.event_name);
        city = view.findViewById(R.id.txt_city);
        eventStartDate = view.findViewById(R.id.txt_event_start_date);
//        eventEndDate = view.findViewById(R.id.txt_event_end_date);
        nestedScrollView= view.findViewById(R.id.nestedScrollView);

        eventImageView = view.findViewById(R.id.current_event_image);
        eventImageView.setOnClickListener(this);
        viewEventMap.setOnClickListener(this);
        morebutton = view.findViewById(R.id.more_button);
        morebutton.setVisibility(View.GONE);
        if(eventDescription.getLineCount()>= Max){
            morebutton.setVisibility(View.VISIBLE);
        }
        morebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isExpanded) {

                    isExpanded = false;
                    eventDescription.setMaxLines(4);
                    morebutton.setText("more");

                } else {

                    isExpanded = true;
                    eventDescription.setMaxLines(1000);
                    morebutton.setText("less");

                }

            }
        });




        setupWebLinks();

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.event);
        ImageView toolbarIcon = view.findViewById(R.id.toolbar_image);
        RelativeLayout toolbarBackIcon = view.findViewById(R.id.toolbar_back);
        final Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbarBackIcon.setOnClickListener(this);
        toolbar.setOnClickListener(this);

        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(inflater);
            }
        });
        if(Utils.isNetworkAvailable(context)) {
            getEventDetails(eventId);
        }else {
            showAlert();
        }
        return view;
    }



    private void getEventDetails(String id)
    {
        showProgressDialog();
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<EventDetailResponse> call=apiServices.getEvent(id);
        call.enqueue(new Callback<EventDetailResponse>() {
            @Override
            public void onResponse(Call<EventDetailResponse> call, Response<EventDetailResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    eventDetailResponse = response.body();
                    eventlinks = eventDetailResponse.getEventlinks();
                    sortByDisplayOrder(eventlinks);
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<EventDetailResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //sort event links by display order
    private void sortByDisplayOrder(List<EventlinksItem> list) {
        Collections.sort(list, new Comparator<EventlinksItem>() {
            @Override public int compare(EventlinksItem p1, EventlinksItem p2) {
                return p1.getDisplayorder()- p2.getDisplayorder();
            }

        });
    }

    private void updateUI( ) {

        if(!eventDetailResponse.getEventimages().isEmpty()&&eventDetailResponse.getEventimages().get(0).getImageurl()!=null) {
            storageRef.child(eventDetailResponse.getEventimages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    imgPath = uri;
                    Picasso.with(context).load(uri)
                            .placeholder(R.drawable.background)
                            .fit()
                            .into(eventImageView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }

        eventName.setText(eventDetailResponse.getName());
        eventDescription.setText(eventDetailResponse.getDescription());
        if(eventDescription.getLineCount()>= Max){
            morebutton.setVisibility(View.VISIBLE);
        }
        morebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isExpanded) {

                    isExpanded = false;
                    eventDescription.setMaxLines(4);
                    morebutton.setText("more");

                } else {

                    isExpanded = true;
                    eventDescription.setMaxLines(1000);
                    morebutton.setText("less");

                }

            }
        });


        String StrtDate=eventDetailResponse.getStartsat();
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        java.util.Date date = null;
        try
        {
            date = form.parse(StrtDate);
        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("MMM dd,hh:mm a");
        String StartDateStr = postFormater.format(date);
//        eventStartDate.setText(StartDateStr);

        String EndDate=eventDetailResponse.getEndsat();
        SimpleDateFormat endformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        java.util.Date endate = null;
        try
        {
            endate = endformat.parse(EndDate);
        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }
        SimpleDateFormat Formater = new SimpleDateFormat("MMM dd,hh:mm a");
        String endDateStr = Formater.format(endate);
//        eventStartDate.setText(endDateStr);
        String Serverdate=StartDateStr+" - "+endDateStr;
        eventStartDate.setText(Serverdate);

        city.setText(eventDetailResponse.getLocation().getName());

        if(!eventDetailResponse.getEventlinks().isEmpty()) {
            if(eventDetailResponse.getEventlinks().size()>0){
                weblink1.setText(eventDetailResponse.getEventlinks().get(0).getLinktext());
            }

            if(eventDetailResponse.getEventlinks().size()>1){
                weblink2.setText(eventDetailResponse.getEventlinks().get(1).getLinktext());
            }

            if(eventDetailResponse.getEventlinks().size()>2){
                weblink3.setText(eventDetailResponse.getEventlinks().get(2).getLinktext());
            }

        }

        adapter = new HorizontalContentAdapter(context,eventDetailResponse.getTags());
        recyclerView.setAdapter(adapter);
        hideProgressDialog();

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
                    transaction.replace(R.id.frame_layout, logFragment);
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
                    transaction.replace(R.id.frame_layout, logFragment);
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

    private void setupWebLinks() {


//        weblink1.setTextColor(ContextCompat.getColor(context, R.color.blue));
//        weblink2.setTextColor(ContextCompat.getColor(context, R.color.blue));
//        weblink3.setTextColor(ContextCompat.getColor(context, R.color.blue));
//

//        viewEventMap.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(EventBusinessFragment.newInstance(eventId), "Businesses");
        adapter.addFragment(EventItemsCategoryFragment.newInstance(eventId), "Items");
        adapter.addFragment(EventPostsFragment.newInstance(eventId), "Posts");
        viewPager.setAdapter(adapter);
        // to keep all three tabs in memory. Remove below line if app lags and then optimize tab fragments.
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_weblink_1:

//                String url = "https://www.youtube.com/";
//                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//                // set toolbar color
//                builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent));
//                CustomTabsIntent customTabsIntent = builder.build();
//                customTabsIntent.launchUrl(context, Uri.parse(url));

                if(!eventDetailResponse.getEventlinks().isEmpty()) {

                        if(eventDetailResponse.getEventlinks().size()>0){
                        Intent web1 = new Intent(getActivity(), webFragment.class);
                            web1.putExtra("url", eventDetailResponse.getEventlinks().get(0).getWeblink());
                        startActivity(web1);
                    }

                }
                break;

            case R.id.txt_weblink_2:
//                String url2 = "https://www.google.com/";
//                CustomTabsIntent.Builder builder2 = new CustomTabsIntent.Builder();
//                // set toolbar color
//                builder2.setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent));
//                customTabsIntent = builder2.build();
//                customTabsIntent.launchUrl(context, Uri.parse(url2));

                if(!eventDetailResponse.getEventlinks().isEmpty()) {

                    if(eventDetailResponse.getEventlinks().size()>1){
                        Intent web2 = new Intent(getActivity(), webFragment.class);
                        web2.putExtra("url", eventDetailResponse.getEventlinks().get(1).getWeblink());
                        startActivity(web2);
                    }

                }
                break;

            case R.id.txt_weblink_3:
//                String url3 = "https://www.facebook.com/";
//                CustomTabsIntent.Builder builder3 = new CustomTabsIntent.Builder();
                // set toolbar color
//                builder3.setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent));
//                customTabsIntent = builder3.build();
//                customTabsIntent.launchUrl(context, Uri.parse(url3));

                if(!eventDetailResponse.getEventlinks().isEmpty()) {

                    if(eventDetailResponse.getEventlinks().size()>2){
                        Intent web3 = new Intent(getActivity(), webFragment.class);
                        web3.putExtra("url", eventDetailResponse.getEventlinks().get(2).getWeblink());
                        startActivity(web3);
                    }

                }
                break;

            case R.id.toolbar_back:
                getActivity().onBackPressed();
                break;

            case R.id.toolbar:
               nestedScrollView.scrollTo(0,0);
                break;

            case R.id.current_event_image:
                initiatePopupWindow();
                break;

            case R.id.txt_view_event_map:
             Intent newintent=new Intent(context, GoogleMapActivity.class);
                newintent.putExtra("latitude", eventDetailResponse.getLocation().getLattitude());
                newintent.putExtra("longitude", eventDetailResponse.getLocation().getLongitude());
                newintent.putExtra("name", eventDetailResponse.getLocation().getName());
                EventDetailFragment.this.startActivity(newintent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

                break;


        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private int mCurrentPosition = -1;

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            if (position != mCurrentPosition) {
                Fragment fragment = (Fragment) object;
                ContentWrappingViewPager pager = (ContentWrappingViewPager) container;
                if (fragment != null && fragment.getView() != null) {
                    mCurrentPosition = position;
                    pager.measureCurrentView(fragment.getView());
                }
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void showAlert() {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage("No Internet connection");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void initiatePopupWindow() {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.image_popup,null);
        ImageView image=(ImageView)layout.findViewById(R.id.popup_image);
        Picasso.with(context).load(imgPath)
                .placeholder(R.drawable.background)
                .into(image);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        pw = new PopupWindow(layout, lp.width, lp.height, true);
        pw.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
        Animation hide = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
        layout.startAnimation(hide);

        LinearLayout btncancel = layout.findViewById(R.id.btncancelcat);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw.dismiss();
            }
        });

//        ImageView imageView = layout.findViewById(R.id.popup_image);
//
//        Picasso.with(context).load(imgPath.toString())
//                .placeholder(R.drawable.background)
//                .into(imageView);
    }

}

