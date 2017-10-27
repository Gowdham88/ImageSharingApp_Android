package com.numnu.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.fragments.home.EventsFragment;
import com.numnu.android.fragments.home.MenuItemsFragment;
import com.numnu.android.fragments.home.PostsFragment;
import com.numnu.android.fragments.home.UsersFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thulir on 9/10/17.
 */

public class HomeSearchFragment extends Fragment {

    SearchView searchViewFood,searchViewLocation;
    private Context context;

    public static HomeSearchFragment newInstance() {
        return new HomeSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home_search, container, false);

        ViewPager viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        searchViewFood=view.findViewById(R.id.search_food);
        searchViewLocation=view.findViewById(R.id.search_location);
        setupSearchListener();
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TextView toolbarTitle=view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getString(R.string.app_name));
        return view;
    }

    private void setupSearchListener() {
        
        searchViewFood.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(context, "Food name:"+query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchViewLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(context, "Location name:"+query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new PostsFragment(), "Posts");
        adapter.addFragment(new UsersFragment(), "Users");
        adapter.addFragment(new MenuItemsFragment(), "Menu Items");
        adapter.addFragment(new EventsFragment(), "Events");
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

    @Override
    public void onAttach(Context context) {
        this.context=context;
        super.onAttach(context);
    }
}

