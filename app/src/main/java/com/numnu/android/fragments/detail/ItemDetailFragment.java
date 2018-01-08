package com.numnu.android.fragments.detail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.numnu.android.R;
import com.numnu.android.activity.SliceActivity;
import com.numnu.android.adapter.HorizontalContentAdapter;
import com.numnu.android.fragments.itemdetail.ItemLocationsFragment;
import com.numnu.android.fragments.auth.LoginFragment;
import com.numnu.android.fragments.itemdetail.ItemPostsFragment;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.request.BookmarkRequestData;
import com.numnu.android.network.response.BookmarkResponse;
import com.numnu.android.network.response.ItemDetailsResponse;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.ContentWrappingViewPager;
import com.numnu.android.utils.CustomScrollView;
import com.numnu.android.utils.PreferencesHelper;
import com.numnu.android.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thulir on 9/10/17.
 */

public class ItemDetailFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private TextView viewEventMap, eventName, city, eventDate, eventTime,morebutton,businessName;
    private ImageView eventImageView,entityImageView;
    private TextView eventDescription;
    private PopupWindow pw;
    LinearLayout busCntnRelLay;
    private CustomScrollView nestedScrollView;
    HorizontalContentAdapter adapter1,adapter2;
    RecyclerView recyclerView1,recyclerView2;
    private Boolean isExpanded = false;
    private String itemId="115";
    private ProgressDialog mProgressDialog;
    ItemDetailsResponse itemDetailsResponse;
    // Create a storage reference from our app
    StorageReference storageRef ;
    private FirebaseStorage storage;
    int Max=4;
    private Uri itemimgPath;
    private AlertDialog dialog;

    public static ItemDetailFragment newInstance(String itemId) {

        ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putString("itemId", itemId);
        itemDetailFragment.setArguments(args);
        return itemDetailFragment;

    }

    public static ItemDetailFragment newInstance() {

        return new ItemDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            itemId = bundle.getString("itemId");
        }


        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_item_detail, container, false);

        ViewPager viewPager = view.findViewById(R.id.event_viewpager);
        setupViewPager(viewPager);

        viewEventMap = view.findViewById(R.id.txt_view_event_map);
        eventDescription = view.findViewById(R.id.event_description);
        eventName = view.findViewById(R.id.event_name);
        businessName = view.findViewById(R.id.business_name);
//        eventDate = view.findViewById(R.id.txt_event_date);
//        eventTime = view.findViewById(R.id.txt_event_time);
        nestedScrollView= view.findViewById(R.id.nestedScrollView);
        recyclerView1=(RecyclerView)view.findViewById(R.id.item_tags_recyclerview);
        recyclerView2=(RecyclerView)view.findViewById(R.id.flatron_recyclerview);

        recyclerView1.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
