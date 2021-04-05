package com.example.weatherapp.screen.home;

import com.example.weatherapp.activity.MainPresenter;
import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.DailyForecast;

import java.util.List;

public interface HomeContract {

    interface View {

        void setCurrentWeather(CurrentWeather currentWeather);

        void setDailyForecasts(List<DailyForecast> dailyForecastList);

        void setCity(String city);

        HomeContract.Presenter getPresenter();
    }

    interface Presenter {

        void dropView();

        void setCity(String city);

        void updateDailyForecasts();

        void updateCurrentWeather(CurrentWeather currentWeather);
    }
}
