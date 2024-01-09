package com.ieening.androidipcserver.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.ieening.androidipcserver.IVectorQuantityInterface;
import com.ieening.androidipcserver.model.VectorQuantity;

public class VectorQuantityService extends Service {
    private final IVectorQuantityInterface.Stub iVectorQuantityBinder = new IVectorQuantityInterface.Stub() {

        @Override
        public void updateVectorQuantityIn(VectorQuantity vectorQuantity) throws RemoteException {

        }

        @Override
        public void updateVectorQuantityOut(VectorQuantity vectorQuantity) throws RemoteException {

        }

        @Override
        public void updateVectorQuantityInOut(VectorQuantity vectorQuantity) throws RemoteException {

        }
    };

    public VectorQuantityService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return iVectorQuantityBinder;
    }
}