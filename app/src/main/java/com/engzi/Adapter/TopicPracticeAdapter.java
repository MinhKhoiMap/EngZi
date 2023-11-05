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
import com.engzi.Model.TopicPractice;
import com.engzi.R;

import java.util.ArrayList;

public class TopicPracticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static int TYPE_TOPIC_UN_COMPLETE = 1;
    private static int TYPE_TOPIC_COMPLETE = 2;
    private ArrayList<TopicPractice> mlistTopic;

    public void setData(ArrayList<TopicPractice> listTopic){
        mlistTopic = listTopic;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == TYPE_TOPIC_COMPLETE ){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_practice_done,parent,false);
            return new TopicPracticeComplete(view);
        }
        else if(viewType == TYPE_TOPIC_UN_COMPLETE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_practice,parent,false);
            return new TopicPracticeUnComplete(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TopicPractice topicPractice = mlistTopic.get(position);
        if(topicPractice == null){
            return;
        }
        if(TYPE_TOPIC_COMPLETE == holder.getItemViewType()){
            TopicPracticeComplete topicPracticeComplete = (TopicPracticeComplete) holder;
            topicPracticeComplete.tvTitle.setText(topicPractice.getTitle());
            topicPracticeComplete.tvDescription.setText(topicPractice.getDescription());
        }
        else if(TYPE_TOPIC_UN_COMPLETE == holder.getItemViewType()){
            TopicPracticeUnComplete topicPracticeUnComplete = (TopicPracticeUnComplete) holder;
            topicPracticeUnComplete.tvTitle.setText(topicPractice.getTitle());
            topicPracticeUnComplete.tvDescription.setText(topicPractice.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        if(mlistTopic != null ){
            return  mlistTopic.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        TopicPractice topic = mlistTopic.get(position);
        if(topic.isCompleted()){
            return TYPE_TOPIC_COMPLETE;
        }
        return TYPE_TOPIC_UN_COMPLETE;
    }

    public class TopicPracticeUnComplete extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private TextView tvDescription;
        public TopicPracticeUnComplete(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title_topic_practice);
            tvDescription = itemView.findViewById(R.id.des_topic_practice);
        }
    }

    public class TopicPracticeComplete extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private TextView tvDescription;
        public TopicPracticeComplete(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title_topic_practice);
            tvDescription = itemView.findViewById(R.id.des_topic_practice);
        }
    }


}
