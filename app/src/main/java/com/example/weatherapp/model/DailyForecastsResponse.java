package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyForecastsResponse {
    @SerializedName("DailyForecasts")
    List<DailyForecast> forecasts;
}
