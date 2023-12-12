package com.engzi.Activities;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.engzi.Adapter.AnswersAdapter;
import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.FlashCard;
import com.engzi.Model.LessonPractice;
import com.engzi.R;
import com.engzi.Services.ExamsServices;
import com.engzi.Services.FlashCardServices;
import com.engzi.Utils.FillBlankQuestion;
import com.engzi.Utils.MultipleChoiceQuestion;
import com.engzi.Utils.Question;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ExamActivity extends AppCompatActivity {
    final int MULTI_CHOICE_QUESTION_TYPE = 0;
    final int FILL_BLANK_QUESTION_TYPE = 1;
    final int LIMIT_QUESTION = 10;
    LessonPractice topicExam;
    List<FlashCard> flashCards;
    //    List<Map<String, String>> userAnswerRecord;
    List<Question> questionList;
    int currentPosition = 0;
    boolean isHaveRecord;

    //    View
    View question_layout;
    TextView exam_topic_title, progress_question;
    Toolbar exam_toolbar;
    FrameLayout question_type_layout;
    LinearLayout exam_layout;
    BottomNavigationView bottom_main_nav_view;
    AppCompatButton next_navigate, previous_navigate;
    FloatingActionButton home_button;

    //    Services
    FlashCardServices flashCardServices = new FlashCardServices();
    ExamsServices examsServices = new ExamsServices();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        flashCards = new ArrayList<>();
        Intent topicIntent = getIntent();
        topicExam = (LessonPractice) topicIntent.getSerializableExtra("topicObject");
        isHaveRecord = topicIntent.getBooleanExtra("isHaveRecord", false);

        getListCard();
        questionList = new ArrayList<>();

        initUI();
        exam_topic_title.setText("Topic: " + topicExam.getTopic_name());


        next_navigate.setOnClickListener(view -> {
            if (currentPosition + 1 < LIMIT_QUESTION - 1) {
                next_navigate.setText("Next question");
                handleRecordAnswer();
                handleNextQuestion();
                next_navigate.setEnabled(true);
            } else if (currentPosition + 1 == 9) {
                handleRecordAnswer();
                handleNextQuestion();
                next_navigate.setText("Submit");
            } else {
                int score = calScore();
                if (isHaveRecord) {
                    examsServices.updateExamRecords(topicExam.getLessonID(), String.format("%d/%d", score, 10), new IServiceCallBack() {
                        @Override
                        public void retrieveData(Object response) {

                        }

                        @Override
                        public void onFailed(Exception e) {

                        }

                        @Override
                        public void onComplete() {
                            Log.d("RESULT", "Complete");
                        }
                    });
                } else {
                    examsServices.createExamRecord(topicExam.getLessonID(), String.format("%d/%d", score, 10), new IServiceCallBack() {
                        @Override
                        public void retrieveData(Object response) {

                        }

                        @Override
                        public void onFailed(Exception e) {

                        }

                        @Override
                        public void onComplete() {
                            Log.d("LKJSF", "Complete create record");
                        }
                    });
                }

                Intent resultIntent = new Intent(this, ResultActivity.class);
                Bundle questionListBundle = new Bundle();
                questionListBundle.putSerializable("question_list", (Serializable) questionList);
                questionListBundle.putInt("score", score);
                resultIntent.putExtras(questionListBundle);
                startActivity(resultIntent);
                finish();
            }
        });

        previous_navigate.setOnClickListener(view -> {
            if (currentPosition + 1 > 1) {
                handlePreviousQuestion();
            }
        });

        exam_toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });

        home_button.setOnClickListener(view -> {
            finish();
        });
    }

    private int calScore() {
        int score = 0;
        for (Question question : questionList) {
            if (question.getUserAnswer() != null)
                if (question.getUserAnswer().toLowerCase().trim().equals(question.getEnglish_word().toLowerCase().trim()))
                    score++;
        }
        return score;
    }

    private void handleLoadQuestion() {
        Class<? extends Question> question_type = questionList.get(currentPosition).getClass();
        if (question_type == FillBlankQuestion.class) {
            handleRenderQuestion(FILL_BLANK_QUESTION_TYPE, questionList.get(currentPosition));
        } else if (question_type == MultipleChoiceQuestion.class) {
            handleRenderQuestion(MULTI_CHOICE_QUESTION_TYPE, questionList.get(currentPosition));
        }
    }

    private void handleRenderQuestion(int question_type, Question question) {
        if (question_type == FILL_BLANK_QUESTION_TYPE) {
            handleRenderQuestionLayout(FILL_BLANK_QUESTION_TYPE);
            String question_sentence = ((FillBlankQuestion) question).getQuestion();
            ((TextView) question_layout.findViewById(R.id.sentences_label)).setText(question_sentence);
            if (question.getUserAnswer() != null) {
                ((EditText) question_layout.findViewById(R.id.edtxt_answer)).setText(question.getUserAnswer());
            }
        } else if (question_type == MULTI_CHOICE_QUESTION_TYPE) {
            handleRenderQuestionLayout(MULTI_CHOICE_QUESTION_TYPE);
            ImageView exam_img = question_layout.findViewById(R.id.exam_img);
            RecyclerView answer_list = question_layout.findViewById(R.id.answer_list);

            Glide.with(getBaseContext()).load(((MultipleChoiceQuestion) question).getCover_img_url())
                    .placeholder(R.drawable.hold_sign)
                    .transition(withCrossFade())
                    .into(exam_img);


            List<String> answerListAdapter = ((MultipleChoiceQuestion) question).getAnswerList();

            AnswersAdapter answersAdapter = new AnswersAdapter(answerListAdapter, question.getUserAnswer(),
                    position -> question.setUserAnswer(answerListAdapter.get(position))
            );

            LinearLayoutManager mLinearLayoutManager =
                    new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };

            answer_list.setLayoutManager(mLinearLayoutManager);
            answer_list.setAdapter(answersAdapter);
        }
    }

    private void handleGenerateQuestion(int question_type, FlashCard card) {
        if (question_type == FILL_BLANK_QUESTION_TYPE) {
            String sentence = card.getExample();
            String word = card.getEnglish_word();
            String question = sentence.replace(word, "...");

            FillBlankQuestion fillBlankQuestion = new FillBlankQuestion(word, question);

            questionList.add(fillBlankQuestion);
        } else {
            // Random Answer List
            Set<String> answerSet = new HashSet<>();
            answerSet.add(card.getEnglish_word());
            Random myRandom = new Random();
            while (answerSet.size() < 4) {
                int random = myRandom.nextInt(flashCards.size());
                String wordChosen = flashCards.get(random).getEnglish_word();
                answerSet.add(wordChosen);
            }
            String[] answerArr = new String[4];
            answerSet.toArray(answerArr);
            List<String> answerList = Arrays.asList(answerArr);

            MultipleChoiceQuestion multipleChoiceQuestion =
                    new MultipleChoiceQuestion(card.getEnglish_word(), answerList, card.getCover_image_url());

            questionList.add(multipleChoiceQuestion);
        }
    }

    private void handleRenderQuestionLayout(int question_type) {
        question_type_layout.removeAllViews();
        if (question_type == FILL_BLANK_QUESTION_TYPE) {
            question_layout = LayoutInflater.from(this)
                    .inflate(R.layout.fill_blank_question_type, question_type_layout, false);
        } else {
            question_layout = LayoutInflater.from(this)
                    .inflate(R.layout.multiple_choice_question_type, question_type_layout, false);
        }
        question_type_layout.addView(question_layout);
    }

    private void handleRecordAnswer() {
        Class<? extends Question> question_type = questionList.get(currentPosition).getClass();
        if (question_type == FillBlankQuestion.class) {
            String answer = ((EditText) question_layout.findViewById(R.id.edtxt_answer)).getText().toString().trim();
            questionList.get(currentPosition).setUserAnswer(answer);
        }
    }

    @SuppressLint("DefaultLocale")
    private void handlePreviousQuestion() {
        currentPosition--;
        handleLoadQuestion();
        progress_question.setText(String.format("%d/%d", currentPosition + 1, 10));
    }

    @SuppressLint("DefaultLocale")
    private void handleNextQuestion() {
        currentPosition++;
        if (currentPosition < questionList.size()) {
            handleLoadQuestion();
        } else {
            int randomWord = new Random().nextInt(flashCards.size());
            handleGenerateQuestion(chooseQuestionType(), flashCards.get(randomWord));
            handleLoadQuestion();
        }
        progress_question.setText(String.format("%d/%d", currentPosition + 1, 10));
    }

    private int chooseQuestionType() {
        Random rand = new Random();
        return rand.nextInt(2);
    }

    private void getListCard() {
        for (String cardID : topicExam.getList_cards()) {
            flashCardServices.getCardByID(cardID, new IServiceCallBack() {
                @Override
                public void retrieveData(Object response) {
                    flashCards.add((FlashCard) response);
                }

                @Override
                public void onFailed(Exception e) {
                }

                @Override
                public void onComplete() {
                    if (flashCards.size() == topicExam.getList_cards().size()) {
                        if (chooseQuestionType() == MULTI_CHOICE_QUESTION_TYPE) {
                            handleGenerateQuestion(MULTI_CHOICE_QUESTION_TYPE, flashCards.get(currentPosition));
                            handleLoadQuestion();
                        } else {
                            handleGenerateQuestion(FILL_BLANK_QUESTION_TYPE, flashCards.get(currentPosition));
                            handleLoadQuestion();
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        question_type_layout.removeAllViews();
    }

    private void initUI() {
        exam_toolbar = findViewById(R.id.exam_toolbar);
        exam_topic_title = findViewById(R.id.exam_topic_title);
        question_type_layout = findViewById(R.id.question_type_layout);
        bottom_main_nav_view = findViewById(R.id.bottom_main_nav_view);
        next_navigate = findViewById(R.id.next_navigate);
        previous_navigate = findViewById(R.id.previous_navigate);
        progress_question = findViewById(R.id.progress_question);
        exam_layout = findViewById(R.id.exam_layout);
        home_button = findViewById(R.id.home_button);

        bottom_main_nav_view.setBackground(null);
        bottom_main_nav_view.getMenu().getItem(2).setChecked(true);
    }

}