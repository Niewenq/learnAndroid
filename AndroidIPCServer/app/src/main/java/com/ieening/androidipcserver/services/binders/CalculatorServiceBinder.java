package com.ieening.androidipcserver.services.binders;

import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ieening.androidipcserver.constant.CalculatorTransactionCodeEnum;

import java.util.Objects;

public class CalculatorServiceBinder extends Binder {
    private static final String DESCRIPTOR = "CalculatorService";

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        CalculatorTransactionCodeEnum calculatorTransactionCodeEnum = CalculatorTransactionCodeEnum.getCalculatorTransactionCodeEnumByCode(code);
        if (calculatorTransactionCodeEnum != null) {
            data.enforceInterface(DESCRIPTOR);
            float _arg0 = data.readFloat();
            float _arg1 = data.readFloat();
            float _result = 0f;
            switch (calculatorTransactionCodeEnum) {
                case ADD:
                    _result = _arg0 + _arg1;
                    break;
                case SUBTRACT:
                    _result = _arg0 - _arg1;
                    break;
                case MULTIPLY:
                    _result = _arg0 * _arg1;
                    break;
                case DIVIDE:
                    _result = _arg0 / _arg1;
                    break;
            }
            Objects.requireNonNull(reply).writeNoException();
            reply.writeFloat(_result);
            return true;
        }
        return super.onTransact(code, data, reply, flags);
    }
}
