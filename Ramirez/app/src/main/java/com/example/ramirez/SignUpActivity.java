package com.example.ramirez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private boolean isRegisterAsPhotographer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_sing_up);

        ConstraintLayout photographerData = findViewById(R.id.photographerData);
        photographerData.setVisibility(View.GONE);

        TextView signUpAsPhotographer = findViewById(R.id.signUpAsPhotographer);
        Button registerButton = findViewById(R.id.btnRegister);

        signUpAsPhotographer.setOnClickListener(view -> {
            if (isRegisterAsPhotographer) {
                isRegisterAsPhotographer = false;
            } else {
                isRegisterAsPhotographer = true;
            }
            photographerData.setVisibility(View.VISIBLE);
        });

        registerButton.setOnClickListener(button -> registerNewUser());
    }

    private void registerNewUser() {
        TextView userName = findViewById(R.id.registerUserName);
        TextView userEmail = findViewById(R.id.registerEmail);
        TextView userPassword = findViewById(R.id.registerPassword);
        TextView userConfirmPassword = findViewById(R.id.registerConfirmPassword);
        TextView userCity = findViewById(R.id.registerCity);
        TextView userState = findViewById(R.id.registerState);

        JSONObject body = new JSONObject();
        JSONObject user = new JSONObject();

        try {
            user.put("name", userName.getText().toString());
            user.put("email", userEmail.getText().toString());
            user.put("password", userPassword.getText().toString());
            user.put("password_confirmation", userConfirmPassword.getText().toString());
            user.put("city", this.isRegisterAsPhotographer ? userCity.getText().toString() : null);
            body.put("state", this.isRegisterAsPhotographer ? userState.getText().toString() : null);
            body.put("photographer", this.isRegisterAsPhotographer);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();
        String url = "https://dolphin-app-vgvge.ondigitalocean.app/register";

        RequestBody newBody = RequestBody.create(JSON, body.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(newBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("DEU CERTOOOOOOOOO: " + response.body().toString());
        } catch (IOException e) {
            SignUpActivity.this.runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Cadastro falhou! Verifique seus dados", Toast.LENGTH_SHORT).show());
        }

    }
}