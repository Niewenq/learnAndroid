package com.ieening.androidipcserver.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CalculatorBinderService extends Service {
    private static final String TAG = CalculatorBinderService.class.getName();

    public CalculatorBinderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "executing CalculatorBinderService onBind method");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "executing CalculatorBinderService onCreate method");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "executing CalculatorBinderService onStartCommand method");
        return super.onStartCommand(intent, flags, startId);
    }

}