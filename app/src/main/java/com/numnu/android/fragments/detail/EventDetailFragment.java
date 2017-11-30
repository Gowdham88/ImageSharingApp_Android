package com.numnu.android.fragments.detail;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.activity.GoogleMapActivity;
import com.numnu.android.activity.webFragment;
import com.numnu.android.fragments.auth.LoginFragment;
import com.numnu.android.fragments.EventDetail.EventBusinessFragment;
import com.numnu.android.fragments.EventDetail.EventItemsCategoryFragment;
import com.numnu.android.fragments.EventDetail.EventPostsFragment;
import com.numnu.android.utils.ContentWrappingViewPager;
import com.numnu.android.utils.CustomScrollView;
import com.numnu.android.utils.ExpandableTextView;
import com.numnu.android.utils.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thulir on 9/10/17.
 */

public class EventDetailFragment extends Fragment implements View.OnClickListener {

    SearchView searchViewFood, searchViewLocation;
    private Context context;
    TextView weblink1, weblink2, weblink3;
    private TextView viewEventMap, eventName, city, eventDate, eventTime;
    private ImageView eventImageView;
    private ExpandableTextView eventDescription;
    private AppBarLayout appBarLayout;
    private PopupWindow pw;
    private ViewPager viewPager;
    private CustomScrollView nestedScrollView;


    public static EventDetailFragment newInstance() {
        return new EventDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        viewPager = view.findViewById(R.id.event_viewpager);


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
        nestedScrollView= view.findViewById(R.id.nestedScrollView);

        eventImageView = view.findViewById(R.id.current_event_image);
        eventImageView.setOnClickListener(this);
        viewEventMap.setOnClickListener(this);

        setupExpandableText();

        setupWebLinks();

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.event);
        ImageView toolbarIcon = view.findViewById(R.id.toolbar_image);
        ImageView toolbarBackIcon = view.findViewById(R.id.toolbar_back);
        final Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbarBackIcon.setOnClickListener(this);
        toolbar.setOnClickListener(this);

        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(inflater);
            }
        });

        return view;
    }

    private void showBottomSheet(LayoutInflater inflater) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomSheetView = inflater.inflate(R.layout.dialog_share_bookmark,null);
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
                    Intent intent = new Intent(getActivity(), LoginFragment.class);
                    intent.putExtra("EventBookmarkIntent","eventbookmark");
                    startActivity(intent);
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


//        weblink1.setTextColor(ContextCompat.getColor(context, R.color.blue));
//        weblink2.setTextColor(ContextCompat.getColor(context, R.color.blue));
//        weblink3.setTextColor(ContextCompat.getColor(context, R.color.blue));
//

//        viewEventMap.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        weblink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout,webFragment.class);
//                transaction.addToBackStack(null).commit();
                Intent web =new Intent(getActivity(),webFragment.class);
                startActivity(web);


            }
        });
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new EventBusinessFragment(), "Businesses");
        adapter.addFragment(new EventItemsCategoryFragment(), "Items");
        adapter.addFragment(new EventPostsFragment(), "Posts");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_weblink_1:

//                String url = "https://www.youtube.com/";
//                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//                // set toolbar color
//                builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent));
//                CustomTabsIntent customTabsIntent = builder.build();
//                customTabsIntent.launchUrl(context, Uri.parse(url));

                Intent web =new Intent(getActivity(),webFragment.class);
                startActivity(web);
                break;

            case R.id.txt_weblink_2:
//                String url2 = "https://www.google.com/";
//                CustomTabsIntent.Builder builder2 = new CustomTabsIntent.Builder();
//                // set toolbar color
//                builder2.setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent));
//                customTabsIntent = builder2.build();
//                customTabsIntent.launchUrl(context, Uri.parse(url2));

                Intent web2 =new Intent(getActivity(),webFragment.class);
                startActivity(web2);
                break;

            case R.id.txt_weblink_3:
//                String url3 = "https://www.facebook.com/";
//                CustomTabsIntent.Builder builder3 = new CustomTabsIntent.Builder();
                // set toolbar color
//                builder3.setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent));
//                customTabsIntent = builder3.build();
//                customTabsIntent.launchUrl(context, Uri.parse(url3));

                Intent web3 =new Intent(getActivity(),webFragment.class);
                startActivity(web3);
                break;

            case R.id.toolbar_back:
                getActivity().onBackPressed();
                break;

            case R.id.toolbar:
               nestedScrollView.scrollTo(0,0);
                break;

            case R.id.current_event_image:
                initiatePopupWindow();
                break;

            case R.id.txt_view_event_map:
                startActivity(new Intent(context, GoogleMapActivity.class));
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


        ImageView btncancel = layout.findViewById(R.id.btncancelcat);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw.dismiss();
            }
        });

    }

}

