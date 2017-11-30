package com.numnu.android.fragments.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.fragments.auth.LoginFragment;
import com.numnu.android.fragments.EventDetail.EventItemsCategoryFragment;
import com.numnu.android.fragments.search.PostsFragment;
import com.numnu.android.utils.ContentWrappingViewPager;
import com.numnu.android.utils.CustomScrollView;
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
    TextView entityTitle;
    TextView Viewimage,eventName;
    TextView ViewTxt;
    private CustomScrollView nestedScrollView;

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
        tabLayout = view.findViewById(R.id.tabs);
        eventDescription = view.findViewById(R.id.business_detail_description);
        eventName = view.findViewById(R.id.event_name);
        entityTitle = view.findViewById(R.id.text_business_entity);


        setupExpandableText();
        setupViewPager(viewPagerBusiness);
        tabLayout.setupWithViewPager(viewPagerBusiness);
        TextView toolbarTitleBuss = view.findViewById(R.id.toolbar_title);
        toolbarTitleBuss.setText(getString(R.string.businesses));
        ImageView toolbarIconBuss = view.findViewById(R.id.toolbar_image);
        ImageView toolbarBackIconBuss = view.findViewById(R.id.toolbar_back);
        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        Viewimage= view.findViewById(R.id.business_viewtxt);
        nestedScrollView= view.findViewById(R.id.nestedScrollView);


        Viewimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, SearchBusinessDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        eventName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        entityTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        toolbarBackIconBuss.setOnClickListener(this);
        toolbar.setOnClickListener(this);

        toolbarIconBuss.setOnClickListener( new View.OnClickListener() {
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
        adapter.addFragment(new EventItemsCategoryFragment(), "Items");
        adapter.addFragment(new PostsFragment(), "Posts");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                nestedScrollView.scrollTo(0,0);
                break;

            case R.id.toolbar_back:
                 getActivity().onBackPressed();
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
        this.context=context;
        super.onAttach(context);
    }
}
