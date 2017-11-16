package com.numnu.android.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.numnu.android.R;
import com.numnu.android.activity.GoogleMapActivity;
import com.numnu.android.activity.MainActivity;
import com.numnu.android.fragments.EventDetail.EventItemsCategoryFragment;
import com.numnu.android.fragments.EventDetail.EventPostsFragment;
import com.numnu.android.utils.AppBarStateChangeListener;
import com.numnu.android.utils.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by thulir on 9/10/17.
 */

public class LocationDetailFragment extends Fragment implements View.OnClickListener,EasyPermissions.PermissionCallbacks  {

    private Context context;
    private TextView viewEventMap, eventName, city, eventDate, eventTime;
    private ImageView eventImageView;
    private AppBarLayout appBarLayout;
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private static final String[] LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int RC_LOCATION_PERM = 1;



    public static LocationDetailFragment newInstance() {
        return new LocationDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        eventDate = view.findViewById(R.id.txt_event_date);
        eventTime = view.findViewById(R.id.txt_event_time);

        eventImageView = view.findViewById(R.id.current_event_image);


        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.location);
        TextView toolbarTitle1 = view.findViewById(R.id.toolbar_title1);
        toolbarTitle1.setText(R.string.location);
        ImageView toolbarIcon = view.findViewById(R.id.toolbar_image);
        ImageView collapsedtoolbarIcon = view.findViewById(R.id.toolbar_image1);
        ImageView toolbarBackIcon = view.findViewById(R.id.toolbar_back);
        ImageView collapsedtoolbarBackIcon = view.findViewById(R.id.toolbar_back1);
        final Toolbar toolbar1 = view.findViewById(R.id.toolbar1);

        toolbarBackIcon.setOnClickListener(this);
        collapsedtoolbarBackIcon.setOnClickListener(this);
        toolbar1.setOnClickListener(this);


        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(inflater);
            }
        });
        collapsedtoolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(inflater);
            }
        });

        appBarLayout = view.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
             switch (state.name()){

                 case "EXPANDED":
                     toolbar1.setVisibility(View.GONE);
                     view.findViewById(R.id.tabs).setVisibility(View.VISIBLE);
                     break;

                 case "IDLE":
                     toolbar1.setVisibility(View.GONE);
                     view.findViewById(R.id.tabs).setVisibility(View.VISIBLE);
                     break;
                 case "COLLAPSED":
                     toolbar1.setVisibility(View.VISIBLE);
                     view.findViewById(R.id.tabs).setVisibility(View.GONE);
                     break;
             }


            }
        });

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

        return view;
    }

    protected void loadMap(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {
            // Map is ready
            if (hasLocationPermission()) {

                // Add a marker in Montreal and move the camera
                LatLng sydney = new LatLng(45.5088400, -73.5878100);
                map.addMarker(new MarkerOptions().position(sydney).title("Marker in Montreal"));
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
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomSheetView = inflater.inflate(R.layout.dialog_event_bottomsheet,null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

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

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean loginStatus =  PreferencesHelper.getPreferenceBoolean(getActivity(),PreferencesHelper.PREFERENCE_LOGGED_IN);
                if (!loginStatus) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    bottomSheetDialog.dismiss();
                }else if (loginStatus){
                    Toast.makeText(getActivity(), "Bookmarked this page", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new EventPostsFragment(), "Posts");
        adapter.addFragment(new EventItemsCategoryFragment(), "Items");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                getActivity().onBackPressed();
                break;

            case R.id.toolbar_back1:
                getActivity().onBackPressed();
                break;
            case R.id.toolbar1:
                appBarLayout.setExpanded(true);
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

