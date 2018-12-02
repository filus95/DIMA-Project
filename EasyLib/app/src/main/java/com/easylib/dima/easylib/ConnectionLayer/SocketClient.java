package com.easylib.dima.easylib.ConnectionLayer;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class SocketClient implements Runnable{

    private transient MessagesFromServerHandler messageHandler;
    private transient ObjectOutputStream objectOutputStream;
    private transient ObjectInputStream objectInputStream;
    private transient String startingMessage;


    SocketClient(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, String message) {
        this.messageHandler = new MessagesFromServerHandler(this);
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.startingMessage = message;
    }

     public void run() {

         try {
             messageHandler.handleMessage(startingMessage);
         } catch (IOException | ClassNotFoundException e) {
             e.printStackTrace();
         }
     }


    /**
         * TODO: Implements callback methods
         */
        private void goToLogin() {
            // TO IMPLEMENT
        }

        void winnerComunication(){}

        // example
        void sendMessage(){}

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }
}

/**
 * Server Listener

 private void waitingForTheNewInteraction() {

 String message;

 try {
 while (true) {
 message = (String) objectInputStream.readObject();
 messageHandler.handleMessage(message);
 }
 } catch (IOException | ClassNotFoundException e) {
 e.printStackTrace();
 }
 }


 */
