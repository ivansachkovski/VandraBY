package com.example.vandraby.model;

public class Presenter implements Contract.Presenter {

    private final Contract.View mView;
    private final Contract.Model mModel;

    public Presenter(Contract.View view) {
        mView = view;
        mModel = DataModel.getInstance();
    }

    @Override
    public void initializeData() {
        mView.showLoadingScreen();
        mModel.loadDataFromDatabase();
    }
}
