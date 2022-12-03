package com.example.ramirez.helpers;

import androidx.annotation.NonNull;

import com.example.ramirez.model.Photographer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UsersService {
    private SessionManager sessionManager;
    private static UsersService instance;
    private List<Photographer> photographersList;

    private UsersService(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.photographersList = getPhotographersByDatabase();
    }

    public List<Photographer> getPhotographers() {
        return this.photographersList;
    }

    public static UsersService getInstance(SessionManager sessionManager) {
        if(instance == null) {
            synchronized(UsersService.class) {
                if(instance == null) {
                    instance = new UsersService(sessionManager);
                }
            }
        }
        return instance;
    }

    public List<Photographer> getPhotographersByDatabase() {
        List<Photographer> photographers = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        String url = "http://10.0.2.2:3001/users";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bearer " + sessionManager.fetchAuthToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
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
                            JSONObject mJsonObjectProperty = mJsonObjectProperty = jsonArray.getJSONObject(i);

                            String id = mJsonObjectProperty.getJSONObject("_id").getString("$oid");
                            String name = mJsonObjectProperty.getString("name");
                            String state = mJsonObjectProperty.getString("state");
                            String city = mJsonObjectProperty.getString("city");
                            String profileImage = mJsonObjectProperty.getString("profile_img");
                            long views = mJsonObjectProperty.getLong("views");
                            String bio = mJsonObjectProperty.getString("bio").equals("null") ? "" : mJsonObjectProperty.getString("bio");
                            JSONArray specializationsArray = mJsonObjectProperty.getJSONArray("specialization");
                            JSONArray pricesArray = mJsonObjectProperty.getJSONArray("services_price");

                            ArrayList<String> specializations = new ArrayList<>();
                            Float[] prices = new Float[2];

                            System.out.println("BIIIOO: " + bio);

                            for (int j = 0; j < specializationsArray.length(); j++) {
                                specializations.add(specializationsArray.get(j).toString());
                            }

                            for (int k = 0; k < pricesArray.length(); k++) {
                                prices[k] = (Float.parseFloat(pricesArray.get(k).toString()));
                            }

                            photographers.add(new Photographer(id, name, state, city, profileImage, views, bio, specializations, prices));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        return photographers;
    }

    public Photographer getPhotographer(String idPhotographer) {
        Photographer currentPhotographer = null;

        OkHttpClient client = new OkHttpClient();
        String url = "http://10.0.2.2:3001/user/" + idPhotographer;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bearer " + sessionManager.fetchAuthToken())
                .build();

        try {
            Response response = client.newCall(request).execute();
            JSONObject photographerJsonObject = new JSONObject(response.body().string());

            String id = photographerJsonObject.getJSONObject("_id").getString("$oid");
            String name = photographerJsonObject.getString("name");
            String state = photographerJsonObject.getString("state");
            String city = photographerJsonObject.getString("city");
            String profileImage = photographerJsonObject.getString("profile_img");
            long views = photographerJsonObject.getLong("views");
            String bio = photographerJsonObject.getString("bio").equals("null") ? "" : photographerJsonObject.getString("bio");
            JSONArray specializationsArray = photographerJsonObject.getJSONArray("specialization");
            JSONArray pricesArray = photographerJsonObject.getJSONArray("services_price");

            ArrayList<String> specializations = new ArrayList<>();
            Float[] prices = new Float[2];

            for (int j = 0; j < specializationsArray.length(); j++) {
                specializations.add(specializationsArray.get(j).toString());
            }

            for (int k = 0; k < pricesArray.length(); k++) {
                prices[k] = (Float.parseFloat(pricesArray.get(k).toString()));
            }

            currentPhotographer = new Photographer(id, name, state, city, profileImage, views, bio, specializations, prices);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return currentPhotographer;
    }
}
