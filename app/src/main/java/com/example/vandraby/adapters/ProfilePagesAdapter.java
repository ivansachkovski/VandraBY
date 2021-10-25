package com.example.vandraby.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vandraby.R;
import com.example.vandraby.model.DataModel;

public class ProfilePagesAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {

    private final static int NUMBER_OF_PAGES = 3;

    public ProfilePagesAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return LikedPlacesPage.newInstance();
            case 1:
                return TripsPage.newInstance();
            case 2:
                return StatisticPage.newInstance();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return NUMBER_OF_PAGES;
    }

    // TODO::pass arguments
    static public class LikedPlacesPage extends Fragment {
        public static LikedPlacesPage newInstance() {
            return new LikedPlacesPage();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_liked_places, null);
            view.setAdapter(new PlacesViewAdapter(DataModel.getInstance().getUserLikedPlaces()));
            return view;
        }
    }

    // TODO::pass arguments
    static public class TripsPage extends Fragment {
        public static TripsPage newInstance() {
            return new TripsPage();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_liked_places, null);
            // TODO::implement
            return view;
        }
    }

    // TODO::pass arguments
    static public class StatisticPage extends Fragment {
        public static StatisticPage newInstance() {
            return new StatisticPage();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_liked_places, null);
            // TODO::implement
            return view;
        }
    }
}