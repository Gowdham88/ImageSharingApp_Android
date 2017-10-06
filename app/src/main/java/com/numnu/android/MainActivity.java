package com.numnu.android;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView=findViewById(R.id.textView3);
        textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    public void createAccount(View view) {
        Intent mainIntent = new Intent(MainActivity.this,SignupActivity.class);
        MainActivity.this.startActivity(mainIntent);

    }

    public void facebookSignIn(View view) {
    }

    public void signIn(View view) {
        Intent mainIntent = new Intent(this,LoginActivity.class);
        this.startActivity(mainIntent);
    }
}





