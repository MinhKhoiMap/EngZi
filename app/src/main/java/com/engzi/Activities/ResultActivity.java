package com.engzi.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.engzi.R;
import com.engzi.Utils.Question;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    List<Question> questionList;
    int score = 0;
    //    View
    BottomNavigationView bottom_main_nav_view;
    TextView score_txt, result_comment, not_now_button;
    ImageView reaction_img;
    AppCompatButton view_result_button, next_exam_button;

    @SuppressLint({"DefaultLocale", "SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initUI();

        Intent examIntent = getIntent();
        questionList = (List<Question>) examIntent.getSerializableExtra("question_list");

        assert questionList != null;
        for (Question question : questionList) {
            if (question.getUserAnswer() != null)
                if (question.getUserAnswer().toLowerCase().trim().equals(question.getEnglish_word().toLowerCase().trim()))
                    score++;
        }
        score_txt.setText(String.format("%d/%d", score, 10));
        if (score >= 5) {
            reaction_img.setImageDrawable(getDrawable(R.drawable.teaching_correct));
            if (score > 8)
                result_comment.setText("Tốt lắm!");
            else
                result_comment.setText("Hãy cố gắng lên nhé!");
        } else {
            reaction_img.setImageDrawable(getDrawable(R.drawable.teaching_wrong));
            result_comment.setText("Cần xem lại bài nhé!");
        }

        view_result_button.setOnClickListener(view -> {
            Intent viewResultIntent = new Intent(this, ViewResultActivity.class);
            Bundle questionListBundle = new Bundle();
            questionListBundle.putSerializable("question_list", (Serializable) questionList);
            viewResultIntent.putExtras(questionListBundle);
            startActivity(viewResultIntent);
        });

        not_now_button.setOnClickListener(view -> {
            Intent homeIntent = new Intent(this, MainActivity.class);
            startActivity(homeIntent);
        });

        next_exam_button.setOnClickListener(view -> {
            finish();
        });
    }

    private void initUI() {
        bottom_main_nav_view = findViewById(R.id.bottom_main_nav_view);
        score_txt = findViewById(R.id.score_txt);
        result_comment = findViewById(R.id.result_comment);
        reaction_img = findViewById(R.id.reaction_img);
        view_result_button = findViewById(R.id.view_result_button);
        next_exam_button = findViewById(R.id.next_exam_button);
        not_now_button = findViewById(R.id.not_now_button);

        bottom_main_nav_view.setBackground(null);
        bottom_main_nav_view.getMenu().getItem(2).setChecked(true);
    }
}

//.Activities.ViewResultActivity