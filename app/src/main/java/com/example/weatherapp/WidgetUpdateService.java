package com.example.weatherapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

public class WidgetUpdateService extends IntentService {

    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        StorageManager storageManager = new StorageManager(this);
        double[] coordinates = storageManager.getCoordinates();

        if (coordinates != null) {
            WeatherService weatherService = new WeatherService(this);
            weatherService.getCurrentWeather(coordinates[0], coordinates[1], new WeatherService.WeatherCallback() {
                @Override
                public void onSuccess(WeatherData weatherData) {
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(WidgetUpdateService.this);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                            new ComponentName(WidgetUpdateService.this, WeatherWidgetProvider.class));
                    for (int appWidgetId : appWidgetIds) {
                        WeatherWidgetProvider.updateAppWidget(WidgetUpdateService.this,
                                appWidgetManager, appWidgetId, weatherData);
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    // need to check what makes it faiL, maybe no internet permsison ?
                }
            });
        }
    }
}
