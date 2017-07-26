package com.company;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.Arrays;

/**
 * Created on 7/24/17
 *
 * @author RJ Russell
 */
public class MovieTable extends AbstractTableModel implements TableModel {

    private String[][] movies;
    private String[] labels = {"ID", "IMDB ID", "Title", "Year",
            "<html>Content<br>Rating<html>", "Genre", "Actors", "Rating", "<html>Runtime<br>(min)<html>",
            "Plot"};

    MovieTable(MovieData[] movies, boolean isAdd) {
        if(isAdd) {
            labels = Arrays.copyOfRange(labels, 1, labels.length);
        }
        this.movies = new String[movies.length][this.labels.length];

        for(int i = 0; i < movies.length; ++i) {
            this.movies[i] = movies[i].toArray(isAdd);
        }
    }

    @Override
    public int getColumnCount() {
        return this.labels.length;
    }

    @Override
    public int getRowCount() {
        return this.movies.length;
    }

    @Override
    public String getValueAt(int row, int col) {
        if(movies[row][col] == null || movies[row][col].isEmpty()) {
            return "N/A";
        }
        return this.movies[row][col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public void setValueAt(Object val, int row, int col) {
        String value = (String) val;
        if(val == null || value.isEmpty()) {
            movies[row][col] = "N/A";
        } else {
            movies[row][col] = value;
        }
    }

    String[] getRowAt(int row) {
        return movies[row];
    }

    public String getColumnName(int col) {
        return labels[col];
    }

    public Class getColumnClass(int c) {
        return String.class;
    }
}
