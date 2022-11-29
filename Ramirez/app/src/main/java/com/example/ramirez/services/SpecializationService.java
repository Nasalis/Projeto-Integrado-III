package com.example.ramirez.services;

import androidx.annotation.NonNull;

import com.example.ramirez.helpers.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SpecializationService {
    private SessionManager sessionManager;

    public SpecializationService(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public ArrayList<String> getSpecialization() {
        ArrayList<String> specializations = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        String url = "http://10.0.2.2:3001/specializations";

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
                            JSONObject mJsonObjectProperty = jsonArray.getJSONObject(i);

                            String name = mJsonObjectProperty.getString("name");

                            specializations.add(name);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        return specializations;
    }
}
