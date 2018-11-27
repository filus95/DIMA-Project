package com.easylib.dima.easylib.ConnectionLayer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used to manage the answers coming from the server: we firstly receive a string that
 * explain the message's content and we call the correct method to manage that kind of communication
 *
 */
public class MessagesFromServerHandler {

    private Map<String, ContextCreator> map;
    private SocketClient client;


    MessagesFromServerHandler(SocketClient client){
        map = new HashMap<>();
        this.client = client;

        map.put(CommunicationConstants.TEST_CONN, this::test);
    }

    private void test() {
        System.out.print("TEST");
    }

    /**
     * This method calls winnerComunication method on the client
     */
    private void winnerComunication() {
        client.winnerComunication();
    }

    /**
     * It overwrite the functional interface with the reference of a specific method in the map according to the string
     * received from Socket client
     *
     * @param message string received from the socket client
     * @throws IOException  Signals that an I/O exception of some sort has occurred. This
     * @throws ClassNotFoundException hrown when an application tries to load in a class through its
     * string name using:
     * The forName method in class Class.
     * The findSystemClass method in class ClassLoader.
     * The loadClass method in class ClassLoader
     * but no definition for the class with the specified name could be found.
     */
    void handleMessage(String message) throws IOException, ClassNotFoundException {
        ContextCreator contextCreator = map.get(message);
        contextCreator.build();
    }

    /**
     * functional interface
     */
    @FunctionalInterface
    private interface ContextCreator{
        void build() throws IOException, ClassNotFoundException;
    }
}
