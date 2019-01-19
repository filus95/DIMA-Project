package com.easylib.network.socket;


/**
 *  Constants
 */
public class Constants {

    static final String GET_USER_RATED_BOOKS = "get_user_rated_books";
    static final String QUERY_ON_BOOKS_ALL_LIBRARIES = "query_on_books_all_libraries";
    static final String GET_WAITING_LIST_USER = "get_waiting_list_user";
    static final String GET_USER_RESERVATION = "get_user_reservation";
    static final String GET_EVENTS = "get_events";
    static final String GET_NEWS = "get_news";
    static final String GET_WAITING_LIST_BOOK = "get_waiting_list_book";
    static final String GET_USER_PREFERENCES = "get_user_preferences";
    static final String INSERT_RATING = "insert_rating";
    static final String INSERT_PREFERENCE = "insert_preference";
    static final String PASSWORD_FORGOT = "password_forgot";
    static final String USER_LOGIN = "user_login";
    static final String GET_ALL_LIBRARIES = "get_all_libraries";
    static final String GET_LIBRARY_INFO = "get_library_info";
    static final String INSERT_NEW_LIBRARY = "insert_new_library";
    static final String REGISTER_USER = "register_user";
    static final String INSERT_WAITING_PERSON = "insert_waiting_person";
    static final String INSERT_NEWS = "insert_news";
    static final String QUERY_ON_BOOKS = "query_on_books" ;
    static final String GET_LIBRARY_CONN_INFO = "library_conn_info";
    static final String INSERT_RESERVATION = "insert_reservation";
    static final String INSERT_EVENT_PARTICIPANT = "insert_event_participant";
    static final String INSERT_EVENT = "insert_event";
    static final String TEST_CONN = "test";
    static final String GET_ALL_BOOKS = "get_all_books";
    static final String NEW_NOTIFICATION_TOKEN = "new_notification_token";

    // Communication strings

    // DB strings
    // Schema names
    public static final String PROPIETARY_DB = "propietary_db";
    public static final String LIBRARY_1_DB = "library_1";


    // Table names
    // PROPIETARY_DB
    public static final String PREFERENCE_TABLE_NAME = "users_preferences";
    public static final String USERS_TABLE_NAME = "users";
    public static final String NOTIFICATION_TABLE_NAME = "notifications";
    public static final String LIBRARIES_TABLE_NAME = "libraries";


    //LIBRARY DB
    public static final String BOOKS_TABLE_NAME = "books";
    public static final String RESERVATIONS_TABLE_NAME = "booksreservations";
    public static final String EVENT_PARTECIPANT_TABLE_NAME = "event_partecipants";
    public static final String EVENTS_TABLE_NAME = "events";
    public static final String NEWS_TABLE_NAME = "news";
    public static final String RATINGS_TABLE_NAME = "ratings";
    public static final String WAITING_LIST_TABLE_NAME = "waitinglist";

    public static final String SERVER_KEY = "AAAAhmRahuY:APA91bHb85Vko2UAgMKbXJuSmH7477oWLoLRuhgvknl8h1o-" +
            "M14C7OFGX99sYvVI9Aa_o0uwxZNmKu5gwKqP4y2tc5XbAKZ0uQ-PdnHXF3IL9VBS3IS8lo8GIj3pheqLmxLipS5lJrrp";



}
