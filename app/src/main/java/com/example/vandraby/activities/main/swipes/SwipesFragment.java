package com.example.vandraby.activities.main.swipes;

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
import com.example.vandraby.activities.main.details.DetailsFragment;
import com.example.vandraby.information.DataModel;
import com.example.vandraby.information.Place;

public class SwipesFragment extends Fragment {
    private final SwipeModel model = new SwipeModel(DataModel.getInstance().getObjectsForSwipes());
    CardView cvRoot;
    TextView tvObjectName;
    TextView tvObjectLocation;

    public static SwipesFragment newInstance() {
        return new SwipesFragment();
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
            loadFragment(DetailsFragment.newInstance(model.getCurrentObject(), 2));
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

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
}
