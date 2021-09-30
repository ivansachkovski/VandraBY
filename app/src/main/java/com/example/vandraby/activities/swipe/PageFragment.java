package com.example.vandraby.activities.swipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.vandraby.R;
import com.squareup.picasso.Picasso;

public class PageFragment extends Fragment {
    private static final String ARGUMENT_PAGE_URL = "arg_page_url";

    private String photoUrl;

    public static PageFragment newInstance(String photoUrl) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_PAGE_URL, photoUrl);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        photoUrl = getArguments().getString(ARGUMENT_PAGE_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, null);

        ImageView imageView = view.findViewById(R.id.sight_picture);
        Picasso.with(getContext()).load(photoUrl).fit().into(imageView);

        return view;
    }
}