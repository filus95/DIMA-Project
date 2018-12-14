package com.easylib.dima.easylib.ConnectionLayer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.LogRecord;

public class ConnectionHandler implements Serializable{
            private ConnectionService connectionService;
            MessageFromThreadHandler messageFromThreadHandler;


            @SuppressLint("HandlerLeak")
            private final Handler handler = new Handler(Looper.getMainLooper()){

                @Override
                public void handleMessage(Message msg){
                    Bundle b = (Bundle) msg.obj;
                    Set<String> x = b.keySet();
                    Iterator c = x.iterator();
                    String key = (String)c.next();
                    try {
                        messageFromThreadHandler.handleMessage(key, b);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
//                    if ( msg.obj == Constants.REGISTER_USER)
//                    boolean r = (boolean) msg.getData().getSerializable("registered");
//                    System.out.print("RESULT!: "+ r);

        }
    };


    ConnectionHandler(ConnectionService connectionService){
        this.connectionService = connectionService;
        this.messageFromThreadHandler = new MessageFromThreadHandler();
    }

    public Handler getHandler() {
        return handler;
    }
}
