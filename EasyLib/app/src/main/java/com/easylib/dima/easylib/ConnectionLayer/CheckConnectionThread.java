package com.easylib.dima.easylib.ConnectionLayer;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;

public class CheckConnectionThread implements Runnable {
    private CheckConnectionService connectionService;
    private boolean networkAvailable;
    private Handler handler;


    CheckConnectionThread(CheckConnectionService connService){
        connectionService = connService;
        networkAvailable = true;
    }

    @Override
    public void run() {
        while (true){
            boolean res = isNetworkAvailable();
            if (res != networkAvailable){
                networkAvailable = res;
                Intent intent;
                if ( res ) {
                    intent = new Intent(Constants.NETWORK_STATE_UP);
                    intent.putExtra(Constants.NETWORK_STATE_UP, res);
                }
                else {
                    intent = new Intent(Constants.NETWORK_STATE_DOWN);
                    intent.putExtra(Constants.NETWORK_STATE_DOWN, res);
                }

                //send broadcast
                MyApplication.getAppContext().sendBroadcast(intent);
            }
        }
    }

    private boolean isNetworkAvailable() {
        Context context = MyApplication.getAppContext();
        if ( context != null) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) MyApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        else
            return networkAvailable;
    }


}
