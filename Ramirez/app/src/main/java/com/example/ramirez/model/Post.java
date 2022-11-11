package com.example.ramirez.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Post {
    private String id;
    private String name;
    private Float price;
    private Bitmap image;
    private ArrayList<Comment> comments;

    public Post(String id, String name, Float price, Bitmap image, ArrayList<Comment> comments) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.comments = comments;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Float getPrice() {
        return this.price;
    }

    public Bitmap getImage() { return this.image; }

}
