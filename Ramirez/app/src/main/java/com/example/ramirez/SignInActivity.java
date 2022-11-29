package com.example.ramirez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

public class SignInActivity extends AppCompatActivity {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private Boolean signed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_sing_in);

        TextView goToSingUpActivity = findViewById(R.id.goToSingUpActivity);
        Button loginBtn = findViewById(R.id.btnLogin);

        goToSingUpActivity.setOnClickListener(view -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(v -> {
            try {
                singIn();
                if (signed) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }


    private void singIn() throws IOException, JSONException {
        Context context = getApplicationContext();

        TextView email = findViewById(R.id.emailSignIn);
        TextView password = findViewById(R.id.passwordSignIn);

        JSONObject body = new JSONObject();
        JSONObject user = new JSONObject();
        user.put("email", email.getText().toString());
        user.put("password", password.getText().toString());
        body.put("user", user);

        OkHttpClient client = new OkHttpClient();
        String url = "http://10.0.2.2:3001/login";

        RequestBody newBody = RequestBody.create(JSON, body.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(newBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    signed = true;
                } else {
                    SignInActivity.this.runOnUiThread(() -> Toast.makeText(context, "Login falhou. Verifique se os dados estão corretos", Toast.LENGTH_SHORT).show());

                }
            }
        });
    }

}