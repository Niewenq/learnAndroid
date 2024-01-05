package com.ieening.blogsservicedemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class StartStopCounterService extends Service {
    private boolean serviceThreadStopFlag = false;

    public static final String COUNT = "count";

    public static final String COUNTER_SERVICE_BROADCAST = "com.ieening.blogsservicedemo.COUNTER_SERVICE_BROADCAST";
    private final String TAG = StartStopCounterService.class.getName();
    private static int count = 0;

    public StartStopCounterService() {
        Log.d(TAG, "executing StartStopCounterService constructor");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        serviceThreadStopFlag = false;

        Thread serviceThread = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                if (serviceThreadStopFlag) {
                    break;
                }
                Log.d(TAG, "executing StartStopCounterService onCreate method, count= " + count);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                count++;
                Intent intent = new Intent(COUNTER_SERVICE_BROADCAST);
                intent.putExtra(COUNT, count);
                intent.setPackage(getPackageName());
                sendBroadcast(intent);
            }
            Log.d(TAG, "executing StartStopCounterService onCreate method, Service stopped");
            stopSelf();
        });
        serviceThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceThreadStopFlag = false;
        count = 0;
        Intent sendedIntent = new Intent(COUNTER_SERVICE_BROADCAST);
        sendedIntent.putExtra(COUNT, count);
        sendedIntent.setPackage(getPackageName()); // 一定需要加上，变为显示广播
        sendBroadcast(sendedIntent);
        Log.d(TAG, "executing StartStopCounterService onStartCommand method");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        serviceThreadStopFlag = true;
        Log.d(TAG, "executing StartStopCounterService onDestroy method");
        super.onDestroy();
    }
}