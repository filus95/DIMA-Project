package com.easylib.dima.easylib.ConnectionLayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import AnswerClasses.Book;
import AnswerClasses.Event;
import AnswerClasses.LibraryDescriptor;
import AnswerClasses.News;
import AnswerClasses.Reservation;
import AnswerClasses.User;
import AnswerClasses.WaitingPerson;

public class MessageFromThreadHandler implements Serializable {
    private Map<String, ContextCreator> map;
    Context currentContext;

    MessageFromThreadHandler() {
        map = new HashMap<>();

        map.put(CommunicationConstants.TEST_CONN, this::test);
        map.put(Constants.GET_ALL_BOOKS, this::getAllBooks);
        map.put(Constants.QUERY_ON_BOOKS, this::bookQuery);
        map.put(Constants.GET_LIBRARY_CONN_INFO, this::librayConnInfo);
        map.put(Constants.INSERT_RESERVATION, this::insertReservation);
        map.put(Constants.INSERT_EVENT_PARTICIPANT, this::insertEventPartecipant);
        map.put(Constants.INSERT_WAITING_PERSON, this::insertWaitingPerson);
        map.put(Constants.REGISTER_USER, this::userRegistration);
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
        map.put(Constants.GET_WAITING_LIST_USER, this::getWaitingListForAUser);
        map.put(Constants.QUERY_ON_BOOKS_ALL_LIBRARIES, this::bookQueryAllLib);
        map.put(Constants.GET_USER_RATED_BOOKS, this::getUserRatedBooks);
        map.put(Constants.NEW_NOTIFICATION_TOKEN, this::newNotificationToken);
        map.put(Constants.RESERVED_BOOK_TAKEN, this::reservedBookTaken);
        map.put(Constants.RESERVED_BOOK_RETURNED, this::reservedBookReturned);
        map.put(Constants.GET_ALL_RESERVATIONS_FOR_BOOK, this::getAllReservationsForBook);
        map.put(Constants.USER_LOGIN_GOOGLE, this::userLoginGoogle);
        map.put(Constants.USER_SILENT_LOGIN_GOOGLE, this::userSilentLoginGoogle);
        map.put(Constants.EDIT_PROFILE, this::editProfile);
        map.put(Constants.NETWORK_STATE_CHANGED, this::networkStateChanged);
        map.put(Constants.GET_EVENTS_PER_USER, this::getEventsPerUser);
        map.put(Constants.GET_READ_BOOKS, this::getReadBooks);
        map.put(Constants.REMOVE_RESERVATION, this::removeReservation);
        map.put(Constants.REMOVE_WAITING_PERSON, this::removeWaitingPerson);
        map.put(Constants.EDIT_PROFILE_INFO, this::editProfileInfo);

    }

    private void editProfileInfo(Bundle bundle) {
        boolean res = (boolean) bundle.getSerializable(Constants.EDIT_PROFILE_INFO);
        Intent intent = new Intent(Constants.EDIT_PROFILE_INFO);

        //put whatever data you want to send, if any
        intent.putExtra(Constants.EDIT_PROFILE_INFO, res);

        //send broadcast
        this.currentContext.sendBroadcast(intent);
    }

    private void removeWaitingPerson(Bundle bundle) {
        boolean res = (boolean) bundle.getSerializable(Constants.REMOVE_WAITING_PERSON);
        Intent intent = new Intent(Constants.REMOVE_WAITING_PERSON);

        //put whatever data you want to send, if any
        intent.putExtra(Constants.REMOVE_WAITING_PERSON, res);

        //send broadcast
        this.currentContext.sendBroadcast(intent);
    }


    // ALL THE METHODS TAKE FROM THE BUNDLE THE OBJECT, CASTING IT IN THE RIGHT WAY,
    // AND CREATE THE RIGHT CONTEXT ( LAUNCH THE ACTIVITY ) WITH THE RECEIVED DATA
    private void bookQuery(Bundle bundle) {
        ArrayList<Book> books = (ArrayList<Book>) bundle.getSerializable(Constants.QUERY_ON_BOOKS);
        Intent intent = new Intent(Constants.QUERY_ON_BOOKS);

        //put whatever data you want to send, if any
        intent.putExtra(Constants.QUERY_ON_BOOKS, books);

        //send broadcast
        this.currentContext.sendBroadcast(intent);
    }

