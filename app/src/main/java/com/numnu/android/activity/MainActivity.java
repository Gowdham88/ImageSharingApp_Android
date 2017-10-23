package com.motomecha.com.motocube;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.motomecha.com.motocube.Adapter.Shopping3Adapter;
import com.motomecha.com.motocube.Api.FilterApi;
import com.motomecha.com.motocube.Api.Shopping3Api;
import com.motomecha.com.motocube.helper.PrefManager;
import com.motomecha.com.motocube.modelclass.Contact;
import com.motomecha.com.motocube.modelclass.ContactList;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Shopping4 extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{

    private String TAG = Shopping3.class.getSimpleName();
    private  boolean isSpinnerTouched = false;
    Retrofit retrofit = null;
    GridView gridview4;
    ProgressDialog pDialog;
    //    EditText s4;
    Spinner s4;
    ArrayList<Contact> contactLists = new ArrayList<Contact>();
    ArrayList<Contact> spinnerlist = new ArrayList<Contact>();
    ArrayList<HashMap<String, String>> contactList;
    Shopping3Adapter adapter;
    ArrayList<String> countries = new ArrayList<String>();
    ArrayAdapter adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping4);
        contactList = new ArrayList<>();
        gridview4 = (GridView) findViewById(R.id.gridview4);


        s4 = (Spinner) findViewById(R.id.search4);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://motomecha.com/MM_vendor/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, String> params2 = new HashMap<String, String>();
        FilterApi api2 = retrofit.create(FilterApi.class);
        Call<ContactList> call1 = api2.getTopRatedMovies(params2);
        call1.enqueue(new Callback<ContactList>() {
            @Override
            public void onResponse(Call<ContactList> call, Response<ContactList> response) {
                spinnerlist = response.body().getContacts();
                Log.e("xsxs", "" + spinnerlist.size());
                for(int i =0; i < spinnerlist.size(); i++){

                    Log.e("xsxs", "" + spinnerlist.get(i).getFilter());
                    countries.add(spinnerlist.get(i).getFilter());

                }
                adapter1 = new ArrayAdapter(Shopping4.this, android.R.layout.simple_spinner_item, countries);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                s4.setAdapter(adapter1);

            }

            @Override
            public void onFailure(Call<ContactList> call, Throwable t) {

            }
        });



        retrofit = new Retrofit.Builder()
                .baseUrl("http://motomecha.com/MM_vendor/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, String> params = new HashMap<String, String>();
        Shopping3Api api = retrofit.create(Shopping3Api.class);
        Call<ContactList> call = api.getTopRatedMovies(params);
        call.enqueue(new Callback<ContactList>() {
            @Override
            public void onResponse(Call<ContactList> call, Response<ContactList> response) {
                contactLists = response.body().getContacts();
                Log.e("xsxs", "" + contactLists.size());
                adapter = new Shopping3Adapter(getApplicationContext(), contactLists);
                gridview4.setAdapter(adapter);

//                for(int i =0; i < contactLists.size(); i++){
//
//                    Log.e("xsxs", "" + contactLists.get(i).getProductName());
//                    countries.add(contactLists.get(i).getProductName());
//
//                }
//                adapter1 = new ArrayAdapter(Shopping4.this, android.R.layout.simple_spinner_item, countries);
//                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                s4.setAdapter(adapter1);
            }

            @Override
            public void onFailure(Call<ContactList> call, Throwable t) {

            }
        });

        gridview4.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            TextView prod_nme, prod_prc, prod1, prod2, prod3, prod4, prod5, prod6, prod7, prod8, prod9, prod10, mrp;
            ImageView prod_img;
            Button b1, b2;
            String mobile_number = "";
            String product_image = "";
            Spinner quan;
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                final Dialog dialog = new Dialog(Shopping4.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.product_descrip);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                prod_nme = (TextView) dialog.findViewById(R.id.prodnme);
                prod_prc = (TextView) dialog.findViewById(R.id.prodprc);
                prod_img = (ImageView) dialog.findViewById(R.id.imageView3);
                prod1 = (TextView) dialog.findViewById(R.id.proddes);
                prod2 = (TextView) dialog.findViewById(R.id.proddes1);
                prod3 = (TextView) dialog.findViewById(R.id.proddes2);
                prod4 = (TextView) dialog.findViewById(R.id.proddes3);
                prod5 = (TextView) dialog.findViewById(R.id.proddes4);
                prod6 = (TextView) dialog.findViewById(R.id.proddes5);
                prod7 = (TextView) dialog.findViewById(R.id.proddes6);
                prod8 = (TextView) dialog.findViewById(R.id.proddes7);
                prod9 = (TextView) dialog.findViewById(R.id.proddes8);
                prod10 = (TextView) dialog.findViewById(R.id.proddes9);
                mrp = (TextView) dialog.findViewById(R.id.textView8);
                b1 = (Button) dialog.findViewById(R.id.bookshop);
                b2 = (Button) dialog.findViewById(R.id.close);
                prod_nme.setText(contactLists.get(position).getProductName());
                prod_prc.setText(contactLists.get(position).getProductPrice());
                prod1.setText(contactLists.get(position).getProdDescription());
                prod2.setText(contactLists.get(position).getProdDescription1());
                prod3.setText(contactLists.get(position).getProdDescription2());
                prod4.setText(contactLists.get(position).getProdDescription3());
                prod5.setText(contactLists.get(position).getProdDescription4());
                prod6.setText(contactLists.get(position).getProdDescription5());
                prod7.setText(contactLists.get(position).getProdDescription6());
                prod8.setText(contactLists.get(position).getProdDescription7());
                prod9.setText(contactLists.get(position).getProdDescription8());
                prod10.setText(contactLists.get(position).getProdDescription9());
                mrp.setText(contactLists.get(position).getMrpPrice());
                Picasso.with(Shopping4.this).load(contactLists.get(position).getProductImage()).into(prod_img);
                PrefManager pref = new PrefManager(Shopping4.this);
                mobile_number = pref.getMobileNumber();
                product_image = contactLists.get(position).getProductImage();
                quan = (Spinner) dialog.findViewById(R.id.spinner3);
                final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Shopping4.this,
                        R.array.SELECT, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                quan.setAdapter(adapter);
                dialog.show();
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        dialog.dismiss();
                    }
                });

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String product_name = prod_nme.getText().toString();
                        final String product_price = prod_prc.getText().toString();
                        final String quantity = quan.getSelectedItem().toString();

                        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {
                                        Toast.makeText(Shopping4.this,
                                                " Your Item has been added to cart ", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Shopping4.this);
                                        builder.setMessage("Register Failed")
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        ProdDescRequest prodDescRequest = new ProdDescRequest(product_name, product_price, mobile_number, quantity, product_image, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(Shopping4.this);
                        queue.add(prodDescRequest);

                    }
                });


            }


        });
        s4.setOnItemSelectedListener(Shopping4.this);
        s4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinnerTouched = true;
                return false;
            }
        });
    }
