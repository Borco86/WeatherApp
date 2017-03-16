package com.example.rent.weatherapp;

/**
 * Created by RENT on 2017-03-15.
 */

public class WeatherDetail {
    private String location;
    private String temerature;
    private String day;
    private String skyText;
    private String humidity;
    private String wind;
    private String date;

    public String getHumidity() {
        return humidity;
    }

    public String getWind() {
        return wind;
    }

    public String getDate() {
        return date;
    }

    public String getSkyText() {
        return skyText;
    }

    public String getLocation() {
        return location;
    }

    public String getTemerature() {
        return temerature;
    }

    public String getDay() {
        return day;
    }
}