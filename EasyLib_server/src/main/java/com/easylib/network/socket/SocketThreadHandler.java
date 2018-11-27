package com.easylib.network.socket;

import java.io.*;
import java.net.Socket;

/**
 * This class is created when a connection with a socket client is performed. It receive the messages from the client and pass them
 * to "ServerDataHandler" class that elaborate the message and call a specific method here for acting the correct operations.
 */

public class SocketThreadHandler implements Runnable {

    private transient ServerDataHandler serverDataHandler;
    private transient ObjectOutputStream objectOutputStream;
    private transient ObjectInputStream objectInputStream;
    private transient boolean conditionToCloseConnection = false;

    /**
     * Constructor
     *
     */
    SocketThreadHandler(Socket clientSocket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    /**
     * Thread launched by "SocketServer" class that is always active for reading by and object input stream the client's messages
     */
    @Override
    public void run() {
        try {

            serverDataHandler = new ServerDataHandler(this, objectInputStream, objectOutputStream);
            while (!conditionToCloseConnection) {

                System.out.print("waiting for a string...\n");
                Object object = objectInputStream.readObject();
                System.out.print("String received!\n");
                serverDataHandler.handleRequest(object);
                System.out.print("Task terminated!\n");
            }
        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }


    }
    private void sendString(String message){

    }

    private void closeSocket(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    public void sendString(String message) {

        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
            objectOutputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendAnswer(Object returnFromEffect) {
        if (isOn()) {
            try {
                objectOutputStream.writeObject(returnFromEffect.toString());

                objectOutputStream.writeObject(returnFromEffect);
                objectOutputStream.flush();
                objectOutputStream.reset();
            } catch (IOException e) {
                UnixColoredPrinter.Logger.print(Constants.IO_EXCEPTION);
            }
        }
    }
**/

}
