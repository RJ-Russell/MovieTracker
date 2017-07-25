package com.company;

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

    MovieTable(MovieData[] movies, String[] labels, boolean isAdd) {
        if(isAdd) {
            this.labels = Arrays.copyOfRange(labels, 1, labels.length);
        } else {
            this.labels = labels;
        }
        this.movies = new String[movies.length][this.labels.length];


        for(int i = 0; i < movies.length; ++i) {
            this.movies[i] = movies[i].toArray(isAdd);
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

    @Override
    public String getValueAt(int row, int col) {
        if(movies[row][col] == null || movies[row][col].isEmpty()) {
            return "N/A";
        }
        return this.movies[row][col];
    }

    public String getColumnName(int col) {
        return labels[col];
    }

    public Class getColumnClass(int c) {
        return String.class;
    }
}
