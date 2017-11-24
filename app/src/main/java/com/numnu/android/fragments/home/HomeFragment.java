package com.numnu.android.fragments.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.numnu.android.R;
import com.numnu.android.adapter.CurrentUpEventsAdapter;
import com.numnu.android.adapter.PlaceAutocompleteAdapter;
import com.numnu.android.adapter.search.PlaceAutocompleteRecyclerViewAdapter;
import com.numnu.android.adapter.search.SearchResultsAdapter;
import com.numnu.android.fragments.search.EventsFragmentwithToolbar;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.ContentValues.TAG;
import static com.numnu.android.utils.Utils.hideKeyboard;


/**
 * Created by thulir on 9/10/17.
 */

public class HomeFragment extends Fragment {


    EditText searchViewFood,searchViewLocation;
    private RecyclerView searchListView;
    private TabLayout tabLayout;
    NestedScrollView nestedScrollView;
    private RecyclerView currentEventsList,currentEventsList1,currentEventsList2, pastEventsList;
    private ArrayList<String> stringlist,stringlist1;
    Context context;
    Toolbar toolbar;
    BottomNavigationView mBottomNavigationView;
    private ImageView toolbarBackIcon,mSearchIcon,mLocationIcon;
    RelativeLayout Homelinlay1,Homelinlay2,Homelinlay3;
    AppBarLayout AppLay;


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
        searchViewFood.setText("");
        searchViewLocation.setText("");
        nestedScrollView.setVisibility(View.VISIBLE);
        searchListView.setVisibility(View.GONE);

        Finish();
    }

    private void Finish() {

            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                        getActivity().finish();
                        // handle back button

                        return true;

                    }

                    return false;
                }
            });
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
                getActivity().onBackPressed();
            }
        });
        AppLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getActivity());
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
//        searchViewLocation.setOnItemClickListener(mAutocompleteClickListener);


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
                locationSearch(charSequence);
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

    private void locationSearch(final CharSequence charSequence) {
       if(!charSequence.toString().equals("")){

           nestedScrollView.setVisibility(View.GONE);
           searchListView.setVisibility(View.VISIBLE);
           // Set up the adapter that will retrieve suggestions from the Places Geo Data Client.
           mAdapter = new PlaceAutocompleteRecyclerViewAdapter(getActivity(), mGeoDataClient, Constants.BOUNDS_GREATER_SYDNEY, null);
           mAdapter.getFilter().filter(charSequence.toString());
           mAdapter.setOnItemClickListener(new SearchResultsAdapter.OnItemClickListener() {
               @Override
               public void onRecyclerItemClick(View view, int position) {
                   final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.NORMAL);
                   String s = mAdapter.getItem(position).getPrimaryText(STYLE_BOLD).toString();
                   searchViewLocation.setText(s);
                   Bundle bundle = new Bundle();
                   bundle.putString("keyword",s);
                   bundle.putString("type","location");
                   HomeSearchFragment searchFragment=HomeSearchFragment.newInstance();
                   searchFragment.setArguments(bundle);
                   FragmentTransaction transaction =  getFragmentManager().beginTransaction();
                   transaction.replace(R.id.frame_layout,searchFragment);
                   transaction.addToBackStack(null).commit();
               }
           });
           searchListView.setAdapter(mAdapter);

       }else {
           googleLogo.setVisibility(View.GONE);
           nestedScrollView.setVisibility(View.VISIBLE);
           searchListView.setVisibility(View.GONE);
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



}



