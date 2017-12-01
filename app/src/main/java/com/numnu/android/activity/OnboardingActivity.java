package com.numnu.android.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.numnu.android.R;
import com.numnu.android.utils.Utils;


import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class OnboardingActivity extends MyActivity implements EasyPermissions.PermissionCallbacks {

    private static final String[] LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int RC_LOCATION_PERM = 1;
    private static final String TAG = "Onboarding";
    TextView textView;
    Animation slideUpAnimation,slidedownAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        textView=(TextView)findViewById(R.id.textView_info);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_LOCATION_PERM)
    private void gotoHome() {
        Intent mainIntent = new Intent(OnboardingActivity.this, HomeActivity.class);
        OnboardingActivity.this.startActivity(mainIntent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        OnboardingActivity.this.finish();
    }

    private boolean hasLocationPermission() {
        return EasyPermissions.hasPermissions(this, LOCATION);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {

            Toast.makeText(this, "Location:"+(hasLocationPermission()?"yes":"no"), Toast.LENGTH_SHORT).show();

        }

    }

    public void showAlert(View view) {
        findViewById(R.id.textView_info).setVisibility(View.GONE);
        findViewById(R.id.button_letme).setVisibility(View.GONE);

        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.custom_alert, null);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setView(deleteDialogView);
        Button ok = deleteDialogView.findViewById(R.id.ok_button);

        final AlertDialog alertDialog1 = alertDialog.create();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hasLocationPermission()) {
                    // Have permissions, do the thing!
                    gotoHome();
                } else {
                    alertDialog1.dismiss();
                    findViewById(R.id.textView_info).setVisibility(View.VISIBLE);
                    findViewById(R.id.button_letme).setVisibility(View.VISIBLE);
                    // Ask for one permission
                    EasyPermissions.requestPermissions(
                            OnboardingActivity.this,
                             getString(R.string.rationale_location),
                            RC_LOCATION_PERM,
                            LOCATION);
                }

            }
        });


        alertDialog1.setCanceledOnTouchOutside(false);
        try {
            alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        alertDialog1.show();
        alertDialog1.getWindow().setLayout((int)Utils.convertDpToPixel(228,this),(int)Utils.convertDpToPixel(220,this));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog1.getWindow().getAttributes());
//        lp.height=200dp;
//        lp.width=228;
        lp.gravity = Gravity.CENTER;
        lp.windowAnimations = R.style.DialogAnimation;
        alertDialog1.getWindow().setAttributes(lp);
    }
}
