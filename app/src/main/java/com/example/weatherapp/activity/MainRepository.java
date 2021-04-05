package com.example.weatherapp.activity;

import android.location.Location;
import android.widget.Toast;

import com.example.weatherapp.api.APIClient;
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

public class MainRepository implements MainContract.Repository {

    private static MainRepository mainRepository;
    private int locationKey;

    private MainRepository() {
    }

    public static MainRepository getInstance() {
        if (mainRepository == null) {
            mainRepository = new MainRepository();
        }
        return mainRepository;
    }

    public void requestLocationKey(Location location, OnLocationKeyResult onLocationKeyResult) {
        APIClient.getInstance()
                .getApiInterface()
                .getLocationKey(String.valueOf(location.getLatitude()) + ',' + location.getLongitude())
                .enqueue(new Callback<LocationResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<LocationResponse> call,
                                           @NotNull Response<LocationResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            locationKey = response.body().getLocationKey();
                            onLocationKeyResult.onLocationResult(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<LocationResponse> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void requestCurrentWeather(OnCurrentWeatherResult onCurrentWeatherResult) {
        APIClient.getInstance()
                .getApiInterface()
                .getCurrentWeather(locationKey, true, true)
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

    @Override
    public void requestDailyForecasts(OnDailyForecastsResult onDailyForecastsResult) {
        APIClient.getInstance()
                .getApiInterface()
                .get5DaysForecast(locationKey, true)
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

    @Override
    public void requestHourlyForecasts(OnHourlyForecastsResult onHourlyForecastsResult) {
        APIClient.getInstance()
                .getApiInterface()
                .get12HoursForecast(locationKey, true, true)
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

        void onCurrentWeatherResult(List<CurrentWeather> currentWeather);

    }

    public interface OnDailyForecastsResult {

        void onDailyForecastsResult(List<DailyForecast> dailyForecastList);

    }

    public interface OnHourlyForecastsResult {

        void onHourlyForecastsResult(List<HourlyForecast> hourlyForecastList);

    }
}
