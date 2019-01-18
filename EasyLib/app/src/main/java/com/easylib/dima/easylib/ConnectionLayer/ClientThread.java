package com.easylib.dima.easylib.ConnectionLayer;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import AnswerClasses.User;


public class ClientThread implements Runnable, Serializable {
    private ConnectionService connectionService;
    private boolean isStopped;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Socket socket;
    private MessagesFromServerHandler messagesFromServerHandler;
    private Handler handler;


    ClientThread(Handler handler, ConnectionService connectionService){
        isStopped  = false;
        this.connectionService = connectionService;
        this.handler = handler;
    }

    public void run(){
        String message;
        try {
            try {

                socket = new Socket("192.168.1.3",
                        Integer.parseInt(CommunicationConstants.SOCKET_PORT));
                System.out.print("CONNECTED");
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                connectionService.setOut(objectOutputStream);
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                this.messagesFromServerHandler = new MessagesFromServerHandler(objectInputStream, handler);
                connectionService.setOut(objectOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!isStopped) {

                System.out.print("Listening...\n");
                message = (String)objectInputStream.readObject();
                messagesFromServerHandler.handleMessage(message);

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {

            objectInputStream.close();
            objectOutputStream.close();
            socket.close();

        } catch (IOException e) {
        e.printStackTrace();
        }

        System.out.println("Server Stopped.") ;
    }


    // MESSAGE TO SEND TO THE SERVER


    public void login(String username, String password, String email){
        sendMessage(Constants.REGISTER_USER);

        User user = new User();
        user.setUsername(username);
        user.setPlainPassword(password);
        user.setEmail(email);
        sendViaSocket(user);
    }

    public void sendRegistration(User user){
        sendViaSocket(Constants.REGISTER_USER);
        sendViaSocket(user);

        // TODO: method to call for activating a waiting screen
    }



    private void sendViaSocket(Object toSend){
        try {
            objectOutputStream.writeObject(toSend);
            objectOutputStream.flush();
            objectOutputStream.reset();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void sendMessage(String message){
        try {
            objectOutputStream.writeObject(message);
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
