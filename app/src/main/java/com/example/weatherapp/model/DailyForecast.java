package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyForecast {

    @SerializedName("EpochDate")
    private int epochDate;
    @SerializedName("Temperature")
    private Temperature temperature;
    @SerializedName("Day")
    private Day forecast;

    public DailyForecast(int epochDate, Temperature temperature, Day forecast) {
        this.epochDate = epochDate;
        this.temperature = temperature;
        this.forecast = forecast;
    }

    public int getEpochDate() {
        return epochDate;
    }

    public void setEpochDate(int epochDate) {
        this.epochDate = epochDate;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Day getForecast() {
        return forecast;
    }

    public void setForecast(Day forecast) {
        this.forecast = forecast;
    }

    public static class Temperature {
        @SerializedName("Minimum")
        Metric metricMin;
        @SerializedName("Maximum")
        Metric metricMax;
    }

    public static class Day {
        @SerializedName("IconPhrase")
        String forecast;
        @SerializedName("HasPrecipitation")
        boolean hasPrecipitation;
        @SerializedName("PrecipitationType")
        String precipitationType;
    }
}