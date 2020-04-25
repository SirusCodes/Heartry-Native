package com.darshan.heartry.model;

public class PoemModel {
    private String title;
    private String poem;

    public PoemModel() {
    }

    public PoemModel(String title, String poem) {
        this.title = title;
        this.poem = poem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoem() {
        return poem;
    }

    public void setPoem(String poem) {
        this.poem = poem;
    }
}
