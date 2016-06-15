package com.company;

import java.sql.*;

/**
 * Created by chupacabra on 6/15/16.
 */
public class movieDB {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/movieDB";
    private static final String DB_USERNAME = "movies";
    private static final String DB_PASSWORD = "movies";

    
    movieDB() {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(
                    DB_URL,
                    DB_USERNAME,DB_PASSWORD);
            Statement stmt = conn.createStatement();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
