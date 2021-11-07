package com.example.vandraby.activities.main.pages.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.vandraby.R;
import com.example.vandraby.model.DataModel;
import com.example.vandraby.model.User;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchSettingsFragment extends Fragment {

    private SearchSettingsFragment() {
    }

    public static SearchSettingsFragment newInstance() {
        return new SearchSettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_settings, null);
        getActivity().invalidateOptionsMenu();

        return view;
    }
}