//        adapter = new HorizontalContentAdapter(context, eventDetailResponse.getTags());
//        recyclerView2.setAdapter(adapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        eventImageView = view.findViewById(R.id.current_event_image);
        entityImageView = view.findViewById(R.id.entity_image);
        eventImageView.setOnClickListener(this);
        busCntnRelLay=(LinearLayout) view.findViewById(R.id.buss_content);
        busCntnRelLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, SearchBusinessDetailFragment.newInstance("50"));
                transaction.addToBackStack(null).commit();
            }
        });




        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.item);
        ImageView toolbarIcon = view.findViewById(R.id.toolbar_image);
        RelativeLayout toolbarBackIcon = view.findViewById(R.id.toolbar_back);
        final Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbarBackIcon.setOnClickListener(this);
        toolbar.setOnClickListener(this);
        morebutton = view.findViewById(R.id.more_button);
        morebutton.setVisibility(View.GONE);


        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertshare();
            }
        });
        getItemsDetails(itemId);
        return view;
    }


    private void getItemsDetails(String id)
    {
        showProgressDialog();
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<ItemDetailsResponse> call=apiServices.getItem(id);
        call.enqueue(new Callback<ItemDetailsResponse>() {
            @Override
            public void onResponse(Call<ItemDetailsResponse> call, Response<ItemDetailsResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    itemDetailsResponse = response.body();
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<ItemDetailsResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateUI() {

        if(!itemDetailsResponse.getItemimages().isEmpty()&&itemDetailsResponse.getItemimages().get(0).getImageurl()!=null) {
            storageRef.child(itemDetailsResponse.getItemimages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    itemimgPath = uri;
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

        if(!itemDetailsResponse.getBusinessdetail().getImages().isEmpty()&&itemDetailsResponse.getBusinessdetail().getImages().get(0).getImageurl()!=null) {
            storageRef.child(itemDetailsResponse.getBusinessdetail().getImages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Picasso.with(context).load(uri)
                            .placeholder(R.drawable.background)
                            .fit()
                            .into(entityImageView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }

        eventName.setText(itemDetailsResponse.getName());
        businessName.setText(itemDetailsResponse.getBusinessdetail().getBusinessname());
        if(!itemDetailsResponse.getDescription().isEmpty()) {
            eventDescription.setText(itemDetailsResponse.getDescription());
        }else {
            eventDescription.setVisibility(View.GONE);
        }
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

        if(!itemDetailsResponse.getItemlinks().isEmpty()) {
            if(itemDetailsResponse.getItemlinks().size()>0){
//                weblink1.setText(itemDetailsResponse.getEventlinks().get(0).getLinktext());
            }

            if(itemDetailsResponse.getItemlinks().size()>1){
//                weblink2.setText(eventDetailResponse.getEventlinks().get(1).getLinktext());
            }

            if(itemDetailsResponse.getItemlinks().size()>2){
//                weblink3.setText(eventDetailResponse.getEventlinks().get(2).getLinktext());
            }

        }
        if(!itemDetailsResponse.getTags().isEmpty()) {
            adapter1 = new HorizontalContentAdapter(context, itemDetailsResponse.getTags());
            recyclerView1.setAdapter(adapter1);
        }else {
            recyclerView1.setVisibility(View.GONE);
        }

        adapter2 = new HorizontalContentAdapter(context, itemDetailsResponse.getBusinessdetail().getTags());
        recyclerView2 .setAdapter(adapter2);

        hideProgressDialog();
    }
    public void showProgressDialog() {


        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(getActivity());
        //View view = getLayoutInflater().inflate(R.layout.progress);
        alertDialog.setView(R.layout.progress);
        dialog = alertDialog.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void hideProgressDialog(){
        if(dialog!=null)
        dialog.dismiss();
    }

    public void showAlertshare() {

        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View deleteDialogView = factory.inflate(R.layout.bookmark_layout, null);
        final android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(getActivity());
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
                    postBookmark();
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

    private void postBookmark()
    {
        showProgressDialog();
        String userId = PreferencesHelper.getPreference(context, PreferencesHelper.PREFERENCE_ID);
        BookmarkRequestData bookmarkRequestData = new BookmarkRequestData();
        bookmarkRequestData.setClientapp(Constants.CLIENT_APP);
        bookmarkRequestData.setClientip(Utils.getLocalIpAddress(context));
        bookmarkRequestData.setType(Constants.BOOKMARK_ITEM);
        bookmarkRequestData.setEntityid(itemId);
        bookmarkRequestData.setEntityname(eventName.getText().toString());

        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<BookmarkResponse> call=apiServices.postBookmark(userId,bookmarkRequestData);
        call.enqueue(new Callback<BookmarkResponse>() {
            @Override
            public void onResponse(Call<BookmarkResponse> call, Response<BookmarkResponse> response) {
                int responsecode = response.code();
                if(responsecode==201) {
                    BookmarkResponse bookmarkResponse = response.body();
                    Toast.makeText(context, "Bookmarked this page", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                }else if(responsecode==422) {

                    Toast.makeText(context, "Already Bookmarked!!", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ItemPostsFragment(), "Posts");
        adapter.addFragment(new ItemLocationsFragment(), "Locations");
//        adapter.addFragment(new EventsFragment(), "Events");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                getActivity().onBackPressed();
                break;

            case R.id.toolbar:
                nestedScrollView.scrollTo(0,0);
                break;

            case R.id.current_event_image:
                initiatePopupWindow();
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

    private void initiatePopupWindow() {
        if(itemimgPath!=null){
            Intent intent=new Intent(getActivity(), SliceActivity.class);
            intent.putExtra("imagepath",itemimgPath.toString());
            context.startActivity(intent);
        }
        else{
            Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
        }


    }
}

