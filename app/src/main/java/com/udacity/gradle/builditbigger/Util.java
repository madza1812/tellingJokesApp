package com.udacity.gradle.builditbigger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by An on 12/10/2015.
 */
public class Util {
    private static Util util;
    private static final String LOG_TAG = "UTIL CLASS";

    public static synchronized Util instatnce() {
        if (util == null)
            util = new Util();
        return util;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    public static void displayToast (Context context, CharSequence msg) {
        Toast noConToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        noConToast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
        noConToast.show();
    }

    public static void displayNeutralAlert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Title and message
        builder.setMessage(message)
                .setTitle(title);
        // OK button
        builder.setNeutralButton(R.string.ok_alert, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}