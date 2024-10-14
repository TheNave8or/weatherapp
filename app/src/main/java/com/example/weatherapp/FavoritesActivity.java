package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private static final String TAG = "FavoritesActivity";

    private EditText locationNameEditText;
    private EditText descriptionEditText;
    private Button addButton;
    private ListView favoritesListView;

    private ArrayList<FavoriteLocation> favoriteLocations;
    private ArrayAdapter<String> adapter;
    private DatabaseHelper databaseHelper;
    private StorageManager storageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Log.d(TAG, "onCreate called");
        storageManager = new StorageManager(this);
        locationNameEditText = findViewById(R.id.locationNameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        addButton = findViewById(R.id.addButton);
        favoritesListView = findViewById(R.id.favoritesListView);
        databaseHelper = new DatabaseHelper(this);
        favoriteLocations = databaseHelper.getAllFavoriteLocations();
        ArrayList<String> favoriteNames = new ArrayList<>();
        for (FavoriteLocation location : favoriteLocations) {
            favoriteNames.add(location.getName());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteNames);
        favoritesListView.setAdapter(adapter);
        addButton.setOnClickListener(v -> {
            String name = locationNameEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();

            if (!name.isEmpty()) {
                // get current coordinates from storage
                double[] coordinates = storageManager.getCoordinates();
                if (coordinates == null) {
                    Toast.makeText(this, "No current location available.", Toast.LENGTH_SHORT).show();
                    return;
                }
                double latitude = coordinates[0];
                double longitude = coordinates[1];

                FavoriteLocation location = new FavoriteLocation(name, description, latitude, longitude);
                databaseHelper.addFavoriteLocation(location);
                favoriteLocations.add(location);
                adapter.add(location.getName());
                adapter.notifyDataSetChanged();
                // clear input fields
                locationNameEditText.setText("");
                descriptionEditText.setText("");

                Toast.makeText(this, "Location added to favorites.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Added favorite location: " + name + " at (" + latitude + ", " + longitude + ")");

                Intent intent = new Intent(FavoritesActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please enter a location name.", Toast.LENGTH_SHORT).show();
            }
        });

        favoritesListView.setOnItemClickListener((parent, view, position, id) -> {
            FavoriteLocation selectedLocation = favoriteLocations.get(position);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("selectedLocationName", selectedLocation.getName());
            resultIntent.putExtra("selectedLocationDescription", selectedLocation.getDescription());
            resultIntent.putExtra("selectedLatitude", selectedLocation.getLatitude());
            resultIntent.putExtra("selectedLongitude", selectedLocation.getLongitude());

            // set result and finish
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    public void onDeleteDB(View v) {
        ExpungeDataBase();
        startActivity(new Intent(FavoritesActivity.this, LocationPromptActivity.class));
        finish();
    }

    public void ExpungeDataBase() {
        this.getApplicationContext().deleteDatabase(databaseHelper.getDatabaseName());
    }

}
