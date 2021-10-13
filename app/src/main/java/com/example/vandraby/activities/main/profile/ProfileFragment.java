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
import com.example.vandraby.information.User;

import java.util.ArrayList;
import java.util.HashMap;

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

        DataModel handler = DataModel.getInstance();
        User user = handler.getCurrentUser();

        TextView tvFullName = view.findViewById(R.id.user_full_name_view);
        tvFullName.setText(user.getFullName());

        ListView lvLikedObjects = view.findViewById(R.id.lv_liked_objects);
        final String[] names = new String[] {
                "Рыжик", "Барсик", "Мурзик", "Мурка", "Васька",
                "Томасина", "Пушок", "Дымка", "Кузя",
                "Китти", "Масяня", "Симба"
        };

        ArrayList<HashMap<String, Object>> data = new ArrayList<>(
                names.length);

        HashMap<String, Object> map;
        for (int i = 0; i < names.length; ++i) {
            map = new HashMap<>();
            map.put("name", names[i]);
            map.put("location", "location" + i);
            data.add(map);
        }

        // Массив имен атрибутов, из которых будут читаться данные
        String[] from = {"name", "location"};

        // Массив идентификаторов компонентов, в которые будем вставлять данные
        int[] to = {R.id.tv_name, R.id.tv_location};

        SimpleAdapter likedObjectsAdapter = new SimpleAdapter(getContext(), data, R.layout.list_item, from, to);
        lvLikedObjects.setAdapter(likedObjectsAdapter);

        Button btnReset = view.findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });

        return view;
    }
}