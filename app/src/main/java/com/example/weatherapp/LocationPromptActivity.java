package com.example.weatherapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class LocationPromptActivity extends AppCompatActivity {

    private EditText latitudeEditText;
    private EditText longitudeEditText;
    private StorageManager storageManager;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_prompt_activity);

        latitudeEditText = findViewById(R.id.latitudeEditText);
        longitudeEditText = findViewById(R.id.longitudeEditText);

        storageManager = new StorageManager(this);
    }

    public void onSaveLocationClicked(View view) {
        String latStr = latitudeEditText.getText().toString();
        String lonStr = longitudeEditText.getText().toString();

        if (!latStr.isEmpty() && !lonStr.isEmpty()) {
            double latitude = Double.parseDouble(latStr);
            double longitude = Double.parseDouble(lonStr);

            storageManager.saveCoordinates(latitude, longitude);
            Utils.redirectToMain(this);
            finish();
        } else {
            Utils.showToast(this, "Please enter valid coordinates.");
        }
    }

    public void onDetectLocationClicked(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // request permission for location
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // getlocation
            Utils.getCurrentLocation(this, new Utils.LocationCallback() {
                @Override
                public void onLocationReceived(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    storageManager.saveCoordinates(latitude, longitude);
                    Utils.redirectToMain(LocationPromptActivity.this);
                    finish();
                }

                @Override
                public void onLocationFailed(String errorMsg) {
                    Utils.showToast(LocationPromptActivity.this, errorMsg);
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onDetectLocationClicked(null);
        } else {
            Utils.showToast(this, "Location permission denied.");
        }
    }
}
