package com.engzi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.engzi.R;

public class LogInActivity extends AppCompatActivity {

    TextView forget_password;
    TextView to_signup_button;

    Button toggle_show_password_button;
    EditText login_password_edtxt;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        forget_password = findViewById(R.id.forget_password);
        to_signup_button = findViewById(R.id.to_signup_button);
        toggle_show_password_button = findViewById(R.id.show_password_button);
        login_password_edtxt = findViewById(R.id.login_password_edtxt);

        forget_password.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        to_signup_button.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        toggle_show_password_button.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                login_password_edtxt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                login_password_edtxt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            return true;
        });
    }
}