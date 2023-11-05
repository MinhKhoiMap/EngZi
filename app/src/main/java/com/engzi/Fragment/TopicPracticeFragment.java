package com.engzi.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.engzi.Adapter.TopicPracticeAdapter;
import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.LessonPractice;
import com.engzi.Model.TopicPractice;
import com.engzi.R;
import com.engzi.Services.LessonServices;

import java.util.ArrayList;
import java.util.List;

public class TopicPracticeFragment extends Fragment {
    List<LessonPractice> listTopic = new ArrayList<>();
    //    View
    View groupView;
    TopicPracticeAdapter topicPracticeAdapter;
    RecyclerView topicPracticeRecyclerView;

    //    Services
    LessonServices lessonServices = new LessonServices();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        groupView = inflater.inflate(R.layout.fragment_topic_practice, container, false);

        initUI();

        lessonServices.getAllLessonList(new IServiceCallBack() {
            @Override
            public void retrieveData(Object response) {
                listTopic.add((LessonPractice) response);
            }

            @Override
            public void onFailed(Exception e) {

            }

            @Override
            public void onComplete() {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                topicPracticeAdapter = new TopicPracticeAdapter();
                topicPracticeRecyclerView.setLayoutManager(linearLayoutManager);
                topicPracticeAdapter.setData(listTopic);
                topicPracticeRecyclerView.setAdapter(topicPracticeAdapter);
            }
        });

        return groupView;
    }

    private void initUI() {
        topicPracticeRecyclerView = groupView.findViewById(R.id.list_topic_practice);
    }
}