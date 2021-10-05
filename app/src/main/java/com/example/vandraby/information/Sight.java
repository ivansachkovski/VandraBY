package com.example.vandraby.information;

import org.json.JSONException;
import org.json.JSONObject;

public class Sight {
    public int[] photoIds = new int[100];
    private final int id;
    private final String name;
    private final String description;
    private final String location;
    private final String[] photoUrls;

    public Sight(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");
        name = jsonObject.getString("name");
        description = jsonObject.getString("description");
        location = "Брестская область, город Пинск";
        photoUrls = jsonObject.getString("urls").split(";");
        for (int i = 0; i < photoUrls.length; ++i) {
            photoIds[i] = -1;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String[] getPhotoUrls() {
        return photoUrls;
    }
}
