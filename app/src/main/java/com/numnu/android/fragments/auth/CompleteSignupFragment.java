package com.numnu.android.fragments.auth;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.numnu.android.R;
import com.numnu.android.activity.HomeActivity;
import com.numnu.android.activity.OnboardingActivity;
import com.numnu.android.adapter.FoodAdapter;
import com.numnu.android.adapter.PlaceAutocompleteAdapter;
import com.numnu.android.adapter.TagsAutocompleteAdapter;
import com.numnu.android.fragments.auth.LoginFragment;
import com.numnu.android.fragments.home.SettingsFragment;
import com.numnu.android.fragments.home.UserPostsFragment;
import com.numnu.android.fragments.search.EventsFragmentwithToolbar;
import com.numnu.android.fragments.search.PostsFragment;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.response.CommonResponse;
import com.numnu.android.network.response.TagsResponse;
import com.numnu.android.network.response.Tagsuggestion;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.PreferencesHelper;
import com.numnu.android.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.numnu.android.utils.Utils.hideKeyboard;

/**
 * Created by lenovo on 11/18/2017.
 */

public class CompleteSignupFragment extends Fragment implements EasyPermissions.PermissionCallbacks{

    Context context;
    EditText musername,mEmail, mName,mDob, userDescription;
    AutoCompleteTextView mCity;
    Button mCompleteSignUp;
    RadioGroup mRadioGroup;
    RadioButton mRadioMale, mRadioFemale;
    private String mGenderValue = "";
    private DatePickerDialog.OnDateSetListener datePickerDialog;
    private SimpleDateFormat dateFormat;
    RecyclerView recyclerView;
    FoodAdapter adapter;
    LinearLayout FoodLinearLay;
    TextView AddTxt;
    String AutocomStr;
    AutoCompleteTextView autoComplete;
    String ItemModelList;
    String mainAutotxt;
    ArrayList<Tagsuggestion> mylist = new ArrayList<>();
    ImageView viewImage, EditBtn;
    TextView Gallery,mGender;
    TextView Camera;
    ImageView GalleryIcon,GenderDropimg;
    ImageView CameraIcon;
    private String selectedImagePath = "";
    final private int PICK_IMAGE = 1;
    final private int CAPTURE_IMAGE = 2;
    private String imgPath;
    RelativeLayout EditReLay;
    String GenderStr;
    LinearLayout EditLinearLay,Linearlay;
    ScrollView nestedScrollView;

