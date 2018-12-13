package com.easylib.dima.easylib.ConnectionLayer;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class ConnectionService extends Service {
    private ConnectionHandler connectionHandler;
    Handler myhandler;

    public ConnectionService() {
        this.connectionHandler = new ConnectionHandler(this);
        myhandler = connectionHandler.getHandler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        new Thread(new ClientThreadPool(myhandler)).start();
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }
}