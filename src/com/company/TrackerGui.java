/*
    Copyright 2017 RJ Russell

    Permission is hereby granted, free of charge, to any person obtaining a copy of
    this software and associated documentation files (the "Software"), to deal in
    the Software without restriction, including without limitation the rights to
    use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
    the Software, and to permit persons to whom the Software is furnished to do so,
    subject to the following conditions:

    The above copyright notice and this permission notice shall be included
    in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
    OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
    IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
    CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
    TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
    SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/


package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author RJ Russell
 */

class TrackerGui {
    // GUI Colors.
    private static final Color BACKGROUND = new Color(121,97,50);
    private static final Color FOREGROUND = Color.WHITE;

    private static final int POS_X = 500;
    private static final int POS_Y = 250;

    private static final int MENU_WIDTH = 175;
    private static final int MENU_HEIGHT = 600;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;
    private static final int JTABLE_HEADER_HEIGHT = 32;

    // There are a total of 5 JPanels:
    //      panels[0] = Menu
    //      panels[1] = Home
    //      panels[2] = Search
    //      panels[3] = Add
    //      panels[4] = Remove
    // The menu panel contains all the main buttons on the left, and a section
    // on the right where each of the other panels are swapped out based on
    // which left column button is selected.
    private static final JPanel[] panels = new JPanel[6];

    // Object for database. Initializes connection on creation.
    private MovieDB db = null;
    // Object for the API used to gather movie data.
    private ImdbApi imdbApi = null;

    // The parent container for all frames.
    private Container mainContainer;
    // The parent frame for all other frames.
    private JFrame mainFrame;

