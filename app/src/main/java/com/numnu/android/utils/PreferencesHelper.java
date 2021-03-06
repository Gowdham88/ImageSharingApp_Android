package com.numnu.android.utils;

import android.content.Context;
import android.content.SharedPreferences;


/*
  Created by rajesh on 6/20/16.
*/

public class PreferencesHelper {

    // region Constants
    private static final String USER_PREFERENCES = "userPreferences";
    public static final String PREFERENCE_USER_NAME = USER_PREFERENCES + ".user_name";
    public static final String PREFERENCE_EMAIL = USER_PREFERENCES + ".email";
    public static final String PREFERENCE_ID = USER_PREFERENCES + ".id";
    public static final String PREFERENCE_PROFILE_PIC = ".profilePic";
    public static final String PREFERENCE_STATUS = ".status";
    public static final String PREFERENCE_IS_FIRST ="IsFirst";
    public static final String PREFERENCE_NAME ="name";
    public static final String PREFERENCE_CITY ="city";
    public static final String PREFERENCE_CITY_ID ="cityid";
    public static final String PREFERENCE_DOB ="dob";
    public static final String PREFERENCE_GENDER ="gender";
    public static final String PREFERENCE_LOGGED_IN ="logged in";
    public static final String PREFERENCE_FIREBASE_TOKEN ="firebase_token";
    public static final String PREFERENCE_FIREBASE_UUID ="firebase_uuid";
    public static final String PREFERENCE_USER_DESCRIPTION ="user_desc";
    public static final String PREFERENCE_TAGS ="tags";
    public static final String PREFERENCE_TAG_IDS ="tag_ids";
    public static final String PREFERENCE_GOOGLE_PLACE_ID ="google_place_id";
    public static final String PREFERENCE_GOOGLE_PLACE_TYPE ="google_place_type";
    public static final String PREFERENCE_LATITUDE ="latitude";
    public static final String PREFERENCE_LONGITUDE ="longitude";
    static final String KEY_REQUESTING_LOCATION_UPDATES = "requesting_locaction_updates";
    public static final String PREFERENCE_SEARCH_LOCATION ="search_location";
    public static final String PREFERENCE_SEARCH_LATITUDE ="search_latitude";
    public static final String PREFERENCE_SEARCH_LONGITUDE ="search_longitude";
    public static final String PREFERENCE_FIRST_TIME ="first_time";


    // endregion

    // region Constructors
    private PreferencesHelper() {
        //no instance
    }
    // endregion


    public static void signOut(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(USER_PREFERENCES);
        editor.remove(PREFERENCE_USER_NAME);
        editor.remove(PREFERENCE_EMAIL);
        editor.remove(PREFERENCE_ID);
        editor.remove(PREFERENCE_IS_FIRST);
        editor.remove(PREFERENCE_STATUS);
        editor.remove(PREFERENCE_NAME);
        editor.remove(PREFERENCE_CITY);
        editor.remove(PREFERENCE_DOB);
        editor.remove(PREFERENCE_GENDER);
        editor.remove(PREFERENCE_FIREBASE_TOKEN);
        editor.remove(PREFERENCE_FIREBASE_UUID);
        editor.remove(PREFERENCE_USER_DESCRIPTION);
        editor.remove(PREFERENCE_TAGS);
        editor.remove(PREFERENCE_PROFILE_PIC);
        editor.remove(PREFERENCE_TAG_IDS);
        editor.apply();
    }
    public static void setPreference(Context context, String preference_name, String details) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(preference_name, details);
        editor.apply();
    }

    public static void setPreferenceBoolean(Context context, String preference_name, boolean details) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(preference_name, details);
        editor.apply();
    }

    public static String getPreference(Context context, String name) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getString(name, "");
    }

    public static boolean getPreferenceBoolean(Context context, String name) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getBoolean(name, false);
    }

    public static void setPreferenceInt(Context context, String preference_name, int details) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(preference_name, details);
        editor.apply();
    }

    public static int getPreferenceInt(Context context, String name) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getInt(name, 0);
    }

    // region Helper Methods
    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }
    // endregion
}
