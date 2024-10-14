package com.example.weatherapp;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tempNowTextView;
    private TextView feelsLikeTextView;
    private TextView todayMaxTextView;
    private TextView todayMinTextView;
    private TextView tomorrowMaxTextView;
    private TextView tomorrowMinTextView;
    private TextView currentWeatherTextView;
    private TextView sunsetTextView;

    private int color = Utils.checkColor(20);
    /*
     Editor's note : this stupid fucking dumbass sack of shit language uses ARGB format
     for some fucking reason, meaning instead of RGB meaning that you need to pass
     another parameter (Alpha for transparency) if you want your color to be visible.
    */

    private ImageView nowImageView;
    private ImageView todayImageView;
    private ImageView tomorrowImageView;
    private ImageView backgroundImageView;

    private StorageManager storageManager;
    private WeatherService weatherService;
    private BackgroundManager backgroundManager;

    public static final int FAVORITES_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Color check", "color at 20 degrees is " + color);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempNowTextView = findViewById(R.id.tempNowTextView);
        feelsLikeTextView = findViewById(R.id.feelsLikeTextView);
        todayMaxTextView = findViewById(R.id.todayMaxTextView);
        todayMinTextView = findViewById(R.id.todayMinTextView);
        tomorrowMaxTextView = findViewById(R.id.tomorrowMaxTextView);
        tomorrowMinTextView = findViewById(R.id.tomorrowMinTextView);
        currentWeatherTextView = findViewById(R.id.currentWeatherTextView);
        sunsetTextView = findViewById(R.id.sunsetTextView);
        nowImageView = findViewById(R.id.nowImageView);
        todayImageView = findViewById(R.id.todayImageView);
        tomorrowImageView = findViewById(R.id.tomorrowImageView);
        backgroundImageView = findViewById(R.id.backgroundImageView);

        storageManager = new StorageManager(this);
        weatherService = new WeatherService(this);
        backgroundManager = new BackgroundManager();


//        startActivity(new Intent(MainActivity.this, FavoritesActivity.class));

        Button favoritesButton = findViewById(R.id.favoritesButton);
        favoritesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivityForResult(intent, FAVORITES_REQUEST_CODE);
        });

        // check for coordinates in shared preferences
        double[] coordinates = storageManager.getCoordinates();
        if (coordinates == null) {
            Utils.redirectToLocationPrompt(this);
            finish();
        } else {
            fetchWeatherData(coordinates[0], coordinates[1]);
        }
    }

    private void fetchWeatherData(double latitude, double longitude) {
        weatherService.getCurrentWeather(latitude, longitude, new WeatherService.WeatherCallback() {
            @Override
            public void onSuccess(WeatherData data) {
                Log.d("Main Activity", "API call returned, updating information...");
                tempNowTextView.setTextColor(Utils.checkColor(data.getTempNow()));
                tomorrowMaxTextView.setTextColor(Utils.checkColor(data.getTomorrowMaxTemp()));
                todayMaxTextView.setTextColor(Utils.checkColor(data.getTodayMaxTemp()));
                todayMinTextView.setTextColor(Utils.checkColor(data.getTodayMinTemp()));
                tomorrowMinTextView.setTextColor(Utils.checkColor(data.getTomorrowMinTemp()));
                feelsLikeTextView.setTextColor(Color.BLACK);
                currentWeatherTextView.setTextColor(Color.BLACK);
                sunsetTextView.setTextColor(Color.BLACK);


                tempNowTextView.setText(String.format("%.1f°C", data.getTempNow()));
                feelsLikeTextView.setText(String.format("Feels like: %.1f°C", data.getFeelsLike()));
                todayMaxTextView.setText(String.format("📈 %.1f°C", data.getTodayMaxTemp()));
                todayMinTextView.setText(String.format("📉 %.1f°C", data.getTodayMinTemp()));
                tomorrowMaxTextView.setText(String.format("📈 %.1f°C", data.getTomorrowMaxTemp()));
                tomorrowMinTextView.setText(String.format("📉 %.1f°C", data.getTomorrowMinTemp()));
                currentWeatherTextView.setText(Utils.getWeatherDescription(data.getWeatherCodeNow()));

                String sunsetTime = data.getSunsetTime();
                if (sunsetTime != null && sunsetTime.length() >= 16) {
                    sunsetTextView.setText("Sunset at: " + sunsetTime.substring(11, 16));
                } else {
                    sunsetTextView.setText("Sunset at: N/A");
                }
                nowImageView.setImageResource(Utils.getWeatherImage(data.getWeatherCodeNow()));
                todayImageView.setImageResource(Utils.getWeatherImage(data.getTodayWeatherCode()));
                tomorrowImageView.setImageResource(Utils.getWeatherImage(data.getTomorrowWeatherCode()));

                backgroundManager.setBackground(backgroundImageView, data.getWeatherCodeNow());
//                finishAffinity();
            }

            @Override
            public void onFailure(Exception e) {
                Utils.showToast(MainActivity.this, "Failed to fetch weather data: " + e.getMessage());
                Log.d("Main Activity", "Failed to fetch weather data: " + e.getMessage());
            }
        });
    }

    public void onRefreshClicked(View view) {
        double[] coordinates = storageManager.getCoordinates();
        if (coordinates != null) {
            Toast.makeText(this, "Refreshing weather data...", Toast.LENGTH_SHORT).show();
            fetchWeatherData(coordinates[0], coordinates[1]);
        } else {
            Utils.showToast(this, "No location data available.");
        }
    }

    public void onChangeLocationClicked(View view) {
        Utils.redirectToLocationPrompt(this);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FAVORITES_REQUEST_CODE && resultCode == RESULT_OK) {
            String locationName = data.getStringExtra("selectedLocationName");
            String description = data.getStringExtra("selectedLocationDescription");
            double latitude = data.getDoubleExtra("selectedLatitude", 0.0);
            double longitude = data.getDoubleExtra("selectedLongitude", 0.0);

            // fetch info for selected location
            Toast.makeText(this, "Switched to: " + locationName, Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "Received location: " + locationName + ", Description: " + description + ", Coordinates: (" + latitude + ", " + longitude + ")");
            storageManager.saveCoordinates(latitude, longitude);

            /*
                fetchweatherdata also updates the TextViews, so you need to call it
                when you get back from the favorites activity
            */
            fetchWeatherData(latitude, longitude);
        }
    }
}