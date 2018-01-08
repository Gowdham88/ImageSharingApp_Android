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
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.numnu.android.R;
import com.numnu.android.adapter.HorizontalContentAdapter;
import com.numnu.android.adapter.UserdetailPostsAdapter;
import com.numnu.android.fragments.auth.SignupFragment;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.EventPostsResponse;
import com.numnu.android.network.response.PostdataItem;
import com.numnu.android.network.response.TagsItem;
import com.numnu.android.utils.PreferencesHelper;
import com.numnu.android.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int PAGE_SIZE = 20;
    private int nextPage = 1;
    EventPostsResponse eventPostsResponse;
    private String userId;
    private UserdetailPostsAdapter userPostAdapter;

    public static UserPostsFragment newInstance() {
        UserPostsFragment fragment = new UserPostsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         userId= PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_ID);
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

        if(!PreferencesHelper.getPreferenceBoolean(getActivity(),PreferencesHelper.PREFERENCE_LOGGED_IN))
        {
            SignupFragment signupFragment=new SignupFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
            transaction.replace(R.id.frame_layout,signupFragment);
            transaction.addToBackStack(null).commit();
        }else {

            if (Utils.isNetworkAvailable(context)) {
                getData("51");
            } else {
                showAlert();
            }
        }

         toolbarTitle=view.findViewById(R.id.toolbar_title);

        musername=view.findViewById(R.id.user_name);
        userImage = view.findViewById(R.id.profile_image);
        mCity = view.findViewById(R.id.txt_city);
        userDescription = view.findViewById(R.id.user_description);
        recyclerView=(RecyclerView)view.findViewById(R.id.business_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mUserPostsRecycler = view.findViewById(R.id.user_posts_recycler);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mUserPostsRecycler.setLayoutManager(layoutManager);
        mUserPostsRecycler.setNestedScrollingEnabled(false);


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


        // Pagination
        mUserPostsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        loadMoreItems(userId);
                    }
                }
            }
        });
        return view;
    }


    private void showAlert() {
    }

    private void getData(String id) {
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<EventPostsResponse> call = apiServices.getPostsByUserId(id);
        call.enqueue(new Callback<EventPostsResponse>() {
            @Override
            public void onResponse(Call<EventPostsResponse> call, Response<EventPostsResponse> response) {
                int responsecode = response.code();
                if (responsecode == 200) {
                    eventPostsResponse = response.body();
                    updatePostsUI();
                    isLoading = false;
                }else {
                    Toast.makeText(context, "There are no user posts", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventPostsResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });

    }

    private void loadMoreItems(String id) {
        nextPage += 1;
        isLoading = true;
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<EventPostsResponse> call = apiServices.getPostsByUserId(id, String.valueOf(nextPage));
        call.enqueue(new Callback<EventPostsResponse>() {
            @Override
            public void onResponse(Call<EventPostsResponse> call, Response<EventPostsResponse> response) {
                int responsecode = response.code();
                if (responsecode == 200) {
                    List<PostdataItem> dataItems = response.body().getPostdata();
                    if (!response.body().getPagination().isHasMore()) {
                        isLastPage = true;
                    }
                    userPostAdapter.addData(dataItems);
                    userPostAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<EventPostsResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                isLoading = false;

            }
        });

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
                try {
                    if (!tagId[i].equals("null")) {
                        tagsuggestion.setId(Integer.valueOf(tagId[i]));
                    }
                }catch (Exception e){
                    Log.e("UserPostsFrag", e+"");
                }

                mylist.add(tagsuggestion);
            }

            adapter = new HorizontalContentAdapter(context, mylist);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }

    private void updatePostsUI() {

        userPostAdapter = new UserdetailPostsAdapter(context, eventPostsResponse.getPostdata());
        mUserPostsRecycler.setAdapter(userPostAdapter);
        userPostAdapter.notifyDataSetChanged();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }



}
