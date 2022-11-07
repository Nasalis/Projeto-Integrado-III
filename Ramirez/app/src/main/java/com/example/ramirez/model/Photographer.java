package com.example.ramirez.model;

import java.util.List;

public class Photographer {
    private String id;
    private String name;
    private String state;
    private String city;
    private String image;
    private List<String> specializations;

    public Photographer(String id, String name, String state, String city, String image, List<String> specializations) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.city = city;
        this.image = image;
        this.specializations = specializations;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.id;
    }

    public String getImage() {
        return this.id;
    }
}
