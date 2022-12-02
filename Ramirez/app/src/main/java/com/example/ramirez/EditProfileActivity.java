package com.example.ramirez;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ramirez.helpers.SessionManager;
import com.example.ramirez.helpers.UsersService;
import com.example.ramirez.model.Photographer;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_profile_edit);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("EDIT_PROFILE_ID");

        SessionManager sessionManager = new SessionManager(this);
        UsersService usersService = UsersService.getInstance(sessionManager);

        Photographer currentPhotographer = usersService.getPhotographers().get(id);

        TextView userName = findViewById(R.id.editNameUserProfile);

        userName.setText(currentPhotographer.getName());
    }
}
