package com.company;

import java.sql.*;

/**
 * Created by chupacabra on 6/15/16.
 */
public class MovieDB {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL =
            "jdbc:h2:~/Projects/Java/movieTracker/movieDB";
    private static final String DB_USERNAME = "movies";
    private static final String DB_PASSWORD = "movies";
    private Connection conn;
    private Statement stmt;

    private static final String CREATE_TABLE = "CREATE TABLE `movies`"
            + "(`imdb_id` VARCHAR(255) NOT NULL PRIMARY KEY, "
            + "`title` VARCHAR(255), "
            + "`year` INTEGER(4), "
            + "`genres` VARCHAR(255), "
            + "`actors` VARCHAR(255), "
            + "`rating` VARCHAR(255), "
            + "`runtime` VARCHAR(255), "
            + "`plot` VARCHAR(255))";

    MovieDB() {
        connectDatabase();
    }
    private void connectDatabase() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(
                    DB_URL,
                    DB_USERNAME,DB_PASSWORD);
            stmt = conn.createStatement();
            stmt.execute(CREATE_TABLE);
            System.out.println("Table made successfully");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
