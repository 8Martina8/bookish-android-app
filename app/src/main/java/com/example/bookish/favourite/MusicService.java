package com.example.bookish.favourite;

import static android.os.Build.VERSION_CODES.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.bookish.R;

public class MusicService extends Service {
    private MediaPlayer player;

    public void onCreate() {
//        player = MediaPlayer.create(getApplicationContext(), R.raw.click);
//        player.setLooping(false);
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        player.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}