package com.engzi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.engzi.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    TextView to_login_button;
    EditText signup_password_edtxt;
    EditText signup_confirm_password_edtxt;
    Button toggle_show_password_button;
    Button toggle_show_confirm_password_button;

    //    Sign Up Utils
    FirebaseAuth mAuth;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initUI();
        mAuth = FirebaseAuth.getInstance();


//        Set Event Listener
        toggle_show_password_button.setOnTouchListener((view, motionEvent) -> handle_toggle_show_password(signup_password_edtxt, motionEvent));

        toggle_show_confirm_password_button.setOnTouchListener((view, motionEvent) -> handle_toggle_show_password(signup_confirm_password_edtxt, motionEvent));

        to_login_button.setOnClickListener(view -> {
            Intent loginIntent = new Intent(this, LogInActivity.class);
            startActivity(loginIntent);
        });
    }

    void initUI() {
        toggle_show_password_button = findViewById(R.id.show_password_button);
        toggle_show_confirm_password_button = findViewById(R.id.show_confirm_password_button);
        signup_password_edtxt = findViewById(R.id.signup_password_edtxt);
        signup_confirm_password_edtxt = findViewById(R.id.signup_confirm_password_edtxt);
        to_login_button = findViewById(R.id.to_login_button);

        to_login_button.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    private boolean handle_toggle_show_password(EditText targetView, MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
            targetView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            targetView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        return true;
    }
}