package com.easylib.server.Database;

import AnswerClasses.*;
import com.easylib.network.socket.Constants;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DatabaseManager {

    private DatabaseConnection dbConnection = new DatabaseConnection();
    private Connection conn = dbConnection.startConnection();
    PasswordManager pm = new PasswordManager(conn, this);

    // todo this can be generalized for any insertion: look down
//    void insertNewBooks(Map<String, Object> map, String tableName) {
//        try {
//            String query = createInsertStatement(map, tableName);
//
//           //todo delete
//            columns_name.append(", ").append("quantity");
//            PreparedStatement pstmt = conn.prepareStatement(query);
//
//            int count = 1;
//            Object value;
//            for (String key: map.keySet()) {
//                value = map.get(key);
//                pstmt.setObject(count, value);
//                count++;
//            }
//
//            //todo delete
//            pstmt.setObject(count, count);
//
//
//            pstmt.executeUpdate();
//            pstmt.close();
//
//            //Add e.printStacktrace to debug
//        } catch (SQLException e) {
//           e.printStackTrace();
//        }
//
//        try {
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
    private String createInsertStatement(Map<String, Object> map, String tableName, String schema_name){
        StringBuilder columns_name = new StringBuilder();
        StringBuilder values = new StringBuilder();
        int count = 0;
        int lenght = map.size();
        for (String key: map.keySet()) {
            if ((count != lenght - 1)) {
                columns_name.append(key).append(", ");
                values.append("?, ");
            } else {
                columns_name.append(key);
                values.append("?");
            }

            count++;
        }

        return  "INSERT INTO "+schema_name+"."+tableName+" ("+columns_name+")" + "VALUES ("+values+")";
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //TODO: Create insertion methods that compose the colums names argument used for the precise table

    // INSERTION
    /**
     * Construct the map for the bookreservation insertion and call the general insertStatement method that perform
     * the actual query
     *
     */
    public boolean insertNewReservation(Reservation reservInfo, String schema_name){

        Map<String, Object> map = new HashMap<>();
        ArrayList<String> columnsName = new ArrayList<>();
        columnsName.add("user_id");
        columnsName.add("book_identifier");
        columnsName.add("book_title");
        columnsName.add("reservation_date");
        columnsName.add("starting_reservation_date");
        columnsName.add("ending_reservation_date");
        columnsName.add("quantity");
        // todo: correct bug in jar, there is a duplicated user id
        columnsName.add("user_id");


        // Columns name passed must be in the order of the DB columns
        map = reservInfo.getMapAttribute(columnsName);

        return insertStatement(map, Constants.RESERVATIONS_TABLE_NAME, schema_name);
    }

    public ArrayList<Reservation> getReservations(int user_id, String schema_name) {
        String query = "select * from "+schema_name+"."+Constants.RESERVATIONS_TABLE_NAME+
                " where user_id = "+user_id;

        return getQueryReservations(query);
    }

    private ArrayList<Reservation> getQueryReservations(String query) {
        ArrayList<Reservation> results = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                Reservation queryResult = new Reservation();
                queryResult.setReservation_id(rs.getInt("reservation_id"));
                queryResult.setUser_id(rs.getInt("user_id"));
                queryResult.setBook_idetifier(rs.getString("book_identifier"));
                queryResult.setBook_title(rs.getString("book_title"));
                queryResult.setStart_res_date(rs.getDate("starting_reservation_date").toLocalDate());
                queryResult.setEnd_res_date(rs.getDate("ending_reservation_date").toLocalDate());
                queryResult.setQuantity(rs.getInt("quantity"));

                results.add(queryResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            results = null;
        }
        return results;
    }

    public boolean insertNewEventPartecipant(Event_partecipant partecipant, String schema_name){

        Map<String, Object> map = new HashMap<>();
        ArrayList<String> columnsName = new ArrayList<>();
        columnsName.add("event_id");
        columnsName.add("partecipant_id");

        map = partecipant.getMapAttribute(columnsName);

        return insertStatement(map, Constants.EVENT_PARTECIPANT_TABLE_NAME, schema_name);
    }


    public boolean insertNewEvent(Event event, String schema_name) {

        Map<String, Object> map = new HashMap<>();
        ArrayList<String> columnsName = new ArrayList<>();
        columnsName.add("title");
        columnsName.add("description");
        columnsName.add("image_link");
        columnsName.add("seats");
        columnsName.add("date");

        map = event.getMapAttribute(columnsName);

        return insertStatement(map, Constants.EVENTS_TABLE_NAME, schema_name);
    }

    public boolean insertNews(News news, String schema_name) {

        Map<String, Object> map = new HashMap<>();
        ArrayList<String> columnsName = new ArrayList<>();
        columnsName.add("title");
        columnsName.add("post_date");
        columnsName.add("content");
        columnsName.add("image_link");

        map = news.getMapAttribute(columnsName);

        return insertStatement(map, Constants.NEWS_TABLE_NAME, schema_name);
    }

    public boolean insertNewWaitingPerson(WaitingPerson wp, String schema_name) {

        Map<String, Object> map;
        ArrayList<String> columnsName = new ArrayList<>();
        columnsName.add("user_id");
        columnsName.add("book_identifier");
        columnsName.add("waiting_position");
        columnsName.add("reservation_date");
        columnsName.add("starting_reservation_date");
        columnsName.add("ending_reservation_date");
        columnsName.add("quantity");

        map = wp.getMapAttribute(columnsName);

        return insertStatement(map, Constants.WAITING_LIST_TABLE_NAME, schema_name);
    }

    public ArrayList<Book> getWaitingListUser(int user_id, ArrayList<Integer> id_libs) {
        ArrayList<Book> temp_res;
        ArrayList<WaitingPerson> temp_waiting_p;
        ArrayList<Book> res = new ArrayList<>();
        for ( Integer id_lib: id_libs) {
            String schema_name = getSchemaNameLib(id_lib);
            String query = "select * from " + schema_name + ".waitinglist where " +
                    "user_id = " + user_id;

            temp_waiting_p = getQueryResultsWaitingListBook(query);

//            res.addAll(temp_res);
        }
        return res;
    }

    private ArrayList<Book> getQueryResultsWaitingListUser(String query) {
        ArrayList<Book> results = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                WaitingPerson queryResult = new WaitingPerson();
                queryResult.setBook_identifier(rs.getString("book_identifier"));
                queryResult.setWaiting_pos(rs.getInt("waiting_position"));
                queryResult.setReservation_date(rs.getTimestamp("reservation_date").toLocalDateTime());
                queryResult.setQuantity(rs.getInt("quantity"));

//                results.add(queryResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            results = null;
        }
        return results;
    }

    public ArrayList<WaitingPerson> getWaitingList(String book_id, String schema_name){
        String query = "select * from "+schema_name+".waitinglist where " +
                "book_identifier = "+book_id;

        return getQueryResultsWaitingListBook(query);
    }

    public boolean insertPreferences(UserPreferences up){
        ArrayList<String> columnsName = new ArrayList<>();
        columnsName.add("user_id");
        columnsName.add("library_1_id");
        columnsName.add("library_2_id");
        columnsName.add("library_3_id");

        Map<String, Object> map = up.getMapAttribute(columnsName);
        String query_is_user_in = "select * from "+Constants.PROPIETARY_DB+
                "."+Constants.PREFERENCE_TABLE_NAME+" where user_id = ?";

        try {

            PreparedStatement pst = conn.prepareStatement(query_is_user_in);
            pst.setInt(1, up.getUser_id());
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ) {
                return updateStatement (map, Constants.PROPIETARY_DB, Constants.PREFERENCE_TABLE_NAME);
            }
        } catch (SQLException e) {
            return false;
        }

        return insertStatement(map, Constants.PREFERENCE_TABLE_NAME, Constants.PROPIETARY_DB);

    }

    private boolean updateStatement(Map<String, Object> map, String schema_name, String table_name) {
        boolean res = false;
        try {

            String query = createUpdateStatement(map, schema_name, table_name);
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.executeUpdate();
            pstmt.close();
            res = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    private String createUpdateStatement(Map<String, Object> map, String schema_name, String tableName) {
        StringBuilder columns_name = new StringBuilder();
        StringBuilder values = new StringBuilder();
        int count = 0;
        String stm = "UPDATE "+schema_name+"."+tableName+" SET ";// +"+columns_name+" = "+values",";
        columns_name.append(stm);

        int lenght = map.size();
        for (String key: map.keySet()) {
            if ((count != lenght - 1)) {
                columns_name.append(key).append(" = ").append(map.get(key)).append(", ");
            } else
                columns_name.append(key).append(" = ").append(map.get(key));

            count++;
        }

        columns_name.append(" where user_id = ").append(map.get("user_id"));
        System.out.print("ciao");
        return  columns_name.toString();
    }


    public ArrayList<Integer> getUserPreferences(int user_id) {
        String query = "select * from "+Constants.PROPIETARY_DB+
                "."+Constants.PREFERENCE_TABLE_NAME+" where user_id = "+user_id;

        return getQueryResultsPreferences(query);
    }

    public boolean insertNewLibrary(LibraryDescriptor libDesc, String schema_name) {

        Map<String, Object> map;
        ArrayList<String> columnsName = new ArrayList<>();
        columnsName.add("lib_name");
        columnsName.add("schema_name");
        columnsName.add("image_link");
        columnsName.add("telephone_number");
        columnsName.add("address");
        columnsName.add("email");
        columnsName.add("description");

        map = libDesc.getMapAttribute(columnsName);

        return insertStatement(map, Constants.LIBRARIES_TABLE_NAME, schema_name);
    }

    public boolean insertRating(Rating rating, String schema_name) {

        Map<String, Object> map;
        ArrayList<String> columnsName = new ArrayList<>();
        columnsName.add("user_id");
        columnsName.add("book_identifier");
        columnsName.add("rating");
        columnsName.add("id_lib");

        map = rating.getMapAttribute(columnsName);

        return insertStatement(map, Constants.RATINGS_TABLE_NAME, schema_name);
    }


    public boolean insertStatement(Map<String, Object> map, String table_name, String schema_name) {
        boolean res = false;
        try {

            String query = createInsertStatement(map, table_name, schema_name);
            PreparedStatement pstmt = conn.prepareStatement(query);

            int count = 1;
            Object value;
            for (String key : map.keySet()) {
                // todo: translate it in the set method of the corresponding classes
                if (key.equals("reservation_date") || key.equals("post_date")){
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    value = dtf.format(now);
                }
                else
                    value = map.get(key);

                pstmt.setObject(count, value);
                count++;
            }

            pstmt.executeUpdate();
            pstmt.close();
            res = true;
        } catch (SQLException e) {
            e.printStackTrace();
            res = false;
        }

        //todo: necessary to close conn?
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return res;
        }
        return res;
    }

    ///////////////////////////////////// RETRIEVING DATA FROM LIB DB///////////////////////////////////////////////////


    public ArrayList<Book> queryAllBooks(String schema_lib){
        String query = "select identifier, title, publisher, category_1, category_2, category_3," +
                " author_1, author_2, author_3, author_4 " +
                "from "+schema_lib+".books order by id desc";

        return getQueryResultsBooks(query);
    }

    /**
     * Retrieve books by author
     *
     * @param author
     * @param schema_lib
     * @return it must be casted to the book data type in the calling function
     */
    public ArrayList<Book> queryBooksByAuthor(String author, String schema_lib) {
        String query = "select identifier, title, publisher, category_1, category_2, category_3, author_1, author_2, author_3, author_4 " +
                "from "+schema_lib+".books where author_1 = '"+author+"'" + " or author_2='"+author+"'"
                +" or author_3='"+author+"' or author_4='"+author+"'";

        return getQueryResultsBooks(query);
    }

    /**
     * Retrieve books by title
     *
     * @param title
     * @param schema_lib
     * @return
     */
    public ArrayList<Book> queryBooksByTitle(String title, String schema_lib) {
        String query = "select identifier, title, publisher, category_1, category_2, category_3," +
                " author_1, author_2, author_3, author_4 " +
                "from "+schema_lib+".books where title = '"+title+"'";

        return getQueryResultsBooks(query);
    }

    /**
     * Retrieve books by category
     *
     * @param category
     * @param schema_lib
     * @return
     */
    public ArrayList<Book> queryBooksByCategory(String category, String schema_lib) {
        String query = "select identifier, title, publisher, author_1, author_2, author_3, author_4," +
                "category_1, category_2, category_3 "+
        "from "+schema_lib+".books where category_1 ='"+category+"' or category_2='"+category+"' or category_3='"+category+"'";

        return getQueryResultsBooks(query);
    }

    public ArrayList<Book> queryBooksByAuthorAndCategory(String category, String author, String schema_lib) {
        String query = "select identifier, title, publisher, author_1, auhtor_2, author_3, author_4, " +
                "category_1, category_2, category_3 " +
                "from "+schema_lib+".books where ( category_1 ='" + category + "' or category_2='" + category + "' " +
                "or category_3='"
                + category + "') and ( author_1 = '"+author+"'" + "or author_2='"+author+"'" +
                "or author_3='"+author+"'or author_4='"+author+"')";

        return getQueryResultsBooks(query);
    }

    public ArrayList<Book> queryBooksByAuthorAndTitle(String title, String author, String schema_lib) {
        String query = "select identifier, title, publisher, author_1, author_2, author_3, author_4," +
                " category_1, category_2, category_3 " +
                "from "+schema_lib+".books where title = '"+title+"' and ( author_1 = '"+author+"'" + " " +
                "or author_2='"+author+"'" +
                " or author_3='"+author+"' or author_4='"+author+"')";

        return getQueryResultsBooks(query);
    }

    public ArrayList<Book> queryBooksByTitleAndCategory(String title, String category, String schema_lib) {
        String query = "select identifier, title, publisher, author_1, author_2, author_3, author_4, " +
                "category_1, category_2, category_3 " +
                " from "+schema_lib+".books where title = '"+title+"' and ( category_1 = '"+category+"'" + " " +
                "or category_2='"+category+"'" +
                " or category_3='"+category+"')";


        return getQueryResultsBooks(query);
    }

    public ArrayList<Book> queryBooksByAll(String title, String author, String category, String schema_lib) {
        String query = "select identifier, title, publisher, author_1, author_2, author_3, author_4," +
                " category_1, category_2, category_3 " +
                "from "+schema_lib+".books where title = '"+title+"' and ( category_1 = '"+category+"'" + " or category_2='"+category+"'" +
                " or category_3='"+category+"') and (author_1 = '"+author+"'" + " or author_2='"+author+"' or" +
                " author_3='"+author+"' or author_4='"+author+"')";

        return getQueryResultsBooks(query);
    }

    /**
     * Perform a query on the DB
     *
     * @param query
     * @return an array of objects and who receives the result knows the real datatype and cast its content
     */
    private ArrayList<Book> getQueryResultsBooks(String query) {
        ArrayList<Book> results = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                Book queryResult = new Book();
                queryResult.setBookId_lib(rs.getString("identifier"));
                queryResult.setTitle(rs.getString("title"));
                queryResult.setPublisher((rs.getString("publisher")));
                queryResult.setCategories(rs.getString("category_1"),
                        rs.getString("category_2"),
                        rs.getString("category_3"));

                queryResult.setAuthors((String)(rs.getObject("author_1")),
                        (rs.getString("author_2")),
                        (rs.getString("author_3")),
                        (rs.getString("author_4")));
                //todo: should i add these columns in DB?
//                queryResult.setReserved(rs.getBoolean("reserved"));
//                queryResult.setWaiting_list(rs.getBoolean("waiting_list"));
//                queryResult.setAverageRating(rs.getFloat("averageRating"));
                results.add(queryResult);
            }
        } catch (SQLException e) {
          e.printStackTrace();
          results = null;
        }
        return results;
    }

    private ArrayList<Integer> getQueryResultsPreferences(String query) {
        ArrayList<Integer> results = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                UserPreferences queryResult = new UserPreferences();
                results.add(rs.getInt("library_1_id"));
                results.add(rs.getInt("library_2_id"));
                results.add(rs.getInt("library_3_id"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            results = null;
        }
        return results;
    }

    private ArrayList<WaitingPerson> getQueryResultsWaitingListBook(String query){
        ArrayList<WaitingPerson> results = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                WaitingPerson queryResult = new WaitingPerson();
                queryResult.setBook_identifier(rs.getString("book_identifier"));
                queryResult.setWaiting_pos(rs.getInt("waiting_position"));
                queryResult.setReservation_date(rs.getTimestamp("reservation_date").toLocalDateTime());
                queryResult.setUser_id(rs.getInt("user_id"));
                queryResult.setQuantity(rs.getInt("quantity"));

                results.add(queryResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            results = null;
        }
        return results;
    }

    private ArrayList<News> createNewsObject(ArrayList<News> to_ret, String query) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                News elem = new News();
                elem.setTitle(rs.getString("title"));
                elem.setPost_date(rs.getTimestamp("post_date").toLocalDateTime());
                elem.setContent(rs.getString("content"));
                elem.setImage_link(rs.getString("image_link"));
                to_ret.add(elem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            to_ret = null;
        }
        return to_ret;
    }

    public ArrayList<News> getNews(String schema_name, int limit) {
        ArrayList<News> to_ret = new ArrayList<>();

        String query = "select * from "+schema_name+".news " +
                "order by id desc LIMIT "+limit;

        to_ret = createNewsObject(to_ret, query);
        return to_ret;
    }

    public ArrayList<News> getAllNews(String schema_name){
        ArrayList<News> to_ret = new ArrayList<>();

        String query = "select * from "+schema_name+".news " +
                "order by id desc";

        to_ret = createNewsObject(to_ret, query);
        return to_ret;
    }

    public ArrayList<Event> getAllEvents(String schema_name, int limit){
        ArrayList<Event> to_ret = new ArrayList<>();
        String query = "select * from "+schema_name+".events order by" +
                "  id desc";

        to_ret = createEventsObject(to_ret, query);
        return to_ret;
    }

    public ArrayList<Event> getEvents(String schema_name, int limit){
        ArrayList<Event> to_ret = new ArrayList<>();
        String query = "select * from "+schema_name+".events order by" +
                "  id desc LIMIT "+limit;

        to_ret = createEventsObject(to_ret, query);
        return to_ret;
    }

    private ArrayList<Event> createEventsObject(ArrayList<Event> to_ret, String query) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                Event elem = new Event();
                elem.setId(rs.getInt("id"));
                elem.setTitle(rs.getString("title"));
                elem.setDescription(rs.getString("description"));
                elem.setSeats((Integer.parseInt(rs.getString("seats"))));
                elem.setImage_link(rs.getString("image_link"));
                to_ret.add(elem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            to_ret = null;
        }
        return to_ret;
    }

