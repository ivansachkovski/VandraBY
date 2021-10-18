package com.example.vandraby.information;

import org.json.JSONException;
import org.json.JSONObject;

// TODO::rename all the fields
public class Place {
    private final int id;
    private final String name;
    private final String type;
    private final String description;
    private final String locationRegion;
    private final String locationDistrict;
    private final String locationLocalityName;
    private final String locationLocalityType;
    private final String[] photoUrls;
    private final int buildYear;

    public Place(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");
        name = jsonObject.getString("object_name");
        description = jsonObject.getString("object_description");
        photoUrls = jsonObject.getString("object_photo_urls").split(";");
        type = jsonObject.getString("object_type");
        locationRegion = jsonObject.getString("location_region");
        locationDistrict = jsonObject.getString("location_district");
        locationLocalityType = jsonObject.getString("location_locality_type");
        locationLocalityName = jsonObject.getString("location_locality_name");
        buildYear = jsonObject.getInt("object_build_year");
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

    public String getLocation() {
        return String.format("%s %s, %s р-н, %s обл.", locationLocalityType, locationLocalityName, locationDistrict, locationRegion);
    }

    public int getBuildYear() {
        return buildYear;
    }

    public String[] getPhotoUrls() {
        return photoUrls;
    }
}
