package com.numnu.android;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by thulir on 9/10/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ProximaNova-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
