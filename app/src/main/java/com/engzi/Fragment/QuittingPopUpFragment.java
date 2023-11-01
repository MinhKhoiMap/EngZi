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

import com.engzi.Activities.HomeActivity;
import com.engzi.R;

public class QuittingPopUpFragment extends Fragment {
    AppCompatButton quitting_learn_button, continue_learn_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quitting_pop_up, container, false);

        continue_learn_button = view.findViewById(R.id.continue_learn_button);
        quitting_learn_button = view.findViewById(R.id.quitting_learn_button);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View currentView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(currentView, savedInstanceState);

        quitting_learn_button.setOnClickListener(view -> {
            quitting_learn_button.setAlpha(0.8f);
            Intent homeIntent = new Intent(getContext(), HomeActivity.class);
            startActivity(homeIntent);
        });

        continue_learn_button.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            ((View) currentView.getParent()).setVisibility(View.INVISIBLE);
        });
    }
}