package com.example.ramirez.dao;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.ramirez.model.Photographer;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PhotographerDAO implements IPhotographerDAO {
    private static Context context;
    private static PhotographerDAO userDAO = null;
    private ArrayList<Photographer> photographer;

    private PhotographerDAO(Context context) {
        PhotographerDAO.context = context;
        createUserMock();
    }

    public static IPhotographerDAO getInstance(Context context) {
        if (userDAO == null) {
            userDAO = new PhotographerDAO(context);
        }
        return userDAO;
    }

    @Override
    public void createUserMock() {
        ArrayList<String> specializations = new ArrayList<>();
        ArrayList<String> specializations2 = new ArrayList<>();
        ArrayList<String> specializations3 = new ArrayList<>();

        specializations.add("Jornalismo");
        specializations.add("Publicidade");
        specializations2.add("Astronomia");
        specializations3.add("Arquitetura");

        photographer = new ArrayList<>();
        photographer.add(new Photographer("1", "Jéssica Gomez", "SP", "Santos", "123", specializations, new Float[]{20f, 30f}));
        photographer.add(new Photographer("2", "Lucas Gomez", "SP", "Santos", "123", specializations2, new Float[]{40f, 100f}));
        photographer.add(new Photographer("3", "Matheus Gomez", "SP", "Santos", "123", specializations3, new Float[]{100f, 300f}));

    }

    public void getPhotographersFromDatabase() {

        OkHttpClient client = new OkHttpClient();
        String url = "http://10.0.2.2:3001/users";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    System.out.println("DEU CERTOOO AAAAAAAAAAA");
                }
            }
        });
    }

    @Override
    public ArrayList<Photographer> getPhotographer() {
        return photographer;
    }

    public void setUser(ArrayList<Photographer> photographer) {
        this.photographer = photographer;
    }
}