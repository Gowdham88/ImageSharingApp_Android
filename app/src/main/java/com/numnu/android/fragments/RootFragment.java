package com.numnu.android.fragments;

import android.support.v4.app.Fragment;

public class RootFragment extends Fragment implements OnBackPressListener {
 
    @Override
    public boolean onBackPressed() {
        return new BackPressImpl(this).onBackPressed();
    }
}