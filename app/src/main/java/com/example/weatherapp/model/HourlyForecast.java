package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class HourlyForecast {

    @SerializedName("EpochDateTime")
    private int epochTime;
    @SerializedName("IconPhrase")
    private String forecast;
    @SerializedName("Temperature")
    private Temperature temperature;
    @SerializedName("Wind")
    private Wind wind;

    public HourlyForecast(int epochTime, String forecast, Temperature temperature, Wind wind) {
        this.epochTime = epochTime;
        this.forecast = forecast;
        this.temperature = temperature;
        this.wind = wind;
    }

    public int getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(int epochTime) {
        this.epochTime = epochTime;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public Temperature getTemperature() {
        return temperature;
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

    public static class Wind {
        @SerializedName("Speed")
        Speed speed;
        @SerializedName("Direction")
        Direction direction;

        public Speed getSpeed() {
            return speed;
        }

        public void setSpeed(Speed speed) {
            this.speed = speed;
        }

        public Direction getDirection() {
            return direction;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }
    }

    public static class Speed {
        @SerializedName("Value")
        float speed;
        @SerializedName("Unit")
        String unit;

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }

    public static class Direction {
        @SerializedName("English")
        String direction;

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }
    }

    public static class Temperature {
        @SerializedName("Metric")
        Metric metric;

        public Metric getMetric() {
            return metric;
        }

        public void setMetric(Metric metric) {
            this.metric = metric;
        }
    }

}
