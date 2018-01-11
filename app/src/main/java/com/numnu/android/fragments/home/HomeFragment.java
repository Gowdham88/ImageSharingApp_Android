package com.numnu.android.fragments.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.numnu.android.LocationUpdatesService;
import com.numnu.android.R;
import com.numnu.android.adapter.CurrentUpEventsAdapter;
import com.numnu.android.adapter.search.PlaceAutocompleteRecyclerViewAdapter;
import com.numnu.android.adapter.search.SearchResultsAdapter;
import com.numnu.android.fragments.search.EventsFragment;
import com.numnu.android.fragments.search.EventsFragmentwithToolbar;
import com.numnu.android.fragments.search.SearchPostsFragment;
import com.numnu.android.fragments.search.SearchBusinessFragment;
import com.numnu.android.fragments.search.SearchItemsFragment;
import com.numnu.android.fragments.search.UsersFragment;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.PreferencesHelper;
import com.numnu.android.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;

import static com.numnu.android.utils.Utils.hideKeyboard;


/**
 * Created by thulir on 9/10/17.
 */

public class HomeFragment extends Fragment implements View.OnKeyListener, EasyPermissions.PermissionCallbacks {


    EditText searchViewFood,searchViewLocation;
    private RecyclerView searchListView;
    private TabLayout tabLayout;
    NestedScrollView nestedScrollView;
    private RecyclerView currentEventsList,currentEventsList1,currentEventsList2, pastEventsList;
    private ArrayList<String> stringlist,stringlist1;
    Context context;
    Toolbar toolbar;
    BottomNavigationView mBottomNavigationView;
//    private ImageView toolbarBackIcon,mSearchIcon,mLocationIcon;
    RelativeLayout toolbarBackIcon;
    RelativeLayout Homelinlay1,Homelinlay2,Homelinlay3;
    AppBarLayout AppLay;
    private ViewPager viewPager;
    ImageView foodcloseimg,locationcloseimg;
    // The BroadcastReceiver used to listen from broadcasts from the service.
    private MyReceiver myReceiver;
    private Boolean isLocationSetByGps=false;
    private static final String[] LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int RC_LOCATION_PERM = 1;


    /**
     * GeoDataClient wraps our service connection to Google Play services and provides access
     * to the Google Places API for Android.
     */
    protected GeoDataClient mGeoDataClient;

    private PlaceAutocompleteRecyclerViewAdapter mAdapter;



