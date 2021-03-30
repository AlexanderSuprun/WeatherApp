package com.example.weatherapp.screen.home;

import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.DailyForecast;

import java.util.List;

public interface HomeContract {

    interface View {

        void setCurrentWeather(CurrentWeather currentWeather);

        void setDailyForecasts(List<DailyForecast> dailyForecastList);

        HomeContract.Presenter getPresenter();
    }

    interface Presenter {

        void dropView();

        void updateDailyForecasts();

        void updateCurrentWeather(CurrentWeather currentWeather);

    }
}
