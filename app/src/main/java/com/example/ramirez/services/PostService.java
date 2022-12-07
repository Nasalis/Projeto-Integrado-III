package com.example.ramirez.services;

import androidx.annotation.NonNull;

import com.example.ramirez.helpers.SessionManager;
import com.example.ramirez.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostService {
    private SessionManager sessionManager;
    private static PostService instance;

    private PostService(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public static PostService getInstance(SessionManager sessionManager) {
        if(instance == null) {
            synchronized(UsersService.class) {
                if(instance == null) {
                    instance = new PostService(sessionManager);
                }
            }
        }
        return instance;
    }

     public ArrayList<Post> getPostsOfCurrentUser() throws InterruptedException {
        ArrayList<Post> postList = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        String url = "https://dolphin-app-vgvge.ondigitalocean.app/posts/" + sessionManager.fetchUserId();

        Request request = new Request.Builder()
            .url(url)
            .get()
            .addHeader("Authorization", "Bearer " + sessionManager.fetchAuthToken())
            .build();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                countDownLatch.countDown();
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(jsonData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for ( int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject mJsonObjectProperty = jsonArray.getJSONObject(i);

                            String id = mJsonObjectProperty.getJSONObject("_id").getString("$oid");
                            String postDirectory = mJsonObjectProperty.getString("image");
                            String postTitle = mJsonObjectProperty.getString("title");
                            Float postPrice = Float.parseFloat(mJsonObjectProperty.getString("price"));

                            postList.add(new Post(id, postTitle, postPrice, postDirectory));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                countDownLatch.countDown();
            }
        });

        countDownLatch.await();
        return postList;
    }
}

