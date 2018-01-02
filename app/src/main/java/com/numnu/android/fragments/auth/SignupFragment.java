package com.numnu.android.fragments.auth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
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
import com.google.firebase.auth.GetTokenResult;
import com.numnu.android.R;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.PreferencesHelper;

import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.numnu.android.utils.Utils.hideKeyboard;

public class SignupFragment extends Fragment {

    private static final String TAG = "SignUpActivity";
    TextView textViewSignIn, TxtEmai, TxtPass;
    EditText mEmailField, mPasswordField;
    public ProgressDialog mProgressDialog;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    ConstraintLayout SignupConsLay;
    // [END declare_auth]
    String mBookmarkIntent, mProfileIntent, mEventBookmarkIntent, mReceivedIntent;
    TextView txt_error;
    View emailview,passview;
    private Context context;
    private boolean shown=false;
    private AlertDialog dialog;

    public static SignupFragment newInstance() {
        SignupFragment fragment = new SignupFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mBookmarkIntent = bundle.getString("BookmarkIntent");
            mProfileIntent = bundle.getString("ProfileIntent");
            mEventBookmarkIntent = bundle.getString("EventBookmarkIntent");
        }

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {

            currentUser.unlink(currentUser.getProviderId());
            LoginManager.getInstance().logOut();
            mAuth.signOut();

        }
    }


    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().finish();
                    // handle back button

                    return true;

                }

                return false;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_signup, container, false);
        textViewSignIn = view.findViewById(R.id.textView_signin);
        TxtEmai = view.findViewById(R.id.signup_textView5);
        TxtPass = view.findViewById(R.id.signup_textView6);
        mEmailField = view.findViewById(R.id.et_email);
        mPasswordField = view.findViewById(R.id.et_password);
        txt_error = (TextView) view.findViewById(R.id.txt_error);
        textViewSignIn.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        emailview=view.findViewById(R.id.username_view);
        passview=view.findViewById(R.id.pass_view);
        SignupConsLay = view.findViewById(R.id.signup_const_lay);
        SignupConsLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(getActivity());
            }
        });

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        hideerror();

        setupFocusListeners();

        // [START initialize_auth]

        mCallbackManager = CallbackManager.Factory.create();
        // [END initialize_auth]
        FrameLayout loginButton = view.findViewById(R.id.sinup_btn_facebook);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                // App code
                                Log.e(TAG, "facebook:onSuccess:" + loginResult);

                                handleFacebookAccessToken(loginResult.getAccessToken());
                            }

                            @Override
                            public void onCancel() {
                                Log.d(TAG, "facebook:onCancel");

                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Log.d(TAG, "facebook:onError", exception);

                            }
                        });

                // [END initialize_fblogin]
            }

        });
        // [END initialize_auth]


        if (mProfileIntent != null) {
            mReceivedIntent = mProfileIntent;
        } else if (mBookmarkIntent != null) {
            mReceivedIntent = mBookmarkIntent;
        } else if (mEventBookmarkIntent != null) {
            mReceivedIntent = mEventBookmarkIntent;
        } else {
            mReceivedIntent = null;
        }

        view.findViewById(R.id.button_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());

            }
        });
//        view.findViewById(R.id.button_signup).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout, CompleteSignupFragment.newInstance());
//                transaction.addToBackStack(null).commit();
//            }
//        });

        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(final AccessToken token) {
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

                                                FirebaseUser user = mAuth.getCurrentUser();
                                                user.getIdToken(true)
                                                        .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                                            public void onComplete(@NonNull Task<GetTokenResult> task) {
                                                                if (task.isSuccessful()) {

                                                                    // Sign in success, update UI with the signed-in user's information
                                                                    Log.d(TAG, "signUpWithCredential:success");
                                                                    final FirebaseUser user = mAuth.getCurrentUser();
                                                                    String bookmarkBundle = "bookmark";
                                                                    String profileBundle = "profile";
                                                                    String eventBookmarkBundle = "eventbookmark";
                                                                    PreferencesHelper.setPreference(getApplicationContext(), PreferencesHelper.PREFERENCE_EMAIL, user.getEmail());
                                                                    PreferencesHelper.setPreference(getApplicationContext(), PreferencesHelper.PREFERENCE_USER_NAME, user.getDisplayName());
                                                                    PreferencesHelper.setPreference(getApplicationContext(), PreferencesHelper.PREFERENCE_PROFILE_PIC, String.valueOf(user.getPhotoUrl()));

                                                                    hideProgressDialog();
                                                                    if (!shown) {
                                                                        completeSignup();
                                                                    }
                                                                    shown = true;
                                                                }

                                                            }
                                                        });


                                              } else {
                                                                   // If sign in fails, display a message to the user.
                                                Log.w(TAG, "signUpWithCredential:failure", task.getException());
                                                showerror("Authentication failed.");

                                            }


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

    private void createAccount(final String email, final String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
            showProgressDialog();

            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.getIdToken(true)
                                        .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                            public void onComplete(@NonNull Task<GetTokenResult> task) {
                                                if (task.isSuccessful()) {
                                                    // Sign in success, update UI with the signed-in user's information
                                                    Log.d(TAG, "createUserWithEmail:success");
                                                    final FirebaseUser user = mAuth.getCurrentUser();
                                                    mPasswordField.setText("");

                                                    String bookmarkBundle = "bookmark";
                                                    String profileBundle = "profile";
                                                    String eventBookmarkBundle = "eventbookmark";

                                                    PreferencesHelper.setPreference(getApplicationContext(), PreferencesHelper.PREFERENCE_EMAIL, email);
                                                    PreferencesHelper.setPreference(getApplicationContext(), PreferencesHelper.PREFERENCE_FIREBASE_UUID, user.getUid());
                                                    hideProgressDialog();
                                                    completeSignup();
                                                }
                                            }
                                        });


                            } else {
                                Toast.makeText(getActivity(), "Registration failed! " + "\n" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                                showerror(" email is already exists");
                                hideProgressDialog();
                            }
                        }

                    });



    }

   private void  completeSignup(){

       CompleteSignupFragment loginFragment1 = new CompleteSignupFragment();
       FragmentTransaction transaction = getFragmentManager().beginTransaction();
       transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right, R.anim.exit_to_left);
       transaction.replace(R.id.frame_layout, loginFragment1);
       transaction.addToBackStack(null).commit();
    }

    private void signOut() {
        mAuth.signOut();
    }

