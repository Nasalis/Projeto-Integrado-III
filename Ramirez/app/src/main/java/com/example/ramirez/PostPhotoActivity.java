package com.example.ramirez;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ramirez.helpers.Permission;
import com.example.ramirez.helpers.SessionManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostPhotoActivity extends AppCompatActivity {
    private String mimeType;
    private byte[] imgBytes;

    private final String[] necessaryPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_post_photo);

        ImageButton galleryButton = findViewById(R.id.pickImageButton);

        Permission.validatePermissions(necessaryPermissions, this, 1);
        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Bitmap image = null;

                            try {
                                assert result.getData() != null;
                                Uri localImage = result.getData().getData();
                                image = MediaStore.Images.Media.getBitmap(getContentResolver(), localImage);
                                mimeType = getContentResolver().getType(localImage);
                                InputStream inputStream = getContentResolver().openInputStream(result.getData().getData());
                                imgBytes = new byte[inputStream.available()];
                                inputStream.read(imgBytes);

                            }catch (Exception e){
                                // por toast
                            }

                            if (image != null) {
                                galleryButton.setImageBitmap(image);
                            }
                        }
                    }
                });

        galleryButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            resultLauncher.launch(intent);
        });

        sendNewPost();
    }

    public void sendNewPost() {
        View newPostBtn = findViewById(R.id.newPostButton);

        newPostBtn.setOnClickListener(v -> {
            SessionManager sessionManager = new SessionManager(this);

            TextView postTitle = findViewById(R.id.postTitleBtn);
            TextView postPrice = findViewById(R.id.postPriceBtn);

            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", postTitle.getText().toString(), RequestBody.create(MediaType.parse(mimeType), imgBytes))
                    .addFormDataPart("title", postTitle.getText().toString())
                    .addFormDataPart("price", postPrice.getText().toString())
                    .build();

            Request request = new Request.Builder()
                    .url("http://10.0.2.2:3001/posts/")
                    .post(requestBody)
                    .addHeader("Content-Type", "Multipart/FormData")
                    .addHeader("Authorization", "Bearer " + sessionManager.fetchAuthToken())
                    .build();
            try {
                client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}