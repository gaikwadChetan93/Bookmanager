package mobileutility.bookmanager.utils;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import dagger.Provides;
import mobileutility.bookmanager.di.scopes.UserScope;

/**
 * Created by chegaikw on 7/1/2016.
 */
public class Utils {

    private static final boolean SHOW_LOG = true;
    /**
     * Method is used to check internet connectivity
     * @param context
     * @return
     */
    public static boolean isConnectedToInternet(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    /**
     * Method is used to display alert with custom title and messahe
     * @param context
     * @param title
     * @param message
     */
    public static void showAlert(Context context, String title, String message){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();
    }
    /**
     * Method is used to display alert with custom title and message and exit if clicked ok
     * @param context
     * @param title
     * @param message
     */
    public static void showExitAlert(final Activity context, String title, String message){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        context.finish();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })

                .show();
    }

    /**
     * Method to show debug log message
     * @param tag
     * @param message
     */
    public static void showLog(String tag, String message){
        if(SHOW_LOG){
            Log.d(tag,message);
        }
    }

    /**
     * Method to get current timestamp
     * @return
     */
    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * Provide progress dailog
     * @param application
     * @return
     */
    public static ProgressDialog getProgressDialog(Activity application) {
        ProgressDialog progressDialog = new ProgressDialog(application);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
}
