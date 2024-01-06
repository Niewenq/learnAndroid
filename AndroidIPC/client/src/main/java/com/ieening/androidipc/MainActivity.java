package com.ieening.androidipc;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ieening.androidipc.binders.CalculatorServiceBinder;
import com.ieening.androidipc.constant.CalculatorTransactionCodeEnum;
import com.ieening.androidipc.databinding.ActivityMainBinding;
import com.ieening.androidipc.services.CalculatorBinderService;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private ActivityMainBinding binding;

    private Float firstNumber = null;
    private Float secondNumber = null;

    private IBinder calculatorServiceBinder = null;

    ServiceConnection calculatorBinderServiceConnection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ! 绑定服务
        calculatorBinderServiceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                calculatorServiceBinder = service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                calculatorServiceBinder = null;
            }
        };
        setCalculatorBinderServiceBindUnbindButtonOnClickListener(calculatorBinderServiceConnection);
        // ! 获取输入数字
        addNumberEditTextTextChangedListener();

        binding.addButton.setOnClickListener(v -> {
            calculatorServiceBinderTransact(CalculatorTransactionCodeEnum.ADD.getCode());
        });
        binding.subtractButton.setOnClickListener(v -> {
            calculatorServiceBinderTransact(CalculatorTransactionCodeEnum.SUBTRACT.getCode());
        });
        binding.multiplyButton.setOnClickListener(v -> {
            calculatorServiceBinderTransact(CalculatorTransactionCodeEnum.MULTIPLY.getCode());
        });
        binding.divideButton.setOnClickListener(v -> {
            calculatorServiceBinderTransact(CalculatorTransactionCodeEnum.DIVIDE.getCode());
        });
    }

    private void addNumberEditTextTextChangedListener() {
        binding.firstNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    firstNumber = Float.parseFloat(Objects.requireNonNull(binding.firstNumberEditText.getText()).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "please input float number format text", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "parse first number edit text to float failed");
                    firstNumber = null;
                }
                changeCalculatorCalculateButtonStatus();
            }
        });

        binding.secondNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    secondNumber = Float.parseFloat(Objects.requireNonNull(binding.secondNumberEditText.getText()).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "parse first number edit text to float failed");
                    Toast.makeText(MainActivity.this, "please input float number format text", Toast.LENGTH_SHORT).show();
                    secondNumber = null;
                }
                changeCalculatorCalculateButtonStatus();
            }
        });
    }

    private void setCalculatorBinderServiceBindUnbindButtonOnClickListener(ServiceConnection calculatorBinderServiceConnection) {
        binding.bindCalculatorBinderServiceButton.setOnClickListener(v -> {
            Intent bindIntent = new Intent(getApplicationContext(), CalculatorBinderService.class);
            bindIntent.setAction("com.ieening.androidipc.services.action.CalculatorBinderService");
            Log.d(TAG, "click bind calculator binder service button to bind service");
            boolean bindResult = bindService(bindIntent, calculatorBinderServiceConnection, Context.BIND_AUTO_CREATE);
            if (bindResult) {
                Toast.makeText(this, "bind service CalculatorBinderService successes", Toast.LENGTH_SHORT).show();
                binding.unbindCalculatorBinderServiceButton.setEnabled(true);
                binding.bindCalculatorBinderServiceButton.setEnabled(false);
            } else {
                Toast.makeText(this, "bind service CalculatorBinderService failed", Toast.LENGTH_SHORT).show();
            }


            changeCalculatorCalculateButtonStatus();
        });
        binding.unbindCalculatorBinderServiceButton.setOnClickListener(v -> {
            unbindService(calculatorBinderServiceConnection);

            binding.unbindCalculatorBinderServiceButton.setEnabled(false);
            binding.bindCalculatorBinderServiceButton.setEnabled(true);

            changeCalculatorCalculateButtonStatus();
        });
    }

    private void changeCalculatorCalculateButtonStatus() {
        if (Objects.isNull(firstNumber) || Objects.isNull(secondNumber) || Objects.isNull(calculatorServiceBinder)) {
            binding.addButton.setEnabled(false);
            binding.subtractButton.setEnabled(false);
            binding.multiplyButton.setEnabled(false);
            binding.divideButton.setEnabled(false);
        } else {
            binding.addButton.setEnabled(true);
            binding.subtractButton.setEnabled(true);
            binding.multiplyButton.setEnabled(true);
            binding.divideButton.setEnabled(true);
        }
    }

    private void calculatorServiceBinderTransact(int code) {
        if (calculatorServiceBinder == null) {
            Toast.makeText(this, "not bind service", Toast.LENGTH_SHORT).show();
        } else {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            float _result;
            try {
                _data.writeInterfaceToken(CalculatorServiceBinder.CALCULATOR_SERVICE_INTERFACE_NAME);
                _data.writeFloat(firstNumber);
                _data.writeFloat(secondNumber);
                calculatorServiceBinder.transact(code, _data, _reply, 0);
                _reply.readException();
                _result = _reply.readFloat();
                binding.calculatorResultTextView.setText(Float.toString(_result));

            } catch (RemoteException e) {
                e.printStackTrace();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
        }
    }
}