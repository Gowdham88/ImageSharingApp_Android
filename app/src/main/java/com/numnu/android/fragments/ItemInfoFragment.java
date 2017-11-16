package com.numnu.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.activity.MainActivity;
import com.numnu.android.fragments.search.SearchBusinessDetailFragment;
import com.numnu.android.utils.PreferencesHelper;

/**
 * Created by thulir on 9/10/17.
 */

public class ItemInfoFragment extends Fragment implements View.OnClickListener {

    private Context context;
    TextView ItemInfoTxt;

    public static ItemInfoFragment newInstance() {
        return new ItemInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.item_header, container, false);

        ImageView toolbarBackImage = view.findViewById(R.id.toolbar_back);
        toolbarBackImage.setOnClickListener(this);
        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.item);
        ItemInfoTxt=(TextView)view.findViewById(R.id.text_terms);
        ItemInfoTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, ItemDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
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
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    bottomSheetDialog.dismiss();
                }else if (loginStatus){
                    Toast.makeText(getActivity(), "Bookmarked this page", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                getActivity().onBackPressed();
                break;

        }
    }

}

