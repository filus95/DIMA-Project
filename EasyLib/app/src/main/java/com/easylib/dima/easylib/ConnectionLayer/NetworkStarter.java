package com.easylib.dima.easylib.ConnectionLayer;

import static java.lang.Thread.sleep;

public class NetworkStarter {

    NetworkStarter() {
    }

    public void startNetwork() throws InterruptedException {
        ClientThreadPool clientThreadPool = new ClientThreadPool();
        new Thread(clientThreadPool).start();
        System.out.print("Here\n");


            sleep(5*1000);
//            clientThreadPool.login("Filus", "psw", "raff@io.cit");

    }
}
