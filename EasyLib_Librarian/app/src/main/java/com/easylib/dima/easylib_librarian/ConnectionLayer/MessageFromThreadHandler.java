package com.easylib.dima.easylib_librarian.ConnectionLayer;

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

public class MessageFromThreadHandler implements Serializable{
    private Map<String, ContextCreator> map;
    Context currentContext;
    MessageFromThreadHandler(){
        map = new HashMap<>();

        map.put(CommunicationConstants.TEST_CONN, this::test);
        map.put(Constants.GET_ALL_BOOKS, this::getAllBooks);
        map.put(Constants.QUERY_ON_BOOKS, this::bookQuery);
        map.put(Constants.GET_LIBRARY_CONN_INFO, this:: librayConnInfo);
        map.put(Constants.GET_LIBRARY_INFO, this::getLibraryInfo);
        map.put(Constants.GET_ALL_LIBRARIES, this::getAllLibraries);
        map.put(Constants.USER_LOGIN, this::userLogin);
        map.put(Constants.PASSWORD_FORGOT, this::passwordForgot);
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
        map.put(Constants.LIBRARIAN_LOGIN, this::librarianLogin);

    }


    // ALL THE METHODS TAKE FROM THE BUNDLE THE OBJECT, CASTING IT IN THE RIGHT WAY,
    // AND CREATE THE RIGHT CONTEXT ( LAUNCH THE ACTIVITY ) WITH THE RECEIVED DATA
    private void bookQuery(Bundle bundle) {
        ArrayList<Book> books = (ArrayList<Book>)bundle.getSerializable(Constants.QUERY_ON_BOOKS);
        Intent intent = new Intent(Constants.QUERY_ON_BOOKS);

        //put whatever data you want to send, if any
        intent.putExtra(Constants.QUERY_ON_BOOKS, books);

        //send broadcast
        this.currentContext.sendBroadcast(intent);
    }


    private void librarianLogin(Bundle bundle) {
        Intent intent = new Intent(Constants.LIBRARIAN_LOGIN);
        intent.putExtra(Constants.LIBRARIAN_LOGIN,
                bundle.getSerializable(Constants.LIBRARIAN_LOGIN));

        this.currentContext.sendBroadcast(intent);
    }


    private void reservedBookReturned(Bundle bundle) {
        Intent intent = new Intent(Constants.RESERVED_BOOK_RETURNED);
        intent.putExtra(Constants.RESERVED_BOOK_RETURNED,
                (boolean)bundle.getSerializable(Constants.RESERVED_BOOK_RETURNED));

        this.currentContext.sendBroadcast(intent);
    }

    private void reservedBookTaken(Bundle bundle) {
        Intent intent = new Intent(Constants.RESERVED_BOOK_TAKEN);
        intent.putExtra(Constants.RESERVED_BOOK_TAKEN,
                (boolean)bundle.getSerializable(Constants.RESERVED_BOOK_TAKEN));

        this.currentContext.sendBroadcast(intent);
    }

    private void bookQueryAllLib(Bundle bundle) {
        Intent intent = new Intent(Constants.QUERY_ON_BOOKS_ALL_LIBRARIES);
        intent.putExtra(Constants.QUERY_ON_BOOKS_ALL_LIBRARIES,
                (boolean) bundle.getSerializable(Constants.QUERY_ON_BOOKS_ALL_LIBRARIES));

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
                (boolean)bundle.getSerializable(Constants.PASSWORD_FORGOT));

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
    private void getWaitingListForAbook(Bundle bundle){
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
        ArrayList<Book> books = (ArrayList<Book>)bundle.getSerializable(Constants.GET_ALL_BOOKS);
        Intent intent = new Intent(Constants.GET_ALL_BOOKS);

        //put whatever data you want to send, if any
        intent.putExtra(Constants.GET_ALL_BOOKS, books);

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
    private interface ContextCreator{
        void build(Bundle bundle) throws IOException, ClassNotFoundException;
    }
}
