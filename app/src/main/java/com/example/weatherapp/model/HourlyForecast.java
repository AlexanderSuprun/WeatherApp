package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class HourlyForecast {

    @SerializedName("EpochDateTime")
    private long epochTime;
    @SerializedName("WeatherIcon")
    private int iconNumber;
    @SerializedName("IconPhrase")
    private String forecast;
    @SerializedName("PrecipitationProbability")
    private int precipProbability;
    @SerializedName("Temperature")
    private Temperature temperature;
    @SerializedName("Wind")
    private Wind wind;

    public HourlyForecast(int epochTime, int iconNumber, String forecast, int precipProbability, Temperature temperature, Wind wind) {
        this.epochTime = epochTime;
        this.iconNumber = iconNumber;
        this.forecast = forecast;
        this.precipProbability = precipProbability;
        this.temperature = temperature;
        this.wind = wind;
    }

    public int getIconNumber() {
        return iconNumber;
    }

    public void setIconNumber(int iconNumber) {
        this.iconNumber = iconNumber;
    }

    public long getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(long epochTime) {
        this.epochTime = epochTime;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public float getTemperature() {
        return temperature.value;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public int getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(int precipProbability) {
        this.precipProbability = precipProbability;
    }

    public static class Wind {
        @SerializedName("Speed")
        Speed speed;
        @SerializedName("Direction")
        Direction direction;

        public float getSpeed() {
            return speed.speed;
        }

        public String getDirection() {
            return direction.direction;
        }

    }

    static class Speed {
        @SerializedName("Value")
        float speed;
        @SerializedName("Unit")
        String unit;
    }

    static class Direction {
        @SerializedName("English")
        String direction;

    }

    static class Temperature {
        @SerializedName("Value")
        float value;
    }
}