    TrackerGui() {
        // Creates new database object.
        try {
            db = new MovieDB();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        imdbApi = new ImdbApi();

        // Creates main frame.
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setBackground(BACKGROUND);
        mainFrame.setLocation(POS_X, POS_Y);
        mainFrame.setResizable(false);

        // Retrieves content pane so objects may be added to it.
        mainContainer = mainFrame.getContentPane();
        // Sets the layout for the main fram.
        mainContainer.setLayout(new BorderLayout());
    }

    /**
     * Starts the GUI. Creates the main frame, sets initial configurations.
     * Calls the mainPanelShow function, buildHomePanel then packs the main frame
     * and sets each the initial frame visible.
     */
    void startTracker() {
        // Creates the menu panel (panels[0]) with 5 rows and one column.
        panels[0] = new JPanel(new GridLayout(5,1));

        // Set up menu buttons on left of panel.
        buildMenuPanel();
        // Set up home panel on right of panel.
        buildHomePanel();
        // Pack objects into the main frame.
        mainFrame.pack();
        // Set the main frame to be visible.
        mainFrame.setVisible(true);
    }

    /**
     * Sets up the menu panel. This panel consists of five buttons:
     * Home, Search, Add, Remove, and Exit.
     */
    private void buildMenuPanel() {
        // Set size size of menu panel.
        panels[0].setPreferredSize(new Dimension(MENU_WIDTH,MENU_HEIGHT));
        panels[0].setBackground(BACKGROUND);

        // Create and configure each button in the panel.
        JButton homeBut = new JButton("Home");
        homeBut.setBackground(BACKGROUND);
        homeBut.setForeground(FOREGROUND);
        JButton addBut =  new JButton("Add Movie");
        addBut.setBackground(BACKGROUND);
        addBut.setForeground(FOREGROUND);
        JButton searchBut = new JButton("Search Movies");
        searchBut.setBackground(BACKGROUND);
        searchBut.setForeground(FOREGROUND);
//        JButton remBut = new JButton("Remove Movie");
//        remBut.setBackground(BACKGROUND);
//        remBut.setForeground(FOREGROUND);
        JButton exitBut = new JButton("Exit");
        exitBut.setBackground(BACKGROUND);
        exitBut.setForeground(FOREGROUND);

        // Add each button to the menu panel.
        panels[0].add(homeBut);
        panels[0].add(addBut);
        panels[0].add(searchBut);
//        panels[0].add(remBut);
        panels[0].add(exitBut);

        // Add the menu panel to the left side of the main frame panel.
        mainContainer.add(panels[0], BorderLayout.WEST);

        // Set up action listeners for each of the menu buttons. Each button
        // pertains to one of the actions that can be performed through the menu
        // options and displays the related panel. First the
        // readyPanelsForSwitching function is called, in order to clear the
        // existing panel in on the main container. Next, the appropriate
        // function is called in order to add the corresponding panel to the
        // main container.
        homeBut.addActionListener(e -> {
            readyPanelsForSwitching();
            buildHomePanel();
        });

        searchBut.addActionListener(e -> {
            readyPanelsForSwitching();
            searchPanelShow();
        });

        addBut.addActionListener(e -> {
            readyPanelsForSwitching();
            buildAddPanel();
        });

//        remBut.addActionListener(e -> {
//            readyPanelsForSwitching();
//            buildRemovePanel();
//        });

        exitBut.addActionListener(actionEvent -> System.exit(0));
    }

    /**
     * Sets up the home panel (panels[1]). The home panel contains basic
     * instructions on how to use the application.
     */
    private void buildHomePanel() {
        // Sets the title for the main frame home panel.
        mainFrame.setTitle("Movie Tracker");
        // Creates the home panel and configurations.
        panels[1] = new JPanel();
        panels[1].setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panels[1].setBackground(BACKGROUND);

        // Makes a text area to display the instructions for the application
        // and configures it.
        JTextArea instructions = new JTextArea();
        instructions.setBackground(BACKGROUND);
        instructions.setForeground(FOREGROUND);
        instructions.setText(
                "Simple directions for the Movie Tracker Application\n\n"    +
                        "The Home and Exit buttons should be self explanatory. " +
                        "If you happen to be confused by them, punch yourself.\n\n"
                        + "1. Search Movies:\n    Coming Soon!\n\n"
                        + "2. Add Movie: \n    Coming Soon!\n\n"
                        + "3. Remove Movie:\n    Coming Soon!\n\n"
        );
        instructions.setEditable(false);
        // Add the text area to the panel.
        panels[1].add(instructions);
        // Add the home panel to the right side of the main container.
        mainContainer.add(panels[1], BorderLayout.EAST);
    }

    // ====================================================
    // ADD PANEL
    // ====================================================
    // Sets up the add panel (panels[3]). The add panel lists all the fields
    // for a movie. The add panel contains three buttons: Clear Fields,
    // Search Web, and Add Movie. The Clear Fields button clears all the
    // fields on the panel. The Search Web button utilizes an API to gather
    // data about the movie and populates the fields with that data. The
    // Add Movie button adds the data in the text fields to the database.
    private void buildAddPanel() {
        final String[] labels = {"IMDB ID", "Title", "Year"};

        // Sets the title for adding the add panel.
        mainFrame.setTitle("Movie Tracker: Add Movie");

        // Creates a panel to put the labels on and configures it.
        JPanel addPanel = new JPanel(new GridBagLayout());
        addPanel.setBackground(BACKGROUND);

        JTextField[] fields = new JTextField[labels.length];

        // Adds the labels and fields to the label and field panels.
        addPanelContent(addPanel, labels, fields, true);

        // Creates the add movie panel and configures it.
        panels[2] = new JPanel(new BorderLayout());
        panels[2].setBackground(BACKGROUND);
        panels[2].setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Adds the label and field panels to the add movie panel.
        panels[2].add(addPanel, BorderLayout.WEST);
//        panels[2].add(fieldPanel, BorderLayout.CENTER);

        // Creates the add, search web and clear fields buttons.
        JButton webSearchBut = new JButton("Search Web");
        JButton clearBut = new JButton("Clear Fields");

        // Creates the panel to place the buttons on.
        JPanel buttonPanel = new JPanel(new BorderLayout());
        // Sets the search web button in the center on the button panel.
        buttonPanel.add(webSearchBut, BorderLayout.CENTER);
        // Sets the clear fields button to the right on the button panel.
        buttonPanel.add(clearBut, BorderLayout.EAST);

        // Adds the button panel to the add movie panel.
        panels[2].add(buttonPanel, BorderLayout.SOUTH);

        // Adds the add movie panel to the left on the main container.
        mainContainer.add(panels[2], BorderLayout.EAST);
        // Sets the search web button as the default button.
        mainFrame.getRootPane().setDefaultButton(webSearchBut);

        // Adds an action listener to the search web button. Uses the
        // API to gather information about the movie and  populates each
        // of the corresponding fields.
        webSearchBut.addActionListener(doSearchThing -> {
            // Array of MovieData objects to store all the results.
            MovieData[] movies;
            // Gets the text from each of the corresponding fields.
            String imdbId = fields[0].getText();
            String title = fields[1].getText();
            String year = fields[2].getText();
            // Checks to ensure that the search operation has a movie id
            // or a title before sending the data to the API.
            if(imdbId.isEmpty() && title.isEmpty()) {
                optionPaneErrorMessage("Error: Please enter the IMDB Id or" +
                                " the title of the movie to be searched.",
                                "Error: Unsuccessful Operation");
            } else {
                // If a valid id or title is supplied.
                try {
                    // Hit the API to gather movie data.
                    movies = imdbApi.getMovieData(imdbId, title, year);
                    if(movies != null) {
                        // Checks to see if the API found a movie.
                        if(movies.length == 0 || movies[0].getTitle().isEmpty()) {
                            optionPaneErrorMessage("A movie matching the id or" +
                                            " title was not found!\n\nTry modifying" +
                                            " the search! (maybe add a year)",
                                            "Movie Not Found");
                            clearFields(fields, labels);
                        } else {
//                            consoleDisplayResults(movies);
                            buildResultsPanel(movies, true);
                        }
                    }
                } catch (IOException e) {
                    // If something weird happened. This should not ever be
                    // executed unless there is an issue with the API
                    // (Currently using www.theimdbapi.org).
                    optionPaneErrorMessage("Error: Something crazy and" +
                                    " weird happened.\n\nCheck 'www.theimdbapi.org" +
                                    " to ensure that its working still.",
                                    "Error: Unsuccessful Operation");
                }
            }
        });

        clearBut.addActionListener(e -> clearFields(fields, labels));
    }

    // ====================================================
    // SEARCH PANEL
    // ====================================================
    // Sets up the search panel (panels[2]). The search panel is used to search
    // the database for a movie matching either the IMDB Id, or the title with
    // an optional year parameter.
    private void searchPanelShow() {
        // Array of labels for each potential field on a panel.
        final String[] labels = {"Title", "Year",
                "Content Rating", "Genre", "Actors", "Rating"};
        // Sets the title of the main frame for the search panel.
        mainFrame.setTitle("Movie Tracker: Search Movies");

        // Creates and configures the panel for the labels on the search panel.
        // Creates a panel to put the labels on and configures it.
        JPanel addPanel = new JPanel(new GridBagLayout());
        addPanel.setBackground(BACKGROUND);

        JTextField[] fields = new JTextField[labels.length];

        // Adds the labels and fields to the label and field panels.
        addPanelContent(addPanel, labels, fields, false);

        // Creates and configurs the search panel.
        panels[3] = new JPanel(new BorderLayout());
        panels[3].setBackground(BACKGROUND);
        panels[3].setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // Add label panel to the left on the search panel.
        panels[3].add(addPanel, BorderLayout.WEST);

        // Create search button.
        JButton dbSearchBut = new JButton("Search Movie Collection");

        // Add the search button to the bottom of the search panel.
        panels[3].add(dbSearchBut, BorderLayout.SOUTH);
        // Add the search panel to the left on the main container.
        mainContainer.add(panels[3], BorderLayout.EAST);
        mainFrame.getRootPane().setDefaultButton(dbSearchBut);

        // Action listener for the search panel. Search is done based on the
        // IMDB Id or the title and year (optional) of the movie. Displays a
        // single result utilizing all the fields on the search panel.
        dbSearchBut.addActionListener(doSearchThing -> {
            MovieData[] movies;
            try {
                movies = db.searchAll();
                if(movies == null) {
                    JOptionPane.showMessageDialog(
                            mainContainer, "No Results Found",
                            "Search Notification",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    buildResultsPanel(movies, false);
                }
            } catch(SQLException e) {
               optionPaneExceptionMessage(e);
            }
        });
    }

    // ====================================================
    // RESULTS PANEL
    // ====================================================
    private void buildResultsPanel(MovieData[] movies, boolean isAdd) {
        JTable table = new JTable(new Results(movies));
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        table.setBackground(BACKGROUND);
        table.setForeground(FOREGROUND);
        table.setFont(new Font("Source Code Pro", Font.PLAIN, 14));
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumn column;
        for(int col = 0; col < table.getColumnCount(); ++col) {
            column = table.getColumnModel().getColumn(col);
            switch(col) {
                case 0:
                    // IMDB Id
                    column.setPreferredWidth(80);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 1:
                    // Title
                    column.setPreferredWidth(140);
                    break;
                case 2:
                    // Year
                    column.setPreferredWidth(45);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 3:
                    // Content Rating
                    column.setPreferredWidth(70);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 4:
                    // Genre
                    column.setPreferredWidth(120);
                    break;
                case 5:
                    // Actors
                    column.setPreferredWidth(120);
                    break;
                case 6:
                    // Rating
                    column.setPreferredWidth(50);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 7:
                    // Runtime
                    column.setPreferredWidth(65);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 8:
                    // Plot
                    column.setPreferredWidth(300);
                    break;
                default:
                    column.setPreferredWidth(0);
            }
        }


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setColumnHeader(new JViewport() {
            @Override
            public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                d.height = JTABLE_HEADER_HEIGHT;
                return d;
            }
        });

        panels[4] = new JPanel(new BorderLayout());
        panels[4].setBackground(BACKGROUND);
        panels[4].setPreferredSize(new Dimension(WIDTH, HEIGHT));

        if(isAdd) {
            JButton addBut = new JButton("Add Selected Movie");
            scrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT - addBut.getPreferredSize().height));
            panels[4].add(scrollPane, BorderLayout.NORTH);
            panels[4].add(addBut, BorderLayout.SOUTH);

            addBut.addActionListener(addMovieEvent -> {
                int row = table.getSelectedRow();
                if(row < 0) {
                    optionPaneErrorMessage("Please select a row!", "No row selected");
                } else {
                    Results mt = (Results) table.getModel();
                    MovieData rowData = mt.getRowAt(table.getSelectedRow());

                    try {
                        if(confirmDialogYesNo(rowData, "Add this movie?") == 0) {
                            db.insertMovie(new MovieData(rowData));
                            JOptionPane.showMessageDialog(
                                    mainContainer, "Movie successfully inserted!",
                                    "Successful operation",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    } catch (SQLException e) {
                        // If something bad happened, show error message.
                        // TODO: Set this SQL Code as a constant.
                        if(e.getSQLState().equalsIgnoreCase("23505")) {
                            optionPaneErrorMessage("Selected movie is already in database!",
                                    "Cannot add movie");
                        } else {
                            System.out.println(e.getSQLState());
                            optionPaneExceptionMessage(e);
                        }
                    }
                }
            });
        } else {
            JButton remBut = new JButton("Remove");
            // Creates the panel to place the buttons on.
            JPanel buttonPanel = new JPanel(new BorderLayout());
            // Sets the search web button in the center on the button panel.
            buttonPanel.add(remBut, BorderLayout.CENTER);
            scrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT - remBut.getPreferredSize().height));
            // Sets the clear fields button to the right on the button panel.
            panels[4].add(scrollPane, BorderLayout.NORTH);
            panels[4].add(remBut, BorderLayout.SOUTH);

            // Update cell action.
            table.getModel().addTableModelListener(tableModelEvent -> {
                Results mt = (Results) table.getModel();
                MovieData rowData = mt.getRowAt(tableModelEvent.getFirstRow());
                if(confirmDialogYesNo(rowData, "Update this movie? Is the data correct??") == 0) {
                    try {
                        db.update(rowData);
                    } catch (SQLException e) {
                        optionPaneExceptionMessage(e);
                    }
                }
            });

            // Remove button actions.
            remBut.addActionListener(removeEvent -> {
                Results mt = (Results) table.getModel();
                MovieData rowData = mt.getRowAt(table.getSelectedRow());

                // TODO: Figure out a better way of doing this later?
                if(confirmDialogYesNo(rowData, "Remove this movie? Are you sure??") == 0) {
                    try {
                        db.removeMovie(rowData.getId());
                        MovieData[] reloadMovies = db.searchAll();
                        readyPanelsForSwitching();
                        buildResultsPanel(reloadMovies, false);
                    } catch (SQLException e) {
                        optionPaneExceptionMessage(e);
                    }
                }
            });
        }

