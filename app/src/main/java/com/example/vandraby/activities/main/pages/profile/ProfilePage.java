package com.example.vandraby.activities.main.pages.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.vandraby.R;
import com.example.vandraby.activities.main.pages.details.PlaceDetailsPage.PlaceDetailsPageListener;
import com.example.vandraby.adapters.ProfilePagesAdapter;
import com.example.vandraby.model.DataModel;
import com.example.vandraby.model.User;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePage extends Fragment {

    PlaceDetailsPageListener placeDetailsPageListener;

    private ProfilePage(PlaceDetailsPageListener listener) {
        this.placeDetailsPageListener = listener;
    }

    public static ProfilePage newInstance(PlaceDetailsPageListener listener) {
        return new ProfilePage(listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_profile, null);
        getActivity().invalidateOptionsMenu();

        DataModel dataModel = DataModel.getInstance();
        User user = dataModel.getCurrentUser();

        CircleImageView imageUserPhoto = view.findViewById(R.id.user_profile_image);
        Picasso.with(view.getContext()).load(user.getPhotoUrl()).fit().centerCrop().into(imageUserPhoto);

        TextView textUserFullName = view.findViewById(R.id.user_full_name_view);
        textUserFullName.setText(user.getName());

        TextView textUserNickname = view.findViewById(R.id.user_nickname_view);
        textUserNickname.setText("@" + user.getNickname());

        ProfilePagesAdapter adapter = new ProfilePagesAdapter(this, placeDetailsPageListener);

        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Объекты");
                    break;
                case 1:
                    tab.setText("Маршруты");
                    break;
                case 2:
                    tab.setText("Статистика");
                    break;
            }
        }).attach();

        return view;
    }
}