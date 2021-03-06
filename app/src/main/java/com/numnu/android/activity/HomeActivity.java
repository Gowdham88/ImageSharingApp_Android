package com.numnu.android.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.numnu.android.R;
import com.numnu.android.fragments.auth.CompleteSignupFragment;
import com.numnu.android.fragments.detail.BusinessDetailFragment;
import com.numnu.android.fragments.detail.EventDetailFragment;
import com.numnu.android.fragments.home.HomeFragment;
import com.numnu.android.fragments.home.NotificationFragment;
import com.numnu.android.fragments.home.UserPostsFragment;
import com.numnu.android.fragments.search.SliceFragment;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.PreferencesHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HomeActivity extends MyActivity {

    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private RelativeLayout relativelayout;
    private int[] icons = {
            R.drawable.ic_bottom_menu_home,
            R.drawable.ic_bottom_menu_notification,
            R.drawable.ic_bottom_menu_profile
    };
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        relativelayout=findViewById(R.id.activity_main);
        mCallbackManager = CallbackManager.Factory.create();

        mAuth = FirebaseAuth.getInstance();

//        final BottomNavigationView bottomNavigationView = (BottomNavigationView)
//                findViewById(R.id.navigation);
        tabLayout = findViewById(R.id.tabs);
        setupTabs();
        checkKeyBoardUp(relativelayout);

//        bottomNavigationView.setOnNavigationItemSelectedListener
//                (new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        Fragment selectedFragment = null;
//                        switch (item.getItemId()) {
//                            case R.id.action_item1:
//                                selectedFragment = HomeFragment.newInstance();
//                                break;
//                            case R.id.action_item2:
//                                selectedFragment = NotificationFragment.newInstance();
//                                break;
//                            case R.id.action_item3:
//                                selectedFragment = UserPostsFragment.newInstance();
//                                break;
//                        }
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.frame_layout, selectedFragment);
//                        transaction.addToBackStack(null).commit();
//                        return true;
//                    }
//                });

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
            transaction.replace(R.id.frame_layout, EventDetailFragment.newInstance("51"));
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

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addIdTokenListener(new FirebaseAuth.IdTokenListener() {
            @Override
            public void onIdTokenChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                updateToken(firebaseAuth.getCurrentUser());
            }
        });
        checkKeyBoardUp(relativelayout);
    }

    private void updateToken(FirebaseUser user) {
        user.getIdToken(false)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            Log.d("Token:", idToken);
                            Constants.FIREBASE_TOKEN = idToken;
                            PreferencesHelper.setPreference(getApplicationContext(), PreferencesHelper.PREFERENCE_FIREBASE_TOKEN, idToken);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            //System.out.println("@#@");
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void checkKeyBoardUp(final View view) {

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                View bottomdivider = findViewById(R.id.bottom_divider);
                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);
                int heightDiff = view.getRootView().getHeight() - (r.bottom - r.top);

                if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
                    //ok now we know the keyboard is up...
                    tabLayout.setVisibility(View.GONE);
                    bottomdivider.setVisibility(View.GONE);

                }else{
                    //ok now we know the keyboard is down...
                    tabLayout.setVisibility(View.VISIBLE);
                    bottomdivider.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setupTabs() {

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                Fragment selectedFragment = null;
                switch (tab.getPosition()){
                    case 0:
                        selectedFragment = HomeFragment.newInstance();
                        break;
                    case 1:
                        selectedFragment = NotificationFragment.newInstance();
                        break;
                    case 2:
                        selectedFragment = UserPostsFragment.newInstance();
                        break;
                }

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.addToBackStack(null).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.tab_unselected_color);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}


