package com.example.vandraby.activities.main.swipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.vandraby.R;
import com.squareup.picasso.Picasso;

public class ObjectPhotoFragment extends Fragment {
    private static final String ARGUMENT_PHOTO_URL = "arg_photo_url";
    private String photoUrl;

    public static ObjectPhotoFragment newInstance(String photoUrl) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_PHOTO_URL, photoUrl);
        ObjectPhotoFragment objectPhotoFragment = new ObjectPhotoFragment();
        objectPhotoFragment.setArguments(arguments);
        return objectPhotoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        photoUrl = getArguments().getString(ARGUMENT_PHOTO_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_object_photo, null);

        ImageView imageView = view.findViewById(R.id.sight_picture);
        Picasso.with(view.getContext()).load(photoUrl).fit().into(imageView);

        return view;
    }
}