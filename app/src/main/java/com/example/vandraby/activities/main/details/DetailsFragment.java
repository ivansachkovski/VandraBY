package com.example.vandraby.activities.main.details;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.vandraby.R;
import com.example.vandraby.activities.main.swipes.ObjectPhotoFragment;
import com.example.vandraby.activities.main.swipes.SwipesFragment;
import com.example.vandraby.information.DataModel;
import com.example.vandraby.information.Sight;

public class DetailsFragment extends Fragment {
    private final static String LOGGER_TAG = "LOG_VANDRA_DETAILS";
    private static final String ARGUMENT_PREV_PAGE_ID = "arg_prev_page_id";
    private int prevPageId;

    public static DetailsFragment newInstance(Sight object, int previousPageId) {
        // TODO::change
        DataModel.sight = object;

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

        Sight object = DataModel.sight;

        TextView tvObjectName = view.findViewById(R.id.tv_object_name);
        tvObjectName.setText(object.getName());

        TextView tvObjectLocation = view.findViewById(R.id.tv_object_location);
        tvObjectLocation.setText(object.getLocation());

        TextView tvObjectDescription = view.findViewById(R.id.tv_object_description);
        tvObjectDescription.setText(object.getDescription());

        TextView tvObjectBuildYear = view.findViewById(R.id.tv_object_build_year);
        tvObjectBuildYear.setText("" + object.getBuildYear());

        TextView tvObjectType = view.findViewById(R.id.tv_object_type);
        tvObjectType.setText(object.getType());

        CardView btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
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