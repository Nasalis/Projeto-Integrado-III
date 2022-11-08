package com.example.ramirez.model;

import java.util.List;

public class Photographer {
    private String id;
    private String name;
    private String state;
    private String city;
    private String image;
    private List<String> specializations;
    private Float[] prices;

    public Photographer(String id, String name, String state, String city, String image, List<String> specializations, Float[] prices) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.city = city;
        this.image = image;
        this.specializations = specializations;
        this.prices = prices;
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
        return this.state;
    }

    public String getImage() { return this.image; }

    public float getMinValue() {
        return this.prices[0];
    }

    public float getMaxValue() {
        return  this.prices[1];
    }

    public List<String> getSpecializations() {
        return this.specializations;
    }

    public String getSpecializationsAsString() {
        StringBuilder output = new StringBuilder();
        int iterator = 0;
        for (String item : this.specializations) {
            if (iterator != this.specializations.size() - 1) {
                output.append(item).append(" - ");
            } else {
                output.append(item);
            }
            iterator++;
        }
        return output.toString();
    }
}