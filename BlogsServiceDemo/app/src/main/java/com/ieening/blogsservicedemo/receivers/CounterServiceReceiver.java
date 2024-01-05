package com.ieening.blogsservicedemo.receivers;

import static com.ieening.blogsservicedemo.services.StartStopCounterService.COUNT;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CounterServiceReceiver extends BroadcastReceiver {
    private static final String TAG = CounterServiceReceiver.class.getName();
    public static final String SERVICE_COUNT_ACTION = "com.ieening.blogsservicedemo.COUNTER_SERVICE_COUNT";

    @Override
    public void onReceive(Context context, Intent intent) {
        int count = intent.getIntExtra(COUNT, -1);
        Intent activity_intent = new Intent(SERVICE_COUNT_ACTION);
        activity_intent.putExtra(COUNT, count);
        activity_intent.setPackage(context.getPackageName());
        context.sendBroadcast(activity_intent);
        Log.d(TAG, "executing CounterServiceReceiver onReceive method, count=" + count);
    }
}