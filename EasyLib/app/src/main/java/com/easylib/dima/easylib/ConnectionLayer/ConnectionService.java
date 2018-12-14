package com.easylib.dima.easylib.ConnectionLayer;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;

public class ConnectionService extends Service implements Serializable{
    private ConnectionHandler connectionHandler;
    Handler myhandler;
    ClientThread clientThread;
    ObjectOutputStream out;

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    private final IBinder myBinder = new LocalBinder();

    public void IsBoundable() {
        Toast.makeText(this,"I bind like butter", Toast.LENGTH_LONG).show();
    }

    public class LocalBinder extends Binder {
        public ConnectionService getService() {
            System.out.println("I am in Localbinder ");
            return ConnectionService.this;

        }
    }

    public void sendMessageWithContent(String kindOfMessage, Object content){
        new Thread(new SendingThread(out, kindOfMessage, content)).start();
    }

    public ConnectionService() {
        this.connectionHandler = new ConnectionHandler(this);
        myhandler = connectionHandler.getHandler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful

        this.clientThread = new ClientThread(myhandler, this);
        new Thread(clientThread).start();
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        System.out.println("I am in Ibinder onBind method");
        return myBinder;
    }

}