package com.engzi.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.engzi.Adapter.LessonPracticeAdapter;
import com.engzi.Model.LessonPractice;
import com.engzi.Model.User;
import com.engzi.R;
import com.engzi.Services.LessonServices;
import com.engzi.Services.UserServices;
import com.engzi.Utils.FireBaseUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    ArrayList<LessonPractice> listLesson;
    LessonPracticeAdapter lessonListViewAdapter;
    LessonPracticeAdapter recentlyLearnViewAdapter;
    RecyclerView listViewLessonPractice;
    RecyclerView listViewRecentlyLessonPractice;
    BottomNavigationView bottom_main_nav_view;

    User userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        initUI();

        List<String> card = new ArrayList<>();

        listLesson = new ArrayList<>();
        listLesson.add(new LessonPractice("GBARWVdN2SrcXeLtJr89", "abcc", "abcc1", "abcc12", card, 2));
        listLesson.add(new LessonPractice("0z0H3y0MKNtGTmGGUcbx", "abcc", "abcc1", "abcc12", card, 2));


        LinearLayoutManager mDailyPracticeLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager mRecentlyLearnLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false);

        recentlyLearnViewAdapter = new LessonPracticeAdapter(HomeActivity.this, listLesson);
        lessonListViewAdapter = new LessonPracticeAdapter(HomeActivity.this, listLesson);

        /////////////////////////////////////////////////////////////////////////////
        listViewLessonPractice.setLayoutManager(mDailyPracticeLayoutManager);
        listViewRecentlyLessonPractice.setLayoutManager(mRecentlyLearnLayoutManager);

        listViewLessonPractice.setAdapter(lessonListViewAdapter);
        listViewRecentlyLessonPractice.setAdapter(recentlyLearnViewAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        LessonServices lessonServices = new LessonServices();
        UserServices userServices = new UserServices();

        Intent userProfileIntent = getIntent();
        Toast.makeText(this, Objects.requireNonNull(FireBaseUtil.mAuth.getCurrentUser()).getUid(), Toast.LENGTH_SHORT).show();

        if (userProfileIntent.getSerializableExtra("userProfile") != null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                userProfile = userProfileIntent.getSerializableExtra("userProfile", User.class);
            } else {
                userProfile = (User) userProfileIntent.getSerializableExtra("userProfile");
            }
        } else {
            userServices.getUserById(FireBaseUtil.mAuth.getCurrentUser().getUid());
        }

    }

    private void initUI() {
        listViewLessonPractice = findViewById(R.id.list_daily_practice);
        listViewRecentlyLessonPractice = findViewById(R.id.list_daily_recently_learn);
        bottom_main_nav_view = findViewById(R.id.bottom_main_nav_view);

        bottom_main_nav_view.setBackground(null);
        bottom_main_nav_view.getMenu().getItem(2).setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (lessonListViewAdapter != null) {
            lessonListViewAdapter.release();
        }
        if (recentlyLearnViewAdapter != null) {
            recentlyLearnViewAdapter.release();
        }
    }
}
