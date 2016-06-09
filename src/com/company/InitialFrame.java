package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by chupacabra on 6/2/16.
 */
public class InitialFrame extends JFrame implements ActionListener {

    InitialFrame() {
        super("Movie Tracker");
        setBounds(500, 0, 1000, 800);
        getContentPane().setBackground(new Color(121,97,50));

    }

    public void actionPerformed(ActionEvent e) {
        new UnsupportedOperationException("Not implemented");
    }
}
