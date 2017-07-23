package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    // There are a total of 5 JPanels:
    //      panels[0] = Menu
    //      panels[1] = Home
    //      panels[2] = Search
    //      panels[3] = Add
    //      panels[4] = Remove
    // The menu panel contains all the main buttons on the left, and a section
    // on the right where each of the other panels are swapped out based on
    // which left column button is selected.
    private static final JPanel[] panels = new JPanel[5];
    // Array of labels for each potential field on a panel.
    private static final String[] labels =
            {"IMDB ID", "Title", "Year", "Content Rating", "Genre", "Actors", "Rating", "Runtime", "Plot"};

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
        mainFrame.setLocation(500, 250);
        mainFrame.setResizable(false);

        // Retrieves content pane so objects may be added to it.
        mainContainer = mainFrame.getContentPane();
        // Sets the layour for the main fram.
        mainContainer.setLayout(new BorderLayout());
    }

    /**
     * Starts the GUI. Creates the main frame, sets initial configurations.
     * Calls the mainPanelShow function, homePanelShow then packs the main frame
     * and sets each the initial frame visible.
     */
    void startTracker() {
        // Creates the menu panel (panels[0]) with 5 rows and one column.
        panels[0] = new JPanel(new GridLayout(5,1));

        // Set up menu buttons on left of panel.
        menuPanelShow();
        // Set up home panel on right of panel.
        homePanelShow();
        // Pack objects into the main frame.
        mainFrame.pack();
        // Set the main frame to be visible.
        mainFrame.setVisible(true);
    }

    /**
     * Sets up the menu panel. This panel consists of five buttons:
     * Home, Search, Add, Remove, and Exit.
     */
    private void menuPanelShow() {
        // Set size size of menu panel.
        panels[0].setPreferredSize(new Dimension(175,600));

        // Create and configure each button in the panel.
        JButton homeBut = new JButton("Home");
        homeBut.setBackground(BACKGROUND);
        homeBut.setForeground(FOREGROUND);
        JButton searchBut = new JButton("Search Movies");
        searchBut.setBackground(BACKGROUND);
        searchBut.setForeground(FOREGROUND);
        JButton addBut =  new JButton("Add Movie");
        addBut.setBackground(BACKGROUND);
        addBut.setForeground(FOREGROUND);
        JButton remBut = new JButton("Remove Movie");
        remBut.setBackground(BACKGROUND);
        remBut.setForeground(FOREGROUND);
        JButton exitBut = new JButton("Exit");
        exitBut.setBackground(BACKGROUND);
        exitBut.setForeground(FOREGROUND);

        // Add each button to the menu panel.
        panels[0].add(homeBut);
        panels[0].add(searchBut);
        panels[0].add(addBut);
        panels[0].add(remBut);
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
            homePanelShow();
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

    /**
     * Sets up the home panel (panels[1]). The home panel contains basic
     * instructions on how to use the application.
     */
    private void homePanelShow() {
        // Sets the title for the main frame home panel.
        mainFrame.setTitle("Movie Tracker");
        // Creates the home panel and configurations.
        panels[1] = new JPanel();
        panels[1].setPreferredSize(new Dimension(800,400));
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
                        + "1. Search Movies: \n\n"
                        + "2. Add Movie: \n\n"
                        + "3. Remove Movie: \n\n"
        );

        // Add the text area to the panel.
        panels[1].add(instructions);
        // Add the home panel to the right side of the main container.
        mainContainer.add(panels[1], BorderLayout.EAST);
    }

    // Sets up the search panel (panels[2]). The search panel is used to search
    // the database for a movie matching either the IMDB Id, or the title with
    // an optional year parameter.
    private void searchPanelShow() {
        // Sets the title of the main frame for the search panel.
        mainFrame.setTitle("Movie Tracker: Search Movies");

        // Creates and configures the panel for the labels on the search panel.
        JPanel labelPanel = new JPanel(new GridLayout(labels.length, 1));
        labelPanel.setPreferredSize(new Dimension(123, 15));
        labelPanel.setBackground(BACKGROUND);
        // Creates and configures the panel for the fields on the search panel
        // and gives the field panel a grid layout. The number of rows equals
        // the number of labels created and with one column. Although the panel
        // does not show all of the rows, the labels.length value is used to
        // keep consistent spacing between the panels.
        JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
        fieldPanel.setBackground(BACKGROUND);

        // Sets the number of fields to be included on the search panel.
        // The number of fields is equal to the number of labels so that
        // if the movie exists in the database, the information for the
        // movie can be displayed properly.
        JTextField[] fields = new JTextField[labels.length];
        JTextArea plot = new JTextArea(5, 50);

        // Creates and configurs the search panel.
        panels[2] = new JPanel(new BorderLayout());
        panels[2].setBackground(BACKGROUND);
        panels[2].setPreferredSize(new Dimension(800,400));
        // Add label panel to the left on the search panel.
        panels[2].add(labelPanel, BorderLayout.WEST);
        // Add the fields to the right on the search panel.
        panels[2].add(fieldPanel, BorderLayout.CENTER);
        // Adds the content to the label and field panels.
        addPanelContent(labelPanel, fieldPanel, labels, fields, plot);

        // Create search button.
        JButton goSearch = new JButton("Search Movie Collection");

        // Add the search button to the bottom of the search panel.
        panels[2].add(goSearch, BorderLayout.SOUTH);
        // Add the search panel to the left on the main container.
        mainContainer.add(panels[2], BorderLayout.EAST);

        // TODO: Implement database
        // TODO: Create separate window to display results since there could
        // TODO: be more than one result (instead of just the one that will
        // TODO: currently be displayed.
        // Action listener for the search panel. Search is done based on the
        // IMDB Id or the title and year (optional) of the movie. Displays a
        // single result utilizing all the fields on the search panel.
        goSearch.addActionListener(doSearchThing ->
                optionPaneErrorMessage("Database Not Implemented Yet\n",
                        "Database Error")
        );
    }

    // Sets up the add panel (panels[3]). The add panel lists all the fields
    // for a movie. The add panel contains three buttons: Clear Fields,
    // Search Web, and Add Movie. The Clear Fields button clears all the
    // fields on the panel. The Search Web button utilizes an API to gather
    // data about the movie and populates the fields with that data. The
    // Add Movie button adds the data in the text fields to the database.
    private void addPanelShow() {
        // Sets the title for adding the add panel.
        mainFrame.setTitle("Movie Tracker: Add Movie");

        // Creates a panel to put the labels on and configures it.
        JPanel labelPanel = new JPanel(new GridLayout(labels.length, 1));
        labelPanel.setPreferredSize(new Dimension(123, 15));
        labelPanel.setBackground(BACKGROUND);

        // Creates a pnael to put the fields on and configures it.
        JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
        fieldPanel.setBackground(BACKGROUND);
        JTextField[] fields = new JTextField[labels.length];

        // Creates a text area to display the plot of the movie.
        JTextArea plot = new JTextArea(5, 50);

        // Creates the add movie panel and configures it.
        panels[3] = new JPanel(new BorderLayout());
        panels[3].setBackground(BACKGROUND);
        panels[3].setPreferredSize(new Dimension(800,400));

        // Adds the label and field panels to the add movie panel.
        panels[3].add(labelPanel, BorderLayout.WEST);
        panels[3].add(fieldPanel, BorderLayout.CENTER);

        // Adds the labels and fields to the label and field panels.
        addPanelContent(labelPanel, fieldPanel, labels, fields, plot);

        // Creates the add, search web and clear fields buttons.
        JButton addBut = new JButton("Add Movie");
        JButton searchWebBut = new JButton("Search Web");
        JButton clearBut = new JButton("Clear Fields");

        // Creates the panel to place the buttons on.
        JPanel buttonPanel = new JPanel(new BorderLayout());
        // Sets the add button to the left on the button panel.
        buttonPanel.add(addBut, BorderLayout.EAST);
        // Sets the search web button in the center on the button panel.
        buttonPanel.add(searchWebBut, BorderLayout.CENTER);
        // Sets the clear fields button to the right on the button panel.
        buttonPanel.add(clearBut, BorderLayout.WEST);

        // Adds the button panel to the add movie panel.
        panels[3].add(buttonPanel, BorderLayout.SOUTH);

        // Adds the add movie panel to the left on the main container.
        mainContainer.add(panels[3], BorderLayout.EAST);
        // Sets the search web button as the default button.
        mainFrame.getRootPane().setDefaultButton(searchWebBut);

        // TODO: Implement database
        // Adds an action listener to the add movie button. Inserts the
        // current movie listed in the fields to the database.
        addBut.addActionListener(doAddThing -> {
            // Ensures the id field is not null or empty.
            if(fields[0].getText() != null && !fields[0].getText().isEmpty()) {
                // Ensures the year is in a valid year format.
                if (fields[2].getText().matches("^(19|20)\\d{2}$")) {
                    // Attempt to insert into database.
                    try {
                        db.insertMovie(fields[0].getText(), fields[1].getText(),
                                fields[2].getText(), fields[3].getText(), fields[4].getText(),
                                fields[5].getText(), fields[6].getText(), fields[7].getText(),
                                plot.getText());
                        // Show success message if movie insert is successful.
                        JOptionPane.showMessageDialog(
                                mainContainer, "Movie successfully inserted!",
                                "Successful operation",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        // Clear the fields on successful insertion.
                        clearFields(fields, plot);
                    } catch (SQLException e) {
                        // If something bad happened, show error message.
                        optionPaneExceptionMessage(mainContainer, e);
                    }
                } else {
                    // If invalid year format, display error message.
                    optionPaneErrorMessage("Please ensure that the year is a valid number!",
                                           "Error: Year field is not a valid number");

                    clearFields(fields, plot);
                }
            } else {
                // If missing movie id (field[0]), display error message.
                optionPaneErrorMessage("Movie is missing an ID!",
                                       "Error: Unsuccessful Operation");
            }
        });

        // TODO: Create separate window to display API results since there could
        // TODO: be more than one result (instead of just taking the first
        // TODO: result in the array of results.
        // Adds an action listener to the search web button. Uses the
        // API to gather information about the movie and  populates each
        // of the corresponding fields.
        //
        // Currently only takes the first movie in the array of movies
        // returned by the API.
        searchWebBut.addActionListener(doSearchThing -> {
            // Array of MovieData objects to store all the results.
            MovieData[] movie = null;
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
                    movie = imdbApi.getMovieData(imdbId, title, year);
                } catch (IOException e) {
                    // If something weird happened. This should not ever be
                    // executed unless there is an issue with the API
                    // (Currently using www.theimdbapi.org).
                    optionPaneErrorMessage("Error: Something crazy and" +
                                           " weird happened.\n\nCheck 'www.theimdbapi.org"
                                           + " to ensure that its working still.",
                                           "Error: Unsuccessful Operation");
                }

                // Checks to see if the API found a movie.
                if(movie != null) {
                    title = movie[0].getTitle();
                    if(title == null || title.isEmpty()) {
                        optionPaneErrorMessage("A movie matching the id or" +
                                               " title was not found!\n\nTry modifying" +
                                               " the search! (maybe add a year)",
                                               "Movie Not Found");
                        clearFields(fields, plot);
                    } else {
                        fields[0].setText(movie[0].getImdb_id());
                        fields[1].setText(movie[0].getTitle());
                        fields[2].setText(movie[0].getYear());
                        fields[3].setText(movie[0].getContent_rating());
                        fields[4].setText(movie[0].getGenre());
                        fields[5].setText(movie[0].getStars());
                        fields[6].setText(movie[0].getRating());
                        fields[7].setText(movie[0].getLength());
                        plot.setText(movie[0].getDescription());
                    }
                }
            }
        });

        clearBut.addActionListener(e -> clearFields(fields, plot));
    }

    private void clearFields(JTextField[] fields, JTextArea plot) {
        for(int i = 0; i < labels.length - 1; ++i) {
            fields[i].setText("");
        }
        plot.setText("");
    }

    private void removePanelShow() {
        mainFrame.setTitle("Movie Tracker: Remove Movie");

        JPanel labelPanel = new JPanel(new GridLayout(labels.length, 1));
        labelPanel.setPreferredSize(new Dimension(123, 15));
        labelPanel.setBackground(BACKGROUND);

        JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
        fieldPanel.setBackground(BACKGROUND);

        JTextField[] fields = new JTextField[2];

        panels[4] = new JPanel(new BorderLayout());
        panels[4].setBackground(BACKGROUND);
        panels[4].setPreferredSize(new Dimension(800,400));
        panels[4].add(labelPanel, BorderLayout.WEST);
        panels[4].add(fieldPanel, BorderLayout.CENTER);
        addPanelContent(labelPanel, fieldPanel, labels, fields, null);

        JButton remBut = new JButton("REMOVE MOVIE");
        panels[4].add(remBut, BorderLayout.SOUTH);

        mainContainer.add(panels[4], BorderLayout.EAST);

        remBut.addActionListener(doRemoveThing ->
                optionPaneErrorMessage("Database Not Implemented Yet\n",
                                       "Database Error")
        );
    }

    private void addPanelContent(JPanel labelPanel,
                                 JPanel fieldPanel,
                                 String[] labels,
                                 JTextField[] fields,
                                 JTextArea plot) {
        for(int i = 0; i < fields.length; ++i) {
            if(plot != null && i == labels.length - 1) {
                plot.setBorder(new EmptyBorder(2,2,2,2));
                plot.setToolTipText(labels[labels.length - 1]);
                plot.setWrapStyleWord(true);
                plot.setLineWrap(true);

                JLabel plotLabel = new JLabel(labels[i] + ": ", JLabel.RIGHT);
                plotLabel.setForeground(FOREGROUND);
                plotLabel.setLabelFor(plot);

                labelPanel.add(plotLabel);
                JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
                p.setBackground(BACKGROUND);
                p.add(plot);
                fieldPanel.add(p);
            } else {
                fields[i] = new JTextField();
                fields[i].setToolTipText(labels[i]);
                fields[i].setColumns(50);

                JLabel label = new JLabel(labels[i] + ": ", JLabel.RIGHT);
                label.setForeground(FOREGROUND);
                label.setLabelFor(fields[i]);

                labelPanel.add(label);
                JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 30));
                p.setBackground(BACKGROUND);
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

    private void optionPaneExceptionMessage(Container mainContainer, Exception error) {
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
}
