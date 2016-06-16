package com.company;

import org.h2.mvstore.MVMap;
import org.omg.CORBA.OMGVMCID;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

/**
 * Created by chupacabra on 6/2/16.
 */
class TrackerGui {
 //   MovieDB db = new MovieDB();
    private final OmdbScraper omdb = new OmdbScraper();

    private final Color background = new Color(121,97,50);
    private final Color foreground = Color.WHITE;
    private final JPanel[] panels = new JPanel[5];
    private final String[] labels =
        {"Title", "Year", "Genre", "Actors", "Rated", "Runtime", "Plot"};

    private JFrame mainFrame;
    private Container mainContainer;

    void startTracker() {
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setBackground(background);
        mainFrame.setLocation(500, 0);
        mainFrame.setSize(500,300);

        mainContainer = mainFrame.getContentPane();
        mainContainer.setLayout(new BorderLayout());

        panels[0] = new JPanel(new GridLayout(5,1));

        menuPanelShow();
        defaultPanelShow();
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
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

        mainContainer.add(panels[0], BorderLayout.WEST);
        homeBut.addActionListener(e -> {
            readyPanelsForSwitching();
            defaultPanelShow();
        });

        searchBut.addActionListener(e -> {
            readyPanelsForSwitching();
            searchPanelShow();
        });

        addBut.addActionListener(e -> {
            readyPanelsForSwitching();
            addPanelShow();
        });

        remBut.addActionListener(e -> {
            readyPanelsForSwitching();
            removePanelShow();
        });

        exitBut.addActionListener(actionEvent -> System.exit(0));
    }

    private void defaultPanelShow() {
        mainFrame.setTitle("Movie Tracker");
        panels[1] = new JPanel();
        panels[1].setPreferredSize(new Dimension(800,400));
        panels[1].setBackground(background);

        mainContainer.add(panels[1], BorderLayout.EAST);
    }

    private void searchPanelShow() {
        mainFrame.setTitle("Movie Tracker: Search Movies");

        JPanel labelPanel = new JPanel(new GridLayout(labels.length, 1));
        labelPanel.setPreferredSize(new Dimension(100, 0));
        labelPanel.setBackground(background);
        JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
        fieldPanel.setBackground(background);
        JTextField[] fields = new JTextField[labels.length];

        panels[2] = new JPanel(new BorderLayout());
        panels[2].setBackground(background);
        panels[2].setPreferredSize(new Dimension(800,400));
        panels[2].add(labelPanel, BorderLayout.WEST);
        panels[2].add(fieldPanel, BorderLayout.CENTER);
        addPanelContent(labelPanel, fieldPanel, labels, fields, null);

        JButton goSearch = new JButton("SEARCH MOVIES");

        panels[2].add(goSearch, BorderLayout.SOUTH);
        mainContainer.add(panels[2], BorderLayout.EAST);

        goSearch.addActionListener(doSearchThing ->
                JOptionPane.showMessageDialog(
                        mainContainer,
                "Database Not Implemented Yet\n",
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        ));
    }

