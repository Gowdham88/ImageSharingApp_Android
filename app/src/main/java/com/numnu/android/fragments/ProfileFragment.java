package com.numnu.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.numnu.android.R;
import com.numnu.android.utils.PreferencesHelper;

/**
 * Created by thulir on 9/10/17.
 */

public class ProfileFragment extends Fragment {

    private Context context;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        TextView toolbarTitle=view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.edit_profile);

        EditText profName = view.findViewById(R.id.et_name);
        EditText profEmail = view.findViewById(R.id.et_email_address);

        profName.setText(PreferencesHelper.getPreference(context,PreferencesHelper.PREFERENCE_NAME));
        profEmail.setText(PreferencesHelper.getPreference(context,PreferencesHelper.PREFERENCE_EMAIL));

        view.findViewById(R.id.imageView_profile_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });

        ImageView toolbarBackImage = view.findViewById(R.id.toolbar_back);

        toolbarBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private void editProfile() {



    }



}

