package com.ieening.androidipcserver.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ieening.androidipcserver.services.binders.CalculatorServiceBinder;

public class CalculatorBinderService extends Service {
    private static final String TAG = CalculatorBinderService.class.getName();

    private final CalculatorServiceBinder calculatorServiceBinder = new CalculatorServiceBinder();

    public CalculatorBinderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "executing CalculatorBinderService onBind method");
        return calculatorServiceBinder;
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

    @Override
    public void onDestroy() {
        Log.d(TAG, "executing CalculatorBinderService onDestroy method");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "executing CalculatorBinderService onUnbind method");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "executing CalculatorBinderService onRebind method");
        super.onRebind(intent);
    }
}