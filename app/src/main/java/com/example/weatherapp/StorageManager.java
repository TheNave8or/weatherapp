package com.example.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageManager {

    private static final String PREFS_NAME = "WeatherAppPrefs";
    private static final String KEY_LATITUDE = "Latitude";
    private static final String KEY_LONGITUDE = "Longitude";

    private SharedPreferences sharedPreferences;

    public StorageManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveCoordinates(double latitude, double longitude) {
        sharedPreferences.edit()
                .putString(KEY_LATITUDE, String.valueOf(latitude))
                .putString(KEY_LONGITUDE, String.valueOf(longitude))
                .apply();
    }

    public double[] getCoordinates() {
        String latStr = sharedPreferences.getString(KEY_LATITUDE, null);
        String lonStr = sharedPreferences.getString(KEY_LONGITUDE, null);
        if (latStr != null && lonStr != null) {
            return new double[]{Double.parseDouble(latStr), Double.parseDouble(lonStr)};
        }
        return null;
    }
}
