package com.company;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Arrays;

/**
 * Created on 7/24/17
 *
 * @author RJ Russell
 */
public class MovieTable extends AbstractTableModel {

    private String[][] movies;
    private String[] labels;

    MovieTable(MovieData[] movies, String[] labels) {
        this.labels = labels;
        this.movies = new String[movies.length][labels.length];

        System.out.println("THIS.LABELS.LENGTH: " + this.labels.length);
        System.out.println("THIS.MOVIES.LENGTH: " + this.movies.length);

        for(int i = 0; i < movies.length; ++i) {
            this.movies[i] = movies[i].toArray();
        }

        for (String[] m : this.movies) {
            System.out.println();
            for (int j = 0; j < this.movies[0].length; ++j) {
                System.out.println(m[j]);
            }
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

    public String getColumnName(int col) {
        return labels[col];
    }

    @Override
    public String getValueAt(int row, int col) {
        if(movies[row][col] == null || movies[row][col].isEmpty()) {
            return "N/A";
        }
        return this.movies[row][col];
    }

    public Class getColumnClass(int c) {
        for(int i = 0; i < movies.length; ++i) {
           Object[] row = movies[i];
           if(row[c] != null) {
               return getValueAt(i, c).getClass();
           }
        }
        return String.class;
    }
}
