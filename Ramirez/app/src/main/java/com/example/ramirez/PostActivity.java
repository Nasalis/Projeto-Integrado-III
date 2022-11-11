package com.example.ramirez;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ramirez.adapter.CommentRecyclerViewAdapter;
import com.example.ramirez.adapter.PhotographerRecyclerViewAdapter;
import com.example.ramirez.dao.PhotographerDAO;
import com.example.ramirez.helpers.RecyclerItemClickListener;
import com.example.ramirez.model.Comment;
import com.example.ramirez.model.Photographer;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {
    private RecyclerView commentRecyclerView;
    private List<Comment> comments;
    private CommentRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_post);

        this.commentRecyclerView = findViewById(R.id.recyclerViewComment);
        this.comments = new ArrayList<>();

        comments.add(new Comment("123", "Affonso Ribeiro", "11/11/2022", "Muito bom!", "aaa"));
        comments.add(new Comment("456", "Matias Pinto", "10/11/2022", "Muito bom!", "aaa"));
        comments.add(new Comment("789", "Talita Barcelos", "09/11/2022", "Muito bom!", "aaa"));

        this.adapter = new CommentRecyclerViewAdapter(this.comments);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        commentRecyclerView.setLayoutManager(layoutManager);
        commentRecyclerView.hasFixedSize();
        commentRecyclerView.setAdapter(adapter);

        commentRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        commentRecyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(PostActivity.this,"item selecionado: ", Toast.LENGTH_SHORT).show();
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
