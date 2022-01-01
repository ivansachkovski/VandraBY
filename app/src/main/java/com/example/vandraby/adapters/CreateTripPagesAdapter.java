package com.example.vandraby.adapters;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vandraby.R;
import com.example.vandraby.activities.main.pages.createtrip.CreateTripPageContract;
import com.example.vandraby.activities.main.pages.details.PlaceDetailsPage.PlaceDetailsPageListener;
import com.example.vandraby.model.DataModel;

public class CreateTripPagesAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {

    private final CreateTripPageContract.Presenter mPresenter;

    private final static int NUMBER_OF_PAGES = 4;
    private final PlaceDetailsPageListener mPlaceDetailsPageListener;

    public CreateTripPagesAdapter(Fragment fragment, PlaceDetailsPageListener listener, CreateTripPageContract.Presenter presenter) {
        super(fragment);

        mPlaceDetailsPageListener = listener;
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return SelectStartPointPage.newInstance(mPresenter);
            case 1:
                return SelectPlacesPage.newInstance(mPlaceDetailsPageListener);
            case 2:
                return SelectFinishPointPage.newInstance(mPresenter);
            case 3:
                return StartTripPage.newInstance();
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return NUMBER_OF_PAGES;
    }

    public static class SelectStartPointPage extends Fragment {

        CreateTripPageContract.Presenter mPresenter;

        private SelectStartPointPage(CreateTripPageContract.Presenter presenter) {
            mPresenter = presenter;
        }

        public static SelectStartPointPage newInstance(CreateTripPageContract.Presenter presenter) {
            return new SelectStartPointPage(presenter);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_create_trip_1, null);

            Button buttonNext = view.findViewById(R.id.btn_next);
            buttonNext.setOnClickListener(v -> {
                mPresenter.goToTheNextPage();
            });

            return view;
        }
    }

    static public class SelectPlacesPage extends Fragment {

        private final PlaceDetailsPageListener mPlaceDetailsPageListener;

        private SelectPlacesPage(PlaceDetailsPageListener listener) {
            mPlaceDetailsPageListener = listener;
        }

        public static SelectPlacesPage newInstance(PlaceDetailsPageListener listener) {
            return new SelectPlacesPage(listener);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_create_trip_2, null);

            RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setAdapter(new PlacesViewAdapter(DataModel.getInstance().getUserLikedPlaces(), mPlaceDetailsPageListener, 1));

            return view;
        }
    }

    static public class SelectFinishPointPage extends Fragment {

        CreateTripPageContract.Presenter mPresenter;

        private SelectFinishPointPage(CreateTripPageContract.Presenter presenter) {
            mPresenter = presenter;
        }

        public static SelectFinishPointPage newInstance(CreateTripPageContract.Presenter presenter) {
            return new SelectFinishPointPage(presenter);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = (View) inflater.inflate(R.layout.fragment_create_trip_3, null);

            Button buttonNext = view.findViewById(R.id.btn_next);
            buttonNext.setOnClickListener(v -> {
                mPresenter.goToTheNextPage();
            });

            return view;
        }
    }

    static public class StartTripPage extends Fragment {
        public static StartTripPage newInstance() {
            return new StartTripPage();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_create_trip_4, null);

            Button buttonStart = view.findViewById(R.id.button_start);
            buttonStart.setOnClickListener(v -> {
                String url = "yandexmaps://maps.yandex.ru/?mode=routes&rtext=53.905750%2C27.430876~53.846805%2C27.534015~53.866696%2C27.603366~53.922259%2C27.576931&rtt=auto&ruri=~~~&z=12";
                                                      //"/?mode=routes&rtext=53.905750%2C27.430876~53.846805%2C27.534015~53.866696%2C27.603366~53.922259%2C27.576931&rtt=auto&ruri=~~~&z=12"
                Intent intentYandex = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intentYandex.setPackage("ru.yandex.yandexmaps");

                String uriGoogle = "http://maps.google.com/maps?daddr=" + 53.4565895540122f + "," + 26.4732814995463f + "(Один)%20to:" + 53.224064227881335f + "," + 26.7008371351506f + "(" + "Два" + ")";
                Intent intentGoogle = new Intent(Intent.ACTION_VIEW, Uri.parse(uriGoogle));
                intentGoogle.setPackage("com.google.android.apps.maps");

                String title = "Выберите приложение";
                Intent chooserIntent = Intent.createChooser(intentYandex, title);
                Intent[] arr = new Intent[1];
                arr[0] = intentGoogle;
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arr);
                startActivity(chooserIntent);
            });

            Button buttonSave = view.findViewById(R.id.button_save);
            buttonSave.setOnClickListener(v -> {
                // TODO::save
            });

            return view;
        }
    }
}