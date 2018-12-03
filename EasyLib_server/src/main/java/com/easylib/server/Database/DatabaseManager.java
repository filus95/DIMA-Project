package com.easylib.server.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {

    private Connection conn = new DatabaseConnection().startConnection();

    // todo this can be generalized for any insertion: look down
    void fillBookDb(Map<String, Object> map) {

        try {
            StringBuilder columns_name = new StringBuilder();
            StringBuilder values = new StringBuilder();
            int lenght = map.size();
            int count = 0;
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

//            //todo delete
//            columns_name.append(", ").append("quantity");
//            values.append(", ?");

            // Query statement construction
            String query = "INSERT INTO library_1.books("+columns_name+")" + "VALUES ("+values+")";
            PreparedStatement pstmt = conn.prepareStatement(query);

            count = 1;
            Object value;
            for (String key: map.keySet()) {
                value = map.get(key);
                pstmt.setObject(count, value);
                count++;
            }

//            //todo delete
//            pstmt.setObject(count, count);


            pstmt.executeUpdate();
            pstmt.close();

            //Add e.printStacktrace to debug
        } catch (SQLException e) {
           System.out.print("Book already present. Not inserted.\n");
           //e.printStackTrace();
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    private boolean insertStatement(Map<String, Object> map, String table_name) {
        try {
            StringBuilder columns_name = new StringBuilder();
            StringBuilder values = new StringBuilder();
            int lenght = map.size();
            int count = 0;

            for (String key : map.keySet()) {
                if ((count != lenght - 1)) {
                    columns_name.append(key).append(", ");
                    values.append("?, ");
                } else {
                    columns_name.append(key);
                    values.append("?");
                }
                count++;
            }

            // Query statement construction
            String query = "INSERT INTO"+table_name+"(" + columns_name + ")" + "VALUES (" + values + ")";
            PreparedStatement pstmt = conn.prepareStatement(query);

            count = 1;
            Object value;
            for (String key : map.keySet()) {
                value = map.get(key);
                pstmt.setObject(count, value);
                count++;
            }

            pstmt.executeUpdate();
            pstmt.close();

            //Add e.printStacktrace to debug
        } catch (SQLException e) {
//            System.out.print("Book already present. Not inserted.\n");
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


    /**
     * Retrieve books by author
     *
     * @param author
     * @return
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

    private ArrayList<Object> getQueryResults(String query) {
        ArrayList<Object> queryResult = new ArrayList<Object>();
        ArrayList<Object> results = new ArrayList<Object>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                queryResult.add(rs.getObject("identifier"));
                queryResult.add(rs.getObject("title"));
                queryResult.add(rs.getObject("publisher"));
                queryResult.add(rs.getObject("category_1"));
                queryResult.add(rs.getObject("author_1"));
                queryResult.add(rs.getObject("author_2"));
                queryResult.add(rs.getObject("author_3"));
                queryResult.add(rs.getObject("author_4"));
                results.add(queryResult);
            }
        } catch (SQLException e) {
            String errorAnswer = "No results founds";
//          e.printStackTrace();
        }
        return results;
    }


}

