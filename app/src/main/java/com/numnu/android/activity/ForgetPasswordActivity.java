package com.numnu.android.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.numnu.android.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgetPasswordActivity extends MyActivity {

    private static final String TAG ="ResetPassword" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        findViewById(R.id.button_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailField = findViewById(R.id.et_email);
                String emailAddress = emailField.getText().toString();
                if (emailAddress.isEmpty()||!emailAddress.contains("@")) {
                    Toast.makeText(ForgetPasswordActivity.this, "Enter a email Address", Toast.LENGTH_SHORT).show();
                } else {

                    FirebaseAuth auth = FirebaseAuth.getInstance();

                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Email sent.");
                                    }
                                }
                            });
                }
            }
        });
    }
}
