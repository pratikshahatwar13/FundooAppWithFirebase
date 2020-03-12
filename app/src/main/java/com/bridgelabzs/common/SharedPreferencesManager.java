package com.bridgelabzs.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesManager {
    private static final String PREFERENCES_FILE_NAME = "SharedPreferences";
    private static final String TAG = "PreferencesManager";
    private SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    public  void saveLoginDetails(String email, String password){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.EMAIL, email);
        editor.putString(AppConstants.PASSWORD, password);
        editor.putBoolean(AppConstants.IS_USER_LOGIN, true);
        editor.commit();
    }
    public void removeLoginDetails(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
    public boolean isLoggedOut(){
        return sharedPreferences.getAll().isEmpty();
    }

    public void setAccessToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("key", token);
        editor.apply();
    }

    public String getAccessToken() {
        String authKey = sharedPreferences.getString("key", "NULL");
        Log.e(TAG, "getAccessToken: " + authKey);

        return authKey;
    }
}
