package com.example.hz.soundprofile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompleted extends BroadcastReceiver{
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, Check.class);
        context.startService(serviceIntent);
    }
}