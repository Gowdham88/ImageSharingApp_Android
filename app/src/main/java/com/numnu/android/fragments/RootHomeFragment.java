package com.numnu.android.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numnu.android.R;
import com.numnu.android.fragments.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class RootHomeFragment extends Fragment {


    protected ViewPager pager;

    private ViewPagerAdapter adapter;

    private int[] icons = {
            R.drawable.ic_bottom_menu_home,
            R.drawable.ic_bottom_menu_notification,
            R.drawable.ic_bottom_menu_profile
    };
    private TabLayout tabLayout;
    private Context context;

    public RootHomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.root_home_fragment, container, false);

        pager = rootView.findViewById(R.id.home_viewpager);

         tabLayout = rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Note that we are passing childFragmentManager, not FragmentManager
       setupViewPager(pager);
    }
    private void setupViewPager(ViewPager viewPager) {
         adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FirstFragment(), "");
        adapter.addFragment(new SecondFragment(), "");
        adapter.addFragment(new ThirdFragment(), "");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                int tabIconColor = ContextCompat.getColor(context, R.color.colorAccent);
//                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                int tabIconColor = ContextCompat.getColor(context, R.color.tab_unselected_color);
//                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
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
        }
        /**
         * Get the Fragment by position
         *
         * @param position tab position of the fragment
         * @return
         */
        public Fragment getRegisteredFragment(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    /**
     * Retrieve the currently visible Tab Fragment and propagate the onBackPressed callback
     *
     * @return true = if this fragment and/or one of its associates Fragment can handle the backPress
     */
    public boolean onBackPressed() {
        // currently visible tab Fragment
        OnBackPressListener currentFragment = (OnBackPressListener) adapter.getRegisteredFragment(pager.getCurrentItem());

        if (currentFragment != null) {
            // lets see if the currentFragment or any of its childFragment can handle onBackPressed
            return currentFragment.onBackPressed();
        }

        // this Fragment couldn't handle the onBackPressed call
        return false;
    }

}