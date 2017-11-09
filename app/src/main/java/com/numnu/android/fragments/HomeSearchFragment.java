package com.numnu.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.numnu.android.R;
import com.numnu.android.fragments.EventDetail.EventBusinessFragment;
import com.numnu.android.fragments.EventDetail.EventItemsFragment;
import com.numnu.android.fragments.home.EventsFragment;
import com.numnu.android.fragments.home.PostsFragment;
import com.numnu.android.fragments.home.UsersFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thulir on 9/10/17.
 */

public class HomeSearchFragment extends Fragment {

    EditText searchViewFood,searchViewLocation;
    private Context context;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public static HomeSearchFragment newInstance() {
        return new HomeSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home_search, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        searchViewFood=view.findViewById(R.id.et_search_food);
        searchViewLocation=view.findViewById(R.id.et_search_location);
        tabLayout = view.findViewById(R.id.tabs);

        TextView toolbarTitle=view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getString(R.string.app_name));

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupSearchListener();

        return view;
    }

    private void setupSearchListener() {
//        searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
//        searchViewLocation.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
//        searchViewFood.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                searchViewFood.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_close),null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

//        searchViewFood.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
//                    if (searchViewFood.getCompoundDrawables()[2]!= null){
//                        if (motionEvent.getX() >=(searchViewFood.getRight()-searchViewFood.getLeft() - searchViewFood.getCompoundDrawables()[2].getBounds().width())){
//                            searchViewFood.setText("");
//                        }
//                    }
//                }
//
//                return false;
//            }
//        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new EventsFragment(), "Events");
        adapter.addFragment(new EventBusinessFragment(), "Businesses");
        adapter.addFragment(new EventItemsFragment(), "Items");
        adapter.addFragment(new PostsFragment(), "Posts");
        adapter.addFragment(new UsersFragment(), "Users");
        adapter.addFragment(new EventBusinessFragment(), "Lists");
        viewPager.setAdapter(adapter);
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

