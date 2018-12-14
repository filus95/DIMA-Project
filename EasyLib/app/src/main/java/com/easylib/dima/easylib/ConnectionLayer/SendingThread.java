package com.easylib.dima.easylib.ConnectionLayer;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

public class SendingThread implements Runnable{
    private ObjectOutputStream out;
    private String kindMessage;
    private Object content;

    SendingThread(ObjectOutputStream out, String kindMessage, Object content) {
        this.out = out;
        this.kindMessage = kindMessage;
        this.content = content;
    }

    public void run() {
        sendMessageWithContent(kindMessage, content);
    }

    private void sendMessageWithContent(String kindOfMessage, Object content){
        try {
            out.writeObject(kindOfMessage);
            out.flush();
            out.reset();

            out.writeObject(content);
            out.flush();
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
