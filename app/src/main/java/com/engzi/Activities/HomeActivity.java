package com.engzi.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.engzi.Adapter.LessonPracticeAdapter;
import com.engzi.Model.LessionPractice;
import com.engzi.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ArrayList<LessionPractice> listLesson;
    LessonPracticeAdapter lessonListViewAdapter;
    LessonPracticeAdapter lessonTitleListViewAdapter;
    RecyclerView listViewLessonPractice;
    RecyclerView listViewLessonPractice1;

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        mFirebaseAuth = FirebaseAuth.getInstance();

        Toast.makeText(this, Objects.requireNonNull(mFirebaseAuth.getCurrentUser()).getUid(), Toast.LENGTH_SHORT).show();

        listLesson = new ArrayList<>();
        listLesson.add(new LessionPractice("123", "abcc", "abcc1", "abcc12", 2));
        listLesson.add(new LessionPractice("123", "abcc", "abcc1", "abcc12", 2));
        listLesson.add(new LessionPractice("123", "abcc", "abcc1", "abcc12", 2));
        listViewLessonPractice = findViewById(R.id.list_daily_practice);
        listViewLessonPractice1 = findViewById(R.id.list_daily_practice_two);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        mLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        listViewLessonPractice.setLayoutManager(mLayoutManager);
        listViewLessonPractice1.setLayoutManager(mLayoutManager1);
        lessonListViewAdapter = new LessonPracticeAdapter(listLesson);
        listViewLessonPractice.setAdapter(lessonListViewAdapter);
        listViewLessonPractice1.setAdapter(lessonListViewAdapter);
    }
}
