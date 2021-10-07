package com.example.vandraby.activities.main.swipes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ObjectPhotoFragmentAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {
    private final String[] urls;

    public ObjectPhotoFragmentAdapter(Fragment fragment, String[] urls) {
        super(fragment);
        this.urls = urls;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ObjectPhotoFragment.newInstance(urls[position]);
    }

    @Override
    public int getItemCount() {
        return urls.length;
    }
}