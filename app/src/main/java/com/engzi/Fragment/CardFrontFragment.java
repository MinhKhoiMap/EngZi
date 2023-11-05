package com.engzi.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.engzi.Model.FlashCard;
import com.engzi.R;
import com.squareup.picasso.Picasso;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CardFrontFragment extends Fragment {
    String cover_image_url;
    String example_english;

    //    View
    ImageView lesson_image;
    TextView lesson_example_english;

    public CardFrontFragment(String cover_image_url, String example_english) {
        this.cover_image_url = cover_image_url;
        this.example_english = example_english;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_front, container, false);
        float scale = getContext().getResources().getDisplayMetrics().density;
        view.findViewById(R.id.card_front).setCameraDistance(8000 * scale);

        lesson_image = view.findViewById(R.id.lesson_image);
        lesson_example_english = view.findViewById(R.id.lesson_example_english);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View currentView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(currentView, savedInstanceState);

        Glide.with(getActivity()).load(cover_image_url)
                .placeholder(R.drawable.crying_quitting)
                .transition(withCrossFade())
                .into(lesson_image);
        lesson_example_english.setText(example_english);
    }
}