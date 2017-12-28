package com.numnu.android.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.numnu.android.R;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.PreferencesHelper;
import com.numnu.android.utils.Utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.facebook.FacebookSdk.getApplicationContext;

public class OnboardingActivity extends MyActivity implements EasyPermissions.PermissionCallbacks,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,ActivityCompat.OnRequestPermissionsResultCallback {
    PackageInfo info;
    private static final String[] LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int RC_LOCATION_PERM = 1;
    LocationHelper locationHelper;
    private Location mLastLocation;
    TextView textView;
    Animation slideUpAnimation,slidedownAnimation;
    private FirebaseAuth mAuth;
    private String TAG=this.getClass().getSimpleName();
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        textView=(TextView)findViewById(R.id.textView_info);

        mAuth  = FirebaseAuth.getInstance();
        locationHelper=new LocationHelper(this);
//        locationHelper.checkpermission();
        if (locationHelper.checkPlayServices()) {

            // Building the GoogleApi client
            locationHelper.buildGoogleApiClient();
        }
//        try {
//            info = getPackageManager().getPackageInfo("com.numnu.android", PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md;
//                md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String something = new String(Base64.encode(md.digest(), 0));
//                //String something = new String(Base64.encodeBytes(md.digest()));
//                Log.e("hash key", something);
//            }
//        } catch (PackageManager.NameNotFoundException e1) {
//            Log.e("name not found", e1.toString());
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("no such an algorithm", e.toString());
//        } catch (Exception e) {
//            Log.e("exception", e.toString());
//        }
    }

    @Override
    public void onStart() {
        super.onStart();


        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            updateToken(currentUser);
        }else {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInAnonymously:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateToken(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInAnonymously:failure", task.getException());
                                Toast.makeText(OnboardingActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }
    }

    private void updateToken(FirebaseUser user) {
        user.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            Log.e("Token:", idToken);
                            Constants.FIREBASE_TOKEN = idToken;
                            PreferencesHelper.setPreference(getApplicationContext(), PreferencesHelper.PREFERENCE_FIREBASE_TOKEN, idToken);
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_LOCATION_PERM)
    private void gotoHome() {
//        if (locationHelper.checkPlayServices()) {
//
//            // Building the GoogleApi client
//            locationHelper.buildGoogleApiClient();
//        }
//        locationHelper=new LocationHelper(this);
//        locationHelper.checkpermission();
//        mLastLocation=locationHelper.getLocation();
//
//        if (mLastLocation != null) {
//            latitude = mLastLocation.getLatitude();
//            longitude = mLastLocation.getLongitude();
//            getAddress();
//
//        } else {
            Intent mainIntent = new Intent(OnboardingActivity.this, HomeActivity.class);
            OnboardingActivity.this.startActivity(mainIntent);
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            OnboardingActivity.this.finish();
//        }

    }

    public void getAddress()
    {
        Address locationAddress;

        locationAddress=locationHelper.getAddress(latitude,longitude);

        if(locationAddress!=null)
        {

            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();


            String currentLocation;

            if(!TextUtils.isEmpty(address))
            {
                currentLocation=address;

                if (!TextUtils.isEmpty(address1))
                    currentLocation+=address1+",";

                if (!TextUtils.isEmpty(city))
                {
                    currentLocation+=city+",";

                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation+=postalCode+",";
                }
                else
                {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation+=postalCode+",";
                }

                if (!TextUtils.isEmpty(state))
                    currentLocation+=state+",";

                if (!TextUtils.isEmpty(country))
                    currentLocation+=country+",";

//                tvEmpty.setVisibility(View.GONE);
//                tvAddress.setText(currentLocation);
//                tvAddress.setVisibility(View.VISIBLE);
//
//                if(!btnProceed.isEnabled())
//                    btnProceed.setEnabled(true);
            }

        }
        else{

        }
//            showToast("Something went wrong");
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

            Toast.makeText(this, "Location:" + (hasLocationPermission() ? "yes" : "no"), Toast.LENGTH_SHORT).show();

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

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("Connection failed:", " ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        mLastLocation=locationHelper.getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        locationHelper.connectApiClient();
    }
}
