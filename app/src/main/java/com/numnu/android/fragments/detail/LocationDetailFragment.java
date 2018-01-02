package com.numnu.android.fragments.detail;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.numnu.android.R;
import com.numnu.android.activity.GoogleMapActivity;
import com.numnu.android.adapter.HorizontalContentAdapter;
import com.numnu.android.fragments.eventdetail.EventBusinessDetailFragment;
import com.numnu.android.fragments.eventdetail.EventItemsCategoryFragment;
import com.numnu.android.fragments.eventdetail.EventPostsFragment;
import com.numnu.android.fragments.auth.LoginFragment;
import com.numnu.android.fragments.locationdetail.LocationItemsTagsFragment;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.request.BookmarkRequestData;
import com.numnu.android.network.response.BookmarkResponse;
import com.numnu.android.network.response.ItemDetailsResponse;
import com.numnu.android.network.response.LocationDetailResponse;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.ContentWrappingViewPager;
import com.numnu.android.utils.CustomScrollView;
import com.numnu.android.utils.PreferencesHelper;
import com.numnu.android.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationDetailFragment extends Fragment implements View.OnClickListener,EasyPermissions.PermissionCallbacks  {

    private static final String TAG ="LocationDetail" ;
    private Context context;
    private TextView viewEventMap, eventName, city, businessName;
    LinearLayout openMap;
    TextView openmapTxt;
    private ImageView eventImageView,entityImageView;
    private AppBarLayout appBarLayout;
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private static final String[] LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int RC_LOCATION_PERM = 1;
    private CustomScrollView nestedScrollView;
    HorizontalContentAdapter adapter;
    RecyclerView recyclerView;
    LinearLayout busCntnRelLay;
    String name="name";
    private String  eventId;
    private ProgressDialog mProgressDialog;
    private String locationId;
    // Create a storage reference from our app
    StorageReference storageRef ;
    private FirebaseStorage storage;
    private LocationDetailResponse locationDetailResponse;
    private double latitude,longitude;
    private AlertDialog dialog;

    public static LocationDetailFragment newInstance() {
        return new LocationDetailFragment();
    }

    public static LocationDetailFragment newInstance(String locationId) {

        LocationDetailFragment locationDetailFragment = new LocationDetailFragment();
        Bundle args = new Bundle();
        args.putString("locationId", locationId);
        locationDetailFragment.setArguments(args);
        return locationDetailFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            locationId = bundle.getString("locationId");
        }


        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_location_detail, container, false);

        ViewPager viewPager = view.findViewById(R.id.event_viewpager);
        setupViewPager(viewPager);


        viewEventMap = view.findViewById(R.id.txt_view_event_map);
        eventName = view.findViewById(R.id.event_name);
        city = view.findViewById(R.id.txt_city);
        businessName = view.findViewById(R.id.business_name);
//        eventDate = view.findViewById(R.id.txt_event_date);
//        eventTime = view.findViewById(R.id.txt_event_time);
        openMap = view.findViewById(R.id.txt_open_map);
        openmapTxt=(TextView)view.findViewById(R.id.opne_txt);
        openmapTxt.setText("Open map");

        eventImageView = view.findViewById(R.id.current_event_image);
        entityImageView = view.findViewById(R.id.entity_image);
        recyclerView=(RecyclerView)view.findViewById(R.id.flatron_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        busCntnRelLay=(LinearLayout) view.findViewById(R.id.buss_content);
        busCntnRelLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventBusinessDetailFragment.newInstance("50"));
                transaction.addToBackStack(null).commit();
            }
        });
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.location);
        ImageView toolbarIcon = view.findViewById(R.id.toolbar_image);
        RelativeLayout toolbarBackIcon = view.findViewById(R.id.toolbar_back);
        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        nestedScrollView= view.findViewById(R.id.nestedScrollView);

        toolbarBackIcon.setOnClickListener(this);
        toolbar.setOnClickListener(this);
        openMap.setOnClickListener(this);


        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(inflater);
            }
        });

        if(Utils.isNetworkAvailable(context)) {
            getLocationDetails(locationId);
        }else {
            showAlert();
        }
        return view;
    }

    private void showAlert() {
    }

    private void getLocationDetails(String id)
    {
        showProgressDialog();
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<LocationDetailResponse> call=apiServices.getLocationDetail(id);
        call.enqueue(new Callback<LocationDetailResponse>() {
            @Override
            public void onResponse(Call<LocationDetailResponse> call, Response<LocationDetailResponse> response) {
                int responsecode = response.code();
                if(responsecode==200) {
                    locationDetailResponse = response.body();
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<LocationDetailResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateUI() {

        if(!locationDetailResponse.getLocationimages().isEmpty()&&locationDetailResponse.getLocationimages().get(0).getImageurl()!=null) {
            storageRef.child(locationDetailResponse.getLocationimages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
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

        if(!locationDetailResponse.getLocationbusiness().getImages().isEmpty()&&locationDetailResponse.getLocationbusiness().getImages().get(0).getImageurl()!=null) {
            storageRef.child(locationDetailResponse.getLocationbusiness().getImages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
        try {
            latitude = (double) locationDetailResponse.getLattitude();
            longitude = (double) locationDetailResponse.getLongitude();
        }catch (Exception e){
            Log.e(TAG, e+"");
        }


        mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.location_detailmap));
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    loadMap(map);
                }
            });
        } else {
            Toast.makeText(context, "Error - Map Fragment was null!!", Toast.LENGTH_SHORT).show();
        }

        eventName.setText(locationDetailResponse.getLocationbusiness().getBusinessname());
        city.setText(locationDetailResponse.getName());
        businessName.setText(locationDetailResponse.getLocationbusiness().getBusinessname());

        adapter = new HorizontalContentAdapter(context, locationDetailResponse.getLocationbusiness().getTags());
        recyclerView.setAdapter(adapter);

        hideProgressDialog();
    }


    protected void loadMap(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {
            // Map is ready
            if (hasLocationPermission()) {

                // Add a marker in Montreal and move the camera
                LatLng sydney = new LatLng(latitude, longitude);
                map.addMarker(new MarkerOptions().position(sydney).title("Marker"));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,17));
            }else {
                // Ask for one permission
                EasyPermissions.requestPermissions(
                        getActivity(),
                        getString(R.string.rationale_location),
                        RC_LOCATION_PERM,
                        LOCATION);
            }

        }
    }

    private boolean hasLocationPermission() {
        return EasyPermissions.hasPermissions(getActivity(), LOCATION);
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
                    postBookmark();
                    bottomSheetDialog.dismiss();
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
                    postBookmark();
                    bottomSheetDialog.dismiss();
                }
            }
        });
    }

    private void postBookmark()
    {
        showProgressDialog();
        String userId = PreferencesHelper.getPreference(context, PreferencesHelper.PREFERENCE_ID);
        BookmarkRequestData bookmarkRequestData = new BookmarkRequestData();
        bookmarkRequestData.setClientapp(Constants.CLIENT_APP);
        bookmarkRequestData.setClientip(Utils.getLocalIpAddress(context));
        bookmarkRequestData.setType(Constants.BOOKMARK_LOCATION);
        bookmarkRequestData.setEntityid(locationId);
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


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new LocationpostFragment().newInstance(locationId), "Posts");
        adapter.addFragment(LocationItemsTagsFragment.newInstance(locationId), "Items");
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
            case R.id.txt_open_map:
                Intent newintent=new Intent(context, GoogleMapActivity.class);
                newintent.putExtra("name",name);
                LocationDetailFragment.this.startActivity(newintent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

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

}

