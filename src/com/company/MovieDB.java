package com.company;

import java.sql.*;
import java.util.Arrays;

import static com.company.MovieDBConnection.DB_PASSWORD;
import static com.company.MovieDBConnection.DB_URL;
import static com.company.MovieDBConnection.DB_USERNAME;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;

/**
 * @author RJ Russell
 */

class MovieDB {
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;

    /**
     * Default constructor. Connects to the database on creation of MovieDB object.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    MovieDB() throws SQLException, ClassNotFoundException {
        connectDatabase();
    }

    /**
     * Establishes a connection to the database.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void connectDatabase() throws SQLException, ClassNotFoundException {
        final String JDBC_DRIVER = "org.h2.Driver";
        final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS `movies`"
                        + "(_id BIGINT NOT NULL AUTO_INCREMENT, "
                        + "`imdb_id` VARCHAR(255) UNIQUE, "
                        + "`title` VARCHAR(255), "
                        + "`year` VARCHAR(255), "
                        + "`content_rating` VARCHAR(255), "
                        + "`genres` VARCHAR(255), "
                        + "`actors` VARCHAR(255), "
                        + "`rating` VARCHAR(255), "
                        + "`runtime` VARCHAR(255), "
                        + "`plot` VARCHAR(255))";

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(
                DB_URL,
                DB_USERNAME,DB_PASSWORD);
        stmt = conn.createStatement();
        stmt.execute(CREATE_TABLE);
        System.out.println("Table created successfully\n\n");
    }

    /**
     * Inserts the data for a single movie into the database.
     * @param imdbId: The id that IMDB assigns the movie.
     * @param title: Title of the movie.
     * @param year: Year movie was released.
     * @param contentRating: Content rating for the movie (e.g. PG-13, R).
     * @param genre: Movie genre(s).
     * @param actors: Top billed actors for the movie.
     * @param rating: Rating given to movie by IMDB.
     * @param runtime: Total runtime in minutes.
     * @param plot: Short plot description for the movie.
     * @throws SQLException
     */
    void insertMovie(String imdbId, String title, String year,
                     String contentRating, String genre, String actors,
                     String rating, String runtime, String plot) throws SQLException {

        final String insertStmt = "INSERT INTO `movies`(`_id`,`imdb_id`," +
                "`title`,`year`,`content_rating`,`genres`,`actors`,`rating`," +
                "`runtime`,`plot`) VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        pstmt = conn.prepareStatement(insertStmt);
        pstmt.setString(1, imdbId);
        pstmt.setString(2, title);
        pstmt.setString(3, year);
        pstmt.setString(4, contentRating);
        pstmt.setString(5, genre);
        pstmt.setString(6, actors);
        pstmt.setString(7, rating);
        pstmt.setString(8, runtime);
        pstmt.setString(9, plot);

        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Removes a movie from database matching the given id.
     * @param id: The id for the movie created on insertion of movie.
     * @throws SQLException
     */
    void removeMovie(String id) throws SQLException {
        String removeStmt = "DELETE FROM `movies` WHERE _ID = ?;";
        pstmt = conn.prepareStatement(removeStmt);
        pstmt.setString(1, id);
        pstmt.executeUpdate();
        pstmt.close();
    }

    MovieData[] searchAll() throws SQLException {
        String query = "SELECT * FROM movies;";
        return searchQuery(query);
    }

    /**
     * Gathers the data for each movie entry in the database.
     * @return Array of MovieData objects.
     * @throws SQLException
     */
    private MovieData[] searchQuery(String query) throws SQLException {
        stmt = conn.createStatement(TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(query);

        int rows = 0;
        if(rs.last()) {
            rows = rs.getRow();
        }

        if(rows == 0) {
            return null;
        }

        System.out.println("ROWS: " + rows);

        MovieData[] movieResults = new MovieData[rows];

        rs.beforeFirst();
        int i = 0;
        while(rs.next()) {
            String id = rs.getString("_ID");
            String imdbId = rs.getString("IMDB_ID");
            String title = rs.getString("TITLE");
            String year = rs.getString("YEAR");
            String contentRating = rs.getString("CONTENT_RATING");

            String[] genre = rs.getString("GENRES").split(",");
            Arrays.stream(genre).map(String::trim).toArray(s -> genre);

            String[] stars = rs.getString("ACTORS").split(",");
            Arrays.stream(stars).map(String::trim).toArray(s -> stars);

            String rating = rs.getString("RATING");
            String runtime = rs.getString("RUNTIME");
            String plot = rs.getString("PLOT");

            movieResults[i] = new MovieData(id, imdbId, title, year,
                    contentRating, genre, stars, rating, runtime, plot);
            ++i;
        }
        return movieResults;
    }

}
