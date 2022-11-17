package com.example.ramirez;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ramirez.adapter.CommentRecyclerViewAdapter;
import com.example.ramirez.dao.PostDAO;
import com.example.ramirez.helpers.RecyclerItemClickListener;
import com.example.ramirez.model.Post;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_post);

        RecyclerView commentRecyclerView = findViewById(R.id.recyclerViewComment);

        Bundle extras = getIntent().getExtras();
        Integer index = extras.getInt("POST_ID");

        ArrayList<Post>  posts = PostDAO.getInstance(this).getPosts();
        Post post = posts.get(index);

        TextView photoPrice = findViewById(R.id.photoPrice);
        TextView commentsAmount = findViewById(R.id.commentsAmount);

        photoPrice.setText(String.format("R$ %.2f", post.getPrice()).replace(".", ","));
        commentsAmount.setText(String.format("Comments (%d)", post.getComments().size()));

        CommentRecyclerViewAdapter adapter = new CommentRecyclerViewAdapter(post.getComments());

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
