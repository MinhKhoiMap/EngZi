package com.engzi.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.engzi.Adapter.LessonPracticeAdapter;
import com.engzi.Model.LessonPractice;


import java.util.ArrayList;
import com.engzi.R;
public class WelcomeActivity extends AppCompatActivity {

    ArrayList<LessonPractice> listLesson;
    LessonPracticeAdapter lessonListViewAdapter;
    LessonPracticeAdapter lessonTitleListViewAdapter;
    RecyclerView listViewLessonPractice;
    RecyclerView listViewLessonPractice1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        listLesson = new ArrayList<>();
        listLesson.add(new LessonPractice("abcc", "abcc1", "abcc12"));
        listLesson.add(new LessonPractice("abcc", "abcc12", "abcc123"));
        listLesson.add(new LessonPractice("abcc", "abcc21", "abcc1245"));
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