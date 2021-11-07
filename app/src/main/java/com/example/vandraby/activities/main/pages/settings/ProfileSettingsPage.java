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

public class ProfileSettingsPage extends Fragment {

    private final OnAccountExitListener onAccountExitListener;

    private ProfileSettingsPage(OnAccountExitListener onAccountExitListener) {
        this.onAccountExitListener = onAccountExitListener;
    }

    public static ProfileSettingsPage newInstance(OnAccountExitListener onAccountExitListener) {
        return new ProfileSettingsPage(onAccountExitListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_profile_settings, null);
        getActivity().invalidateOptionsMenu();

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

        Button buttonExit = view.findViewById(R.id.button_exit);
        buttonExit.setOnClickListener(v -> {
            onAccountExitListener.onAccountExitAction();
        });

        return view;
    }

    public interface OnAccountExitListener {
        void onAccountExitAction();
    }
}
