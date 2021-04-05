package com.example.weatherapp.screen.more;

import com.example.weatherapp.activity.MainPresenter;
import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.HourlyForecast;

import java.util.List;

public interface MoreContract {

    interface View {

        void setHourlyForecast(List<HourlyForecast> hourlyForecastList);

        void setCurrentWeatherDetails(CurrentWeather currentWeather);

        MoreContract.Presenter getPresenter();

    }

    interface Presenter {

        void dropView();

        void updateHourlyForecast();

        void updateCurrentWeatherDetails(CurrentWeather currentWeather);

    }
}
