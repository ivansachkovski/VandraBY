package com.example.vandraby.activities.main.pages.swipes.extra;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.vandraby.R;
import com.example.vandraby.adapters.PlacePhotosAdapter;

import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class ViewPagerFragment extends Fragment {

    private final List<String> mPhotoUrls;

    private ViewPagerFragment(List<String> photoUrls) {
        mPhotoUrls = photoUrls;
    }

    public static ViewPagerFragment newInstance(List<String> photoUrls) {
        return new ViewPagerFragment(photoUrls);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_pager, null);

        PlacePhotosAdapter adapter = new PlacePhotosAdapter(this, mPhotoUrls);

        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        int limit = (mPhotoUrls.size() > 1) ? mPhotoUrls.size() / 2 : 1;
        viewPager.setOffscreenPageLimit(limit);

        CircleIndicator3 circleIndicator = view.findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);
        if (mPhotoUrls.size() == 1) {
            circleIndicator.setVisibility(View.INVISIBLE);
        }

        return view;
    }
}