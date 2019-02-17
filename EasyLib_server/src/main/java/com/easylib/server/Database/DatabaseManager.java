package com.easylib.server.Database;

import AnswerClasses.*;
import com.easylib.network.socket.Constants;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.sql.*;
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

        if (zeroQuantity(reservInfo,schema_name,Constants.BOOKS_TABLE_NAME,"identifier"))
            return false;

        Map<String, Object> map = new HashMap<>();
        ArrayList<String> columnsName = new ArrayList<>();
        columnsName.add("user_id");
        columnsName.add("book_identifier");
        columnsName.add("book_title");
        columnsName.add("starting_reservation_date");
        columnsName.add("ending_reservation_date");
        columnsName.add("quantity");
        columnsName.add("taken");
        columnsName.add("reservation_date");
//        // todo: correct bug in jar, there is a duplicated user id
//        columnsName.add("user_id");

        // Columns name passed must be in the order of the DB columns
        map = reservInfo.getMapAttribute(columnsName);

        return insertStatement(map, Constants.RESERVATIONS_TABLE_NAME, schema_name);
    }

    private boolean zeroQuantity(Reservation res, String schema_name, String table_name, String column){

        String query = "select quantity from "+schema_name+"."+table_name+" where " +
                column+" = '"+res.getBook_idetifier()+"'";

        return queryExecute(query, "quantity");
    }

    private boolean zeroSeats(Event_partecipant ep, String schema_name, String table_name, String column){

        String query = "select seats from "+schema_name+"."+table_name+" where " +
                column+" = '"+ep.getEvent_id()+"'";

        return queryExecute(query, "seats");
    }

    private boolean queryExecute(String query, String quantity) {
        Statement st;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next())
                if (rs.getInt(quantity) == 0)
                    return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Reservation> getReservations(int user_id, String schema_name, int id_lib) {
        String query = "select * from "+schema_name+"."+Constants.RESERVATIONS_TABLE_NAME+
                " where "+schema_name+"."+Constants.RESERVATIONS_TABLE_NAME+".user_id = "+user_id+" order by "
                +schema_name+".booksreservations.user_id desc";

        return getQueryReservations(query, schema_name, id_lib);
    }



    private ArrayList<Reservation> getQueryReservations(String query, String schema_lib, int id_lib) {
        ArrayList<Reservation> results = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                Reservation queryResult = new Reservation();
                queryResult.setReservation_id(rs.getInt("reservation_id"));
                queryResult.setUser_id(rs.getInt("user_id"));
                String book_identifier = rs.getString("book_identifier");
                ArrayList<Book> books = queryBookByIdentifier(book_identifier, schema_lib,id_lib);
                if ( books.size() != 0)
                    queryResult.setBook(books.get(0));
                queryResult.setBook_idetifier(rs.getString("book_identifier"));
                queryResult.setBook_title(rs.getString("book_title"));
                queryResult.setQuantity(rs.getInt("quantity"));
                queryResult.setTaken(rs.getBoolean("taken"));
                queryResult.setIdLib(id_lib);

                if ( rs.getDate("starting_reservation_date") != null)
                    queryResult.setStart_res_date(rs.getDate("starting_reservation_date").toLocalDate().toString());

                if ( rs.getDate("ending_reservation_date") != null)
                    queryResult.setEnd_res_date(rs.getDate("ending_reservation_date").toLocalDate().toString());

                queryResult.setReservation_time(rs.getDate("reservation_date").toLocalDate().toString());
                results.add(queryResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            results = null;
        }
        return results;
    }

    public boolean insertNewEventPartecipant(Event_partecipant partecipant, String schema_name){

        if (zeroSeats(partecipant, schema_name,Constants.EVENTS_TABLE_NAME, "id"))
            return false;

        Map<String, Object> map = new HashMap<>();
        ArrayList<String> columnsName = new ArrayList<>();
        columnsName.add("event_id");
        columnsName.add("partecipant_id");

        map = partecipant.getMapAttribute(columnsName);

        return insertStatement(map, Constants.EVENT_PARTICIPANT_TABLE_NAME, schema_name);
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

    public boolean insertNewWaitingPerson(WaitingPersonInsert wp, String schema_name) {

        Map<String, Object> map;
        ArrayList<String> columnsName = new ArrayList<>();
        columnsName.add("user_id");
        columnsName.add("book_identifier");
        columnsName.add("waiting_position");
        columnsName.add("reservation_date");
        columnsName.add("quantity");

        map = wp.getMapAttribute(columnsName);

        return insertStatement(map, Constants.WAITING_LIST_TABLE_NAME, schema_name);
    }

    public WaitingPerson getWaitingListUser(int user_id, ArrayList<Integer> id_libs) {
        WaitingPerson result = new WaitingPerson();
        for ( Integer id_lib: id_libs) {
            String schema_name = getSchemaNameLib(id_lib);
            String query = "select * from " + schema_name + ".waitinglist where " +
                    "user_id = " + user_id;

            result = getQueryResultsWaitingListUser(query, schema_name, result, id_lib);
        }
        return result;
    }

    private WaitingPerson getQueryResultsWaitingListUser(String query, String schema_name, WaitingPerson result, int id_lib) {

        try {
            ArrayList<Book> books = new ArrayList<>();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            boolean first = true;
            boolean zero = true;
            while (rs.next()){
                zero = false;
                Book temp = queryBookByIdentifier(rs.getString("book_identifier"),
                        schema_name, id_lib).get(0);
                temp.setWaiting_position(rs.getInt("waiting_position"));
                temp.setReservation_date(rs.getTimestamp("reservation_date").toLocalDateTime().toString());
                temp.setQuantity_reserved(rs.getInt("quantity"));
                books.add(temp);

                if ( first ) {
                    result.setUser_id(rs.getInt("user_id"));
                    first = false;
                }
            }

            if(result.getBooksInWaitingList()!= null) {
                books.addAll(result.getBooksInWaitingList());
                result.setBooksInWaitingList(books);
            } else
                result.setBooksInWaitingList(books);
        } catch (SQLException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    public ArrayList<WaitingPerson> getWaitingList(String book_id, String schema_name, int id_lib){
        String query = "select * from "+schema_name+".waitinglist where " +
                "book_identifier = "+book_id;

        return getQueryResultsWaitingListBook(query, schema_name, id_lib);
    }

    public boolean insertPreferences(UserPreferences up){
        ArrayList<String> columnsName = new ArrayList<>();

        String query_is_user_in = "select library_1_id, library_2_id, library_3_id from "+Constants.PROPIETARY_DB+
                "."+Constants.PREFERENCE_TABLE_NAME+" where user_id = ?";

        try {

            PreparedStatement pst = conn.prepareStatement(query_is_user_in);
            pst.setInt(1, up.getUser_id());
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ) {
                columnsName.add("user_id");
                columnsName.add("library_id_update");
                Map<String, Object> map = up.getMapAttribute(columnsName);

                String to_fill;
                if ( rs.getInt("library_1_id") == 0 )
                    to_fill = "library_1_id";
                else if (rs.getInt("library_2_id") == 0)
                    to_fill = "library_2_id";
                else if (rs.getInt("library_3_id") == 0)
                    to_fill = "library_3_id";

                // All the slots are full. Need to edit the profile first
                else
                    return false;
                return updateStatement (to_fill, map, Constants.PROPIETARY_DB, Constants.PREFERENCE_TABLE_NAME);
            }
        } catch (SQLException e) {
            return false;
        }

        columnsName.add("user_id");
        columnsName.add("library_1_id");
        Map<String, Object> map = up.getMapAttribute(columnsName);
        return insertStatement(map, Constants.PREFERENCE_TABLE_NAME, Constants.PROPIETARY_DB);

    }

    public Boolean editProfileInfo(User user) {

        boolean res;
        StringBuilder columns_name = new StringBuilder();
        StringBuilder columns_name1 = new StringBuilder();
        String stm = "UPDATE " + Constants.PROPIETARY_DB + "." + Constants.USERS_TABLE_NAME + " " +
                "SET ";// +"+columns_name+" = "+values",";
        columns_name.append(stm);
        Map<String,Object> map = new HashMap<>();
        PreparedStatement preparedStatement = null;

        try {

            if (user.getPlainPassword() != null) {
                byte[] salt = pm.getNextSalt();
                byte[] hashPas = pm.generatePassword(user.getPlainPassword(), salt);

                columns_name1 = columns_name;
                columns_name1.append("hashed_pd = ? where user_id = ").append(user.getUser_id());
                preparedStatement = conn.prepareStatement(columns_name1.toString());
                preparedStatement.setObject(1, hashPas);
                preparedStatement.executeUpdate();

                columns_name1 = new StringBuilder();
                columns_name1.append(stm);
                columns_name1.append(" salt = ? where user_id = ").append(user.getUser_id());
                preparedStatement = conn.prepareStatement(columns_name1.toString());
                preparedStatement.setObject(1, salt);
                preparedStatement.executeUpdate();
            }

            if (user.getName() != null) {
                columns_name1 = new StringBuilder();
                columns_name1.append(stm);
                columns_name1.append(" name = ? where user_id = ").append(user.getUser_id());
                preparedStatement = conn.prepareStatement(columns_name1.toString());
                preparedStatement.setObject(1, user.getName());
                preparedStatement.executeUpdate();
            }

            if (user.getSurname() != null) {
                columns_name1 = new StringBuilder();
                columns_name1.append(stm);
                columns_name1.append(" surname = ? where user_id = ").append(user.getUser_id());
                preparedStatement = conn.prepareStatement(columns_name1.toString());
                preparedStatement.setObject(1, user.getSurname());
                preparedStatement.executeUpdate();
            }

            // Statement execution
            preparedStatement.close();
            res = true;

        } catch (SQLException e) {
            e.printStackTrace();
            res = false;
        }

        return res;
    }

    private boolean updateStatement(String to_fill, Map<String, Object> map, String schema_name, String table_name) {
        boolean res = false;
        try {

            String query = createUpdateStatement(to_fill, map, schema_name, table_name);
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.executeUpdate();
            pstmt.close();
            res = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    // todo: maybe update can raise probles
    private String createUpdateStatement(String to_fill, Map<String, Object> map, String schema_name, String tableName) {
        StringBuilder columns_name = new StringBuilder();
        StringBuilder values = new StringBuilder();
        int count = 0;
        String stm = "UPDATE "+schema_name+"."+tableName+" SET ";// +"+columns_name+" = "+values",";
        columns_name.append(stm);

//        int lenght = map.size();
//        for (String key: map.keySet()) {

//            if ((count != lenght - 1)) {
//                columns_name.append(to_fill).append(" = ").append(map.get("library_to_add")).append(", ");
//            } else
        columns_name.append(to_fill).append(" = ").append(map.get("library_id_update"));

//            count++;
//        }

        columns_name.append(" where user_id = ").append(map.get("user_id"));
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
                if ((key.equals("reservation_date") || key.equals("post_date"))){
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

        return res;
    }

    ///////////////////////////////////// RETRIEVING DATA FROM LIB DB///////////////////////////////////////////////////


    public ArrayList<Book> queryAllBooks(String schema_lib, int lib_id){
        String query = "select * " +
                "from "+schema_lib+".books";

        return getQueryResultsBooks(query, lib_id);
    }

    /**
     * Retrieve books by author
     *
     * @param author
     * @param schema_lib
     * @return it must be casted to the book data type in the calling function
     */
    public ArrayList<Book> queryBooksByAuthor(String author, String schema_lib, int lib_id) {
        String query = "select * " +
                "from "+schema_lib+".books where lower(author_1) like  '%"+author+"%'" + " or lower(author_2) like'%"+author+"%'"
                +" or lower(author_3) like '%"+author+"%' or lower(author_4) like '%"+author+"%'";

        return getQueryResultsBooks(query, lib_id);
    }

    public ArrayList<Book> getReadBooks(int user_id) {
        String query = "select * from "+Constants.PROPIETARY_DB+".read_books" +
                " where user_id = "+user_id;

        ArrayList<Book> read_books = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                read_books.add(queryBookByIdentifier(rs.getString("book_identifier"),
                        getSchemaNameLib(rs.getInt("id_lib")),rs.getInt("id_lib")).get(0));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            read_books = null;
        }
        return read_books;


    }

    /**
     * Retrieve books by title
     *
     * @param title
     * @param schema_lib
     * @return
     */
    public ArrayList<Book> queryBooksByTitle(String title, String schema_lib, int lib_id) {
        String query = "select *" +
                "from "+schema_lib+".books where lower(title) like '%"+title+"%'";

        return getQueryResultsBooks(query, lib_id);
    }

    /**
     * Retrieve books by category
     *
     * @param category
     * @param schema_lib
     * @return
     */
    public ArrayList<Book> queryBooksByCategory(String category, String schema_lib, int lib_id) {
        String query = "select * "+
        "from "+schema_lib+".books where lower(category_1) like '%"+category+"%' or lower(category_2) like '%"+category+"%' or " +
                "lower(category_3) like '%"+category+"%'";

        return getQueryResultsBooks(query, lib_id);
    }

    public ArrayList<Book> queryBooksByAuthorAndCategory(String category, String author, String schema_lib, int lib_id) {
        String query = "select *" +
                "from "+schema_lib+".books where ( lower(category_1) like '%" + category + "%' or lower(category_2) like '%" + category + "%' " +
                "or lower(category_3) like'%"
                + category + "%') and ( lower(author_1) like '%"+author+"%'" + "or lower(author_2) like '%"+author+"%'" +
                "or lower(author_3) like '%"+author+"%'or lower(author_4)like '%"+author+"%')";

        return getQueryResultsBooks(query, lib_id);
    }

    public ArrayList<Book> queryBooksByAuthorAndTitle(String title, String author, String schema_lib, int lib_id) {
        String query = "select * " +
                "from "+schema_lib+".books where lower(title) like '"+title+"%' and ( lower(author_1) like '"+author+"%'" + " " +
                "or lower(author_2) like '%"+author+"%'" +
                " or lower(author_3) like '%"+author+"%' or lower(author_4) like '%"+author+"%')";

        return getQueryResultsBooks(query, lib_id);
    }

    public ArrayList<Book> queryBooksByTitleAndCategory(String title, String category, String schema_lib, int lib_id) {
        String query = "select * " +
                " from "+schema_lib+".books where lower(title) like '%"+title+"%' and ( lower(category_1) like '%"+category+"%'" + " " +
                "or lower(category_2) like '%"+category+"%'" +
                " or lower(category_3) like '%"+category+"%')";


        return getQueryResultsBooks(query, lib_id);
    }

    public ArrayList<Book> queryBooksByAll(String title, String author, String category, String schema_lib, int lib_id) {
        String query = "select * " +
                "from "+schema_lib+".books where lower(title) like '%"+title+"%' and ( lower(category_1) like '%"+category+"%'" + " or " +
                "lower(category_2) like '%"+category+"%'" +
                " or lower(category_3) like '%"+category+"%') and (lower(author_1) like '%"+author+"%'" + " or lower(author_2) like '%"+author+"%' or" +
                " lower(author_3) like '%"+author+"%' or lower(author_4) like '%"+author+"%')";

        return getQueryResultsBooks(query, lib_id);
    }

    /**
     * Perform a query on the DB
     *
     * @param query
     * @return an array of objects and who receives the result knows the real datatype and cast its content
     */
    private ArrayList<Book> getQueryResultsBooks(String query, int lib_id) {
        ArrayList<Book> results = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                Book queryResult = new Book();
                queryResult.setLib_name(getLibraryInfo(lib_id).getLib_name());
                queryResult.setIdentifier(rs.getString("identifier"));
                queryResult.setTitle(rs.getString("title"));
                queryResult.setPublisher((rs.getString("publisher")));
                queryResult.setCategories(rs.getString("category_1"),
                        rs.getString("category_2"),
                        rs.getString("category_3"));

                queryResult.setAuthors((String)(rs.getObject("author_1")),
                        (rs.getString("author_2")),
                        (rs.getString("author_3")),
                        (rs.getString("author_4")));

                queryResult.setIdLibrary(lib_id);
                queryResult.setQuantity_reserved(rs.getInt("quantity"));

                // Create JSON Array from String
                if ( rs.getString("imageLinks") != null ) {
                    JsonParser jsonParser = new JsonParser();
                    JsonObject imageLinks = jsonParser.parse(rs.getString("imageLinks")).getAsJsonObject();
                    queryResult.setImageLink(String.valueOf(imageLinks.get("thumbnail")).replace("\"", ""));
                }
                //todo: should i add these columns in DB?
//                queryResult.setReserved(rs.getBoolean("reserved"));
//                queryResult.setWaiting_list(rs.getBoolean("waiting_list"));
                queryResult.setDescription(rs.getString("description"));
                queryResult.setAverageRating(rs.getFloat("averageRating"));
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

    private ArrayList<WaitingPerson> getQueryResultsWaitingListBook(String query, String schema_name, int id_lib){
        ArrayList<WaitingPerson> results = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                WaitingPerson queryResult = new WaitingPerson();
                Book book = queryBookByIdentifier(rs.getString("book_identifier"),
                        schema_name, id_lib).get(0);
                book.setWaiting_position(rs.getInt("waiting_position"));
                book.setReservation_date(rs.getTimestamp("reservation_date").toLocalDateTime().toString());
                book.setQuantity_reserved(rs.getInt("quantity"));
                ArrayList<Book> books = new ArrayList<>();
                books.add(book);
                queryResult.setUser_id(rs.getInt("user_id"));
                queryResult.setBooksInWaitingList(books);

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
                elem.setPost_date(rs.getTimestamp("post_date").toLocalDateTime().toString());
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

    public ArrayList<Event> getAllEvents(String schema_name, int id_lib){
        ArrayList<Event> to_ret = new ArrayList<>();
        String query = "select * from "+schema_name+".events order by" +
                "  id desc";

        to_ret = createEventsObject(to_ret, query, id_lib);
        return to_ret;
    }

    public ArrayList<Event> getEvents(String schema_name, int limit, int id_lib){
        ArrayList<Event> to_ret = new ArrayList<>();
        String query = "select * from "+schema_name+".events order by" +
                "  id desc LIMIT "+limit;

        to_ret = createEventsObject(to_ret, query, id_lib);
        return to_ret;
    }

    public ArrayList<Event> getEventsPerUser(int user_id) {

        ArrayList<Integer> events_id = new ArrayList<>();
        ArrayList<Event> to_ret = new ArrayList<>();

        ArrayList<Integer> libs_id = getAllIdLibs();

        for ( Integer lib_id: libs_id) {
            String schema_name = getSchemaNameLib(lib_id);
            String query = "select event_id from " + schema_name + ".event_partecipants" +
                    " where partecipant_id = "+user_id+" order by" +
                    "  id desc";
            events_id = eventsIdPerUser(query);

            for (Integer event_id : events_id) {
                String query1 = "select * from " + schema_name + ".events" +
                        " where id = "+event_id+" order by" +
                        "  id desc";
                to_ret = createEventsObject(to_ret, query1, lib_id);
            }
        }

        return to_ret;
    }

    private ArrayList<Integer> eventsIdPerUser(String query){
        ArrayList<Integer> to_ret = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                to_ret.add(rs.getInt("event_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            to_ret = null;
        }
        return to_ret;
    }

    private ArrayList<Event> createEventsObject(ArrayList<Event> to_ret, String query, int lib_id) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while (rs.next()){
                Event elem = new Event();
                elem.setId(rs.getInt("id"));
                elem.setTitle(rs.getString("title"));
                elem.setDescription(rs.getString("description"));
                elem.setDate(rs.getTimestamp("date").toLocalDateTime().toString());
                elem.setSeats((Integer.parseInt(rs.getString("seats"))));
                elem.setImage_link(rs.getString("image_link"));
                elem.setIdLib(lib_id);
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

    public boolean removeReservation(Reservation reservation, boolean book_returned ){

        // insert read book
        if ( book_returned ) {
            if ( !bookAlreadyRead(reservation.getUser_id(), reservation.getBook_idetifier())) {
                Map<String, Object> map = new HashMap<>();
                map.put("user_id", reservation.getUser_id());
                map.put("book_identifier", reservation.getBook_idetifier());
                map.put("id_lib", reservation.getIdLib());
                insertStatement(map, Constants.READ_BOOKS_TABLE, Constants.PROPIETARY_DB);
            }
        }

        // Waiting list flow
        String query_1 = "select * from "+getSchemaNameLib(reservation.getIdLib())+".waitinglist " +
                "where waiting_position = 1 and book_identifier = '"+reservation.getBook_idetifier()+"'";

        // Delete old reservation
        String query = "delete from "+getSchemaNameLib(reservation.getIdLib())+"."+
                Constants.RESERVATIONS_TABLE_NAME+" where " +
                "book_identifier = '"+reservation.getBook_idetifier()+"'" + " and " +
                "user_id = "+reservation.getUser_id();


        ArrayList<WaitingPerson> waitingPeople = getQueryResultsWaitingListBook(query_1, getSchemaNameLib(reservation.getIdLib()),
                reservation.getIdLib());

        //set notification
        String library_name = getLibraryInfo(reservation.getIdLib()).getLib_name();
        String book_title =queryBookByIdentifier(reservation.getBook_idetifier(), getSchemaNameLib(reservation.getIdLib()),
                reservation.getUser_id())
                .get(0)
                .getTitle();

        String title = "Your book is available!";
        String mess = ""+book_title+" is available in "+library_name;

        if ( waitingPeople.size() != 0)
            sendNotification(title,mess, getNotificationToken(waitingPeople.get(0).getUser_id()));

        queryExecution(conn, query);
        WaitingPerson wp;
        if ( waitingPeople.size() != 0 ){
            wp = waitingPeople.get(0);
            Book book = wp.getBooksInWaitingList().get(0);
            Reservation res = new Reservation();
            res.setUser_id(wp.getUser_id());
            res.setBook_idetifier(book.getIdentifier());
            res.setBook_title(book.getTitle());
            res.setQuantity(1);
            res.setReservation_time(book.getReservation_date());

            return insertNewReservation(res, getSchemaNameLib(reservation.getIdLib()));
        }
        return true;
    }

    private boolean bookAlreadyRead(int user_id, String book_idetifier) {
        String query = "select * from "+Constants.PROPIETARY_DB+"."+Constants.READ_BOOKS_TABLE+"" +
                " where book_identifier = '"+book_idetifier+"' and " +
                "user_id = "+user_id;

        return checkOneResult(query);

    }

    private boolean checkOneResult(String query) {
        Statement st;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeEventParticipant(Event_partecipant participant, String schema_name) {
        String query = "delete from "+schema_name+"."+Constants.EVENT_PARTICIPANT_TABLE_NAME +" where " +
                "partecipant_id = "+participant.getPartecipant_id()+" and event_id = "+participant.getEvent_id();

        return queryExecution(conn, query);
    }

    public boolean removeWaitingPerson(WaitingPersonInsert wp, String schema_name) {
        String query_waiting_to_delete = "select waiting_position from "+schema_name+"."+Constants.WAITING_LIST_TABLE_NAME
                + " where user_id = "+wp.getUser_id()+" and book_identifier = "+wp.getBook_identifier();

        Integer wpDelUser = null;
        Statement st = null;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(query_waiting_to_delete);

            while (rs.next())
                wpDelUser = rs.getInt("waiting_position");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query = "delete from "+schema_name+"."+Constants.WAITING_LIST_TABLE_NAME+" where " +
                "user_id = "+wp.getUser_id()+" and book_identifier = "+wp.getBook_identifier();

        queryExecution(conn, query);

        query = "update "+schema_name+"."+Constants.WAITING_LIST_TABLE_NAME+" " +
                "set "+schema_name+"."+Constants.WAITING_LIST_TABLE_NAME+".waiting_position" +
                " = waiting_position - 1" +
                " where waiting_position > "+wpDelUser;

        return queryExecution(conn, query);

    }

    private boolean deleteStatementUsers(User user, String tableName, String schemaName){
        String query = "delete from "+schemaName+"."+tableName+" where " +
                tableName+".email = '"+user.getEmail()+"'";

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
                libraryDescriptor.setId_lib(rs.getInt("id_lib"));
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


    public LibraryContent getLibraryContent(String schema_name, int id_lib) {
        ArrayList<Book> books = queryAllBooks(schema_name, id_lib);
        ArrayList<News> news = getAllNews(schema_name);
        ArrayList<Event> events = getAllEvents(schema_name, id_lib);

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

    private ResultSet queryUser(User user){

        ResultSet rs;
        String query;
        try {
            if ( user.getEmail() == null){
                query = "select * from "+Constants.PROPIETARY_DB+".users where "+Constants.PROPIETARY_DB+".users.user_id =" +
                        "?";
            }else
                query = "select * from "+Constants.PROPIETARY_DB+".users where "+Constants.PROPIETARY_DB+".users.email =" +
                    "?";
            PreparedStatement st = conn.prepareStatement(query);

            if ( user.getEmail() == null)
                st.setInt(1, user.getUser_id());
            else
                st.setString(1, user.getEmail());

            rs = st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            rs = null;
        }
        return rs;
    }

    public String passwordChangeAfterForgot(User user) {
        String res;
        try {
            ResultSet rs = queryUser(user);

            if ( rs.next()) {
                user.setUser_id(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setNotification_token(rs.getString("messaging_token"));
            }

            System.out.print("deleting old row...");
            Boolean bo = deleteStatementUsers(user, Constants.USERS_TABLE_NAME, Constants.PROPIETARY_DB);
            System.out.print("changing forgotten password..");
            res = pm.changeForgottenPassword(user);
        } catch (SQLException e) {
            e.printStackTrace();
            res = null;
        }
        return res;
    }

    public User addUser(User user) {

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
        if (insertStatement(map, tableName, Constants.PROPIETARY_DB)) {

            String sql = "SELECT user_id FROM propietary_db.users WHERE email = ?";

            PreparedStatement st = null;
            try {
                st = this.conn.prepareStatement(sql);

                st.setString(1, user.getEmail());
                ResultSet rs = st.executeQuery();

                if (rs.next())
                    user.setUser_id(rs.getInt("user_id"));

            } catch (SQLException e) {
                e.printStackTrace();
                user.setUser_id(-1);
            }
        }

        return user;
    }

    public User checkCorrectPassword(User user, String table_name, boolean librarian) {
        User to_ret = null;
        try {
            to_ret = pm.isExpectedPassword(user, table_name, librarian);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("QUERY ERROR!");
        }
        return to_ret;
    }

    public User getUser(User user) {
        String sql = "SELECT users.name, users.surname, users.email FROM propietary_db.users WHERE user_id = ?";

        PreparedStatement st = null;
        try {
            st = this.conn.prepareStatement(sql);
            st.setInt(1,user.getUser_id());
            ResultSet rs = st.executeQuery();


        if (rs.next()) {
            user.setEmail(rs.getString("email"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
        } else
            user.setUser_id(-1);

        } catch (SQLException e) {
            e.printStackTrace();
            user.setUser_id(-1);
        }
        return user;
    }

    public User getUserId(User user) {
        String sql = "SELECT * FROM propietary_db.users WHERE email = ?";

        PreparedStatement st = null;
        try {
            st = this.conn.prepareStatement(sql);
            st.setString(1,user.getEmail());
            ResultSet rs = st.executeQuery();


            if (rs.next()) {
                user.setNotification_token(rs.getString("messaging_token"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public ArrayList<Book> queryBookByIdentifier(String identifier, String schema_lib, int lib_id) {
        String query = "select * " +
                "from "+schema_lib+".books where identifier = '"+ identifier+"'";

        return getQueryResultsBooks(query, lib_id);
    }


    public ArrayList<Book> getAllUserRatedBooksForLib(Integer user_id, String schema_lib, int lib_id) {
        String query = "select book_identifier from "+schema_lib+".ratings where " +
                "user_id = "+user_id;

        ArrayList<String> book_identifiers = getIdRatedBooks(query);
        ArrayList<Book> result = new ArrayList<>();
        for (String identifier: book_identifiers) {
            query = "select * " +
                    "from " + schema_lib + ".books where identifier = '"+identifier+"'";

            result.addAll(getQueryResultsBooks(query, lib_id));
        }
        return result;
    }

    private ArrayList<String> getIdRatedBooks(String query) {
        ArrayList<String> results = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next())
                results.add(rs.getString("book_identifier"));

        } catch (SQLException e) {
            e.printStackTrace();
            results = null;
        }
        return results;
    }

    public Boolean insertNotificationToken(User user) {
        String query = "UPDATE "+Constants.PROPIETARY_DB+"."+Constants.USERS_TABLE_NAME+
                " SET messaging_token = '"+user.getNotification_token()+"' WHERE " +
                "user_id = "+ user.getUser_id() +";";

        return performStatement(query);
    }

    public Boolean reservedBookTaken(Reservation reservation) {
        String query = "UPDATE "+getSchemaNameLib(reservation.getIdLib())+"."+Constants.RESERVATIONS_TABLE_NAME+
                " SET taken = true WHERE " +
                "book_identifier = "+reservation.getBook_idetifier()+" and " +
                "user_id = "+reservation.getUser_id()+";";

        performStatement(query);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime start_date = LocalDateTime.now();
        String start_date_s = dtf.format(start_date);
        LocalDateTime end_date = start_date.plusMonths(1);
        String end_date_s = dtf.format(end_date);

        query = "UPDATE "+getSchemaNameLib(reservation.getIdLib())+"."+Constants.RESERVATIONS_TABLE_NAME+
                " SET starting_reservation_date = '"+start_date_s+
                "', ending_reservation_date = '"+end_date_s+
                "' WHERE " +
                "book_identifier = "+reservation.getBook_idetifier()+" and " +
                "user_id = "+reservation.getUser_id()+";";

        return performStatement(query);
    }

    private boolean performStatement(String query) {
        PreparedStatement pstmt;
        boolean res;
        try {
            pstmt = conn.prepareStatement(query);

            pstmt.executeUpdate();
            pstmt.close();
            res = true;
        } catch (SQLException e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    public ArrayList<Reservation> getAllReservationsForBook(Reservation res) {
        String query = "select * from "+getSchemaNameLib(res.getIdLib())+"."+Constants.RESERVATIONS_TABLE_NAME+
                " where book_identifier = '"+res.getBook_idetifier()+"'";

        return getQueryReservations(query, getSchemaNameLib(res.getIdLib()), res.getIdLib());
    }

    public String getNotificationToken(int user_id) {
        User user = new User();
        user.setUser_id(user_id);
        ResultSet rs = queryUser(user);
        String token = null;
        try {
            if ( rs.next())
                token = rs.getString("messaging_token");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return token;
    }

    public void sendNotification(String title, String mess, String token){
        // Declaration of Message Parameters
        String message_url = "https://fcm.googleapis.com/fcm/send";
        String message_sender_id = token;
        String message_key = "key="+Constants.SERVER_KEY;

        try {// Generating a JSONObject for the content of the message
            JSONObject message = new JSONObject();

            message.put("message", mess);
            message.put("title", title);

            JSONObject protocol = new JSONObject();
            protocol.put("to", message_sender_id);
            protocol.put("data", message);

            // Send Protocol

            HttpClient httpClient = HttpClientBuilder.create().build();

            HttpPost request = new HttpPost(message_url);
            request.addHeader("content-type", "application/json");
            request.addHeader("Authorization", message_key);

            StringEntity params = new StringEntity(protocol.toString());
            request.setEntity(params);
            System.out.println(params);

            HttpResponse response = httpClient.execute(request);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User registrationGoogleToken(User user) {

        user = silentGoogleLogin(user);
        if ( user.getUser_id() != -1)
            return user;

        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getName());
        map.put("surname", user.getSurname());
        map.put("email", user.getEmail());
        map.put("google_id_token", user.getGoogle_id_token());

        insertStatement(map,Constants.USERS_TABLE_NAME,Constants.PROPIETARY_DB);

//        String sql = "SELECT user_id FROM propietary_db.users WHERE email = ?";
//
//        PreparedStatement st = null;
//        try {
//            st = this.conn.prepareStatement(sql);
//
//            st.setString(1, user.getEmail());
//            ResultSet rs = st.executeQuery();
//            user.setUser_id(-1);
//
//            if (rs.next())
//                user.setUser_id(rs.getInt("user_id"));
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            user.setUser_id(-1);
//            return user;
//        }

        return user;
    }

    public User silentGoogleLogin(User user) {

        String sql = "SELECT * FROM propietary_db.users WHERE email = ?";

        PreparedStatement st = null;
        try {
            st = this.conn.prepareStatement(sql);

            st.setString(1, user.getEmail());
            ResultSet rs = st.executeQuery();
            user.setUser_id(-1);

            if (rs.next()){
                user.setUser_id(rs.getInt("user_id"));
                if ( rs.getString("google_id_token") != user.getGoogle_id_token()){
                    String query = "update propietary_db.users " +
                            "set google_id_token = ? where email = ?";

                    PreparedStatement preparedStmt = null;

                    preparedStmt = conn.prepareStatement(query);
                    preparedStmt.setString(1, user.getGoogle_id_token());
                    preparedStmt.setString(2, user.getEmail());
                    preparedStmt.executeUpdate();

                }

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean editProfile(UserPreferences up) {
        int lib_to_delete = up.getLibrary_to_delete();
        String library_to_edit = null;

        String query_is_user_in = "select library_1_id, library_2_id, library_3_id from "+Constants.PROPIETARY_DB+
                "."+Constants.PREFERENCE_TABLE_NAME+" where user_id = ?";

        try {
            PreparedStatement pst1 = conn.prepareStatement(query_is_user_in);
            pst1.setInt(1, up.getUser_id());
            ResultSet rs = pst1.executeQuery();
            if ( rs.next() ) {
                if ( rs.getInt("library_1_id") == lib_to_delete )
                    library_to_edit = "library_1_id";
                else if ( rs.getInt("library_2_id") == lib_to_delete )
                    library_to_edit = "library_2_id";
                else if ( rs.getInt("library_3_id") == lib_to_delete )
                    library_to_edit = "library_3_id";
                else
                    return false;
            }

            String query = "update propietary_db.users_preferences " +
                    "set " + library_to_edit + " = ? where user_id = ?";
            PreparedStatement preparedStmt = null;

            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setObject(1, null);
            preparedStmt.setInt(2, up.getUser_id());

            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean getLibraryForAbook(String schemaNameLib, String identifier) {
        String query = "select * from "+schemaNameLib+".books where " +
                "identifier = '"+identifier+"'";

        Statement st = null;
        if (checkOneResult(query)) return true;
        return false;
    }
}

