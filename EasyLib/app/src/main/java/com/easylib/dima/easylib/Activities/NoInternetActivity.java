package com.easylib.dima.easylib.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.easylib.dima.easylib.ConnectionLayer.CheckConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class NoInternetActivity extends AppCompatActivity {

    //Comunication
    ConnectionService mBoundService;
    CheckConnectionService mCheckConnService;

    private boolean isSafe = false;

    //For the communication Service Network Up or Down
    private ServiceConnection mConnection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mCheckConnService = ((CheckConnectionService.LocalBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    private String extractKey(Intent intent){
        Set<String> keySet = Objects.requireNonNull(intent.getExtras()).keySet();
        Iterator iterator = keySet.iterator();
        return (String)iterator.next();
    }

    //This is the handler that will manager to process the broadcast intent
    //This has to be created inside each activity that needs it ( almost anyone )
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = extractKey (intent);

            if (key.equals(Constants.NETWORK_STATE_UP)){
                startService(new Intent(NoInternetActivity.this, ConnectionService.class));
                isSafe = true;
                finish ();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_internet_connection);

        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.NETWORK_STATE_UP));
    }

    @Override
    protected void onDestroy() {
        if (isSafe) {
            super.onDestroy ();
            unregisterReceiver (mMessageReceiver);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    public void onBackPressed() {
        // nothing
    }
}
