package com.example.weatherapp.activity;

import android.location.Location;

import com.example.weatherapp.api.APIClient;
import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.DailyForecastsResponse;
import com.example.weatherapp.model.HourlyForecast;
import com.example.weatherapp.model.LocationResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository implements MainContract.Repository {

    private static MainRepository mainRepository;
    private int locationKey;
    private List<HourlyForecast> hourlyForecasts;

    private MainRepository() {
    }

    public static MainRepository getInstance() {
        if (mainRepository == null) {
            mainRepository = new MainRepository();
        }
        return mainRepository;
    }

    public LocationResponse requestLocationKey(Location location, OnLocationKeyResult locationKeyResult) {
        final LocationResponse[] locationResponse = new LocationResponse[1];
        APIClient.getInstance()
                .getApiInterface()
                .getLocationKey(String.valueOf(location.getLatitude()) + ',' + location.getLongitude())
                .enqueue(new Callback<LocationResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<LocationResponse> call,
                                           @NotNull Response<LocationResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            locationResponse[0] = response.body();
                            locationKey = response.body().getLocationKey();
                            locationKeyResult.onLocationResult();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<LocationResponse> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
        return locationResponse[0];
    }

    @Override
    public CurrentWeather requestCurrentWeather() {
        final CurrentWeather[] currentWeather = new CurrentWeather[1];
        APIClient.getInstance()
                .getApiInterface()
                .getCurrentWeather(locationKey, true, true)
                .enqueue(new Callback<CurrentWeather>() {
                    @Override
                    public void onResponse(@NotNull Call<CurrentWeather> call,
                                           @NotNull Response<CurrentWeather> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            currentWeather[0] = response.body();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<CurrentWeather> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
        return currentWeather[0];
    }

    @Override
    public DailyForecastsResponse requestDailyForecasts() {
        final DailyForecastsResponse[] dailyForecasts = new DailyForecastsResponse[1];
        APIClient.getInstance()
                .getApiInterface()
                .get5DaysForecast(locationKey, true)
                .enqueue(new Callback<DailyForecastsResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<DailyForecastsResponse> call,
                                           @NotNull Response<DailyForecastsResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            dailyForecasts[0] = response.body();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<DailyForecastsResponse> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
        return dailyForecasts[0];
    }

    @Override
    public List<HourlyForecast> requestHourlyForecasts() {
        hourlyForecasts = new ArrayList<>();
        APIClient.getInstance()
                .getApiInterface()
                .get12HoursForecast(locationKey, true, true)
                .enqueue(new Callback<List<HourlyForecast>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<HourlyForecast>> call,
                                           @NotNull Response<List<HourlyForecast>> response) {
                        hourlyForecasts.addAll(response.body());
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<HourlyForecast>> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
        return hourlyForecasts;
    }

    public interface OnLocationKeyResult {
        void onLocationResult();
    }
}
