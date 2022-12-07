package com.example.ramirez.dao;

import android.content.res.Resources;

import com.example.ramirez.model.Comment;
import com.example.ramirez.model.Post;

import java.util.ArrayList;

public interface IPostDAO {
    void createCommentMock();
    ArrayList<Post> getPosts();
    void setComment(ArrayList<Post> user);
}