//    private void setupSearchView()
//    {
//        s4.setIconifiedByDefault(false);
//        s4.setOnQueryTextListener(this);
//        s4.setSubmitButtonEnabled(true);
//        s4.setQueryHint("Search Here");
//    }
//
//
//    public boolean onQueryTextChange(String newText)
//    {
//
//        if (TextUtils.isEmpty(newText)) {
//            gridview4.clearTextFilter();
//        } else {
//            gridview4.setFilterText(newText);
//        }
//        return true;
//    }
//
//
//    public boolean onQueryTextSubmit(String query)
//    {
//        return false;
//    }


    @Override
    public void onItemSelected(AdapterView a, View v, final int i, long lng) {

        if (!isSpinnerTouched) return;
        pDialog=new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(true);
        pDialog.setIndeterminate(true);
        pDialog.show();
        long delayInMillis = 3000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                pDialog.dismiss();
            }
        }, delayInMillis);

        String text = s4.getSelectedItem().toString();
        ArrayList<Contact> filtercontactLists = new ArrayList<>();

        if (contactLists.size() > 1) {

            for(int j=0;j < contactLists.size(); j++) {

                Log.e("jjj", "" + contactLists.get(j).getProdDescription0());
                Log.e("xsjkhkhkhkhxs", "" + text);

                if ("Pulsar" == text) {

                    filtercontactLists.add(contactLists.get(j));
                    Log.e("xsjkhkhkhkhxs", "" + contactLists.get(j));

                }
            }

            Log.e("xsjkhkhkhkhxs", "" + filtercontactLists.size());
            adapter = new Shopping3Adapter(getApplicationContext(), filtercontactLists);
            gridview4.setAdapter(adapter);

        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parentView)
    {


    }

}