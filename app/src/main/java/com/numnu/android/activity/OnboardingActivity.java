package com.numnu.android.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.numnu.android.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OnboardingActivity extends MyActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        showAlert();
    }

    public void gotoHome(View view) {
        Intent mainIntent = new Intent(OnboardingActivity.this,HomeActivity.class);
        OnboardingActivity.this.startActivity(mainIntent);
        OnboardingActivity.this.finish();
    }

    public void showAlert(){

        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.custom_alert, null);
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setView(deleteDialogView);
        Button ok=deleteDialogView.findViewById(R.id.ok_button);
        Button cancel=deleteDialogView.findViewById(R.id.cancel_button);

        final AlertDialog alertDialog1=alertDialog.create();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OnboardingActivity.this, "Ok clicked", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OnboardingActivity.this, "cancel clicked", Toast.LENGTH_SHORT).show();
                alertDialog1.dismiss();
            }
        });
        alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog1.show();
    }
}
