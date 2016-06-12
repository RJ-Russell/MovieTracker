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

        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(121,97,50));
        panel.setPreferredSize(new Dimension(400,400));
        panel.setVisible(true);

        JButton searchBut = new JButton("Search Movies");
        JButton  addBut =  new JButton("Add Movie");
        JButton  remBut = new JButton("Remove Movie");
        JButton exitBut = new JButton("Exit");

        exitBut.addActionListener(this);


        getContentPane().add(panel);
        panel.add(searchBut);
        panel.add(addBut);
        panel.add(remBut);
        panel.add(exitBut);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Exiting...");
        System.exit(1);
    }
}
