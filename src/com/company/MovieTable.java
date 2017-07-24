package com.company;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
 * Created on 7/24/17
 *
 * @author RJ Russell
 */
public class MovieTable extends AbstractTableModel {

    private MovieData[] movies;
    private String[] labels;

    MovieTable(MovieData[] movies, String[] labels) {
        this.movies = movies;
        this.labels = labels;
    }

    @Override
    public int getRowCount() {
        return movies.length;
    }

    @Override
    public int getColumnCount() {
        return labels.length;
    }

    @Override
    public MovieData getValueAt(int row, int col) {
        return this.getValueAt(row, col);
    }
}
