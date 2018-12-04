package com.easylib.network.socket; /**
 * Created by raffaelebongo on 21/05/17.
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * This class receive the message from "SocketPlayerHandler" and call a method back on that class according to the
 * String received
 */

class ServerDataHandler implements ClientConnMethods, LibrarianConnMethods{
    private SocketThreadHandler socketThreadHandler;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Map<String, MethodsHadler> map;
    private MethodsHadler methodsHadler;

    ServerDataHandler(SocketThreadHandler socketThreadHandler, ObjectInputStream input, ObjectOutputStream output){
        map = new HashMap<>();
        this.socketThreadHandler = socketThreadHandler;
        this.objectInputStream = input;
        this.objectOutputStream = output;
        loadMap();
    }

    /**
     * Load the map for the functional interface
     */
    private void loadMap(){
        map.put(Constants.TEST_CONN, this::test_conn);
        // Add new methods
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //TODO: METHODS THAT CALLS GOOGLE BOOKS OR DBMS METHODS WITH THE PARAMETERS ARRIVED FROM THE CLIENT AND CALL
    //TODO: THE CALLBACKS METHODS ON THE SOCKETTHREADHANDLER CLASS TO SEND BACK THE OUTPUT






    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /***
     * ONLY FOR TESTING
     */
    private void test_conn() {
        //try {
            //Object res = objectInputStream.readObject();
        try {
            objectOutputStream.writeObject("test");
            objectOutputStream.flush();
            objectOutputStream.reset();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("SENT\n");
    }

    /**
     * It overwrite the functional interface with the reference of a specific method in the map according to the string
     * received from SocketPlayerHandler
     *
     * @param object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    void handleRequest(Object object) throws IOException, ClassNotFoundException {

        this.methodsHadler = map.get(object.toString());
        this.methodsHadler.handle();
    }

    @FunctionalInterface
    private interface MethodsHadler{
        void handle() throws IOException, ClassNotFoundException;
    }
}
