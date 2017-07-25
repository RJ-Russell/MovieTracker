package com.company;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author RJ Russell
 */

public class Main {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        TrackerGui tracker = new TrackerGui();
        tracker.startTracker();
//        final String[] labels = {"_ID", "IMDB ID", "Title", "Year",
//                "Content Rating", "Genre", "Actors", "Rating", "Runtime\n (min)",
//                "Plot"};
//        MovieDB db = new MovieDB();
//        MovieData[] md = db.searchAll();
//
//        MovieTable mt = new MovieTable(md, labels);
    }
}
