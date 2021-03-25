package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class CurrentWeather {

    @SerializedName("Temperature")
    private Temperature temperature;
    @SerializedName("RealFeelTemperature")
    private Temperature realFeelTemperature;
    @SerializedName("WeatherText")
    private String weatherText;
    @SerializedName("WeatherIcon")
    private String weatherIcon;
    @SerializedName("PrecipitationType")
    private String precipitationType;
    @SerializedName("RelativeHumidity")
    private int humidity;
    @SerializedName("Wind")
    private Wind wind;
    @SerializedName("UVIndex")
    private int uvIndex;
    @SerializedName("Pressure")
    private Pressure pressure;

    public CurrentWeather(Temperature temperature, Temperature realFeelTemperature, String weatherText,
                          String weatherIcon, String precipitationType, int humidity,
                          Wind wind, int uvIndex, Pressure pressure) {
        this.temperature = temperature;
        this.realFeelTemperature = realFeelTemperature;
        this.weatherText = weatherText;
        this.weatherIcon = weatherIcon;
        this.precipitationType = precipitationType;
        this.humidity = humidity;
        this.wind = wind;
        this.uvIndex = uvIndex;
        this.pressure = pressure;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Temperature getRealFeelTemperature() {
        return realFeelTemperature;
    }

    public void setRealFeelTemperature(Temperature realFeelTemperature) {
        this.realFeelTemperature = realFeelTemperature;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getPrecipitationType() {
        return precipitationType;
    }

    public void setPrecipitationType(String precipitationType) {
        this.precipitationType = precipitationType;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(int uvIndex) {
        this.uvIndex = uvIndex;
    }

    public Pressure getPressure() {
        return pressure;
    }

    public void setPressure(Pressure pressure) {
        this.pressure = pressure;
    }

    private static class Wind {
        @SerializedName("Speed")
        Speed speed;
    }

    private static class Speed {
        @SerializedName("Value")
        Metric metric;
    }

    private static class Pressure {
        @SerializedName("Metric")
        Metric metric;
    }

    private static class Temperature {
        @SerializedName("Metric")
        Metric metric;
    }
}