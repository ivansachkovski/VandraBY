package com.example.vandraby.activities.main.pages.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.vandraby.R;

public class SearchSettingsPage extends Fragment {

    private SearchSettingsPage() {
    }

    public static SearchSettingsPage newInstance() {
        return new SearchSettingsPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_search_settings, null);
        getActivity().invalidateOptionsMenu();

        return view;
    }
}
