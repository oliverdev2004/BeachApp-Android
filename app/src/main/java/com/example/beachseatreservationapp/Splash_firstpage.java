package com.example.beachseatreservationapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_firstpage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //splash go over the screen
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.splash);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Splash_firstpage.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000); // 5 seconds
    }

    }
