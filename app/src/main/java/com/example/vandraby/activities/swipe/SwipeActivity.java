package com.example.vandraby.activities.swipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.vandraby.BasicActivityWithFooter;
import com.example.vandraby.R;
import com.example.vandraby.activities.objectdetails.ObjectDetailsActivity;
import com.example.vandraby.information.Sight;
import com.google.android.material.tabs.TabLayout;

public class SwipeActivity extends BasicActivityWithFooter {
    SwipeModel model = new SwipeModel();
    SwipeController controller = new SwipeController(model, this);

    private CardView cardView;

    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        cardView = findViewById(R.id.cardView);

        ImageView ivButtonLike = findViewById(R.id.button_like);
        ivButtonLike.setOnClickListener(view -> controller.onLikeButtonClick());

        ImageView ivButtonDislike = findViewById(R.id.button_dislike);
        ivButtonDislike.setOnClickListener(view -> controller.onDislikeButtonClick());

        pager = findViewById(R.id.pager);

        pagerAdapter = new SwipeFragmentPagerAdapter(getSupportFragmentManager(), model);
        pager.setAdapter(pagerAdapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d("qweqweqwe", "onPageSelected, position = " + position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                Log.d("qweqweqwe", "onPageScrolled, position = " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("qweqweqwe", "onPageScrollStateChanged, state = " + state);
            }
        });

        // Give the PagerSlidingTabStrip the ViewPager
        TabLayout tabsStrip = findViewById(R.id.sliding_tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setupWithViewPager(pager);

        controller.run();
    }

    public void onDetailsClick(View v) {
        Intent intent = new Intent(this, ObjectDetailsActivity.class);
        startActivity(intent);
    }

    public void fooUdp() {
        pagerAdapter.notifyDataSetChanged();
    }

    public void updateScreen(Sight sight) {
        TextView tvName = findViewById(R.id.sight_name);
        tvName.setText(sight.getName());

        TextView tvLocation = findViewById(R.id.sight_location);
        tvLocation.setText(sight.getLocation());
    }

    public void blockScreen() {
        cardView.setVisibility(View.INVISIBLE);
    }

    public void unblockScreen() {
        cardView.setVisibility(View.VISIBLE);
    }
}
