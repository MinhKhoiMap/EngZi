package com.engzi.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.engzi.Activities.MainActivity;
import com.engzi.Adapter.TopicListAdapter;
import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.LessonPractice;
import com.engzi.R;
import com.engzi.Services.LessonServices;
import com.engzi.Services.UserServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class TopicListFragment extends Fragment {
    MainActivity mMainActivity;
    List<LessonPractice> listTopic;
    //    View
    View groupView;
    RecyclerView topicPracticeRecyclerView;
    Toolbar topic_toolbar;

    //    Adapter
    TopicListAdapter topicPracticeAdapter;

    //    Services
    LessonServices lessonServices = new LessonServices();
    UserServices userServices = new UserServices();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        groupView = inflater.inflate(R.layout.fragment_topic_list, container, false);
        mMainActivity = (MainActivity) getActivity();
        listTopic = new ArrayList<>();

        initUI();

        lessonServices.getAllLessonList(new IServiceCallBack() {
            @Override
            public void retrieveData(Object response) {
                LessonPractice data = (LessonPractice) response;
                userServices.getLastPositionCardLesson(data.getLessonID(), new IServiceCallBack() {
                    @Override
                    public void retrieveData(Object response) {
                        if (response != null) {
//                            Log.d("data", data.getTopic_name());
//                            Log.d("lesson list", String.valueOf(response));
                            long res = (long) response;
                            data.setLast_position_card((int) res);
                        }
                        listTopic.add(data);
                    }

                    @Override
                    public void onFailed(Exception e) {

                    }

                    @Override
                    public void onComplete() {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        topicPracticeAdapter = new TopicListAdapter();
                        topicPracticeRecyclerView.setLayoutManager(linearLayoutManager);
                        listTopic.sort((t1, t2) -> t1.getLesson_name().compareToIgnoreCase(t2.getLesson_name()));
                        topicPracticeAdapter.setData(listTopic);
                        topicPracticeRecyclerView.setAdapter(topicPracticeAdapter);
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {

            }

            @Override
            public void onComplete() {

            }
        });

        topic_toolbar.setNavigationOnClickListener(view -> {
            ((BottomNavigationView) mMainActivity.findViewById(R.id.bottom_main_nav_view))
                    .getMenu().getItem(2).setChecked(true);
            mMainActivity.setSectionFragment(new HomePageFragment(), mMainActivity.getUserBundle(), false);
        });


        return groupView;
    }

    private void initUI() {
        topicPracticeRecyclerView = groupView.findViewById(R.id.list_topic_practice);
        topic_toolbar = groupView.findViewById(R.id.topic_toolbar);
    }
}