package com.example.weatherapp.screen.home;

import com.example.weatherapp.activity.MainRepository;
import com.example.weatherapp.api.APIClient;
import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.DailyForecastsResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void getDailyForecasts() {
        view.setDailyForecasts(MainRepository.getInstance().requestDailyForecasts().getForecasts());
    }

    @Override
    public void getCurrentWeather() {
        view.setCurrentWeather(MainRepository.getInstance().requestCurrentWeather());
    }

    @Override
    public void dropView() {
        view = null;
    }
}