    private void getReadBooks(Bundle bundle) {
        ArrayList<Book> books = (ArrayList<Book>) bundle.getSerializable(Constants.GET_READ_BOOKS);
        Intent intent = new Intent(Constants.GET_READ_BOOKS);

        //put whatever data you want to send, if any
        intent.putExtra(Constants.GET_READ_BOOKS, books);

        //send broadcast
        this.currentContext.sendBroadcast(intent);
    }

    private void userSilentLoginGoogle(Bundle bundle) {
        if ((boolean) bundle.getSerializable(Constants.USER_SILENT_LOGIN_GOOGLE)) {
            Intent intent = new Intent(Constants.USER_SILENT_LOGIN_GOOGLE);

            //put whatever data you want to send, if any
            intent.putExtra(Constants.USER_SILENT_LOGIN_GOOGLE,
                    (boolean) bundle.getSerializable(Constants.USER_SILENT_LOGIN_GOOGLE));

            //send broadcast
            this.currentContext.sendBroadcast(intent);

        }
    }

    private void editProfile(Bundle bundle) {
        Intent intent = new Intent(Constants.EDIT_PROFILE);

        //put whatever data you want to send, if any
        intent.putExtra(Constants.EDIT_PROFILE,
                (boolean) bundle.getSerializable(Constants.EDIT_PROFILE));

        //send broadcast
        this.currentContext.sendBroadcast(intent);
    }

