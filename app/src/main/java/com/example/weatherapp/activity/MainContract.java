package com.example.weatherapp.activity;

interface MainContract {

    interface View {

        void showProgress();

        void hideProgress();

        void setCity(String city);

        void showMessageEnableGPS();
    }

    interface Presenter {

        void updateData();

        void dropView();

    }
}
