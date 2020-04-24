package com.darshan.writepoems.model;

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

    public String getPoem() {
        return poem;
    }
}
