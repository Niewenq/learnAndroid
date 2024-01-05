package com.ieening.androidipcclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ieening.androidipcclient.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String serverPackageName = "com.ieening.androidipcserver";
    private static final String serverActivityName = "com.ieening.androidipcserver.MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Intent intent = new Intent();
//        intent.setClassName(serverPackageName, serverActivityName);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra("serviceName", "MyService");
//        startActivity(intent);
    }
}