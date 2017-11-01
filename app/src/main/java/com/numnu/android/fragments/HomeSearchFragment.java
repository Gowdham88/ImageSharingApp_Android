package com.numnu.android.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.fragments.home.EventsFragment;
import com.numnu.android.fragments.home.MenuItemsFragment;
import com.numnu.android.fragments.home.PostsFragment;
import com.numnu.android.fragments.home.UsersFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thulir on 9/10/17.
 */

public class HomeSearchFragment extends Fragment {

    EditText mSearchFood,mSearchLocation;
//    SearchView searchViewFood,searchViewLocation;
    private Context context;

    public static HomeSearchFragment newInstance() {
        return new HomeSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home_search, container, false);

        ViewPager viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        mSearchFood = view.findViewById(R.id.et_search_food);
        mSearchLocation = view.findViewById(R.id.et_search_location);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mSearchFood.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
            mSearchLocation.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
        }

        mSearchFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
//        mSearchFood.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                final int DRAWABLE_LEFT = 0;
//                final int DRAWABLE_TOP = 1;
//                final int DRAWABLE_RIGHT = 2;
//                final int DRAWABLE_BOTTOM = 3;
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                    mSearchFood.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_close,0);
//                    mSearchLocation.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
//                }
//                if (motionEvent.getAction()==MotionEvent.ACTION_UP) {
//                    if (motionEvent.getRawX() <= (mSearchFood.getRight() - mSearchFood.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
//
//                    }return true;
//                }
//                return false;
//            }
//        });


        mSearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        mSearchLocation.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                final int DRAWABLE_LEFT = 0;
//                final int DRAWABLE_TOP = 1;
//                final int DRAWABLE_RIGHT = 2;
//                final int DRAWABLE_BOTTOM = 3;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            mSearchLocation.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_close,0);
//            mSearchFood.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
//        }
//
//                if (motionEvent.getAction()==MotionEvent.ACTION_UP) {
//                    if (motionEvent.getRawX() <= (mSearchLocation.getRight() - mSearchLocation.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
//
//                    }return true;
//                }
//                return false;
//            }
//        });


//        searchViewFood=view.findViewById(R.id.search_food);
//        searchViewLocation=view.findViewById(R.id.search_location);

        setupSearchListener();
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TextView toolbarTitle=view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getString(R.string.app_name));
        return view;
    }

    private void setupSearchListener() {

        mSearchFood.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    mSearchFood.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_close,0);
                    mSearchLocation.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    mSearchFood.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_close,0);
                    mSearchLocation.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
                }
            }
        });

        mSearchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    mSearchLocation.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_close,0);
                    mSearchFood.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    mSearchLocation.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_close,0);
                    mSearchFood.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
                }

            }
        });
//        searchViewFood.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(context, "Food name:"+query, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//        searchViewLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(context, "Location name:"+query, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new PostsFragment(), "Posts");
        adapter.addFragment(new UsersFragment(), "Users");
        adapter.addFragment(new MenuItemsFragment(), "Menu Items");
        adapter.addFragment(new EventsFragment(), "Events");
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

