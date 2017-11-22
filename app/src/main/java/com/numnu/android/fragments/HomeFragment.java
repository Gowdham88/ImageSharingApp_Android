package com.numnu.android.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.numnu.android.R;
import com.numnu.android.adapter.CurrentUpEventsAdapter;
import com.numnu.android.adapter.PastEventsAdapter;
import com.numnu.android.adapter.PlaceAutocompleteAdapter;
import com.numnu.android.adapter.search.SearchResultsAdapter;
import com.numnu.android.fragments.EventDetail.EventBusinessFragment;
import com.numnu.android.fragments.search.CurrentEventsFragment;
import com.numnu.android.fragments.search.EventsFragment;
import com.numnu.android.fragments.search.EventsFragmentwithToolbar;
import com.numnu.android.fragments.search.PastEventsFragment;
import com.numnu.android.fragments.search.PostsFragment;
import com.numnu.android.fragments.search.SearchItemsFragment;
import com.numnu.android.fragments.search.SearchListFragment;
import com.numnu.android.fragments.search.UsersFragment;
import com.numnu.android.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by thulir on 9/10/17.
 */

public class HomeFragment extends Fragment {


    EditText searchViewFood;
    private RecyclerView searchListView;
    private TabLayout tabLayout;
    NestedScrollView nestedScrollView;
    private RecyclerView currentEventsList,currentEventsList1,currentEventsList2, pastEventsList;
    private ArrayList<String> stringlist,stringlist1;
    Context context;
    Toolbar toolbar;
    BottomNavigationView mBottomNavigationView;
    private ImageView toolbarBackIcon,mSearchIcon,mLocationIcon;


    /**
     * GeoDataClient wraps our service connection to Google Play services and provides access
     * to the Google Places API for Android.
     */
    protected GeoDataClient mGeoDataClient;

    private PlaceAutocompleteAdapter mAdapter;

    private AutoCompleteTextView searchViewLocation;

    private TextView mPlaceDetailsText;

