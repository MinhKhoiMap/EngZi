package com.engzi.Activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.engzi.Fragment.DoneLessonPopUpFragment;
import com.engzi.Fragment.QuittingPopUpFragment;
import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.FlashCard;
import com.engzi.Model.LessonPractice;
import com.engzi.R;
import com.engzi.Services.FlashCardServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class LessonActivity extends AppCompatActivity {
    final int SWIPE_THRESHOLD = 100;
    final int SWIPE_VELOCITY_THRESHOLD = 100;
    final int CLICK_THREHOLD = 80;
    final List<FlashCard> flashCardList = new ArrayList<>();

    //    Variants Utils
    int positionCard;
    LessonPractice lessonPractice;


    /////////////////////////////////////////////////////////////
    Toolbar toolbar;
    BottomNavigationView bottom_main_nav_view;
    FloatingActionButton home_button;
    ImageView lesson_image;
    TextView lesson_example_english, lesson_english_word, lesson_vowel, lesson_translate_word;
    ProgressBar lesson_progress_bar;

    //    Layout
    LinearLayout card_front, card_back, main_learn_layout;
    FrameLayout pop_up_modal;

    //    Fragment
    Fragment DoneLessonPopUpFragment = new DoneLessonPopUpFragment(),
            QuittingPopUpFragment = new QuittingPopUpFragment();

    //    Event Management
    GestureDetector gestureDetector;

    //    Services
    FlashCardServices flashCardServices = new FlashCardServices();



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

        getListCard();

        gestureDetector = new GestureDetector(this, new MyGesture());
        initUI();

        ((TextView) toolbar.findViewById(R.id.lesson_title)).setText("Topic: " + lessonPractice.getTopic_name());

        toolbar.setNavigationOnClickListener(view -> {
            handleBackPress();
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                handleBackPress();
            }
        });

        main_learn_layout.setOnTouchListener((view, motionEvent) -> {
            gestureDetector.onTouchEvent(motionEvent);
            return true;
        });

        card_front.setOnTouchListener((view, motionEvent) -> {
            long duration = motionEvent.getEventTime() - motionEvent.getDownTime();

            if (motionEvent.getAction() == MotionEvent.ACTION_UP && duration < CLICK_THREHOLD) {
                Toast.makeText(this, "click event", Toast.LENGTH_SHORT).show();
                handleFlipCard(card_front, card_back);
            } else {
                gestureDetector.onTouchEvent(motionEvent);
            }
            return true;
        });

        card_back.setOnTouchListener((view, motionEvent) -> {
            long duration = motionEvent.getEventTime() - motionEvent.getDownTime();

            if (motionEvent.getAction() == MotionEvent.ACTION_UP && duration < CLICK_THREHOLD) {
                Toast.makeText(this, "click event", Toast.LENGTH_SHORT).show();
                handleFlipCard(card_back, card_front);
            } else {
                gestureDetector.onTouchEvent(motionEvent);
            }
            return true;
        });

        home_button.setOnClickListener(view -> {
            bottom_main_nav_view.getMenu().getItem(2).setChecked(true);
            Intent homeActivity = new Intent(LessonActivity.this, HomeActivity.class);
            homeActivity.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(homeActivity);
        });
    }

    private void handleBackPress() {
        if (positionCard < flashCardList.size()) {
            pop_up_modal.setVisibility(View.VISIBLE);
            setFragment(QuittingPopUpFragment);
        } else {
            onBackPressed();
            finish();
        }
    }

    private void initUI() {
        toolbar = findViewById(R.id.lesson_toolbar);
        bottom_main_nav_view = findViewById(R.id.bottom_main_nav_view);
        card_front = findViewById(R.id.card_front);
        card_back = findViewById(R.id.card_back);
        main_learn_layout = findViewById(R.id.main_learn_layout);
        lesson_image = findViewById(R.id.lesson_image);
        lesson_example_english = findViewById(R.id.lesson_example_english);
        lesson_english_word = findViewById(R.id.lesson_english_word);
        lesson_vowel = findViewById(R.id.lesson_vowel);
        lesson_translate_word = findViewById(R.id.lesson_translate_word);
        lesson_progress_bar = findViewById(R.id.lesson_progress_bar);
        pop_up_modal = findViewById(R.id.pop_up_modal);
        home_button = findViewById(R.id.home_button);

        bottom_main_nav_view.setBackground(null);
        bottom_main_nav_view.getMenu().getItem(2).setEnabled(false);
        bottom_main_nav_view.getMenu().getItem(2).setChecked(true);
    }

    private void getListCard() {
        for (String cardID : lessonPractice.getList_cards()) {
            Log.d("Card ID", cardID);
            flashCardServices.getCardByID(cardID, new IServiceCallBack() {
                @Override
                public void retrieveData(Object response) {
                    flashCardList.add((FlashCard) response);
                }

                @Override
                public void onFailed(Exception e) {

                }

                @Override
                public void onComplete() {
                    positionCard = 0;
                    FlashCard currentCard = flashCardList.get(positionCard);
                    Picasso.get().load(currentCard.getCover_image_url()).into(lesson_image);
                    lesson_example_english.setText(currentCard.getEnglish_word());
                    lesson_english_word.setText(currentCard.getEnglish_word());
                    lesson_vowel.setText(currentCard.getVowel());
                    lesson_translate_word.setText(currentCard.getTranslate_word());
                }
            });
        }
    }

    private void handleChangeCard() {
        Picasso.get().load(flashCardList.get(positionCard).getCover_image_url()).into(lesson_image);
        lesson_example_english.setText(flashCardList.get(positionCard).getEnglish_word());
        lesson_english_word.setText(flashCardList.get(positionCard).getEnglish_word());
        lesson_vowel.setText(flashCardList.get(positionCard).getVowel());
        lesson_translate_word.setText(flashCardList.get(positionCard).getTranslate_word());
    }


    private void handleFlipCard(LinearLayout card_front, LinearLayout card_back) {
        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        card_front.setCameraDistance(8000 * scale);
        card_back.setCameraDistance(8000 * scale);

        AnimatorSet front_anim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.front_card_animator);
        AnimatorSet back_anim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.back_card_animator);

        front_anim.setTarget(card_front);
        back_anim.setTarget(card_back);
        front_anim.start();
        back_anim.start();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(pop_up_modal.getId(), fragment);
        fragmentTransaction.commit();
    }

    class MyGesture extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
//            FLing from right to left
            if (e1.getX() - e2.getX() > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (positionCard < flashCardList.size() - 1) {
                    positionCard += 1;
                    handleChangeCard();
                    lesson_progress_bar.setProgress((positionCard + 1));
                    lesson_progress_bar.setMax(flashCardList.size());
                } else {
                    lesson_progress_bar.setProgress((positionCard + 1));
                    pop_up_modal.setVisibility(View.VISIBLE);
                    setFragment(DoneLessonPopUpFragment);
                }
            }
//            FLing from left to right
            if (e2.getX() - e1.getX() > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (positionCard > 0) {
                    positionCard -= 1;
                    handleChangeCard();
                    lesson_progress_bar.setProgress((positionCard + 1));
                    lesson_progress_bar.setMin(1);
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}