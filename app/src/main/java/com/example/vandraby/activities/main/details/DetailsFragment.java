package com.example.vandraby.activities.main.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.vandraby.R;
import com.example.vandraby.activities.main.swipes.SwipesFragment;
import com.example.vandraby.activities.main.swipes.ViewPagerFragment;
import com.example.vandraby.information.DataModel;
import com.example.vandraby.information.Place;

public class DetailsFragment extends Fragment {
    private final static String LOGGER_TAG = "LOG_VANDRA_DETAILS";
    private static final String ARGUMENT_PREV_PAGE_ID = "arg_prev_page_id";
    private int prevPageId;

    public static DetailsFragment newInstance(Place object, int previousPageId) {
        // TODO::change
        DataModel.place = object;

        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PREV_PAGE_ID, previousPageId);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        prevPageId = getArguments().getInt(ARGUMENT_PREV_PAGE_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, null);

        Place place = DataModel.place;

        Fragment fragment = ViewPagerFragment.newInstance(place.getPhotoUrls());

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.view_pager_fragment, fragment);
        fragmentTransaction.commit();

        TextView textObjectName = view.findViewById(R.id.tv_object_name);
        textObjectName.setText(place.getName());

        TextView textObjectLocation = view.findViewById(R.id.tv_object_location);
        textObjectLocation.setText(place.getLocation());

        TextView textObjectDescription = view.findViewById(R.id.tv_object_description);
        textObjectDescription.setText(place.getDescription());

        TextView textObjectBuildYear = view.findViewById(R.id.tv_object_build_year);
        textObjectBuildYear.setText("" + place.getBuildYear());

        TextView textObjectType = view.findViewById(R.id.tv_object_type);
        textObjectType.setText(place.getType());

        CardView buttonBack = view.findViewById(R.id.btn_back);
        buttonBack.setOnClickListener(v -> {
            openPreviousPage();
        });

        return view;
    }

    private void openPreviousPage() {
        if (prevPageId == 1) {
            openProfilePage();
        } else if (prevPageId == 2) {
            openSwipesPage();
        }
    }

    private void openProfilePage() {
        // TODO::implement
    }

    private void openSwipesPage() {
        loadFragment(SwipesFragment.newInstance());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment, fragment);
        fragmentTransaction.commit();
    }
}