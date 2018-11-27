package com.easylib.server;

import com.easylib.server.Database.DatabaseConnection;
import com.easylib.server.Database.DatabaseManager;
import com.easylib.server.Database.GoogleBooks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

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
//        DatabaseConnection db = new DatabaseConnection();
//        Connection conn = db.startConnection();

        String query = "J.K. Rowling";
        DatabaseManager dbms = new DatabaseManager();
        ArrayList<Object> res = dbms.queryBooksByTitle(query);
        printQueryResult(res);

        //GoogleBooks gb = new GoogleBooks();
        //gb.apiFillDb(query.replaceAll("\\s", "+"));

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
