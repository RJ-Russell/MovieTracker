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
    private JPanel[] panels = new JPanel[5];
    private JPanel addPanel;
    private JPanel remPanel;
    TrackerGui() {
        super("Movie Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 0, 1000, 400);

        mainFrame = getContentPane();
        mainFrame.setLayout(new BorderLayout());

        panels[0] = new JPanel(new GridLayout(5,1));

        menuPanelShow();
        defaultPanelShow();
        setVisible(true);
    }

    private void menuPanelShow() {
        panels[0].setPreferredSize(new Dimension(200,400));

        JButton homeBut = new JButton("Home");
        homeBut.setBackground(background);
        homeBut.setForeground(foreground);
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

        panels[0].add(homeBut);
        panels[0].add(searchBut);
        panels[0].add(addBut);
        panels[0].add(remBut);
        panels[0].add(exitBut);

        mainFrame.add(panels[0], BorderLayout.WEST);
        homeBut.addActionListener(e -> {
            readyPanelsForSwitching();
            defaultPanelShow();
        });

        searchBut.addActionListener(e -> {
            readyPanelsForSwitching();
            searchPanelShow();
        });

        exitBut.addActionListener(actionEvent -> System.exit(0));
    }

    private void defaultPanelShow() {
        panels[1] = new JPanel();
        panels[1].setPreferredSize(new Dimension(800,400));
        panels[1].setBackground(background);

        mainFrame.add(panels[1], BorderLayout.EAST);
    }

    private void searchPanelShow() {
        panels[2] = new JPanel();
        panels[2].setBackground(background);
        panels[2].setPreferredSize(new Dimension(800,400));

        JTextArea title = new JTextArea("Title: \t");
        JTextField titleField = new JTextField(30);

        panels[2].add(title);
        panels[2].add(titleField);

        mainFrame.add(panels[2], BorderLayout.EAST);
    }

    private void readyPanelsForSwitching() {
        for(int i = 1; i < 3; ++i) {
            if(panels[i] != null && panels[i].isDisplayable()) {
                System.out.print("I: " + i);
                System.out.println(panels[i].isDisplayable());
                mainFrame.remove(panels[i]);
            }
        }
        mainFrame.repaint();
        mainFrame.revalidate();
    }

}
