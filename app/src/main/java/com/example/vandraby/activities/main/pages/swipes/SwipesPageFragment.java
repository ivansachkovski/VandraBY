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
import com.example.vandraby.activities.main.pages.swipes.extra.ViewPagerFragment;
import com.example.vandraby.model.Place;

public class SwipesPageFragment extends Fragment implements SwipesPageContract.View {

    private final SwipesPagePresenter mPresenter;

    private CardView mViewRoot;
    private TextView mViewPlaceName;
    private TextView mViewPlaceLocation;

    private SwipesPageFragment(PlaceDetailsPageListener listener) {
        mPresenter = new SwipesPagePresenter(this, listener);
    }

    public static SwipesPageFragment newInstance(PlaceDetailsPageListener listener) {
        return new SwipesPageFragment(listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_swipes, null);
        getActivity().invalidateOptionsMenu();

        mViewRoot = view.findViewById(R.id.card_view);
        mViewPlaceName = view.findViewById(R.id.sight_name);
        mViewPlaceLocation = view.findViewById(R.id.tv_object_location);

        ImageView buttonLike = view.findViewById(R.id.btn_like);
        buttonLike.setOnClickListener(v -> {
            mPresenter.likePlace();
        });

        ImageView buttonDislike = view.findViewById(R.id.btn_dislike);
        buttonDislike.setOnClickListener(v -> {
            mPresenter.dislikePlace();
        });

        ImageView buttonDetails = view.findViewById(R.id.btn_details);
        buttonDetails.setOnClickListener(v -> {
            mPresenter.openPlaceDetails();
        });

        mPresenter.invalidateScreen();

        return view;
    }

    @Override
    public void showPlaceToRate(Place place) {
        mViewPlaceName.setText(place.getName());
        mViewPlaceLocation.setText(place.getFormattedLocation());

        Fragment fragment = ViewPagerFragment.newInstance(place.getPhotoUrls());

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.view_pager_fragment, fragment);
        fragmentTransaction.commit();

        mViewRoot.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyScreen() {
        mViewRoot.setVisibility(View.INVISIBLE);
    }
}
