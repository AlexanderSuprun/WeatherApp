package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyForecastsResponse {
    @SerializedName("DailyForecasts")
    List<DailyForecast> forecasts;

    public List<DailyForecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<DailyForecast> forecasts) {
        this.forecasts = forecasts;
    }
}
