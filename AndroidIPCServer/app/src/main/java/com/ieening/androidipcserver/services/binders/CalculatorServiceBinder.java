package com.ieening.androidipcserver.services.binders;

import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ieening.androidipcserver.constant.CalculatorTransactionCodeEnum;

public class CalculatorServiceBinder extends Binder {

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        CalculatorTransactionCodeEnum calculatorTransactionCodeEnum = CalculatorTransactionCodeEnum.getCalculatorTransactionCodeEnumByCode(code);
        switch (calculatorTransactionCodeEnum){
            case ADD:
        }
        return super.onTransact(code, data, reply, flags);
    }
}
