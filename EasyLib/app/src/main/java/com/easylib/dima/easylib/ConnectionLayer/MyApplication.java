package com.easylib.dima.easylib.ConnectionLayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

//@SuppressLint("Registered")
public class MyApplication extends MultiDexApplication {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}

