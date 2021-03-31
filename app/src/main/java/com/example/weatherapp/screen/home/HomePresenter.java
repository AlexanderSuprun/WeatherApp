package com.example.weatherapp.screen.home;

import com.example.weatherapp.activity.MainRepository;
import com.example.weatherapp.model.CurrentWeather;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void updateDailyForecasts() {
        MainRepository.getInstance().requestDailyForecasts(dailyForecastList -> view.setDailyForecasts(dailyForecastList));
    }

    @Override
    public void updateCurrentWeather(CurrentWeather currentWeather) {
        view.setCurrentWeather(currentWeather);
    }

    @Override
    public void setCity(String city) {
        view.setCity(city);
    }

    @Override
    public void dropView() {
        view = null;
    }
}
