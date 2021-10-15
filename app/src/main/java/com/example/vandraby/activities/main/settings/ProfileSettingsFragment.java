package com.example.vandraby.activities.main.settings;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.vandraby.R;
import com.example.vandraby.activities.main.profile.ProfileFragment;
import com.example.vandraby.information.DataModel;

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
        View view = inflater.inflate(R.layout.fragment_profile_setting, null);

        CardView buttonBack = view.findViewById(R.id.button_back);
        buttonBack.setOnClickListener(v -> {
            loadFragment(ProfileFragment.newInstance());
        });

        Button buttonReset = view.findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(v -> {
            DataModel.getInstance().reset();
        });

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment, fragment);
        fragmentTransaction.commit();
    }
}
