package com.numnu.android.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.numnu.android.R;
import com.numnu.android.utils.PreferencesHelper;

import java.util.Arrays;

public class SignupActivity extends MyActivity  {

    private static final String TAG ="SignUpActivity";
    TextView textView;
    EditText mEmailField, mPasswordField;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    // [END declare_auth]
    String mBookmarkIntent,mProfileIntent,mEventBookmarkIntent,mReceivedIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textView=findViewById(R.id.textView3);
        mEmailField =findViewById(R.id.et_email);
        mPasswordField =findViewById(R.id.et_password);

        textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        // [END initialize_auth]
        Button loginButton = findViewById(R.id.login_btn_facebook);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(SignupActivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager,
                        new FacebookCallback<LoginResult>()
                        {
                            @Override
                            public void onSuccess(LoginResult loginResult)
                            {
                                // App code
                                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                                handleFacebookAccessToken(loginResult.getAccessToken());
                            }

                            @Override
                            public void onCancel()
                            {
                                Log.d(TAG, "facebook:onCancel");
                            }

                            @Override
                            public void onError(FacebookException exception)
                            {
                                Log.d(TAG, "facebook:onError", exception);
                            }
                        });

                // [END initialize_fblogin]
            }
        });
        // [END initialize_auth]

        mBookmarkIntent = getIntent().getStringExtra("BookmarkIntent");
        mProfileIntent = getIntent().getStringExtra("ProfileIntent");
        mEventBookmarkIntent = getIntent().getStringExtra("EventBookmarkIntent");

        if (mProfileIntent!=null){
            mReceivedIntent = mProfileIntent;
        }else if (mBookmarkIntent!=null){
            mReceivedIntent = mBookmarkIntent;
        }else if (mEventBookmarkIntent!=null){
            mReceivedIntent = mEventBookmarkIntent;
        }else {
            mReceivedIntent = null;
        }

        findViewById(R.id.button_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signUpWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signUpWithCredential:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    public void forgetPassword(View view) {
        Intent mainIntent = new Intent(this,ForgetPasswordActivity.class);
        this.startActivity(mainIntent);
    }


    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    // [END on_start_check_user]

    private void createAccount(final String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            String bookmarkBundle = "bookmark";
                            String profileBundle = "profile";
                            String eventBookmarkBundle = "eventbookmark";

                            if (mReceivedIntent == null){
                                Intent mainIntent = new Intent(SignupActivity.this, CompleteSignupActivity.class);
                                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                SignupActivity.this.startActivity(mainIntent);
                                SignupActivity.this.finish();
                                PreferencesHelper.setPreference(SignupActivity.this, PreferencesHelper.PREFERENCE_EMAIL, email);
                                PreferencesHelper.setPreferenceBoolean(getApplicationContext(),PreferencesHelper.PREFERENCE_LOGGED_IN,true);
                            }else if (mReceivedIntent.equals(bookmarkBundle)){
                                Intent mainIntent = new Intent(SignupActivity.this, CompleteSignupActivity.class);
                                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                mainIntent.putExtra("BookmarkIntent",bookmarkBundle);
                                SignupActivity.this.startActivity(mainIntent);
                                SignupActivity.this.finish();
                                PreferencesHelper.setPreference(SignupActivity.this, PreferencesHelper.PREFERENCE_EMAIL, email);
                                PreferencesHelper.setPreferenceBoolean(getApplicationContext(),PreferencesHelper.PREFERENCE_LOGGED_IN,true);
                            }else if (mReceivedIntent.equals(profileBundle)){
                                Intent mainIntent = new Intent(SignupActivity.this, CompleteSignupActivity.class);
                                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                mainIntent.putExtra("ProfileIntent",profileBundle);
                                SignupActivity.this.startActivity(mainIntent);
                                SignupActivity.this.finish();
                                PreferencesHelper.setPreference(SignupActivity.this, PreferencesHelper.PREFERENCE_EMAIL, email);
                                PreferencesHelper.setPreferenceBoolean(getApplicationContext(),PreferencesHelper.PREFERENCE_LOGGED_IN,true);
                            }else if (mReceivedIntent.equals(eventBookmarkBundle)) {
                                Intent mainIntent = new Intent(SignupActivity.this, CompleteSignupActivity.class);
                                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                mainIntent.putExtra("EventBookmarkIntent", eventBookmarkBundle);
                                SignupActivity.this.startActivity(mainIntent);
                                SignupActivity.this.finish();
                                PreferencesHelper.setPreference(SignupActivity.this, PreferencesHelper.PREFERENCE_EMAIL, email);
                                PreferencesHelper.setPreferenceBoolean(getApplicationContext(),PreferencesHelper.PREFERENCE_LOGGED_IN,true);
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void signOut() {
        mAuth.signOut();
    }

//    private void sendEmailVerification() {
//        // Disable button
//        findViewById(R.id.verify_email_button).setEnabled(false);
//
//        // Send verification email
//        // [START send_email_verification]
//        final FirebaseUser user = mAuth.getCurrentUser();
//        user.sendEmailVerification()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // [START_EXCLUDE]
//                        // Re-enable button
//                        findViewById(R.id.verify_email_button).setEnabled(true);
//
//                        if (task.isSuccessful()) {
//                            Toast.makeText(LoginActivity.this,
//                                    "Verification email sent to " + user.getEmail(),
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            Log.e(TAG, "sendEmailVerification", task.getException());
//                            Toast.makeText(LoginActivity.this,
//                                    "Failed to send verification email.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                        // [END_EXCLUDE]
//                    }
//                });
//        // [END send_email_verification]
//    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }




    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public void signIn(View view) {
        String bookmarkBundle = "bookmark";
        String profileBundle = "profile";
        String eventBookmarkBundle = "eventbookmark";
        if (mReceivedIntent == null){
            Intent mainIntent = new Intent(SignupActivity.this,LoginActivity.class);
            this.startActivity(mainIntent);
            this.finish();
        }
        else if(mReceivedIntent.equals(bookmarkBundle)) {
            Intent mainIntent = new Intent(SignupActivity.this, LoginActivity.class);
//            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mainIntent.putExtra("BookmarkIntent",bookmarkBundle);
            SignupActivity.this.startActivity(mainIntent);
            SignupActivity.this.finish();

        }else if (mReceivedIntent.equals(profileBundle)){
            Intent mainIntent = new Intent(SignupActivity.this, LoginActivity.class);
//            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mainIntent.putExtra("ProfileIntent",bookmarkBundle);
            SignupActivity.this.startActivity(mainIntent);
            SignupActivity.this.finish();
        }else if (mReceivedIntent.equals(eventBookmarkBundle)){
            Intent mainIntent = new Intent(SignupActivity.this, LoginActivity.class);
//            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mainIntent.putExtra("EventBookmarkIntent",bookmarkBundle);
            SignupActivity.this.startActivity(mainIntent);
            SignupActivity.this.finish();
        }
    }
}