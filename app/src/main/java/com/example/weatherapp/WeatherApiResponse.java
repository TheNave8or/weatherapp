package com.example.weatherapp;

import java.util.List;

public class WeatherApiResponse {
    public double latitude;
    public double longitude;
    public CurrentUnits current_units;
    public Current current;
    public DailyUnits daily_units;
    public Daily daily;

    public static class CurrentUnits {
        public String time;
        public String interval;
        public String temperature_2m;
        public String apparent_temperature;
        public String weather_code;
        public String cloud_cover;
    }

    public static class Current {
        public String time;
        public int interval;
        public double temperature_2m;
        public double apparent_temperature;
        public int weather_code;
        public double cloud_cover;
    }

    public static class DailyUnits {
        public String time;
        public String weather_code;
        public String temperature_2m_max;
        public String temperature_2m_min;
        public String sunset;
        public String daylight_duration;
        public String precipitation_probability_max;
    }

    public static class Daily {
        public List<String> time;
        public List<Integer> weather_code;
        public List<Double> temperature_2m_max;
        public List<Double> temperature_2m_min;
        public List<String> sunset;
        public List<Double> daylight_duration;
        public List<Integer> precipitation_probability_max;
    }

    public WeatherData toWeatherData() {
        WeatherData data = new WeatherData();

        // Set current weather data
        if (current != null) {
            data.setTempNow(current.temperature_2m);
            data.setFeelsLike(current.apparent_temperature);
            data.setWeatherCodeNow(current.weather_code);
        }

        // Set daily weather data
        if (daily != null && daily.time != null && !daily.time.isEmpty()) {
            // Today's data (index 0)
            if (daily.temperature_2m_max != null && daily.temperature_2m_max.size() > 1) {
                data.setTodayMaxTemp(daily.temperature_2m_max.get(1));
                data.setTodayMinTemp(daily.temperature_2m_min.get(1));
                data.setTodayWeatherCode(daily.weather_code.get(1));
                data.setSunsetTime(daily.sunset.get(1));
            }

            // Tomorrow's data (index 2)
            if (daily.temperature_2m_max != null && daily.temperature_2m_max.size() > 2) {
                data.setTomorrowMaxTemp(daily.temperature_2m_max.get(2));
                data.setTomorrowMinTemp(daily.temperature_2m_min.get(2));
                data.setTomorrowWeatherCode(daily.weather_code.get(2));
            }
        }

        return data;
    }
}
