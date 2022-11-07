package com.example.ramirez.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ramirez.R;
import com.example.ramirez.model.Photographer;

import java.util.List;

public class PhotographerRecyclerViewAdapter extends RecyclerView.Adapter<PhotographerRecyclerViewAdapter.PhotographerViewHolder> {
    private List<Photographer> photographers;

    public PhotographerRecyclerViewAdapter(List<Photographer> photographers) {
        this.photographers = photographers;
    }

    public static class PhotographerViewHolder extends RecyclerView.ViewHolder {
        TextView photographer_id;
        TextView photographer_name;
        TextView photographer_city;
        TextView photographer_state;

        public PhotographerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.photographer_name = itemView.findViewById(R.id.name);
            this.photographer_city = itemView.findViewById(R.id.lacation);
            this.photographer_state = itemView.findViewById(R.id.specialization);
        }
    }

    @NonNull
    @Override
    public PhotographerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_photographer_adapter, parent, false);

        return new PhotographerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotographerViewHolder holder, int position) {
        Photographer photographer = this.photographers.get(position);

        holder.photographer_id.setText(photographer.getId());
        holder.photographer_name.setText(photographer.getName());
        holder.photographer_city.setText(photographer.getCity());
        holder.photographer_state.setText(photographer.getState());
    }

    @Override
    public int getItemCount() {
        return photographers.size();
    }
}
