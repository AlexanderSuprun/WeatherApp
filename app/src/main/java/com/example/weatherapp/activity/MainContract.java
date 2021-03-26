package com.example.weatherapp.activity;

import android.location.Location;

import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.DailyForecastsResponse;
import com.example.weatherapp.model.HourlyForecast;
import com.example.weatherapp.model.LocationResponse;

import java.util.List;

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

    interface Repository {

        LocationResponse requestLocationKey(Location location, MainRepository.OnLocationKeyResult locationKeyResult);

        CurrentWeather requestCurrentWeather();

        DailyForecastsResponse requestDailyForecasts();

        List<HourlyForecast> requestHourlyForecasts();

    }
}
