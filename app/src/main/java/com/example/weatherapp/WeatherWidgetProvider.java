package com.example.weatherapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WeatherWidgetProvider extends AppWidgetProvider {

    public static final String UPDATE_ACTION = "com.example.weatherapp.UPDATE_WIDGET";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        context.startService(intent);

        // update every widget installed (needed in case there's more than one)
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, null);
        }
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId, WeatherData weatherData) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        if (weatherData != null) {
            views.setTextViewText(R.id.widgetTemperatureTextView,
                    String.format("%.1f°C", weatherData.getTempNow()));
            views.setImageViewResource(R.id.widgetIconImageView,
                    getWeatherIcon(weatherData.getWeatherCodeNow()));
        }

        // Set up a click handler to open the app when the widget is clicked
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static int getWeatherIcon(int weatherCode) {
        switch (weatherCode) {
            case 0:
                return R.drawable.ic_clear; // ic_clear.png
            case 1:
            case 2:
            case 3:
                return R.drawable.ic_cloudy; // ic_cloudy.png
            case 45:
            case 48:
                return R.drawable.ic_cloudy; // fog.png
            case 51:
            case 53:
            case 55:
                return R.drawable.ic_heavy_rain; // rain.png
            case 56:
            case 57:
                return R.drawable.ic_heavy_rain; // rain.png
            case 61:
            case 63:
            case 65:
                return R.drawable.ic_heavy_rain; // ic_heavy_rain.png
            case 66:
            case 67:
                return R.drawable.ic_thunderstorm; // ic_thunderstorm.png
            case 71:
            case 73:
            case 75:
                return R.drawable.ic_thunderstorm; // ic_thunderstorm.png
            case 77:
                return R.drawable.ic_thunderstorm; // ic_thunderstorm.png
            case 80:
            case 81:
            case 82:
                return R.drawable.ic_heavy_rain; // ic_heavy_rain.png
            case 85:
            case 86:
                return R.drawable.ic_thunderstorm; // ic_thunderstorm.png
            case 95:
                return R.drawable.ic_heavy_rain; // ic_heavy_rain.png
            case 96:
            case 99:
                return R.drawable.ic_thunderstorm; // ic_thunderstorm.png
            default:
                return R.drawable.ic_clear;
        }

    }
}
