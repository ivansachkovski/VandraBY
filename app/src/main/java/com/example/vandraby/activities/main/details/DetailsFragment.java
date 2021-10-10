package com.example.vandraby.activities.main.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.vandraby.R;
import com.example.vandraby.information.DatabaseHandler;
import com.example.vandraby.information.Sight;
import com.example.vandraby.information.User;

public class DetailsFragment extends Fragment {
    private Sight object;

    public static DetailsFragment newInstance(Sight object) {
        DatabaseHandler.getInstance(null).sight = object;
        return new DetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, null);

        DatabaseHandler handler = DatabaseHandler.getInstance(null);
        Sight object = DatabaseHandler.getInstance(null).sight;

        TextView tvObjectName = view.findViewById(R.id.tv_object_name);
        tvObjectName.setText(object.getName());

        TextView tvObjectLocation = view.findViewById(R.id.tv_object_location);
        tvObjectLocation.setText(object.getLocation());

        TextView tvObjectDescription = view.findViewById(R.id.tv_object_description);
        tvObjectDescription.setText(object.getDescription());

        return view;
    }
}