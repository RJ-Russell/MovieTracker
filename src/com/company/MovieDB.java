package com.company;

import java.sql.*;

/**
 * @author RJ Russell
 */

class MovieDB {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL =
            "jdbc:h2:~/Projects/movieTracker/movies";
    private static final String DB_USERNAME = "movies";
    private static final String DB_PASSWORD = "movies";
    private Connection conn;
    private Statement stmt;

    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS `movies`"
            + "(`imdb_id` VARCHAR(255) NOT NULL PRIMARY KEY, "
            + "`title` VARCHAR(255), "
            + "`year` VARCHAR(255), "
            + "`content_rating` VARCHAR(255), "
            + "`genres` VARCHAR(255), "
            + "`actors` VARCHAR(255), "
            + "`rating` VARCHAR(255), "
            + "`runtime` VARCHAR(255), "
            + "`plot` VARCHAR(255))";

    MovieDB() throws SQLException, ClassNotFoundException {
        connectDatabase();
    }

    private void connectDatabase() throws SQLException, ClassNotFoundException {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(
                    DB_URL,
                    DB_USERNAME,DB_PASSWORD);
            stmt = conn.createStatement();
            stmt.execute(CREATE_TABLE);
            System.out.println("Table made successfully");
    }

    void insertMovie(String imdbId, String title, String year,
                    String contentRating, String genre, String actors,
                    String rating, String runtime, String plot) throws SQLException {

        if(!title.isEmpty()) {
            title = title.replaceAll("'", "''");
        }

        final String insertStmt = "INSERT INTO `movies` " +
                             " VALUES('" + imdbId + "','" + title + "','" + year +
                             "','" + contentRating + "','" + genre + "','" + actors +
                             "','" + rating + "','" + runtime + "','" + plot + "');";

        stmt = conn.createStatement();
        stmt.executeUpdate(insertStmt);
        stmt.close();
    }
}
