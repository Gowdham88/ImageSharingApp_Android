package com.numnu.android.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.numnu.android.R;
import com.numnu.android.activity.webFragment;
import com.numnu.android.fragments.BookmarksFragment;
import com.numnu.android.fragments.auth.SignupFragment;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.PreferencesHelper;
import com.squareup.picasso.Picasso;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by thulir on 9/10/17.
 */

public class SettingsFragment extends Fragment {

    ScrollView nestedScrollView;

    private Context context;
    String textterms="terms",privacypol="privacy";
    // Create a storage reference from our app
    StorageReference storageRef;
    private FirebaseStorage storage;
    private ImageView profileImage;
    private TextView userName;
    private String userId;

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

        userId = PreferencesHelper.getPreference(getApplicationContext(), PreferencesHelper.PREFERENCE_ID);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);

        userName = view.findViewById(R.id.txt_username);


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
                Intent setintent=new Intent(getActivity(),webFragment.class);
                setintent.putExtra("textterms",textterms);
                SettingsFragment.this.startActivity(setintent   );
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        TextView privacy = view.findViewById(R.id.text_privacy);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setintent=new Intent(context, webFragment.class);
                setintent.putExtra("textterms",privacypol);
                SettingsFragment.this.startActivity(setintent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        TextView bookmarkEvents = view.findViewById(R.id.text_bookmark_events);
        bookmarkEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 26/12/17
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, BookmarksFragment.newInstance(userId, Constants.BOOKMARK_EVENT));
                transaction.addToBackStack(null).commit();
            }
        });

        TextView bookmarkBusiness = view.findViewById(R.id.text_bookmark_business);
        bookmarkBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   // TODO: 26/12/17
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, BookmarksFragment.newInstance(userId, Constants.BOOKMARK_BUSNIESS));
                transaction.addToBackStack(null).commit();
            }
        });

        TextView bookmarkItems = view.findViewById(R.id.text_bookmark_items);
        bookmarkItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   // TODO: 26/12/17
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, BookmarksFragment.newInstance(userId, Constants.BOOKMARK_ITEM));
                transaction.addToBackStack(null).commit();
            }
        });

        TextView bookmarkPosts = view.findViewById(R.id.text_bookmark_posts);
        bookmarkPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     // TODO: 26/12/17
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, BookmarksFragment.newInstance(userId, Constants.BOOKMARK_POST));
                transaction.addToBackStack(null).commit();
            }
        });

        TextView bookamrkUsers = view.findViewById(R.id.text_bookmark_users);
        bookamrkUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   // TODO: 26/12/17
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, BookmarksFragment.newInstance(userId, Constants.BOOKMARK_LOCATION));
                transaction.addToBackStack(null).commit();
            }
        });



        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Settings");

        profileImage = view.findViewById(R.id.profile_image);
        view.findViewById(R.id.imageView_profile_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });

        view.findViewById(R.id.text_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                logout();
                showAlertlogout();
            }
        });

        RelativeLayout toolbarBackImage = view.findViewById(R.id.toolbar_back);

        toolbarBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, UserPostsFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        final android.support.v7.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nestedScrollView.scrollTo(0, 0);
            }
        });

        updateUI();

        return view;

    }


//    private void logout() {
//        showAlertlogout();
//
//    }
    public void showAlertlogout() {

        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View deleteDialogView = factory.inflate(R.layout.logout_popup, null);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(deleteDialogView);
        final TextView ok = (TextView) deleteDialogView.findViewById(R.id.ok_txt);
        final TextView cancel = (TextView) deleteDialogView.findViewById(R.id.cancel_txt);

        final AlertDialog alertDialog1 = alertDialog.create();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {

                    currentUser.unlink(currentUser.getProviderId());
                    LoginManager.getInstance().logOut();
                    mAuth.signOut();
                    alertDialog1.dismiss();
                }
                PreferencesHelper.signOut(getApplicationContext());
                PreferencesHelper.setPreferenceBoolean(getApplicationContext(), PreferencesHelper.PREFERENCE_LOGGED_IN, false);
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, SignupFragment.newInstance());
                transaction.addToBackStack(null).commit();
                alertDialog1.dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });
        alertDialog1.show();
//        alertDialog1.setCanceledOnTouchOutside(false);
//        try {
//            alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        alertDialog1.getWindow().setLayout((int) Utils.convertDpToPixel(228,getActivity()),(int)Utils.convertDpToPixel(220,getActivity()));
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(alertDialog1.getWindow().getAttributes());
////        lp.height=200dp;
////        lp.width=228;
//        lp.gravity = Gravity.CENTER;
////        lp.windowAnimations = R.style.DialogAnimation;
//        alertDialog1.getWindow().setAttributes(lp);

    }
    private void editProfile() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, EditProfileFragment.newInstance());
        transaction.addToBackStack(null).commit();
    }

    private void updateUI() {


        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();

        String profilepic = PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_PROFILE_PIC);
        String username = PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_USER_NAME);

        userName.setText(username);

        if (!profilepic.isEmpty() && profilepic != null) {

            if (profilepic.startsWith("https")) {
                Picasso.with(context).load(profilepic)
                        .placeholder(R.drawable.background)
                        .into(profileImage);

            } else {

                storageRef.child(profilepic).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        Log.e("dsddfd", uri.toString());
                        Picasso.with(context).load(uri.toString()).placeholder(R.drawable.background)
                                .into(profileImage);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }
        }

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

