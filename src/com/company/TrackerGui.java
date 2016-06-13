package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by chupacabra on 6/2/16.
 */
class TrackerGui extends JFrame {
    private final Color background = new Color(121,97,50);
    private final Color foreground = Color.WHITE;

    private Container mainFrame;
    private JPanel mainPanel;
    private JPanel defaultPanel;
    private JPanel addPanel;
    private JPanel searchPanel;
    private JPanel remPanel;
    TrackerGui() {
        super("Movie Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 0, 1000, 400);

        mainFrame = getContentPane();
        mainFrame.setLayout(new BorderLayout());

        mainPanel = new JPanel(new GridLayout(4,1));

        menuPanelShow();
        showDefaultPanel();
        setVisible(true);
    }

    private void menuPanelShow() {
        mainPanel.setPreferredSize(new Dimension(200,400));

        JButton searchBut = new JButton("Search Movies");
        searchBut.setBackground(background);
        searchBut.setForeground(foreground);
        JButton addBut =  new JButton("Add Movie");
        addBut.setBackground(background);
        addBut.setForeground(foreground);
        JButton remBut = new JButton("Remove Movie");
        remBut.setBackground(background);
        remBut.setForeground(foreground);
        JButton exitBut = new JButton("Exit");
        exitBut.setBackground(background);
        exitBut.setForeground(foreground);

        mainPanel.add(searchBut);
        mainPanel.add(addBut);
        mainPanel.add(remBut);
        mainPanel.add(exitBut);

        mainFrame.add(mainPanel, BorderLayout.WEST);

        searchBut.addActionListener(e -> {
            mainFrame.remove(defaultPanel);
            mainFrame.repaint();
            mainFrame.revalidate();
            searchPanelShow();
        });
        exitBut.addActionListener(actionEvent -> System.exit(0));
    }

    private void showDefaultPanel() {
        defaultPanel = new JPanel();
        defaultPanel.setPreferredSize(new Dimension(800,400));
        defaultPanel.setBackground(background);

        mainFrame.add(defaultPanel, BorderLayout.EAST);
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
        });
    }

}
