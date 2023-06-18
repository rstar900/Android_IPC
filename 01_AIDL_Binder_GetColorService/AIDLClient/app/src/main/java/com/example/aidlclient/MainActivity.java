package com.example.aidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aidlserver.IAIDLColorInterface;

public class MainActivity extends AppCompatActivity {


    // Define an IAIDLColorInterface object (basically a proxy)
    IAIDLColorInterface aidlColorService;

    // Create a ServiceConnection object
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // Initialize the proxy object by passing the iBinder
            aidlColorService = IAIDLColorInterface.Stub.asInterface(iBinder);
            Log.d("OnServiceConnected", "Remote Service Connected!");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // Leaving it as it is
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the intent with the package name of the server and the action as defined in the manifest
        Intent intent = new Intent("AIDLColorService");
        intent.setPackage("com.example.aidlserver");

        // Bind with the service using the intent, connection object and BIND_AUTO_CREATE
        // for creating a new instance of service in case it is not running already
        bindService(intent, mConnection, BIND_AUTO_CREATE);

        // find the background constraint layout
        final View background = findViewById(R.id.background);

        // Set an onClick listner for the button
        Button getColorBtn = findViewById(R.id.getColorBtn);
        getColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Call the method via proxy "aidlColorService" and set the color of the background
                    int color = aidlColorService.getColor();
                    background.setBackgroundColor(color);

                } catch (RemoteException e) {
                    Log.d("RemoteException", "Could not call the remote method!");
                }
            }
        });
    }


}