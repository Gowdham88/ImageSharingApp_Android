package com.numnu.android.fragments.home;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.numnu.android.BuildConfig;
import com.numnu.android.R;
import com.numnu.android.adapter.FoodAdapter;
import com.numnu.android.adapter.PlaceAutocompleteAdapter;
import com.numnu.android.adapter.TagsAutocompleteAdapter;
import com.numnu.android.fragments.detail.SearchBusinessDetailFragment;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.request.Citylocation;
import com.numnu.android.network.request.CompleteSignUpData;
import com.numnu.android.network.request.Tag;
import com.numnu.android.network.response.CommonResponse;
import com.numnu.android.network.response.SignupResponse;
import com.numnu.android.network.response.Tagsuggestion;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.PreferencesHelper;
import com.numnu.android.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.numnu.android.utils.Utils.hideKeyboard;

/**
 * Created by lenovo on 11/18/2017.
 */

public class  EditProfileFragment extends Fragment implements EasyPermissions.PermissionCallbacks{

    Context context;
    EditText musername,mEmail, mName,mDob, userDescription;
    AutoCompleteTextView mCity;
    TextView mCompleteSignUp;
    RadioGroup mRadioGroup;
    RadioButton mRadioMale, mRadioFemale;
    private String mGenderValue = "";
    private DatePickerDialog.OnDateSetListener datePickerDialog;
    private SimpleDateFormat dateFormat;
    RecyclerView recyclerView;
    FoodAdapter adapter;
    LinearLayout FoodLinearLay;
    TextView AddTxt;
    AutoCompleteTextView autoComplete;
    String ItemModelList,cityid;
    ArrayList<Tagsuggestion> mylist = new ArrayList<>();
    ImageView viewImage, EditBtn;
    TextView Gallery,mGender;
    TextView Camera;
    ImageView GalleryIcon;
    ImageView CameraIcon,GenderDropimg;
    private String selectedImagePath = "";
    final private int PICK_IMAGE = 1;
    final private int CAPTURE_IMAGE = 2;
    private String imgPath;
    RelativeLayout EditReLay;
    String GenderStr;
    LinearLayout EditLinearLay,Linearlay;
    ScrollView nestedScrollView;

    public String placeId, placeType = "city", placeAddress;

    private RecyclerView myRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private static final String[] CAMERA= {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    String AutocompleteStr;
    private static final int REQUEST_CAMERA = 1888;

    final private int RC_PICK_IMAGE = 1;
    final private int RC_CAPTURE_IMAGE = 2;
    private static final int PERMISSIONS_REQUEST_CAMERA = 1888;
    private static final int PERMISSIONS_REQUEST_GALLERY = 1889;
    /**
     * GeoDataClient wraps our service connection to Google Play services and provides access
     * to the Google Places API for Android.
     */
    protected GeoDataClient mGeoDataClient;

    private PlaceAutocompleteAdapter mAdapter;
    ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
    private TagsAutocompleteAdapter tagsAutocompleteAdapter;
    private FirebaseAuth mAuth;
    private Uri fileUri;
    private String mCurrentPhotoPath;
    private ProgressDialog mProgressDialog;
    private String userId;
    String USERId;
    // Create a storage reference from our app
    StorageReference storageRef ;
    private FirebaseStorage storage;
    private double latitude,longitude;
    private AlertDialog dialog;
    SignupResponse signupResp;
    Citylocation   citylocation ;
    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.edit_profile_frag, container, false);
        TextView toolbarTitle = v.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Edit Profile");
        recyclerView = (RecyclerView) v.findViewById(R.id.food_recyclerview);
        FoodLinearLay = (LinearLayout) v.findViewById(R.id.food_layout);
        EditBtn = (ImageView) v.findViewById(R.id.imageView_profile_edit);
        viewImage = (ImageView) v.findViewById(R.id.profile_image);
        EditBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showBottomSheet(inflater);
            }
        });
        EditLinearLay=(LinearLayout)v.findViewById(R.id.linear_lay);
        EditReLay=(RelativeLayout) v.findViewById(R.id.editrel_lay);
        GenderDropimg=(ImageView) v.findViewById(R.id.gender_img);
        nestedScrollView = (ScrollView) v.findViewById(R.id.nestedScrollView);
        RelativeLayout Toolbarbackicon=v.findViewById(R.id.toolbar_back);
        Toolbarbackicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        EditLinearLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(getActivity());
            }
        });
        EditReLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(getActivity());
            }
        });
