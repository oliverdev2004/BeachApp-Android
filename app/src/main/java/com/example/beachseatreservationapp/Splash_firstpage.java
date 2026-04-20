package com.example.beachseatreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_firstpage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Splash_firstpage.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 5000); // 5 seconds
    }

    }
