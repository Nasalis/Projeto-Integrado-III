package com.example.ramirez.dao;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.ramirez.R;
import com.example.ramirez.model.Comment;
import com.example.ramirez.model.Post;

import java.util.ArrayList;

public class PostDAO implements IPostDAO {
    private static Context context;
    private static PostDAO postDAO = null;
    private ArrayList<Post> posts;

    private PostDAO(Context context) {
        PostDAO.context = context;
        createCommentMock();
    }

    public static IPostDAO getInstance(Context context) {
        if (postDAO == null) {
            postDAO = new PostDAO(context);
        }
        return postDAO;
    }

    @Override
    public void createCommentMock() {
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment("1", "Jéssica Gomez", "11/11/22", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam viverra et orci eu tempor", "aaa"));
        comments.add(new Comment("1", "Rodrigo Affonso", "11/11/22", " Aenean semper dapibus sapien ut vestibulum. Morbi sed tristique nulla. Nulla facilisi", "aaa"));
        comments.add(new Comment("1", "Letícia Gomes", "11/11/22", "Donec cursus augue id leo placerat, in posuere urna posuere. Nullam urna urna, egestas ut tempor sed, elementum non turpis", "aaa"));

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo_view);

        posts = new ArrayList<>();

    }

    @Override
    public ArrayList<Post> getPosts() {
        return this.posts;
    }

    public void setComment(ArrayList<Post> posts) {
        this.posts = posts;
    }
}
