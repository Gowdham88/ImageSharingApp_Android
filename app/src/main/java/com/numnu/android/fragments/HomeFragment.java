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
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.numnu.android.R;
import com.numnu.android.adapter.CurrentUpEventsAdapter;
import com.numnu.android.adapter.PastEventsAdapter;
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
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by thulir on 9/10/17.
 */

public class HomeFragment extends Fragment {


    EditText searchViewFood,searchViewLocation;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    NestedScrollView nestedScrollView;
    private RecyclerView currentEventsList,currentEventsList1,currentEventsList2, pastEventsList;
    private ArrayList<String> stringlist,stringlist1;
    Context context;
    Toolbar toolbar;
    BottomNavigationView mBottomNavigationView;
    private ImageView toolbarBackIcon,mSearchIcon,mLocationIcon;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        currentEventsList = view.findViewById(R.id.current_up_recyclerview);
        currentEventsList1 = view.findViewById(R.id.current_up_recyclerview1);
        currentEventsList2 = view.findViewById(R.id.current_up_recyclerview2);
        pastEventsList = view.findViewById(R.id.past_recyclerview);
        ImageView searchIcon = view.findViewById(R.id.search_icon);
        ImageView locationIcon = view.findViewById(R.id.location_icon);
        viewPager = view.findViewById(R.id.viewpager);
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

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, HomeSearchFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        locationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    searchViewLocation.setEnabled(true);
                    searchViewLocation.requestFocus();
                }
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
        setupSearchListener();
        return view;
    }

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
                searchingTexts(charSequence);
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
                searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_close),null);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!searchViewLocation.getText().toString().trim().equals("")){
                    searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_close),null);
                }else{
                    searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                }

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

    }

    private void searchingTexts(CharSequence charSequence) {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

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