    private TextView mPlaceDetailsAttribution;

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));
    private ImageView googleLogo;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        int fragments = getFragmentManager().getBackStackEntryCount();
        if (fragments <= 1) {
            toolbarBackIcon.setVisibility(View.GONE);
        }
        else {
            toolbarBackIcon.setVisibility(View.VISIBLE);
        }

        googleLogo.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        googleLogo = view.findViewById(R.id.img_google);
        currentEventsList = view.findViewById(R.id.current_up_recyclerview);
        currentEventsList1 = view.findViewById(R.id.current_up_recyclerview1);
        currentEventsList2 = view.findViewById(R.id.current_up_recyclerview2);
        pastEventsList = view.findViewById(R.id.past_recyclerview);
        ImageView searchIcon = view.findViewById(R.id.search_icon);
        ImageView locationIcon = view.findViewById(R.id.location_icon);
        searchListView = view.findViewById(R.id.search_results_recyclerview);
        searchViewFood=view.findViewById(R.id.et_search_food);
        searchViewLocation=view.findViewById(R.id.et_search_location);
        tabLayout = view.findViewById(R.id.tabs);
        nestedScrollView = view.findViewById(R.id.events_scroll_view);
        toolbarBackIcon = view.findViewById(R.id.toolbar_back);
        toolbarBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        ImageView viewCurrentEventsList = view.findViewById(R.id.view_current_event_list);
        ImageView viewCurrentEventsList1 = view.findViewById(R.id.view_current_event_list1);
        ImageView viewCurrentEventsList2 = view.findViewById(R.id.view_current_event_list2);

        ImageView viewPastEventsList = view.findViewById(R.id.view_past_event_list);

        viewCurrentEventsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EventsFragmentwithToolbar.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        viewCurrentEventsList1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EventsFragmentwithToolbar.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        viewCurrentEventsList2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EventsFragmentwithToolbar.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        viewPastEventsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EventsFragmentwithToolbar.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        setupRecyclerView();


        // Construct a GeoDataClient for the Google Places API for Android.
        mGeoDataClient = Places.getGeoDataClient(getActivity(), null);
        searchViewLocation.setOnItemClickListener(mAutocompleteClickListener);

        // Set up the adapter that will retrieve suggestions from the Places Geo Data Client.
        mAdapter = new PlaceAutocompleteAdapter(getActivity(), mGeoDataClient, BOUNDS_GREATER_SYDNEY, null);
        searchViewLocation.setAdapter(mAdapter);
        setupSearchListener();
        return view;
    }



    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data Client
     * to retrieve more details about the place.
     *
     * @see GeoDataClient#getPlaceById(String...)
     */
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);

            googleLogo.setVisibility(View.GONE);
            Utils.hideKeyboard(getActivity());
            locationSearch(primaryText);
        }
    };



    private void checkKeyBoardUp(final View view) {

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                View bottomNavigationView = getActivity().findViewById(R.id.navigation);
                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);
                int heightDiff = view.getRootView().getHeight() - (r.bottom - r.top);

                if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
                    //ok now we know the keyboard is up...
                    bottomNavigationView.setVisibility(View.GONE);


                }else{
                    //ok now we know the keyboard is down...
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @SuppressLint("ClickableViewAccessibility")
    private void setupSearchListener() {

        searchViewFood.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!searchViewFood.getText().toString().trim().equals("")){
                    searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_close),null);
                }else{
                    searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }
                if (!searchViewLocation.getText().toString().trim().equals("")){
                    searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_close),null);
                }else {
                    searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_close),null);
                foodSearch(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!searchViewFood.getText().toString().trim().equals("")){
                    searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_close),null);

                }else{
                    searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }
            }


        });

        searchViewLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                googleLogo.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchViewFood.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (searchViewFood.getCompoundDrawables()[2]!= null){
                        if (motionEvent.getX() >=(searchViewFood.getRight()-searchViewFood.getLeft() - searchViewFood.getCompoundDrawables()[2].getBounds().width())){
                            searchViewFood.setText("");
                            searchViewFood.clearFocus();
                                Utils.hideKeyboard(getActivity());

                        }
                    }
                }
                checkKeyBoardUp(view);
                return false;
            }
        });
        searchViewLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (searchViewLocation.getCompoundDrawables()[2]!= null){
                        if (motionEvent.getX() >=(searchViewLocation.getRight()-searchViewLocation.getLeft() - searchViewLocation.getCompoundDrawables()[2].getBounds().width())){
                            searchViewLocation.setText("");
                            searchViewLocation.clearFocus();
                            Utils.hideKeyboard(getActivity());
                        }
                    }
                }
                checkKeyBoardUp(view);
                return false;
            }
        });

        searchViewFood.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //do here your stuff f
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
                    //do here your stuff f
                    Utils.hideKeyboard(getActivity());
                    return true;
                }
                return false;
            }

        });

    }

    private void locationSearch(CharSequence charSequence) {
       if(!charSequence.toString().equals("")){

                   Bundle bundle = new Bundle();
                   bundle.putString("keyword",charSequence.toString());
                   bundle.putString("type","location");
                   HomeSearchFragment searchFragment=HomeSearchFragment.newInstance();
                   searchFragment.setArguments(bundle);
                   FragmentTransaction transaction =  getFragmentManager().beginTransaction();
                   transaction.replace(R.id.frame_layout,searchFragment);
                   transaction.addToBackStack(null).commit();
       }
    }

    private void foodSearch(CharSequence charSequence) {
        if(!charSequence.toString().equals("")){
            nestedScrollView.setVisibility(View.GONE);
            searchListView.setVisibility(View.VISIBLE);

            final String[] arr = {"montreal", "location1", "location2", "location3", "location4", "location5", "location6", "location7",};

            ArrayList<String> stringArrayList=new ArrayList<>();
            stringArrayList.addAll(Arrays.asList(arr));
            SearchResultsAdapter searchResultsAdapter = new SearchResultsAdapter(context, stringArrayList);

            searchResultsAdapter.setOnItemClickListener( new SearchResultsAdapter.OnItemClickListener() {
                @Override
                public void onRecyclerItemClick(View view, int position) {
                    searchViewFood.setText(arr[position]);
                    Bundle bundle = new Bundle();
                    bundle.putString("keyword", arr[position]);
                    bundle.putString("type","food");
                    HomeSearchFragment searchFragment=HomeSearchFragment.newInstance();
                    searchFragment.setArguments(bundle);
                    FragmentTransaction transaction =  getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout,searchFragment);
                    transaction.addToBackStack(null).commit();
                }
            });

            searchListView.setAdapter(searchResultsAdapter);

        }else {
            nestedScrollView.setVisibility(View.VISIBLE);
            searchListView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void setupRecyclerView() {
        stringlist = new ArrayList<>();
        stringlist1 = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            stringlist.add("Flatron");
        }
            CurrentUpEventsAdapter currentUpAdapter = new CurrentUpEventsAdapter(context, stringlist);
            currentEventsList.setAdapter(currentUpAdapter);

        CurrentUpEventsAdapter currentUpAdapter1 = new CurrentUpEventsAdapter(context, stringlist);
        currentEventsList1.setAdapter(currentUpAdapter1);

        CurrentUpEventsAdapter currentUpAdapter2 = new CurrentUpEventsAdapter(context, stringlist);
        currentEventsList2.setAdapter(currentUpAdapter2);


        for (int i = 1; i <= 10; i++) {
            stringlist1.add("Flatron");
        }
//            PastEventsAdapter pastEventsAdapter = new PastEventsAdapter(context, stringlist1);
//            pastEventsList.setAdapter(pastEventsAdapter);


    }


}



