package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author RJ Russell
 */

public class Main {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        TrackerGui tracker = new TrackerGui();
        tracker.startTracker();

//        String fonts[]
//                = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//
//        for (int i = 0; i < fonts.length; i++) {
//            System.out.println(fonts[i]);
//        }
    }
}
