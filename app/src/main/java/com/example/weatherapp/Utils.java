package com.example.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class Utils {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void redirectToMain(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void redirectToLocationPrompt(Context context) {
        Intent intent = new Intent(context, LocationPromptActivity.class);
        context.startActivity(intent);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void getCurrentLocation(Context context, final LocationCallback callback) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(context)
                    .getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                callback.onLocationReceived(location);
                            } else {
                                callback.onLocationFailed("Unable to get current location.");
                            }
                        }
                    });
        } else {
            callback.onLocationFailed("Location permission not granted.");
        }
    }

    public interface LocationCallback {
        void onLocationReceived(Location location);
        void onLocationFailed(String errorMsg);
    }

    public static String getWeatherDescription(int weatherCode) {
        switch (weatherCode) {
            case 0:
                return "Clear skies";
            case 1:
            case 2:
            case 3:
                return "Partly clear_skies";
            case 45:
            case 48:
                return "Foggy";
            case 51:
            case 53:
            case 55:
                return "Light Drizzle";
            case 56:
            case 57:
                return "Light cold drizzle";
            case 61:
            case 63:
            case 65:
                return "Moderate rain";
            case 66:
            case 67:
                return "Heavy cold rain";
            case 71:
            case 73:
            case 75:
                return "Slight snow fall";
            case 77:
                return "Snow grains";
            case 80:
            case 81:
            case 82:
                return "Violent rain";
            case 85:
            case 86:
                return "Snow showers";
            case 95:
                return "Slight thunderstorm";
            case 96:
            case 99:
                return "Thunderstorm";
            default:
                return "unknown code";
        }
    }

    // Translated getWeatherImage method
    public static int getWeatherImage(int weatherCode) {
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
            case 56:
            case 57:
                return R.drawable.ic_heavy_rain; // rain.png
            case 61:
            case 63:
            case 65:
                return R.drawable.ic_heavy_rain; // ic_heavy_rain.png
            case 66:
            case 67:
            case 71:
            case 73:
            case 75:
            case 85:
            case 86:
            case 96:
            case 99:
            case 77:
                return R.drawable.ic_thunderstorm; // ic_thunderstorm.png
            case 80:
            case 81:
            case 82:
                return R.drawable.ic_heavy_rain; // ic_heavy_rain.png
            case 95:
                return R.drawable.ic_heavy_rain; // ic_heavy_rain.png
            default:
                return R.drawable.ic_clear; // Fallback in case the weather code is not recognized
        }
    }


//public static int checkColor(double temperature) {
//        if (temperature <= 15) {
//            return Color.WHITE;
//        } else if (temperature >= 40) {
//            return Color.RED;
//        } else {
//            float ratio = (float) ((temperature - 15) / (40 - 15));
//            int red = (int) (255 * ratio);
//            int green = (int) (255 * (1 - ratio));
//            return Color.rgb(red, green, 0);
//        }
//    }

public static int checkColor(double temperature) {
    if (temperature <= 15) {
        return 0xFCF14F;
    } else if (temperature >= 15 && temperature < 23) {
        return getColorBasedOnGradient(0xFCF14F, 0xFFED27, 15, 23, temperature);
    } else if (temperature >= 23 && temperature < 45) {
        return getColorBasedOnGradient(0xFFED27, 0xFF1919, 23, 45, temperature);
    } else if (temperature >= 45) {
        return 0xFF1919;
    } else {
        return 0xFFFFFF;
    }
}


public static int getColorBasedOnGradient(int startColor, int endColor, int startTemp, int endTemp, double temperature) {
        int startR = (startColor >> 16) & 0xFF;
        int startG = (startColor >> 8) & 0xFF;
        int startB = startColor & 0xFF;

        int endR = (endColor >> 16) & 0xFF;
        int endG = (endColor >> 8) & 0xFF;
        int endB = endColor & 0xFF;

        double normalizedTemp = (temperature - startTemp) / (double) (endTemp - startTemp);

        int interpolatedR = (int) Math.round(startR + (endR - startR) * normalizedTemp);
        int interpolatedG = (int) Math.round(startG + (endG - startG) * normalizedTemp);
        int interpolatedB = (int) Math.round(startB + (endB - startB) * normalizedTemp);
        // editor's note : fuck this stupid ass language, why do i have to specify RGB
        return Color.rgb(interpolatedR, interpolatedG, interpolatedB);
//        return (interpolatedR << 16) | (interpolatedG << 8) | interpolatedB;
    }
}