package com.engzi.Adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.engzi.Activities.LessonActivity;
import com.engzi.Model.LessonPractice;
import com.engzi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LessonPracticeAdapter extends RecyclerView.Adapter<LessonPracticeAdapter.MyViewHolder> {

    private final List<LessonPractice> listLesson;
    private Context mContext;

    public LessonPracticeAdapter(Context mContext, List<LessonPractice> listLesson) {
        this.listLesson = listLesson;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lesson_practice, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final LessonPractice lessonPractice = listLesson.get(position);
        holder.tvTopic.setText(lessonPractice.getTopic_name());
        holder.tvName.setText(lessonPractice.getLesson_name());

        if (lessonPractice.getThumbnail() != null)
            Picasso.get().load(lessonPractice.getThumbnail()).into(holder.lesson_thumb);

        holder.lesson_card_item.setOnClickListener(view -> {
            Toast.makeText(mContext, lessonPractice.getLessonID(), Toast.LENGTH_SHORT).show();
            Intent lessonDetailIntent = new Intent(mContext, LessonActivity.class);
            Bundle lessonBundle = new Bundle();
            lessonBundle.putSerializable("lesson", lessonPractice);
            lessonDetailIntent.putExtras(lessonBundle);
            mContext.startActivity(lessonDetailIntent);
        });

    }

    @Override
    public int getItemCount() {
        return listLesson.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTopic, tvName;
        CardView lesson_card_item;
        ImageView lesson_thumb;

        MyViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.title_lesson_practice);
            tvTopic = itemView.findViewById(R.id.des_lesson_practice);
            lesson_thumb = itemView.findViewById(R.id.lesson_thumb);
            lesson_card_item = itemView.findViewById(R.id.lesson_card_item);
        }
    }

    public void release() {
        mContext = null;
    }
}