//    private ArrayList<Object> getQueryResultsReservation(String query) {
//        ArrayList<Object> results = new ArrayList<>();
//
//        try {
//            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery(query);
//
//            while (rs.next()){
//                Book queryResult = new Book();
//                queryResult.setBookId_lib((String) rs.getObject("identifier"));
//                queryResult.setTitle((String) rs.getObject("title"));
//                queryResult.setPublisher((String) rs.getObject("publisher"));
//                queryResult.setCategories((String)rs.getObject("category_1"),
//                        (String)rs.getObject("category_2"),
//                        (String)rs.getObject("category_3"));
//
//                queryResult.setAuthors((String)(rs.getObject("author_1")),
//                        (String)(rs.getObject("author_2")),
//                        (String)(rs.getObject("author_3")),
//                        (String)(rs.getObject("author_4")));
//                results.add(queryResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return results;
//    }

    /////////////////////////////////////////////DELETION///////////////////////////////////////////////////////////////

    public boolean deleteStatementReservations( Reservation reservation, String tableName, String schemaName){
        String query = "delete from "+schemaName+"."+tableName+" where " +
                tableName+".book_identifier = "+reservation.getBook_idetifier()+" and " +
                tableName+".user_id = "+reservation.getUser_id();

        return queryExecution(conn, query);
    }

    public boolean deleteStatementUsers( User user, String tableName, String schemaName){
        String query = "delete from "+schemaName+"."+tableName+" where " +
                tableName+".username = "+user.getUsername();

        return queryExecution(conn, query);

    }

    private boolean queryExecution(Connection conn, String query){
        Statement st = null;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    ///////////////////////////////////// RETRIEVING DATA FROM PROPIETARY DB//////////////////////////////////////////////////

    public String getSchemaNameLib(int id_lib) {
        String query = "select schema_name from "+Constants.PROPIETARY_DB+".libraries where " +
                "libraries.id_lib = "+id_lib+"";

        Statement st;
        try {
            st = conn.createStatement();

            ResultSet rs = st.executeQuery(query);
            String res = null;
        while (rs.next()){
            res = rs.getString("schema_name");
        }
        return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LibraryDescriptor getLibraryInfo(int id_lib) {
        String query = "select * from "+ Constants.PROPIETARY_DB+".libraries " +
                "where id_lib = "+id_lib;

        Statement st;
        LibraryDescriptor libraryDescriptor = new LibraryDescriptor();
        try {
            st = conn.createStatement();

            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                libraryDescriptor.setLib_name(rs.getString("lib_name"));
                libraryDescriptor.setSchema_name(rs.getString("schema_name"));
                libraryDescriptor.setImage_link(rs.getString("image_link"));
                libraryDescriptor.setTelephone_number(rs.getString("telephone_number"));
                libraryDescriptor.setAddress(rs.getString("address"));
                libraryDescriptor.setEmail(rs.getString("email"));
                libraryDescriptor.setDescription(rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            libraryDescriptor = null;
        }
        return libraryDescriptor;
    }


    public LibraryContent getLibraryContent(String schema_name) {
        ArrayList<Book> books = queryAllBooks(schema_name);
        books = getNbooks(books, 5);
        ArrayList<News> news = getAllNews(schema_name);
        ArrayList<Event> events = getAllEvents(schema_name, 5);

        LibraryContent lc = new LibraryContent();
        lc.setBooks(books);
        lc.setEvents(events);
        lc.setNews(news);

        return lc;
    }

    private ArrayList<Book> getNbooks(ArrayList<Book> list, int n){
        Collections.shuffle(list);
        ArrayList<Book> to_ret = new ArrayList<>();

        for (int i = 0; i < n; i++ )
            to_ret.add(list.get(i));

        return to_ret;
    }

    public ArrayList<Integer> getAllIdLibs() {
        String query = "select * from "+Constants.PROPIETARY_DB+".libraries";
        Statement st;
        ArrayList<Integer> to_ret = new ArrayList<>();
        try {
            st = conn.createStatement();

            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                to_ret.add(rs.getInt("id_lib"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            to_ret = null;
        }
        return to_ret;

    }


    public boolean checkUserExsist(String email) {
        boolean ret = false;
        try {

            //todo: transform the query in this way
            String sql = "SELECT username, name, surname FROM "+Constants.PROPIETARY_DB+".users " +
                    "WHERE email = ?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if ( rs.getFetchSize() > 0 )
                ret = true;

        } catch (SQLException e) {
            System.out.print("Query Error!");
            e.printStackTrace();
            ret = false;
            conn = dbConnection.startConnection();
        }
        return ret;
    }

    ResultSet queryUser ( User user ){

        ResultSet rs;
        try {
            String query = "select * from "+Constants.PROPIETARY_DB+".users where "+Constants.PROPIETARY_DB+".users.username =" +
                    " "+user.getUsername();

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, user.getUsername());

            rs = st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            rs = null;
        }
        return rs;
    }

    public boolean passwordChangeAfterForgot(User user) {
        boolean res = false;
        try {
            String email = "";
            ResultSet rs = queryUser(user);

            if ( rs.next())
                email = rs.getString("email");

            System.out.print("deleting old row...");
            String tableName = "users";
            deleteStatementUsers(user, tableName, Constants.PROPIETARY_DB);
            System.out.print("changing forgotten password..");
            res = pm.changeForgottenPassword(user.getUsername(), email);
        } catch (SQLException e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    public boolean addUser(User user) {

        byte[] salt = pm.getNextSalt();
        byte[] hashPas = pm.generatePassword(user.getPlainPassword(), salt);

        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("name", user.getName());
        map.put("surname", user.getSurname());
        map.put("email", user.getEmail());
        map.put("hashed_pd", hashPas);
        map.put("salt", salt);

        String tableName = "users";
        return  insertStatement(map, tableName, Constants.PROPIETARY_DB);
    }

    public boolean checkCorrectPassword(User user) {
        boolean to_ret;
        try {
            to_ret = pm.isExpectedPassword(user);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("QUERY ERROR!");
            to_ret = false;
        }
        return to_ret;
    }


    public ArrayList<Book> queryBookByIdentifier(String identifier, String schema_lib) {
        String query = "select identifier, title, publisher, category_1, category_2, category_3, author_1, author_2, author_3, author_4 " +
                "from "+schema_lib+".books where identifier = "+ identifier;

        return getQueryResultsBooks(query);
    }


}

