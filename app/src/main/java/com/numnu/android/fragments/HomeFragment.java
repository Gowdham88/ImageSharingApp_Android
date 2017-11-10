package com.numnu.android.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import com.numnu.android.R;
import com.numnu.android.adapter.CurrentUpEventsAdapter;
import com.numnu.android.adapter.PastEventsAdapter;
import com.numnu.android.fragments.EventDetail.EventBusinessFragment;
import com.numnu.android.fragments.EventDetail.EventItemsFragment;
import com.numnu.android.fragments.home.CurrentEventsFragment;
import com.numnu.android.fragments.home.EventsFragment;
import com.numnu.android.fragments.home.PastEventsFragment;
import com.numnu.android.fragments.home.PostsFragment;
import com.numnu.android.fragments.home.SearchListFragment;
import com.numnu.android.fragments.home.UsersFragment;
import com.numnu.android.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by thulir on 9/10/17.
 */

public class HomeFragment extends Fragment {


    EditText searchViewFood,searchViewLocation;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    NestedScrollView nestedScrollView;
    private RecyclerView currentEventsList, pastEventsList;
    private ArrayList<String> stringlist,stringlist1;
    Context context;
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
        if (fragments == 1) {
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
                nestedScrollView.setVisibility(View.GONE);
                tabLayout.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                setupViewPager(viewPager);
                tabLayout.setupWithViewPager(viewPager);
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

        ImageView viewPastEventsList = view.findViewById(R.id.view_past_event_list);

        viewCurrentEventsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, CurrentEventsFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        viewPastEventsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, PastEventsFragment.newInstance());
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void setupRecyclerView() {
        stringlist = new ArrayList<>();
        stringlist1 = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            stringlist.add("Item " + i);
        }
            CurrentUpEventsAdapter currentUpAdapter = new CurrentUpEventsAdapter(context, stringlist);
            currentEventsList.setAdapter(currentUpAdapter);


        for (int i = 1; i <= 10; i++) {
            stringlist1.add("Event " + i);
        }
            PastEventsAdapter pastEventsAdapter = new PastEventsAdapter(context, stringlist1);
            pastEventsList.setAdapter(pastEventsAdapter);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new EventsFragment(), "Events");
        adapter.addFragment(new EventBusinessFragment(), "Businesses");
        adapter.addFragment(new EventItemsFragment(), "Items");
        adapter.addFragment(new PostsFragment(), "Posts");
        adapter.addFragment(new UsersFragment(), "Users");
        adapter.addFragment(new SearchListFragment(), "Lists");
        viewPager.setAdapter(adapter);
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


}



