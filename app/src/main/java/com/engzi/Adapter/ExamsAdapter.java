package com.engzi.Adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

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
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.engzi.Activities.ExamActivity;
import com.engzi.Model.LessonPractice;
import com.engzi.R;

import java.util.List;
import java.util.Map;

public class ExamsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HAVE_RESULT = 0;
    private static final int TYPE_DONT_HAVE_RESULT = 1;
    private List<Map<String, Object>> mListTopic;
    private Context mContext;

    public ExamsAdapter(List<Map<String, Object>> listTopic) {
        this.mListTopic = listTopic;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View itemView;
        if (viewType == TYPE_HAVE_RESULT) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_exam_have_result, parent, false);
            return new ExamsListHaveResultViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_exam_dont_have_result, parent, false);
            return new ExamsListViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LessonPractice topic = (LessonPractice) mListTopic.get(position).get("lesson");
        if (topic == null) {
            return;
        }

        ((ExamsListViewHolder) holder).title_topic_practice.setText(topic.getTopic_name());
        ((ExamsListViewHolder) holder).start_exam_button.setOnClickListener(view -> {
            Toast.makeText(holder.itemView.getContext(), "Let's go", Toast.LENGTH_SHORT).show();
        });

        Glide.with(holder.itemView.getContext()).load(topic.getThumbnail())
                .placeholder(R.drawable.hold_sign)
                .transition(withCrossFade())
                .into(((ExamsListViewHolder) holder).topic_thumb);

        if (holder.getItemViewType() == TYPE_HAVE_RESULT)
            ((ExamsListHaveResultViewHolder) holder).score_record.setText(mListTopic.get(position).get("score").toString());

        holder.itemView.setOnClickListener(view -> {
            Intent examIntent = new Intent(mContext, ExamActivity.class);
            Bundle examTopicBundle = new Bundle();
            examTopicBundle.putSerializable("topicObject", topic);
            examTopicBundle.putBoolean("isHaveRecord", mListTopic.get(position).get("score") != null);
            examIntent.putExtras(examTopicBundle);

            mContext.startActivity(examIntent);
        });

        ((ExamsListViewHolder) holder).start_exam_button.setOnClickListener(view -> {
            Intent examIntent = new Intent(mContext, ExamActivity.class);
            Bundle examTopicBundle = new Bundle();
            examTopicBundle.putSerializable("topicObject", topic);
            examTopicBundle.putBoolean("isHaveRecord", mListTopic.get(position).get("score") != null);
            examIntent.putExtras(examTopicBundle);

            mContext.startActivity(examIntent);
        });
    }

    @Override
    public int getItemCount() {
        if (mListTopic != null)
            return mListTopic.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mListTopic.get(position).get("score") != null) {
            return TYPE_HAVE_RESULT;
        }
        return TYPE_DONT_HAVE_RESULT;
    }

    public static class ExamsListHaveResultViewHolder extends ExamsListViewHolder {
        TextView score_record;

        public ExamsListHaveResultViewHolder(@NonNull View itemView) {
            super(itemView);

            score_record = itemView.findViewById(R.id.score_record);
        }
    }

    public static class ExamsListViewHolder extends RecyclerView.ViewHolder {
        TextView title_topic_practice;
        AppCompatButton start_exam_button;
        ImageView topic_thumb;

        public ExamsListViewHolder(@NonNull View itemView) {
            super(itemView);

            title_topic_practice = itemView.findViewById(R.id.title_topic_practice);
            start_exam_button = itemView.findViewById(R.id.start_exam_button);
            topic_thumb = itemView.findViewById(R.id.topic_thumb);
        }
    }
}
