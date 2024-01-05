package com.ieening.blogsservicedemo.manager;

import android.util.Log;

import java.util.Objects;

public class CountManager {
    private static final String TAG = CountManager.class.getName();
    private static volatile CountManager instance;

    public int getCount() {
        return count;
    }

    private int count = 0;

    private boolean stopCountFlag = false;

    public static CountManager getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (CountManager.class) {
                if (Objects.isNull(instance)) {
                    instance = new CountManager();
                }
            }
        }
        return instance;
    }

    public void startCount() {
        stopCountFlag = false;
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                if (stopCountFlag) {
                    break;
                }
                count++;
                Log.d(TAG, "executing startCount, count=" + count);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void stopCount() {
        stopCountFlag = true;
    }
}
