package com.example.vandraby.information;

import com.example.vandraby.requests.RequestQueue;

public class DatabaseImpl {
    private static DatabaseImpl instance = null;
    private User user = null;

    private DatabaseImpl() {

    }

    public static DatabaseImpl getInstance() {
        if (instance == null) {
            instance = new DatabaseImpl();
        }
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
