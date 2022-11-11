package com.example.ramirez.dao;

import com.example.ramirez.model.Photographer;

import java.util.ArrayList;

public interface IPhotographerDAO {
    void createUserMock();
    ArrayList<Photographer> getPhotographer();
    void setUser(ArrayList<Photographer> user);
}