//        adapter.setClickListener(this);
        autoComplete = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextView1);
        AddTxt = (TextView) v.findViewById(R.id.add_txt);

        setupTagAutocomplete();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        citylocation = new Citylocation();
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();

        musername = v.findViewById(R.id.et_cmpltsignup_username);
        mEmail    = v.findViewById(R.id.et_signup_email);
        mName     = v.findViewById(R.id.et_signup_name);
        mCity     = v.findViewById(R.id.et_signup_city);
        mGender   = v.findViewById(R.id.ed_gender);
        userDescription = v.findViewById(R.id.et_user_description);

//        mRadioGroup = v.findViewById(R.id.radio_group);
//        mRadioMale = v.findViewById(R.id.male_radio);
//        mRadioFemale = v.findViewById(R.id.female_radio);
        mDob = v.findViewById(R.id.et_signup_dob);

        mCompleteSignUp =(TextView) v.findViewById(R.id.button_complete_signup);
        mCompleteSignUp.setText("Save");
        mCompleteSignUp.setVisibility(View.VISIBLE);
        mDob.setInputType(InputType.TYPE_NULL);
        mDob.requestFocus();

        USERId=PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_ID);
        Toast.makeText(context, USERId, Toast.LENGTH_SHORT).show();
        setupFocusListeners(v);


        // Construct a GeoDataClient for the Google Places API for Android.
        mGeoDataClient = Places.getGeoDataClient(getActivity(), null);
        mCity.setOnItemClickListener(mAutocompleteClickListener);
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
        // Set up the adapter that will retrieve suggestions from the Places Geo Data Client.
        mAdapter = new PlaceAutocompleteAdapter(getActivity(), mGeoDataClient, Constants.BOUNDS_GREATER_SYDNEY, autocompleteFilter);
        mCity.setAdapter(mAdapter);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        final Toolbar toolbar = v.findViewById(R.id.toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nestedScrollView.scrollTo(0,0);
            }
        });
        mGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getActivity());
                showAlert();
            }
        });
        GenderDropimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getActivity());
                showAlert();
            }
        });
//        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                if (checkedId == R.id.male_radio) {
//                    mGenderValue = "Male";
//
//                } else if (checkedId == R.id.female_radio) {
//                    mGenderValue = "Female";
//                }
//            }
//        });

        mDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(i, i1, i2);
                        mDob.setText(dateFormat.format(newDate.getTime()));
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                pickerDialog.show();
            }
        });


        updateUI();

        mCompleteSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {

                    if(!isUsernameChanged()){
                        completeSignUp();
                    }else {
                    //check username
                    checKUsernameExists(musername.getText().toString());

                    }

                }

            }
        });


        return v;
    }

    private boolean isUsernameChanged() {
        Boolean isUsernameChanged=false;
        String newusername = musername.getText().toString();
        String oldusername= PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_USER_NAME);
        if(!newusername.equals(oldusername)){
            isUsernameChanged=true;
        }

        return isUsernameChanged;
    }

    private void updateUI() {


        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();

        userId=PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_ID);
        String name=PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_NAME);
        String username= PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_USER_NAME);
        String email=PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_EMAIL);
        String city= PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_CITY);
        String cityid=PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_CITY_ID);
        String dob=PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_DOB);
        String gender=PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_GENDER);
        String userinfo=PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_USER_DESCRIPTION);

        String profilepic=PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_PROFILE_PIC);

