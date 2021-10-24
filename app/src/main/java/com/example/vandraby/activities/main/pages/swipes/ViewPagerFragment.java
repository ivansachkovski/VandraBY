package com.example.vandraby.activities.main.pages.swipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.vandraby.R;
import com.example.vandraby.adapters.PlacePhotosAdapter;

import me.relex.circleindicator.CircleIndicator3;

public class ViewPagerFragment extends Fragment {
    private static final String ARGUMENT_PHOTO_URLS = "arg_photo_urls";
    private String[] photoUrls;

    public static ViewPagerFragment newInstance(String[] photoUrls) {
        Bundle arguments = new Bundle();
        arguments.putStringArray(ARGUMENT_PHOTO_URLS, photoUrls);

        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        photoUrls = getArguments().getStringArray(ARGUMENT_PHOTO_URLS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_pager, null);

        PlacePhotosAdapter adapter = new PlacePhotosAdapter(this, photoUrls);

        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(photoUrls.length / 2);
        viewPager.setAdapter(adapter);

        CircleIndicator3 circleIndicator = view.findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);

        return view;
    }
}