package com.numnu.android.fragments;

import android.content.Context;
import android.graphics.Paint;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.numnu.android.R;
import com.numnu.android.fragments.EventDetail.EventBusinessFragment;
import com.numnu.android.fragments.EventDetail.EventMenuItemsFragment;
import com.numnu.android.fragments.EventDetail.EventReviewsFragment;
import com.numnu.android.utils.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thulir on 9/10/17.
 */

public class EventDetailFragment extends Fragment implements View.OnClickListener {

    SearchView searchViewFood,searchViewLocation;
    private Context context;
    TextView weblink1,weblink2,weblink3;
    private TextView viewEventMap,eventName,city,eventDate,eventTime;
    private ImageView eventImageView;
    private ExpandableTextView eventDescription;


    public static EventDetailFragment newInstance() {
        return new EventDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_event_detail, container, false);

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

        eventDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eventDescription.isExpanded()){
                    eventDescription.truncateText();
                }else {
                    eventDescription.expandText();
                }
            }
        });

//        Picasso.with(activity).load(url).transform(new RoundedCornersTransform(this)).into(eventImageView)


        setupWebLinks();

        searchViewFood=view.findViewById(R.id.search_food);
        searchViewLocation=view.findViewById(R.id.search_location);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
//
//
        TextView toolbarTitle=view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.event);

        return view;
    }

    private void setupWebLinks() {


            weblink1.setTextColor(ContextCompat.getColor(context,R.color.blue));
            weblink2.setTextColor(ContextCompat.getColor(context,R.color.blue));
            weblink3.setTextColor(ContextCompat.getColor(context,R.color.blue));


        viewEventMap.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new EventBusinessFragment(), "Businesses");
        adapter.addFragment(new EventMenuItemsFragment(), "Menu Items");
        adapter.addFragment(new EventReviewsFragment(), "Reviews");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
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
        this.context=context;
        super.onAttach(context);
    }
}

