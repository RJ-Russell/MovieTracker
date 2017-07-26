package com.company;

import javax.swing.*;

/**
 * @author RJ Russell
 */

public class Main {
    public static void main(String[] args) {
        Runnable app = () -> {
            TrackerGui tracker = new TrackerGui();
            tracker.startTracker();
        };

        SwingUtilities.invokeLater(app);
    }
}
