package com.example.weatherapp.screen.home;

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
    public void requestDailyForecast(int locationKey) {
        APIClient.getInstance()
                .getApiInterface()
                .get5DaysForecast(locationKey, true)
                .enqueue(new Callback<DailyForecastsResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<DailyForecastsResponse> call,
                                           @NotNull Response<DailyForecastsResponse> response) {
                        if (response.isSuccessful()) {
                            DailyForecastsResponse dailyForecasts = response.body();
                            view.setDailyForecasts(dailyForecasts.getForecasts());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<DailyForecastsResponse> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void requestCurrentWeather(int locationKey) {
        APIClient.getInstance()
                .getApiInterface()
                .getCurrentWeather(locationKey, true, true)
                .enqueue(new Callback<CurrentWeather>() {
                    @Override
                    public void onResponse(@NotNull Call<CurrentWeather> call,
                                           @NotNull Response<CurrentWeather> response) {
                        if (response.isSuccessful()) {
                            CurrentWeather currentWeather = response.body();
                            view.setCurrentWeather(currentWeather);
                        }                    }

                    @Override
                    public void onFailure(@NotNull Call<CurrentWeather> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void dropView() {
        view = null;
    }
}
