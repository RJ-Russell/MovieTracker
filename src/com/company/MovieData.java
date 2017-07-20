package com.company;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author RJ Russell
 */

class MovieData {
    private String rating;
    private String description;
    private String title;
    private String release_date;
    private String content_rating;
    private String original_title;
    private String[] writers;
    private String imdb_id;
    private String director;
    private String length;
    private String rating_count;
    private String storyline;
    private String[] stars;
    private String year;
    private String[] genre;

// Additional fields in the JSON that are not wanted in the returned value.
//    private List<CastData> cast;
//    private URLData url;
//    private PosterData poster;
//    private List<TrailerData> trailer;

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Rating: " + rating + "\n" +
                "Description: " + description + "\n" +
                "Release Date: " + release_date + "\n" +
                "Content Rating: " + content_rating + "\n" +
                "IMDB Id: " + imdb_id + "\n" +
                "Director: " + director + "\n" +
                "Movie Length: " + length + "\n" +
                "Storyline: " + storyline + "\n" +
                "Stars: " + getStars() + "\n" +
                "Year: " + year + "\n" +
                "Genre: " + getGenre() + "\n";
    }

    String getRating() {
        return rating;
    }

    String getDescription() {
        return description;
    }

    String getTitle() {
        return title;
    }

    String getContent_rating() {
        return content_rating;
    }

    String getImdb_id() {
        return imdb_id;
    }

    String getLength() {
        return length;
    }

    String getStars() {
        return Arrays.stream(stars)
                .map(String::toString)
                .collect(Collectors.joining(", "));
    }

    String getYear() {
        return year;
    }

    String getGenre() {
        return Arrays.stream(genre)
                .map(String::toString)
                .collect(Collectors.joining(", "));
    }
}

// Additional classes that are included in the JSON and not wanted in return value.
//class URLData {
//    private String url;
//    private String title;
//    private String year;
//}
//class PosterData {
//    private String large;
//    private String thumb;
//}
//class CastData {
//    private String link;
//    private String image;
//    private String character;
//    private String name;
//}
//class TrailerData {
//    private String mimeType;
//    private String definition;
//    private String videoUrl;
//}
//
//class Error {
//    private String _type;
//    private String message;
//
//    @Override
//    public String toString() {
//        return message + "\n\n";
//    }
//}

