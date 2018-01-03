package com.numnu.android.fragments.detail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.numnu.android.adapter.eventdetail.EventPostsAdapter;
import com.numnu.android.fragments.home.SettingsFragment;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.EventPostsResponse;
import com.numnu.android.network.response.PostdataItem;
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

public class UserDetailsFragment extends Fragment {
    private RecyclerView mUserPostsRecycler;
    Context context;
    private String id, username;
    HorizontalContentAdapter adapter;
    RecyclerView recyclerView;
    ImageView SettingImg;
    Boolean showarrow = false;
    StorageReference storageRef;
    private FirebaseStorage storage;
    ImageView userImage;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int PAGE_SIZE = 20;
    private int nextPage = 1;
    private AlertDialog dialog;
    private TextView toolbarTitle, musername, mCity, userDescription;
    EventPostsResponse eventPostsResponse;
    private String userId;
    private UserdetailPostsAdapter userPostAdapter;


    public static UserDetailsFragment newInstance(String userId) {
        UserDetailsFragment userDetailsFragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        userDetailsFragment.setArguments(args);
        return userDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            userId = bundle.getString("userId");
            username = bundle.getString("uname", "Festival");
            showarrow = bundle.getBoolean("Showarrow");

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_details, container, false);

        RelativeLayout toolbarBackImage = view.findViewById(R.id.toolbar_back);
        toolbarBackImage.setVisibility(View.GONE);
        SettingImg = (ImageView) view.findViewById(R.id.setting_img);
        SettingImg.setVisibility(View.VISIBLE);
        SettingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, SettingsFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });


        toolbarBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        musername = view.findViewById(R.id.user_name);
        userImage = view.findViewById(R.id.profile_image);
        mCity = view.findViewById(R.id.txt_city);
        userDescription = view.findViewById(R.id.user_description);
        toolbarTitle = view.findViewById(R.id.toolbar_title);
        recyclerView = (RecyclerView) view.findViewById(R.id.business_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mUserPostsRecycler = view.findViewById(R.id.user_posts_recycler);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mUserPostsRecycler.setLayoutManager(layoutManager);
        final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mUserPostsRecycler.getContext(), LinearLayoutManager.VERTICAL);
        mUserPostsRecycler.addItemDecoration(dividerItemDecoration);
        mUserPostsRecycler.setNestedScrollingEnabled(false);

        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUserPostsRecycler.scrollToPosition(0);
            }
        });

        if (showarrow) {
            toolbarBackImage.setVisibility(View.VISIBLE);
            toolbarBackImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().onBackPressed();
                }
            });
        }

        if (Utils.isNetworkAvailable(context)) {
            getData(userId);
        } else {
            showAlert();
        }
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

    private void updateUserUI() {


        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();

        String username = PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_USER_NAME);
        String city = PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_CITY);
        String userinfo = PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_USER_DESCRIPTION);

        String profilepic = PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_PROFILE_PIC);

        toolbarTitle.setText("@" + username);
        musername.setText(username);
        mCity.setText(city);
        userDescription.setText(userinfo);

        if (!profilepic.isEmpty() && profilepic != null) {

            if (profilepic.startsWith("https")) {
                Picasso.with(context).load(profilepic)
                        .placeholder(R.drawable.background)
                        .fit()
                        .into(userImage);
            } else {
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


        adapter = new HorizontalContentAdapter(context, eventPostsResponse.getPostdata().get(0).getTags());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


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
