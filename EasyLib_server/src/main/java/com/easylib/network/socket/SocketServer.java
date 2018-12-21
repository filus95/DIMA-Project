package com.easylib.network.socket;

import com.easylib.server.Database.DatabaseConnection;
import com.easylib.server.Database.DatabaseManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Socket Server that starts a Thread always managing the new socket client requests.
 */
class SocketServer implements Runnable{
    private int serverPort;
    private SocketServer socketServer;
    private ServerSocket serverSocket;
    private Thread runningThread;
    private ExecutorService threadPool;
    private boolean isStopped;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;


    SocketServer(int serverPort) {
        this.socketServer = this;
        isStopped    = false;
        this.serverPort = serverPort;
        this.serverSocket = null;
        this.runningThread= null;
        this.threadPool = Executors.newFixedThreadPool(100);
    }
    
    /**
     * It starts the Server's Thread for receiving the new Socket's client requests.

    void startServer(int serverPort) throws IOException {
        this.serverPort = serverPort;
        serverSocket = new ServerSocket(this.serverPort);
        new RequestHandler().start();
    }*/


    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        new DatabaseConnection().startConnection();

        while (!isStopped()) {
            Socket clientSocket = null;
            try {
                System.out.print("waiting for a connection...\n");
                clientSocket = this.serverSocket.accept();
                System.out.print("connected!\n");

                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                objectOutputStream.flush();
                this.objectInputStream = objectInputStream;
                this.objectOutputStream = objectOutputStream;

                this.threadPool.execute(
                        new SocketThreadHandler(objectInputStream, objectOutputStream));

                System.out.print("QUI\n");

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



    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }
}

   /* public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }

        Socket clientSocket = null;
        try {
            clientSocket = this.serverSocket.accept();
        } catch (IOException e) {
           e.printStackTrace();
            }

        this.threadPool.execute(
                new SocketThreadHandler(clientSocket));

        this.threadPool.shutdown();
        System.out.println("Server Stopped.");
    }

    private class RequestHandler extends Thread {
        public void run() {
            while (true) {
                Socket clientSocket = null;
                try {
                    clientSocket = this.serverSocket.accept();
                } catch (IOException e) {
                    throw new RuntimeException(
                            "Error accepting client connection", e);
                }

                this.threadPool.execute(
                        new WorkerRunnable(clientSocket, "Thread Pooled Server"));

            }
        }
        /**
         * Thread for receiving the new Socket's client requests.

        @Override
        public void run() {

            while (true) {
                try {

                    Socket socket = serverSocket.accept();
                    SocketPlayerHandler socketPlayerHandler = new SocketPlayerHandler(socketServer, socket);
                    new Thread(socketPlayerHandler).start();

                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/



