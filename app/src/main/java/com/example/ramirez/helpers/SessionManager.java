package com.example.ramirez.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ramirez.R;

public class SessionManager {
    private SharedPreferences prefs;
    private String USER_TOKEN;
    private String USER_ID;

    public SessionManager(Context context) {
        this.prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        this.USER_TOKEN = "user_token";
        this.USER_ID = "user_id";
    }

    public void saveAuthToken(String token) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }

    public void saveUserId(String id) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_ID, id);
        editor.apply();
    }

    public String fetchAuthToken() {
        return prefs.getString(USER_TOKEN, null);
    }

    public  String fetchUserId() { return prefs.getString(USER_ID, null); }
}