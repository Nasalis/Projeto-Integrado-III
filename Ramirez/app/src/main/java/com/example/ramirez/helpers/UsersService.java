package com.example.ramirez.helpers;

import androidx.annotation.NonNull;

import com.example.ramirez.model.Photographer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UsersService {
    private SessionManager sessionManager;

    public UsersService(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
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
                            JSONArray specializationsArray = mJsonObjectProperty.getJSONArray("specialization");
                            JSONArray pricesArray = mJsonObjectProperty.getJSONArray("services_price");

                            ArrayList<String> specializations = new ArrayList<>();
                            Float[] prices = new Float[2];

                            for (int j = 0; j < specializationsArray.length(); j++) {
                                specializations.add(specializationsArray.get(j).toString());
                            }

                            for (int k = 0; k < pricesArray.length(); k++) {
                                prices[k] = (Float.parseFloat(pricesArray.get(k).toString()));
                            }

                            photographers.add(new Photographer(id, name, state, city, profileImage, specializations, prices));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        return photographers;
    }
}
