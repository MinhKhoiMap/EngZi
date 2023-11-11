package com.engzi.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.engzi.R;
import com.engzi.Utils.FireBaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
    TextView forget_password;
    TextView to_signup_button;
    Button toggle_show_password_button;
    AppCompatButton login_btn;
    EditText login_email_edtxt, login_password_edtxt;

    FirebaseAuth mFirebaseAuth = FireBaseUtils.mAuth;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initUI();
        triggerInput();

        toggle_show_password_button.setOnTouchListener((view, motionEvent) ->
                handle_toggle_show_password(login_password_edtxt, motionEvent)
        );


        to_signup_button.setOnClickListener(view -> {
            Intent signupIntent = new Intent(this, SignUpActivity.class);
            startActivity(signupIntent);
            finish();
        });

        login_password_edtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                triggerInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        login_email_edtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                triggerInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        login_btn.setOnClickListener(view -> {
            login_btn.setEnabled(false);
            login_btn.setTextColor(getColor(R.color.yellow_kv));
            logInWithFirebaseAuth();
        });
    }

    private void initUI() {
        forget_password = findViewById(R.id.forget_password);
        to_signup_button = findViewById(R.id.to_signup_button);
        toggle_show_password_button = findViewById(R.id.show_password_button);
        login_password_edtxt = findViewById(R.id.login_password_edtxt);
        login_email_edtxt = findViewById(R.id.login_email_edtxt);
        login_btn = findViewById(R.id.login_btn);

        forget_password.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        to_signup_button.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void logInWithFirebaseAuth() {
        String email = login_email_edtxt.getText().toString().trim();
        String password = login_password_edtxt.getText().toString();

        if (email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            mFirebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Intent homePageIntent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(homePageIntent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                        login_btn.setEnabled(true);
                        login_btn.setTextColor(getColor(R.color.white));
                    });
        } else {
            login_email_edtxt.setError("Invalid Email!");
        }
    }

    private boolean handle_toggle_show_password(EditText targetView, MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
            targetView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            targetView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        return true;
    }

    private void triggerInput() {
        if (login_email_edtxt.getText().toString().trim().isEmpty()) {
            login_btn.setEnabled(false);
            login_btn.setTextColor(getColor(R.color.coffeeKV));
        } else {
            login_btn.setEnabled(true);
            login_btn.setTextColor(getColor(R.color.white));
        }
    }
}