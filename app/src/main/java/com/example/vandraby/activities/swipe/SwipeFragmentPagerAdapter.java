package com.example.vandraby.activities.swipe;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.vandraby.PageFragment;
import com.example.vandraby.information.Sight;

public class SwipeFragmentPagerAdapter extends FragmentStatePagerAdapter {
    SwipeModel swipeModel;

    public SwipeFragmentPagerAdapter(FragmentManager fragmentManager, SwipeModel swipeModel) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.swipeModel = swipeModel;
    }

    @Override
    public PageFragment getItem(int position) {
        Sight sight = swipeModel.getCurrentSight();
        if (sight == null) {
            return PageFragment.newInstance("");
        }
        String[] photoUrls = sight.getPhotoUrls();
        return PageFragment.newInstance(photoUrls[position]);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        Sight sight = swipeModel.getCurrentSight();
        if (sight == null) {
            return 0;
        }
        String[] photoUrls = sight.getPhotoUrls();
        return photoUrls.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return Integer.toString(position + 1);
    }
}