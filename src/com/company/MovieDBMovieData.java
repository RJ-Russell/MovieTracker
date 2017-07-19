package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

/**
 * Created on 7/19/17
 *
 * @author RJ Russell
 */
public class MovieDBMovieData implements DataSource {
    @Override
    public Map<String, String> getMovieData(String imdbId, String title, String year) throws IOException {
        String url = "http://theapache64.xyz:8080/movie_db/search?keyword=Titanic";

        String charset = "UTF-8";
        InputStream is = new URL(url).openStream();
        InputStreamReader stream = new InputStreamReader(is, charset);

        StringBuilder sb = new StringBuilder();
        // TODO: Finish this scraper.

        return null;
    }
}
