package com.example.airplanemodebroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    // Override the onReceive() method to make the TextView change on Airplane Mode status change
    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
                if (intent.getBooleanExtra("state", false))
                    tv.setText("Airplane Mode: ON");
                else
                    tv.setText("Airplane Mode: OFF");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.airplaneModeStatus);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Creating IntentFilter dynamically
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);

        // Will do the context registered broadcast receiver
        registerReceiver(br, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Deregister the Receiver
        unregisterReceiver(br);
    }

}