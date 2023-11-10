package com.engzi.Adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.engzi.Model.LessonPractice;
import com.engzi.R;

import java.util.List;

public class TopicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_TOPIC_UN_COMPLETE = 1;
    private static final int TYPE_TOPIC_COMPLETE = 2;
    private List<LessonPractice> mListTopic;

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<LessonPractice> listTopic) {
        mListTopic = listTopic;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_TOPIC_COMPLETE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_practice_done, parent, false);
            return new TopicPracticeComplete(view);
        } else if (viewType == TYPE_TOPIC_UN_COMPLETE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_practice, parent, false);
            return new TopicPracticeUnComplete(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LessonPractice topicPractice = mListTopic.get(position);
        if (topicPractice == null) {
            return;
        }
        if (TYPE_TOPIC_COMPLETE == holder.getItemViewType()) {
            TopicPracticeComplete topicPracticeComplete = (TopicPracticeComplete) holder;
            topicPracticeComplete.lesson_name.setText(topicPractice.getLesson_name());
            topicPracticeComplete.topic_name.setText(topicPractice.getTopic_name());

            if (topicPractice.getThumbnail() != null)
                Glide.with(holder.itemView.getContext()).load(topicPractice.getThumbnail())
                        .placeholder(R.drawable.loading)
                        .transition(withCrossFade())
                        .into(topicPracticeComplete.lesson_thumb);
        } else if (TYPE_TOPIC_UN_COMPLETE == holder.getItemViewType()) {
            TopicPracticeUnComplete topicPracticeUnComplete = (TopicPracticeUnComplete) holder;
            topicPracticeUnComplete.lesson_name.setText(topicPractice.getLesson_name());
            topicPracticeUnComplete.topic_name.setText(topicPractice.getTopic_name());

            if (topicPractice.getThumbnail() != null)
                Glide.with(holder.itemView.getContext()).load(topicPractice.getThumbnail())
                        .placeholder(R.drawable.loading)
                        .transition(withCrossFade())
                        .into(topicPracticeUnComplete.lesson_thumb);
        }
    }

    @Override
    public int getItemCount() {
        if (mListTopic != null) {
            return mListTopic.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        LessonPractice topic = mListTopic.get(position);
        if (topic.getCompletion_percent() >= 1) {
            return TYPE_TOPIC_COMPLETE;
        }
        return TYPE_TOPIC_UN_COMPLETE;
    }

    public static class TopicPracticeUnComplete extends RecyclerView.ViewHolder {
        private final TextView lesson_name, topic_name;
        private final ImageView lesson_thumb;

        public TopicPracticeUnComplete(@NonNull View itemView) {
            super(itemView);
            lesson_name = itemView.findViewById(R.id.title_topic_practice);
            topic_name = itemView.findViewById(R.id.des_topic_practice);
            lesson_thumb = itemView.findViewById(R.id.lesson_thumb);
        }
    }

    public static class TopicPracticeComplete extends RecyclerView.ViewHolder {
        private final TextView lesson_name, topic_name;
        private final ImageView lesson_thumb;

        public TopicPracticeComplete(@NonNull View itemView) {
            super(itemView);
            lesson_name = itemView.findViewById(R.id.title_topic_practice);
            topic_name = itemView.findViewById(R.id.des_topic_practice);
            lesson_thumb = itemView.findViewById(R.id.lesson_thumb);
        }
    }
}
