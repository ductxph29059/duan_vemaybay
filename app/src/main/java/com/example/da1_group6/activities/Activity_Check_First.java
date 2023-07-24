package com.example.da1_group6.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.da1_group6.R;

public class Activity_Check_First extends AppCompatActivity {

    public static final String key = "first";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_first);

        final MySharedPreferences preference = new MySharedPreferences(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(preference.getValues(key)) {
                    // key = true -> login.activity
                    startActivity(Activity_Login.class);
                } else {
                    // key = false -> onboard.activity
                    startActivity(OnboardActivity.class);
                }
            }
        }, 0);
    }

    void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }
}