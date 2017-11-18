package com.numnu.android.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.numnu.android.R;
import com.numnu.android.adapter.FoodAdapter;
import com.numnu.android.utils.PreferencesHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.GraphRequest.TAG;

/**
 * Created by lenovo on 11/18/2017.
 */

public class CompleteSignupFragment extends Fragment{

    Context context;
    EditText mEmail,mName,mCity,mGender,mDob,mFoodPreferences;
    Button mCompleteSignUp;
    RadioGroup mRadioGroup;
    RadioButton mRadioMale,mRadioFemale;
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
    ArrayList<String> mylist=new ArrayList<>();

    private RecyclerView myRecyclerView;
    private LinearLayoutManager linearLayoutManager;


    String[] arr = {"Biryani", "Mutton Biryani","Mutton Ticka","Mutton 65","Mutton Curry","Mutton Fry","Chicken Curry","Chicken 65","Chicken Fry"};
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.activity_complete_signup, container, false);
        TextView toolbarTitle = v.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Complete Profile");
        recyclerView=(RecyclerView) v.findViewById(R.id.food_recyclerview);
        FoodLinearLay=(LinearLayout)  v.findViewById(R.id.food_layout);

//        adapter.setClickListener(this);
        autoComplete =(AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextView1);
        AddTxt=(TextView) v.findViewById(R.id.add_txt);


        final ArrayAdapter<String> vairam = new ArrayAdapter<String>(getActivity(), R.layout.auto_dialog,R.id.lbl_name, arr);
        autoComplete.setThreshold(1);
        autoComplete.setAdapter(vairam);
        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ItemModelList =vairam.getItem(position).toString() ;


            }
        });
        AddTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!autoComplete.getText().toString().isEmpty()&&ItemModelList!=null) {
                    if (!ItemModelList.isEmpty()) {

                        if (mylist.contains(ItemModelList)) {
                            Toast.makeText(getActivity(), "already added", Toast.LENGTH_SHORT).show();
                        } else {
                            mylist.add(ItemModelList);
                            adapter = new FoodAdapter(context, mylist);
                            recyclerView.setAdapter(adapter);
                        }

                    } else {
                        Toast.makeText(getActivity(), "please choose the food Preference", Toast.LENGTH_SHORT).show();

                    }

                }else {
                    Toast.makeText(getActivity(), "please choose the food Preference", Toast.LENGTH_SHORT).show();
                }

            }
        });


        adapter = new FoodAdapter(context,mylist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        mEmail = v.findViewById(R.id.et_signup_email);
        mName = v. findViewById(R.id.et_signup_name);
        mCity =  v.findViewById(R.id.et_signup_city);
//        mGender = findViewById(R.id.et_signup_gender);
        mRadioGroup =  v.findViewById(R.id.radio_group);
        mRadioMale =  v.findViewById(R.id.male_radio);
        mRadioFemale =  v.findViewById(R.id.female_radio);
        mDob =  v.findViewById(R.id.et_signup_dob);
//        mFoodPreferences = findViewById(R.id.et_signup_food_preferences);
        mCompleteSignUp =  v.findViewById(R.id.button_complete_signup);
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

                } else  if (checkedId == R.id.female_radio) {
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
                },newCalendar.get(Calendar.YEAR),newCalendar.get(Calendar.MONTH),newCalendar.get(Calendar.DAY_OF_MONTH));
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
                }else {
                    if (email.matches(emailPattern)&&(!(email.equals("") && name.equals("") && city.equals("") && dob.equals("") && ItemModelList.equals("")))) {
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
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });




        return v;
    }
}
