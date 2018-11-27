package com.easylib.dima.easylib.ConnectionLayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientThreadPool implements Runnable {
    private Thread runningThread;
    private ExecutorService threadPool;
    private boolean isStopped;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Socket socket;


    ClientThreadPool(){
        isStopped  = false;
        this.runningThread= null;
        this.threadPool = Executors.newFixedThreadPool(10);
        try {
            socket = new Socket(CommunicationConstants.SERVER_IP_ADDRESS,
                    Integer.parseInt(CommunicationConstants.SOCKET_PORT));

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }

        String message;
        try {
            while (!isStopped) {

                System.out.print("Listening...\n");
                message = (String)objectInputStream.readObject();
                this.threadPool.execute(
                        new SocketClient(objectInputStream,objectOutputStream, message));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.threadPool.shutdown();
        // close the connection

        try {

            objectInputStream.close();
            objectOutputStream.close();
            socket.close();

        } catch (IOException e) {
        e.printStackTrace();
        }

        System.out.println("Server Stopped.") ;
    }

    public void sendMessage(String message){
        try {
            objectOutputStream.writeObject("test");
            objectOutputStream.flush();
            objectOutputStream.reset();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void setStopped(boolean val){
        isStopped = val;
    }
}
