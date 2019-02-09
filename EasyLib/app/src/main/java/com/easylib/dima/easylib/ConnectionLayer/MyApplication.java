package com.easylib.dima.easylib.ConnectionLayer;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

//@SuppressLint("Registered")
public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}

