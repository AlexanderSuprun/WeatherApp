package com.example.weatherapp;

import androidx.lifecycle.LiveData;

import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.DailyForecast;
import com.example.weatherapp.model.HourlyForecast;

import java.util.List;

public interface ViewModelContract {

    interface Activity {

        void updateData();

        LiveData<CurrentWeather> getCurrentWeather();

    }

    interface HomeFragment {

        LiveData<String> getCity();

        LiveData<List<DailyForecast>> getDailyForecasts();

        LiveData<CurrentWeather> getCurrentWeather();

    }

    interface MoreFragment {

        LiveData<CurrentWeather> getCurrentWeather();

        LiveData<List<HourlyForecast>> getHourlyForecasts();
    }
}
