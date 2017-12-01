package com.numnu.android.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.numnu.android.R;
import com.numnu.android.fragments.auth.LoginFragment;
import com.numnu.android.fragments.auth.SignupFragment;
import com.numnu.android.utils.PreferencesHelper;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by thulir on 9/10/17.
 */

public class SettingsFragment extends Fragment {

    ScrollView nestedScrollView;

    private Context context;
    String name;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        if(!PreferencesHelper.getPreferenceBoolean(getActivity(),PreferencesHelper.PREFERENCE_LOGGED_IN))
//        {
//            getActivity().finish();
//            Intent intent = new Intent(getActivity(),LoginFragment.class);
//            intent.putExtra("ProfileIntent","profile");
//            startActivity(intent);
//
//        }

            View view = inflater.inflate(R.layout.fragment_settings, container, false);
            nestedScrollView = view.findViewById(R.id.nestedScrollView);

            TextView shareApp = view.findViewById(R.id.text_share_app);
            shareApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    shareApp();
                }
            });

            TextView rateApp = view.findViewById(R.id.text_rate_app);
            rateApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    rateApp();
                }
            });

            TextView terms = view.findViewById(R.id.text_terms);
            terms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    showTerms();
                }
            });

            TextView privacy = view.findViewById(R.id.text_privacy);
            privacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    showPrivacyPolicy();
                }
            });

            TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
            toolbarTitle.setText("Settings");

            view.findViewById(R.id.imageView_profile_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editProfile();
                }
            });

            view.findViewById(R.id.text_logout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logout();
                }
            });

        ImageView toolbarBackImage = view.findViewById(R.id.toolbar_back);

        toolbarBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        final android.support.v7.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nestedScrollView.scrollTo(0,0);
            }
        });

            return view;

    }

    private void logout() {

        PreferencesHelper.setPreferenceBoolean(getApplicationContext(),PreferencesHelper.PREFERENCE_LOGGED_IN,false);
        FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, SignupFragment.newInstance());
        transaction.addToBackStack(null).commit();
    }

    private void editProfile() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, EditProfileFragment.newInstance());
        transaction.addToBackStack(null).commit();
    }

//    private void showPrivacyPolicy() {
//
//        String url = "https://www.youtube.com/";
//        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//        // set toolbar color
//        builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent));
//        CustomTabsIntent customTabsIntent = builder.build();
//        customTabsIntent.launchUrl(context, Uri.parse(url));
//    }
//
//    private void showTerms() {
//
//        String url = "https://www.youtube.com/";
//        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//        // set toolbar color
//        builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent));
//        CustomTabsIntent customTabsIntent = builder.build();
//        customTabsIntent.launchUrl(context, Uri.parse(url));
//    }
//
//    private void rateApp() {
//        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
//    }
//
//    private void shareApp() {
//
//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_content)+context.getPackageName());
//        sendIntent.setType("text/plain");
//        context.startActivity(Intent.createChooser(sendIntent, context.getResources().getText(R.string.share_using)));
//    }


}

