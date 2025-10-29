package com.example.englishvocabularyapp.models;

public class Word {
    private int id;
    private int topicId;
    private String english;
    private String vietnamese;
    private String image;

    public Word(int id, int topicId, String english, String vietnamese, String image) {
        this.id = id;
        this.topicId = topicId;
        this.english = english;
        this.vietnamese = vietnamese;
        this.image = image;
    }

    public Word(int id, int topicId, String english, String vietnamese) {
        this(id, topicId, english, vietnamese, null);
    }

    public int getId() {
        return id;
    }

    public int getTopicId() {
        return topicId;
    }

    public String getEnglish() {
        return english;
    }

    public String getVietnamese() {
        return vietnamese;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
