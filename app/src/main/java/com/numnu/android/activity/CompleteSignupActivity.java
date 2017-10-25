package com.numnu.android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.numnu.android.R;

public class CompleteSignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_signup);

        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Complete Sign Up");

    }
}
