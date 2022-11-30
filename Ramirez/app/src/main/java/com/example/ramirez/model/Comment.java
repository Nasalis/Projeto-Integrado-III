package com.example.ramirez.model;

public class Comment {
    private String id;
    private String name;
    private String date;
    private String text;
    private String image;

    public Comment(String id, String name, String date, String text, String image) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.text = text;
        this.image = image;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public String getText() {
        return this.text;
    }

    public String getImage() { return this.image; }

}
