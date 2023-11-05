package com.engzi.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.engzi.R;

public class CardBackFragment extends Fragment {
    String english_word;
    String vowel;
    String translate_word;

    //    View
    TextView lesson_english_word, lesson_vowel, lesson_translate_word;

    public CardBackFragment(String english_word, String vowel, String translate_word) {
        this.english_word = english_word;
        this.vowel = vowel;
        this.translate_word = translate_word;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_back, container, false);
        float scale = getContext().getResources().getDisplayMetrics().density;
        view.findViewById(R.id.card_back).setCameraDistance(8000 * scale);

        lesson_english_word = view.findViewById(R.id.lesson_english_word);
        lesson_vowel = view.findViewById(R.id.lesson_vowel);
        lesson_translate_word = view.findViewById(R.id.lesson_translate_word);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lesson_translate_word.setText(translate_word);
        lesson_vowel.setText(vowel);
        lesson_english_word.setText(english_word);
    }
}