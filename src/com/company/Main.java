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
    }
}