//    private void sendEmailVerification() {
//        // Disable button
//        view.findViewById(R.id.verify_email_button).setEnabled(false);
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
//                        view.findViewById(R.id.verify_email_button).setEnabled(true);
//
//                        if (task.isSuccessful()) {
//                            Toast.makeText(LoginFragment.this,
//                                    "Verification email sent to " + user.getEmail(),
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            Log.e(TAG, "sendEmailVerification", task.getException());
//                            Toast.makeText(LoginFragment.this,
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
        String password = mPasswordField.getText().toString();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            valid = true;

        } else {

            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                showerror("Enter email address and password.");
                valid = false;
            }
            else if((email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()))
            {
                Toast.makeText(context, "enter a valid email address", Toast.LENGTH_SHORT).show();
//            mEmail.setError("enter a valid email address");
                valid = false;
            }
            else if (TextUtils.isEmpty(password) || password.length() < 4) {
                showerror("Enter password");
                valid = false;
            } else {
                showerror("Enter email address.");
                valid = false;
            }


        }

        return valid;
    }


    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public void signIn() {

        String bookmarkBundle = "bookmark";
        String profileBundle = "profile";
        String eventBookmarkBundle = "eventbookmark";
        if (mReceivedIntent == null) {

            LoginFragment loginFragment1 = new LoginFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.frame_layout, loginFragment1);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (mReceivedIntent.equals(bookmarkBundle)) {

            Bundle bundle = new Bundle();
            bundle.putString("BookmarkIntent", bookmarkBundle);
            showFragment(bundle);

        } else if (mReceivedIntent.equals(profileBundle)) {

            Bundle bundle = new Bundle();
            bundle.putString("ProfileIntent", bookmarkBundle);
            showFragment(bundle);
        } else if (mReceivedIntent.equals(eventBookmarkBundle)) {

            Bundle bundle = new Bundle();
            bundle.putString("EventBookmarkIntent", bookmarkBundle);
            showFragment(bundle);
        }

    }

    private void showFragment(Bundle bundle) {

        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.frame_layout, loginFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showProgressDialog() {


        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(getActivity());
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
    public void showerror(String error) {

        txt_error.setText(error);
        final Animation animShake = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_shake);
        txt_error.startAnimation(animShake);
        txt_error.setVisibility(View.VISIBLE);

    }

    public void hideerror() {

        txt_error.setVisibility(View.GONE);
    }

    private void setupFocusListeners() {
        mEmailField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    TxtEmai.setTextColor(getResources().getColor(R.color.weblink_color));
                    emailview.setBackgroundColor(getResources().getColor(R.color.weblink_color));
                }
                else{
                    TxtEmai.setTextColor(getResources().getColor(R.color.email_color));
                    emailview.setBackgroundColor(getResources().getColor(R.color.email_color));
                }
            }
        });

        mPasswordField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    TxtPass.setTextColor(getResources().getColor(R.color.weblink_color));
                    passview.setBackgroundColor(getResources().getColor(R.color.weblink_color));
                    hideerror();
                }
                else{
                    TxtPass.setTextColor(getResources().getColor(R.color.email_color));
                    passview.setBackgroundColor(getResources().getColor(R.color.email_color));
                }

            }
        });

        mEmailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                hideerror();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                hideerror();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}