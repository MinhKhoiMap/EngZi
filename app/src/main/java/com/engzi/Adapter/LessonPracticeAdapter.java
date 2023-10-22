package com.engzi.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.engzi.Model.LessionPractice;
import com.engzi.R;

import java.util.List;

public class LessonPracticeAdapter extends RecyclerView.Adapter<LessonPracticeAdapter.MyViewHolder> {

    private List<LessionPractice> listLesson;

    public LessonPracticeAdapter(List<LessionPractice> listLesson) {
        this.listLesson = listLesson;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDes;

        MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.title_lesson_practice);
            tvDes = view.findViewById(R.id.des_lesson_practice);
        }
    }

    @Override
    public int getItemCount() {
        return listLesson.size();
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
        LessionPractice lp = listLesson.get(position);
        holder.tvTitle.setText(lp.getLessionName());
        holder.tvTitle.setText(lp.getDescription());
    }
}
