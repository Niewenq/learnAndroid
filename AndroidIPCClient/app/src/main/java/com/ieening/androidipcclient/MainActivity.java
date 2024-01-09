package com.ieening.androidipcclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.ieening.androidipcclient.databinding.ActivityMainBinding;
import com.ieening.androidipcserver.vectorquantity.IVectorQuantityInterface;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getName();
    private ActivityMainBinding binding;

    private IVectorQuantityInterface vectorQuantityRemote = null;


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            vectorQuantityRemote = IVectorQuantityInterface.Stub.asInterface(service);
            Log.d(TAG, "executing serviceConnection onServiceConnected");
            changeVectorQuantityButtonsStatus();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            vectorQuantityRemote = null;
            Log.d(TAG, "executing serviceConnection onServiceDisconnected");
            changeVectorQuantityButtonsStatus();
        }
    };

    private void changeVectorQuantityButtonsStatus() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bindVectorQuantityAidlServiceButton.setOnClickListener(v -> {
            Log.d(TAG, "click bind vector quantity aidl service button to bind service");
            Intent bindIntent = new Intent();
            ComponentName componentName = new ComponentName("com.ieening.androidipcserver", "com.ieening.androidipcserver.vectorquantity.VectorQuantityService");
            bindIntent.setComponent(componentName); // ! 两个坑：设置包的可见性；2、设置 ComponentName
            bindIntent.setPackage("com.ieening.androidipcserver");
            bindIntent.setAction("com.ieening.androidipcserver.action.vectorquantity.VectorQuantityService");
            boolean flag = bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
            if (flag) {
                binding.bindVectorQuantityAidlServiceButton.setEnabled(false);
                binding.unbindVectorQuantityAidlServiceButton.setEnabled(true);
                Toast.makeText(this, "bind vector quantity service success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "bind vector quantity service failed", Toast.LENGTH_SHORT).show();
            }
        });

        binding.unbindVectorQuantityAidlServiceButton.setOnClickListener(v -> {
            unbindService(serviceConnection);
            Toast.makeText(this, "unbind vector quantity service success", Toast.LENGTH_SHORT).show();

            binding.bindVectorQuantityAidlServiceButton.setEnabled(true);
            binding.unbindVectorQuantityAidlServiceButton.setEnabled(false);
        });
    }

    @Override
    protected void onDestroy() {
        if (!Objects.isNull(vectorQuantityRemote) && vectorQuantityRemote.asBinder().isBinderAlive()) {
            unbindService(serviceConnection);
        }
        super.onDestroy();
    }
}