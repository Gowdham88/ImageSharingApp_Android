package com.numnu.android.fragments.auth;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.numnu.android.R;
import com.numnu.android.activity.OnboardingActivity;
import com.numnu.android.fragments.search.SliceFragment;
import com.numnu.android.utils.Utils;
import pub.devrel.easypermissions.EasyPermissions;

import static com.facebook.GraphRequest.TAG;
import static com.numnu.android.utils.Utils.hideKeyboard;

/**
 * Created by lenovo on 11/18/2017.
 */

public class ForgetPassWordFragment extends Fragment {
    Button Resetbutton;
    ImageView backButton;
    TextView txt_error,txt_email;
    EditText emailField;
    View emailview;
    private ProgressDialog mProgressDialog;
ConstraintLayout Constainlay;
    TextInputEditText emailtxtinlay;
    public static ForgetPassWordFragment newInstance() {
        ForgetPassWordFragment fragment = new ForgetPassWordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.activity_forget_password, container, false);
        Resetbutton=(Button)v.findViewById(R.id.button_reset);
        Constainlay = (ConstraintLayout)v.findViewById(R.id.const_lay);
        backButton  = (ImageView)v.findViewById(R.id.toolbar_back);
        txt_error   = (TextView) v.findViewById(R.id.txt_error);
        txt_email   = (TextView) v.findViewById(R.id.textView5);
        emailview=v.findViewById(R.id.username_view);
        emailField  = v.findViewById(R.id.et_email);
        emailtxtinlay=v.findViewById(R.id.et_email);
        Resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String emailAddress = emailField.getText().toString();
                    if (emailAddress.isEmpty()||!emailAddress.contains("@")) {

                        showerror("Enter email Address");

                    } else {
                        showProgressDialog();
                        FirebaseAuth auth = FirebaseAuth.getInstance();

                        auth.sendPasswordResetEmail(emailAddress)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Popup();
//                                            Toast.makeText(getContext(), "Reset Successsfully", Toast.LENGTH_SHORT).show();
//                                            Log.d(TAG, "Email sent.");
                                        }else {
                                            showerror("Reset password failed.");
                                            hideProgressDialog();
                                        }
                                    }
                                });
                    }
                }

        });
        Constainlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(getActivity());
            }
        });

        hideerror();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();

            }
        });

        emailField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    txt_email.setTextColor(getResources().getColor(R.color.weblink_color));
                    emailview.setBackgroundColor(getResources().getColor(R.color.weblink_color));
                    hideerror();
                }
                else{

                    txt_email.setTextColor(getResources().getColor(R.color.email_color));
                    emailview.setBackgroundColor(getResources().getColor(R.color.email_color));
                }
            }
        });



        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                hideerror();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    public void showProgressDialog() {
//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(context);
//            mProgressDialog.setMessage(getString(R.string.loading));
//            mProgressDialog.setIndeterminate(true);
//        }
//
//        mProgressDialog.show();
//    }

        mProgressDialog = new ProgressDialog(getActivity(),R.style.Custom);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Drawable drawable = new ProgressBar(getActivity()).getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(getActivity(), R.color.White_clr),
                PorterDuff.Mode.SRC_IN);
        mProgressDialog.setIndeterminateDrawable(drawable);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    private void Popup() {


        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View deleteDialogView = factory.inflate(R.layout.alert, null);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(deleteDialogView);
        Button ok = deleteDialogView.findViewById(R.id.ok_button);

        final AlertDialog alertDialog1 = alertDialog.create();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, LoginFragment.newInstance());
                transaction.addToBackStack(null).commit();
                alertDialog1.dismiss();
            }
        });


        alertDialog1.setCanceledOnTouchOutside(false);
        try {
            alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        alertDialog1.show();
        alertDialog1.getWindow().setLayout((int) Utils.convertDpToPixel(228,getActivity()),(int)Utils.convertDpToPixel(220,getActivity()));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog1.getWindow().getAttributes());
//        lp.height=200dp;
//        lp.width=228;
        lp.gravity = Gravity.CENTER;
//        lp.windowAnimations = R.style.DialogAnimation;
        alertDialog1.getWindow().setAttributes(lp);
    }

    public void showerror(String error) {

        txt_error.setText(error);
        final Animation animShake = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_shake);
        txt_error.startAnimation(animShake);
        txt_error.setVisibility(View.VISIBLE);

    }

    public void hideerror(){

        txt_error.setVisibility(View.GONE);
    }
}
