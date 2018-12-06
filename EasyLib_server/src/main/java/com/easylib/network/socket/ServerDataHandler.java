package com.easylib.network.socket; /**
 * Created by raffaelebongo on 21/05/17.
 */

import com.easylib.server.Database.AnswerClasses.Book;
import com.easylib.server.Database.AnswerClasses.Query;
import com.easylib.server.Database.DatabaseManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

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
        // Add new methods
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //TODO: METHODS THAT CALLS GOOGLE BOOKS OR DBMS METHODS WITH THE PARAMETERS ARRIVED FROM THE CLIENT AND CALL
    //TODO: THE CALLBACKS METHODS ON THE SOCKETTHREADHANDLER CLASS TO SEND BACK THE OUTPUT


    //////////////////////////////////////METHODS THAT INTERACT WITH THE LIB DB////////////////////////////////////////////


    private void getAllBooks(){
        socketHandler.sendBooks(dbms.queryAllBooks());
    }

    private void bookQuery(){
        try {
            ArrayList<Book> result;
            Query query = (Query)objectInputStream.readObject();
            if (query.getTitle()!=null && query.getAuthor() == null && query.getCategory() == null)
                result = dbms.queryBooksByTitle(query.getTitle());
            else if (query.getTitle()==null && query.getAuthor() != null && query.getCategory() == null)
                result = dbms.queryBooksByAuthor(query.getAuthor());
            else if(query.getTitle()==null && query.getAuthor() == null && query.getCategory() != null)
                result = dbms.queryBooksByCategory(query.getCategory());
            else if(query.getTitle()!=null && query.getAuthor() != null && query.getCategory() == null)
                result = dbms.queryBooksByAuthorAndTitle(query.getTitle(), query.getAuthor());
            else if (query.getTitle()==null && query.getAuthor() != null && query.getCategory() != null)
                result = dbms.queryBooksByAuthorAndCategory(query.getCategory(), query.getAuthor());
            else if (query.getTitle()!=null && query.getAuthor() == null && query.getCategory() != null)
                result = dbms.queryBooksByTitleAndCategory(query.getTitle(), query.getCategory());
            else
                result = dbms.queryBooksByAll(query.getTitle(), query.getAuthor(),query.getCategory());

            socketHandler.sendBooks(result);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

     // ONLY FOR TESTING

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


    //////////////////////////////////////METHODS THAT INTERACT WITH THE PROPIETARY DB//////////////////////////////////

    private void librayConnInfo() {
        try {
            int id_lib = (int)objectInputStream.readObject();
            String schema_lib = dbms.getLibInfo(id_lib);
            socketHandler.sendLibraryConnInfo(schema_lib);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FunctionalInterface
    private interface MethodsHadler{
        void handle() throws IOException, ClassNotFoundException;
    }
}
