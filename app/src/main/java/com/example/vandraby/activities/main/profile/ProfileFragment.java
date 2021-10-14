package com.example.vandraby.activities.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.vandraby.R;
import com.example.vandraby.information.DataModel;
import com.example.vandraby.information.Sight;
import com.example.vandraby.information.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

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

        CircleImageView civUserPhoto = view.findViewById(R.id.user_profile_image);
        Picasso.with(view.getContext()).load(user.getPhotoUrl()).fit().into(civUserPhoto);

        TextView tvFullName = view.findViewById(R.id.user_full_name_view);
        tvFullName.setText(user.getFullName());

        TextView tvNickname = view.findViewById(R.id.user_nickname_view);
        tvNickname.setText("@" + user.getNickname());

        ArrayList<Sight> objects = dataModel.getUserLikedObjects();
        ArrayList<HashMap<String, Object>> data = new ArrayList<>(objects.size());

        for (Sight object: objects) {
            HashMap<String, Object> item = new HashMap<>();
            item.put("name", object.getName());
            item.put("location", object.getLocation());
            data.add(item);
        }

        String[] from = {"name", "location"};
        int[] to = {R.id.tv_name, R.id.tv_location};

        SimpleAdapter likedObjectsAdapter = new SimpleAdapter(getContext(), data, R.layout.list_item, from, to);

        ListView lvLikedObjects = view.findViewById(R.id.lv_liked_objects);
        lvLikedObjects.setAdapter(likedObjectsAdapter);

        return view;
    }
}