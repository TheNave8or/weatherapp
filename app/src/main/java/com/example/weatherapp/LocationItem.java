package com.example.weatherapp;

public class LocationItem {
    private String name;

    public LocationItem(String name, String coordinates) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
