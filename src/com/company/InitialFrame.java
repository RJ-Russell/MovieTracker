package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by chupacabra on 6/2/16.
 */
class InitialFrame extends JFrame {
    private Container mainFrame;
    InitialFrame() {
        super("Movie Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 0, 500, 400);
        mainFrame = getContentPane();
        mainFrame.setLayout(new CardLayout());
        setVisible(true);

        initPanelShow();
    }

    private void initPanelShow() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(121,97,50));
        mainPanel.setPreferredSize(new Dimension(500,400));

        JButton searchBut = new JButton("Search Movies");
        JButton  addBut =  new JButton("Add Movie");
        JButton  remBut = new JButton("Remove Movie");
        JButton exitBut = new JButton("Exit");
        mainPanel.add(searchBut);
        mainPanel.add(addBut);
        mainPanel.add(remBut);
        mainPanel.add(exitBut);

        mainFrame.add(mainPanel);

        searchBut.addActionListener(e -> {
            mainFrame.removeAll();
            mainFrame.repaint();
            mainFrame.revalidate();
            searchPanelShow();
        });
        exitBut.addActionListener(actionEvent -> System.exit(0));

    }

    private void searchPanelShow() {
        getContentPane().removeAll();
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(0,0,0));
        searchPanel.setPreferredSize(new Dimension(500,400));
        searchPanel.setVisible(true);
        getContentPane().repaint();

        JTextArea title = new JTextArea("Title: ");
        JTextField titleField = new JTextField();

        JButton goHomeBut = new JButton("Home");

        searchPanel.add(title);
        searchPanel.add(titleField);
        searchPanel.add(goHomeBut);

        mainFrame.add(searchPanel);

        goHomeBut.addActionListener(e -> {
            searchPanel.setVisible(false);
            initPanelShow();
        });
    }

}
