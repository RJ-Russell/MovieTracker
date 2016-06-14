package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        omdbScraper omdbScraper = new omdbScraper();
        omdbScraper.getStuff("Green Mile");
       /* TrackerGui tracker = new TrackerGui();
        tracker.startTracker();
       */
    }
}
