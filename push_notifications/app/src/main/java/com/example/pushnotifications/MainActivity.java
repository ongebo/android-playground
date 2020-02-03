package com.example.pushnotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {
    public static final String TOKEN_UPDATE = "Token Update";
    public static final String TOKEN_KEY = "token";
    private BroadcastReceiver receiver;
    private IntentFilter intentFilter;
    private TextView tokenTextView;
    private AppDataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ensureGooglePlayServicesAvailable();
        init();

        if (dataManager.tokenNotStored()) {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            String token = task.getResult().getToken();
                            dataManager.storeToken(token);
                            tokenTextView.setText(token);
                        } else {
                            tokenTextView.setText("Could not obtain token!");
                        }
                    });
        } else {
            tokenTextView.setText(dataManager.getToken());
        }
    }

    private void ensureGooglePlayServicesAvailable() {
        if (playServicesNotAvailable()) {
            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this)
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            finish();
                        }
                    });
        }
    }

    private boolean playServicesNotAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        return resultCode != ConnectionResult.SUCCESS;
    }

    private void init() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() == null) {
                    return;
                }
                if (intent.getAction().equals(TOKEN_UPDATE)) {
                    String token = intent.getStringExtra(TOKEN_KEY);
                    dataManager.storeToken(token);
                    String message = "Token Updated: " + token;
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
            }
        };
        intentFilter = new IntentFilter(TOKEN_UPDATE);
        tokenTextView = findViewById(R.id.token);
        dataManager = new AppDataManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ensureGooglePlayServicesAvailable();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
