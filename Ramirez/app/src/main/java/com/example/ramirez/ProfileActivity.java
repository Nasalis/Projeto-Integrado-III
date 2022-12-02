package com.example.ramirez;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ramirez.adapter.PhotoRecyclerViewAdapter;
import com.example.ramirez.dao.PostDAO;
import com.example.ramirez.helpers.RecyclerItemClickListener;
import com.example.ramirez.helpers.SessionManager;
import com.example.ramirez.helpers.UsersService;
import com.example.ramirez.model.Photographer;
import com.example.ramirez.model.Post;

import java.util.List;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    private RecyclerView postRecyclerView;
    private List<Post> posts;
    private PhotoRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_profile_photo);

        this.postRecyclerView = findViewById(R.id.listaDePostagem);
        this.posts = PostDAO.getInstance(getApplicationContext()).getPosts();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("PROFILE_ID");

        SessionManager sessionManager = new SessionManager(this);
        UsersService usersService = UsersService.getInstance(sessionManager);


        Photographer currentPhotographer = usersService.getPhotographers().get(id);

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

        this.adapter = new PhotoRecyclerViewAdapter(this.posts);

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
                                Toast.makeText(ProfileActivity.this,"item selecionado: ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ProfileActivity.this, PostActivity.class);
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

        ImageView editProfileButton = findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            intent.putExtra("EDIT_PROFILE_ID", id);
            startActivity(intent);
        });
    }
}
