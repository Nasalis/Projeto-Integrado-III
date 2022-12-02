package com.example.ramirez;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_profile_edit);


    }
}
