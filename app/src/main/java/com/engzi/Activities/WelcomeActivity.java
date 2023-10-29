package com.engzi.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.engzi.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class WelcomeActivity extends AppCompatActivity {
    ImageView welcome_logo_app;
    AppCompatButton login_action_btn;
    AppCompatButton signup_action_btn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();

        login_action_btn = findViewById(R.id.login_action_btn);
        signup_action_btn = findViewById(R.id.signup_action_btn);
        welcome_logo_app = findViewById(R.id.welcome_logo_app);


        login_action_btn.setOnClickListener(view -> {
            Intent loginIntent = new Intent(WelcomeActivity.this, LogInActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(WelcomeActivity.this,
                    welcome_logo_app, Objects.requireNonNull(ViewCompat.getTransitionName(welcome_logo_app)));
            startActivity(loginIntent, options.toBundle());
        });

        signup_action_btn.setOnClickListener(view -> {
            Intent signupIntent = new Intent(WelcomeActivity.this, SignUpActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(WelcomeActivity.this,
                    welcome_logo_app, Objects.requireNonNull(ViewCompat.getTransitionName(welcome_logo_app)));
            startActivity(signupIntent, options.toBundle());
        });
    }
}