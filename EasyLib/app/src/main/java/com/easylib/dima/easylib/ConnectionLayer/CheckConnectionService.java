package com.easylib.dima.easylib.ConnectionLayer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.ObjectOutputStream;

public class CheckConnectionService extends Service {
    CheckConnectionThread checkConnectionThread;
    ObjectOutputStream out;
    Context currentContext;

    public CheckConnectionService() {
    }

    public void setCurrentContext(Context currentContext) {
        this.currentContext = currentContext;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    private final IBinder myBinder = new LocalBinder();


    public class LocalBinder extends Binder {
        public CheckConnectionService getService() {
            System.out.println("I am in Localbinder ");
            return CheckConnectionService.this;

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful

        this.checkConnectionThread = new CheckConnectionThread(this);
        new Thread(checkConnectionThread).start();
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        System.out.println("I am in Ibinder onBind method");
        return myBinder;
    }

    public Context getCurrentContext() {
        return currentContext;
    }
}
