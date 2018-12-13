package com.easylib.dima.easylib.ConnectionLayer;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import java.io.ObjectInputStream;
import java.util.logging.LogRecord;

public class ConnectionHandler {
    private ConnectionService connectionService;

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg){

        }
    };


    public ConnectionHandler(ConnectionService connectionService){
        this.connectionService = connectionService;
    }

    public Handler getHandler() {
        return handler;
    }
}
