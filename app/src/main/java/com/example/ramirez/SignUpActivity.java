package com.example.ramirez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
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
        EditText userName = findViewById(R.id.registerUserName);
        EditText userEmail = findViewById(R.id.registerEmail);
        EditText userPassword = findViewById(R.id.registerPassword);
        EditText userConfirmPassword = findViewById(R.id.registerConfirmPassword);
        EditText userCity = findViewById(R.id.registerCity);
        EditText userState = findViewById(R.id.registerState);

        JSONObject body = new JSONObject();
        JSONObject user = new JSONObject();

        try {
            user.put("name", userName.getText().toString());
            user.put("email", userEmail.getText().toString());
            user.put("password", userPassword.getText().toString());
            user.put("password_confirmation", userConfirmPassword.getText().toString());
            user.put("city", this.isRegisterAsPhotographer ? userCity.getText().toString() : null);
            user.put("state", this.isRegisterAsPhotographer ? userState.getText().toString() : null);
            user.put("photographer", this.isRegisterAsPhotographer);
            body.put("user", user);

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

        SignUpActivity self = this;

        Thread thread = new Thread(() -> {
            try {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        self.runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Cadastro falhou! Verifique seus dados", Toast.LENGTH_SHORT).show());
                    }
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                            startActivity(intent);
                        } else {
                            self.runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Cadastro falhou! Verifique seus dados", Toast.LENGTH_SHORT).show());
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }
}