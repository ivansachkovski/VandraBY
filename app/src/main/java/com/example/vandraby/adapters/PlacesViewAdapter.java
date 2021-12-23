package com.example.vandraby.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vandraby.R;
import com.example.vandraby.activities.main.pages.details.PlaceDetailsPage.PlaceDetailsPageListener;
import com.example.vandraby.model.Place;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlacesViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Place> mItems;

    private final PlaceDetailsPageListener mPlaceDetailsPageListener;

    private final int mType;

    public PlacesViewAdapter(List<Place> items, PlaceDetailsPageListener listener, int type) {
        mItems = items;
        mPlaceDetailsPageListener = listener;

        mType = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new PlaceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_object, parent, false));
        } else {
            return new Place2ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_object_2, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mType == 0) {
            ((PlaceViewHolder) holder).fillItem(mItems.get(position));
        } else {
            ((Place2ViewHolder) holder).fillItem(mItems.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mType;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Place mPlace;

        private final ImageView mImagePhoto;
        private final TextView mTextName;
        private final TextView mTextLocation;

        PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            mImagePhoto = itemView.findViewById(R.id.object_image);
            mTextName = itemView.findViewById(R.id.tv_name);
            mTextLocation = itemView.findViewById(R.id.tv_location);

            itemView.setOnClickListener(this);
        }

        void fillItem(Place item) {
            mPlace = item;

            mTextName.setText(item.getName());
            mTextLocation.setText(item.getFormattedLocation());
            Picasso.with(mImagePhoto.getContext()).load(item.getPhotoUrls().get(0)).fit().into(mImagePhoto);
        }

        @Override
        public void onClick(View v) {
            mPlaceDetailsPageListener.onOpenPlaceDetailsPage(mPlace);
        }
    }

    class Place2ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Place mPlace;

        private final ImageView mImagePhoto;
        private final TextView mTextName;
        private final TextView mTextLocation;

        Place2ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImagePhoto = itemView.findViewById(R.id.object_image);
            mTextName = itemView.findViewById(R.id.tv_name);
            mTextLocation = itemView.findViewById(R.id.tv_location);

            itemView.setOnClickListener(this);
        }

        void fillItem(Place item) {
            mPlace = item;

            mTextName.setText(item.getName());
            mTextLocation.setText(item.getFormattedLocation());
            Picasso.with(mImagePhoto.getContext()).load(item.getPhotoUrls().get(0)).fit().into(mImagePhoto);
        }

        @Override
        public void onClick(View v) {
            mPlaceDetailsPageListener.onOpenPlaceDetailsPage(mPlace);
        }
    }
}
