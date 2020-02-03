package com.example.pushnotifications;

import android.content.Context;
import android.content.SharedPreferences;

class AppDataManager {
    private static final String SHARED_PREFS_NAME = "com.example.pushnotifications";
    private static final String FCM_TOKEN = "FCM_TOKEN";
    private SharedPreferences sharedPreferences;

    AppDataManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    void storeToken(String token) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(FCM_TOKEN, token);
        editor.apply();
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    boolean tokenNotStored() {
        return sharedPreferences.getString(FCM_TOKEN, null) == null;
    }

    String getToken() {
        return sharedPreferences.getString(FCM_TOKEN, null);
    }
}
