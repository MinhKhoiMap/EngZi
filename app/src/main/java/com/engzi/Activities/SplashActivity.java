package com.engzi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.engzi.R;
import com.engzi.Utils.FireBaseUtils;
import com.google.firebase.auth.FirebaseUser;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseUser mUser = FireBaseUtils.mAuth.getCurrentUser();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (mUser != null) {
                Intent homePageIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(homePageIntent);
            } else {
                Intent welcomeIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(welcomeIntent);
            }
            finish();
        }, 1100);
    }
}