    private void userLoginGoogle(Bundle bundle) {
        Intent intent = new Intent(Constants.USER_LOGIN_GOOGLE);
        intent.putExtra(Constants.USER_LOGIN_GOOGLE,
                bundle.getSerializable(Constants.USER_LOGIN_GOOGLE));

        this.currentContext.sendBroadcast(intent);
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertReservation(Bundle bundle) {
        Intent intent = new Intent(Constants.INSERT_RESERVATION);
        intent.putExtra(Constants.INSERT_RESERVATION,
                (boolean) bundle.getSerializable(Constants.INSERT_RESERVATION));

        this.currentContext.sendBroadcast(intent);
    }
    private void removeReservation(Bundle bundle) {
        Intent intent = new Intent(Constants.REMOVE_RESERVATION);
        intent.putExtra(Constants.REMOVE_RESERVATION,
                (boolean) bundle.getSerializable(Constants.REMOVE_RESERVATION));

        this.currentContext.sendBroadcast(intent);
    }


    private void reservedBookReturned(Bundle bundle) {
        Intent intent = new Intent(Constants.RESERVED_BOOK_RETURNED);
        intent.putExtra(Constants.RESERVED_BOOK_RETURNED,
                (boolean) bundle.getSerializable(Constants.RESERVED_BOOK_RETURNED));

        this.currentContext.sendBroadcast(intent);
    }

    private void reservedBookTaken(Bundle bundle) {
        Intent intent = new Intent(Constants.RESERVED_BOOK_TAKEN);
        intent.putExtra(Constants.RESERVED_BOOK_TAKEN,
                (boolean) bundle.getSerializable(Constants.RESERVED_BOOK_TAKEN));

        this.currentContext.sendBroadcast(intent);
    }

    private void bookQueryAllLib(Bundle bundle) {
        Intent intent = new Intent(Constants.QUERY_ON_BOOKS_ALL_LIBRARIES);
        intent.putExtra(Constants.QUERY_ON_BOOKS_ALL_LIBRARIES,
                (ArrayList<Book>) bundle.getSerializable(Constants.QUERY_ON_BOOKS_ALL_LIBRARIES));

        this.currentContext.sendBroadcast(intent);
    }

    private void getAllReservationsForBook(Bundle bundle) {
        Intent intent = new Intent(Constants.GET_ALL_RESERVATIONS_FOR_BOOK);
        intent.putExtra(Constants.GET_ALL_RESERVATIONS_FOR_BOOK,
                bundle.getSerializable(Constants.GET_ALL_RESERVATIONS_FOR_BOOK));

        this.currentContext.sendBroadcast(intent);
    }

    private void newNotificationToken(Bundle bundle) {
        Intent intent = new Intent(Constants.NEW_NOTIFICATION_TOKEN);
        intent.putExtra(Constants.NEW_NOTIFICATION_TOKEN,
                (boolean) bundle.getSerializable(Constants.NEW_NOTIFICATION_TOKEN));

        this.currentContext.sendBroadcast(intent);
    }

    private void getUserRatedBooks(Bundle bundle) {
        Intent intent = new Intent(Constants.GET_USER_RATED_BOOKS);
        intent.putExtra(Constants.GET_USER_RATED_BOOKS,
                bundle.getSerializable(Constants.GET_USER_RATED_BOOKS));

        this.currentContext.sendBroadcast(intent);
    }

    private void getWaitingListForAUser(Bundle bundle) {
        Intent intent = new Intent(Constants.GET_WAITING_LIST_USER);
        intent.putExtra(Constants.GET_WAITING_LIST_USER,
                bundle.getSerializable(Constants.GET_WAITING_LIST_USER));

        this.currentContext.sendBroadcast(intent);
    }


    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertEventPartecipant(Bundle bundle) {
        Intent intent = new Intent(Constants.INSERT_EVENT_PARTICIPANT);
        intent.putExtra(Constants.INSERT_EVENT_PARTICIPANT,
                (boolean) bundle.getSerializable(Constants.INSERT_EVENT_PARTICIPANT));

        this.currentContext.sendBroadcast(intent);
    }


    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertWaitingPerson(Bundle bundle) {
        Intent intent = new Intent(Constants.INSERT_WAITING_PERSON);
        intent.putExtra(Constants.INSERT_WAITING_PERSON,
                (boolean) bundle.getSerializable(Constants.INSERT_WAITING_PERSON));

        this.currentContext.sendBroadcast(intent);

    }

    private void userRegistration(Bundle bundle) {
        Intent intent = new Intent(Constants.REGISTER_USER);

        //put whatever data you want to send, if any
        intent.putExtra(Constants.REGISTER_USER,
                bundle.getSerializable(Constants.REGISTER_USER));

        //send broadcast
        this.currentContext.sendBroadcast(intent);

    }


    //it receives a Library descriptor object from the stream. If something wrong, receives null
    private void getLibraryInfo(Bundle bundle) {
        LibraryDescriptor libraryDescriptor = (LibraryDescriptor) bundle.getSerializable(Constants.GET_LIBRARY_INFO);
        Intent intent = new Intent(Constants.GET_LIBRARY_INFO);
        intent.putExtra(Constants.GET_LIBRARY_INFO, libraryDescriptor);

        this.currentContext.sendBroadcast(intent);
    }

    //it receives a LibraryDescriptor arraylist from the stream. If something wrong, receives null
    private void getAllLibraries(Bundle bundle) {
        ArrayList<LibraryDescriptor> libraryDescriptors = (ArrayList<LibraryDescriptor>)
                bundle.getSerializable(Constants.GET_ALL_LIBRARIES);

        Intent intent = new Intent(Constants.GET_ALL_LIBRARIES);
        intent.putExtra(Constants.GET_ALL_LIBRARIES, libraryDescriptors);

        this.currentContext.sendBroadcast(intent);
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void userLogin(Bundle bundle) {
        Intent intent = new Intent(Constants.USER_LOGIN);
        intent.putExtra(Constants.USER_LOGIN,
                    bundle.getSerializable(Constants.USER_LOGIN));

        this.currentContext.sendBroadcast(intent);
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void passwordForgot(Bundle bundle) {
        Intent intent = new Intent(Constants.PASSWORD_FORGOT);
        intent.putExtra(Constants.PASSWORD_FORGOT,
                (boolean) bundle.getSerializable(Constants.PASSWORD_FORGOT));

        this.currentContext.sendBroadcast(intent);
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertPreference(Bundle bundle) {
        Intent intent = new Intent(Constants.INSERT_PREFERENCE);
        intent.putExtra(Constants.INSERT_PREFERENCE,
                (boolean) bundle.getSerializable(Constants.INSERT_PREFERENCE));

        this.currentContext.sendBroadcast(intent);
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertRating(Bundle bundle) {
        Intent intent = new Intent(Constants.INSERT_RATING);
        intent.putExtra(Constants.INSERT_RATING,
                (boolean) bundle.getSerializable(Constants.INSERT_RATING));

        this.currentContext.sendBroadcast(intent);
    }

    //it receives a LibraryDescriptor arraylist from the stream. If something wrong, receives null
    private void getUserPreferences(Bundle bundle) {
        ArrayList<LibraryDescriptor> libraryDescriptors = (ArrayList<LibraryDescriptor>)
                bundle.getSerializable(Constants.GET_USER_PREFERENCES);

        Intent intent = new Intent(Constants.GET_USER_PREFERENCES);
        intent.putExtra(Constants.GET_USER_PREFERENCES, libraryDescriptors);

        this.currentContext.sendBroadcast(intent);
    }

    //it receives a WaitingPerson arraylist from the stream. If something wrong, receives null
    private void getWaitingListForAbook(Bundle bundle) {
        ArrayList<WaitingPerson> waitingPeople = (ArrayList<WaitingPerson>)
                bundle.getSerializable(Constants.GET_WAITING_LIST_BOOK);

        Intent intent = new Intent(Constants.GET_WAITING_LIST_BOOK);
        intent.putExtra(Constants.GET_WAITING_LIST_BOOK, waitingPeople);

        this.currentContext.sendBroadcast(intent);
    }

    //it receives a News arraylist from the stream. If something wrong, receives null
    private void getNews(Bundle bundle) {
        ArrayList<News> news = (ArrayList<News>)
                bundle.getSerializable(Constants.GET_NEWS);

        Intent intent = new Intent(Constants.GET_NEWS);
        intent.putExtra(Constants.GET_NEWS, news);

        this.currentContext.sendBroadcast(intent);
    }

    //it receives a Event arraylist from the stream. If something wrong, receives null
    private void getEvents(Bundle bundle) {
        ArrayList<Event> events = (ArrayList<Event>)
                bundle.getSerializable(Constants.GET_EVENTS);

        Intent intent = new Intent(Constants.GET_EVENTS);
        intent.putExtra(Constants.GET_EVENTS, events);

        this.currentContext.sendBroadcast(intent);
    }


    private void getEventsPerUser(Bundle bundle) {
        ArrayList<Event> events = (ArrayList<Event>)
                bundle.getSerializable(Constants.GET_EVENTS_PER_USER);

        Intent intent = new Intent(Constants.GET_EVENTS_PER_USER);
        intent.putExtra(Constants.GET_EVENTS_PER_USER, events);

        this.currentContext.sendBroadcast(intent);
    }

    //it receives a Reservation arraylist from the stream. If something wrong, receives null
    private void getUserReservations(Bundle bundle) {
        ArrayList<Reservation> reservations = (ArrayList<Reservation>)
                bundle.getSerializable(Constants.GET_USER_RESERVATION);

        Intent intent = new Intent(Constants.GET_USER_RESERVATION);
        intent.putExtra(Constants.GET_USER_RESERVATION, reservations);

        this.currentContext.sendBroadcast(intent);
    }

    //it receives a String from the stream. If something wrong, receives null
    private void librayConnInfo(Bundle bundle) {
        String connectionInfo = (String) bundle.getSerializable(Constants.GET_LIBRARY_CONN_INFO);
        Intent intent = new Intent(Constants.GET_LIBRARY_CONN_INFO);
        intent.putExtra(Constants.GET_LIBRARY_CONN_INFO, connectionInfo);

        this.currentContext.sendBroadcast(intent);
    }

    //it receives a BookActivity arraylist from the stream. If something wrong, receives null
    private void getAllBooks(Bundle bundle) {
        ArrayList<Book> books = (ArrayList<Book>) bundle.getSerializable(Constants.GET_ALL_BOOKS);
        Intent intent = new Intent(Constants.GET_ALL_BOOKS);

        //put whatever data you want to send, if any
        intent.putExtra(Constants.GET_ALL_BOOKS, books);

        //send broadcast
        this.currentContext.sendBroadcast(intent);
    }

    private void networkStateChanged(Bundle bundle){
        boolean connectionUp = (boolean) bundle.getSerializable(Constants.NETWORK_STATE_CHANGED);
        Intent intent = new Intent(Constants.NETWORK_STATE_CHANGED);

        //put whatever data you want to send, if any
        intent.putExtra(Constants.NETWORK_STATE_CHANGED, connectionUp);

        //send broadcast
        this.currentContext.sendBroadcast(intent);
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
    private interface ContextCreator {
        void build(Bundle bundle) throws IOException, ClassNotFoundException;
    }
}

