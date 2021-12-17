package com.example.vandraby.activities.main.pages.swipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.vandraby.R;
import com.example.vandraby.activities.main.pages.details.PlaceDetailsPage.PlaceDetailsPageListener;
import com.example.vandraby.model.DataModel;
import com.example.vandraby.model.Place;

public class SwipesPage extends Fragment {

    private final SwipeModel mSwipeModel;

    CardView mViewRoot;
    TextView mViewPlaceName;
    TextView mViewPlaceLocation;

    PlaceDetailsPageListener mPlaceDetailsPageListener;

    private SwipesPage(PlaceDetailsPageListener listener) {
        this.mPlaceDetailsPageListener = listener;

        mSwipeModel = new SwipeModel(DataModel.getInstance().getUnratedPlaces());
    }

    public static SwipesPage newInstance(PlaceDetailsPageListener listener) {
        return new SwipesPage(listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_swipes, null);
        getActivity().invalidateOptionsMenu();

        mViewRoot = view.findViewById(R.id.card_view);
        mViewPlaceName = view.findViewById(R.id.sight_name);
        mViewPlaceLocation = view.findViewById(R.id.tv_object_location);

        ImageView ivLike = view.findViewById(R.id.btn_like);
        ivLike.setOnClickListener(v -> {
            DataModel.getInstance().likeObject(mSwipeModel.getCurrentObject().getId());
            mSwipeModel.swipe();
            updateScreen();
        });

        ImageView ivDislike = view.findViewById(R.id.btn_dislike);
        ivDislike.setOnClickListener(v -> {
            DataModel.getInstance().dislikeObject(mSwipeModel.getCurrentObject().getId());
            mSwipeModel.swipe();
            updateScreen();
        });

        ImageView ivDetails = view.findViewById(R.id.btn_details);
        ivDetails.setOnClickListener(v -> {
            mPlaceDetailsPageListener.onOpenPlaceDetailsPage(mSwipeModel.getCurrentObject());
        });

        updateScreen();

        return view;
    }

    private void updateScreen() {
        Place place = mSwipeModel.getCurrentObject();
        if (place != null) {
            showPlace(place);
        } else {
            showEmptyScreen();
        }
    }

    private void showPlace(Place place) {
        mViewPlaceName.setText(place.getName());
        mViewPlaceLocation.setText(place.getFormattedLocation());

        Fragment fragment = ViewPagerFragment.newInstance(place.getPhotoUrls());

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.view_pager_fragment, fragment);
        fragmentTransaction.commit();
    }

    private void showEmptyScreen() {
        mViewRoot.setVisibility(View.INVISIBLE);
    }
}
