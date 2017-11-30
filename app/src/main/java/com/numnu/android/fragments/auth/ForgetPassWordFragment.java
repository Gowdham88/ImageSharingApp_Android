package com.numnu.android.fragments.auth;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.numnu.android.R;

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
ConstraintLayout Constainlay;
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
        emailField  = v.findViewById(R.id.et_email);
        Resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String emailAddress = emailField.getText().toString();
                    if (emailAddress.isEmpty()||!emailAddress.contains("@")) {

                        showerror("Enter email Address");

                    } else {

                        FirebaseAuth auth = FirebaseAuth.getInstance();

                        auth.sendPasswordResetEmail(emailAddress)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Email sent.");
                                        }else {
                                            showerror("Reset password failed.");
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
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    txt_email.setTextColor(getResources().getColor(R.color.weblink_color));
                    hideerror();
                }
                else{
                    txt_email.setTextColor(getResources().getColor(R.color.email_color));
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
