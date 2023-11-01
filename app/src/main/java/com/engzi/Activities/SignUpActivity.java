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

import com.engzi.Model.User;
import com.engzi.R;
import com.engzi.Services.UserServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {
    TextView to_login_button;
    EditText email_edtxt;
    EditText signup_password_edtxt;
    EditText signup_confirm_password_edtxt;
    Button toggle_show_password_button;
    Button toggle_show_confirm_password_button;

    AppCompatButton signup_btn;

    //   Utils
    FirebaseAuth mFirebaseAuth;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initUI();
        triggerInputData();

        mFirebaseAuth = FirebaseAuth.getInstance();


//        Set Event Listener
        toggle_show_password_button.setOnTouchListener((view, motionEvent) ->
                handle_toggle_show_password(signup_password_edtxt, motionEvent));

        toggle_show_confirm_password_button.setOnTouchListener((view, motionEvent) ->
                handle_toggle_show_password(signup_confirm_password_edtxt, motionEvent));

        to_login_button.setOnClickListener(view -> {
            Intent loginIntent = new Intent(this, LogInActivity.class);
            startActivity(loginIntent);
            finish();
        });

        signup_password_edtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                triggerInputData();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signup_confirm_password_edtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                triggerInputData();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        email_edtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                triggerInputData();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signup_btn.setOnClickListener(view -> {
            signup_btn.setEnabled(false);
            signUpWithFirebaseAuth();
        });
    }

    private void initUI() {
        toggle_show_password_button = findViewById(R.id.show_password_button);
        toggle_show_confirm_password_button = findViewById(R.id.show_confirm_password_button);
        signup_password_edtxt = findViewById(R.id.signup_password_edtxt);
        signup_confirm_password_edtxt = findViewById(R.id.signup_confirm_password_edtxt);
        to_login_button = findViewById(R.id.to_login_button);
        email_edtxt = findViewById(R.id.email_edtxt);
        signup_btn = findViewById(R.id.signup_btn);

        to_login_button.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    private void signUpWithFirebaseAuth() {
        String email = email_edtxt.getText().toString().trim();
        String password = signup_password_edtxt.getText().toString();
        String confirmPassword = signup_confirm_password_edtxt.getText().toString();
        if (email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            if (!password.contains(" ") || !confirmPassword.contains(" ")) {
                if (password.equals(confirmPassword)) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser userDocument = task.getResult().getUser();
                                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                        LocalDate now = LocalDate.now();

                                        UserServices userServices = new UserServices();
                                        userServices.createUser(
                                                userDocument.getDisplayName() != null ? userDocument.getDisplayName() : ("profile" + userDocument.getUid()),
                                                now.toString());

                                        Intent homePageIntent = new Intent(getBaseContext(), HomeActivity.class);
                                        Bundle userBundle = new Bundle();
//                                        userBundle.putSerializable("userProfile", userProfile);
//                                        homePageIntent.putExtras(userBundle);
                                        startActivity(homePageIntent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), task.getResult().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "sai confirm pass", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "password co dau khoang trang", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "sai format email", Toast.LENGTH_SHORT).show();
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

    private void triggerInputData() {
        String email = email_edtxt.getText().toString();
        String password = signup_password_edtxt.getText().toString();
        String confirmPassword = signup_confirm_password_edtxt.getText().toString();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            signup_btn.setEnabled(false);
            signup_btn.setTextColor(getColor(R.color.black));
        } else {
            signup_btn.setEnabled(true);
            signup_btn.setTextColor(getColor(R.color.white));
        }
    }

}