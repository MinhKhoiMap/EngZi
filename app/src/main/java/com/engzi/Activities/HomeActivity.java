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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    List<LessonPractice> listLesson = new ArrayList<>();
    List<LessonPractice> recentlyLesson = new ArrayList<>();
    LessonPracticeAdapter lessonListViewAdapter;
    LessonPracticeAdapter recentlyLearnViewAdapter;
    RecyclerView listViewLessonPractice;
    RecyclerView listViewRecentlyLessonPractice;
    BottomNavigationView bottom_main_nav_view;
    TextView hello_home_txt;
    FloatingActionButton home_button;

    User userProfile;

    //    Services
    UserServices userServices = new UserServices();
    LessonServices lessonServices = new LessonServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        initUI();

        Intent userProfileIntent = getIntent();
        Toast.makeText(this, Objects.requireNonNull(FireBaseUtil.mAuth.getCurrentUser()).getUid(), Toast.LENGTH_SHORT).show();

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
                lessonListViewAdapter = new LessonPracticeAdapter(HomeActivity.this, listLesson);

                listViewLessonPractice.setLayoutManager(mDailyPracticeLayoutManager);
                listViewLessonPractice.setAdapter(lessonListViewAdapter);
            }

            @Override
            public void onFailed(Exception exception) {
                Log.w("lessons", "Error getting documents", exception);
            }
        });

        home_button.setOnClickListener(view -> {
            bottom_main_nav_view.getMenu().getItem(2).setChecked(true);
            Intent homeActivity = new Intent(HomeActivity.this, HomeActivity.class);
            homeActivity.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(homeActivity);
        });
    }

    private void initUI() {
        listViewLessonPractice = findViewById(R.id.list_daily_practice);
        listViewRecentlyLessonPractice = findViewById(R.id.list_daily_recently_learn);
        bottom_main_nav_view = findViewById(R.id.bottom_main_nav_view);
        hello_home_txt = findViewById(R.id.hello_home_txt);
        home_button = findViewById(R.id.home_button);

        bottom_main_nav_view.setBackground(null);
        bottom_main_nav_view.getMenu().getItem(2).setEnabled(false);
        bottom_main_nav_view.getMenu().getItem(2).setChecked(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        userServices.getRecentlyLesson(new IServiceCallBack() {
            @Override
            public void retrieveData(Object response) {
                Log.d("recently lessonsssss", ((LessonPractice) response).getTopic_name());
                recentlyLesson.add(0, (LessonPractice) response);
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("recently lessonsssss", e.getMessage());
            }

            @Override
            public void onComplete() {
                LinearLayoutManager mRecentlyLearnLayoutManager = new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                recentlyLearnViewAdapter = new LessonPracticeAdapter(HomeActivity.this, recentlyLesson);

                listViewRecentlyLessonPractice.setLayoutManager(mRecentlyLearnLayoutManager);
                listViewRecentlyLessonPractice.setAdapter(recentlyLearnViewAdapter);
                Log.d("TAG12313", "onComplete:242424563646");
            }
        });
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
