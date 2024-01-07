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

import com.ieening.server.ICalculatorAidlInterface;
import com.ieening.server.binders.CalculatorServiceBinder;
import com.ieening.server.constant.CalculatorTransactionCodeEnum;
import com.ieening.androidipc.databinding.ActivityMainBinding;
import com.ieening.server.services.CalculatorAidlService;
import com.ieening.server.services.CalculatorBinderService;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private ActivityMainBinding binding;

    private Float firstNumber = null;
    private Float secondNumber = null;

    private IBinder calculatorServiceBinder = null;
    private ICalculatorAidlInterface iCalculatorAidlBinder = null;

    ServiceConnection calculatorBinderServiceConnection = null;
    ServiceConnection calculatorAidlServiceConnection = null;

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
                Log.d(TAG, "executing calculatorBinderServiceConnection onServiceConnected");
                changeCalculatorCalculateButtonStatus();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                calculatorServiceBinder = null;
                Log.d(TAG, "executing calculatorBinderServiceConnection onServiceDisconnected");
                changeCalculatorCalculateButtonStatus();
            }
        };
        calculatorAidlServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                iCalculatorAidlBinder = ICalculatorAidlInterface.Stub.asInterface(service);
                Log.d(TAG, "executing calculatorAidlServiceConnection onServiceConnected");
                changeCalculatorCalculateButtonStatus();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                iCalculatorAidlBinder = null;
                Log.d(TAG, "executing calculatorAidlServiceConnection onServiceDisconnected");
                changeCalculatorCalculateButtonStatus();
            }
        };


        setCalculatorBinderServiceBindUnbindButtonOnClickListener(calculatorBinderServiceConnection);
        setCalculatorAidlServiceBindUnbindButtonOnClickListener(calculatorAidlServiceConnection);
        // ! 获取输入数字
        addNumberEditTextTextChangedListener();
        // ! 设置 操作运算符 OnClickListener
        setOperationButtonsOnClickListener();
    }

    private void setOperationButtonsOnClickListener() {
        setAddButtonOnClickListener();
        setSubtractButtonOnClickListener();
        setMultiplyButtonOnClickListener();
        setDivideButtonOnClickListener();
    }

    private void setDivideButtonOnClickListener() {
        binding.divideButton.setOnClickListener(v -> {
            calculatorServiceBinderTransact(CalculatorTransactionCodeEnum.DIVIDE.getCode());
            if (Objects.isNull(iCalculatorAidlBinder)) {
                Toast.makeText(this, "not bind CalculatorBinderService", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    float result = iCalculatorAidlBinder.twoNumberDivide(firstNumber, secondNumber);
                    binding.calculatorResultTextView.setText(Float.toString(result));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void setMultiplyButtonOnClickListener() {
        binding.multiplyButton.setOnClickListener(v -> {
            calculatorServiceBinderTransact(CalculatorTransactionCodeEnum.MULTIPLY.getCode());
            if (Objects.isNull(iCalculatorAidlBinder)) {
                Toast.makeText(this, "not bind CalculatorBinderService", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    float result = iCalculatorAidlBinder.twoNumberMultiply(firstNumber, secondNumber);
                    binding.calculatorResultTextView.setText(Float.toString(result));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void setSubtractButtonOnClickListener() {
        binding.subtractButton.setOnClickListener(v -> {
            calculatorServiceBinderTransact(CalculatorTransactionCodeEnum.SUBTRACT.getCode());
            if (Objects.isNull(iCalculatorAidlBinder)) {
                Toast.makeText(this, "not bind CalculatorBinderService", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    float result = iCalculatorAidlBinder.twoNumberSubtract(firstNumber, secondNumber);
                    binding.calculatorResultTextView.setText(Float.toString(result));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void setAddButtonOnClickListener() {
        binding.addButton.setOnClickListener(v -> {
            calculatorServiceBinderTransact(CalculatorTransactionCodeEnum.ADD.getCode());

            if (Objects.isNull(iCalculatorAidlBinder)) {
                Toast.makeText(this, "not bind CalculatorBinderService", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    float result = iCalculatorAidlBinder.twoNumberAdd(firstNumber, secondNumber);
                    binding.calculatorResultTextView.setText(Float.toString(result));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
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
            bindIntent.setAction("com.ieening.server.services.action.CalculatorBinderService");
            Log.d(TAG, "click bind calculator binder service button to bind service");
            boolean bindResult = bindService(bindIntent, calculatorBinderServiceConnection, Context.BIND_AUTO_CREATE);
            if (bindResult) {
                Toast.makeText(this, "bind service CalculatorBinderService successes", Toast.LENGTH_SHORT).show();
                binding.unbindCalculatorBinderServiceButton.setEnabled(true);
                binding.bindCalculatorBinderServiceButton.setEnabled(false);
            } else {
                Toast.makeText(this, "bind service CalculatorBinderService failed", Toast.LENGTH_SHORT).show();
            }


        });
        binding.unbindCalculatorBinderServiceButton.setOnClickListener(v -> {
            unbindService(calculatorBinderServiceConnection);


            binding.unbindCalculatorBinderServiceButton.setEnabled(false);
            binding.bindCalculatorBinderServiceButton.setEnabled(true);

            calculatorServiceBinder = null;

            changeCalculatorCalculateButtonStatus();
        });
    }

    private void setCalculatorAidlServiceBindUnbindButtonOnClickListener(ServiceConnection calculatorAidlServiceConnection) {
        binding.bindCalculatorAidlServiceButton.setOnClickListener(v -> {
            Intent bindIntent = new Intent(getApplicationContext(), CalculatorAidlService.class);
            bindIntent.setAction("com.ieening.server.services.action.CalculatorAidlService");
            Log.d(TAG, "click bind calculator aidl service button to bind service");
            boolean bindResult = bindService(bindIntent, calculatorAidlServiceConnection, Context.BIND_AUTO_CREATE);
            if (bindResult) {
                Toast.makeText(this, "bind service CalculatorAidlService successes", Toast.LENGTH_SHORT).show();
                binding.unbindCalculatorAidlServiceButton.setEnabled(true);
                binding.bindCalculatorAidlServiceButton.setEnabled(false);
            } else {
                Toast.makeText(this, "bind service CalculatorAidlService failed", Toast.LENGTH_SHORT).show();
            }

            changeCalculatorCalculateButtonStatus();
        });
        binding.unbindCalculatorAidlServiceButton.setOnClickListener(v -> {
            unbindService(calculatorAidlServiceConnection);

            binding.unbindCalculatorAidlServiceButton.setEnabled(false);
            binding.bindCalculatorAidlServiceButton.setEnabled(true);

            iCalculatorAidlBinder = null;

            changeCalculatorCalculateButtonStatus();
        });
    }

    private void changeCalculatorCalculateButtonStatus() {
        if (Objects.isNull(firstNumber) || Objects.isNull(secondNumber) || (Objects.isNull(calculatorServiceBinder) && Objects.isNull(iCalculatorAidlBinder))) {
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
            Toast.makeText(this, "not bind CalculatorBinderService", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}