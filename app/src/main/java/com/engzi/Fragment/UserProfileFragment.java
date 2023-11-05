package com.engzi.Fragment;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.engzi.Activities.MainActivity;
import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.User;
import com.engzi.R;
import com.engzi.Services.UserServices;

public class UserProfileFragment extends Fragment {
    MainActivity mMainActivity;
    User userProfile;
    String avatar_url, email, username, fake_pass = "123456789";

    //    View
    View groupView;
    ImageButton edit_avatar_button, update_username_button, update_password_button;
    ImageView avatar_img;
    EditText username_edtxt, password_edtxt, email_edtxt;

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
            mMainActivity.setSectionFragment(new AvatarFragment(), avatarBundle);
        });

        update_username_button.setOnClickListener(view -> {
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
                }
            });
        });

        update_password_button.setOnClickListener(view -> {
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
                }
            });
        });

        return groupView;
    }

    private void initUI() {
        edit_avatar_button = groupView.findViewById(R.id.edit_avatar_button);
        avatar_img = groupView.findViewById(R.id.avatar_img);
        username_edtxt = groupView.findViewById(R.id.username_edtxt);
        password_edtxt = groupView.findViewById(R.id.password_edtxt);
        email_edtxt = groupView.findViewById(R.id.email_edtxt);
        update_username_button = groupView.findViewById(R.id.update_username_button);
        update_password_button = groupView.findViewById(R.id.update_password_button);
    }
}