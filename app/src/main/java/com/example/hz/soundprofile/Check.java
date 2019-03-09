package com.example.hz.soundprofile;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.IBinder;
import android.widget.Toast;

public class Check extends Service {




    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);

            String action = intent.getAction();
            AudioManager myAudioManager;
            myAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            try {
                if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
                    myAudioManager.setStreamVolume(AudioManager.STREAM_RING, 7, AudioManager.FLAG_ALLOW_RINGER_MODES | AudioManager.FLAG_PLAY_SOUND);
                    boolean check = pref.getBoolean("check", false);
                    if (check) {
                        ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                        toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 1000);
                    }
                } else if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                }
            }catch (Exception e){
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }
    };

    int mStartMode;
    IBinder mBinder;
    boolean mAllowRebind;

    public void onCreate() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(receiver, filter);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return mStartMode;
    }

    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    public void onRebind(Intent intent) {

    }

    public void onDestroy() {
        unregisterReceiver(receiver);
    }
}
