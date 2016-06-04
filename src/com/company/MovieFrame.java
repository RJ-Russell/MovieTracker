package com.company;

import javax.swing.*;

/**
 * Created by chupacabra on 6/2/16.
 */
public class MovieFrame extends JFrame {
    MovieFrame() {
        setTitle("Movie Tracker");
        setSize(400, 800);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public class createLayoutClass {
        createLayoutClass() {
            new JRadioButton("This thing");
        }
    }
}
