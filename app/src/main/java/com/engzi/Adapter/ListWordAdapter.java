package com.engzi.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.engzi.Interface.OnItemClickCallBack;
import com.engzi.Model.FlashCard;
import com.engzi.R;

import java.util.List;

public class ListWordAdapter extends RecyclerView.Adapter<ListWordAdapter.MyViewHolder> {
    private final int ODD_ITEM = 1;
    private final int EVEN_ITEM = 2;
    private final List<FlashCard> listWords;
    private final OnItemClickCallBack iListWordsPopUp;

    public ListWordAdapter(List<FlashCard> listWords, OnItemClickCallBack iListWordsPopUp) {
        this.iListWordsPopUp = iListWordsPopUp;
        this.listWords = listWords;
    }

    @NonNull
    @Override
    public ListWordAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == ODD_ITEM) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_odd_word_bookmark, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_even_word_bookmark, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListWordAdapter.MyViewHolder holder, int position) {
        FlashCard word = listWords.get(position);
        if (word == null) {
            return;
        }
        holder.english_word.setText(word.getEnglish_word());
        holder.translate_word.setText(word.getTranslate_word());
        holder.word_vowel.setText(word.getVowel());

        holder.word_item.setOnClickListener(view -> {
            iListWordsPopUp.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        if (listWords != null)
            return listWords.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return EVEN_ITEM;
        }
        return ODD_ITEM;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView english_word, word_vowel, translate_word;
        final LinearLayout word_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            word_item = itemView.findViewById(R.id.word_item);
            english_word = itemView.findViewById(R.id.english_word);
            word_vowel = itemView.findViewById(R.id.word_vowel);
            translate_word = itemView.findViewById(R.id.translate_word);
//            topic_tag = itemView.findViewById(R.id.topic_tag);
        }
    }
}
