package com.ieening.server.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.ieening.server.ICalculatorAidlInterface;


public class CalculatorAidlService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return calculatorAidlBinder;
    }

    private final ICalculatorAidlInterface.Stub calculatorAidlBinder = new ICalculatorAidlInterface.Stub() {
        @Override
        public float twoNumberAdd(float firstNumber, float secondNumber) throws RemoteException {
            return firstNumber + secondNumber;
        }

        @Override
        public float twoNumberSubtract(float firstNumber, float secondNumber) throws RemoteException {
            return firstNumber - secondNumber;
        }

        @Override
        public float twoNumberMultiply(float firstNumber, float secondNumber) throws RemoteException {
            return firstNumber * secondNumber;
        }

        @Override
        public float twoNumberDivide(float firstNumber, float secondNumber) throws RemoteException {
            return firstNumber / secondNumber;
        }
    };

}