    private void addPanelShow() {
        mainFrame.setTitle("Movie Tracker: Add Movie");

        JPanel labelPanel = new JPanel(new GridLayout(labels.length, 1));
        labelPanel.setPreferredSize(new Dimension(100, 0));
        labelPanel.setBackground(background);
        JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
        fieldPanel.setBackground(background);
        JTextField[] fields = new JTextField[labels.length - 1];

        JTextArea plot = new JTextArea();
        plot.setToolTipText(labels[labels.length - 1]);
        plot.setColumns(50);
        plot.setLineWrap(true);


        panels[3] = new JPanel(new BorderLayout());
        panels[3].setBackground(background);
        panels[3].setPreferredSize(new Dimension(800,400));
        panels[3].add(labelPanel, BorderLayout.WEST);
        panels[3].add(fieldPanel, BorderLayout.CENTER);
        addPanelContent(labelPanel, fieldPanel, labels, fields, plot);

        JButton addBut = new JButton("ADD MOVIE");

        panels[3].add(addBut, BorderLayout.SOUTH);
        mainContainer.add(panels[3], BorderLayout.EAST);
        mainFrame.getRootPane().setDefaultButton(addBut);

        addBut.addActionListener(doAddThing -> {
            Map<String, String> movie = null;
            String title = fields[0].getText();
            if(title.equals("")) {
                JOptionPane.showMessageDialog(
                    mainContainer, "Error: Title field cannot be empty!",
                    "Error", JOptionPane.ERROR_MESSAGE
                );
            } else {
                try {
                    movie = omdb.getStuff(title);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(movie != null) {
                    fields[0].setText(movie.get("Title"));
                    fields[1].setText(movie.get("Year"));
                    fields[2].setText(movie.get("Genre"));
                    fields[3].setText(movie.get("Actors"));
                    fields[4].setText(movie.get("Rated"));
                    fields[5].setText(movie.get("Runtime"));
                 //   fields[6].setText(movie.get("Plot"));
                    plot.setText(movie.get("Plot"));
                }
            }
        });
    }

    private void removePanelShow() {
        mainFrame.setTitle("Movie Tracker: Remove Movie");

        JPanel labelPanel = new JPanel(new GridLayout(labels.length, 1));
        labelPanel.setPreferredSize(new Dimension(100, 0));
        labelPanel.setBackground(background);
        JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
        fieldPanel.setBackground(background);
        JTextField remField = new JTextField();

        panels[4] = new JPanel(new BorderLayout());
        panels[4].setBackground(background);
        panels[4].setPreferredSize(new Dimension(800,400));
        panels[4].add(labelPanel, BorderLayout.WEST);
        panels[4].add(fieldPanel, BorderLayout.CENTER);

        remField.setToolTipText(labels[0]);
        remField.setColumns(50);
        JLabel remLabel = new JLabel(labels[0] + ": ", JLabel.RIGHT);
        remLabel.setForeground(foreground);
        remLabel.setLabelFor(remField);
        labelPanel.add(remLabel);
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 85));
        p.setBackground(background);
        p.add(remField);
        panels[4].add(p);

        JButton remBut = new JButton("REMOVE MOVIE");

        panels[4].add(remBut, BorderLayout.SOUTH);
        mainContainer.add(panels[4], BorderLayout.EAST);

        remBut.addActionListener(doRemoveThing ->
                JOptionPane.showMessageDialog(
                        mainContainer,
                        "Database Not Implemented Yet\n",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                ));
    }

    private void addPanelContent(JPanel labelPanel,
                                 JPanel fieldPanel,
                                 String[] labels,
                                 JTextField[] fields,
                                 JTextArea plot) {
        for(int i = 0; i < labels.length; ++i) {
            if(i == labels.length - 1) {
                JLabel plotLabel = new JLabel(labels[i] + ": ", JLabel.RIGHT);
                plotLabel.setForeground(foreground);
                plotLabel.setLabelFor(plot);
                labelPanel.add(plotLabel);
                JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 15));
                p.add(plot);
                fieldPanel.add(p);
            } else {
                fields[i] = new JTextField();
                fields[i].setToolTipText(labels[i]);
                fields[i].setColumns(50);

                JLabel label = new JLabel(labels[i] + ": ", JLabel.RIGHT);
                label.setForeground(foreground);
                label.setLabelFor(fields[i]);

                labelPanel.add(label);
                JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 15));
                p.setBackground(background);
                p.add(fields[i]);
                fieldPanel.add(p);
            }
        }
    }

    private void readyPanelsForSwitching() {
        for(int i = 1; i < panels.length; ++i) {
            if(panels[i] != null && panels[i].isDisplayable()) {
                mainContainer.remove(panels[i]);
            }
        }
        mainContainer.repaint();
        mainContainer.revalidate();
    }
}
