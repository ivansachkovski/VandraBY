package com.example.vandraby.activities.main.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.vandraby.R;
import com.example.vandraby.information.DataModel;
import com.example.vandraby.information.User;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileSettingsFragment extends Fragment {

    public static ProfileSettingsFragment newInstance() {
        return new ProfileSettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_settings, null);

        DataModel model = DataModel.getInstance();
        User user = model.getCurrentUser();

        CircleImageView imageUserPhoto = view.findViewById(R.id.image_user_photo);
        Picasso.with(getContext()).load(user.getPhotoUrl()).fit().into(imageUserPhoto);

        EditText editLastName = view.findViewById(R.id.edit_last_name);
        editLastName.setText(user.getLastName());

        EditText editFirstName = view.findViewById(R.id.edit_first_name);
        editFirstName.setText(user.getFirstName());

        Button buttonSave = view.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(v -> {
            user.setFirstName(editFirstName.getText().toString());
            user.setLastName(editLastName.getText().toString());
        });

        Button buttonReset = view.findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(v -> {
            model.reset();
        });

        return view;
    }
}
