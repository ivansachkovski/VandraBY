package com.example.vandraby.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Place {

    private long id;

    private String name;
    private String description;
    private String type;
    private long buildYear;

    private ArrayList<String> photoUrls;

    private String locationRegion;
    private String locationDistrict;
    private String locationLocalityType;
    private String locationLocalityName;

    public Place() {
        photoUrls = new ArrayList<>();
    }

    public long getId() {
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

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public long getBuildYear() {
        return buildYear;
    }

    public String getLocationRegion() {
        return locationRegion;
    }

    public String getLocationDistrict() {
        return locationDistrict;
    }

    public String getLocationLocalityType() {
        return locationLocalityType;
    }

    public String getLocationLocalityName() {
        return locationLocalityName;
    }

    public String getFormattedLocation() {
        return String.format("%s %s, %s р-н, %s обл.", locationLocalityType, locationLocalityName, locationDistrict, locationRegion);
    }
}
