package com.example.ramirez.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ramirez.R;

public class SessionManager {
    private SharedPreferences prefs;
    private String USER_TOKEN;

    public SessionManager(Context context) {
        this.prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        this.USER_TOKEN = "user_token";
    }

    public void saveAuthToken(String token) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }

    public String fetchAuthToken() {
        return prefs.getString(USER_TOKEN, null);
    }
}