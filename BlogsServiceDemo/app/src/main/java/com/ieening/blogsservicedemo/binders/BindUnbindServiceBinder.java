package com.ieening.blogsservicedemo.binders;


import android.app.Service;
import android.os.Binder;
import android.util.Log;

public class BindUnbindServiceBinder extends Binder {
    private final String TAG = BindUnbindServiceBinder.class.getName();
    private final Service service;

    public BindUnbindServiceBinder(Service service) { // ! 持有 Service 引用
        Log.d(TAG, "executing BindUnbindServiceBinder constructor");
        this.service = service;
    }

    public Service getService() { // ! 返回 Service 引用
        Log.d(TAG, "executing BindUnbindServiceBinder getService");
        return service;
    }
}
