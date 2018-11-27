package com.easylib.network.socket;

import java.io.IOException;

public class NetworkStarter {
    public static void main( String[] args )
    {
        SocketServer server = new SocketServer(5000);
        new Thread(server).start();

        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("Stopping Server");
        //server.stop();
    }
}
