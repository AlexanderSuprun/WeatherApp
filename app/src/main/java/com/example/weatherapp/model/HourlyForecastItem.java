package com.example.weatherapp.model;

public class HourlyForecastItem {

    private String time;
    private String forecast;
    private String windDirection;
    private int temperature;
    private float windSpeed;

    public HourlyForecastItem(String time, String forecast, String windDirection, int temperature, float windSpeed) {
        this.time = time;
        this.forecast = forecast;
        this.windDirection = windDirection;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HourlyForecastItem that = (HourlyForecastItem) o;

        if (temperature != that.temperature) return false;
        if (Float.compare(that.windSpeed, windSpeed) != 0) return false;
        if (!time.equals(that.time)) return false;
        if (!forecast.equals(that.forecast)) return false;
        return windDirection.equals(that.windDirection);
    }

    @Override
    public int hashCode() {
        int result = time.hashCode();
        result = 31 * result + forecast.hashCode();
        result = 31 * result + windDirection.hashCode();
        result = 31 * result + temperature;
        result = 31 * result + (windSpeed != +0.0f ? Float.floatToIntBits(windSpeed) : 0);
        return result;
    }
}
