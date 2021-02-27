package com.example.weatherapp.model;

public class DailyForecastItem {

    private String day;
    private String forecast;
    private int temperatureDay;
    private int temperatureNight;

    public DailyForecastItem(String day, String forecast, int temperatureDay, int temperatureNight) {
        this.day = day;
        this.forecast = forecast;
        this.temperatureDay = temperatureDay;
        this.temperatureNight = temperatureNight;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public int getTemperatureDay() {
        return temperatureDay;
    }

    public void setTemperatureDay(int temperatureDay) {
        this.temperatureDay = temperatureDay;
    }

    public int getTemperatureNight() {
        return temperatureNight;
    }

    public void setTemperatureNight(int temperatureNight) {
        this.temperatureNight = temperatureNight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyForecastItem that = (DailyForecastItem) o;

        if (temperatureDay != that.temperatureDay) return false;
        if (temperatureNight != that.temperatureNight) return false;
        if (!day.equals(that.day)) return false;
        return forecast.equals(that.forecast);
    }

    @Override
    public int hashCode() {
        int result = day.hashCode();
        result = 31 * result + forecast.hashCode();
        result = 31 * result + temperatureDay;
        result = 31 * result + temperatureNight;
        return result;
    }
}
