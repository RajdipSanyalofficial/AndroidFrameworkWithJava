package com.example.weatherapp;

public class WeatherData {

    private String date;
    private String temperature;
    private String description;
    private String icon;

    public WeatherData(String date, String temperature, String description, String icon) {
        this.date = date;
        this.temperature = temperature;
        this.description = description;
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
