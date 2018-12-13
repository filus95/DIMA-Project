package com.easylib.dima.easylib.ConnectionLayer;

import java.util.logging.Handler;

import static java.lang.Thread.sleep;

public class NetworkStarter {

    public ClientThreadPool clientThreadPool;
    public NetworkStarter() {
    }

    public void startNetwork() {
        android.os.Handler handler = null;
        this.clientThreadPool = new ClientThreadPool(handler);
        new Thread(clientThreadPool).start();
        System.out.print("Here\n");

//        sleep(5*1000);
//        clientThreadPool.login("Fede", "cacca_addosso", "bettix4@io.cit");

    }

}
