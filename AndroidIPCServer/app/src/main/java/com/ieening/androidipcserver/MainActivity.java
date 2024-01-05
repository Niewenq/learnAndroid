package com.ieening.androidipcserver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ieening.androidipcserver.services.MyService;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String serviceName = getIntent().getStringExtra("serviceName");
        if ("MyService".equals(serviceName)) {
            Intent intent = new Intent(getApplicationContext(), MyService.class);
            startService(intent);
        }
    }
}