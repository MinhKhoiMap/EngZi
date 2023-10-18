package com.engzi.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.engzi.Model.LessonPractice;
import com.engzi.R;
import java.util.ArrayList;
import java.util.List;

public class LessonPracticeAdapter extends RecyclerView.Adapter<LessonPracticeAdapter.MyViewHolder> {

    private List<LessonPractice> listLesson;

    public LessonPracticeAdapter(List<LessonPractice> listLesson) {
        this.listLesson = listLesson;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDes;
        MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.title_lesson_practice);
            tvDes = view.findViewById(R.id.des_lesson_practice);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lesson_practive, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LessonPractice lp = listLesson.get(position);
        holder.tvTitle.setText(lp.getTitle());
        holder.tvTitle.setText(lp.getDescription());
    }

    @Override
    public int getItemCount() {
        return listLesson.size();
    }
}
