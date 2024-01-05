package com.ieening.blogsservicedemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ieening.blogsservicedemo.binders.BindUnbindServiceBinder;

public class BindUnbindCounterService extends Service {
    private final String TAG = BindUnbindCounterService.class.getName();

    private boolean serviceThreadStopFlag = false;


    public int getCount() {
        return count;
    }

    private int count = 0;


    public BindUnbindCounterService() {
        Log.d(TAG, "executing BindUnbindCounterService constructor");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "executing BindUnbindCounterService onBind");
        return new BindUnbindServiceBinder(this);
    }

    @Override
    public void onCreate() {
        // Service初次创建会调用该方法，我们可以做一些初始化操作，与 onDestroy相对
        Log.d(TAG, "executing BindUnbindCounterService onCreate");
        serviceThreadStopFlag = false;

        super.onCreate();

        Thread serviceThread = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                if (serviceThreadStopFlag) {
                    break;
                }
                Log.d(TAG, "executing BindUnbindCounterService onCreate method, count= " + count);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                count++;
            }
            Log.d(TAG, "executing BindUnbindCounterService onCreate method, Service stopped");
            stopSelf();
        });
        serviceThread.start();
    }

    @Override
    public void onDestroy() {
        serviceThreadStopFlag = true;
        // 每次销毁 Service 时都会调用该方法，在该方法内，可以添加释放资源的操作，与 onCreate 相对
        Log.d(TAG, "executing BindUnbindService onDestroy");
        super.onDestroy();
    }
}