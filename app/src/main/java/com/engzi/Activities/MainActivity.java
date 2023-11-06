package com.engzi.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.engzi.Fragment.HomePageFragment;
import com.engzi.Fragment.TopicPracticeFragment;
import com.engzi.Fragment.UserProfileFragment;
import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.User;
import com.engzi.R;
import com.engzi.Services.UserServices;
import com.engzi.Utils.FireBaseUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    User userProfile;
    Bundle userBundle;
    //    View
    FrameLayout section_layout;
    BottomNavigationView bottom_main_nav_view;

    FloatingActionButton home_button;

    //    Fragment
    Fragment home_section, user_profile_section, topic_practice_section;

    //    Services
    UserServices userServices = new UserServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent userProfileIntent = getIntent();
//        Toast.makeText(this, Objects.requireNonNull(FireBaseUtil.mAuth.getCurrentUser()).getUid(), Toast.LENGTH_SHORT).show();

        user_profile_section = new UserProfileFragment();
        topic_practice_section = new TopicPracticeFragment();
        home_section = new HomePageFragment();

        if (userProfileIntent.getSerializableExtra("userProfile") != null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                userProfile = userProfileIntent.getSerializableExtra("userProfile", User.class);
            } else {
                userProfile = (User) userProfileIntent.getSerializableExtra("userProfile");
            }
        } else {
            userServices.getUserById(new IServiceCallBack() {
                @Override
                public void retrieveData(Object response) {
                    userProfile = (User) response;
                    userProfile.setEmail(FireBaseUtils.mAuth.getCurrentUser().getEmail());
                    userProfile.setUID(FireBaseUtils.mAuth.getCurrentUser().getUid());
                }

                @Override
                public void onFailed(Exception e) {
                    Log.w("users", "Error get user by ID", e);
                }

                @Override
                public void onComplete() {
                    userBundle = new Bundle();
                    userBundle.putSerializable("userProfile", userProfile);
                    setSectionFragment(home_section, userBundle);
//                    setSectionFragment(new TopicPracticeFragment(), null);
                }
            });
        }

        initUI();

        home_button.setOnClickListener(view -> {
            setSectionFragment(home_section, userBundle);
            bottom_main_nav_view.getMenu().getItem(2).setChecked(true);
        });

        bottom_main_nav_view.setOnItemSelectedListener(item -> {
            int itemID = item.getItemId();
            if (itemID == R.id.profile) {
                setSectionFragment(user_profile_section, userBundle);
                return true;
            } else if (itemID == R.id.daily_practice) {
                setSectionFragment(topic_practice_section, null);
                return true;
            } else if (itemID == R.id.notebook) {
                return true;
            } else if (itemID == R.id.notification) {
                return true;
            }
            return false;
        });

    }


    private void initUI() {
        section_layout = findViewById(R.id.section_layout);
        bottom_main_nav_view = findViewById(R.id.bottom_main_nav_view);
        home_button = findViewById(R.id.home_button);

        bottom_main_nav_view.setBackground(null);
        bottom_main_nav_view.getMenu().getItem(2).setEnabled(false);
        bottom_main_nav_view.getMenu().getItem(2).setChecked(true);
    }

    public void setSectionFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
//        Log.d("fragment", fragment.getClass().getSimpleName());
        String targetFragment = fragment.getClass().getSimpleName().toLowerCase();
        switch (targetFragment) {
            case "topicpracticefragment":
                bottom_main_nav_view.getMenu().getItem(0).setChecked(true);
                break;
            case "userprofilefragment":
                bottom_main_nav_view.getMenu().getItem(4).setChecked(true);
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(section_layout.getId(), fragment);
        fragmentTransaction.commit();
    }
}