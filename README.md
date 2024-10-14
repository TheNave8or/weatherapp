## Weather App - README

### Overview
This project is a weather app for Android, designed to display weather conditions for the current and future days, manage favorite locations, and interact with location data through custom widgets. Users can view forecasts, save their preferred locations, and detect or manually enter coordinates.

---

### Features
1. **Current Weather Display**:
   - Displays the current temperature, weather description, and feels-like temperature.
   - Icons and text for today and tomorrow’s weather forecasts.

2. **Favorites Management**:
   - Users can **add or remove favorite locations** with a description.
   - A **list view** displays all saved locations.

3. **Location Prompt**:
   - Allows users to **input coordinates manually** or **detect location** automatically.

4. **Widget Support**:
   - Displays weather conditions (icon + temperature) in a **home screen widget** for quick access.

---

### File Structure

#### XML Layout Files
- **`activity_main.xml`**:  
  Main screen with current weather, forecasts, and navigation buttons for favorites and location management.
  
- **`activity_favorites.xml`**:  
  Layout for managing and displaying a list of favorite locations. Includes input fields for name and description, plus buttons to **add** and **remove** favorites.

- **`location_prompt_activity.xml`**:  
  Allows users to enter latitude/longitude or detect their current location using a button click.

- **`widget_layout.xml`**:  
  Defines the layout for the weather widget, showing an icon and temperature on the home screen.

#### Java Files
- **`MainActivity.java`**:  
  Handles displaying weather information on the main screen, including refresh and location-change actions.

- **`FavoritesActivity.java`**:  
  Manages the favorites list: adding new locations, removing all locations, and displaying them in a `ListView`.

- **`LocationPromptActivity.java`**:  
  Implements location management features, including manual input and automatic location detection.

---

### Usage

1. **Launching the App**:
   - On launch, the **main activity** shows the current weather and basic forecasts.
   - Use the **"Favorites" button** to manage your saved locations.

2. **Managing Favorites**:
   - Enter a location name and description, then click **"Add to Favorites"** to save.
   - Use the **"Remove all favorites"** button to clear the list.

3. **Changing Location**:
   - Access the **location prompt screen** to input new coordinates or detect your location automatically.

4. **Using the Widget**:
   - Add the widget to your home screen to see weather updates without opening the app.

---

### Installation and Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/TheNave8or/weatherapp.git
   ```
2. **Open the Project** in Android Studio.
3. **Run the App** on an emulator or physical device.

---

### Dependencies

- **Android SDK**: Ensure Android SDK is installed.
- **Google Play Services**: Required for location detection.

---

### Screenshots

- **Main Activity**: Shows the current weather, forecasts, and navigation buttons.  
- **Favorites Management**: Interface to add or remove favorite locations.  
- **Location Prompt**: Manual entry of coordinates with a button to detect location.