package com.example.ramirez;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ramirez.adapter.PhotoRecyclerViewAdapter;
import com.example.ramirez.dao.PhotographerDAO;
import com.example.ramirez.helpers.RecyclerItemClickListener;
import com.example.ramirez.model.Comment;
import com.example.ramirez.model.Photographer;
import com.example.ramirez.model.Post;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private RecyclerView postRecyclerView;
    private List<Post> posts;
    private PhotoRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile_photo);

        this.postRecyclerView = findViewById(R.id.listaDePostagem);
        this.posts = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        Integer index = extras.getInt("PROFILE_ID");

        ArrayList<Photographer> photographers = PhotographerDAO.getInstance(this).getPhotographer();
        Photographer currentPhotographer = photographers.get(index);

        TextView userName = findViewById(R.id.photographerNameProfile);
        TextView userPrices = findViewById(R.id.photographerPrices);
        TextView userSpecialization = findViewById(R.id.photographerSpecializations);

        userName.setText(currentPhotographer.getName());
        userPrices.setText(currentPhotographer.getPrices());
        userSpecialization.setText(currentPhotographer.getSpecializationsAsString());

        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.photo_view);
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment("123", "Affonso Ribeiro", "11/11/2022", "Muito bom!", "aaa"));
        posts.add(new Post("123", "Affonso Ribeiro", 25f, bitmap, comments));

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
