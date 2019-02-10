package com.easylib.dima.easylib_librarian.ConnectionLayer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import AnswerClasses.Answer;
import AnswerClasses.Book;
import AnswerClasses.Event;
import AnswerClasses.LibraryDescriptor;
import AnswerClasses.News;
import AnswerClasses.Reservation;
import AnswerClasses.User;
import AnswerClasses.WaitingPerson;

/**
 * Class used to manage the answers coming from the server: we firstly receive a string that
 * explain the message's content and we call the correct method to manage that kind of communication
 *
 */
public class MessagesFromServerHandler {

    private Map<String, ContextCreator> map;
//    private SocketClient client;
    private ObjectInputStream objectInputStream;
    private Handler handler;


    MessagesFromServerHandler( ObjectInputStream inputStream, Handler handler){
        map = new HashMap<>();
        this.handler = handler;
        this.objectInputStream = inputStream;

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
        map.put(Constants.REMOVE_RESERVATION, this::removeReservation);
    }

    private void removeReservation() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            boolean res = (boolean) objectInputStream.readObject();
            b.putSerializable(Constants.REMOVE_RESERVATION, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void librarianLogin() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            User res = (User) objectInputStream.readObject();
            b.putSerializable(Constants.LIBRARIAN_LOGIN, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void getWaitingListForAUser() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            WaitingPerson res = (WaitingPerson) objectInputStream.readObject();
            b.putSerializable(Constants.GET_WAITING_LIST_USER, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void bookQueryAllLib() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            ArrayList<Book> res = (ArrayList<Book>) objectInputStream.readObject();
            b.putSerializable(Constants.QUERY_ON_BOOKS_ALL_LIBRARIES, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void getUserRatedBooks() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            ArrayList<Book> res = (ArrayList<Book>) objectInputStream.readObject();
            b.putSerializable(Constants.GET_USER_RATED_BOOKS, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void getAllReservationsForBook() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            ArrayList<Reservation> res = (ArrayList<Reservation>) objectInputStream.readObject();
            b.putSerializable(Constants.GET_ALL_RESERVATIONS_FOR_BOOK, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void reservedBookReturned() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            boolean res = (boolean) objectInputStream.readObject();
            b.putSerializable(Constants.RESERVED_BOOK_RETURNED, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void reservedBookTaken() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            boolean res = (boolean) objectInputStream.readObject();
            b.putSerializable(Constants.RESERVED_BOOK_TAKEN, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void bookQuery() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            ArrayList<Book> res = (ArrayList<Book>) objectInputStream.readObject();
            b.putSerializable(Constants.QUERY_ON_BOOKS, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void newNotificationToken() {
        try {

            boolean res = (boolean)objectInputStream.readObject();

            System.out.print(res);
            // do we need something here?
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void userRegistration() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            boolean res = (boolean) objectInputStream.readObject();
            b.putSerializable(Constants.REGISTER_USER, res );

            //Extract the key to use in the map in the UI thread
//            Set<String> x = b.keySet();
//            Iterator c = x.iterator();
//            String key = (String) c.next();

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    //it receives a Library descriptor object from the stream. If something wrong, receives null
    private void getLibraryInfo() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            LibraryDescriptor res = (LibraryDescriptor) objectInputStream.readObject();
            b.putSerializable(Constants.GET_LIBRARY_INFO, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    //it receives a LibraryDescriptor arraylist from the stream. If something wrong, receives null
    private void getAllLibraries() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            ArrayList<LibraryDescriptor> res = (ArrayList<LibraryDescriptor>)
                    objectInputStream.readObject();
            b.putSerializable(Constants.GET_ALL_LIBRARIES, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void userLogin() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            User res = (User) objectInputStream.readObject();
            b.putSerializable(Constants.USER_LOGIN, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void passwordForgot() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            boolean res = (boolean) objectInputStream.readObject();
            b.putSerializable(Constants.PASSWORD_FORGOT, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    //it receives a true boolean from the stream. If something wrong, receives false
    private void insertPreference() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            boolean res = (boolean) objectInputStream.readObject();
            b.putSerializable(Constants.GET_USER_PREFERENCES, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    //it receives a LibraryDescriptor arraylist from the stream. If something wrong, receives null
    private void getUserPreferences() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            ArrayList<LibraryDescriptor> res =
                    (ArrayList<LibraryDescriptor>) objectInputStream.readObject();
            b.putSerializable(Constants.GET_USER_PREFERENCES, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    //it receives a WaitingPerson arraylist from the stream. If something wrong, receives null
    private void getWaitingListForAbook() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            ArrayList<WaitingPerson> res =
                    (ArrayList<WaitingPerson>) objectInputStream.readObject();
            b.putSerializable(Constants.GET_WAITING_LIST_BOOK, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    //it receives a News arraylist from the stream. If something wrong, receives null
    private void getNews() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            ArrayList<News> res =
                    (ArrayList<News>) objectInputStream.readObject();
            b.putSerializable(Constants.GET_NEWS, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    //it receives a Event arraylist from the stream. If something wrong, receives null
    private void getEvents() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            ArrayList<Event> res = (ArrayList<Event>) objectInputStream.readObject();
            b.putSerializable(Constants.GET_EVENTS, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    //it receives a Reservation arraylist from the stream. If something wrong, receives null
    private void getUserReservations() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            ArrayList<Reservation> res = (ArrayList<Reservation>) objectInputStream.readObject();
            b.putSerializable(Constants.GET_USER_RESERVATION, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    //it receives a String from the stream. If something wrong, receives null
    private void librayConnInfo() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            String res = (String) objectInputStream.readObject();
            b.putSerializable(Constants.GET_LIBRARY_CONN_INFO, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    //it receives a BookActivity arraylist from the stream. If something wrong, receives null
    private void getAllBooks() {
        try {
            Bundle b = new Bundle();
            Message message = handler.obtainMessage();

            ArrayList<Book> res = (ArrayList<Book>) objectInputStream.readObject();
            b.putSerializable(Constants.GET_ALL_BOOKS, res );

            message.obj = b;
            message.sendToTarget();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
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
