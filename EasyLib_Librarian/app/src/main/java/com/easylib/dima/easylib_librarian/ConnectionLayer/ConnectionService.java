package com.easylib.dima.easylib_librarian.ConnectionLayer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ConnectionService extends Service implements Serializable{
    private ConnectionHandler connectionHandler;
    Handler myhandler;
    ClientThread clientThread;
    ObjectOutputStream out;
    Context currentContext;

    public ConnectionService() {
        this.connectionHandler = new ConnectionHandler(this);
        myhandler = connectionHandler.getHandler();
    }

    public void setCurrentContext(Context currentContext) {
        this.currentContext = currentContext;
        this.connectionHandler.setCurrentContext(currentContext);
    }

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

    public void sendMessage(String kindOfMessage, Object content){
        new Thread(new SendingThread(out, kindOfMessage, content)).start();
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