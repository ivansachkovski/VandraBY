package com.example.vandraby.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vandraby.R;
import com.example.vandraby.information.Sight;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ObjectsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Sight> items;

    public ObjectsAdapter(List<Sight> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ObjectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_object, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ObjectViewHolder)holder).fill(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ObjectViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageObjectPhoto;
        private final TextView textObjectName;
        private final TextView textObjectLocation;

        ObjectViewHolder(@NonNull View itemView) {
            super(itemView);
            imageObjectPhoto = itemView.findViewById(R.id.object_image);
            textObjectName = itemView.findViewById(R.id.tv_name);
            textObjectLocation = itemView.findViewById(R.id.tv_location);
        }

        void fill(Sight item) {
            Picasso.with(imageObjectPhoto.getContext()).load(item.getPhotoUrls()[0]).fit().into(imageObjectPhoto);
            textObjectName.setText(item.getName());
            textObjectLocation.setText(item.getLocation());
        }
    }
}
