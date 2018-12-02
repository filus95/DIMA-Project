package com.easylib.dima.easylib.ConnectionLayer;

public class FakeMain {
    public static void main( String[] args )
    {

        try {
            new NetworkStarter().startNetwork();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
