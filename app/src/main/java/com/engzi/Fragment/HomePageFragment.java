package com.engzi.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.engzi.Activities.MainActivity;
import com.engzi.Adapter.LessonPracticeAdapter;
import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.FlashCard;
import com.engzi.Model.LessonPractice;
import com.engzi.Model.User;
import com.engzi.R;
import com.engzi.Services.LessonServices;
import com.engzi.Services.NoteBookServices;
import com.engzi.Services.UserServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomePageFragment extends Fragment {
    MainActivity mMainActivity;
    User userProfile;
    List<LessonPractice> listLesson, recentlyLesson;
    List<FlashCard> flashCards;
    //    Adapter
    LessonPracticeAdapter lessonListViewAdapter, recentlyLearnViewAdapter;
    Bundle listTopicBundle;

    //    View
    View groupView;
    RecyclerView listViewLessonPractice,
            listViewRecentlyLessonPractice, recommend_lesson;
    TextView hello_home_txt, remind_english, remind_vowel, remind_translate, remind_example;
    CardView remind_layout;
    FloatingActionButton home_button;
    ConstraintLayout begin_learning_layout, recently_layout;
    LinearLayout lesson_practice_button, notebooks_button, exam_button;

    //    Services
    UserServices userServices = new UserServices();
    LessonServices lessonServices = new LessonServices();
    NoteBookServices noteBookServices = new NoteBookServices();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
        groupView = inflater.inflate(R.layout.fragment_home_page, container, false);
        listLesson = new ArrayList<>();
        recentlyLesson = new ArrayList<>();

        initUI();

        assert getArguments() != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            userProfile = getArguments().getSerializable("userProfile", User.class);
        } else {
            userProfile = (User) getArguments().getSerializable("userProfile");
        }

        hello_home_txt.setText("Hello " + userProfile.getProfileName() + "!");

        if (flashCards == null) {
            flashCards = new ArrayList<>();
            noteBookServices.getAllNotebookCard(new IServiceCallBack() {
                @Override
                public void retrieveData(Object response) {
                    flashCards.add((FlashCard) response);
                }

                @Override
                public void onFailed(Exception e) {

                }

                @Override
                public void onComplete() {
                    if (flashCards.size() > 0) {
                        remind_layout.setVisibility(View.VISIBLE);
                        Random random = new Random();
                        int positionRand = random.nextInt(flashCards.size());
                        FlashCard card = flashCards.get(positionRand);
                        remind_english.setText(card.getEnglish_word());
                        remind_vowel.setText(card.getVowel());
                        remind_translate.setText(card.getTranslate_word());
                        remind_example.setText("Ex: " + card.getExample());
                    } else {
                        remind_layout.setVisibility(View.INVISIBLE);
                    }
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
                LinearLayoutManager mDailyPracticeLayoutManager = new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.HORIZONTAL, false);
                LinearLayoutManager mStartingLearningLayoutManager = new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.HORIZONTAL, false);
                lessonListViewAdapter = new LessonPracticeAdapter(getActivity(), listLesson);

                listViewLessonPractice.setLayoutManager(mDailyPracticeLayoutManager);
                listViewLessonPractice.setAdapter(lessonListViewAdapter);


                listTopicBundle = new Bundle();
                listTopicBundle.putSerializable("lesson_practice", (Serializable) listLesson);


                ///////////////////////////////////////////
                if (listLesson.size() > 0) {
                    List<LessonPractice> startingLearning = new ArrayList<>();
                    startingLearning.add(listLesson.get(0));
                    LessonPracticeAdapter startingLearningAdapter = new LessonPracticeAdapter(getActivity(), startingLearning);

                    recommend_lesson.setLayoutManager(mStartingLearningLayoutManager);
                    recommend_lesson.setAdapter(startingLearningAdapter);
                }
            }

            @Override
            public void onFailed(Exception exception) {
                Log.w("lessons", "Error getting documents", exception);
            }
        });

        userServices.getRecentlyLessonList(new IServiceCallBack() {
            @Override
            public void retrieveData(Object response) {
                recentlyLesson.add((LessonPractice) response);
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("recently lessonsssss", e.getMessage());
            }

            @Override
            public void onComplete() {
                if (recentlyLesson.size() < 1) {
                    listViewRecentlyLessonPractice.setVisibility(View.INVISIBLE);
                    begin_learning_layout.setVisibility(View.VISIBLE);
                    ((ViewGroup) container.findViewById(R.id.main_layout)).removeView(remind_layout);
                } else {
                    listViewRecentlyLessonPractice.setVisibility(View.VISIBLE);
                    remind_layout.setVisibility(View.VISIBLE);
                    recently_layout.removeView(begin_learning_layout);

                    LinearLayoutManager mRecentlyLearnLayoutManager = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL, false);
                    recentlyLearnViewAdapter = new LessonPracticeAdapter(getActivity(), recentlyLesson);

                    listViewRecentlyLessonPractice.setLayoutManager(mRecentlyLearnLayoutManager);
                    listViewRecentlyLessonPractice.setAdapter(recentlyLearnViewAdapter);
                }
            }
        });

        lesson_practice_button.setOnClickListener(view -> {
            mMainActivity.setSectionFragment(new TopicListFragment(), null, false);
        });

        exam_button.setOnClickListener(view -> {
            mMainActivity.setSectionFragment(new ExamsListFragment(), listTopicBundle, false);
        });

        notebooks_button.setOnClickListener(view -> {
            mMainActivity.setSectionFragment(new BookMarkFragment(), null, false);
        });

        return groupView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (lessonListViewAdapter != null) {
            lessonListViewAdapter.release();
        }
        if (recentlyLearnViewAdapter != null) {
            recentlyLearnViewAdapter.release();
        }
    }


    private void initUI() {
        listViewLessonPractice = groupView.findViewById(R.id.list_daily_practice);
        listViewRecentlyLessonPractice = groupView.findViewById(R.id.list_daily_recently_learn);
        hello_home_txt = groupView.findViewById(R.id.hello_home_txt);
        home_button = groupView.findViewById(R.id.home_button);
        remind_english = groupView.findViewById(R.id.remind_english);
        remind_vowel = groupView.findViewById(R.id.remind_vowel);
        remind_translate = groupView.findViewById(R.id.remind_translate);
        remind_example = groupView.findViewById(R.id.remind_example);
        recommend_lesson = groupView.findViewById(R.id.recommend_lesson);
        remind_layout = groupView.findViewById(R.id.remind_layout);
        begin_learning_layout = groupView.findViewById(R.id.begin_learning_layout);
        lesson_practice_button = groupView.findViewById(R.id.lesson_practice_button);
        notebooks_button = groupView.findViewById(R.id.notebooks_button);
        exam_button = groupView.findViewById(R.id.exam_button);
        recently_layout = groupView.findViewById(R.id.recently_layout);
    }

    public Bundle getListTopicBundle() {
        return listTopicBundle;
    }
}