package com.company;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author RJ Russell
 */

class ImdbApi {
    MovieData[] getMovieData(String imdbId, String title, String year) throws IOException {
        title = title.replace(" ", "+");

        // TODO: If searching with IMDB ID, returns single object.
        // TODO: If searching by title, returns an array of objects.
        String url = "http://www.theimdbapi.org/api";
        if (!imdbId.equals("")) {
            url += "/movie?movie_id=" + imdbId;
        } else if (!title.equals("")) {
            url += "/find/movie?title=" + title;
            if(!year.equals("")) {
                url += "&year=" + year;
            }
        } else {
            System.out.println("Not enough info provided.");
            return null;
        }
        System.out.println(url);

        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }

        Gson gson = new Gson();

        MovieData[] movieResponse = null;
        try {
            movieResponse = gson.fromJson(sb.toString(), MovieData[].class);
            for (MovieData v : movieResponse) {
                System.out.println(v);
            }
        } catch(JsonSyntaxException | IllegalStateException e) {
            movieResponse = new MovieData[1];
            movieResponse[0] = gson.fromJson(sb.toString(), MovieData.class);
            System.out.println(movieResponse);
        }

        return movieResponse;
    }
}

class MovieData {
    private String rating;
    private String description;
    private String title;
    private URLData url;
    private PosterData poster;
    private String release_date;
    private String content_rating;
    private String original_title;
    private String[] writers;
    private String imdb_id;
    private String director;
    private List<CastData> cast;
    private String length;
    private String rating_count;
    private String storyline;
    private String[] stars;

    private String year;
    private String[] genre;
    private List<TrailerData> trailer;

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

    String getRelease_date() {
        return release_date;
    }

    String getContent_rating() {
        return content_rating;
    }

    String getImdb_id() {
        return imdb_id;
    }

    String getDirector() {
        return director;
    }

    String getLength() {
        return length;
    }

    String getStoryline() {
        return storyline;
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

class URLData {
    private String url;
    private String title;
    private String year;
}
class PosterData {
    private String large;
    private String thumb;
}
class CastData {
    private String link;
    private String image;
    private String character;
    private String name;
}
class TrailerData {
    private String mimeType;
    private String definition;
    private String videoUrl;
}

class Error {
    private String _type;
    private String message;

    @Override
    public String toString() {
        return message + "\n\n";
    }
}