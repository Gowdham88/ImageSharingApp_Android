package com.numnu.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.activity.LoginActivity;
import com.numnu.android.utils.AppBarStateChangeListener;
import com.numnu.android.utils.ExpandableTextView;
import com.numnu.android.utils.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

public class BusinessDetailFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private ViewPager viewPagerBusiness;
    private TabLayout tabLayout;
    private ExpandableTextView eventDescription;
    private AppBarLayout appBarLayout;

    public static BusinessDetailFragment newInstance() {
        return new BusinessDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.frag_business_details, container, false);

        viewPagerBusiness = view.findViewById(R.id.business_viewpager);
//        searchViewFood=view.findViewById(R.id.et_search_food);
//        searchViewLocation=view.findViewById(R.id.et_search_location);
        tabLayout = view.findViewById(R.id.business_tabs);
        eventDescription = view.findViewById(R.id.business_detail_description);
        setupExpandableText();
        setupViewPager(viewPagerBusiness);
        tabLayout.setupWithViewPager(viewPagerBusiness);
        TextView toolbarTitleBuss = view.findViewById(R.id.toolbar_title);
        toolbarTitleBuss.setText(getString(R.string.businesses));
        TextView toolbarTitle1Buss = view.findViewById(R.id.toolbar_title1);
        toolbarTitle1Buss.setText(getString(R.string.businesses));
        ImageView toolbarIconBuss = view.findViewById(R.id.toolbar_image);
        ImageView collapsedtoolbarIconBuss = view.findViewById(R.id.toolbar_image1);
        ImageView toolbarBackIconBuss = view.findViewById(R.id.toolbar_back_image);
        ImageView collapsedtoolbarBackIconBuss = view.findViewById(R.id.toolbar_back1);
        final Toolbar toolbar1 = view.findViewById(R.id.toolbar1);

        toolbarBackIconBuss.setOnClickListener(this);
        collapsedtoolbarBackIconBuss.setOnClickListener(this);
        toolbar1.setOnClickListener(this);

        toolbarIconBuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(inflater);
            }
        });
        collapsedtoolbarIconBuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(inflater);
            }
        });
        toolbarBackIconBuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        collapsedtoolbarBackIconBuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        appBarLayout = view.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                switch (state.name()){

                    case "EXPANDED":
                        toolbar1.setVisibility(View.GONE);
                        view.findViewById(R.id.business_tabs).setVisibility(View.VISIBLE);
                        break;

                    case "IDLE":
                        toolbar1.setVisibility(View.GONE);
                        view.findViewById(R.id.business_tabs).setVisibility(View.VISIBLE);
                        break;
                    case "COLLAPSED":
                        toolbar1.setVisibility(View.VISIBLE);
                        view.findViewById(R.id.business_tabs).setVisibility(View.GONE);
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
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("BusinessBookmarkIntent","businessbookmark");
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


    private void setupViewPager(ViewPager viewPager) {
       ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Items(), "Items");
        adapter.addFragment(new Post(), "Post");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar1:
                appBarLayout.setExpanded(true);
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
