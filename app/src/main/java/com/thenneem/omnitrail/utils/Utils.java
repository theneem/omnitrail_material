package com.thenneem.omnitrail.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.thenneem.omnitrail.R;

public class Utils {
    private static ProgressDialog pdialog;

    public static void showLoader(Activity act) {

        if (act != null && !act.isFinishing()) {

            try {
                if (pdialog != null && pdialog.isShowing()) {
                    pdialog.cancel();
                }
            } catch (Exception e) {
                Log.e("LOADER", "CANCEL EXCEPTION");
            }

            pdialog = new ProgressDialog(act);
            pdialog.setMessage(act.getString(R.string.please_wait));
            pdialog.setCancelable(true);
            pdialog.setCanceledOnTouchOutside(false);
            pdialog.setIndeterminate(false);
            pdialog.show();
        }
    }

    public static void hideLoader(Activity act) {
        /*if (act != null && !act.isFinishing() && popupDialog != null) {
            Log.i("LOADER", "HIDING LOADER");
            popupDialog.cancel();
        }*/
        if (act != null && !act.isFinishing() && pdialog != null) {
            Log.i("LOADER", "HIDING LOADER");
            pdialog.dismiss();
        }
    }

    public static void showSnackBar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showErrorSnackBar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setBackgroundTint(Color.RED).setActionTextColor(Color.WHITE).show();
    }

    public static boolean isConnected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return wificonn != null && wificonn.isConnected() || mobileconn != null && mobileconn.isConnected();
    }
}