//        SimpleDateFormat endformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
//        Date date = null;
//        try
//        {
//            date = endformat.parse(dob);
//        }
//        catch (ParseException e)
//        {
//
//            e.printStackTrace();
//        }
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String dobString = dateFormat.format(date);
        musername.setText(username);
        mEmail.setText(email);
        mName.setText(name!=null?name:"");
        mCity.setText(city);
        mGender.setText(gender);
        mDob.setText(dob);
        userDescription.setText(userinfo!=null?userinfo:"");
        citylocation = new Citylocation();
        citylocation.setId(cityid);

        if(!profilepic.isEmpty()&&profilepic!=null) {
            if (profilepic.startsWith("https")) {
                Picasso.with(context).load(profilepic)
                        .fit()
                        .placeholder(R.drawable.background)
                        .into(viewImage);
            } else {
                storageRef.child(profilepic).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        Picasso.with(context).load(uri)
                                .placeholder(R.drawable.background)
                                .into(viewImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors

                    }
                });
            }
        }

        //prepare Tag List
        String tags=PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_TAGS);
        String tagIds=PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_TAG_IDS);

        placeId =PreferencesHelper.getPreference(context, PreferencesHelper.PREFERENCE_GOOGLE_PLACE_ID);
        placeType=PreferencesHelper.getPreference(context, PreferencesHelper.PREFERENCE_GOOGLE_PLACE_TYPE);
        try {
            latitude = Double.parseDouble(PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_LATITUDE));
            longitude = Double.parseDouble(PreferencesHelper.getPreference(getActivity(), PreferencesHelper.PREFERENCE_LONGITUDE));
        }catch (Exception e)
        {
            Log.e("EditProfile", e+"");
        }

        if(!tags.isEmpty() && tags!=null) {

            String[] tag = tags.split(",");
            String[] tagId = tagIds.split(",");


            for (int i = 0; i < tag.length; i++) {

                Tagsuggestion tagsuggestion = new Tagsuggestion();
                tagsuggestion.setText(tag[i]);
                try {
                    if (!tagId[i].equals("null")) {
                        tagsuggestion.setId(Integer.valueOf(tagId[i]));
                    }
                }catch (Exception e){
                    Log.e("UserPostsFrag", e+"");
                }
                mylist.add(tagsuggestion);
            }

            adapter = new FoodAdapter(context, mylist);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }

    public boolean validate() {

        boolean valid = true;

        String username = musername.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String name = mName.getText().toString().trim();
        String city = mCity.getText().toString().trim();
        String gender = mGender.getText().toString().trim();
        String dob = mDob.getText().toString().trim();
        String userdescription = userDescription.getText().toString().trim();


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "enter a valid email address", Toast.LENGTH_SHORT).show();
//            mEmail.setError("enter a valid email address");
            valid = false;
        }
