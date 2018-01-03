package com.numnu.android.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.numnu.android.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by thulir on 9/10/17.
 */

public class MyActivity extends AppCompatActivity {

    private AlertDialog dialog;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

//    public void showProgressDialog() {
//        RelativeLayout layout = new RelativeLayout(this);
//        ProgressBar progressBar = new ProgressBar(MyActivity.this,null,android.R.attr.progressBarStyleLarge);
//        progressBar.setIndeterminate(true);
//        progressBar.setVisibility(View.VISIBLE);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
//        params.addRule(RelativeLayout.CENTER_IN_PARENT);
//        layout.addView(progressBar,params);
//        layout.getRootView().setBackgroundResource(R.color.transparent);
//        setContentView(layout);
//    }
//
//    public void hideProgressDialog() {
//        setContentView(R.layout.activity_login);
//    }
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {


        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(this);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        alertDialog.setView(R.layout.progress);
        dialog = alertDialog.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void hideProgressDialog(){
        if(dialog!=null)
        dialog.dismiss();
    }

    private void showSnackBar(String message){
        Snackbar.make(this.findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .show();
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }


}
