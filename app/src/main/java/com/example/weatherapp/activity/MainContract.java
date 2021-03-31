package com.example.weatherapp.activity;

import android.location.Location;

public interface MainContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showMessageEnableGPS();
    }

    interface Presenter {

        void updateData();

        void dropView();

    }

    interface Repository {

        void requestLocationKey(Location location, MainRepository.OnLocationKeyResult onLocationKeyResult);

        void requestCurrentWeather(MainRepository.OnCurrentWeatherResult onCurrentWeatherResult);

        void requestDailyForecasts(MainRepository.OnDailyForecastsResult onDailyForecastsResult);

        void requestHourlyForecasts(MainRepository.OnHourlyForecastsResult onHourlyForecastsResult);

    }
}
