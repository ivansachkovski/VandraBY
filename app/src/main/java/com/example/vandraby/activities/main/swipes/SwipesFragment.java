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
import com.example.vandraby.information.Sight;

public class SwipesFragment extends Fragment {
    private final SwipeModel model = new SwipeModel();
    CardView cvRoot;
    TextView tvObjectName;

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

        ImageView ivLike = view.findViewById(R.id.button_like);
        ivLike.setOnClickListener(v -> {
            model.swipe();
            update();
        });

        model.loadDataFromDatabase(() -> {
            update();
            return null;
        });

        return view;
    }

    private void update() {
        Sight object = model.getCurrentSight();

        if (object == null) {
            cvRoot.setVisibility(View.INVISIBLE);
            return;
        }

        tvObjectName.setText(object.getName());

        Fragment fragment = ViewPagerFragment.newInstance(object.getPhotoUrls());

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.view_pager_fragment, fragment);
        fragmentTransaction.commit();
    }
}
