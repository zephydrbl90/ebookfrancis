package com.example.ebookfrancis;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

public class SensorActivity extends AppCompatActivity {

    private TextView lightLevelText;
    private static final int REQUEST_WRITE_SETTINGS_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        lightLevelText = findViewById(R.id.lightLevelText);
        checkAndRequestWriteSettingsPermission();

        lightLevelText.setOnClickListener(view -> adjustScreenBrightness());
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {
                Log.d("Permission Check", "WRITE_SETTINGS permission granted.");
                adjustScreenBrightness(); // Permission granted, proceed
            } else {
                Log.e("Permission Check", "WRITE_SETTINGS permission NOT granted.");
                lightLevelText.setText("Permission denied. Enable it in Settings.");
            }
        }
    }


    private void checkAndRequestWriteSettingsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Log.d("Permission Check", "Requesting WRITE_SETTINGS permission...");
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent); // Redirect user to system settings
            } else {
                Log.d("Permission Check", "WRITE_SETTINGS permission already granted.");
                adjustScreenBrightness(); // Proceed to adjust brightness
            }
        } else {
            Log.d("Permission Check", "WRITE_SETTINGS not needed for this API level.");
            adjustScreenBrightness();
        }
    }


    private void changeScreenBrightness(int brightnessValue) {
        ContentResolver resolver = getContentResolver();
        try {
            Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightnessValue);
            Log.d("Screen Brightness", "Brightness set to: " + brightnessValue);
        } catch (SecurityException e) {
            Log.e("Screen Brightness", "Failed to set brightness: " + e.getMessage());
        }
    }

    @SuppressLint("SetTextI18n")
    private void adjustScreenBrightness() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, intentFilter);

        if (batteryStatus != null) {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int batteryPct = (int) ((level / (float) scale) * 100);

            Log.d("Battery Status", "Battery Level: " + batteryPct + "%");

            int brightness;
            if (batteryPct > 70) {
                brightness = 255;
            } else if (batteryPct > 30) {
                brightness = 127;
            } else {
                brightness = 50;
            }

            if (Settings.System.canWrite(this)) {
                changeScreenBrightness(brightness);
                lightLevelText.setText("Battery Level: " + batteryPct + "%\nScreen Brightness: " + brightness);
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }
    }
}
