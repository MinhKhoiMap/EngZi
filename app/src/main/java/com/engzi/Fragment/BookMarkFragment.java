package com.engzi.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.engzi.Activities.MainActivity;
import com.engzi.Adapter.ListWordAdapter;
import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.FlashCard;
import com.engzi.R;
import com.engzi.Services.NoteBookServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class BookMarkFragment extends Fragment {
    MainActivity mMainActivity;
    List<FlashCard> bookmarkList = new ArrayList<>();

    //    View
    View groupView;
    RecyclerView list_words;
    FrameLayout word_detail_pop_up;
    Toolbar bookmark_toolbar;

    //    Services
    NoteBookServices noteBookServices = new NoteBookServices();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        groupView = inflater.inflate(R.layout.fragment_book_mark, container, false);

        mMainActivity = (MainActivity) getActivity();

        initUI();

        noteBookServices.getNotebookCardOrderByLevel(1, new IServiceCallBack() {
            @Override
            public void retrieveData(Object response) {
                bookmarkList.add((FlashCard) response);
            }

            @Override
            public void onFailed(Exception e) {

            }

            @Override
            public void onComplete() {
                LinearLayoutManager mLinearLayoutManager =
                        new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                ListWordAdapter listWordAdapter = new ListWordAdapter(bookmarkList, (position) -> {
                    View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_up_layout, null);
                    TextView english_word = popupView.findViewById(R.id.english_word),
                            word_vowel = popupView.findViewById(R.id.word_vowel),
                            word_translate = popupView.findViewById(R.id.word_translate),
                            word_example = popupView.findViewById(R.id.word_example);

                    FlashCard clickedWord = bookmarkList.get(position);

                    english_word.setText(clickedWord.getEnglish_word());
                    word_vowel.setText(clickedWord.getVowel());
                    word_translate.setText(clickedWord.getTranslate_word());
                    word_example.setText("Ex: " + clickedWord.getExample());

                    if (word_detail_pop_up.getChildCount() > 0) {
                        word_detail_pop_up.removeAllViews();
                    }

                    word_detail_pop_up.addView(popupView);

                    popupView.findViewById(R.id.close_modal).setOnClickListener(view -> {
                        word_detail_pop_up.removeView(popupView);
                    });
                    list_words.setClickable(false);
                });

                list_words.setLayoutManager(mLinearLayoutManager);
                list_words.setAdapter(listWordAdapter);
            }
        });

        bookmark_toolbar.setNavigationOnClickListener(view -> {
            ((BottomNavigationView) mMainActivity.findViewById(R.id.bottom_main_nav_view))
                    .getMenu().getItem(2).setChecked(true);
            mMainActivity.setSectionFragment(new HomePageFragment(), mMainActivity.getUserBundle(), false);

        });

        return groupView;
    }

    private void initUI() {
        list_words = groupView.findViewById(R.id.list_words);
        word_detail_pop_up = groupView.findViewById(R.id.word_detail_pop_up);
        bookmark_toolbar = groupView.findViewById(R.id.bookmark_toolbar);
    }
}