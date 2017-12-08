package com.numnu.android.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.numnu.android.R;
import com.numnu.android.adapter.FoodAdapter;
import com.numnu.android.fragments.EventDetail.EventBusinessFragment;
import com.numnu.android.fragments.EventDetail.EventItemsCategoryFragment;
import com.numnu.android.fragments.EventDetail.EventPostsFragment;
import com.numnu.android.fragments.FirstFragment;
import com.numnu.android.fragments.RootHomeFragment;
import com.numnu.android.fragments.SecondFragment;
import com.numnu.android.fragments.ThirdFragment;
import com.numnu.android.fragments.auth.CompleteSignupFragment;
import com.numnu.android.fragments.detail.BusinessDetailFragment;
import com.numnu.android.fragments.detail.EventDetailFragment;
import com.numnu.android.fragments.home.HomeFragment;
import com.numnu.android.fragments.home.NotificationFragment;
import com.numnu.android.fragments.home.UserPostsFragment;
import com.numnu.android.fragments.search.SliceFragment;
import com.numnu.android.utils.ContentWrappingViewPager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends MyActivity {

    private CallbackManager mCallbackManager;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.activity_frame_layout, new RootHomeFragment());
                        transaction.addToBackStack(null).commit();
//        mCallbackManager = CallbackManager.Factory.create();

//        final BottomNavigationView bottomNavigationView = (BottomNavigationView)
//                findViewById(R.id.navigation);

//        viewPager = findViewById(R.id.home_viewpager);
//
//        setupViewPager(viewPager);
//        TabLayout tabLayout = findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);

//        bottomNavigationView.setOnNavigationItemSelectedListener
//                (new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        Fragment selectedFragment = null;
//                        switch (item.getItemId()) {
//                            case R.id.action_item1:
//                                selectedFragment = FirstFragment.newInstance();
//                                break;
//                            case R.id.action_item2:
//                                selectedFragment = SecondFragment.newInstance();
//                                break;
//                            case R.id.action_item3:
//                                selectedFragment = ThirdFragment.newInstance();
//                                break;
//                        }
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.frame_layout, selectedFragment);
//                        transaction.addToBackStack(null).commit();
//                        return true;
//                    }
//
//                });

        String bookmarkBundle = getIntent().getStringExtra("BookmarkIntent");
        String profileBundle = getIntent().getStringExtra("ProfileIntent");
        String eventBookmarkBundle = getIntent().getStringExtra("EventBookmarkIntent");
        String businessBookmarkBundle = getIntent().getStringExtra("BusinessBookmarkIntent");
//        if (bookmarkBundle==null && profileBundle == null && eventBookmarkBundle ==null && businessBookmarkBundle == null) {
//            //Manually displaying the first fragment - one time only
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.frame_layout, FirstFragment.newInstance());
//            transaction.commit();
//        }else if (bookmarkBundle != null && bookmarkBundle.equals("bookmark")) {
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout, SliceFragment.newInstance());
//                transaction.addToBackStack(null).commit();
//            } else if (profileBundle != null && profileBundle.equals("profile")){
//                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frame_layout, UserPostsFragment.newInstance());
//                    transaction.addToBackStack(null).commit();
//        }else if (eventBookmarkBundle != null && eventBookmarkBundle.equals("eventbookmark")) {
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.frame_layout, EventDetailFragment.newInstance());
//            transaction.addToBackStack(null).commit();
//        }else if (businessBookmarkBundle != null && businessBookmarkBundle.equals("businessbookmark")) {
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.frame_layout, BusinessDetailFragment.newInstance());
//            transaction.addToBackStack(null).commit();
//        }
        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.numnu.android",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FirstFragment(), "Businesses");
        adapter.addFragment(new SecondFragment(), "Items");
        adapter.addFragment(new ThirdFragment(), "Posts");
        viewPager.setAdapter(adapter);
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

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            //System.out.println("@#@");
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }



}