    private ImageView googleLogo;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(hasLocationPermission()) {
            context.startService(new Intent(context, LocationUpdatesService.class));
        }else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    HomeFragment.this,
                    getString(R.string.rationale_location),
                    RC_LOCATION_PERM,
                    LOCATION);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        int fragments = getFragmentManager().getBackStackEntryCount();
        if (fragments <= 1) {
            toolbarBackIcon.setVisibility(View.GONE);
        }
        else {
            toolbarBackIcon.setVisibility(View.GONE);
        }

        googleLogo.setVisibility(View.GONE);
        searchViewFood.setText("");
        searchViewLocation.setText("");
        nestedScrollView.setVisibility(View.VISIBLE);
        searchListView.setVisibility(View.GONE);

        if(viewPager.getVisibility()==View.VISIBLE) {
            toolbarBackIcon.setVisibility(View.VISIBLE);
        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(this);
        myReceiver = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(myReceiver, new IntentFilter(LocationUpdatesService.ACTION_BROADCAST));
        String s= PreferencesHelper.getPreference(getActivity(),PreferencesHelper.PREFERENCE_SEARCH_LOCATION);
        isLocationSetByGps = true;
        searchViewLocation.setText(s);


    }

    private boolean hasLocationPermission() {
        return EasyPermissions.hasPermissions(getActivity(), LOCATION);
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(myReceiver);
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        googleLogo = view.findViewById(R.id.img_google);

        currentEventsList = view.findViewById(R.id.current_up_recyclerview);
        currentEventsList1 = view.findViewById(R.id.current_up_recyclerview1);
        currentEventsList2 = view.findViewById(R.id.current_up_recyclerview2);
        pastEventsList = view.findViewById(R.id.past_recyclerview);
        searchListView = view.findViewById(R.id.search_results_recyclerview);
        searchViewFood=view.findViewById(R.id.et_search_food);
        searchViewLocation=view.findViewById(R.id.et_search_location);
        tabLayout = view.findViewById(R.id.tabs);
        nestedScrollView = view.findViewById(R.id.events_scroll_view);
        toolbarBackIcon = view.findViewById(R.id.toolbar_back);
        Homelinlay1=(RelativeLayout)view.findViewById(R.id.search_linlay1);
        Homelinlay2=(RelativeLayout)view.findViewById(R.id.search_linlay2);
        Homelinlay3=(RelativeLayout)view.findViewById(R.id.search_linlay3);
        AppLay=(AppBarLayout)view.findViewById(R.id.appbar_layout);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        Homelinlay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getActivity());
            }
        });
        Homelinlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getActivity());
            }
        });
        Homelinlay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getActivity());
            }
        });
        toolbarBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getVisibility()==View.VISIBLE){

                    nestedScrollView.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                    toolbarBackIcon.setVisibility(View.GONE);

                }else {
                    getActivity().finish();
                    // handle back button
                }
            }
        });
        AppLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getActivity());
            }
        });


        RelativeLayout viewCurrentEventsList = view.findViewById(R.id.view_current_event_list);
        RelativeLayout viewCurrentEventsList1 = view.findViewById(R.id.view_current_event_list1);
        RelativeLayout viewCurrentEventsList2 = view.findViewById(R.id.view_current_event_list2);

        ImageView viewPastEventsList = view.findViewById(R.id.view_past_event_list);

        viewCurrentEventsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventsFragmentwithToolbar.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        viewCurrentEventsList1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventsFragmentwithToolbar.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        viewCurrentEventsList2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventsFragmentwithToolbar.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        viewPastEventsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventsFragmentwithToolbar.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        setupRecyclerView();


        // Construct a GeoDataClient for the Google Places API for Android.
        mGeoDataClient = Places.getGeoDataClient(getActivity(), null);

        setupSearchListener();

        final android.support.v7.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nestedScrollView.scrollTo(0,0);
            }
        });

        return view;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupSearchListener() {

        searchViewLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!searchViewLocation.getText().toString().trim().equals("")){
                    searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
//                    searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_close),null);
                    searchViewLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(hasFocus){
                                searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
//                                searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_close),null);
                            }
                        }
                    });
                    searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);

                }else {
                    searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                locationSearch(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!searchViewLocation.getText().toString().trim().equals("")){

                    searchViewLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(hasFocus){
                                searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                                searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_close),null);
                            }

                        }
                    });


                }else{
                    searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }
            }
        });




        searchViewFood.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!searchViewFood.getText().toString().trim().equals("")){
                    searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    searchViewFood.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(hasFocus){
                                searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                                searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_close),null);
                            }
                        }
                    });

                }else{
                    searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (!searchViewFood.getText().toString().trim().equals("")){

                    searchViewFood.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(hasFocus){
                                searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                                searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_close),null);
                            }
                            searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                        }

                    });
                    searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_close),null);

                }else{
                    searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }
            }


        });



        searchViewFood.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (searchViewFood.getCompoundDrawables()[2]!= null){
                        if (motionEvent.getX() >=(searchViewFood.getRight()-searchViewFood.getLeft() - searchViewFood.getCompoundDrawables()[2].getBounds().width())){
                            searchViewFood.setText("");

                            searchViewFood.clearFocus();
                                Utils.hideKeyboard(getActivity());

                        }
                    }
                }
//                checkKeyBoardUp(view);
                return false;
            }
        });
        searchViewLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (searchViewLocation.getCompoundDrawables()[2]!= null){
                        if (motionEvent.getX() >=(searchViewLocation.getRight()-searchViewLocation.getLeft() - searchViewLocation.getCompoundDrawables()[2].getBounds().width())){
                            searchViewLocation.setText("");
                            searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                            searchViewLocation.clearFocus();
                            Utils.hideKeyboard(getActivity());
                        }
                    }
                }
