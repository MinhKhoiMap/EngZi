package com.engzi.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.engzi.Adapter.ViewResultAdapter;
import com.engzi.R;
import com.engzi.Utils.Question;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ViewResultActivity extends AppCompatActivity {
    //    View
    BottomNavigationView bottom_main_nav_view;
    TextView score_txt;
    Toolbar view_result_toolbar;
    RecyclerView view_result_word;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);

        Intent resultIntent = getIntent();
        List<Question> questionList = (List<Question>) resultIntent.getSerializableExtra("question_list");
        int score = resultIntent.getIntExtra("score", 0);

        initUI();

        score_txt.setText(String.format("%d/%d", score, 10));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ViewResultAdapter viewResultAdapter = new ViewResultAdapter(questionList);

        view_result_word.setLayoutManager(linearLayoutManager);
        view_result_word.setAdapter(viewResultAdapter);

        view_result_toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void initUI() {
        bottom_main_nav_view = findViewById(R.id.bottom_main_nav_view);
        view_result_toolbar = findViewById(R.id.view_result_toolbar);
        view_result_word = findViewById(R.id.view_result_word);
        score_txt = findViewById(R.id.score_txt);

        bottom_main_nav_view.setBackground(null);
        bottom_main_nav_view.getMenu().getItem(2).setChecked(true);
    }
}