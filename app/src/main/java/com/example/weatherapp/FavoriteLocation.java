package com.example.weatherapp;

public class FavoriteLocation extends LocationItem {
    private String description;
    private double latitude;
    private double longitude;

    public FavoriteLocation(String name, String description, double latitude, double longitude) {
        super(name, latitude + "," + longitude);
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
