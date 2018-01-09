package com.numnu.android.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.format.Formatter;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.numnu.android.R;
import com.numnu.android.network.response.BookmarkdataItem;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;


public class Utils {


	private static final String TAG = "Utils";

	public static  boolean isNetworkAvailable(Context context) {
		if(context == null) { return false; }
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// if no network is available networkInfo will be null, otherwise check if we are connected
		try {
			NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, "isNetworkAvailable()" ,e);
		}
		return false;
	}


	public static void hideKeyboard(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		//Find the currently focused view, so we can grab the correct window token from it.
		View view = activity.getCurrentFocus();
		//If no view currently has focus, create a new one, just so we can grab a window token from it
		if (view == null) {
			view = new View(activity);
		}
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}



    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

	public static void setTextColors(TextView view, String fulltext, String subtext, int color) {
		view.setText(fulltext, TextView.BufferType.SPANNABLE);
		Spannable str = (Spannable) view.getText();
		int i = fulltext.indexOf(subtext);
		str.setSpan(new ForegroundColorSpan(color), i, i + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}




	public static String getLocalIpAddress(Context context) {
		WifiManager wifiMgr = (WifiManager) context.getApplicationContext().getSystemService(context.WIFI_SERVICE);
		if(wifiMgr.isWifiEnabled()) {
			WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
			int ip = wifiInfo.getIpAddress();
			String wifiIpAddress = String.format("%d.%d.%d.%d",
					(ip & 0xff),
					(ip >> 8 & 0xff),
					(ip >> 16 & 0xff),
					(ip >> 24 & 0xff));

			return wifiIpAddress;
		}

		try {
		for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
			NetworkInterface intf = en.nextElement();
			for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
				InetAddress inetAddress = enumIpAddr.nextElement();
				Log.i("","111 inetAddress.getHostAddress(): "+inetAddress.getHostAddress());
//the condition after && is missing in your snippet, checking instance of inetAddress
				if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
					Log.i("","111 return inetAddress.getHostAddress(): "+inetAddress.getHostAddress());
					return inetAddress.getHostAddress();
				}

			}
		}
		} catch (Exception ex) { } // for now eat exceptions

		return null;
	}

	public  static String parseDate(String date1)
	{
		SimpleDateFormat endformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		Date date = null;
		try
		{
			date = endformat.parse(date1);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String parsedDate = dateFormat.format(date);
		return parsedDate;
	}


	/**
	 * Returns true if requesting location updates, otherwise returns false.
	 *
	 * @param context The {@link Context}.
	 */
	public static boolean requestingLocationUpdates(Context context) {

		return  PreferencesHelper.getPreferenceBoolean(context,PreferencesHelper.KEY_REQUESTING_LOCATION_UPDATES);
	}

	/**
	 * Stores the location updates state in SharedPreferences.
	 * @param requestingLocationUpdates The location updates state.
	 */
	public static void setRequestingLocationUpdates(Context context, boolean requestingLocationUpdates) {
		PreferencesHelper.setPreferenceBoolean(context,PreferencesHelper.KEY_REQUESTING_LOCATION_UPDATES,requestingLocationUpdates);
	}
	public static String getTimeAgo(java.util.Date date, Context ctx) {

		if(date == null) {
			return null;
		}

		long time = date.getTime();

		Date curDate = currentDate();
		long now = curDate.getTime();
		if (time > now || time <= 0) {
			return null;
		}

		int dim = getTimeDistanceInMinutes(time);

		String timeAgo = null;

		if (dim == 0) {
			timeAgo = ctx.getResources().getString(R.string.date_util_term_less) + " " +  ctx.getResources().getString(R.string.date_util_term_a) + " " + ctx.getResources().getString(R.string.date_util_unit_minute);
		} else if (dim == 1) {
			return "1 " + ctx.getResources().getString(R.string.date_util_unit_minute);
		} else if (dim >= 2 && dim <= 44) {
			timeAgo = dim + " " + ctx.getResources().getString(R.string.date_util_unit_minutes);
		} else if (dim >= 45 && dim <= 89) {
			timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " "+ctx.getResources().getString(R.string.date_util_term_an)+ " " + ctx.getResources().getString(R.string.date_util_unit_hour);
		} else if (dim >= 90 && dim <= 1439) {
			timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + (Math.round(dim / 60)) + " " + ctx.getResources().getString(R.string.date_util_unit_hours);
		} else if (dim >= 1440 && dim <= 2519) {
			timeAgo = "1 " + ctx.getResources().getString(R.string.date_util_unit_day);
		} else if (dim >= 1440 && dim <= 10079) {
			timeAgo = (Math.round(dim / 1440)) + " " + ctx.getResources().getString(R.string.date_util_unit_days);
		}else if (dim >= 10080 && dim <= 43199) {
			timeAgo = (Math.round(dim / 10080)) + " " + ctx.getResources().getString(R.string.date_util_unit_weeks);
		}else if (dim >= 43200 && dim <= 86399) {
			timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " "+ctx.getResources().getString(R.string.date_util_term_a)+ " " + ctx.getResources().getString(R.string.date_util_unit_month);
		} else if (dim >= 86400 && dim <= 525599) {
			timeAgo = (Math.round(dim / 43200)) + " " + ctx.getResources().getString(R.string.date_util_unit_months);
		} else if (dim >= 525600 && dim <= 655199) {
			timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " "+ctx.getResources().getString(R.string.date_util_term_a)+ " " + ctx.getResources().getString(R.string.date_util_unit_year);
		} else if (dim >= 655200 && dim <= 914399) {
			timeAgo = ctx.getResources().getString(R.string.date_util_prefix_over) + " "+ctx.getResources().getString(R.string.date_util_term_a)+ " " + ctx.getResources().getString(R.string.date_util_unit_year);
		} else if (dim >= 914400 && dim <= 1051199) {
			timeAgo = ctx.getResources().getString(R.string.date_util_prefix_almost) + " 2 " + ctx.getResources().getString(R.string.date_util_unit_years);
		} else {
			timeAgo = ctx.getResources().getString(R.string.date_util_prefix_about) + " " + (Math.round(dim / 525600)) + " " + ctx.getResources().getString(R.string.date_util_unit_years);
		}

		return timeAgo + " " + ctx.getResources().getString(R.string.date_util_suffix);
	}

	public static Date currentDate() {
		Calendar calendar = Calendar.getInstance();
		return (Date) calendar.getTime();
	}
	private static int getTimeDistanceInMinutes(long time) {
		long timeDistance = currentDate().getTime() - time;
		return Math.round((Math.abs(timeDistance) / 1000) / 60);
	}

	public static List<BookmarkdataItem> bookmarkFilter(List<BookmarkdataItem> bookmarkdataItems,String type){
		List<BookmarkdataItem> filteredList=new ArrayList<>();
		for (BookmarkdataItem bookmarkdataItem:bookmarkdataItems) {
			if(bookmarkdataItem.getType().equals(type)){
				filteredList.add(bookmarkdataItem);
			}
		}
		return filteredList;

	}


}
