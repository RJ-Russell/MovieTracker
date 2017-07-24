package com.company;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author RJ Russell
 */

public class Main {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
//        TrackerGui tracker = new TrackerGui();
//        tracker.startTracker();

        MovieDB db = new MovieDB();
//        db.removeMovie("6");
        MovieData[] md = db.getResults();
        if(md == null) {
            System.out.println("No movies in database!");
        } else {
            for (MovieData d : md) {
                System.out.println(d);
            }
        }

    }
}
