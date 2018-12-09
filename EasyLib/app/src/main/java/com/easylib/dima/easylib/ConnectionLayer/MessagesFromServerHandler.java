package com.easylib.dima.easylib.ConnectionLayer;

import java.io.IOException;
import java.io.ObjectInputStream;
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
        map.put(Constants.GET_ALL_BOOKS, this::getAllBooks);
        map.put(Constants.QUERY_ON_BOOKS, this::bookQuery);
        map.put(Constants.GET_LIBRARY_CONN_INFO, this:: librayConnInfo);
        map.put(Constants.INSERT_RESERVATION, this::insertReservation);
        map.put(Constants.INSERT_EVENT_PARTICIPANT, this::insertEventPartecipant);
        map.put(Constants.INSERT_EVENT, this::insertEvent);
        map.put(Constants.INSERT_NEWS, this::insertNews);
        map.put(Constants.INSERT_WAITING_PERSON, this::insertWaitingPerson);
        map.put(Constants.REGISTER_USER, this::userRegistration);
        map.put(Constants.INSERT_NEW_LIBRARY, this::insertLibrary);
        map.put(Constants.GET_LIBRARY_INFO, this::getLibraryInfo);
        map.put(Constants.GET_ALL_LIBRARIES, this::getAllLibraries);
        map.put(Constants.USER_LOGIN, this::userLogin);
        map.put(Constants.PASSWORD_FORGOT, this::passwordForgot);
        map.put(Constants.INSERT_PREFERENCE, this::insertPreference);
        map.put(Constants.INSERT_RATING, this::insertRating);
        map.put(Constants.GET_USER_PREFERENCES, this::getUserPreferences);
        map.put(Constants.GET_WAITING_LIST_BOOK, this::getWaitingListForAbook);
        map.put(Constants.GET_NEWS, this::getNews);
        map.put(Constants.GET_EVENTS, this::getEvents);
        map.put(Constants.GET_USER_RESERVATION, this::getUserReservations);
    }

    private void bookQuery() {
    }

    private void insertReservation() {
    }

    private void insertEventPartecipant() {
    }

    private void insertEvent() {
    }

    private void insertNews() {
    }

    private void insertWaitingPerson() {
    }

    private void userRegistration() {
    }

    private void insertLibrary() {
    }

    private void getLibraryInfo() {
    }

    private void getAllLibraries() {
    }

    private void userLogin() {
        ObjectInputStream inputStream = client.getObjectInputStream();
        try {
            boolean res = (boolean) inputStream.readObject();
            System.out.print(res);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void passwordForgot() {
    }

    private void insertPreference() {
    }

    private void insertRating() {
    }

    private void getUserPreferences() {
    }

    private void getWaitingListForAbook() {
    }

    private void getNews() {
    }

    private void getEvents() {
    }

    private void getUserReservations() {
    }

    private void librayConnInfo() {
    }

    private void getAllBooks() {
    }

    private void test() {
        System.out.print("TEST\n");
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
