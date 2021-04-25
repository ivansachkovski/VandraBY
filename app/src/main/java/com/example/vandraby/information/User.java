package com.example.vandraby.information;

import java.security.cert.TrustAnchor;
import java.util.Vector;

public class User {
    public User(String firstName, String lastName, String nickname) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getFollowersNumber() {
        return followersNumber;
    }

    void setFollowersNumber(int followersNumber) {
        this.followersNumber = followersNumber;
    }

    public int getFollowingNumber() {
        return followingNumber;
    }

    void setFollowingNumber(int followingNumber) {
        this.followingNumber = followingNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Vector<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(Vector<Achievement> achievements) {
        this.achievements = achievements;
    }

    public Vector<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Vector<Trip> trips) {
        this.trips = trips;
    }

    private String firstName;
    private String lastName;
    private String nickname;

    int followersNumber;
    int followingNumber;

    private String photoUrl;

    private Vector<Achievement> achievements = new Vector<Achievement>();
    private Vector<Trip> trips = new Vector<Trip>();
}
