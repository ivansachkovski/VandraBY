package com.example.vandraby.activities.main.pages.createtrip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.vandraby.R;
import com.example.vandraby.activities.main.pages.details.PlaceDetailsPage.PlaceDetailsPageListener;
import com.example.vandraby.adapters.CreateTripPagesAdapter;

public class CreateTripPageFragment extends Fragment implements CreateTripPageContract.View {

    private final CreateTripPagePresenter mPresenter;

    private final PlaceDetailsPageListener mPlaceDetailsPageListener;

    private ViewPager2 viewPager2;

    private CreateTripPageFragment(PlaceDetailsPageListener listener) {
        mPresenter = new CreateTripPagePresenter(this);
        mPlaceDetailsPageListener = listener;
    }

    public static CreateTripPageFragment newInstance(PlaceDetailsPageListener listener) {
        return new CreateTripPageFragment(listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_create_trip, null);
        getActivity().invalidateOptionsMenu();

        CreateTripPagesAdapter adapter = new CreateTripPagesAdapter(this, mPlaceDetailsPageListener, mPresenter);

        viewPager2 = view.findViewById(R.id.view_pager);
        viewPager2.setAdapter(adapter);

        return view;
    }

    @Override
    public void goToTheNextPage() {
        viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1, true);
    }
}
