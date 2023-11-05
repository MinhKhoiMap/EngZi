package com.engzi.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.engzi.Adapter.LessonPracticeAdapter;
import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.FlashCard;
import com.engzi.Model.LessonPractice;
import com.engzi.Model.User;
import com.engzi.R;
import com.engzi.Services.LessonServices;
import com.engzi.Services.NoteBookServices;
import com.engzi.Services.UserServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomePageFragment extends Fragment {
    User userProfile;
    List<LessonPractice> listLesson;
    List<LessonPractice> recentlyLesson;
    List<FlashCard> flashCards;
    //    Adapter
    LessonPracticeAdapter lessonListViewAdapter;
    LessonPracticeAdapter recentlyLearnViewAdapter;

    //    View
    View groupView;
    RecyclerView listViewLessonPractice;
    RecyclerView listViewRecentlyLessonPractice;
    BottomNavigationView bottom_main_nav_view;
    TextView hello_home_txt, remind_english, remind_vowel, remind_translate, remind_example;
    FloatingActionButton home_button;

    //    Services
    UserServices userServices = new UserServices();
    LessonServices lessonServices = new LessonServices();
    NoteBookServices noteBookServices = new NoteBookServices();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
            noteBookServices.getNotebookCardOrderByLevel(1, new IServiceCallBack() {
                @Override
                public void retrieveData(Object response) {
                    flashCards.add((FlashCard) response);
                }

                @Override
                public void onFailed(Exception e) {

                }

                @Override
                public void onComplete() {
                    Random random = new Random();
                    int positionRand = random.nextInt(flashCards.size());
                    FlashCard card = flashCards.get(positionRand);
                    remind_english.setText(card.getEnglish_word());
                    remind_vowel.setText(card.getVowel());
                    remind_translate.setText(card.getTranslate_word());
                    remind_example.setText("Ex: " + card.getExample());
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
                lessonListViewAdapter = new LessonPracticeAdapter(getActivity(), listLesson);

                listViewLessonPractice.setLayoutManager(mDailyPracticeLayoutManager);
                listViewLessonPractice.setAdapter(lessonListViewAdapter);
            }

            @Override
            public void onFailed(Exception exception) {
                Log.w("lessons", "Error getting documents", exception);
            }
        });

        userServices.getRecentlyLesson(new IServiceCallBack() {
            @Override
            public void retrieveData(Object response) {
                Log.d("recently lessonsssss", ((LessonPractice) response).getTopic_name());
                recentlyLesson.add((LessonPractice) response);
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("recently lessonsssss", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("TAG12313", "onComplete: " + recentlyLesson.size());
                if (recentlyLesson.size() < 1) {
                    Toast.makeText(getActivity(), "Chua co hoc gi het", Toast.LENGTH_SHORT).show();
                } else {
                    LinearLayoutManager mRecentlyLearnLayoutManager = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL, false);
                    recentlyLearnViewAdapter = new LessonPracticeAdapter(getActivity(), recentlyLesson);

                    listViewRecentlyLessonPractice.setLayoutManager(mRecentlyLearnLayoutManager);
                    listViewRecentlyLessonPractice.setAdapter(recentlyLearnViewAdapter);

                }
            }
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
        bottom_main_nav_view = groupView.findViewById(R.id.bottom_main_nav_view);
        hello_home_txt = groupView.findViewById(R.id.hello_home_txt);
        home_button = groupView.findViewById(R.id.home_button);
        remind_english = groupView.findViewById(R.id.remind_english);
        remind_vowel = groupView.findViewById(R.id.remind_vowel);
        remind_translate = groupView.findViewById(R.id.remind_translate);
        remind_example = groupView.findViewById(R.id.remind_example);
    }
}