package com.engzi.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.engzi.Activities.MainActivity;
import com.engzi.Adapter.ExamsAdapter;
import com.engzi.Model.LessonPractice;
import com.engzi.R;
import com.engzi.Services.ExamsServices;
import com.engzi.Services.NoteBookServices;

import java.util.ArrayList;
import java.util.List;

public class ExamsListFragment extends Fragment {
    MainActivity mMainActivity;
    List<LessonPractice> listExamTopic;
    //    View
    View groupView;
    Toolbar exams_list_toolbar;
    RecyclerView exams_list;

    //    Adapter
    ExamsAdapter examsAdapter;

    //    Services
    ExamsServices examsServices = new ExamsServices();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        groupView = inflater.inflate(R.layout.fragment_exams_list, container, false);
        mMainActivity = (MainActivity) getActivity();

        listExamTopic = (List<LessonPractice>) getArguments().get("lesson_practice");


        initUI();

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        examsAdapter = new ExamsAdapter(listExamTopic);

        exams_list.setLayoutManager(mLinearLayoutManager);
        exams_list.setAdapter(examsAdapter);

        exams_list_toolbar.setNavigationOnClickListener(view -> {
            mMainActivity.setSectionFragment(new HomePageFragment(), mMainActivity.getUserBundle(), false);
        });

        return groupView;
    }

    private void initUI() {
        exams_list_toolbar = groupView.findViewById(R.id.exams_list_toolbar);
        exams_list = groupView.findViewById(R.id.exams_list);
    }
}