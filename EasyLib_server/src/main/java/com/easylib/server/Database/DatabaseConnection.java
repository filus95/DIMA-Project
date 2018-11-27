package com.easylib.server.Database;

import java.sql.*;

public class DatabaseConnection {

    public Connection startConnection(){
        // Step 1: "Load" the JDBC driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Step 2: Establish the connection to the Database
        String url = "jdbc:mysql://127.0.0.1:3306/?user=root";


        try {
            return DriverManager.getConnection(url,"root","texaspoker");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}