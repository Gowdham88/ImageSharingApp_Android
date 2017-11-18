package com.numnu.android.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.numnu.android.R;
import com.numnu.android.fragments.HomeFragment;

import static com.facebook.GraphRequest.TAG;

/**
 * Created by lenovo on 11/18/2017.
 */

public class ForgetPassWordFragment extends Fragment {
    Button Resetbutton;

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
        Resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    EditText emailField =view.findViewById(R.id.et_email);
                    String emailAddress = emailField.getText().toString();
                    if (emailAddress.isEmpty()||!emailAddress.contains("@")) {
                        Toast.makeText(getActivity(), "Enter a email Address", Toast.LENGTH_SHORT).show();
                    } else {

                        FirebaseAuth auth = FirebaseAuth.getInstance();

                        auth.sendPasswordResetEmail(emailAddress)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Email sent.");
                                        }else {
                                            Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }

        });
        return v;
    }
}
