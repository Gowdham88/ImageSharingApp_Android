package com.numnu.android.fragments.auth;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.numnu.android.fragments.home.UserPostsFragment;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.LoginResponse;
import com.numnu.android.network.response.TagsItem;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.PreferencesHelper;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.numnu.android.utils.Utils.hideKeyboard;


public class LoginFragment extends Fragment {

    private static final String TAG ="LoginFragment";
    TextView textViewSignup;
    EditText mEmailField, mPasswordField;
    public ProgressDialog mProgressDialog;
    TextView ForgetPassTxt,EmailTxt,PassTxt;
    ImageView backButton;
    TextView txt_error;
    TextInputEditText emailtxtinlay,passTxtinLay;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    private Button login;
    private CallbackManager mCallbackManager;
    ConstraintLayout ConsLay;
    View emailview,passview;
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

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {

            currentUser.unlink(currentUser.getProviderId());
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

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
//                    getActivity().finish();
                    // handle back button

                    return true;

                }

                return false;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.activity_login, container, false);

        textViewSignup = view.findViewById(R.id.textView_signup);
        mEmailField    = view.findViewById(R.id.et_email);
        mPasswordField = view.findViewById(R.id.et_password);
        ForgetPassTxt  = (TextView)view.findViewById(R.id.txt_forget_pwd);
        EmailTxt       = (TextView) view.findViewById(R.id.textView5);
        PassTxt        = (TextView) view.findViewById(R.id.textView6);
        backButton     = (ImageView)view.findViewById(R.id.toolbar_back);
        txt_error      = (TextView) view.findViewById(R.id.txt_error);
        emailtxtinlay=(TextInputEditText) view.findViewById(R.id.et_email);
        passTxtinLay=(TextInputEditText) view.findViewById(R.id.et_password);
        textViewSignup.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        ConsLay   =view.findViewById(R.id.const_lay);
        emailview=view.findViewById(R.id.username_view);
        passview=view.findViewById(R.id.pass_view);
        ConsLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         hideKeyboard(getActivity());
            }
        });
        // [START initialize_auth]


        mCallbackManager = CallbackManager.Factory.create();
        // [END initialize_auth]
        FrameLayout loginButton = view.findViewById(R.id.login_btn_facebook);
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
                                showerror("Facebook login cancelled.");
                            }

                            @Override
                            public void onError(FacebookException exception)
                            {
                                Log.d(TAG, "facebook:onError", exception);
                                showerror("Facebook login failed.");
                            }
                        });

                // [END initialize_fblogin]
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();

            }
        });


        setupFocusListeners();


        ForgetPassTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frame_layout, ForgetPassWordFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        hideerror();

        if (mProfileIntent!=null){
            mReceivedIntent = mProfileIntent;
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



    private void  loginWithServer() {

        FirebaseUser user = mAuth.getCurrentUser();
        user.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {

                            String idToken = task.getResult().getToken();
                            Log.e("Token:", idToken);
                            Constants.FIREBASE_TOKEN = idToken;
                            PreferencesHelper.setPreference(getApplicationContext(), PreferencesHelper.PREFERENCE_FIREBASE_TOKEN, idToken);
                            // Send token to your backend via HTTPS
                            ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
                            Call<LoginResponse> call = apiServices.login();
                            call.enqueue(new Callback<LoginResponse>() {

                                @Override
                                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                    int responsecode = response.code();
                                    LoginResponse body = response.body();
                                    if(responsecode==200) {
                                        PreferencesHelper.setPreference(context, PreferencesHelper.PREFERENCE_EMAIL, body.getEmail() != null ? body.getEmail() : "");

                                        PreferencesHelper.setPreference(context, PreferencesHelper.PREFERENCE_USER_NAME, body.getUsername() != null ? body.getUsername() : "");
                                        // TODO: 5/12/17
//                PreferencesHelper.setPreference(context, PreferencesHelper.PREFERENCE_NAME,body.get);
                                        PreferencesHelper.setPreference(context, PreferencesHelper.PREFERENCE_ID, String.valueOf(body.getId()));
                                        PreferencesHelper.setPreference(context, PreferencesHelper.PREFERENCE_DOB, body.getDateofbirth());
                                        PreferencesHelper.setPreference(context, PreferencesHelper.PREFERENCE_USER_DESCRIPTION, body.getDescription());
                                        PreferencesHelper.setPreference(context, PreferencesHelper.PREFERENCE_GENDER, (body.getGender() == 0) ? "Male" : "Female");
                                        PreferencesHelper.setPreference(context, PreferencesHelper.PREFERENCE_CITY, body.getCitylocation().getName());
//                PreferencesHelper.setPreference(context, PreferencesHelper.PREFERENCE_PROFILE_PIC,body.getUserimages().get(0).getImageurl());
                                        PreferencesHelper.setPreferenceBoolean(getApplicationContext(), PreferencesHelper.PREFERENCE_LOGGED_IN, true);

                                        String tagsString = "";
                                        String tagsIds = "";
                                        for (TagsItem tag : body.getTags()) {
                                            tagsString = tag.getText() + ",";
                                            tagsIds = tag.getId() + ",";
                                        }

                                        PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_TAGS, tagsString);
                                        PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_TAG_IDS, tagsIds);

                                        goToHomeActivity();

                                        hideProgressDialog();

                                    }else if(responsecode==404){
                                        hideProgressDialog();
                                        Toast.makeText(context, "Invalid User!.Please Register!", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<LoginResponse> call, Throwable t) {
                                    Toast.makeText(context, "Server error!", Toast.LENGTH_SHORT).show();
                                    hideProgressDialog();

                                }
                            });

                        }
                    }

                });


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
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signUpWithCredential:success");
                            final FirebaseUser user = mAuth.getCurrentUser();
                            PreferencesHelper.setPreference(getApplicationContext(), PreferencesHelper.PREFERENCE_EMAIL, user.getEmail());

                            loginWithServer();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signUpWithCredential:failure", task.getException());
                            showerror("Authentication failed.");

                        }

                    }
                });


    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        // [START on_start_check_user]
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // [END on_start_check_user]
    }



    private void signIn(final String email, final String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

            showProgressDialog();


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                loginWithServer();


                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getActivity(), "Registration failed! " + "\n" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                showerror("Authentication failed.");
                                hideProgressDialog();

                            }

                            // [START_EXCLUDE]
                            if (!task.isSuccessful()) {

                                hideProgressDialog();
                                showerror("Authentication failed.");
                            }
                        }
                    });

    }


    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

              valid = true;

        } else {

            if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                showerror("Enter email address and password.");
                valid = false;
            }
            else if((email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()))
            {
                Toast.makeText(context, "enter a valid email address", Toast.LENGTH_SHORT).show();
//            mEmail.setError("enter a valid email address");
                valid = false;
            }else if (TextUtils.isEmpty(password) || password.length()<4) {
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
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.frame_layout,signupFragment);
        transaction.commit();
    }

    private void goToHomeActivity(){

        mEmailField.setText("");
        mPasswordField.setText("");

        String bookmarkBundle = "bookmark";
        String profileBundle = "profile";
        String eventBookmarkBundle = "eventbookmark";
        String businessBookmarkBundle = "businessbookmark";

        Fragment fragment=new UserPostsFragment();
        if (mReceivedIntent == null){
            fragment = new UserPostsFragment();
        } else if (mReceivedIntent.equals(bookmarkBundle)) {
            fragment = new UserPostsFragment();

        }else if (mReceivedIntent.equals(profileBundle)){
            fragment = new UserPostsFragment();

        }else if (mReceivedIntent.equals(eventBookmarkBundle)) {
            fragment = new UserPostsFragment();

        }else if (mReceivedIntent.equals(businessBookmarkBundle)) {
            fragment = new UserPostsFragment();

        }

//        Bundle bundle = new Bundle();
//        bundle.putString(intentName,  intentValue);
//        UserPostsFragment homeFragment=new UserPostsFragment();
//        homeFragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.frame_layout,fragment);
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

    public void showerror(String error) {

        txt_error.setText(error);
        final Animation animShake = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_shake);
        txt_error.startAnimation(animShake);
        txt_error.setVisibility(View.VISIBLE);

    }

    public void hideerror(){

        txt_error.setVisibility(View.GONE);
    }

    private void setupFocusListeners() {
        mEmailField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    EmailTxt.setTextColor(getResources().getColor(R.color.weblink_color));
                    emailview.setBackgroundColor(getResources().getColor(R.color.weblink_color));
                }
                else{
                    EmailTxt.setTextColor(getResources().getColor(R.color.email_color));
                    emailview.setBackgroundColor(getResources().getColor(R.color.email_color));
                }
            }
        });

        mPasswordField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    PassTxt.setTextColor(getResources().getColor(R.color.weblink_color));
                    passview.setBackgroundColor(getResources().getColor(R.color.weblink_color));
                    hideerror();
                }
                else{
                    PassTxt.setTextColor(getResources().getColor(R.color.email_color));
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


