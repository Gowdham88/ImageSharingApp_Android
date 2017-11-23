package com.numnu.android.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.numnu.android.fragments.EventDetail.EventItemsListFragment;
import com.numnu.android.fragments.HomeFragment;
import com.numnu.android.utils.PreferencesHelper;

import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.numnu.android.utils.Utils.hideKeyboard;


public class LoginFragment extends Fragment {
    private static final String TAG ="LoginFragment";
    TextView textViewSignup;
    EditText mEmailField, mPasswordField;
    public ProgressDialog mProgressDialog;
    TextView ForgetPassTxt,EmailTxt,PassTxt;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    private Button login;
    private CallbackManager mCallbackManager;
    ConstraintLayout ConsLay;
    // [END declare_auth]
    String mPostBookmarkIntent,mBusinessBookmarkIntent,mProfileIntent,mEventBookmarkIntent,mReceivedIntent;
    private Context context;
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mPostBookmarkIntent = bundle.getString("BookmarkIntent");
            mProfileIntent =  bundle.getString("ProfileIntent");
            mEventBookmarkIntent =  bundle.getString("EventBookmarkIntent");
            mBusinessBookmarkIntent =  bundle.getString("BusinessBookmarkIntent");

        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.activity_login, container, false);

        textViewSignup = view.findViewById(R.id.textView_signup);
        mEmailField = view.findViewById(R.id.et_email);
        mPasswordField = view.findViewById(R.id.et_password);
        ForgetPassTxt=(TextView)view.findViewById(R.id.txt_forget_pwd);
        EmailTxt=(TextView) view.findViewById(R.id.textView5);
        PassTxt=(TextView) view.findViewById(R.id.textView6);


        mEmailField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    EmailTxt.setTextColor(getResources().getColor(R.color.weblink_color));
                }
                else{
                    EmailTxt.setTextColor(getResources().getColor(R.color.email_color));
                }
            }
        });

        mPasswordField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    PassTxt.setTextColor(getResources().getColor(R.color.weblink_color));
                }
                else{
                    PassTxt.setTextColor(getResources().getColor(R.color.email_color));
                }
            }
        });
        ForgetPassTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, ForgetPassWordFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        textViewSignup.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        ConsLay=view.findViewById(R.id.const_lay);
        ConsLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         hideKeyboard(getActivity());
            }
        });
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();

        mCallbackManager = CallbackManager.Factory.create();
        // [END initialize_auth]
        Button loginButton = view.findViewById(R.id.login_btn_facebook);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("email", "public_profile"));
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

        if (mProfileIntent!=null){
            mReceivedIntent = mProfileIntent;    //    private void hidekeyboard() {
//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(ConsLay.getWindowToken(), 0);
//    }

        }else if (mPostBookmarkIntent !=null){
            mReceivedIntent = mPostBookmarkIntent;
        }else if (mEventBookmarkIntent!=null){
            mReceivedIntent = mEventBookmarkIntent;
        }else if (mBusinessBookmarkIntent!=null){
            mReceivedIntent = mBusinessBookmarkIntent;
        }else {
            mReceivedIntent = null;
        }

        login = view.findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());

            }
        });

        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


//    public void forgetPassword(View view) {
//        Intent mainIntent = new Intent(context,ForgetPasswordActivity.class);
//        LoginFragment.this.startActivity(mainIntent);
//    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signUpWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signUpWithCredential:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }


    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    // [END on_start_check_user]


    private void signIn(final String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            PreferencesHelper.setPreferenceBoolean(getApplicationContext(),PreferencesHelper.PREFERENCE_LOGGED_IN,true);
                            PreferencesHelper.setPreference(context, PreferencesHelper.PREFERENCE_EMAIL, email);
                            String bookmarkBundle = "bookmark";
                            String profileBundle = "profile";
                            String eventBookmarkBundle = "eventbookmark";
                            String businessBookmarkBundle = "businessbookmark";
                            if (mReceivedIntent == null){
                                goToHomeActivity(null,null);
                            } else if (mReceivedIntent.equals(bookmarkBundle)) {
                                goToHomeActivity("BookmarkIntent",bookmarkBundle);
                            }else if (mReceivedIntent.equals(profileBundle)){
                                goToHomeActivity("ProfileIntent",profileBundle);
                            }else if (mReceivedIntent.equals(eventBookmarkBundle)) {
                                goToHomeActivity("EventBookmarkIntent",eventBookmarkBundle);
                            }else if (mReceivedIntent.equals(businessBookmarkBundle)) {
                                goToHomeActivity("BusinessBookmarkIntent",businessBookmarkBundle);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Toast.makeText(context, "Auth Failed!", Toast.LENGTH_SHORT).show();
                        }
                        hideProgressDialog();

                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }


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
        if (TextUtils.isEmpty(password) || password.length()<4) {
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

    public void signUp() {

        String bookmarkBundle = "bookmark";
        String profileBundle = "profile";
        String eventBookmarkBundle = "eventbookmark";
        String businessBookmarkBundle = "businessbookmark";
        if (mReceivedIntent == null){
            signUpCall(null,null);
        } else if (mReceivedIntent.equals(bookmarkBundle)) {
            signUpCall("BookmarkIntent",bookmarkBundle);
        }else if (mReceivedIntent.equals(profileBundle)){
            signUpCall("ProfileIntent",profileBundle);
        }else if (mReceivedIntent.equals(eventBookmarkBundle)){
            signUpCall("EventBookmarkIntent",bookmarkBundle);
        }else if (mReceivedIntent.equals(eventBookmarkBundle)){
            signUpCall("BusinessBookmarkIntent",businessBookmarkBundle);
        }
    }

    private void signUpCall(String intentName, String intentValue){
        Bundle bundle = new Bundle();
        bundle.putString(intentName,  intentValue);
        SignupFragment signupFragment=new SignupFragment();
        signupFragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout,signupFragment);
        transaction.commit();
    }

    private void goToHomeActivity(String intentName, String intentValue){
        Bundle bundle = new Bundle();
        bundle.putString(intentName,  intentValue);
        HomeFragment homeFragment=new HomeFragment();
        homeFragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout,homeFragment);
        transaction.commit();

    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}


