package com.company;

import java.io.IOException;

/**
 * @author RJ Russell
 */

public class Main {
    public static void main(String[] args) throws IOException {
//        TrackerGui tracker = new TrackerGui();
//        tracker.startTracker();
        ImdbApi md = new ImdbApi();
        md.getMovieData("", "", "");
    }
}
