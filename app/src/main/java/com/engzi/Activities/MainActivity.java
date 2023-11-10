package com.engzi.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.engzi.Fragment.BookMarkFragment;
import com.engzi.Fragment.ExamsListFragment;
import com.engzi.Fragment.HomePageFragment;
import com.engzi.Fragment.TopicListFragment;
import com.engzi.Fragment.UserProfileFragment;
import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.User;
import com.engzi.R;
import com.engzi.Services.ExamsServices;
import com.engzi.Services.UserServices;
import com.engzi.Utils.FireBaseUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    User userProfile;
    Bundle userBundle;
    //    View
    FrameLayout section_layout;
    BottomNavigationView bottom_main_nav_view;

    FloatingActionButton home_button;
    //    Fragment
    Fragment home_section, user_profile_section,
            topic_practice_section, bookmark_section;

    //    Services
    UserServices userServices = new UserServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent userProfileIntent = getIntent();

        user_profile_section = new UserProfileFragment();
        topic_practice_section = new TopicListFragment();
        home_section = new HomePageFragment();
        bookmark_section = new BookMarkFragment();

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
                    setSectionFragment(home_section, userBundle, false);
                }
            });
        }

        initUI();

        home_button.setOnClickListener(view -> {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            setSectionFragment(home_section, userBundle, false);
            bottom_main_nav_view.getMenu().getItem(2).setChecked(true);
        });

        bottom_main_nav_view.setOnItemSelectedListener(item -> {
            int itemID = item.getItemId();
            if (itemID == R.id.profile) {
                setSectionFragment(user_profile_section, userBundle, false);
                return true;
            } else if (itemID == R.id.daily_practice) {
                setSectionFragment(topic_practice_section, null, false);
                return true;
            } else if (itemID == R.id.notebook) {
                setSectionFragment(bookmark_section, null, false);
                return true;
            } else if (itemID == R.id.exam) {
                setSectionFragment(new ExamsListFragment(), ((HomePageFragment) home_section).getListTopicBundle(), false);
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

    public Fragment getSectionFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible()) {
                return fragment;
            }
        }
        return null;
    }

    public void setSectionFragment(Fragment fragment, Bundle bundle, boolean isAddBackStack) {
        fragment.setArguments(bundle);
        String targetFragment = fragment.getClass().getSimpleName().trim().toLowerCase();
        switch (targetFragment) {
            case "topicpracticefragment":
                bottom_main_nav_view.getMenu().getItem(0).setChecked(true);
                break;
            case "userprofilefragment":
                bottom_main_nav_view.getMenu().getItem(4).setChecked(true);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(section_layout.getId(), fragment);
        if (isAddBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public Bundle getUserBundle() {
        return userBundle;
    }
}