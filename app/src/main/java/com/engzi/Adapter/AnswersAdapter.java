package com.engzi.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.engzi.Interface.OnItemClickCallBack;
import com.engzi.R;

import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.MyViewHolder> {
    private final List<String> answerList;
    private AnswersAdapter.MyViewHolder checkedAnswer;
    private final OnItemClickCallBack itemCallBack;
    private final String userAnswer;

    public AnswersAdapter(List<String> answerList, String userAnswer, OnItemClickCallBack callBack) {
        this.answerList = answerList;
        this.itemCallBack = callBack;
        this.userAnswer = userAnswer;
    }

    @NonNull
    @Override
    public AnswersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull AnswersAdapter.MyViewHolder holder, int position) {
        AppCompatButton answer_button = (AppCompatButton) holder.itemView;
        String answer = answerList.get(position);
        if (answer.equals(userAnswer)) {
            answer_button.setBackgroundDrawable(holder.itemView.getResources().getDrawable(R.drawable.answer_checked_button));
            checkedAnswer = holder;
        }
        answer_button.setText(answer);
        answer_button.setOnClickListener(view -> {
            itemCallBack.onClick(position);
            if (checkedAnswer != null)
                ((AppCompatButton) checkedAnswer.itemView)
                        .setBackgroundDrawable(checkedAnswer.itemView.getResources().getDrawable(R.drawable.answer_button));
            checkedAnswer = holder;
            answer_button.setBackgroundDrawable(holder.itemView.getResources().getDrawable(R.drawable.answer_checked_button));
        });

    }

    @Override
    public int getItemCount() {
        if (answerList != null) {
            return answerList.size();
        }
        return 0;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
