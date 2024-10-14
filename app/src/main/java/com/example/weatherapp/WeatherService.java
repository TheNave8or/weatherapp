package com.example.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {

    private Context context;
    private static final String API_URL_TEMPLATE =
            "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s" +
                    "&current=temperature_2m,apparent_temperature,weather_code,cloud_cover" +
                    "&daily=weather_code,temperature_2m_max,temperature_2m_min,sunset,daylight_duration,precipitation_probability_max" +
                    "&timezone=auto&forecast_days=3";

    public WeatherService(Context context) {
        this.context = context;
    }

    public interface WeatherCallback {
        void onSuccess(WeatherData weatherData);
        void onFailure(Exception e);
    }

    public void getCurrentWeather(double latitude, double longitude, WeatherCallback callback) {
        String apiUrl = String.format(API_URL_TEMPLATE, latitude, longitude);
        Log.d("WeatherService", "API URL: " + apiUrl);
        new FetchWeatherTask(callback).execute(apiUrl);
    }

    private class FetchWeatherTask extends AsyncTask<String, Void, WeatherData> {

        private WeatherCallback callback;
        private Exception exception;

        public FetchWeatherTask(WeatherCallback callback) {
            this.callback = callback;
        }

        @Override
        protected WeatherData doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                if (!Utils.isNetworkAvailable(context)) {
                    throw new Exception("No internet connection.");
                }
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                WeatherApiResponse response = new Gson().fromJson(reader, WeatherApiResponse.class);
                reader.close();

                return response.toWeatherData();
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(WeatherData weatherData) {
            if (exception != null) {
                exception.printStackTrace();
                callback.onFailure(exception);
            } else {
                callback.onSuccess(weatherData);
            }
        }
    }
}
