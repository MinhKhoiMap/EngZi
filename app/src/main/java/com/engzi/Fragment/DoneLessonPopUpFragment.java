package com.engzi.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.engzi.Activities.MainActivity;
import com.engzi.R;

public class DoneLessonPopUpFragment extends Fragment {
    ImageButton navigate_home_button;
    AppCompatButton next_lesson_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_done_lesson_pop_up, container, false);
        navigate_home_button = view.findViewById(R.id.navigate_home_button);
        next_lesson_button = view.findViewById(R.id.next_lesson_button);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View currentView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(currentView, savedInstanceState);

        navigate_home_button.setOnClickListener(view -> {
            navigate_home_button.setAlpha(0.5f);
            Intent homeIntent = new Intent(view.getContext(), MainActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        });

        next_lesson_button.setOnClickListener(view -> {
//            Navigate to list lesson
        });
    }
}