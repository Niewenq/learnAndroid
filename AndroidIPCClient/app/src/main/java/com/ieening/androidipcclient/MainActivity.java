package com.ieening.androidipcclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.ieening.androidipcclient.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bindVectorQuantityAidlServiceButton.setOnClickListener(v -> {
            Intent bindIntent = new Intent();
            ComponentName componentName = new ComponentName("com.ieening.androidipcserver", "com.ieening.androidipcserver.services.VectorQuantityService");
            bindIntent.setComponent(componentName);

            bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
        });
    }
}