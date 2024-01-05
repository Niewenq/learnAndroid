package com.ieening.blogsservicedemo;

import static com.ieening.blogsservicedemo.receivers.CounterServiceReceiver.SERVICE_COUNT_ACTION;
import static com.ieening.blogsservicedemo.services.StartStopCounterService.COUNT;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.ieening.blogsservicedemo.databinding.ActivityMainBinding;
import com.ieening.blogsservicedemo.manager.CountManager;
import com.ieening.blogsservicedemo.services.BindUnbindCounterService;
import com.ieening.blogsservicedemo.services.BindUnbindService;
import com.ieening.blogsservicedemo.binders.BindUnbindServiceBinder;
import com.ieening.blogsservicedemo.services.StartStopCounterService;
import com.ieening.blogsservicedemo.services.StartStopService;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final String TAG = MainActivity.class.getName();

    private CounterServiceReceiver counterServiceReceiver;

    private BindUnbindCounterService bindUnbindCounterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.startServiceButton.setOnClickListener(v -> {
            Log.d(TAG, "click start service button to start service");
            // ! 显示开启 Service
            Intent intent = new Intent(getApplicationContext(), StartStopService.class);
            startService(intent);

            v.setEnabled(false);
            binding.restartServiceButton.setEnabled(true);
            binding.stopServiceButton.setEnabled(true);
        });

        binding.restartServiceButton.setOnClickListener(v -> {
            Log.d(TAG, "click restart service button to restart service");
            // ! 显示重启 Service
            Intent intent = new Intent(getApplicationContext(), StartStopService.class);
            startService(intent);
        });

        binding.stopServiceButton.setOnClickListener(v -> {
            Log.d(TAG, "click stop service button to stop service");
            // ! 显示停止 Service
            Intent stopIntent = new Intent(this, StartStopService.class);
            stopService(stopIntent);

            v.setEnabled(false);
            binding.startServiceButton.setEnabled(true);
            binding.restartServiceButton.setEnabled(false);
        });


        // ! 绑定 Activity 和 Service
        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "executing ServiceConnection onServiceConnected");
                BindUnbindServiceBinder bindUnbindServiceBinder = (BindUnbindServiceBinder) service;
                // 获取 MyService 引用
                BindUnbindService bindUnbindService = (BindUnbindService) bindUnbindServiceBinder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "executing ServiceConnection onServiceDisconnected");
                // Service 被销毁时调用（内存不足等，正常解绑不会走这）
            }
        };

        binding.bindServiceButton.setOnClickListener(v -> {
            Log.d(TAG, "click bind service button to bind service");
            Intent bindIntent = new Intent(this, BindUnbindService.class);
            bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);

            binding.bindServiceButton.setEnabled(false);
            binding.unbindServiceButton.setEnabled(true);
        });

        binding.unbindServiceButton.setOnClickListener(v -> {
            Log.d(TAG, "click unbind service button to unbind service");
            unbindService(serviceConnection);

            binding.bindServiceButton.setEnabled(true);
            binding.unbindServiceButton.setEnabled(false);
        });

        // ! 注册 Receiver
        counterServiceReceiver = new CounterServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SERVICE_COUNT_ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(counterServiceReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(counterServiceReceiver, filter);
        }

        binding.startCounterServiceButton.setOnClickListener(v -> {
            Log.d(TAG, "click start counter service button to start counter service");
            // ! 显示开启 Service
            Intent intent = new Intent(this, StartStopCounterService.class);
            startService(intent);

            v.setEnabled(false);
            binding.restartCounterServiceButton.setEnabled(true);
            binding.stopCounterServiceButton.setEnabled(true);
        });

        binding.restartCounterServiceButton.setOnClickListener(v -> {
            Log.d(TAG, "click restart counter service button to restart counter service");
            // ! 显示重启 Service
            Intent intent = new Intent(this, StartStopCounterService.class);
            startService(intent);
        });

        binding.stopCounterServiceButton.setOnClickListener(v -> {
            Log.d(TAG, "click stop counter service button to stop counter service");
            // ! 显示停止 Service
            Intent stopIntent = new Intent(this, StartStopCounterService.class);
            stopService(stopIntent);

            v.setEnabled(false);
            binding.startCounterServiceButton.setEnabled(true);
            binding.restartCounterServiceButton.setEnabled(false);
        });

        // ! 绑定 Activity 和 Service
        ServiceConnection counterServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "executing ServiceConnection onServiceConnected");
                BindUnbindServiceBinder bindUnbindServiceBinder = (BindUnbindServiceBinder) service;
                // 获取 MyService 引用
                bindUnbindCounterService = (BindUnbindCounterService) bindUnbindServiceBinder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                bindUnbindCounterService = null;
                Log.d(TAG, "executing ServiceConnection onServiceDisconnected");
                // Service 被销毁时调用（内存不足等，正常解绑不会走这）
            }
        };

        binding.bindCounterServiceButton.setOnClickListener(v -> {
            Log.d(TAG, "click bind counter service button to bind counter service");
            Intent bindIntent = new Intent(this, BindUnbindCounterService.class);
            bindService(bindIntent, counterServiceConnection, BIND_AUTO_CREATE);

            binding.bindCounterServiceButton.setEnabled(false);
            binding.unbindCounterServiceButton.setEnabled(true);
            binding.counterServiceGetCountButton.setEnabled(true);
        });

        binding.unbindCounterServiceButton.setOnClickListener(v -> {
            Log.d(TAG, "click unbind counter service button to unbind counter service");
            unbindService(counterServiceConnection);

            binding.bindCounterServiceButton.setEnabled(true);
            binding.unbindCounterServiceButton.setEnabled(false);
            binding.counterServiceGetCountButton.setEnabled(false);
        });

        binding.counterServiceGetCountButton.setOnClickListener(v -> {
            if (!Objects.isNull(bindUnbindCounterService)) {
                binding.bindUnbindCounterServiceCounterText.setText(Integer.toString(bindUnbindCounterService.getCount()));
            }
        });
        // CounterManager
        binding.startCounterManagerButton.setOnClickListener(v -> {
            CountManager.getInstance().startCount();

            binding.startCounterManagerButton.setEnabled(false);
            binding.stopCounterManagerButton.setEnabled(true);
            binding.counterManagerGetCountButton.setEnabled(true);

        });
        binding.stopCounterManagerButton.setOnClickListener(v -> {
            CountManager.getInstance().stopCount();
            binding.startCounterManagerButton.setEnabled(true);
            binding.stopCounterManagerButton.setEnabled(false);
            binding.counterManagerGetCountButton.setEnabled(false);
        });
        binding.counterManagerGetCountButton.setOnClickListener(v -> {
            binding.counterManagerCountText.setText(Integer.toString(CountManager.getInstance().getCount()));
        });

    }

    private class CounterServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (SERVICE_COUNT_ACTION.equals(intent.getAction())) {
                int count = intent.getIntExtra(COUNT, -1);
                if (count != -1) {
                    binding.startStopCounterServiceCounterText.setText(Integer.toString(count));
                }
                Log.d(TAG, "executing MainActivity CounterServiceReceiver onReceive, count=" + count);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(counterServiceReceiver);
    }
}