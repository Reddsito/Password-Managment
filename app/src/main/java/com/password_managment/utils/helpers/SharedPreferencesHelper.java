package com.password_managment.utils.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private final SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
    }

    // Save a String value
    public void saveString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    // Retrieve a String value
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    // Save an integer value
    public void saveInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    // Retrieve an integer value
    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    // Save a boolean value
    public void saveBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    // Retrieve a boolean value
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // Remove a value
    public void removeValue(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    // Check if a key exists
    public boolean containsKey(String key) {
        return sharedPreferences.contains(key);
    }

    // Clear all preferences
    public void clearAll() {
        sharedPreferences.edit().clear().apply();
    }

}
