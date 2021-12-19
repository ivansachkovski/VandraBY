package com.example.vandraby.activities.main.pages.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.vandraby.R;
import com.example.vandraby.activities.main.pages.swipes.extra.ViewPagerFragment;
import com.example.vandraby.model.Place;

public class PlaceDetailsPage extends Fragment {

    private final Place place;

    private PlaceDetailsPage(Place place) {
        this.place = place;
    }

    public static PlaceDetailsPage newInstance(Place place) {
        return new PlaceDetailsPage(place);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_place_details, null);
        getActivity().invalidateOptionsMenu();

        Fragment fragment = ViewPagerFragment.newInstance(place.getPhotoUrls());

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.view_pager_fragment, fragment);
        fragmentTransaction.commit();

        TextView textObjectName = view.findViewById(R.id.tv_object_name);
        textObjectName.setText(place.getName());

        TextView textObjectLocation = view.findViewById(R.id.tv_object_location);
        textObjectLocation.setText(place.getFormattedLocation());

        TextView textObjectDescription = view.findViewById(R.id.tv_object_description);
        textObjectDescription.setText(place.getDescription());

        String buildYearStr = Long.toString(place.getBuildYear());
        TextView textObjectBuildYear = view.findViewById(R.id.tv_object_build_year);
        textObjectBuildYear.setText(buildYearStr);

        TextView textObjectType = view.findViewById(R.id.tv_object_type);
        textObjectType.setText(place.getType());

        return view;
    }

    public interface PlaceDetailsPageListener {
        void onOpenPlaceDetailsPage(Place place);
    }
}