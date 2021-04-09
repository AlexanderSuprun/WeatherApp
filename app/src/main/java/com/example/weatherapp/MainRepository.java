package com.example.weatherapp;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    private final MutableLiveData<Integer> mLocationKey;
    private final MutableLiveData<CurrentWeather> mCurrentWeather;
    private final MutableLiveData<List<DailyForecast>> mDailyForecasts;
    private final MutableLiveData<List<HourlyForecast>> mHourlyForecasts;
    private final MutableLiveData<String> mCity;

    public MainRepository() {
        mLocationKey = new MutableLiveData<>();
        mCurrentWeather = new MutableLiveData<>();
        mDailyForecasts = new MutableLiveData<>();
        mHourlyForecasts = new MutableLiveData<>();
        mCity = new MutableLiveData<>();
    }

    public void requestLocationKey(Location location) {
        ApiClient.getInstance()
                .getApiInterface()
                .getLocationKey(String.valueOf(location.getLatitude()) + ',' + location.getLongitude())
                .enqueue(new Callback<LocationResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<LocationResponse> call,
                                           @NotNull Response<LocationResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            mLocationKey.postValue(response.body().getLocationKey());
                            mCity.postValue(response.body().getCity());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<LocationResponse> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void requestCurrentWeather() {
        ApiClient.getInstance()
                .getApiInterface()
                .getCurrentWeather(mLocationKey.getValue(), true, true)
                .enqueue(new Callback<List<CurrentWeather>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<CurrentWeather>> call,
                                           @NotNull Response<List<CurrentWeather>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            mCurrentWeather.postValue(response.body().get(0));
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<CurrentWeather>> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void requestDailyForecasts() {
        ApiClient.getInstance()
                .getApiInterface()
                .get5DaysForecast(mLocationKey.getValue(), true)
                .enqueue(new Callback<DailyForecastsResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<DailyForecastsResponse> call,
                                           @NotNull Response<DailyForecastsResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            mDailyForecasts.postValue(response.body().getForecasts());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<DailyForecastsResponse> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void requestHourlyForecasts() {
        ApiClient.getInstance()
                .getApiInterface()
                .get12HoursForecast(mLocationKey.getValue(), true, true)
                .enqueue(new Callback<List<HourlyForecast>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<HourlyForecast>> call,
                                           @NotNull Response<List<HourlyForecast>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            mHourlyForecasts.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<HourlyForecast>> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public LiveData<Integer> getLocationKey() {
        return mLocationKey;
    }

    public void setLocationKey(int locationKey) {
        this.mLocationKey.setValue(locationKey);
    }

    public LiveData<String> getCity() {
        return mCity;
    }

    public LiveData<CurrentWeather> getCurrentWeather() {
        return mCurrentWeather;
    }

    public LiveData<List<DailyForecast>> getDailyForecasts() {
        return mDailyForecasts;
    }

    public LiveData<List<HourlyForecast>> getHourlyForecasts() {
        return mHourlyForecasts;
    }
}
