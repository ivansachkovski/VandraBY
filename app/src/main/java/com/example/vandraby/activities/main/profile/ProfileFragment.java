package com.example.vandraby.activities.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vandraby.R;
import com.example.vandraby.adapters.ObjectsAdapter;
import com.example.vandraby.information.DataModel;
import com.example.vandraby.information.User;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);

        DataModel dataModel = DataModel.getInstance();
        User user = dataModel.getCurrentUser();

        CircleImageView imageUserPhoto = view.findViewById(R.id.user_profile_image);
        Picasso.with(view.getContext()).load(user.getPhotoUrl()).fit().into(imageUserPhoto);

        TextView textUserFullName = view.findViewById(R.id.user_full_name_view);
        textUserFullName.setText(user.getFullName());

        TextView textUserNickname = view.findViewById(R.id.user_nickname_view);
        textUserNickname.setText("@" + user.getNickname());

        RecyclerView listLikedObjects = view.findViewById(R.id.lv_liked_objects);
        listLikedObjects.setAdapter(new ObjectsAdapter(dataModel.getUserLikedObjects()));

        return view;
    }
}