package com.easylib.network.socket; /**
 * Created by raffaelebongo on 21/05/17.
 */

import AnswerClasses.*;
import com.easylib.server.Database.DatabaseManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class receive the message from "SocketPlayerHandler" and call a method back on that class according to the
 * String received
 */

class ServerDataHandler implements ClientConnMethods, LibrarianConnMethods{
    private SocketThreadHandler socketHandler;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Map<String, MethodsHadler> map;
    private MethodsHadler methodsHadler;
    private DatabaseManager dbms;

    ServerDataHandler(SocketThreadHandler socketThreadHandler, ObjectInputStream input, ObjectOutputStream output){
        map = new HashMap<>();
        this.socketHandler = socketThreadHandler;
        this.objectInputStream = input;
        this.objectOutputStream = output;
        this.dbms = new DatabaseManager();
        loadMap();
    }

    /**
     * Load the map for the functional interface
     */
    private void loadMap(){
        map.put(Constants.TEST_CONN, this::test_conn);
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
        // Add new methods
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //TODO: METHODS THAT CALLS GOOGLE BOOKS OR DBMS METHODS WITH THE PARAMETERS ARRIVED FROM THE CLIENT AND CALL
    //TODO: THE CALLBACKS METHODS ON THE SOCKETTHREADHANDLER CLASS TO SEND BACK THE OUTPUT


    //////////////////////////////////////METHODS THAT INTERACT WITH THE LIB DB////////////////////////////////////////////

    private void getAllBooks(){
        try {
            int id_lib = (int)objectInputStream.readObject();

            // extract the schema_name recorded in the DB of libraries related to id_lib
            String schema_lib = dbms.getSchemaNameLib(id_lib);
            ArrayList<Book> res = dbms.queryAllBooks(schema_lib);
            socketHandler.sendViaSocket(Constants.GET_ALL_BOOKS);
            socketHandler.sendBooks(res);
        }
            catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void bookQuery(){
        try {
            ArrayList<Book> result;
            // receive the library id that the user want to query
            // extract the schema_name recorded in the DB of libraries related to id_lib
            Query query = (Query)objectInputStream.readObject();
            String schema_lib = dbms.getSchemaNameLib(query.getIdLib());

            if (query.getTitle()!=null && query.getAuthor() == null && query.getCategory() == null)
                result = dbms.queryBooksByTitle(query.getTitle(), schema_lib);
            else if (query.getTitle()==null && query.getAuthor() != null && query.getCategory() == null)
                result = dbms.queryBooksByAuthor(query.getAuthor(), schema_lib);
            else if(query.getTitle()==null && query.getAuthor() == null && query.getCategory() != null)
                result = dbms.queryBooksByCategory(query.getCategory(), schema_lib);
            else if(query.getTitle()!=null && query.getAuthor() != null && query.getCategory() == null)
                result = dbms.queryBooksByAuthorAndTitle(query.getTitle(), query.getAuthor(), schema_lib);
            else if (query.getTitle()==null && query.getAuthor() != null && query.getCategory() != null)
                result = dbms.queryBooksByAuthorAndCategory(query.getCategory(), query.getAuthor(), schema_lib);
            else if (query.getTitle()!=null && query.getAuthor() == null && query.getCategory() != null)
                result = dbms.queryBooksByTitleAndCategory(query.getTitle(), query.getCategory(), schema_lib);
            else
                result = dbms.queryBooksByAll(query.getTitle(), query.getAuthor(),query.getCategory(), schema_lib);

            socketHandler.sendViaSocket(Constants.QUERY_ON_BOOKS);
            socketHandler.sendBooks(result);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getAllLibraries(){
        ArrayList<Integer> id_libs = dbms.getAllIdLibs();
        ArrayList<LibraryDescriptor> libraries = new ArrayList<>();

        for (Integer elem: id_libs){
            libraries.add(getLibraryDescriptor(elem));
        }

        socketHandler.sendViaSocket(Constants.GET_ALL_LIBRARIES);
        socketHandler.sendViaSocket(libraries);
    }

    private void getLibraryInfo(){
        try {
            int id_lib = (int)objectInputStream.readObject();
            LibraryDescriptor ld = getLibraryDescriptor(id_lib);
            socketHandler.sendViaSocket(Constants.GET_LIBRARY_INFO);
            socketHandler.sendViaSocket(ld);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            socketHandler.sendViaSocket(null);
        }
    }

    private LibraryDescriptor getLibraryDescriptor(int id_lib){
        LibraryDescriptor ld = dbms.getLibraryInfo(id_lib);
        LibraryContent lc = dbms.getLibraryContent(ld.getSchema_name());
        ld.setLibraryContent(lc);
        return ld;
    }


    ////////////////////////////////////////////INSERTION METHODS///////////////////////////////////////////////////////
    // Each insertion method get the schema name related to the lib_id since each lib has its own schema, database which
    // contains many tables but with a consistent structure over the different schemas.

    private void insertReservation(){
        boolean res;
        try {

            Reservation reservation = (Reservation)objectInputStream.readObject();
            String schema_name = dbms.getSchemaNameLib(reservation.getIdLib());

            res = dbms.insertNewReservation(reservation, schema_name);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            res = false;
        }
        socketHandler.sendViaSocket(Constants.INSERT_RESERVATION);
        socketHandler.sendViaSocket(res);
    }

    private void getUserReservations(){
        try {

            Reservation reservation = (Reservation)objectInputStream.readObject();
            String schema_name = dbms.getSchemaNameLib(reservation.getIdLib());

            ArrayList<Reservation> res = dbms.getReservations(reservation.getUser_id(), schema_name);
            socketHandler.sendViaSocket(Constants.GET_USER_RESERVATION);
            socketHandler.sendViaSocket(res);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            socketHandler.sendViaSocket(null);
        }
    }

    private void insertEventPartecipant(){
        boolean res;

        try {
            Event_partecipant partecipant = (Event_partecipant)objectInputStream.readObject();
            String schema_name = dbms.getSchemaNameLib(partecipant.getIdLib());
            res = dbms.insertNewEventPartecipant(partecipant, schema_name);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            res = false;
        }
        socketHandler.sendViaSocket(Constants.INSERT_EVENT_PARTICIPANT);
        socketHandler.sendViaSocket(res);
    }

    private void insertEvent(){

        boolean res;
        try {

            Event event = (Event)objectInputStream.readObject();
            String schema_name = dbms.getSchemaNameLib(event.getIdLib());

            res = dbms.insertNewEvent(event, schema_name);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            res = false;
        }
        socketHandler.sendViaSocket(res);
    }

    private void getEvents(){
        ArrayList<Event> res;
        try {
            int id_lib = (int)objectInputStream.readObject();
            String schema_name = dbms.getSchemaNameLib(id_lib);

            int limit = 5;
            socketHandler.sendViaSocket(Constants.GET_EVENTS);
            res = dbms.getAllEvents(schema_name, limit);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            res = null;
        }
        socketHandler.sendViaSocket(res);
    }

    private void insertNews(){
        boolean res;


        try {

            News news = (News)objectInputStream.readObject();
            String schema_name = dbms.getSchemaNameLib(news.getIdLib());

            res = dbms.insertNews(news, schema_name);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            res = false;
        }
        socketHandler.sendViaSocket(res);
    }

    private void getNews(){
        ArrayList<News> res;
        int limit = 5;
        try {
            int id_lib = (int)objectInputStream.readObject();
            String schema_name = dbms.getSchemaNameLib(id_lib);
            socketHandler.sendViaSocket(Constants.GET_NEWS);
            res = dbms.getAllNews(schema_name, limit);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            res = null;
        }
        socketHandler.sendViaSocket(res);
    }

    private void insertWaitingPerson(){
        boolean res;
        try {


            WaitingPerson wp = (WaitingPerson) objectInputStream.readObject();
            String schema_name = dbms.getSchemaNameLib(wp.getIdLib());

            res = dbms.insertNewWaitingPerson(wp, schema_name);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            res = false;
        }
        socketHandler.sendViaSocket(Constants.INSERT_WAITING_PERSON);
        socketHandler.sendViaSocket(res);
    }

    private void getWaitingListForAbook(){
        ArrayList<WaitingPerson> res;
        try {
            Book book = (Book)objectInputStream.readObject();
            int id_lib = book.getIdLibrary();
            String schema_name = dbms.getSchemaNameLib(id_lib);
            String book_id = book.getIdentifier();
            socketHandler.sendViaSocket(Constants.GET_WAITING_LIST_BOOK);
            res = dbms.getWaitingList(book_id, schema_name);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            res = null;
        }
        socketHandler.sendViaSocket(res);
    }

    private void insertLibrary(){
        boolean res = false;

        try {
            LibraryDescriptor libDesc = (LibraryDescriptor) objectInputStream.readObject();
            res = dbms.insertNewLibrary(libDesc, Constants.PROPIETARY_DB);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        socketHandler.sendViaSocket(Constants.INSERT_NEW_LIBRARY);
        socketHandler.sendViaSocket(res);
    }

    private void insertPreference(){
        try {
            UserPreferences up = (UserPreferences) objectInputStream.readObject();
            socketHandler.sendViaSocket(Constants.INSERT_PREFERENCE);
            socketHandler.sendViaSocket(dbms.insertPreferences(up));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getUserPreferences(){
        ArrayList<LibraryDescriptor> res;
        try {
            int user_id = (int)objectInputStream.readObject();
            ArrayList<Integer> pref_lib_ids = dbms.getUserPreferences(user_id);
            socketHandler.sendViaSocket(Constants.GET_USER_PREFERENCES);

            res = new ArrayList<>();
            for ( Integer id : pref_lib_ids)
                res.add(getLibraryDescriptor(id));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            res = null;
        }
        socketHandler.sendViaSocket(res);

    }

    private void insertRating(){

        try {
            Rating rating = (Rating) objectInputStream.readObject();
            String schema_name = dbms.getSchemaNameLib(rating.getIdLib());
            socketHandler.sendViaSocket(Constants.INSERT_RATING);
            socketHandler.sendViaSocket(dbms.insertRating(rating, schema_name));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // ONLY FOR TESTING

    private void test_conn() {

        try {
            objectOutputStream.writeObject("test");
            objectOutputStream.flush();
            objectOutputStream.reset();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("SENT\n");
    }

    //////////////////////////////////////METHODS THAT INTERACT WITH THE PROPIETARY DB//////////////////////////////////


    private void librayConnInfo() {
        try {
            int id_lib = (int)objectInputStream.readObject();
            String schema_lib = dbms.getSchemaNameLib(id_lib);
            socketHandler.sendViaSocket(Constants.GET_LIBRARY_CONN_INFO);
            socketHandler.sendLibraryConnInfo(schema_lib);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void userRegistration(){
        try {
            User user = (User) objectInputStream.readObject();
            socketHandler.sendViaSocket(Constants.REGISTER_USER);

            if (!dbms.checkUserExsist(user.getUsername())) {
                socketHandler.sendViaSocket(dbms.addUser(user));

            }else
                socketHandler.sendViaSocket(false);



        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void userLogin(){
        try {
            User user = (User) objectInputStream.readObject();
            socketHandler.sendViaSocket(Constants.USER_LOGIN);
            socketHandler.sendViaSocket(dbms.checkCorrectPassword(user));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void passwordForgot(){
        try {
            User user = (User) objectInputStream.readObject();
            socketHandler.sendViaSocket(Constants.PASSWORD_FORGOT);
            socketHandler.sendViaSocket(dbms.passwordChangeAfterForgot(user));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