        readyPanelsForSwitching();
        mainContainer.add(panels[4], BorderLayout.EAST);
    }

    private void addPanelContent(JPanel addPanel, String[] labels,
                                 JTextField[] fields, boolean isAdd) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = (isAdd) ? new Insets(0,0,60,5) : new Insets(0, 0, 50, 5);
        for(int i = 0; i < labels.length; ++i) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.ipadx = (isAdd) ? 80 : 70;

            JLabel label = new JLabel(labels[i] + ": ", JLabel.RIGHT);
            label.setForeground(FOREGROUND);
            label.setLabelFor(fields[i]);
            label.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
            gbc.anchor = (gbc.gridx == 0) ? GridBagConstraints.EAST : GridBagConstraints.WEST;
            gbc.fill = (gbc.gridx == 0) ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;

            fields[i] = new JTextField();
            fields[i].setToolTipText(labels[i]);
            fields[i].setColumns(50);
            fields[i].setFont(new Font("DejaVu Sans", Font.PLAIN, 12));

            addPanel.add(label, gbc);
            ++gbc.gridx;
            addPanel.add(fields[i], gbc);
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

    private int confirmDialogYesNo(MovieData rowData, String dialog) {
        JTextArea movieInfo = new JTextArea();
        movieInfo.setText(rowData.toString());
        movieInfo.setRows(rowData.toString().split("\n").length);
        movieInfo.setColumns(50);
        movieInfo.setEditable(false);
        JScrollPane infoScroll = new JScrollPane(movieInfo);

        return JOptionPane.showConfirmDialog(
                null,
                infoScroll,
                dialog,
                JOptionPane.YES_NO_OPTION
        );
    }

    private void optionPaneExceptionMessage(Exception error) {
        StringBuilder sb = new StringBuilder();
        sb.append(error.getMessage()).append("\n\n");
        for(StackTraceElement ste : error.getStackTrace()) {
            sb.append(ste.toString()).append("\n");
        }
        JTextArea errorArea = new JTextArea(sb.toString());

        JScrollPane errorScrollPane = new JScrollPane(errorArea);
        errorScrollPane.setPreferredSize(new Dimension(480,320));

        JOptionPane.showMessageDialog(mainContainer, errorScrollPane,
                "Error: Database borked the operation",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void optionPaneErrorMessage(String message, String error) {
        JOptionPane.showMessageDialog(
                mainContainer, message, error, JOptionPane.ERROR_MESSAGE
        );
    }

    private void clearFields(JTextField[] fields, String[] labels) {
        for(int i = 0; i < labels.length - 1; ++i) {
            fields[i].setText("");
        }
    }
}
