package com.example.weatherapp.screen.more;

import com.example.weatherapp.activity.MainRepository;
import com.example.weatherapp.model.CurrentWeather;

public class MorePresenter implements MoreContract.Presenter {

    private MoreContract.View view;

    public MorePresenter(MoreContract.View view) {
        this.view = view;
    }

    @Override
    public void updateHourlyForecast() {
        MainRepository.getInstance().requestHourlyForecasts(hourlyForecastList -> view.setHourlyForecast(hourlyForecastList));
    }

    @Override
    public void updateCurrentWeatherDetails(CurrentWeather currentWeather) {
        view.setCurrentWeatherDetails(currentWeather);
    }

    @Override
    public void dropView() {
        view = null;
    }
}
