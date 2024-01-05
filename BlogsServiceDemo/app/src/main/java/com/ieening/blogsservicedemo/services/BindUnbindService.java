package com.ieening.blogsservicedemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ieening.blogsservicedemo.binders.BindUnbindServiceBinder;

public class BindUnbindService extends Service {
    private final String TAG = BindUnbindService.class.getName();

    public BindUnbindService() {
        Log.d(TAG, "executing BindUnbindService constructor");
    }

    @Override
    public void onCreate() {
        // Service初次创建会调用该方法，我们可以做一些初始化操作，与 onDestroy相对
        Log.d(TAG, "executing BindUnbindService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 每次显示启动 Service 都会调用该方法
        Log.d(TAG, "executing BindUnbindService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // 每次销毁 Service 时都会调用该方法，在该方法内，可以添加释放资源的操作，与 onCreate 相对
        Log.d(TAG, "executing BindUnbindService onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) { // ! 返回 Binder 对象
        Log.d(TAG, "executing BindUnbindService onBind");
        return new BindUnbindServiceBinder(this);
    }
}