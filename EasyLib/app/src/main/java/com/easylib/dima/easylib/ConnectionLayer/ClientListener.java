package com.easylib.dima.easylib.ConnectionLayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


// DO I NEED THIS CLASS? DIFFICULT TO MANAGE
public class ClientListener implements Runnable{
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    Thread runningThread;
    boolean isStopped;
    private Socket socket;

    ClientListener(){
        try {
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // Do i need a port for the ClientListener and another for the sender?
            socket = new Socket(CommunicationConstants.SERVER_IP_ADDRESS,
                    Integer.parseInt(CommunicationConstants.SOCKET_PORT));
            runningThread = null;
            isStopped = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }

    /**
    @Override
    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }

        while (!isStopped()) {
            Socket clientSocket = null;
            try {

                ObjectInputStream objectInputStream = new ObjectInputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                objectOutputStream.flush();
                this.objectInputStream = objectInputStream;
                this.objectOutputStream = objectOutputStream;

                this.threadPool.execute(
                        new SocketThreadHandler(clientSocket, objectInputStream, objectOutputStream));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.threadPool.shutdown();
        try {
            objectInputStream.close();
            objectOutputStream.close();
            socketServer.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isStopped() {
        return isStopped;
    }**/
}
