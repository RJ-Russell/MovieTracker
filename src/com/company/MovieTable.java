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

    private MovieData[] movies;
    private String[] labels = {"IMDB ID", "Title", "Year",
            "<html>Content<br>Rating<html>", "Genre", "Actors", "Rating",
            "<html>Runtime<br>(min)<html>", "Plot"};

    MovieTable(MovieData[] movies) {
        this.movies = movies;
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
        MovieData md = movies[row];
        String retVal = null;
        switch(col) {
            case 0:
                retVal = md.getImdbId();
                break;
            case 1:
                retVal = md.getTitle();
                break;
            case 2:
                retVal = md.getYear();
                break;
            case 3:
                retVal = md.getContentRating();
                break;
            case 4:
                retVal = md.getGenre();
                break;
            case 5:
                retVal = md.getStars();
                break;
            case 6:
                retVal = md.getRating();
                break;
            case 7:
                 retVal = md.getRuntime();
                 break;
            case 8:
                retVal = md.getPlot();
                break;
        }
        return (retVal == null || retVal.isEmpty()) ? "N/A" : retVal;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        MovieData md = movies[row];
        String val = (String) value;
        switch(col) {
            case 0:
                md.setImdbId(val);
                break;
            case 1:
                md.setTitle(val);
                break;
            case 2:
                md.setYear(val);
                break;
            case 3:
                md.setContentRating(val);
                break;
            case 4:
                String[] genre = val.split(",");
                md.setGenre(Arrays.stream(genre).map(String::trim).toArray(s -> genre));
                break;
            case 5:
                String[] stars = val.split(",");
                md.setStars(Arrays.stream(stars).map(String::trim).toArray(s -> stars));
                break;
            case 6:
                md.setRating(val);
                break;
            case 7:
                md.setRuntime(val);
                break;
            case 8:
                md.setPlot(val);
                break;
        }
    }

    MovieData getRowAt(int row) {
        return movies[row];
    }

    public String getColumnName(int col) {
        return labels[col];
    }

    public Class getColumnClass(int c) {
        return String.class;
    }
}
