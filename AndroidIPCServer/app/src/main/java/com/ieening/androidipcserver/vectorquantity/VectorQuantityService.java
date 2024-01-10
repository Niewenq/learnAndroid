package com.ieening.androidipcserver.vectorquantity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ieening.androidipcserver.MainActivity;

public class VectorQuantityService extends Service {
    private final static String TAG = VectorQuantityService.class.getName();

    public static final String UPDATE_EDIT_TEXT_ACTION = "action_update_receive_from_client_edit_text";

    private final IVectorQuantityInterface.Stub iVectorQuantityBinder = new IVectorQuantityInterface.Stub() {

        @Override
        public void updateVectorQuantityIn(VectorQuantity vectorQuantity) {
            sendBroadcastToActivity(vectorQuantity, 1);

            Log.d(TAG, "receive from client updateVectorQuantityIn firstVectorQuantity = " + vectorQuantity);
            VectorQuantity newInstance = VectorQuantity.getNewInstance(MainActivity.firstVectorQuantityString);


            if (newInstance.getDimension() > 0) {
                vectorQuantity.update(newInstance);
                Log.d(TAG, "send to client updateVectorQuantityIn firstVectorQuantity = " + vectorQuantity);
            }
        }

        @Override
        public void updateVectorQuantityOut(VectorQuantity vectorQuantity) {
            sendBroadcastToActivity(vectorQuantity, 2);

            Log.d(TAG, "receive from client updateVectorQuantityOut secondVectorQuantity = " + vectorQuantity);
            VectorQuantity newInstance = VectorQuantity.getNewInstance(MainActivity.secondVectorQuantityString);
            if (newInstance.getDimension() > 0) {
                vectorQuantity.update(newInstance);
                Log.d(TAG, "send to client updateVectorQuantityOut secondVectorQuantity = " + vectorQuantity);
            }
        }

        @Override
        public void updateVectorQuantityInOut(VectorQuantity vectorQuantity) {
            sendBroadcastToActivity(vectorQuantity, 3);

            Log.d(TAG, "receive from client updateVectorQuantityOut thirdVectorQuantity = " + vectorQuantity);
            VectorQuantity newInstance = VectorQuantity.getNewInstance(MainActivity.thirdVectorQuantityString);
            if (newInstance.getDimension() > 0) {
                vectorQuantity.update(newInstance);
                Log.d(TAG, "send to client updateVectorQuantityOut thirdVectorQuantity = " + vectorQuantity);
            }
        }
    };

    private void sendBroadcastToActivity(VectorQuantity vectorQuantity, int number) {
        Intent activity_intent = new Intent(UPDATE_EDIT_TEXT_ACTION);
        activity_intent.putExtra("number", number);
        activity_intent.putExtra("vector_quantity", vectorQuantity);
        activity_intent.setPackage(getPackageName());
        sendBroadcast(activity_intent);
    }

    public VectorQuantityService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return iVectorQuantityBinder;
    }
}