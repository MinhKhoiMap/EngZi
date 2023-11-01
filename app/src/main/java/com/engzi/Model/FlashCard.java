package com.engzi.Model;

public class FlashCard {
    String english_word;
    String vowel;
    String translate_word;
    String example_sentence;
    String cover_image_url;
    String cardID;

    public FlashCard() {
    }

    public FlashCard(String english_word, String vowel, String translate_word, String example_sentence, String cover_image_url, String cardID) {
        this.english_word = english_word;
        this.vowel = vowel;
        this.translate_word = translate_word;
        this.example_sentence = example_sentence;
        this.cover_image_url = cover_image_url;
        this.cardID = cardID;
    }

    public String getEnglish_word() {
        return english_word;
    }

    public void setEnglish_word(String english_word) {
        this.english_word = english_word;
    }

    public String getVowel() {
        return vowel;
    }

    public void setVowel(String vowel) {
        this.vowel = vowel;
    }

    public String getTranslate_word() {
        return translate_word;
    }

    public void setTranslate_word(String translate_word) {
        this.translate_word = translate_word;
    }

    public String getExample_sentence() {
        return example_sentence;
    }

    public void setExample_sentence(String example_sentence) {
        this.example_sentence = example_sentence;
    }

    public String getCover_image_url() {
        return cover_image_url;
    }

    public void setCover_image_url(String cover_image_url) {
        this.cover_image_url = cover_image_url;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }
}
