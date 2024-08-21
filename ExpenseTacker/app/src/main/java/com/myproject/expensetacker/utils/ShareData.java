package com.myproject.expensetacker.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ShareData {
    private final Context mContext;
    private final String mPreferenceName = "com.myproject.expensetacker";
    public static final String IS_LOGIN = "IS_LOGIN"; // boolean
    public static final String USERNAME = "USERNAME"; // String


    public ShareData(Context context) {
        mContext = context;
    }

    public void clearAll() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void deleteSharedValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).apply();
    }

    public boolean containsKey(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
        return sharedPreferences.contains(key);
    }

    public void putString(String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putDouble(String key, double value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, Double.doubleToRawLongBits(value));
        editor.apply();
    }

    public void putFloat(String key, float value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }


    public String getString(String key, String defaultValue) {
        String value = "";
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
        value = sharedPreferences.getString(key, defaultValue);
        return value;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    public double getDouble(String key, double defaultValue) {
        double value;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
        value = Double.longBitsToDouble(sharedPreferences.getLong(key, Double.doubleToLongBits(defaultValue)));
        return value;
    }

    public float getFloat(String key, float defaultValue) {
        float value;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
        value = sharedPreferences.getFloat(key, defaultValue);
        return value;
    }

    public void removeKeyValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
