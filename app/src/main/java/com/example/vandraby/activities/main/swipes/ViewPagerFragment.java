package com.example.vandraby.activities.main.swipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.vandraby.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

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

        ObjectPhotoFragmentAdapter adapter = new ObjectPhotoFragmentAdapter(this, photoUrls);

        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(photoUrls.length / 2);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    tab.setIcon(R.drawable.ic_indicator_circle);
                }).attach();

        return view;
    }
}