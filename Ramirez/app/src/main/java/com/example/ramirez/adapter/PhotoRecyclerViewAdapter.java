package com.example.ramirez.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ramirez.R;
import com.example.ramirez.model.Post;

import java.util.List;

public class PhotoRecyclerViewAdapter extends RecyclerView.Adapter<PhotoRecyclerViewAdapter.PhotoViewHolder> {
    private List<Post> posts;

    public PhotoRecyclerViewAdapter(List<Post> posts) {
        this.posts = posts;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView post_image;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.post_image = itemView.findViewById(R.id.postImage);
        }
    }

    public void setComments(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoRecyclerViewAdapter.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_images_adapter, parent, false);

        return new PhotoRecyclerViewAdapter.PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoRecyclerViewAdapter.PhotoViewHolder holder, int position) {
        Post post = this.posts.get(position);

        holder.post_image.setImageBitmap(post.getImage());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