    private RecyclerView myRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private static final int CAMERA_REQUEST = 1888;
    private static final String[] CAMERA= {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int RC_LOCATION_PER = 1;
    String[] arr = {"Biryani", "Mutton Biryani", "Mutton Ticka", "Mutton 65", "Mutton Curry", "Mutton Fry", "Chicken Curry", "Chicken 65", "Chicken Fry"};
    String AutocompleteStr;
    /**
     * GeoDataClient wraps our service connection to Google Play services and provides access
     * to the Google Places API for Android.
     */
    protected GeoDataClient mGeoDataClient;

    private PlaceAutocompleteAdapter mAdapter;

    ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
    private TagsAutocompleteAdapter tagsAutocompleteAdapter;

    public static CompleteSignupFragment newInstance() {
        CompleteSignupFragment fragment = new CompleteSignupFragment();
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
        View v = inflater.inflate(R.layout.
                activity_complete_signup, container, false);
        TextView toolbarTitle = v.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Complete SignUp");
        ImageView Toolbarbackicon=v.findViewById(R.id.toolbar_back);
        Toolbarbackicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
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

        

        musername=v.findViewById(R.id.et_cmpltsignup_username);
        mEmail = v.findViewById(R.id.et_signup_email);
        mName = v.findViewById(R.id.et_signup_name);
        mCity = v.findViewById(R.id.et_signup_city);
        mGender=v.findViewById(R.id.ed_gender);
        userDescription = v.findViewById(R.id.et_user_description);

//        mRadioGroup = v.findViewById(R.id.radio_group);
//        mRadioMale = v.findViewById(R.id.male_radio);
//        mRadioFemale = v.findViewById(R.id.female_radio);
        mDob = v.findViewById(R.id.et_signup_dob);

        mCompleteSignUp = v.findViewById(R.id.button_complete_signup);
        mDob.setInputType(InputType.TYPE_NULL);
        mDob.requestFocus();

        setupFocusListeners(v);

        // Construct a GeoDataClient for the Google Places API for Android.
        mGeoDataClient = Places.getGeoDataClient(getActivity(), null);
        mCity.setOnItemClickListener(mAutocompleteClickListener);
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
        // Set up the adapter that will retrieve suggestions from the Places Geo Data Client.
        mAdapter = new PlaceAutocompleteAdapter(getActivity(), mGeoDataClient, Constants.BOUNDS_GREATER_SYDNEY, autocompleteFilter);
        mCity.setAdapter(mAdapter);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

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


        mCompleteSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = musername.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String name = mName.getText().toString().trim();
                String city = mCity.getText().toString().trim();
                String gender = mGender.getText().toString().trim();
                String dob = mDob.getText().toString().trim();
//                String foodPreferences = mFoodPreferences.getText().toString();

//                gender: 0 -> male, 1 -> female



                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (email.equals("")) {
                    Toast.makeText(
                            getActivity(), "Email is mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    if (email.matches(emailPattern) && (!(email.equals("") && username.equals("") && name.equals("") && city.equals("") && gender.equals(null) && dob.equals("") && ItemModelList.equals("")))) {
//                        Intent intent = new Intent(getActivity(), HomeActivity.class);
//                        intent.putExtra("completesignup", "showprofilefragment");
//                        startActivity(intent);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                        transaction.replace(R.id.frame_layout, UserPostsFragment.newInstance());
                        transaction.addToBackStack(null).commit();

//                        context.getApplicationContext().this.finish();
                        PreferencesHelper.setPreferenceBoolean(getActivity(), PreferencesHelper.PREFERENCE_LOGGED_IN, true);
                        PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_NAME, name);
                        PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_USER_NAME, email);
                        PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_CITY, city);
                        PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_DOB, dob);
                        if (!(gender.equals(""))) {
                            PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_GENDER, gender);
                        }

                        PreferencesHelper.setPreference(getActivity(), PreferencesHelper.PREFERENCE_DOB, dob);
                    } else {
                        Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });


        return v;
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

                Toast.makeText(context, "after text changed", Toast.LENGTH_SHORT).show();
            }
        });

         tagsAutocompleteAdapter = new TagsAutocompleteAdapter(context, apiServices);
        autoComplete.setThreshold(1);
        autoComplete.setAdapter(tagsAutocompleteAdapter);
        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ItemModelList = tagsAutocompleteAdapter.getItem(position).getText();
                if ( !autoComplete.getText().toString().isEmpty()&& ItemModelList != null && !autoComplete.getText().toString().equals(null)) {
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

        // TODO: 1/12/17  create tags and and add to the list,clarify with CZ team.
//        AddTxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String autoTxt=autoComplete.getText().toString();
//                if ( !autoComplete.getText().toString().isEmpty() && !autoComplete.getText().toString().equals(null)) {
//                    if (!autoTxt.isEmpty()) {
//
//                        if (mylist.contains(autoTxt)) {
//                            Toast.makeText(getActivity(), "already added", Toast.LENGTH_SHORT).show();
//                        } else {
//                            mylist.add(autoTxt);
//                            adapter = new FoodAdapter(context, mylist);
//                            recyclerView.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//                            autoComplete.setText(null);
//                        }
//
//                    } else {
//                        Toast.makeText(getActivity(), "please choose the food Preference", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                } else {
//                    Toast.makeText(getActivity(), "please choose the food Preference", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
    }

    private void getSuggestions(String s) {

        Call<TagsResponse> call = apiServices.getTags(s);
        call.enqueue(new Callback<TagsResponse>() {

            @Override
            public void onResponse(Call<TagsResponse> call, Response<TagsResponse> response) {
                int responsecode = response.code();
                TagsResponse tagsResponse = response.body();
                if (responsecode == 200) {

                    Toast.makeText(context, "Tags loaded!", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<TagsResponse> call, Throwable t) {
                Toast.makeText(context, "Server error!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void uploadImage()
    {
        String userId = PreferencesHelper.getPreference(getApplicationContext(), PreferencesHelper.PREFERENCE_ID);
        File file=null;
        try{
             file = new File(imgPath);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"You didn't selected any image",Toast.LENGTH_SHORT).show();
        }

        if (file != null && file.exists()) {

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);


            Call<CommonResponse> call = apiServices.uploadImage(userId, body);
            call.enqueue(new Callback<CommonResponse>() {

                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    int responsecode = response.code();
                    CommonResponse commonResponse = response.body();
                    if (responsecode == 200) {

                        PreferencesHelper.setPreference(getApplicationContext(),PreferencesHelper.PREFERENCE_PROFILE_PIC,commonResponse.getImageurl());
                        Toast.makeText(context, "Image Uploaded!", Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {
                    Toast.makeText(context, "Server error!", Toast.LENGTH_SHORT).show();

                }
            });
        }else {
            Toast.makeText(context, "file not exists!", Toast.LENGTH_SHORT).show();
        }

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
        alertDialog1.getWindow().setAttributes(lp);
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
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);

            Utils.hideKeyboard(getActivity());
        }
    };




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



    private boolean hasLocationPermission() {
        return EasyPermissions.hasPermissions(getActivity(), CAMERA);
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
                if (hasLocationPermission()) {
                    // Have permissions, do the thing!
                    Intent cameraIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    bottomSheetDialog.dismiss();
                } else {
//
                    EasyPermissions.requestPermissions(
                            getActivity(),
                            getString(R.string.rationale_location),
                            RC_LOCATION_PER,
                            CAMERA);
                }



            }
        });

        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, ""),
                        PICK_IMAGE);
                bottomSheetDialog.dismiss();

            }
        });
    }

    public String getImagePath() {
        return imgPath;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE) {
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURI);
                        viewImage.setImageBitmap(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
//                bottomSheetDialog.dismiss();
                Toast.makeText(getActivity(), "Camera:"+(hasLocationPermission()?"yes":"no"), Toast.LENGTH_SHORT).show();
            }else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                viewImage.setImageBitmap(photo);
            }

        } else {
            super.onActivityResult(requestCode, resultCode,
                    data);
        }
    }



    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of
            // 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE
                    && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }

    private String getAbsolutePath(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getApplicationContext().getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
