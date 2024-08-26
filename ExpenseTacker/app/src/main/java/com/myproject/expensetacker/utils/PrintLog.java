package com.myproject.expensetacker.utils;

import android.util.Log;


public class PrintLog {
    private static final String TAG = "PrintLog";

    public static void debugLog(String tag, String message) {
        Log.d(TAG, tag + " : " + message);
    }

    public static void infoLog(String tag, String message) {
        Log.i(TAG, tag + " : " + message);
    }

    public static void errorLog(String tag, String message) {
        Log.e(TAG, tag + " : " + message);
    }
}
