package com.example.vandraby.activities.main.profile;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.vandraby.R;
import com.example.vandraby.information.DatabaseHandler;
import com.example.vandraby.information.User;

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

        DatabaseHandler handler = DatabaseHandler.getInstance(null);
        User user = handler.getCurrentUser();

        TextView tvFullName = view.findViewById(R.id.user_full_name_view);
        tvFullName.setText(user.getFullName());

        return view;
    }
}