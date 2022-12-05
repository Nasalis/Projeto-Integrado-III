package com.example.ramirez;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ramirez.adapter.PhotoRecyclerViewAdapter;
import com.example.ramirez.dao.PostDAO;
import com.example.ramirez.helpers.FirebaseHelper;
import com.example.ramirez.helpers.RecyclerItemClickListener;
import com.example.ramirez.helpers.SessionManager;
import com.example.ramirez.services.PostService;
import com.example.ramirez.services.UsersService;
import com.example.ramirez.model.Photographer;
import com.example.ramirez.model.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileUserActivity extends AppCompatActivity {
    private RecyclerView postRecyclerView;
    private List<Post> posts = new ArrayList<>();
    private PhotoRecyclerViewAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_profile_photo_user);

        SessionManager sessionManager = new SessionManager(this);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            // do your stuff
            try {
                getUserPosts(sessionManager);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            mAuth.signInAnonymously().addOnSuccessListener(this, authResult -> {
                // do your stuff
                try {
                    getUserPosts(sessionManager);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            })
            .addOnFailureListener(this, exception -> exception.printStackTrace());
        }

        this.postRecyclerView = findViewById(R.id.listaDePostagem);
        this.posts = PostDAO.getInstance(getApplicationContext()).getPosts();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        UsersService usersService = UsersService.getInstance(sessionManager);
        try {
            getUserPosts(sessionManager);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Photographer currentPhotographer = usersService.getPhotographer(sessionManager.fetchUserId());

        TextView userName = findViewById(R.id.editNameUserProfile);
        TextView userPrices = findViewById(R.id.photographerPrices);
        TextView userSpecialization = findViewById(R.id.photographerSpecializations);
        TextView userBio = findViewById(R.id.photographerBio);
        TextView userViews = findViewById(R.id.txtViewsAmount);

        userName.setText(currentPhotographer.getName());
        userPrices.setText(currentPhotographer.getPrices());
        String bioText = !currentPhotographer.getBio().isEmpty() ? currentPhotographer.getBio() : "Sem informação adicionada...";
        userBio.setText(bioText);
        userSpecialization.setText(currentPhotographer.getSpecializationsAsString());
        String viewsMessage = currentPhotographer.getViews() + " visualizações";
        userViews.setText(viewsMessage);

        buildAdapter();

        ImageView editProfileButton = findViewById(R.id.editProfileButton);
        ImageView publishNewPhoto = findViewById(R.id.publishPostButton);

        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
            intent.putExtra("EDIT_PROFILE_ID", sessionManager.fetchUserId());
            startActivity(intent);
        });

        publishNewPhoto.setOnClickListener(v -> {
            System.out.println("PPPPPPPPPPPPRRRRRRRRRRRRRRRRINNNNNNNNNNNNNNNTTTTTTTT");
            Intent intent = new Intent(getApplicationContext(), PostPhotoActivity.class);
            startActivity(intent);
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        SessionManager sessionManager = new SessionManager(this);
        try {
            getUserPosts(sessionManager);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getUserPosts(SessionManager sessionManager) throws InterruptedException {
        PostService postService = PostService.getInstance(sessionManager);
        StorageReference storageRef = FirebaseHelper.getStorageReference();
        ArrayList<Post> postList =  postService.getPostsOfCurrentUser();

        postList.forEach(post -> storageRef.child(post.getImage()).getDownloadUrl()
            .addOnSuccessListener(uri -> {
                post.setImageUri(uri.toString());
                this.posts.add(post);
            })
            .addOnFailureListener(e -> System.out.println("ERRRRRROOOOOOOOORRRR")));
    }

    public void buildAdapter() {
        if (this.posts.isEmpty()) {
            return;
        }
        this.adapter = new PhotoRecyclerViewAdapter(this.posts, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        postRecyclerView.setLayoutManager(layoutManager);
        postRecyclerView.hasFixedSize();
        postRecyclerView.setAdapter(adapter);

        postRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        postRecyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(ProfileUserActivity.this,"item selecionado: ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ProfileUserActivity.this, PostActivity.class);
                                intent.putExtra("POST_ID", position);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );
    }
}
