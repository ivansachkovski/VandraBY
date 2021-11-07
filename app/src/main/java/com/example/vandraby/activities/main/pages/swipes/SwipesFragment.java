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
import com.example.vandraby.activities.main.pages.details.DetailsFragment.PlaceDetailsPageListener;
import com.example.vandraby.model.DataModel;
import com.example.vandraby.model.Place;

public class SwipesFragment extends Fragment {
    private final SwipeModel model = new SwipeModel(DataModel.getInstance().getNotSwipedPlaces());
    CardView cvRoot;
    TextView tvObjectName;
    TextView tvObjectLocation;

    PlaceDetailsPageListener placeDetailsPageListener;

    private SwipesFragment(PlaceDetailsPageListener listener) {
        this.placeDetailsPageListener = listener;
    }

    public static SwipesFragment newInstance(PlaceDetailsPageListener listener) {
        return new SwipesFragment(listener);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swipes, null);

        cvRoot = view.findViewById(R.id.card_view);
        tvObjectName = view.findViewById(R.id.sight_name);
        tvObjectLocation = view.findViewById(R.id.tv_object_location);

        ImageView ivLike = view.findViewById(R.id.btn_like);
        ivLike.setOnClickListener(v -> {
            DataModel.getInstance().likeObject(model.getCurrentObject().getId());
            model.swipe();
            showObject();
        });

        ImageView ivDislike = view.findViewById(R.id.btn_dislike);
        ivDislike.setOnClickListener(v -> {
            DataModel.getInstance().dislikeObject(model.getCurrentObject().getId());
            model.swipe();
            showObject();
        });

        ImageView ivDetails = view.findViewById(R.id.btn_details);
        ivDetails.setOnClickListener(v -> {
            placeDetailsPageListener.onOpenPlaceDetailsPage(model.getCurrentObject());
        });

        showObject();

        return view;
    }

    private void showObject() {
        Place object = model.getCurrentObject();

        if (object == null) {
            cvRoot.setVisibility(View.INVISIBLE);
            return;
        }

        tvObjectName.setText(object.getName());
        tvObjectLocation.setText(object.getLocation());

        Fragment fragment = ViewPagerFragment.newInstance(object.getPhotoUrls());

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.view_pager_fragment, fragment);
        fragmentTransaction.commit();
    }
}
