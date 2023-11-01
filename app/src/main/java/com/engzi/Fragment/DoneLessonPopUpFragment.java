package com.engzi.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.engzi.Activities.HomeActivity;
import com.engzi.Activities.LessonActivity;
import com.engzi.R;

public class DoneLessonPopUpFragment extends Fragment {
    ImageButton navigate_home_button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_done_lesson_pop_up, container, false);
        navigate_home_button = view.findViewById(R.id.navigate_home_button);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View currentView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(currentView, savedInstanceState);

        navigate_home_button.setOnClickListener(view -> {
            navigate_home_button.setAlpha(0.5f);
            Intent homeIntent = new Intent(getContext(), HomeActivity.class);
            startActivity(homeIntent);
        });
    }
}