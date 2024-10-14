package com.example.weatherapp;

public class WeatherData {
    private double tempNow;
    private double feelsLike;
    private int weatherCodeNow;

    private double todayMaxTemp;
    private double todayMinTemp;
    private int todayWeatherCode;
    private String sunsetTime;

    private double tomorrowMaxTemp;
    private double tomorrowMinTemp;
    private int tomorrowWeatherCode;

    // Getters and Setters

    public double getTempNow() {
        return tempNow;
    }

    public void setTempNow(double tempNow) {
        this.tempNow = tempNow;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getWeatherCodeNow() {
        return weatherCodeNow;
    }

    public void setWeatherCodeNow(int weatherCodeNow) {
        this.weatherCodeNow = weatherCodeNow;
    }

    public double getTodayMaxTemp() {
        return todayMaxTemp;
    }

    public void setTodayMaxTemp(double todayMaxTemp) {
        this.todayMaxTemp = todayMaxTemp;
    }

    public double getTodayMinTemp() {
        return todayMinTemp;
    }

    public void setTodayMinTemp(double todayMinTemp) {
        this.todayMinTemp = todayMinTemp;
    }

    public int getTodayWeatherCode() {
        return todayWeatherCode;
    }

    public void setTodayWeatherCode(int todayWeatherCode) {
        this.todayWeatherCode = todayWeatherCode;
    }

    public String getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(String sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public double getTomorrowMaxTemp() {
        return tomorrowMaxTemp;
    }

    public void setTomorrowMaxTemp(double tomorrowMaxTemp) {
        this.tomorrowMaxTemp = tomorrowMaxTemp;
    }

    public double getTomorrowMinTemp() {
        return tomorrowMinTemp;
    }

    public void setTomorrowMinTemp(double tomorrowMinTemp) {
        this.tomorrowMinTemp = tomorrowMinTemp;
    }

    public int getTomorrowWeatherCode() {
        return tomorrowWeatherCode;
    }

    public void setTomorrowWeatherCode(int tomorrowWeatherCode) {
        this.tomorrowWeatherCode = tomorrowWeatherCode;
    }
}
