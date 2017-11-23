package com.numnu.android.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

import com.numnu.android.R;
import com.numnu.android.fragments.detail.BusinessDetailFragment;
import com.numnu.android.fragments.detail.EventDetailFragment;
import com.numnu.android.fragments.home.HomeFragment;
import com.numnu.android.fragments.home.NotificationFragment;
import com.numnu.android.fragments.home.UserPostsFragment;
import com.numnu.android.fragments.search.SliceFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HomeActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = HomeFragment.newInstance();
                                break;
                            case R.id.action_item2:
                                selectedFragment = NotificationFragment.newInstance();
                                break;
                            case R.id.action_item3:
                                selectedFragment = UserPostsFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.addToBackStack(null).commit();
                        return true;
                    }
                });

        String bookmarkBundle = getIntent().getStringExtra("BookmarkIntent");
        String profileBundle = getIntent().getStringExtra("ProfileIntent");
        String eventBookmarkBundle = getIntent().getStringExtra("EventBookmarkIntent");
        String businessBookmarkBundle = getIntent().getStringExtra("BusinessBookmarkIntent");
        if (bookmarkBundle==null && profileBundle == null && eventBookmarkBundle ==null && businessBookmarkBundle == null) {
            //Manually displaying the first fragment - one time only
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
            transaction.commit();
        }else if (bookmarkBundle != null && bookmarkBundle.equals("bookmark")) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, SliceFragment.newInstance());
                transaction.addToBackStack(null).commit();
            } else if (profileBundle != null && profileBundle.equals("profile")){
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, UserPostsFragment.newInstance());
                    transaction.addToBackStack(null).commit();
        }else if (eventBookmarkBundle != null && eventBookmarkBundle.equals("eventbookmark")) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, EventDetailFragment.newInstance());
            transaction.addToBackStack(null).commit();
        }else if (businessBookmarkBundle != null && businessBookmarkBundle.equals("businessbookmark")) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, BusinessDetailFragment.newInstance());
            transaction.addToBackStack(null).commit();
        }
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
}
