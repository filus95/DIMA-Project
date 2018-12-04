package com.easylib.server.Database;

import com.easylib.network.socket.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {

    private Connection conn = new DatabaseConnection().startConnection();

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
    private String createInsertStatement(Map<String, Object> map, String tableName){
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

        return  "INSERT INTO "+tableName+" ("+columns_name+")" + "VALUES ("+values+")";
    }

    /**
     * Construct the map for the bookreservation insertion and call the general insertStatement method that perform
     * the actual query
     *
     * @param values array of values to insert in the DB
     * @return
     */
    public boolean insertNewReservation(ArrayList<Object> values){

        Map<String, Object> map = new HashMap<>();
        ArrayList<String> columnsName = new ArrayList<>();
        columnsName.add("user_id");
        columnsName.add("book_identifier");
        columnsName.add("book_title");
        columnsName.add("reservation_date");
        columnsName.add("starting_reservation_date");
        columnsName.add("ending_reservation_date");
        columnsName.add("quantity");

        int count = 0;
        for( String key: columnsName) {
            map.put(key, values.get(count));
            count++;
        }

        String table_name = " library_1.booksreservations ";
        return insertStatement(map, table_name);
    }

     public boolean insertStatement(Map<String, Object> map, String table_name) {
        try {

            String query = createInsertStatement(map, table_name);
            PreparedStatement pstmt = conn.prepareStatement(query);

            int count = 1;
            Object value;
            for (String key : map.keySet()) {
                value = map.get(key);
                pstmt.setObject(count, value);
                count++;
            }

            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Retrieve books by author
     *
     * @param author
     * @return it must be casted to the book data type in the calling function
     */
    public ArrayList<Object> queryBooksByAuthor(String author) {
        String query = "select identifier, title, publisher, category_1, author_1, author_2, author_3, author_4 " +
                "from library_1.books where author_1 = '"+author+"'" + "or author_2='"+author+"'"
                +"or author_3='"+author+"'or author_4='"+author+"'";

        return getQueryResults(query);
    }

    /**
     * Retrieve books by title
     *
     * @param title
     * @return
     */
    public ArrayList<Object> queryBooksByTitle(String title) {
        String query = "select identifier, title, publisher, category_1, author_1, author_2, author_3, author_4 " +
                "from library_1.books where title = '"+title+"'";

        return getQueryResults(query);
    }

    /**
     * Retrieve books by category
     *
     * @param category
     * @return
     */
    public ArrayList<Object> queryBooksByCategory(String category) {
        String query = "select identifier, title, publisher, category_1, category_2, category_3"+
        "from books where category_1 ='"+category+"' or category_2='"+category+"' or category_3='"+category+"'";

        return getQueryResults(query);
    }

    /**
     * Perform a query on the DB
     *
     * @param query
     * @return an array of objects and who receives the result knows the real datatype and cast its content
     */
    private ArrayList<Object> getQueryResults(String query) {
        ArrayList<Object> results = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                Book queryResult = new Book();
                queryResult.setBookId_lib((String) rs.getObject("identifier"));
                queryResult.setTitle((String) rs.getObject("title"));
                queryResult.setPublisher((String) rs.getObject("publisher"));
                queryResult.setCategories((String)rs.getObject("category_1"),
                        (String)rs.getObject("category_2"),
                        (String)rs.getObject("category_3"));

                queryResult.setAuthors((String)(rs.getObject("author_1")),
                        (String)(rs.getObject("author_2")),
                        (String)(rs.getObject("author_3")),
                        (String)(rs.getObject("author_4")));
                results.add(queryResult);
            }
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return results;
    }

    public boolean deleteStatementReservations( String book_identifier, String user_id,
                                                String tableName ){
        String query = "delete from "+tableName+" where " +
                tableName+".book_identifier = "+book_identifier+" and " +
                tableName+".user_id = "+user_id;

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
}

