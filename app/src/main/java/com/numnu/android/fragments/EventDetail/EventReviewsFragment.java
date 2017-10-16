package com.numnu.android.fragments.EventDetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numnu.android.R;

/**
 * Created by thulir on 9/10/17.
 */

public class EventReviewsFragment extends Fragment {

    public static EventReviewsFragment newInstance() {
        EventReviewsFragment fragment = new EventReviewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_reviews, container, false);
    }
}

