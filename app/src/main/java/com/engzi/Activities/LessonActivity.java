package com.engzi.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.engzi.Model.FlashCard;
import com.engzi.Model.LessonPractice;
import com.engzi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class LessonActivity extends AppCompatActivity {
    final int SWIPE_THRESHOLD = 100;
    final int SWIPE_VELOCITY_THRESHOLD = 100;

    Toolbar toolbar;
    BottomNavigationView bottom_main_nav_view;
    LinearLayout card_front, card_back;
    ConstraintLayout main_learn_layout;

    //    Event Management
    GestureDetector gestureDetector;

    List<FlashCard> flashCardList;
    LessonPractice lessonPractice;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            lessonPractice = getIntent().getSerializableExtra("lesson", LessonPractice.class);
        } else {
            lessonPractice = (LessonPractice) getIntent().getSerializableExtra("lesson");
        }

        Log.d("lesson", lessonPractice.getLessonID());

        gestureDetector = new GestureDetector(this, new MyGesture());
        initUI();


        toolbar.setNavigationOnClickListener(view -> {
            Toast.makeText(getBaseContext(), "go back", Toast.LENGTH_SHORT).show();
            onBackPressed();
            finish();
        });

        main_learn_layout.setOnTouchListener((view, motionEvent) -> {
            gestureDetector.onTouchEvent(motionEvent);
            return true;
        });
        handleFlipCard();
    }

    private void initUI() {
        toolbar = findViewById(R.id.lesson_toolbar);
        bottom_main_nav_view = findViewById(R.id.bottom_main_nav_view);
        card_front = findViewById(R.id.card_front);
        card_back = findViewById(R.id.card_back);
        main_learn_layout = findViewById(R.id.main_learn_layout);


        bottom_main_nav_view.setBackground(null);
        bottom_main_nav_view.getMenu().getItem(2).setEnabled(false);
    }

    private void handleFlipCard() {
        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        card_front.setCameraDistance(8000 * scale);
        card_back.setCameraDistance(8000 * scale);

        AnimatorSet front_anim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.front_card_animator);
        AnimatorSet back_anim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.back_card_animator);
        card_front.setOnClickListener(view -> {
            front_anim.setTarget(card_front);
            back_anim.setTarget(card_back);
            front_anim.start();
            back_anim.start();
        });

        card_back.setOnClickListener(view -> {
            front_anim.setTarget(card_back);
            back_anim.setTarget(card_front);
            front_anim.start();
            back_anim.start();
        });
    }

    class MyGesture extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
//            FLing from right to left
            if (e1.getX() - e2.getX() > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                Toast.makeText(getBaseContext(), "FLing from right to left", Toast.LENGTH_SHORT).show();
            }
//            FLing from left to right
            if (e2.getX() - e1.getX() > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                Toast.makeText(getBaseContext(), "FLing from left to right", Toast.LENGTH_SHORT).show();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

}