package com.easylib.network.socket;


import AnswerClasses.Book;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * This class is created when a connection with a socket client is performed. It receive the messages from the client and pass them
 * to "ServerDataHandler" class that elaborate the message and call a specific method here for acting the correct operations.
 */

public class SocketThreadHandler implements Runnable, ClientConnMethods, LibrarianConnMethods {

    private transient ServerDataHandler serverDataHandler;
    private transient ObjectOutputStream objectOutputStream;
    private transient ObjectInputStream objectInputStream;
    private transient boolean conditionToCloseConnection = false;

    /**
     * Constructor
     */
    SocketThreadHandler(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream)
    {
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
                System.out.print(object);
                serverDataHandler.handleRequest(object);
                System.out.print("Task terminated!\n");
            }
        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.print("SONO USCITO");
        }

    }


    /////////////////////////////////METHODS TO SEND RESULTS BACK TO THE CLIENT/////////////////////////////////////////

    //////////////////////////////////////METHODS THAT INTERACT WITH THE LIBRARY SERVER/////////////////////////////////

    private void sendString(String message){

    }

    private void closeSocket(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendBooks(ArrayList<Book> books) {
        sendViaSocket(books);
    }

    void sendLibraryConnInfo(String schema_lib) {
        sendViaSocket(schema_lib);
    }

    void sendViaSocket(Object toSend){
        try {
            objectOutputStream.writeObject(toSend);
            objectOutputStream.flush();
            objectOutputStream.reset();
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

    //////////////////////////////////////METHODS THAT INTERACT WITH THE PROPIETARY SERVER//////////////////////////////

}
