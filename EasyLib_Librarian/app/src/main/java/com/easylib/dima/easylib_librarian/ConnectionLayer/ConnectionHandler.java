package com.easylib.dima.easylib_librarian.ConnectionLayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

public class ConnectionHandler implements Serializable{
            private ConnectionService connectionService;
            private MessageFromThreadHandler messageFromThreadHandler;
            private Context currentContext;


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
        }
    };


    ConnectionHandler(ConnectionService connectionService){
        this.connectionService = connectionService;
        this.messageFromThreadHandler = new MessageFromThreadHandler();
    }

    public Handler getHandler() {
        return handler;
    }

    public void setCurrentContext(Context currentContext) {
        this.currentContext = currentContext;
        this.messageFromThreadHandler.setCurrentContext(this.currentContext);

    }
}
