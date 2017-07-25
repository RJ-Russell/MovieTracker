package com.company;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author RJ Russell
 */

class ImdbApi {
    MovieData[] getMovieData(String imdbId, String title, String year) throws IOException {

        String url = "http://www.theimdbapi.org/api";
        if (!imdbId.equals("")) {
            url += "/movie?movie_id=" + imdbId;
        } else if (!title.equals("")) {
            title = title.replace(" ", "+");
            url += "/find/movie?title=" + title;
            if(!year.equals("")) {
                url += "&year=" + year;
            }
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

        MovieData[] movieResponse;
        try {
            movieResponse = gson.fromJson(sb.toString(), MovieData[].class);
        } catch(JsonSyntaxException | IllegalStateException e) {
            movieResponse = new MovieData[1];
            movieResponse[0] = gson.fromJson(sb.toString(), MovieData.class);
        }

        return movieResponse;
    }
}

