package com.ieening.androidipcserver.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

public class MyService extends Service {
    private static final String TAG = MyService.class.getName();
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AtomicInteger count = new AtomicInteger();
        Thread serviceThread = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                Log.d(TAG, "executing BindUnbindCounterService onCreate method, count= " + count);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                count.getAndIncrement();
            }
            Log.d(TAG, "executing BindUnbindCounterService onCreate method, Service stopped");
            stopSelf();
        });
        serviceThread.start();
    }
}