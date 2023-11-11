package com.engzi.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.engzi.R;
import com.engzi.Utils.Question;

import java.util.List;

public class ViewResultAdapter extends RecyclerView.Adapter<ViewResultAdapter.MyViewHolder> {
    private final int CORRECT = 0;
    private final int WRONG = 1;
    private final List<Question> questionList;

    public ViewResultAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public ViewResultAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == CORRECT) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_result_correct, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_result_wrong, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewResultAdapter.MyViewHolder holder, int position) {
        Question question = questionList.get(position);

        holder.question_name.setText("Question " + (position + 1));
        if (question.getUserAnswer() != null && !question.getUserAnswer().isEmpty()) {
            holder.user_answer.setText(question.getUserAnswer());
        } else {
            holder.user_answer.setTextColor(Color.parseColor("#5c5c5c"));
            holder.user_answer.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        }
        holder.correct_answer.setText(question.getEnglish_word());
    }

    @Override
    public int getItemCount() {
        if (questionList != null)
            return questionList.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        Question question = questionList.get(position);
        if (question.getUserAnswer() != null)
            if (question.getUserAnswer().toLowerCase().trim().equals(question.getEnglish_word().toLowerCase().trim()))
                return CORRECT;
        return WRONG;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView question_name, user_answer, correct_answer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            question_name = itemView.findViewById(R.id.question_name);
            user_answer = itemView.findViewById(R.id.user_answer);
            correct_answer = itemView.findViewById(R.id.correct_answer);
        }
    }
}
