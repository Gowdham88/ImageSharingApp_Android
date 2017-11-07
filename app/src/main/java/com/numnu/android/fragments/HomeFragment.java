package com.numnu.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.numnu.android.R;
import com.numnu.android.adapter.CurrentUpEventsAdapter;
import com.numnu.android.adapter.PastEventsAdapter;
import com.numnu.android.fragments.EventDetail.EventBusinessFragment;
import com.numnu.android.fragments.EventDetail.EventMenuItemsFragment;
import com.numnu.android.fragments.home.CurrentEventsFragment;
import com.numnu.android.fragments.home.EventsFragment;
import com.numnu.android.fragments.home.PastEventsFragment;
import com.numnu.android.fragments.home.PostsFragment;
import com.numnu.android.fragments.home.UsersFragment;
import com.numnu.android.utils.Utils;

import java.util.ArrayList;
import java.util.List;

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

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        return view;
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
        adapter.addFragment(new UserPostsFragment(), "Businesses");
        adapter.addFragment(new EventMenuItemsFragment(), "Items");
        adapter.addFragment(new PostsFragment(), "Posts");
        adapter.addFragment(new UsersFragment(), "Users");
        adapter.addFragment(new EventBusinessFragment(), "Lists");
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



