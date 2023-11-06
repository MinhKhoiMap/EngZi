package com.engzi.Activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.engzi.Fragment.CardBackFragment;
import com.engzi.Fragment.CardFrontFragment;
import com.engzi.Fragment.DoneLessonPopUpFragment;
import com.engzi.Fragment.QuittingPopUpFragment;
import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.FlashCard;
import com.engzi.Model.LessonPractice;
import com.engzi.R;
import com.engzi.Services.FlashCardServices;
import com.engzi.Services.NoteBookServices;
import com.engzi.Services.UserServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LessonActivity extends AppCompatActivity {
    final int SWIPE_THRESHOLD = 100;
    final int SWIPE_VELOCITY_THRESHOLD = 100;
    final List<FlashCard> flashCardList = new ArrayList<>();

    //    Variants Utils
    int positionCard;
    boolean isFlipped = false;
    LessonPractice lessonPractice;

    //    Fragment
    CardFrontFragment cardFrontFragment, cardPreviousFrontFragment, cardNextFrontFragment;
    CardBackFragment cardBackFragment, cardPreviousBackFragment, cardNextBackFragment;
    DoneLessonPopUpFragment DoneLessonPopUpFragment = new DoneLessonPopUpFragment();
    QuittingPopUpFragment QuittingPopUpFragment = new QuittingPopUpFragment();

    /////////////////////////////////////////////////////////////
    Toolbar toolbar;
    BottomNavigationView bottom_main_nav_view;
    FloatingActionButton home_button;
    ImageView lesson_image;
    TextView lesson_example_english, lesson_english_word, lesson_vowel, lesson_translate_word;
    ProgressBar lesson_progress_bar;

    //    Layout
    LinearLayout main_learn_layout;
    FrameLayout pop_up_modal, flash_card;

    //    Fragment

    //    Event Management
    GestureDetector gestureDetector;

    //    Services
    FlashCardServices flashCardServices = new FlashCardServices();
    NoteBookServices noteBookServices = new NoteBookServices();
    UserServices userServices = new UserServices();


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
//        Toast.makeText(this, String.valueOf(lessonPractice.getCompletion_percent()), Toast.LENGTH_SHORT).show();

        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });

        main_learn_layout.setOnTouchListener((view, motionEvent) -> {
            gestureDetector.onTouchEvent(motionEvent);
            return true;
        });

        flash_card.setOnTouchListener((view, motionView) -> {
            gestureDetector.onTouchEvent(motionView);
            return true;
        });

        home_button.setOnClickListener(view -> {
            bottom_main_nav_view.getMenu().getItem(2).setChecked(true);
            Intent homeActivity = new Intent(LessonActivity.this, MainActivity.class);
            homeActivity.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(homeActivity);
        });
    }

    @Override
    public void onBackPressed() {
        userServices.updateRecentlyLesson(lessonPractice.getLessonID(), positionCard);
        if (positionCard < flashCardList.size() - 1) {
            pop_up_modal.setVisibility(View.VISIBLE);
            setPopUpFragment(QuittingPopUpFragment);
        } else {
            super.onBackPressed();
        }
    }

    private void initUI() {
        toolbar = findViewById(R.id.lesson_toolbar);
        bottom_main_nav_view = findViewById(R.id.bottom_main_nav_view);
        main_learn_layout = findViewById(R.id.main_learn_layout);
        lesson_image = findViewById(R.id.lesson_image);
        lesson_example_english = findViewById(R.id.lesson_example_english);
        lesson_english_word = findViewById(R.id.lesson_english_word);
        lesson_vowel = findViewById(R.id.lesson_vowel);
        lesson_translate_word = findViewById(R.id.lesson_translate_word);
        lesson_progress_bar = findViewById(R.id.lesson_progress_bar);
        pop_up_modal = findViewById(R.id.pop_up_modal);
        home_button = findViewById(R.id.home_button);
        flash_card = findViewById(R.id.flash_card);

        bottom_main_nav_view.setBackground(null);
        bottom_main_nav_view.getMenu().getItem(2).setEnabled(false);
        bottom_main_nav_view.getMenu().getItem(2).setChecked(true);
    }

    private void getListCard() {
        for (String cardID : lessonPractice.getList_cards()) {
//            Log.d("Card ID", cardID);
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
                    lesson_progress_bar.setMax(flashCardList.size() - 1);

                    positionCard = lessonPractice.getLast_position_card();
                    if (positionCard >= flashCardList.size())
                        positionCard = 0;
                    FlashCard currentCard = flashCardList.get(positionCard);

                    cardFrontFragment = new CardFrontFragment(currentCard.getCover_image_url(), currentCard.getExample());
                    cardBackFragment = new CardBackFragment(currentCard.getEnglish_word(), currentCard.getVowel(), currentCard.getTranslate_word());

                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(flash_card.getId(), cardFrontFragment)
                            .commit();
                }
            });
        }
    }

    private void handleChangeCard() {
        FlashCard nextCard = flashCardList.get(positionCard);
        cardFrontFragment = new CardFrontFragment(nextCard.getCover_image_url(), nextCard.getExample());
        cardBackFragment = new CardBackFragment(nextCard.getEnglish_word(), nextCard.getVowel(), nextCard.getTranslate_word());

//        Log.d("Card", "onFling: " + positionCard + nextCard.getCover_image_url());

        getSupportFragmentManager()
                .popBackStack();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(flash_card.getId(), cardFrontFragment)
                .commit();
    }


    //    TypeFlip:  1 == is showing, 2 == show now
    private void handleFlipCard(int typeFlip) {
//        Toast.makeText(this, String.valueOf(typeFlip), Toast.LENGTH_SHORT).show();
        if (typeFlip == 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_enter, //enter
                            R.animator.card_flip_right_exit, //exit
                            R.animator.card_flip_left_enter,
                            R.animator.card_flip_left_exit
                    )
                    .replace(flash_card.getId(), cardBackFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void setPopUpFragment(Fragment fragment) {
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
                    noteBookServices.updateNoteBookOrderByLevel(1, flashCardList.get(positionCard).getCardID(),
                            new IServiceCallBack() {
                                @Override
                                public void retrieveData(Object response) {

                                }

                                @Override
                                public void onFailed(Exception e) {
                                    Log.w("update process", "onFailed: ", e);
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                    handleChangeCard();
                    handleFlipCard(1);
                    lesson_progress_bar.setProgress(positionCard);
                } else {
                    pop_up_modal.setVisibility(View.VISIBLE);
                    userServices.updateRecentlyLesson(lessonPractice.getLessonID(), positionCard + 1);
                    setPopUpFragment(DoneLessonPopUpFragment);
                }
            }
//            FLing from left to right
            if (e2.getX() - e1.getX() > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (positionCard > 0) {
                    positionCard -= 1;
                    handleChangeCard();
                    handleFlipCard(1);
                    lesson_progress_bar.setProgress((positionCard + 1));
                    lesson_progress_bar.setMin(0);
                }
            }
            isFlipped = false;
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onSingleTapUp(@NonNull MotionEvent e) {
//            Toast.makeText(LessonActivity.this, "single tap up" + isFlipped, Toast.LENGTH_SHORT).show();
            if (isFlipped) {
                handleFlipCard(1);
            } else {
                handleFlipCard(2);
            }
            isFlipped = !isFlipped;
            return super.onSingleTapUp(e);
        }
    }
}