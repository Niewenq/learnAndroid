package com.ieening.androidipcclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import com.ieening.androidipcclient.databinding.ActivityMainBinding;
import com.ieening.androidipcserver.vectorquantity.IVectorQuantityInterface;
import com.ieening.androidipcserver.vectorquantity.VectorQuantity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getName();
    private ActivityMainBinding binding;

    private IVectorQuantityInterface vectorQuantityRemote = null;

    private final VectorQuantity firstVectorQuantity = new VectorQuantity();
    private final VectorQuantity secondVectorQuantity = new VectorQuantity();
    private final VectorQuantity thirdVectorQuantity = new VectorQuantity();


    private final ServiceConnection serviceConnection = new ServiceConnection() {
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
        if (!Objects.isNull(vectorQuantityRemote)) {
            binding.vectorQuantityFirstNumberButton.setEnabled(firstVectorQuantity.getDimension() > 0);
            binding.vectorQuantitySecondNumberButton.setEnabled(secondVectorQuantity.getDimension() > 0);
            binding.vectorQuantityThirdNumberButton.setEnabled(thirdVectorQuantity.getDimension() > 0);
        } else {
            binding.vectorQuantityFirstNumberButton.setEnabled(false);
            binding.vectorQuantitySecondNumberButton.setEnabled(false);
            binding.vectorQuantityThirdNumberButton.setEnabled(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setBindUnbindVectorQuantityAidlServiceButtonOnClickListener();

        setVectorQuantityNumberButtonsOnclickListener();

        addNumberEditTextChangedListener();
    }

    private void addNumberEditTextChangedListener() {
        binding.firstNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                firstVectorQuantity.update(s.toString().trim());
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
                secondVectorQuantity.update(s.toString().trim());
            }
        });
        binding.thirdNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                thirdVectorQuantity.update(s.toString().trim());
            }
        });
    }

    private void setVectorQuantityNumberButtonsOnclickListener() {
        binding.vectorQuantityFirstNumberButton.setOnClickListener(v -> {
            try {
                vectorQuantityRemote.updateVectorQuantityIn(firstVectorQuantity);
            } catch (RemoteException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

        binding.vectorQuantitySecondNumberButton.setOnClickListener(v -> {
            try {
                vectorQuantityRemote.updateVectorQuantityOut(secondVectorQuantity);
            } catch (RemoteException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

        binding.vectorQuantityThirdNumberButton.setOnClickListener(v -> {
            try {
                vectorQuantityRemote.updateVectorQuantityInOut(thirdVectorQuantity);
            } catch (RemoteException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    private void setBindUnbindVectorQuantityAidlServiceButtonOnClickListener() {
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