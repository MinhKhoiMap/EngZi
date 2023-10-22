package com.engzi.Model;

public class FlashCard {
    String englishWord;
    String vowel;
    String translateWord;
    String exampleSentence;
    String imageUrl;

    public FlashCard() {
    }

    public FlashCard(String englishWord, String vowel, String translateWord, String exampleSentence, String imageUrl) {
        this.englishWord = englishWord;
        this.vowel = vowel;
        this.translateWord = translateWord;
        this.exampleSentence = exampleSentence;
        this.imageUrl = imageUrl;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getVowel() {
        return vowel;
    }

    public void setVowel(String vowel) {
        this.vowel = vowel;
    }

    public String getTranslateWord() {
        return translateWord;
    }

    public void setTranslateWord(String translateWord) {
        this.translateWord = translateWord;
    }

    public String getExampleSentence() {
        return exampleSentence;
    }

    public void setExampleSentence(String exampleSentence) {
        this.exampleSentence = exampleSentence;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
