package com.example.pushnotifications;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;

import static com.example.pushnotifications.MainActivity.TOKEN_KEY;
import static com.example.pushnotifications.MainActivity.TOKEN_UPDATE;

public class NotificationService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Intent intent = new Intent(TOKEN_UPDATE);
        intent.putExtra(TOKEN_KEY, token);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }
}
