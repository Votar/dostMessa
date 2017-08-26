package com.entregoya.msngr.util;


import android.util.Log;

/**
 * Created by bertalt on 06.09.16.
 */
public class Logger {


    public static boolean DEBUG;

    static {
        DEBUG = false;
    }


    public static void logd(String message) {

        if (DEBUG)
            Log.d("DEBUG",""+ message);
    }

    public static void logd(String TAG, String message) {

        if (DEBUG)
            Log.d(TAG, ""+ message);
    }

    public static void lognet(String message) {

        if (DEBUG)
            Log.d("NETWORK", ""+ message);
    }

    public static void loge(String TAG, String message) {

        if (DEBUG)
            Log.e(TAG, ""+ message);
    }


}
