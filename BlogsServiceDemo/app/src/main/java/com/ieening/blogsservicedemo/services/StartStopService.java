package com.ieening.blogsservicedemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class StartStopService extends Service {
    private final String TAG = StartStopService.class.getName();


    public StartStopService() {
        Log.d(TAG, "executing StartStopService constructor");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        // 必须重写该方法，该方法为抽象方法。绑定开启 Service 时会调用该方法
        Log.d(TAG, "executing StartStopService onBind method");
        return null;
    }

    @Override
    public void onCreate() {
        // Service初次创建会调用该方法，我们可以做一些初始化操作，与 onDestroy相对
        Log.d(TAG, "executing StartStopService onCreate method");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 每次显示启动 Service 都会调用该方法
        Log.d(TAG, "executing StartStopService onStartCommand method");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // 每次销毁 Service 时都会调用该方法，在该方法内，可以添加释放资源的操作，与 onCreate 相对
        Log.d(TAG, "executing StartStopService onDestroy method");
        super.onDestroy();
    }
}