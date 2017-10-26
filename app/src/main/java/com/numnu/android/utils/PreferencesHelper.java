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
    public static final String PREFERENCE_SYNCED ="synced";
    public static final String PREFERENCE_PWD ="pass";
    public static final String PREFERENCE_OFFLINE_AMOUNT_LIMIT ="offline_limit";
    public static final String PREFERENCE_ONLINE_AMOUNT_LIMIT ="online_limit";
    public static final String PREFERENCE_COLLECTED_AMOUNT ="collected_amount";
    public static final String PREFERENCE_COLLECTION_DATE ="collected_date";
    public static final String PREFERENCE_LAST_BILL_NO ="last_bill_no";
    public static final String PREFERENCE_IS_SIGNED_IN ="is_signed_in";
    public static final String PREFERENCE_NAME ="name";
    public static final String PREFERENCE_CITY ="city";
    public static final String PREFERENCE_DOB ="dob";
    public static final String PREFERENCE_GENDER ="gender";



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
        return preferences.getBoolean(name, true);
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
