package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Map;

class TrackerGui {
 //   MovieDB db = new MovieDB();
    private final MovieDataScraper omdb = new MovieDataScraper();

    private final Color background = new Color(121,97,50);
    private final Color foreground = Color.WHITE;
    private final JPanel[] panels = new JPanel[5];
    private final String[] labels =
        {"IMDB ID", "Title", "Year", "Genre", "Actors", "Rated", "Runtime", "Plot"};

    private JFrame mainFrame;
    private Container mainContainer;

    void startTracker() {
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setBackground(background);
        mainFrame.setLocation(500, 250);
        mainFrame.setResizable(false);

        mainContainer = mainFrame.getContentPane();
        mainContainer.setLayout(new BorderLayout());

        panels[0] = new JPanel(new GridLayout(5,1));

        menuPanelShow();
        defaultPanelShow();
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void menuPanelShow() {
        panels[0].setPreferredSize(new Dimension(175,600));

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

        JTextArea instructions = new JTextArea();
        instructions.setBackground(background);
        instructions.setForeground(foreground);
        instructions.setText(
            "Simple directions for the Movie Tracker Application\n\n"    +
                "The Home and Exit buttons should be self explanatory. " +
                "If you happen to be confused by them, punch yourself.\n\n"
            + "1. Search Movies: \n\n"
            + "2. Add Movie: \n\n"
            + "3. Remove Movie: \n\n"
        );

        panels[1].add(instructions);
        mainContainer.add(panels[1], BorderLayout.EAST);
    }

    private void searchPanelShow() {
        mainFrame.setTitle("Movie Tracker: Search Movies");

        JPanel labelPanel = new JPanel(new GridLayout(labels.length, 1));
        labelPanel.setPreferredSize(new Dimension(123, 15));
        labelPanel.setBackground(background);
        JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
        fieldPanel.setBackground(background);
        JTextField[] fields = new JTextField[labels.length];

        JTextArea plot = new JTextArea(5, 50);

        panels[2] = new JPanel(new BorderLayout());
        panels[2].setBackground(background);
        panels[2].setPreferredSize(new Dimension(800,400));
        panels[2].add(labelPanel, BorderLayout.WEST);
        panels[2].add(fieldPanel, BorderLayout.CENTER);
        addPanelContent(labelPanel, fieldPanel, labels, fields, plot);

        JButton goSearch = new JButton("Search Movie Collection");

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
        labelPanel.setPreferredSize(new Dimension(123, 15));
        labelPanel.setBackground(background);

        JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
        fieldPanel.setBackground(background);
        JTextField[] fields = new JTextField[labels.length];

        JTextArea plot = new JTextArea(5, 50);

        panels[3] = new JPanel(new BorderLayout());
        panels[3].setBackground(background);
        panels[3].setPreferredSize(new Dimension(800,400));
        panels[3].add(labelPanel, BorderLayout.WEST);
        panels[3].add(fieldPanel, BorderLayout.CENTER);
        addPanelContent(labelPanel, fieldPanel, labels, fields, plot);

        JButton addBut = new JButton("Add Movie");
        JButton omdbBut = new JButton("Search IMDB");
        JButton clearBut = new JButton("Clear Fields");


        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(addBut, BorderLayout.EAST);
        buttonPanel.add(omdbBut, BorderLayout.CENTER);
        buttonPanel.add(clearBut, BorderLayout.WEST);

        panels[3].add(buttonPanel, BorderLayout.SOUTH);

        mainContainer.add(panels[3], BorderLayout.EAST);
        mainFrame.getRootPane().setDefaultButton(omdbBut);

        // TODO: Implement adding to database
        addBut.addActionListener((ActionEvent doAddThing) -> JOptionPane.showMessageDialog(
            mainContainer, "Error: Database not implemented yet.",
            "Error", JOptionPane.ERROR_MESSAGE
        ));

        omdbBut.addActionListener(doSearchThing -> {
            Map<String, String> movie = null;
            String imdbId = fields[0].getText();
            System.out.println("IMDB ID: " + imdbId);
            String title = fields[1].getText();
            String year = fields[2].getText();
            if(title.equals("") && imdbId.equals("")) {
                JOptionPane.showMessageDialog(
                    mainContainer, "Error: Please enter the title or the imdb ID",
                    "Error", JOptionPane.ERROR_MESSAGE
                );
            } else {
                try {
                    movie = omdb.getMovieData(imdbId, title, year);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(movie != null) {
                    title = movie.get("Title");
                    if(title == null) {
                        JOptionPane.showMessageDialog(
                            mainContainer, "Movie not found in IMDB database",
                            "Movie Not Found", JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        fields[0].setText(movie.get("imdbID"));
                        fields[1].setText(movie.get("Title"));
                        fields[2].setText(movie.get("Year"));
                        fields[3].setText(movie.get("Genre"));
                        fields[4].setText(movie.get("Actors"));
                        fields[5].setText(movie.get("Rated"));
                        fields[6].setText(movie.get("Runtime"));
                        plot.setText(movie.get("Plot"));
                    }
                }

            }
        });

        clearBut.addActionListener(e -> {
            for(int i = 0; i < labels.length - 1; ++i) {
                fields[i].setText("");
            }
            plot.setText("");
        });
    }

    private void removePanelShow() {
        mainFrame.setTitle("Movie Tracker: Remove Movie");

        JPanel labelPanel = new JPanel(new GridLayout(labels.length, 1));
        labelPanel.setPreferredSize(new Dimension(123, 15));
        labelPanel.setBackground(background);

        JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
        fieldPanel.setBackground(background);

        JTextField[] fields = new JTextField[2];

        panels[4] = new JPanel(new BorderLayout());
        panels[4].setBackground(background);
        panels[4].setPreferredSize(new Dimension(800,400));
        panels[4].add(labelPanel, BorderLayout.WEST);
        panels[4].add(fieldPanel, BorderLayout.CENTER);
        addPanelContent(labelPanel, fieldPanel, labels, fields, null);



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
        for(int i = 0; i < fields.length; ++i) {
            if(i == labels.length - 1) {
                plot.setBorder(new EmptyBorder(2,2,2,2));
                plot.setToolTipText(labels[labels.length - 1]);
                plot.setWrapStyleWord(true);
                plot.setLineWrap(true);

                JLabel plotLabel = new JLabel(labels[i] + ": ", JLabel.RIGHT);
                plotLabel.setForeground(foreground);
                plotLabel.setLabelFor(plot);

                labelPanel.add(plotLabel);
                JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
                p.setBackground(background);
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
                JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 30));
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
