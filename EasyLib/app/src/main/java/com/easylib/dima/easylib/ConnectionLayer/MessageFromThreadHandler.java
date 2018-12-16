package com.easylib.dima.easylib.ConnectionLayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.easylib.dima.easylib.Login.Register;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MessageFromThreadHandler implements Serializable{
    private Map<String, ContextCreator> map;
    Context currentContext;
    MessageFromThreadHandler(){
        map = new HashMap<>();

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

    // ALL THE METHODS TAKE FROM THE BUNDLE THE OBJECT, CASTING IT IN THE RIGHT WAY,
    // AND CREATE THE RIGHT CONTEXT ( LAUNCH THE ACTIVITY ) WITH THE RECEIVED DATA
    private void bookQuery(Bundle bundle) {
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertReservation(Bundle bundle) {

    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertEventPartecipant(Bundle bundle) {
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertEvent(Bundle bundle) {
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertNews(Bundle bundle) {
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertWaitingPerson(Bundle bundle) {
    }

    private void userRegistration(Bundle bundle) {
        if ((boolean)bundle.getSerializable(Constants.REGISTER_USER)) {
            Intent intent = new Intent(Constants.REGISTER_USER);

            //put whatever data you want to send, if any
//            intent.putExtra("message", message);

            //send broadcast
            this.currentContext.sendBroadcast(intent);

        }
        System.out.print("HERE");
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertLibrary(Bundle bundle) {
    }

    //it receives a Library descriptor object from the stream. If something wrong, receives null
    private void getLibraryInfo(Bundle bundle) {
    }

    //it receives a LibraryDescriptor arraylist from the stream. If something wrong, receives null
    private void getAllLibraries(Bundle bundle) {
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void userLogin(Bundle bundle) {
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void passwordForgot(Bundle bundle) {
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertPreference(Bundle bundle) {
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertRating(Bundle bundle) {
    }

    //it receives a LibraryDescriptor arraylist from the stream. If something wrong, receives null
    private void getUserPreferences(Bundle bundle) {
    }

    //it receives a WaitingPerson arraylist from the stream. If something wrong, receives null
    private void getWaitingListForAbook(Bundle bundle) {
    }

    //it receives a News arraylist from the stream. If something wrong, receives null
    private void getNews(Bundle bundle) {
    }

    //it receives a Event arraylist from the stream. If something wrong, receives null
    private void getEvents(Bundle bundle) {
    }

    //it receives a Reservation arraylist from the stream. If something wrong, receives null
    private void getUserReservations(Bundle bundle) {
    }

    //it receives a String from the stream. If something wrong, receives null
    private void librayConnInfo(Bundle bundle) {
    }

    //it receives a Book arraylist from the stream. If something wrong, receives null
    private void getAllBooks(Bundle bundle) {
    }

    // test
    private void test(Bundle bundle) {
        System.out.print("TEST\n");
    }

    void handleMessage(String message, Bundle b) throws IOException, ClassNotFoundException {
        ContextCreator contextCreator = map.get(message);
        contextCreator.build(b);
    }

    public void setCurrentContext(Context currentContext) {
        this.currentContext = currentContext;
    }

    /**
     * functional interface
     */
    @FunctionalInterface
    private interface ContextCreator{
        void build(Bundle bundle) throws IOException, ClassNotFoundException;
    }
}
