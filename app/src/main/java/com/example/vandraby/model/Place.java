package com.example.vandraby.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Place implements Serializable {

    private final int id;

    private final String name;
    private final String description;
    private final String type;
    private final String[] photoUrls;
    private final int buildYear;

    private final String locationRegion;
    private final String locationDistrict;
    private final String locationLocalityType;
    private final String locationLocalityName;

    public Place(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");

        name = jsonObject.getString("place_name");
        description = jsonObject.getString("place_description");
        type = jsonObject.getString("place_type");
        photoUrls = jsonObject.getString("place_photo_urls").split(";");
        buildYear = jsonObject.getInt("place_build_year");

        locationRegion = jsonObject.getString("location_region");
        locationDistrict = jsonObject.getString("location_district");
        locationLocalityType = jsonObject.getString("location_locality_type");
        locationLocalityName = jsonObject.getString("location_locality_name");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String[] getPhotoUrls() {
        return photoUrls;
    }

    public int getBuildYear() {
        return buildYear;
    }

    public String getLocation() {
        return String.format("%s %s, %s р-н, %s обл.", locationLocalityType, locationLocalityName, locationDistrict, locationRegion);
    }
}
