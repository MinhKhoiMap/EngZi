package com.engzi.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.engzi.R;

public class SignUpActivity extends AppCompatActivity {
    TextView to_login_button;
    EditText signup_password_edtxt;
    EditText signup_confirm_password_edtxt;
    Button toggle_show_password_button;
    Button toggle_show_confirm_password_button;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toggle_show_password_button = findViewById(R.id.show_password_button);
        toggle_show_confirm_password_button = findViewById(R.id.show_confirm_password_button);
        signup_password_edtxt = findViewById(R.id.signup_password_edtxt);
        signup_confirm_password_edtxt = findViewById(R.id.signup_confirm_password_edtxt);
        to_login_button = findViewById(R.id.to_login_button);

        to_login_button.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        toggle_show_password_button.setOnTouchListener((view, motionEvent) ->
                handle_toggle_show_password(signup_password_edtxt, motionEvent));

        toggle_show_confirm_password_button.setOnTouchListener((view, motionEvent) ->
                handle_toggle_show_password(signup_confirm_password_edtxt, motionEvent));
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