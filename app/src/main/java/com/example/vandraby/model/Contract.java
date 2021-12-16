package com.example.vandraby.model;

public interface Contract {
    interface View {
        void showLoadingScreen();
        void showProfileScreen();
        void showSwipesScreen();
    }

    interface Model {
        void loadDataFromDatabase();
    }

    interface Presenter {
        void initializeData();
    }
}
