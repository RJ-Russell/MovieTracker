package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by chupacabra on 6/2/16.
 */
class TrackerGui extends JFrame {
    private final Color background = new Color(121,97,50);
    private final Color foreground = Color.WHITE;
    private final JPanel[] panels = new JPanel[5];

    private Container mainFrame;
    private JPanel addPanel;
    private JPanel remPanel;
    TrackerGui() {
        super("Movie Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(500, 0);

        mainFrame = getContentPane();
        mainFrame.setLayout(new BorderLayout());

        panels[0] = new JPanel(new GridLayout(5,1));

        menuPanelShow();
     //   defaultPanelShow();
        searchPanelShow();
        pack();
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
        String[] labels = {"Title", "Year", "Genre"};
        JPanel labelPanel = new JPanel(new GridLayout(labels.length, 1));
        labelPanel.setBackground(background);
        JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
        fieldPanel.setBackground(background);
        JTextField[] fields = new JTextField[labels.length];

        panels[2] = new JPanel(new BorderLayout());
        panels[2].setBackground(background);
        panels[2].setPreferredSize(new Dimension(800,400));
        panels[2].add(labelPanel, BorderLayout.WEST);
        panels[2].add(fieldPanel, BorderLayout.CENTER);

        for(int i = 0; i < labels.length; ++i) {
            fields[i] = new JTextField();
            fields[i].setToolTipText(labels[i]);
            fields[i].setColumns(50);

            JLabel label = new JLabel(labels[i] + ": ", JLabel.RIGHT);
            label.setLabelFor(fields[i]);

            labelPanel.add(label);
            JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 50));
            p.setBackground(background);
            p.add(fields[i]);
            fieldPanel.add(p);
        }


        JButton goSearch = new JButton("SEARCH");

        panels[2].add(goSearch, BorderLayout.SOUTH);

        mainFrame.add(panels[2], BorderLayout.EAST);
    }

    private void readyPanelsForSwitching() {
        for(int i = 1; i < 3; ++i) {
            if(panels[i] != null && panels[i].isDisplayable()) {
                System.out.print("I: " + i + " ");
                System.out.println(panels[i].isDisplayable());
                mainFrame.remove(panels[i]);
            }
        }
        mainFrame.repaint();
        mainFrame.revalidate();
    }

}