//                checkKeyBoardUp(view);
                return false;
            }
        });

        searchViewFood.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    //do here your stuff
                    foodSearch(textView.getText().toString());
                    Utils.hideKeyboard(getActivity());
                    return true;
                }
                return false;
            }

        });

        searchViewLocation.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //do here your stuff
                    if(searchListView.getVisibility()!=View.VISIBLE && !searchViewFood.getText().toString().isEmpty()) {

                        nestedScrollView.setVisibility(View.VISIBLE);
                    }
                    searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    Utils.hideKeyboard(getActivity());
                    return true;
                }
                return false;
            }

        });

    }

    private void locationSearch(final CharSequence charSequence) {
       if(!charSequence.toString().equals("")){

            if(!isLocationSetByGps) {
                searchListView.setVisibility(View.VISIBLE);
                googleLogo.setVisibility(View.VISIBLE);
                nestedScrollView.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);

                AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
                // Set up the adapter that will retrieve suggestions from the Places Geo Data Client.
                mAdapter = new PlaceAutocompleteRecyclerViewAdapter(getActivity(), mGeoDataClient, Constants.BOUNDS_GREATER_SYDNEY, autocompleteFilter);
                mAdapter.getFilter().filter(charSequence.toString());
                mAdapter.setOnItemClickListener(new SearchResultsAdapter.OnItemClickListener() {
                    @Override
                    public void onRecyclerItemClick(View view, int position) {
                        final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.NORMAL);
                        String s = mAdapter.getItem(position).getPrimaryText(STYLE_BOLD).toString();
                        searchViewLocation.setText(s);
                        searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                        Utils.hideKeyboard(getActivity());
                        googleLogo.setVisibility(View.GONE);
                        searchListView.setVisibility(View.GONE);
                        if (searchViewFood.getText().toString().isEmpty()) {
                            nestedScrollView.setVisibility(View.VISIBLE);
                        } else {
                            showSearchResults();
                        }


                    }
                });
                searchListView.setAdapter(mAdapter);
            }else {
                isLocationSetByGps = false;
                searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
            }

       }else {
           googleLogo.setVisibility(View.GONE);
           nestedScrollView.setVisibility(View.VISIBLE);
           searchListView.setVisibility(View.GONE);
       }
    }



    private void showSearchResults() {
        googleLogo.setVisibility(View.GONE);
        searchListView.setVisibility(View.GONE);
        toolbarBackIcon.setVisibility(View.VISIBLE);
        nestedScrollView.setVisibility(View.GONE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        setupViewPager(viewPager);
        searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        tabLayout.setupWithViewPager(viewPager);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(this);
    }

    private void foodSearch(String fooditem) {
        if(!fooditem.equals("")){

                    showSearchResults();

        }else {
            nestedScrollView.setVisibility(View.VISIBLE);
            googleLogo.setVisibility(View.GONE);
            searchListView.setVisibility(View.GONE);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(EventsFragment.newInstance(searchViewFood.getText().toString()), "Events");
        adapter.addFragment(SearchBusinessFragment.newInstance(searchViewFood.getText().toString()), "Businesses");
        adapter.addFragment( SearchItemsFragment.newInstance(searchViewFood.getText().toString()), "Items");
        adapter.addFragment( SearchPostsFragment.newInstance(searchViewFood.getText().toString()), "Posts");
        adapter.addFragment( UsersFragment.newInstance(searchViewFood.getText().toString()), "Users");
//        adapter.addFragment(new SearchListFragment(), "Lists");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        context.startService(new Intent(context, LocationUpdatesService.class));
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
        super.onAttach(context);
        this.context = context;
    }



    private void setupRecyclerView() {
//        stringlist = new ArrayList<>();
//        stringlist1 = new ArrayList<>();
//
//        for (int i = 1; i <= 10; i++) {
//            stringlist.add("Flatron");
//        }
            CurrentUpEventsAdapter currentUpAdapter = new CurrentUpEventsAdapter(context);
            currentEventsList.setAdapter(currentUpAdapter);

        CurrentUpEventsAdapter currentUpAdapter1 = new CurrentUpEventsAdapter(context);
        currentEventsList1.setAdapter(currentUpAdapter1);

        CurrentUpEventsAdapter currentUpAdapter2 = new CurrentUpEventsAdapter(context);
        currentEventsList2.setAdapter(currentUpAdapter2);


//        for (int i = 1; i <= 10; i++) {
//            stringlist1.add("Flatron");
//        }
////            PastEventsAdapter pastEventsAdapter = new PastEventsAdapter(context, stringlist1);
//            pastEventsList.setAdapter(pastEventsAdapter);


    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if(viewPager.getVisibility()==View.VISIBLE){

                    nestedScrollView.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                    toolbarBackIcon.setVisibility(View.GONE);
                    searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);

                }else {
                    getActivity().finish();
                    // handle back button
                }
                return true;
            }
        }

        return false;
    }


    /**
     * Receiver for broadcasts sent by {@link LocationUpdatesService}.
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(LocationUpdatesService.EXTRA_LOCATION);
            if (location != null) {
                String s = getAddress(location);
                isLocationSetByGps = true;
                searchViewLocation.setText(s);
                PreferencesHelper.setPreference(getActivity(),PreferencesHelper.PREFERENCE_SEARCH_LOCATION,s);
                PreferencesHelper.setPreference(getActivity(),PreferencesHelper.PREFERENCE_SEARCH_LATITUDE, String.valueOf(location.getLatitude()));
                PreferencesHelper.setPreference(getActivity(),PreferencesHelper.PREFERENCE_SEARCH_LONGITUDE,String.valueOf(location.getLongitude()));

            }
        }
    }

    public String getAddress(Location location) {

        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        // Address found using the Geocoder.
        List<Address> addresses = null;

        try {
            // Using getFromLocation() returns an array of Addresses for the area immediately
            // surrounding the given latitude and longitude. The results are a best guess and are
            // not guaranteed to be accurate.
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, we get just a single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
//            errorMessage = getString(R.string.service_not_available);
//            Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
//            errorMessage = getString(R.string.invalid_lat_long_used);
//            Log.e(TAG, errorMessage + ". " +
//                    "Latitude = " + location.getLatitude() +
//                    ", Longitude = " + location.getLongitude(), illegalArgumentException);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0) {
//            if (errorMessage.isEmpty()) {
//                errorMessage = getString(R.string.no_address_found);
//                Log.e(TAG, errorMessage);
//            }
//            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage);
            Toast.makeText(context, "Geocoding failed!", Toast.LENGTH_SHORT).show();
            return "";
        } else {
            Address address = addresses.get(0);

         return address.getLocality();
        }
    }


}



