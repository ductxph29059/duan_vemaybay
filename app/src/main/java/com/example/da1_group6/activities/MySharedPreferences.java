package com.example.da1_group6.activities;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    public static final String NAME = "MY_SHARED";
    Context context;

    public MySharedPreferences(Context context) {
        this.context = context;
    }

    public void putValues(String key, boolean values) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(key, values);
        editor.apply();
    }

    public boolean getValues(String key) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, 0);
        return preferences.getBoolean(key, false);
    }
}
