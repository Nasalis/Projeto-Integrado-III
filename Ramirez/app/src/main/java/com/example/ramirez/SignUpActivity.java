package com.example.ramirez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_sing_up);

        ConstraintLayout photographerData = findViewById(R.id.photographerData);
        photographerData.setVisibility(View.GONE);

        TextView signUpAsPhotographer = findViewById(R.id.signUpAsPhotographer);
        signUpAsPhotographer.setOnClickListener(view -> photographerData.setVisibility(View.VISIBLE));
    }
}