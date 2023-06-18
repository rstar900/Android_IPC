package com.example.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.Random;

public class AIDLColorService extends Service {
    public AIDLColorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service (our Binder object).
        return binder;
    }

    // Create the Binder object implementing the IAIDLColorInterface
    private final IAIDLColorInterface.Stub binder = new IAIDLColorInterface.Stub() {
        @Override
        public int getColor() throws RemoteException {
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            Log.d("getColor() called", "" + color);
            return color;
        }
    };
}
