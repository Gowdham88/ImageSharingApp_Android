package com.numnu.android.fragments.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.adapter.HorizontalContentAdapter;
import com.numnu.android.fragments.LocationItemsFragment;
import com.numnu.android.fragments.auth.LoginFragment;
import com.numnu.android.fragments.search.PostsFragment;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.ItemDetailsResponse;
import com.numnu.android.utils.ContentWrappingViewPager;
import com.numnu.android.utils.CustomScrollView;
import com.numnu.android.utils.PreferencesHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thulir on 9/10/17.
 */

public class ItemDetailFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private TextView viewEventMap, eventName, city, eventDate, eventTime,morebutton;
    private ImageView eventImageView,entityImageView;
    private TextView eventDescription;
    private PopupWindow pw;
    LinearLayout busCntnRelLay;
    private CustomScrollView nestedScrollView;
    HorizontalContentAdapter adapter;
    RecyclerView recyclerView1,recyclerView2;
    private Boolean isExpanded = false;
    private String itemId;


    public static ItemDetailFragment newInstance() {
        return new ItemDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            itemId = bundle.getString("itemId");
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_item_detail, container, false);

        ViewPager viewPager = view.findViewById(R.id.event_viewpager);
        setupViewPager(viewPager);

        viewEventMap = view.findViewById(R.id.txt_view_event_map);
        eventDescription = view.findViewById(R.id.event_description);
        eventName = view.findViewById(R.id.event_name);
        city = view.findViewById(R.id.txt_city);
//        eventDate = view.findViewById(R.id.txt_event_date);
//        eventTime = view.findViewById(R.id.txt_event_time);
        nestedScrollView= view.findViewById(R.id.nestedScrollView);
        recyclerView1=(RecyclerView)view.findViewById(R.id.business_recyclerview);
        recyclerView2=(RecyclerView)view.findViewById(R.id.flatron_recyclerview);
//        adapter = new HorizontalContentAdapter(context, eventDetailResponse.getTags());
        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
//        adapter = new HorizontalContentAdapter(context, eventDetailResponse.getTags());
        recyclerView2.setAdapter(adapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        eventImageView = view.findViewById(R.id.current_event_image);
        entityImageView = view.findViewById(R.id.entity_image);
        eventImageView.setOnClickListener(this);
        busCntnRelLay=(LinearLayout) view.findViewById(R.id.buss_content);
        busCntnRelLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, SearchBusinessDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        Picasso.with(context).load(R.drawable.burger)
                .placeholder(R.drawable.food_715539_1920)
                .fit()
                .into(entityImageView);



        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.item);
        ImageView toolbarIcon = view.findViewById(R.id.toolbar_image);
        RelativeLayout toolbarBackIcon = view.findViewById(R.id.toolbar_back);
        final Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbarBackIcon.setOnClickListener(this);
        toolbar.setOnClickListener(this);
        morebutton = view.findViewById(R.id.more_button);
        morebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isExpanded) {

                    isExpanded = false;
                    eventDescription.setMaxLines(4);
                    morebutton.setText("more");

                } else {

                    isExpanded = true;
                    eventDescription.setMaxLines(1000);
                    morebutton.setText("less");

                }

            }
        });

        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(inflater);
            }
        });

        return view;
    }


    private void getItemsDetails(String id)
    {
        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<ItemDetailsResponse> call=apiServices.getItem(id);
        call.enqueue(new Callback<ItemDetailsResponse>() {
            @Override
            public void onResponse(Call<ItemDetailsResponse> call, Response<ItemDetailsResponse> response) {
                int responsecode = response.code();
                ItemDetailsResponse itemDetailsResponse = response.body();
                updateUI(itemDetailsResponse);
            }

            @Override
            public void onFailure(Call<ItemDetailsResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateUI(ItemDetailsResponse itemDetailsResponse) {

        Picasso.with(context).load(R.drawable.burger)
                .placeholder(R.drawable.food_715539_1920)
                .fit()
                .into(eventImageView);


    }

    private void showBottomSheet(LayoutInflater inflater) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View bottomSheetView = inflater.inflate(R.layout.dialog_share_bookmark,null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
        ImageView shareimg = bottomSheetView.findViewById(R.id.dialog_image);
        ImageView bookmarkimg = bottomSheetView.findViewById(R.id.bookmark_icon);
        TextView share = bottomSheetView.findViewById(R.id.share_title);
        TextView bookmark = bottomSheetView.findViewById(R.id.bookmark_title);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Post Content here..."+context.getPackageName());
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, context.getResources().getText(R.string.share_using)));
                bottomSheetDialog.dismiss();
            }
        });
        shareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Post Content here..."+context.getPackageName());
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, context.getResources().getText(R.string.share_using)));
                bottomSheetDialog.dismiss();
            }
        });
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean loginStatus =  PreferencesHelper.getPreferenceBoolean(context,PreferencesHelper.PREFERENCE_LOGGED_IN);
                if (!loginStatus) {
                    Bundle bundle = new Bundle();
                    bundle.putString("BusinessBookmarkIntent","businessbookmark");
                    LoginFragment logFragment = new LoginFragment();
                    logFragment.setArguments(bundle);
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                    transaction.replace(R.id.frame_layout, logFragment);
                    transaction.addToBackStack(null).commit();
//                    Intent intent = new Intent(context, LoginFragment.class);
//                    intent.putExtra("BusinessBookmarkIntent","businessbookmark");
//                    context.startActivity(intent);
                    bottomSheetDialog.dismiss();
                }else if (loginStatus){
                    Toast.makeText(context, "Bookmarked this page", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bookmarkimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean loginStatus =  PreferencesHelper.getPreferenceBoolean(context,PreferencesHelper.PREFERENCE_LOGGED_IN);
                if (!loginStatus) {
                    Bundle bundle = new Bundle();
                    bundle.putString("BusinessBookmarkIntent","businessbookmark");
                    LoginFragment logFragment = new LoginFragment();
                    logFragment.setArguments(bundle);
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                    transaction.replace(R.id.frame_layout, logFragment);
                    transaction.addToBackStack(null).commit();
//                    Intent intent = new Intent(context, LoginFragment.class);
//                    intent.putExtra("BusinessBookmarkIntent","businessbookmark");
//                    context.startActivity(intent);
                    bottomSheetDialog.dismiss();
                }else if (loginStatus){
                    Toast.makeText(context, "Bookmarked this page", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PostsFragment(), "Posts");
        adapter.addFragment(new LocationItemsFragment(), "Locations");
//        adapter.addFragment(new EventsFragment(), "Events");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                getActivity().onBackPressed();
                break;

            case R.id.toolbar:
                nestedScrollView.scrollTo(0,0);
                break;

            case R.id.current_event_image:
                initiatePopupWindow();
                break;

        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
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
            if (position != mCurrentPosition) {
                Fragment fragment = (Fragment) object;
                ContentWrappingViewPager pager = (ContentWrappingViewPager) container;
                if (fragment != null && fragment.getView() != null) {
                    mCurrentPosition = position;
                    pager.measureCurrentView(fragment.getView());
                }
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onAttach(Context context) {

        this.context = context;
        super.onAttach(context);
    }

    private void initiatePopupWindow() {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.image_popup,null);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        pw = new PopupWindow(layout, lp.width, lp.height, true);
        pw.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0, 0);


        LinearLayout btncancel = layout.findViewById(R.id.btncancelcat);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw.dismiss();
            }
        });

    }
}

