package com.example.hz.soundprofile;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    AudioManager myAudioManager;
    Button button;
    SharedPreferences sharedpreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        boolean IsRunning = isServiceRunning();
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.push_button);
        sharedpreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        boolean bool = sharedpreferences.getBoolean("check", false);

        if (IsRunning) {
            button.setBackgroundResource(R.drawable.button_bg_round);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            button.setText(getString(R.string.run));
        } else {
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            button.setText(getString(R.string.stop));
            button.setBackgroundResource(R.drawable.button_bg_round_off);
        }
    }

    public void onOff(View view) {
        boolean serviceCheck = isServiceRunning();
        if (serviceCheck) {
            stopService(new Intent(getBaseContext(), Check.class));
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            button.setText(getString(R.string.stop));
            button.setBackgroundResource(R.drawable.button_bg_round_off);
        } else {
            button.setBackgroundResource(R.drawable.button_bg_round);
            startService(new Intent(getBaseContext(), Check.class));
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            button.setText(getString(R.string.run));
        }
    }


    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.example.hz.soundprofile.Check".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void allowDnd(View view) {
        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !(notificationManager != null && notificationManager.isNotificationPolicyAccessGranted())) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            Toast.makeText(this,"Enable Auto Profile Setting",Toast.LENGTH_LONG).show();
            startActivity(intent);
        }else{
            Toast.makeText(this,"Already has Permissions",Toast.LENGTH_LONG).show();
        }
    }
}
