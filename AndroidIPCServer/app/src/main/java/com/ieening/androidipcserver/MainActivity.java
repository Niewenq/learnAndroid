package com.ieening.androidipcserver;

import static com.ieening.androidipcserver.vectorquantity.VectorQuantityService.UPDATE_EDIT_TEXT_ACTION;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import com.ieening.androidipcserver.databinding.ActivityMainBinding;
import com.ieening.androidipcserver.vectorquantity.VectorQuantity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    public static String firstVectorQuantityString = "";


    public static String secondVectorQuantityString = "";


    public static String thirdVectorQuantityString = "";

    private VectorQuantityServiceReceiver vectorQuantityServiceReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addSendToClientEditTextChangedListener();

        // ! 注册 Receiver
        vectorQuantityServiceReceiver = new VectorQuantityServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATE_EDIT_TEXT_ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(vectorQuantityServiceReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(vectorQuantityServiceReceiver, filter);
        }
    }

    private void addSendToClientEditTextChangedListener() {
        binding.firstNumberSendToClientEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                firstVectorQuantityString = s.toString();
            }
        });
        binding.secondNumberSendToClientEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                secondVectorQuantityString = s.toString();
            }
        });
        binding.thirdNumberSendToClientEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                thirdVectorQuantityString = s.toString();
            }
        });
    }

    private class VectorQuantityServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (UPDATE_EDIT_TEXT_ACTION.equals(intent.getAction())) {
                int number = intent.getIntExtra("number", 0);
                VectorQuantity vectorQuantity = intent.getParcelableExtra("vector_quantity");
                assert vectorQuantity != null;
                if (number == 1) {
                    binding.firstNumberReceiveFromClientEditText.setText(vectorQuantity.toString());
                } else if (number == 2) {
                    binding.secondNumberReceiveFromClientEditText.setText(vectorQuantity.toString());
                } else if (number == 3) {
                    binding.thirdNumberReceiveFromClientEditText.setText(vectorQuantity.toString());
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(vectorQuantityServiceReceiver);
        super.onDestroy();
    }
}