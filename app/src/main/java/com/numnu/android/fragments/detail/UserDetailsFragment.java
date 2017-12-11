package com.numnu.android.fragments.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.numnu.android.R;
import com.numnu.android.adapter.HorizontalContentAdapter;
import com.numnu.android.adapter.TagsAdapter;
import com.numnu.android.adapter.UserPostsAdapter;
import com.numnu.android.adapter.Userdetailadapter;
import com.numnu.android.fragments.home.SettingsFragment;
import com.numnu.android.fragments.search.SliceFragment;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.LoginCityLocation;
import com.numnu.android.network.response.LoginResponse;
import com.numnu.android.network.response.SignupResponse;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.PreferencesHelper;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by dhivy on 06/11/2017.
 */

public class UserDetailsFragment extends Fragment {
    private RecyclerView mUserPostsRecycler;
    Context context;
    private String id,username;
    TagsAdapter adapter;
    RecyclerView recyclerView;
    ImageView SettingImg;
    Boolean showarrow = false;
    PreferencesHelper preferencesHelper;
    private FirebaseAuth mAuth;
    String strname;
    TextView Strusername,StrUsercity;
    String Strplace;
    ArrayList<String> tagslist;


    public static UserDetailsFragment newInstance() {
        UserDetailsFragment fragment = new UserDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id=bundle.getString("type","location");
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
        SettingImg=(ImageView) view.findViewById(R.id.setting_img);
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
//        String idvalue=PreferencesHelper.getPreference(context,".id");
        TextView toolbarTitle=view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("@Marc chiriqui");
        Strusername=(TextView)view.findViewById(R.id.user_name);
        StrUsercity=(TextView)view.findViewById(R.id.txt_city);
        recyclerView=(RecyclerView)view.findViewById(R.id.business_recyclerview) ;
        mUserPostsRecycler = view.findViewById(R.id.user_posts_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mUserPostsRecycler.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mUserPostsRecycler.getContext(), LinearLayoutManager.VERTICAL);
        mUserPostsRecycler.addItemDecoration(dividerItemDecoration);
        mUserPostsRecycler.setNestedScrollingEnabled(false);

        setupRecyclerView();

        final Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUserPostsRecycler.scrollToPosition(0);
            }
        });

            if(showarrow){
                toolbarBackImage.setVisibility(View.VISIBLE);
                toolbarBackImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().onBackPressed();
                    }
                });
            }


        return view;
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
            Userdetailadapter userPostAdapter = new Userdetailadapter(context, stringlist);
            mUserPostsRecycler.setAdapter(userPostAdapter);
        }
    }


}
