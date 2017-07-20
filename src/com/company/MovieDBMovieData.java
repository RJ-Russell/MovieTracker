package com.company;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 7/19/17
 *
 * @author RJ Russell
 */
class MovieDBMovieData implements DataSource {
    @Override
    public Map<String, String> getMovieData(String imdbId, String title, String year) throws IOException {
        String url = "http://www.theimdbapi.org/api/find/movie?title=transformers&year=2007";
        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }

        Gson gson = new Gson();

        MovieData[] movieResponse = gson.fromJson(sb.toString(), MovieData[].class);
        for(MovieData v : movieResponse) {
            System.out.println(v);
        }
        return null;
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
                "Stars: " + Arrays.stream(stars)
                    .map(String::toString)
                    .collect(Collectors.joining(", ")) + "\n" +
                "Year: " + year + "\n" +
                "Genre: " + Arrays.stream(genre)
                    .map(String::toString)
                    .collect(Collectors.joining(", ")) + "\n";
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