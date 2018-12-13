package com.easylib.dima.easylib.ConnectionLayer;

import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import AnswerClasses.LibraryDescriptor;
import AnswerClasses.User;

/**
 * Class used to manage the answers coming from the server: we firstly receive a string that
 * explain the message's content and we call the correct method to manage that kind of communication
 *
 */
public class MessagesFromServerHandler {

    private Map<String, ContextCreator> map;
//    private SocketClient client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Handler handler;


    MessagesFromServerHandler(ObjectOutputStream outputStream, ObjectInputStream inputStream, Handler handler){
        map = new HashMap<>();
//        this.client = client;
        this.handler = handler;
        this.objectInputStream = inputStream;
        this.objectOutputStream = outputStream;

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

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertReservation() {

    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertEventPartecipant() {
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertEvent() {
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertNews() {
    }

    //it receives a true boolean from the stream. If something wrong, receives fals
    private void insertWaitingPerson() {
    }

    private void userRegistration() {
        try {
            Message message = new Message();
            boolean res = (boolean) objectInputStream.readObject();
            message.obj = res;
            handler.handleMessage(message);

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertLibrary() {
    }

    //it receives a Library descriptor object from the stream. If something wrong, receives null
    private void getLibraryInfo() {
        try {
            ArrayList<LibraryDescriptor> res = (ArrayList<LibraryDescriptor>) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //it receives a LibraryDescriptor arraylist from the stream. If something wrong, receives null
    private void getAllLibraries() {
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void userLogin() {
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void passwordForgot() {
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertPreference() {
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertRating() {
    }

    //it receives a LibraryDescriptor arraylist from the stream. If something wrong, receives null
    private void getUserPreferences() {
    }

    //it receives a WaitingPerson arraylist from the stream. If something wrong, receives null
    private void getWaitingListForAbook() {
    }

    //it receives a News arraylist from the stream. If something wrong, receives null
    private void getNews() {
    }

    //it receives a Event arraylist from the stream. If something wrong, receives null
    private void getEvents() {
    }

    //it receives a Reservation arraylist from the stream. If something wrong, receives null
    private void getUserReservations() {
    }

    //it receives a String from the stream. If something wrong, receives null
    private void librayConnInfo() {
    }

    //it receives a Book arraylist from the stream. If something wrong, receives null
    private void getAllBooks() {
    }

    // test
    private void test() {
        System.out.print("TEST\n");
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
