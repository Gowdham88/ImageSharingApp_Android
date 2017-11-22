package com.numnu.android.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import android.widget.TextView;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.adapter.FoodAdapter;
import com.numnu.android.utils.PreferencesHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission_group.LOCATION;
import static android.content.ContentValues.TAG;
import static android.graphics.BitmapFactory.decodeFile;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.numnu.android.utils.Utils.hideKeyboard;

/**
 * Created by lenovo on 11/18/2017.
 */

public class CompleteSignupFragment extends Fragment implements EasyPermissions.PermissionCallbacks,View.OnKeyListener{

    Context context;
    EditText mEmail, mName, mCity, mGender, mDob, mFoodPreferences;
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
    ArrayList<String> mylist = new ArrayList<>();
    ImageView viewImage, EditBtn;
    TextView Gallery;
    TextView Camera;
    ImageView GalleryIcon;
    ImageView CameraIcon;
    LinearLayout CompleteSignupLay;
    private String selectedImagePath = "";
    final private int PICK_IMAGE = 1;
    final private int CAPTURE_IMAGE = 2;
    private String imgPath;

    private RecyclerView myRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private static final int CAMERA_REQUEST = 1888;
    private static final String[] CAMERA= {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int RC_LOCATION_PER = 1;

    String[] arr = {"Biryani", "Mutton Biryani", "Mutton Ticka", "Mutton 65", "Mutton Curry", "Mutton Fry", "Chicken Curry", "Chicken 65", "Chicken Fry"};
    String AutocompleteStr;

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
        View v = inflater.inflate(R.layout.activity_complete_signup, container, false);
        final TextView toolbarTitle = v.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Complete SignUp");
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
        CompleteSignupLay=(LinearLayout)v.findViewById(R.id.Complete_Linlay);
        CompleteSignupLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(getActivity());
            }
        });

//        adapter.setClickListener(this);
        autoComplete = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextView1);
        AddTxt = (TextView) v.findViewById(R.id.add_txt);


        final ArrayAdapter<String> vairam = new ArrayAdapter<String>(getActivity(), R.layout.auto_dialog, R.id.lbl_name, arr);
        autoComplete.setThreshold(1);
        autoComplete.setAdapter(vairam);
        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ItemModelList = vairam.getItem(position).toString();
                if ( !autoComplete.getText().toString().isEmpty()&& ItemModelList != null && !autoComplete.getText().toString().equals(null)) {
                    if (!ItemModelList.isEmpty()) {

                        if (mylist.contains(ItemModelList)) {
                            Toast.makeText(getActivity(), "already added", Toast.LENGTH_SHORT).show();
                        } else {
                            mylist.add(ItemModelList);
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
                String autoTxt=autoComplete.getText().toString();
                if ( !autoComplete.getText().toString().isEmpty() && !autoComplete.getText().toString().equals(null)) {
                    if (!autoTxt.isEmpty()) {

                        if (mylist.contains(autoTxt)) {
                            Toast.makeText(getActivity(), "already added", Toast.LENGTH_SHORT).show();
                        } else {
                            mylist.add(autoTxt);
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
        adapter = new FoodAdapter(context, mylist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));


        mEmail = v.findViewById(R.id.et_signup_email);
        mName = v.findViewById(R.id.et_signup_name);
        mCity = v.findViewById(R.id.et_signup_city);
//        mGender = findViewById(R.id.et_signup_gender);
        mRadioGroup = v.findViewById(R.id.radio_group);
        mRadioMale = v.findViewById(R.id.male_radio);
        mRadioFemale = v.findViewById(R.id.female_radio);
        mDob = v.findViewById(R.id.et_signup_dob);
//        mFoodPreferences = findViewById(R.id.et_signup_food_preferences);
        mCompleteSignUp = v.findViewById(R.id.button_complete_signup);
//        mFoodPreferences.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FoodLinearLay.setVisibility(View.VISIBLE);
//                hideKeyboardFrom();
//            }
//        });


        mDob.setInputType(InputType.TYPE_NULL);
        mDob.requestFocus();

//        final RecipientEditTextView recipientEditTextView = findViewById(R.id.et_signup_food_preferences);
//        recipientEditTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
//        BaseRecipientAdapter recipientAdapter = new BaseRecipientAdapter(BaseRecipientAdapter.QUERY_TYPE_PHONE,context);
//        recipientAdapter.setShowMobileOnly(false);
//        recipientEditTextView.setAdapter(recipientAdapter);


        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.male_radio) {
                    mGenderValue = "Male";

                } else if (checkedId == R.id.female_radio) {
                    mGenderValue = "Female";
                }
            }
        });

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

                String email = mEmail.getText().toString().trim();
                String name = mName.getText().toString().trim();
                String city = mCity.getText().toString().trim();
                String gender = mGenderValue.trim();
                String dob = mDob.getText().toString().trim();
//                String foodPreferences = mFoodPreferences.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (email.equals("")) {
                    Toast.makeText(context, "Email is mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    if (email.matches(emailPattern) && (!(email.equals("") && name.equals("") && city.equals("") && dob.equals("") && ItemModelList.equals("")))) {
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        intent.putExtra("completesignup", "showprofilefragment");
                        startActivity(intent);

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
                        Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });


        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(this);
    }


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
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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

//    public Uri setImageUri() {
//        // Store image in dcim
//        File file = new File(Environment.getExternalStorageDirectory()
//                + "/DCIM/", "image" + new Date().getTime() + ".png");
//        Uri imgUri = Uri.fromFile(file);
//        this.imgPath = file.getAbsolutePath();
//        return imgUri;
//    }
//
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


    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                getActivity().finish();
                return true;
            }
        }

        return false;
    }
}
