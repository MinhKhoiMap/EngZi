package com.engzi.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.engzi.R;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {
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


        login_action_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(WelcomeActivity.this, LogInActivity.class);
                startActivity(loginIntent);
            }
        });

        signup_action_btn.setOnClickListener(view -> {
            Intent signupIntent = new Intent(WelcomeActivity.this, SignUpActivity.class);
            startActivity(signupIntent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            Intent homePageIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homePageIntent);
            finish();
        }
    }
}