// else {
//            mEmail.setError(null);
//        }

        else if (username.isEmpty()) {
            Toast.makeText(context, "enter a valid username", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else if (name.isEmpty()) {
            Toast.makeText(context, "enter a valid name", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else if (city.isEmpty()) {
            Toast.makeText(context, "enter a valid city", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else if(gender.isEmpty()) {
            Toast.makeText(context, "enter a valid gender", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else if (dob.isEmpty())  {
            Toast.makeText(context, "enter a valid dob", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else if (userdescription.isEmpty()) {
            Toast.makeText(context, "enter a valid userdescription", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else{
//            Toast.makeText(context, "Complete Signup Successfully", Toast.LENGTH_SHORT).show();

        }
//            musername.setError("User name cannot be empty");
//            valid = false;
//        } else {
//            musername.setError(null);
//        }
//
//        if (name.isEmpty()) {
//            mName.setError("Name cannot be empty");
//            valid = false;
//        } else {
//            mName.setError(null);
//        }
//
//        if (city.isEmpty()) {
//            mCity.setError("City cannot be empty");
//            valid = false;
//        } else {
//            mCity.setError(null);
//        }
//
//        if (gender.isEmpty()) {
//            mGender.setError("Please choose gender");
//            valid = false;
//        } else {
//            mGender.setError(null);
//        }
//
//        if (mylist.isEmpty()) {
//            autoComplete.setError("Please add food preferences");
//            valid = false;
//        } else {
//            autoComplete.setError(null);
//        }
//
//        if (dob.isEmpty()) {
//            mDob.setError("Please choose date of birth");
//            valid = false;
//        } else {
//            mDob.setError(null);
//        }

        return valid;
    }




    private void checKUsernameExists(String s) {
        showProgressDialog();
        Call<CommonResponse> call = apiServices.checkUserName(s);
        call.enqueue(new Callback<CommonResponse>() {

            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                int responsecode = response.code();
                CommonResponse body = response.body();
                if (responsecode == 200) {
                    if (body.getUsernameexists()) {
                        musername.setError("User name already exists");
                        hideProgressDialog();
                    } else {
                        completeSignUp();
                    }

                }else if (responsecode == 400) {
                    hideProgressDialog();
                }else if(responsecode==401){
                    hideProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(context, "Server error!", Toast.LENGTH_SHORT).show();
                hideProgressDialog();

            }
        });
    }

    /*
      sends data to server
     */
    private void completeSignUp() {

        final String username = musername.getText().toString().trim();
        final String name = mName.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        final String city = mCity.getText().toString().trim();
        final String gender = mGender.getText().toString().trim();
        final String dob = mDob.getText().toString().trim();
        final String userdescription = userDescription.getText().toString().trim();
        Log.e("citylocation",""+citylocation.getLattitude()+citylocation.getLongitude()+citylocation.getAddress()+citylocation.getId());
        //converting gender to numbers
        // gender: 0 -> male, 1 -> femal
        int genderNumber = 0;

        if (gender.equals("Male")) {
            genderNumber = 0;
        } else if (gender.equals("Female")) {
            genderNumber = 1;
        }

        //Tags preparation

        List<Tag> tags = new ArrayList<>();

        for (int i = 0; i < mylist.size(); i++) {
            Tagsuggestion tag = mylist.get(i);

            Tag tag1 = new Tag();

            if (tag.getId() != null) {
                tag1.setId(tag.getId());
            }
            tag1.setText(tag.getText());
            tag1.setDisplayorder(i + 1);

            tags.add(tag1);
        }

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String uid = "";
        if (firebaseUser != null) {
            uid = mAuth.getCurrentUser().getUid();
        }

        final CompleteSignUpData completeSignUpData = new CompleteSignUpData();
        completeSignUpData.setUsername(username);
        completeSignUpData.setName(name);
        completeSignUpData.setEmail(email);
        completeSignUpData.setCitylocation(citylocation);
        completeSignUpData.setGender(genderNumber);
        completeSignUpData.setDateofbirth(dob);
        completeSignUpData.setDescription(userdescription);
        completeSignUpData.setIsbusinessuser(false);
        completeSignUpData.setTags(tags);

        completeSignUpData.setClientapp("android");
        completeSignUpData.setClientip(Utils.getLocalIpAddress(context));
        String tagsString = "";
        String tagsIds = "";
        for (Tag tag:tags){
            tagsString =tagsString+ tag.getText()+",";
            tagsIds =tagsIds+ tag.getId()+",";
        }

        PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_TAGS, tagsString);
        PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_TAG_IDS, tagsIds);
        showProgressDialog();
        Call<SignupResponse> call = apiServices.editprofile(USERId,completeSignUpData);
        call.enqueue(new Callback<SignupResponse>() {

            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                int responsecode = response.code();
                SignupResponse body = response.body();
                if (responsecode == 201) {
//id=102

//                        context.getApplicationContext().this.finish();
                    PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_ID, String.valueOf(body.getId()));
                    PreferencesHelper.setPreferenceBoolean(getActivity(), PreferencesHelper.PREFERENCE_LOGGED_IN, true);
                    PreferencesHelper.setPreference(getActivity(),PreferencesHelper.PREFERENCE_CITY_ID,cityid);
                    PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_NAME, body.getName()!=null?body.getName():"");
                    PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_USER_NAME,  body.getUsername()!=null?body.getUsername():"");
                    PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_EMAIL,  body.getEmail()!=null?body.getEmail():"");
                    PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_CITY,  body.getCitylocation().getName()!=null?body.getCitylocation().getName():"");
                    PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_DOB, body.getDateofbirth()!=null?body.getDateofbirth():"");
                    PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_USER_DESCRIPTION, body.getDescription()!=null?body.getDescription():"");
                    PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_LATITUDE, String.valueOf(body.getCitylocation().getLattitude()!=null?body.getCitylocation().getLattitude():""));
                    PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_LONGITUDE, String.valueOf(body.getCitylocation().getLongitude()!=null?body.getCitylocation().getLongitude():""));


                    if(body.getGender()!=null){
                        if(body.getGender()==0)
                        {
                            PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_GENDER, "Male");
                        }
                        else {
                            PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_GENDER,"FeMale" );
                        }
                    }



                    hideProgressDialog();
                    Toast.makeText(context, "Profile edited successfully ", Toast.LENGTH_SHORT).show();
                    gotoSeetings();

//                    uploadImage(selectedImagePath,String.valueOf(body.getId()));

                } else if (responsecode == 400) {
                    try {
                        String s = response.errorBody().string();
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    hideProgressDialog();
                }else if(responsecode==401){
                    hideProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(context, "Server error!", Toast.LENGTH_SHORT).show();
                hideProgressDialog();

            }
        });
    }

    private void gotoSeetings() {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_righ);
        transaction.replace(R.id.frame_layout, SettingsFragment.newInstance());
        transaction.addToBackStack(null).commitAllowingStateLoss();
    }

    /*
    * This method is fetching the absolute path of the image file
    * if you want to upload other kind of files like .pdf, .docx
    * you need to make changes on this method only
    * Rest part will be the same
    * */
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


    /**
     * This function will upload the image to server
     * @param contentURI ->absolute file path
     */
    public void uploadImage(String contentURI,String userId) {

        File file = null;
        try {
            file = new File(contentURI);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "You didn't selected any image", Toast.LENGTH_SHORT).show();
            hideProgressDialog();
            gotoUserProfile();
        }

        if (file != null && file.exists()) {

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);


            Call<CommonResponse> call = apiServices.uploadImage(userId, body);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    showProgressDialog();
                    int responsecode = response.code();
                    CommonResponse commonResponse = response.body();
                    if (responsecode == 201) {
                        showProgressDialog();
                        PreferencesHelper.setPreference(getApplicationContext(), PreferencesHelper.PREFERENCE_PROFILE_PIC, commonResponse.getImageurl());
                        Toast.makeText(context, "Image Uploaded!", Toast.LENGTH_LONG).show();

                        hideProgressDialog();
//                        gotoUserProfile();
                    }
                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {
                    Toast.makeText(context, "Server error!", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                }
            });
        } else {
//            Toast.makeText(context, "file not exists!", Toast.LENGTH_SHORT).show();
            hideProgressDialog();
            gotoUserProfile();
        }

    }

    private void gotoUserProfile() {
        FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
        transaction.replace(R.id.frame_layout, SettingsFragment.newInstance());
        transaction.addToBackStack(null).commit();
    }

    private void setupTagAutocomplete() {

        //setting horizontal orientation for tag layout
        adapter = new FoodAdapter(context, mylist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        autoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tagsAutocompleteAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tagsAutocompleteAdapter = new TagsAutocompleteAdapter(context, apiServices);
        autoComplete.setThreshold(1);
        autoComplete.setAdapter(tagsAutocompleteAdapter);
        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ItemModelList = tagsAutocompleteAdapter.getItem(position).getText();
                if (!autoComplete.getText().toString().isEmpty() && ItemModelList != null && !autoComplete.getText().toString().equals(null)) {
                    if (!ItemModelList.isEmpty()) {

                        if (mylist.contains(ItemModelList)) {
                            Toast.makeText(getActivity(), "already added", Toast.LENGTH_SHORT).show();
                        } else {
                            mylist.add(tagsAutocompleteAdapter.getItem(position));
                            adapter = new FoodAdapter(context, mylist);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            autoComplete.setText(null);
                        }

                    } else {
                        Toast.makeText(getActivity(), "please choose the food Preference", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(getActivity(), "please choose the food Preference", Toast.LENGTH_SHORT).show();
                }


            }
        });
        AddTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String autoTxt = autoComplete.getText().toString();
                if (!autoComplete.getText().toString().isEmpty() && !autoComplete.getText().toString().equals(null)) {
                    if (!autoTxt.isEmpty()) {

                        if (mylist.contains(autoTxt)) {
                            Toast.makeText(getActivity(), "already added", Toast.LENGTH_SHORT).show();
                        } else {
                            Tagsuggestion tagsuggestion = new Tagsuggestion();
                            tagsuggestion.setText(autoTxt);
                            mylist.add(tagsuggestion);
                            adapter = new FoodAdapter(context, mylist);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            autoComplete.setText(null);
                        }

                    } else {
                        Toast.makeText(getActivity(), "please choose the food Preference", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(getActivity(), "please choose the food Preference", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void showAlert() {

        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View deleteDialogView = factory.inflate(R.layout.gender_popup, null);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(deleteDialogView);
        final TextView female=(TextView)deleteDialogView.findViewById(R.id.gender_female);
        final TextView male=(TextView)deleteDialogView.findViewById(R.id.gender_male);
        TextView cancel=(TextView)deleteDialogView.findViewById(R.id.gender_cancel);
        LinearLayout GenderLinLay=(LinearLayout)deleteDialogView.findViewById(R.id.genlin_lay);
//        Button ok = deleteDialogView.findViewById(R.id.ok_button);

        final AlertDialog alertDialog1 = alertDialog.create();
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenderStr="Female";
                mGender.setText(GenderStr);
                alertDialog1.dismiss();
            }
        });
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenderStr="Male";
                mGender.setText(GenderStr);
                alertDialog1.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            alertDialog1.dismiss();
            }
        });
        GenderLinLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });

