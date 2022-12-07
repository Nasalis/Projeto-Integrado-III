package com.example.ramirez.model;

import android.net.Uri;

import java.util.ArrayList;

public class Post {
    private String id;
    private String name;
    private Float price;
    private String image;
    private ArrayList<Comment> comments;
    private String imageUri;

    public Post(String id, String name, Float price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.imageUri = null;
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

    public String getImage() { return this.image; }

    public ArrayList<Comment> getComments() { return this.comments; }

    public String getImageUri() { return this.imageUri; }

    public void setImageUri(String uri) { this.imageUri = uri; }

}
