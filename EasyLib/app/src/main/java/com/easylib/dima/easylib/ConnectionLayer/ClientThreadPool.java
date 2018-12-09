package com.easylib.dima.easylib.ConnectionLayer;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import AnswerClasses.User;


public class ClientThreadPool implements Runnable {
    private Thread runningThread;
    private ExecutorService threadPool;
    private boolean isStopped;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Socket socket;
    private MessagesFromServerHandler messagesFromServerHandler;


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
        this.messagesFromServerHandler = new MessagesFromServerHandler(objectOutputStream, objectInputStream);
    }

    public void run(){
//        synchronized(this){
//            this.runningThread = Thread.currentThread();
//        }

        String message;
        try {
            while (!isStopped) {

                System.out.print("Listening...\n");
                message = (String)objectInputStream.readObject();
                messagesFromServerHandler.handleMessage(message);

//                this.threadPool.execute(
//                        new SocketClient(objectInputStream,objectOutputStream, message));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        // close the connection
        this.threadPool.shutdown();

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
