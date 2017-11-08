package com.numnu.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.activity.MainActivity;
import com.numnu.android.fragments.EventDetail.EventBusinessFragment;
import com.numnu.android.fragments.EventDetail.EventMenuItemsFragment;
import com.numnu.android.fragments.EventDetail.EventReviewsFragment;
import com.numnu.android.fragments.home.PostsFragment;
import com.numnu.android.utils.AppBarStateChangeListener;
import com.numnu.android.utils.ExpandableTextView;
import com.numnu.android.utils.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thulir on 9/10/17.
 */

public class ItemDetailFragment extends Fragment implements View.OnClickListener {

    SearchView searchViewFood, searchViewLocation;
    private Context context;
    TextView weblink1, weblink2, weblink3;
    private TextView viewEventMap, eventName, city, eventDate, eventTime;
    private ImageView eventImageView;
    private ExpandableTextView eventDescription;


    public static ItemDetailFragment newInstance() {
        return new ItemDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_item_detail, container, false);

        ViewPager viewPager = view.findViewById(R.id.event_viewpager);
        setupViewPager(viewPager);

        weblink1 = view.findViewById(R.id.txt_weblink_1);
        weblink2 = view.findViewById(R.id.txt_weblink_2);
        weblink3 = view.findViewById(R.id.txt_weblink_3);

        weblink1.setOnClickListener(this);
        weblink2.setOnClickListener(this);
        weblink3.setOnClickListener(this);

        viewEventMap = view.findViewById(R.id.txt_view_event_map);
        eventDescription = view.findViewById(R.id.event_description);
        eventName = view.findViewById(R.id.event_name);
        city = view.findViewById(R.id.txt_city);
        eventDate = view.findViewById(R.id.txt_event_date);
        eventTime = view.findViewById(R.id.txt_event_time);

        eventImageView = view.findViewById(R.id.current_event_image);

        setupExpandableText();

        setupWebLinks();

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.item);
        TextView toolbarTitle1 = view.findViewById(R.id.toolbar_title1);
        toolbarTitle1.setText(R.string.item);
        ImageView toolbarIcon = view.findViewById(R.id.toolbar_image);
        ImageView collapsedtoolbarIcon = view.findViewById(R.id.toolbar_image1);
        ImageView toolbarBackIcon = view.findViewById(R.id.toolbar_back);
        ImageView collapsedtoolbarBackIcon = view.findViewById(R.id.toolbar_back1);

        toolbarBackIcon.setOnClickListener(this);
        collapsedtoolbarBackIcon.setOnClickListener(this);

        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(inflater);
            }
        });
        collapsedtoolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(inflater);
            }
        });

        final Toolbar toolbar1 = view.findViewById(R.id.toolbar1);
        AppBarLayout appBarLayout = view.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
             switch (state.name()){

                 case "EXPANDED":
                     toolbar1.setVisibility(View.GONE);
                     view.findViewById(R.id.tabs).setVisibility(View.VISIBLE);
                     break;

                 case "IDLE":
                     toolbar1.setVisibility(View.GONE);
                     view.findViewById(R.id.tabs).setVisibility(View.VISIBLE);
                     break;
                 case "COLLAPSED":
                     toolbar1.setVisibility(View.VISIBLE);
                     view.findViewById(R.id.tabs).setVisibility(View.GONE);
                     break;
             }


            }
        });

        return view;
    }

    private void showBottomSheet(LayoutInflater inflater) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomSheetView = inflater.inflate(R.layout.dialog_event_bottomsheet,null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

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

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean loginStatus =  PreferencesHelper.getPreferenceBoolean(getActivity(),PreferencesHelper.PREFERENCE_LOGGED_IN);
                if (!loginStatus) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    bottomSheetDialog.dismiss();
                }else if (loginStatus){
                    Toast.makeText(getActivity(), "Bookmarked this page", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupExpandableText() {
        eventDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eventDescription.isExpanded()) {
                    eventDescription.truncateText();
                } else {
                    eventDescription.expandText();
                }
            }
        });
    }

    private void setupWebLinks() {


        weblink1.setTextColor(ContextCompat.getColor(context, R.color.blue));
        weblink2.setTextColor(ContextCompat.getColor(context, R.color.blue));
        weblink3.setTextColor(ContextCompat.getColor(context, R.color.blue));


//        viewEventMap.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PostsFragment(), "Posts");
        adapter.addFragment(new LocationItemsFragment(), "Locations");
        adapter.addFragment(new EventReviewsFragment(), "Events");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_weblink_1:

                String url = "https://www.youtube.com/";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                // set toolbar color
                builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, Uri.parse(url));
                break;

            case R.id.txt_weblink_2:
                String url2 = "https://www.google.com/";
                CustomTabsIntent.Builder builder2 = new CustomTabsIntent.Builder();
                // set toolbar color
                builder2.setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent));
                customTabsIntent = builder2.build();
                customTabsIntent.launchUrl(context, Uri.parse(url2));
                break;

            case R.id.txt_weblink_3:
                String url3 = "https://www.facebook.com/";
                CustomTabsIntent.Builder builder3 = new CustomTabsIntent.Builder();
                // set toolbar color
                builder3.setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent));
                customTabsIntent = builder3.build();
                customTabsIntent.launchUrl(context, Uri.parse(url3));
                break;

            case R.id.toolbar_back:
                getActivity().onBackPressed();
                break;

            case R.id.toolbar_back1:
                getActivity().onBackPressed();
                break;

        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

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
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

}

