package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        OmdbScraper omdbScraper = new OmdbScraper();
        omdbScraper.getStuff("snatch.");
     //   TrackerGui tracker = new TrackerGui();
     //   tracker.startTracker();

    }
}
