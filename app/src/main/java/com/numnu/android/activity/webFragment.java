package com.numnu.android.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.numnu.android.R;

/**
 * Created by czsm4 on 27/11/17.
 */

public class webFragment  extends MyActivity {
    private WebView webView;
    ImageView toolbarBackicon,BrowseIcon,backButton,forwardButton;
    CustomTabsIntent customTabsIntent;
//    public static webFragment newInstance() {
//        return new webFragment();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_fragment);

        Toolbar toolbar=findViewById(R.id.toolbar);
        backButton =(ImageView) findViewById(R.id.back_word);
        backButton.setVisibility(View.VISIBLE);
        forwardButton =(ImageView) findViewById(R.id.forward);
        forwardButton.setVisibility(View.VISIBLE);
        backButton.setColorFilter(getResources().getColor(R.color.tag_text_color));
        forwardButton.setColorFilter(getResources().getColor(R.color.tag_text_color));

        toolbarBackicon=(ImageView)findViewById(R.id.toolbar_back);
        toolbarBackicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView toolbar_title=(TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText("http://www.totc.ca");


        BrowseIcon=(ImageView)findViewById(R.id.google_img);
        BrowseIcon.setVisibility(View.VISIBLE);
        BrowseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url2 = "https://www.google.com/";
                CustomTabsIntent.Builder builder2 = new CustomTabsIntent.Builder();
                // set toolbar color
                 customTabsIntent = builder2.build();
                customTabsIntent.launchUrl(webFragment.this, Uri.parse(url2));
            }
        });
        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);

//Button Initialization

//Back Button Action
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Going back if canGoBack true
                if(webView.canGoBack()){
                    webView.goBack();

                }
            }
        });
//Forward Button Action
        forwardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Go Forward if canGoForward is frue

                if(webView.canGoForward()){
                    webView.goForward();
                }
            }
        });

        webView.setWebViewClient( new WebViewClient() {



            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished( WebView view, String url ) {

                super.onPageFinished(webView, url);

                //Make Enable or Disable buttons
                if (view.canGoBack()) {
                    backButton.setEnabled(true);
                    backButton.setColorFilter(getResources().getColor(R.color.event_map));
                } else {
                    backButton.setEnabled(false);
                    backButton.setColorFilter(getResources().getColor(R.color.tag_text_color));
                }
                if (view.canGoForward()) {
                    forwardButton.setEnabled(true);
                    forwardButton.setColorFilter(getResources().getColor(R.color.event_map));
                } else {

                    forwardButton.setEnabled(false);
                    forwardButton.setColorFilter(getResources().getColor(R.color.tag_text_color));

                }
            }
            @Override
            public void onReceivedError( WebView view, int errorCode, String description, String failingUrl ) {

                super.onReceivedError( webView, errorCode, description, failingUrl );
                Toast.makeText( webFragment.this, description, Toast.LENGTH_LONG );
            }
        } );
        webView.loadUrl("http://www.totc.ca");


    }
}
