package com.numnu.android.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.activity.CompleteSignupActivity;
import com.numnu.android.activity.HomeActivity;
import com.numnu.android.activity.LoginActivity;
import com.numnu.android.activity.MainActivity;
import com.numnu.android.fragments.HomeFragment;
import com.numnu.android.fragments.ProfileFragment;
import com.numnu.android.fragments.SettingsFragment;
import com.numnu.android.utils.PreferencesHelper;

/**
 * Created by Thulirsoft on 20/10/2017.
 */

public class SliceFragment extends Fragment {

    Context context;

    public static SliceFragment newInstance() {
        SliceFragment fragment = new SliceFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_slice, container, false);

        TextView toolbarTitle=view.findViewById(R.id.slice_title);
        toolbarTitle.setText("POST");

        TextView textView = view.findViewById(R.id.liked_counts);
        textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomSheetView = inflater.inflate(R.layout.dialog_share_bookmark,null);
        bottomSheetDialog.setContentView(bottomSheetView);

        ImageView toolbarIcon = view.findViewById(R.id.slice_toolbar_icon);
        ImageView moreIcon = view.findViewById(R.id.event_dots);
        ImageView toolbarBackIcon = view.findViewById(R.id.back_button);

        toolbarBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, PostsFragment.newInstance());
                transaction.commit();
            }
        });

        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, SettingsFragment.newInstance());
                transaction.commit();
            }
        });

        moreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show();
            }
        });


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
                if (loginStatus) {
                    Toast.makeText(getActivity(), "Bookmarked this page", Toast.LENGTH_SHORT).show();
                    bottomSheetDialog.dismiss();
                }else if (!loginStatus){
                    startActivity(new Intent(getActivity(), MainActivity.class));

                }
            }
        });
        return view;
    }
}
