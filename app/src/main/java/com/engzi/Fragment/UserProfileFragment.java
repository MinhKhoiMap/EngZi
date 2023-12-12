package com.engzi.Fragment;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.engzi.Activities.MainActivity;
import com.engzi.Activities.WelcomeActivity;
import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.User;
import com.engzi.R;
import com.engzi.Services.UserServices;
import com.engzi.Utils.FireBaseUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserProfileFragment extends Fragment {
    MainActivity mMainActivity;
    User userProfile;
    String avatar_url, email, username, fake_pass = "123456789";

    //    View
    View groupView;
    ImageButton edit_avatar_button, update_username_button, update_password_button;
    AppCompatButton logout_action_btn;
    ImageView avatar_img;
    EditText username_edtxt, password_edtxt, email_edtxt;
    Toolbar profile_toolbar;

    //    Services
    UserServices userServices = new UserServices();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
        groupView = inflater.inflate(R.layout.fragment_user_profile, container, false);

        assert getArguments() != null;
        userProfile = (User) getArguments().get("userProfile");
        avatar_url = userProfile.getAvatarImg();
        email = userProfile.getEmail();
        username = userProfile.getProfileName();

        initUI();

        Glide.with(mMainActivity).load(avatar_url)
                .transition(withCrossFade())
                .into(avatar_img);

        email_edtxt.setText(email);
        username_edtxt.setText(username);
        password_edtxt.setText(fake_pass);

        edit_avatar_button.setOnClickListener(view -> {
            Bundle avatarBundle = new Bundle();
            avatarBundle.putString("avatar_url", avatar_url);
            mMainActivity.setSectionFragment(new AvatarFragment(), avatarBundle, true);
        });

        update_username_button.setOnClickListener(view -> {
            username_edtxt.setBackground(getResources().getDrawable(R.drawable.edtxt_disable_background));
            username_edtxt.setTextColor(Color.parseColor("#A4A4A4"));
            String newName = username_edtxt.getText().toString().trim();
            userServices.updateUserProfile("profileName", newName, new IServiceCallBack() {
                @Override
                public void retrieveData(Object response) {

                }

                @Override
                public void onFailed(Exception e) {

                }

                @Override
                public void onComplete() {
                    username_edtxt.clearFocus();
                    username_edtxt.setBackground(getResources().getDrawable(R.drawable.edtxt_background));
                    username_edtxt.setTextColor(getResources().getColor(R.color.primary));
                }
            });
        });

        update_password_button.setOnClickListener(view -> {
            password_edtxt.setBackground(getResources().getDrawable(R.drawable.edtxt_disable_background));
            password_edtxt.setTextColor(Color.parseColor("#A4A4A4"));
            String newPass = password_edtxt.getText().toString().trim();
            userServices.updateUserPassword(newPass, new IServiceCallBack() {
                @Override
                public void retrieveData(Object response) {

                }

                @Override
                public void onFailed(Exception e) {

                }

                @Override
                public void onComplete() {
                    username_edtxt.clearFocus();
                    password_edtxt.setBackground(getResources().getDrawable(R.drawable.edtxt_background));
                    username_edtxt.setTextColor(getResources().getColor(R.color.primary));
                }
            });
        });

        logout_action_btn.setOnClickListener(view -> {
            FireBaseUtils.mAuth.signOut();
            Intent welcomeIntent = new Intent(getContext(), WelcomeActivity.class);
            getActivity().finish();
            startActivity(welcomeIntent);
        });

        profile_toolbar.setNavigationOnClickListener(view -> {
            ((BottomNavigationView) mMainActivity.findViewById(R.id.bottom_main_nav_view))
                    .getMenu().getItem(2).setChecked(true);
            mMainActivity.setSectionFragment(new HomePageFragment(), mMainActivity.getUserBundle(), false);
        });

        return groupView;
    }

    @Override
    public void onResume() {
        super.onResume();
        userServices.getUserById(new IServiceCallBack() {
            @Override
            public void retrieveData(Object response) {
                userProfile = (User) response;
            }

            @Override
            public void onFailed(Exception e) {

            }

            @Override
            public void onComplete() {
                avatar_url = userProfile.getAvatarImg();
                Glide.with(mMainActivity).load(avatar_url)
                        .transition(withCrossFade())
                        .into(avatar_img);
            }
        });
    }

    private void initUI() {
        edit_avatar_button = groupView.findViewById(R.id.edit_avatar_button);
        avatar_img = groupView.findViewById(R.id.avatar_img);
        username_edtxt = groupView.findViewById(R.id.username_edtxt);
        password_edtxt = groupView.findViewById(R.id.password_edtxt);
        email_edtxt = groupView.findViewById(R.id.email_edtxt);
        update_username_button = groupView.findViewById(R.id.update_username_button);
        update_password_button = groupView.findViewById(R.id.update_password_button);
        logout_action_btn = groupView.findViewById(R.id.logout_action_btn);
        profile_toolbar = groupView.findViewById(R.id.profile_toolbar);
    }
}