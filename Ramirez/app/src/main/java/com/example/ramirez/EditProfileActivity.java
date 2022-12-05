package com.example.ramirez;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ramirez.helpers.SessionManager;
import com.example.ramirez.services.UsersService;
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

        SessionManager sessionManager = new SessionManager(this);
        UsersService usersService = UsersService.getInstance(sessionManager);

        Photographer currentPhotographer = usersService.getPhotographer(sessionManager.fetchUserId());

        TextView userName = findViewById(R.id.nameUserEdit);
        TextView userEmail = findViewById(R.id.emailUserEdit);
        TextView userBio = findViewById(R.id.bioEdit);
        TextView userCity = findViewById(R.id.cityEdit);
        TextView userState = findViewById(R.id.stateEdit);
        TextView userMinValue = findViewById(R.id.minValueEdit);
        TextView userMaxValue = findViewById(R.id.maxValueEdit);

        userName.setText(currentPhotographer.getName());
        userEmail.setText(currentPhotographer.getName());
        userBio.setText(currentPhotographer.getBio());
        userCity.setText(currentPhotographer.getCity());
        userState.setText(currentPhotographer.getState());
        userMinValue.setText(Float.toString(currentPhotographer.getMinValue()));
        userMaxValue.setText(Float.toString(currentPhotographer.getMinValue()));

    }
}
