package com.example.weatherapp;

import android.location.Location;

import com.example.weatherapp.api.ApiClient;
import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.DailyForecast;
import com.example.weatherapp.model.DailyForecastsResponse;
import com.example.weatherapp.model.HourlyForecast;
import com.example.weatherapp.model.LocationResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    private static MainRepository sMainRepository;
    private int mLocationKey;

    private MainRepository() {
    }

    public static MainRepository getInstance() {
        if (sMainRepository == null) {
            sMainRepository = new MainRepository();
        }
        return sMainRepository;
    }

    public void requestLocationKey(Location location, OnLocationKeyResult onLocationKeyResult) {
        ApiClient.getInstance()
                .getApiInterface()
                .getLocationKey(String.valueOf(location.getLatitude()) + ',' + location.getLongitude())
                .enqueue(new Callback<LocationResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<LocationResponse> call,
                                           @NotNull Response<LocationResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            mLocationKey = response.body().getLocationKey();
                            onLocationKeyResult.onLocationResult(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<LocationResponse> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void requestCurrentWeather(OnCurrentWeatherResult onCurrentWeatherResult) {
        ApiClient.getInstance()
                .getApiInterface()
                .getCurrentWeather(mLocationKey, true, true)
                .enqueue(new Callback<List<CurrentWeather>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<CurrentWeather>> call,
                                           @NotNull Response<List<CurrentWeather>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            onCurrentWeatherResult.onCurrentWeatherResult(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<CurrentWeather>> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void requestDailyForecasts(OnDailyForecastsResult onDailyForecastsResult) {
        ApiClient.getInstance()
                .getApiInterface()
                .get5DaysForecast(mLocationKey, true)
                .enqueue(new Callback<DailyForecastsResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<DailyForecastsResponse> call,
                                           @NotNull Response<DailyForecastsResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            onDailyForecastsResult.onDailyForecastsResult(response.body().getForecasts());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<DailyForecastsResponse> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void requestHourlyForecasts(OnHourlyForecastsResult onHourlyForecastsResult) {
        ApiClient.getInstance()
                .getApiInterface()
                .get12HoursForecast(mLocationKey, true, true)
                .enqueue(new Callback<List<HourlyForecast>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<HourlyForecast>> call,
                                           @NotNull Response<List<HourlyForecast>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            onHourlyForecastsResult.onHourlyForecastsResult(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<HourlyForecast>> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public interface OnLocationKeyResult {

        void onLocationResult(LocationResponse locationResponse);

    }

    public interface OnCurrentWeatherResult {

        void onCurrentWeatherResult(List<CurrentWeather> currentWeatherList);

    }

    public interface OnDailyForecastsResult {

        void onDailyForecastsResult(List<DailyForecast> dailyForecastList);

    }

    public interface OnHourlyForecastsResult {

        void onHourlyForecastsResult(List<HourlyForecast> hourlyForecastList);

    }
}
