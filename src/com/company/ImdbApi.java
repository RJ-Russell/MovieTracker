/*
    Copyright 2017 RJ Russell

    Permission is hereby granted, free of charge, to any person obtaining a copy of
    this software and associated documentation files (the "Software"), to deal in
    the Software without restriction, including without limitation the rights to
    use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
    the Software, and to permit persons to whom the Software is furnished to do so,
    subject to the following conditions:

    The above copyright notice and this permission notice shall be included
    in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
    OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
    IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
    CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
    TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
    SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

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

