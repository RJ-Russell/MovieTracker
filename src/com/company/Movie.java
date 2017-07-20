package com.company;

/**
 * @author RJ Russell
 */

class Movie {
    private String imdbId;
    private String title;
    private String year;
    private String genre;
    private String actors;
    private String rated;
    private String runtime;
    private String plot;


    Movie(String imdbId, String title, String year, String genre,
          String actors, String rated, String runtime, String plot) {
        this.imdbId = imdbId;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.actors = actors;
        this.rated = rated;
        this.runtime = runtime;
        this.plot = plot;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getActors() {
        return actors;
    }

    public String getRated() {
        return rated;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getPlot() {
        return plot;
    }
}
