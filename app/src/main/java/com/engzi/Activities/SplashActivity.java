package com.engzi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.engzi.R;
import com.engzi.Utils.FireBaseUtil;
import com.google.firebase.auth.FirebaseUser;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseUser mUser = FireBaseUtil.mAuth.getCurrentUser();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (mUser != null) {
                Intent homePageIntent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(homePageIntent);
            } else {
                Intent welcomeIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(welcomeIntent);
            }
            finish();
        }, 1100);
    }
}