package com.example.ramirez.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ramirez.R;
import com.example.ramirez.model.Comment;

import java.util.List;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.CommentViewHolder> {
    private List<Comment> comments;

    public CommentRecyclerViewAdapter(List<Comment> photographers) {
        this.comments = photographers;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView comment_name;
        TextView comment_date;
        TextView comment_text;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            this.comment_name = itemView.findViewById(R.id.commentName);
            this.comment_date = itemView.findViewById(R.id.commentDate);
            this.comment_text = itemView.findViewById(R.id.commentText);
        }
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentRecyclerViewAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_comments_adapter, parent, false);

        return new CommentRecyclerViewAdapter.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentRecyclerViewAdapter.CommentViewHolder holder, int position) {
        Comment comment = this.comments.get(position);

        holder.comment_name.setText(comment.getName());
        holder.comment_date.setText(comment.getDate());
        holder.comment_text.setText(comment.getText());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
