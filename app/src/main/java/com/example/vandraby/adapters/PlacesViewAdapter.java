package com.example.vandraby.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vandraby.R;
import com.example.vandraby.activities.main.pages.details.DetailsFragment.PlaceDetailsPageListener;
import com.example.vandraby.model.Place;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlacesViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Place> items;
    PlaceDetailsPageListener placeDetailsPageListener;

    public PlacesViewAdapter(List<Place> items, PlaceDetailsPageListener listener) {
        this.items = items;
        this.placeDetailsPageListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlaceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_object, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PlaceViewHolder) holder).fillItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Place place;

        private final ImageView imagePhoto;
        private final TextView textName;
        private final TextView textLocation;

        PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePhoto = itemView.findViewById(R.id.object_image);
            textName = itemView.findViewById(R.id.tv_name);
            textLocation = itemView.findViewById(R.id.tv_location);

            itemView.setOnClickListener(this);
        }

        void fillItem(Place item) {
            this.place = item;

            textName.setText(item.getName());
            textLocation.setText(item.getLocation());
            Picasso.with(imagePhoto.getContext()).load(item.getPhotoUrls()[0]).fit().into(imagePhoto);
        }

        @Override
        public void onClick(View v) {
            placeDetailsPageListener.onOpenPlaceDetailsPage(place);
        }
    }
}
