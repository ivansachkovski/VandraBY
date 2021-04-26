package com.example.vandraby.information;

import org.json.JSONException;
import org.json.JSONObject;

public class Building {
    public Building(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Building(JSONObject jsonObject) {
        try {
            name = (String) jsonObject.get("name");
            photoUrl = (String) jsonObject.get("url");
            description = (String) jsonObject.get("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String name;
    private String photoUrl;
    private String description;
}
