package com.example.weatherapp.screen.home;

import com.example.weatherapp.activity.MainRepository;
import com.example.weatherapp.api.APIClient;
import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.DailyForecast;
import com.example.weatherapp.model.DailyForecastsResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void dropView() {
        view = null;
    }
}
