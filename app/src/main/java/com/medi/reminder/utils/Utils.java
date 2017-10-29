package com.medi.reminder.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.medi.reminder.Constants;
import com.medi.reminder.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * @author Bhupinder.
 */
public class Utils {


    private static Utils utilities;
    private Toast msg;
    private AlertDialog mAlertDialog;

    public static Utils getInstance() {
        if (utilities == null) {
            utilities = new Utils();
        }
        return utilities;

    }


    /**
     * hide the soft keyboard
     *
     * @param ctx
     */
    public void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * Show toast.
     *
     * @param context the context
     * @param toast   String value which needs to shown in the toast.
     * @description if you want to print a toast just call this method and pass
     * what you want to be shown.
     */
    public synchronized Toast showToast(Context context, String toast) {
        if (context != null && msg == null || msg.getView().getWindowVisibility() != View.VISIBLE) {
            msg = Toast.makeText(context, toast, Toast.LENGTH_LONG);
            msg.setGravity(Gravity.CENTER, 0, 0);
            msg.show();
        }
        return msg;
    }


    /**
     * Show toast.
     *
     * @param message String value which needs to shown in the Snackbar.
     * @description if you want to print a Snackbar just call this method and pass
     * what you want to be shown.
     */
    public synchronized Snackbar showSnackbar(View content, String message) {
        Snackbar snackbar = null;
        if (content != null) {
            snackbar = Snackbar.make(content, message, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        return snackbar;
    }


    /*
    *  Getting the real path of image after select the image through gallery
    * */
    public synchronized String getRealPathFromURI(Uri contentURI, Context context) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    /*
    *  Getting the real path of image through camera
    * */
    public synchronized String getOriginalImagePath(FragmentActivity fragmentActivity) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = fragmentActivity.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);
        int column_index_data = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToLast();

        return cursor.getString(column_index_data);
    }


    public String getFormatedDateTime2(String startTime, String format) {
        String result = "";

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date myDate = simpleDateFormat.parse(startTime);
            DateFormat date = new SimpleDateFormat(format);
            result = date.format(myDate);


        } catch (Exception ex) {
            Log.e("error", ex.getMessage());
        }
        return result.replace(".", "");
    }


    /**
     * Save value to shared preference.
     *
     * @param key     On which key you want to save the value.
     * @param value   The value which needs to be saved.
     * @param context the context
     * @description To save the value to a preference file on the specified key.
     */
    public synchronized void saveValue(String key, long value, Context context) {
        if (context == null) return;
        SharedPreferences prefs = context.getSharedPreferences(Constants.FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor saveValue = prefs.edit();
        saveValue.putLong(key, value);
        saveValue.apply();
    }

    public synchronized void saveValue(String key, String value, Context context) {
        if (context == null) return;
        SharedPreferences prefs = context.getSharedPreferences(Constants.FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor saveValue = prefs.edit();
        saveValue.putString(key, value);
        saveValue.apply();
    }

    /**
     * Gets value from shared preference.
     *
     * @param key          The key from you want to get the value.
     * @param defaultValue Default value, if nothing is found on that key.
     * @param context      the context
     * @return the value from shared preference
     * @description To get the value from a preference file on the specified
     * key.
     */
    public synchronized String getValue(String key, String defaultValue, Context context) {
        if (context == null) return null;
        SharedPreferences prefs = context.getSharedPreferences(Constants.FILE,Context.MODE_PRIVATE);
        return prefs.getString(key, defaultValue);
    }


    /**
     * Save value to shared preference.
     *
     * @param key     On which key you want to save the value.
     * @param value   The value which needs to be saved.
     * @param context the context
     * @description To save the value to a saved preference file on the
     * specified key.
     */
    public synchronized void saveValue(String key, boolean value, Context context) {
        if (context == null) return;
        SharedPreferences prefs = context.getSharedPreferences(Constants.FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor saveValue = prefs.edit();
        saveValue.putBoolean(key, value);
        saveValue.apply();
    }

    /**
     * Gets value from shared preference.
     *
     * @param key          The key from you want to get the value.
     * @param defaultValue Default value, if nothing is found on that key.
     * @param context      the context
     * @return the value from shared preference
     * @description To get the value from a preference file on the specified
     * key.
     */
    public synchronized int getValue(String key, int defaultValue, Context context) {
        if (context == null) return 0;
        SharedPreferences prefs = context.getSharedPreferences(Constants.FILE,Context.MODE_PRIVATE);
        return prefs.getInt(key, defaultValue);
    }

    /**
     * Gets value from shared preference.
     *
     * @param key          The key from you want to get the value.
     * @param defaultValue Default value, if nothing is found on that key.
     * @param context      the context
     * @return the value from shared preference
     * @description To get the value from a preference file on the specified
     * key.
     */
    public synchronized long getValue(String key, long defaultValue, Context context) {
        if (context == null) return 0l;
        SharedPreferences prefs = context.getSharedPreferences(Constants.FILE,Context.MODE_PRIVATE);
        return prefs.getLong(key, defaultValue);
    }


    /**
     * Clear values of shared preference.
     *
     * @param context the context
     * @description If user logout then clear all the saved values from the
     * shared preference file
     */
    public synchronized void clearValueOfKey(Context context, String key) {
        if (context == null) return;
        SharedPreferences prefs = context.getSharedPreferences(Constants.FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor saveValue = prefs.edit();
        saveValue.remove(key).apply();
    }

    /**
     * Gets value from shared preference.
     *
     * @param key          The key from you want to get the value.
     * @param defaultValue Default value, if nothing is found on that key.
     * @param context      the context
     * @return the value from shared preference
     * @description To get the value from a saved preference file on the
     * specified key.
     */
    public synchronized boolean getValue(String key, boolean defaultValue, Context context) {
        if (context == null) return false;
        SharedPreferences prefs = context.getSharedPreferences(Constants.FILE,Context.MODE_PRIVATE);
        return prefs.getBoolean(key, defaultValue);
    }

    public void clearValues(Context context)
    {

        context.getSharedPreferences(Constants.FILE, Context.MODE_PRIVATE).edit().clear().commit();

    }
    /**
     * Is email valid.
     *
     * @param email Pass a email in string format and this method check if it is
     *              a valid address or not.
     * @return True if email is valid otherwise false.
     */
    public boolean isEmailValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
     /*Creates Country code selector  dialog*/


    public boolean hasReadLocationPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 23 && context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }


    public boolean isPermissionDeniedPermanently(Fragment fragment, String permission) {
        return fragment.shouldShowRequestPermissionRationale(permission);
    }


    public boolean isCurrentDay(long selectedMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return selectedMillis == calendar.getTimeInMillis();
    }

    public void downloadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_app_logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public int getWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width;
    }
    public String getFormatedDateTimeFromTo(String startTime, Integer addminutes) {
        String result = "";

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date myDate = simpleDateFormat.parse(startTime);
            DateFormat time = new SimpleDateFormat("hh:mm a");

            //add minutes to given time
            Calendar cal = Calendar.getInstance();
            cal.setTime(myDate);
            cal.add(Calendar.MINUTE, addminutes);
            result = String.valueOf(time.format(myDate)) + " to " + time.format(cal.getTime());

        } catch (Exception ex) {
            Log.e("error", ex.getMessage());
        }
        return result.replace(".", "");
    }


}