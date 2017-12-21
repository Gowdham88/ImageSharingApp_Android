package com.numnu.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.numnu.android.R;
import com.squareup.picasso.Picasso;

public class SliceActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slice);
        ImageView image=(ImageView)findViewById(R.id.popup_image);
        LinearLayout btncancel = (LinearLayout) findViewById(R.id.btncancelcat);
        try{
            Bundle in=getIntent().getExtras();
            String uri= in != null ? in.getString("imagepath") : null;

            Picasso.with(getApplicationContext()).load(uri)
                    .placeholder(R.drawable.background)
                    .into(image);
        }
      catch (NullPointerException e){
            e.printStackTrace();
      }
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });

    }
}
