package com.easylib.server;

import com.easylib.server.Database.DatabaseConnection;
import com.easylib.server.Database.DatabaseManager;
import com.easylib.server.Database.GoogleBooks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        /**
            Connection conn = new DatabaseConnection().startConnection();
            String sql = "DELETE FROM library_1.books";

            try {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
         **/
        DatabaseConnection db = new DatabaseConnection();
        Connection conn = db.startConnection();

        String query = "Pirlo";
        DatabaseManager dbms = new DatabaseManager();
//        ArrayList<Object> res = dbms.queryBooksByTitle(query);
//        printQueryResult(res);


//        GoogleBooks gb = new GoogleBooks();
//        gb.apiFillDb(query.replaceAll("\\s", "+"));

        // TODO: ADD INTO booksreservations
//        java.util.Date dt = new java.util.Date();
//        java.text.SimpleDateFormat reservation_date =
//                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date start_res = stringToDate("2018-12-3");
//
//        Date end_res = stringToDate("2018-12-30");
//        String currentTime = reservation_date.format(dt);
//
//
//        ArrayList<Object> values = new ArrayList<>();
//        values.add(14);
//        values.add("1909430188");
//        values.add("Andrea Pirlo: I Think Therefore I Play");
//        values.add(currentTime);
//        values.add(start_res);
//        values.add(end_res);
//        values.add(1);
//        boolean res = dbms.insertNewReservation(values);
//        System.out.print(res);

        // TODO: DELETE FROM booksreservations

        boolean res = dbms.deleteStatementReservations("1909430188", "15",
                "library_1.booksreservations");
        System.out.print(res);
    }

    private static Date stringToDate(String s) {

        Date result = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            result = dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void printQueryResult(ArrayList<Object> res){
        if (res.size()==0)
            System.out.print("Sorry, no book responding to this title found.");
        for (Object elem: res){
            for (Object to_ret: (ArrayList<Object>)elem){
                if ( to_ret == null)
                    continue;
                System.out.print(to_ret+" ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Correct the query format to make it suitable for an Google API call
     *
     * @param query string to correct
     * @return corrected string
     */
    private String correctQueryString(String query){

        return query.replaceAll("\\s", "+");
    }
}

