package com.example.vandraby.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Place {

    private final long mId;

    private final String mName;
    private final String mDescription;
    private final String mType;
    private final long mBuildYear;

    private final List<String> mPhotoUrls;

    private final String mLocationRegion;
    private final String mLocationDistrict;
    private final String mLocationLocalityType;
    private final String mLocationLocalityName;

    public Place() {
        mId = 0;

        mName = "undefined";
        mDescription = "undefined";
        mType = "undefined";
        mBuildYear = 0;

        mPhotoUrls = new ArrayList<>();

        mLocationLocalityType = "undefined";
        mLocationLocalityName = "undefined";
        mLocationDistrict = "undefined";
        mLocationRegion = "undefined";
    }

    public Place(Map<String, Object> value) {
        mId = Long.parseLong(value.get("id").toString());

        mName = value.get("name").toString();
        mDescription = value.get("description").toString();
        mType = value.get("type").toString();
        mPhotoUrls = new ArrayList<>(Arrays.asList(value.get("photoUrls").toString()
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .split(",")));

        mBuildYear = Long.parseLong(value.get("buildYear").toString());

        mLocationRegion = value.get("locationRegion").toString();
        mLocationDistrict = value.get("locationDistrict").toString();
        mLocationLocalityType = value.get("locationLocalityType").toString();
        mLocationLocalityName = value.get("locationLocalityName").toString();
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getType() {
        return mType;
    }

    public String getDescription() {
        return mDescription;
    }

    public List<String> getPhotoUrls() {
        return mPhotoUrls;
    }

    public long getBuildYear() {
        return mBuildYear;
    }

    public String getLocation() {
        return String.format("%s %s, %s р-н, %s обл.", mLocationLocalityType, mLocationLocalityName, mLocationDistrict, mLocationRegion);
    }
}
