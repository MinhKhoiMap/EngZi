package com.engzi.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.engzi.Adapter.TopicPracticeAdapter;
import com.engzi.Model.TopicPractice;
import com.engzi.R;

import java.util.ArrayList;

public class TopicPracticeActivity extends AppCompatActivity {
    ArrayList<TopicPractice> listTopic;
    TopicPracticeAdapter topicPracticeAdapter;
    RecyclerView topicPracticeRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_practice);

        listTopic = new ArrayList<>();
        listTopic.add(new TopicPractice("test", "test1", "test1",true));
        listTopic.add(new TopicPractice("test", "test2", "test2",false));
        listTopic.add(new TopicPractice("test", "test3", "test3",true));
        listTopic.add(new TopicPractice("test", "test4", "test4",false));
        listTopic.add(new TopicPractice("test", "test4", "test4",false));
        listTopic.add(new TopicPractice("test", "test4", "test4",false));
        listTopic.add(new TopicPractice("test", "haha", "test4",false));
        listTopic.add(new TopicPractice("test", "abc", "test4",true));
        listTopic.add(new TopicPractice("test", "test4", "test4",true));
        topicPracticeAdapter = new TopicPracticeAdapter();
        topicPracticeRecyclerView = findViewById(R.id.list_topic_practice);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        topicPracticeRecyclerView.setLayoutManager(linearLayoutManager);
        topicPracticeAdapter.setData(listTopic);
        topicPracticeRecyclerView.setAdapter(topicPracticeAdapter);
    }
}
