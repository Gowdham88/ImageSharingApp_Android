package com.numnu.android.fragments.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.numnu.android.R;
import com.numnu.android.adapter.FoodAdapter;
import com.numnu.android.adapter.HorizontalContentAdapter;
import com.numnu.android.adapter.UserPostsAdapter;
import com.numnu.android.fragments.auth.SignupFragment;
import com.numnu.android.network.response.TagsItem;
import com.numnu.android.network.response.Tagsuggestion;
import com.numnu.android.utils.PreferencesHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dhivy on 06/11/2017.
 */

public class UserPostsFragment extends Fragment {
    private RecyclerView mUserPostsRecycler;
    private NestedScrollView nestedScrollView ;
    Context context;
    HorizontalContentAdapter adapter;
    RecyclerView recyclerView;
    private TextView toolbarTitle;
    // Create a storage reference from our app
    StorageReference storageRef ;
    private FirebaseStorage storage;
    private TextView musername,mCity,userDescription;
    ImageView userImage;
    private ArrayList<TagsItem> mylist=new ArrayList<>();

    public static UserPostsFragment newInstance() {
        UserPostsFragment fragment = new UserPostsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    getActivity().finish();
                    // handle back button

                    return true;

                }

                return false;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(!PreferencesHelper.getPreferenceBoolean(getActivity(),PreferencesHelper.PREFERENCE_LOGGED_IN))
        {
            SignupFragment signupFragment=new SignupFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
            transaction.replace(R.id.frame_layout,signupFragment);
            transaction.addToBackStack(null).commit();
        }

        View view = inflater.inflate(R.layout.fragment_user_posts, container, false);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);


//        toolbarBackImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().onBackPressed();
//            }
//        });
//        final android.support.v7.widget.Toolbar toolbar = view.findViewById(R.id.slice_toolbar);
        LinearLayout linlay=(LinearLayout)view.findViewById(R.id.lin_lay);
        RelativeLayout toolBackImage = view.findViewById(R.id.toolbar_back);
        toolBackImage.setVisibility(View.GONE);
        linlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nestedScrollView.scrollTo(0,0);
            }
        });


         toolbarTitle=view.findViewById(R.id.toolbar_title);

        musername=view.findViewById(R.id.user_name);
        userImage = view.findViewById(R.id.profile_image);
        mCity = view.findViewById(R.id.txt_city);
        userDescription = view.findViewById(R.id.user_description);
        recyclerView=(RecyclerView)view.findViewById(R.id.business_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mUserPostsRecycler = view.findViewById(R.id.user_posts_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mUserPostsRecycler.setLayoutManager(layoutManager);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mUserPostsRecycler.getContext(), LinearLayoutManager.VERTICAL);
//        mUserPostsRecycler.addItemDecoration(dividerItemDecoration);
        mUserPostsRecycler.setNestedScrollingEnabled(false);

        setupRecyclerView();

        ImageView toolbarIcon = view.findViewById(R.id.setting_img);
        toolbarIcon.setVisibility(View.VISIBLE);

        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, SettingsFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });


        updateUI();
        return view;
    }

    private void updateUI() {


        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();

        String username= PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_USER_NAME);
        String city= PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_CITY);
        String userinfo=PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_USER_DESCRIPTION);

        String profilepic=PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_PROFILE_PIC);

        toolbarTitle.setText("@"+username);
        musername.setText(username);
        mCity.setText(city);
        userDescription.setText(userinfo);

        if(!profilepic.isEmpty()&&profilepic!=null) {

            if(profilepic.startsWith("https")){
                Picasso.with(context).load(profilepic)
                        .placeholder(R.drawable.background)
                        .fit()
                        .into(userImage);
            }else {
                storageRef.child(profilepic).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        Picasso.with(context).load(uri)
                                .placeholder(R.drawable.background)
                                .into(userImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }
        }

        //prepare Tag List
        String tags=PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_TAGS);
        String tagIds=PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_TAG_IDS);

        if(!tags.isEmpty() && tags!=null) {

            String[] tag = tags.split(",");
            String[] tagId = tagIds.split(",");
            mylist.clear();

            for (int i = 0; i < tag.length; i++) {

                TagsItem tagsuggestion = new TagsItem();
                tagsuggestion.setText(tag[i]);
                tagsuggestion.setId(Integer.valueOf(tagId[i]));

                mylist.add(tagsuggestion);
            }

            adapter = new HorizontalContentAdapter(context, mylist);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void setupRecyclerView() {
        ArrayList<String> stringlist = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            stringlist.add("Post item " + i);
            UserPostsAdapter userPostAdapter = new UserPostsAdapter(context, stringlist);
            mUserPostsRecycler.setAdapter(userPostAdapter);
        }
    }

}
