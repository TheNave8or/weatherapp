package com.example.weatherapp;

import android.content.Context;
import android.widget.ImageView;

public class BackgroundManager {

//    private Context context;
//
//    public BackgroundManager(Context context) {
//        this.context = context;
//    }

    public void setBackground(ImageView imageView, int weatherCode) {
        int backgroundResId = getBackgroundResource(weatherCode);
        imageView.setImageResource(backgroundResId);
    }

    private int getBackgroundResource(int weatherCode) {
        return R.drawable.clear_sky;
//        switch (weatherCode) {
//            case 0:
//                return R.drawable.ic_clear; // clear.png
//            case 1:
//            case 2:
//            case 3:
//                return R.drawable.partly_cloudy; // partly_cloudy.png
//            case 45:
//            case 48:
//                return R.drawable.fog; // fog.png
//            case 51:
//            case 53:
//            case 55:
//            case 56:
//            case 57:
//                return R.drawable.rain; // rain.png
//            case 61:
//            case 63:
//            case 65:
//            case 80:
//            case 81:
//            case 82:
//            case 95:
//                return R.drawable.heavy_rain; // heavy rain.png
//            case 66:
//            case 67:
//                return R.drawable.thunderstorm; // thunderstorm.png
//            case 71:
//            case 73:
//            case 75:
//                return R.drawable.thunderstorm; // thunderstorm.png
//            case 77:
//                return R.drawable.thunderstorm; // thunderstorm.png
//            case 85:
//            case 86:
//                return R.drawable.thunderstorm; // thunderstorm.png
//            case 96:
//            case 99:
//                return R.drawable.thunderstorm; // thunderstorm.png
//            default:
//                return R.drawable.ic_clear; // default image
//        }
    }
}
