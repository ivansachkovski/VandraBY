package com.example.vandraby.model;

public interface Contract {
    interface View {
        void showLoadingScreen();
        void showProfileScreen();
        void showSwipesScreen();
    }

    interface Model {
        void initialize();
        boolean isReady();
    }

    interface Presenter {
        void initializeData();
    }
}