//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });


        alertDialog1.setCanceledOnTouchOutside(false);
        try {
            alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        alertDialog1.show();
//        alertDialog1.getWindow().setLayout((int)Utils.convertDpToPixel(290,
//                getActivity()),(int)Utils.convertDpToPixel(290,getActivity()));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog1.getWindow().getAttributes());
        lp.gravity = Gravity.BOTTOM;
        lp.windowAnimations = R.style.shareDialogAnimation;
        alertDialog1.getWindow().setAttributes(lp);
    }




    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = mAdapter.getItem(position);

            if (item != null) {
                placeId = item.getPlaceId();
                placeAddress = item.getSecondaryText(null).toString();
                if (!item.getPlaceTypes().isEmpty()) {
                    placeType = "";
                    for (int i : item.getPlaceTypes()) {
                        placeType = placeType + i + ",";
                    }
                }
                getLatLong(placeId);

            }
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);

            Utils.hideKeyboard(getActivity());
        }
    };

    private void getLatLong(final String placeId) {

        mGeoDataClient.getPlaceById(placeId).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                if (task.isSuccessful()) {
                    final String city = mCity.getText().toString().trim();
                    PlaceBufferResponse places = task.getResult();
                    Place myPlace = places.get(0);
                    Log.i(TAG, "Place found: " + myPlace.getName());
                    latitude = myPlace.getLatLng().latitude;
                    longitude = myPlace.getLatLng().longitude;
                    citylocation=new Citylocation();
                    citylocation.setIsgoogleplace(true);
                    citylocation.setName(city);
                    citylocation.setGoogleplaceid(placeId);
                    citylocation.setAddress(placeAddress);
                    citylocation.setGoogleplacetype(placeType);
                    citylocation.setLattitude(latitude);
                    citylocation.setLongitude(longitude);
                    places.release();
                } else {
                    Log.e(TAG, "Place not found.");
                }
            }
        });
    }




    private void showBottomSheet(LayoutInflater inflater) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomSheetView = inflater.inflate(R.layout.dialo_camera_bottomsheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        Camera = bottomSheetView.findViewById(R.id.camera_title);
        Gallery = bottomSheetView.findViewById(R.id.gallery_title);
        GalleryIcon= bottomSheetView.findViewById(R.id.gallery_icon);
        CameraIcon= bottomSheetView.findViewById(R.id.camera_image);
        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetDialog.dismiss();

                if (hasPermissions()) {
                    captureImage();
                } else {
                    EasyPermissions.requestPermissions(EditProfileFragment.this, "Permissions required", PERMISSIONS_REQUEST_CAMERA, CAMERA);
                }

            }
        });

        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (hasPermissions()) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RC_PICK_IMAGE);
                } else {
                    EasyPermissions.requestPermissions(EditProfileFragment.this, "Permissions required", PERMISSIONS_REQUEST_GALLERY, CAMERA);
                }
                bottomSheetDialog.dismiss();

            }
        });
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        switch (requestCode){

            case PERMISSIONS_REQUEST_GALLERY:
                if(perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)&&perms.contains(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RC_PICK_IMAGE);
                }
                break;

            case PERMISSIONS_REQUEST_CAMERA:
                if(perms.contains(Manifest.permission.CAMERA)) {
                    captureImage();
                }
                break;

        }

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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, EditProfileFragment.this);
    }


    private boolean hasPermissions() {
        return EasyPermissions.hasPermissions(getActivity(), CAMERA);
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, RC_CAPTURE_IMAGE);
    }

    public Uri getOutputMediaFileUri(int type) {
        return FileProvider.getUriForFile(getApplicationContext(),
                BuildConfig.APPLICATION_ID + ".provider",
                getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {

        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Constants.IMAGE_DIRECTORY_NAME);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + Constants.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + mediaFile.getAbsolutePath();
        return mediaFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == RC_PICK_IMAGE) {
                showProgressDialog();
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURI);
                        viewImage.setImageBitmap(bitmap);
                        selectedImagePath=getRealPathFromURI(contentURI);
                        uploadImage(selectedImagePath,userId);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (requestCode == RC_CAPTURE_IMAGE) {
                showProgressDialog();
                // Show the thumbnail on ImageView
                Uri imageUri = Uri.parse(mCurrentPhotoPath);
                File file = new File(imageUri.getPath());
                try {
                    InputStream ims = new FileInputStream(file);
                    viewImage.setImageBitmap(BitmapFactory.decodeStream(ims));
                } catch (FileNotFoundException e) {
                    return;
                }

                // ScanFile so it will be appeared on Gallery
                MediaScannerConnection.scanFile(getApplicationContext(),
                        new String[]{imageUri.getPath()}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {

                                Log.e("Dsds",path);
                                selectedImagePath = path;
                                uploadImage(selectedImagePath,userId);
                            }
                        });




            }

        } else {
            super.onActivityResult(requestCode, resultCode,
                    data);
        }
    }




    private void setupFocusListeners(View v) {
        final TextView usernameLabel = v.findViewById(R.id.text_user_name_label);
        final TextView nameLabel = v.findViewById(R.id.text_name_label);
        final TextView emailLabel = v.findViewById(R.id.text_email_label);
        final TextView citylLabel = v.findViewById(R.id.text_city_label);
        final TextView genderLabel = v.findViewById(R.id.text_gender_label);
        final TextView foodlLabel = v.findViewById(R.id.text_food_preferences_label);
        final TextView userdescriptionLabel = v.findViewById(R.id.text_user_description_label);
        final View usernameview=v.findViewById(R.id.username_view);
        final View nameview=v.findViewById(R.id.name_view);
        final View emailview=v.findViewById(R.id.email_view);
        final View cityview=v.findViewById(R.id.city_view);
        final View genderview=v.findViewById(R.id.gender_view);
        final View dobview=v.findViewById(R.id.dob_view);
        final View foodview=v.findViewById(R.id.food_view);
        final View descview=v.findViewById(R.id.desc_view);

        musername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    usernameLabel.setTextColor(getResources().getColor(R.color.weblink_color));
                    usernameview.setBackgroundColor(getResources().getColor(R.color.weblink_color));
                }
                else{
                    usernameLabel.setTextColor(getResources().getColor(R.color.email_color));
                    usernameview.setBackgroundColor(getResources().getColor(R.color.Edittxt_lineclr));
                }
            }
        });

        mName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    nameLabel.setTextColor(getResources().getColor(R.color.weblink_color));
                    nameview.setBackgroundColor(getResources().getColor(R.color.weblink_color));
                }
                else{
                    nameLabel.setTextColor(getResources().getColor(R.color.email_color));
                    nameview.setBackgroundColor(getResources().getColor(R.color.Edittxt_lineclr));
                }
            }
        });


        mEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    emailLabel.setTextColor(getResources().getColor(R.color.weblink_color));
                    emailview.setBackgroundColor(getResources().getColor(R.color.weblink_color));
                }
                else{
                    emailLabel.setTextColor(getResources().getColor(R.color.email_color));
                    emailview.setBackgroundColor(getResources().getColor(R.color.Edittxt_lineclr));
                }
            }
        });


        mCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    citylLabel.setTextColor(getResources().getColor(R.color.weblink_color));
                    cityview.setBackgroundColor(getResources().getColor(R.color.weblink_color));
                }
                else{
                    citylLabel.setTextColor(getResources().getColor(R.color.email_color));
                    cityview.setBackgroundColor(getResources().getColor(R.color.Edittxt_lineclr));
                }
            }
        });

        mGender.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    genderLabel.setTextColor(getResources().getColor(R.color.weblink_color));
                    genderview.setBackgroundColor(getResources().getColor(R.color.weblink_color));
                }
                else{
                    genderLabel.setTextColor(getResources().getColor(R.color.email_color));
                    genderview.setBackgroundColor(getResources().getColor(R.color.Edittxt_lineclr));
                }
            }
        });

        autoComplete.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    foodlLabel.setTextColor(getResources().getColor(R.color.weblink_color));
                    foodview.setBackgroundColor(getResources().getColor(R.color.weblink_color));
                }
                else{
                    foodlLabel.setTextColor(getResources().getColor(R.color.email_color));
                    foodview.setBackgroundColor(getResources().getColor(R.color.Edittxt_lineclr));
                }
            }
        });

        userDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    userdescriptionLabel.setTextColor(getResources().getColor(R.color.weblink_color));
                    descview.setBackgroundColor(getResources().getColor(R.color.weblink_color));
                }
                else{
                    userdescriptionLabel.setTextColor(getResources().getColor(R.color.email_color));
                    descview.setBackgroundColor(getResources().getColor(R.color.Edittxt_lineclr));
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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

}
