package com.engzi.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.engzi.Adapter.LessonPracticeAdapter;
import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.LessonPractice;
import com.engzi.Model.User;
import com.engzi.R;
import com.engzi.Services.LessonServices;
import com.engzi.Services.UserServices;
import com.engzi.Utils.FireBaseUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    List<LessonPractice> listLesson = new ArrayList<>();
    LessonPracticeAdapter lessonListViewAdapter;
    LessonPracticeAdapter recentlyLearnViewAdapter;
    RecyclerView listViewLessonPractice;
    RecyclerView listViewRecentlyLessonPractice;
    BottomNavigationView bottom_main_nav_view;
    TextView hello_home_txt;

    User userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        initUI();
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
            userServices.getUserById(FireBaseUtil.mAuth.getCurrentUser().getUid(), new IServiceCallBack() {
                @Override
                public void retrieveData(Object response) {
                    userProfile = (User) response;
                    userProfile.setEmail(FireBaseUtil.mAuth.getCurrentUser().getEmail());
                    userProfile.setUID(FireBaseUtil.mAuth.getCurrentUser().getUid());
                }

                @Override
                public void onFailed(Exception e) {
                    Log.w("users", "Error get user by ID", e);
                }

                @Override
                public void onComplete() {
                    hello_home_txt.setText("Hello " + userProfile.getProfileName());
                }
            });
        }

        lessonServices.getAllLessonList(new IServiceCallBack() {
            @Override
            public void retrieveData(Object response) {
                listLesson.add((LessonPractice) response);
//                Log.d("lesson", ((LessonPractice) response).getTopic_name() + ((LessonPractice) response).getLesson_name());
            }

            @Override
            public void onComplete() {
                LinearLayoutManager mDailyPracticeLayoutManager = new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                LinearLayoutManager mRecentlyLearnLayoutManager = new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.HORIZONTAL, false);

                Log.d("TAG", listLesson.get(listLesson.size() - 1).getTopic_name());

                recentlyLearnViewAdapter = new LessonPracticeAdapter(HomeActivity.this, listLesson);
                lessonListViewAdapter = new LessonPracticeAdapter(HomeActivity.this, listLesson);

                /////////////////////////////////////////////////////////////////////////////
                listViewLessonPractice.setLayoutManager(mDailyPracticeLayoutManager);
                listViewRecentlyLessonPractice.setLayoutManager(mRecentlyLearnLayoutManager);

                listViewLessonPractice.setAdapter(lessonListViewAdapter);
                listViewRecentlyLessonPractice.setAdapter(recentlyLearnViewAdapter);
            }

            @Override
            public void onFailed(Exception exception) {
                Log.w("lessons", "Error getting documents", exception);
            }
        });

    }

    private void initUI() {
        listViewLessonPractice = findViewById(R.id.list_daily_practice);
        listViewRecentlyLessonPractice = findViewById(R.id.list_daily_recently_learn);
        bottom_main_nav_view = findViewById(R.id.bottom_main_nav_view);
        hello_home_txt = findViewById(R.id.hello_home_txt);

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
